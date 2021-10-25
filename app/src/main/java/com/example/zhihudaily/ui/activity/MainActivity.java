package com.example.zhihudaily.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.*;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.example.zhihudaily.Bean.before.Before;
import com.example.zhihudaily.Bean.latest.LatestGroup;
import com.example.zhihudaily.Bean.latest.NewsItem;
import com.example.zhihudaily.Bean.version.Version;
import com.example.zhihudaily.R;
import com.example.zhihudaily.ui.adapter.ImageAdapter;
import com.example.zhihudaily.ui.adapter.NewsAdapter;
import com.example.zhihudaily.utils.HttpUtil;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.youth.banner.Banner;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import okhttp3.*;

public class MainActivity extends AppCompatActivity {

    static RecyclerView recyclerView;
    private static List<NewsItem> newsList = new ArrayList<>();//全部列表消息
    private final List<Before> mBeforeList = new ArrayList<>();//过往列表消息
    private final int DAYS = 3;//当天和过去3天内的新闻
    DrawerLayout mDrawerLayout;
    Banner mBanner;
    Gson gson = new Gson();
    TextView date;
    TextView month;
    TextView version;
    Version v;
    Context mContext = this;
    Calendar calendar;
    SimpleDateFormat sdf;
    int pastDay = 0;
    private LatestGroup latestGroup;//最新消息，包括列表消息和轮播消息

    public static List<NewsItem> getNewsList() {
        return newsList;
    }

    public static void setNewsList(List<NewsItem> newsList) {
        MainActivity.newsList = newsList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //沉浸式状态栏设置
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_main);

        mBanner = findViewById(R.id.banner);//轮播图控件
        recyclerView = findViewById(R.id.recyclerView_newsList);//列表控件
        RecyclerViewHeader header = findViewById(R.id.header);//嵌入列表头控件
        date = findViewById(R.id.date);
        month = findViewById(R.id.month);
        mDrawerLayout = findViewById(R.id.drawer);
        ImageButton open = findViewById(R.id.opennavi);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        header.attachTo(recyclerView);//嵌入列表头

        getLatestGroup();
        try {//如果不让线程等待，在latestGroup还没创建完毕的时候就开始执行getList等操作，导致空指针异常
            Thread.sleep(1000);
            if (latestGroup != null) {
                setBanner();
                checkNews();//查看DAYS天前的新闻
            }
            // TODO: 2021/8/18 目前是直接加载出十天内的，以后想实现下拉加载更多
            //等待checkNews
            Thread.sleep(500);
            getList();
            showDate();
            showVersion();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //点击小人可以自动呼出侧滑菜单
        open.setOnClickListener((View v) -> {
            mDrawerLayout.openDrawer(GravityCompat.END);
        });

        //为侧滑菜单选项设置点击响应
        NavigationView navi = findViewById(R.id.navigation);
        navi.setNavigationItemSelectedListener((MenuItem item) -> {
            mDrawerLayout.closeDrawers();
            return true;
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        NewsAdapter adapter = new NewsAdapter(R.layout.item_newslist, newsList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull @NotNull BaseQuickAdapter<?, ?> adapter, @NonNull @NotNull View view, int position) {
                Intent intent = new Intent(mContext, ContentActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("isImage", false);
                mContext.startActivity(intent);
            }
        });
        adapter.getLoadMoreModule().setEnableLoadMoreEndClick(true);
        adapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                for (int i = 0; i < DAYS; i++) {
                    if(i==0)
                        calendar.add(Calendar.DATE,-pastDay);
                    getBefore(sdf.format(calendar.getTime()));//将操作后的Date转换为String
                    calendar.add(Calendar.DATE, -1);
                    pastDay+=1;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                adapter.getLoadMoreModule().loadMoreComplete();
                getList();
                adapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 获取最新消息组
     */
    private void getLatestGroup() {
        HttpUtil.sendRequestWithOkHttp("http://news-at.zhihu.com/api/4/news/latest", new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data = response.body().string();
                latestGroup = gson.fromJson(data, LatestGroup.class);
            }
        });
    }

    /**
     * 获取过往消息组
     *
     * @param date 想要得到多少天前的消息
     */
    private void getBefore(String date) {
        HttpUtil.sendRequestWithOkHttp("http://news.at.zhihu.com/api/4/news/before/" + date, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data = response.body().string();
                mBeforeList.add(gson.fromJson(data, Before.class));
            }
        });
    }

    /**
     * 查看过往新闻
     *
     * @throws ParseException 转格式可能会有异常
     */
    private void checkNews() throws ParseException {
        sdf = new SimpleDateFormat("yyyyMMdd", Locale.CHINESE);
        Date date = sdf.parse(latestGroup.getDate());//把String变为Date
        calendar = Calendar.getInstance();
        assert date != null;
        calendar.setTime(date);//给日历设置date
        for (int i = 0; i < DAYS; i++) {
            calendar.add(Calendar.DATE, -1);
            getBefore(sdf.format(calendar.getTime()));//将操作后的Date转换为String
            pastDay += 1;
        }
    }

    /**
     * 为轮播图设置适配器
     */
    private void setBanner() {
        mBanner.setAdapter(new ImageAdapter(latestGroup.getTop_stories()));
    }

    /**
     * 得到recyclerView内容列表
     */
    private void getList() {
        newsList = latestGroup.getStories();//把所有今日新闻读取进来
        for (Before before : mBeforeList) {
            newsList.addAll(before.getStories());//把所有过往新闻读取进来
        }
    }

    /**
     * 左上角今天日期显示
     */
    private void showDate() {
        date.setText(latestGroup.getDate().substring(6, 8));
        String mon = latestGroup.getDate().substring(4, 6);
        String show = null;
        switch (mon) {
            case "01":
                show = "一月";
                break;
            case "02":
                show = "二月";
                break;
            case "03":
                show = "三月";
                break;
            case "04":
                show = "四月";
                break;
            case "05":
                show = "五月";
                break;
            case "06":
                show = "六月";
                break;
            case "07":
                show = "七月";
                break;
            case "08":
                show = "八月";
                break;
            case "09":
                show = "九月";
                break;
            case "10":
                show = "十月";
                break;
            case "11":
                show = "十一月";
                break;
            case "12":
                show = "十二月";
                break;
            default:
                show = "未知";
                break;
        }
        month.setText(show);
    }

    /**
     * navigationView里版本号显示
     */
    private void showVersion() {
        HttpUtil.sendRequestWithOkHttp("http://news-at.zhihu.com/api/4/version/android/2.6.0", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data = response.body().string();
                v = gson.fromJson(data, Version.class);
                runOnUiThread(() -> {
                    version = findViewById(R.id.version);
                    version.setText("V" + v.getLatest());
                });
            }
        });
    }
}
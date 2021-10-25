package com.example.zhihudaily.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.*;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.zhihudaily.R;
import com.example.zhihudaily.Bean.extra.GoodAndComments;
import com.example.zhihudaily.Bean.latest.NewsItem;
import com.example.zhihudaily.ui.adapter.NewsAdapter;
import com.example.zhihudaily.utils.HttpUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.*;

public class ContentActivity extends AppCompatActivity {

    GoodAndComments gac;
    TextView commentAmounts;
    TextView goodAmounts;
    Gson gson = new Gson();
    int position;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //返回新闻列表时滚动到点击的新闻处
        MainActivity.recyclerView.scrollToPosition(position-1);
    }

    @Override
    @SuppressLint({"ClickableViewAccessibility", "SetJavaScriptEnabled"})
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
        setContentView(R.layout.activity_content);

        ImageButton comment = findViewById(R.id.comment);
        ImageButton good = findViewById(R.id.good);
        commentAmounts = findViewById(R.id.commentAmounts);
        goodAmounts = findViewById(R.id.goodAmounts);


        /*显示出主新闻界面*/
        WebView webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        //接收当前新闻对象
        Gson gson = new Gson();
        Intent intent_get = getIntent();
//        NewsItem item = gson.fromJson(intent_get.getStringExtra("Bean"), NewsItem.class);
        position = intent_get.getIntExtra("position", 0);
        boolean isImage = intent_get.getBooleanExtra("isImage", false);
        NewsAdapter temp=(NewsAdapter)(MainActivity.recyclerView.getAdapter());
        NewsItem item= temp.getData().get(position);

        //初始化点赞按钮状态
        if (item.getIsGood())
            good.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.good_blue));
        else
            good.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.good_black));

        //加载新闻和评论
        webView.loadUrl(item.getUrl().replaceAll("\\\\", ""));
        showGoodAndComments(String.valueOf(item.getId()));

        good.setOnClickListener((View v) -> {
            if (!item.getIsGood()) {
                int plus = Integer.parseInt(goodAmounts.getText().toString()) + 1;
                goodAmounts.setText(String.valueOf(plus));//赞数加一
                good.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.good_blue));
                item.setIsGood(true);
            } else {
                int minus = Integer.parseInt(goodAmounts.getText().toString()) - 1;
                goodAmounts.setText(String.valueOf(minus));//赞数减一
                good.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.good_black));
                item.setIsGood(false);
            }

            //模拟数据更新，如果是轮播图的话不要执行这个
            if (!isImage) {
                MainActivity.recyclerView.getAdapter().notifyItemChanged(position);
//                List<NewsItem> temp = MainActivity.getNewsList();
//                temp.set(position, item);
//                MainActivity.setNewsList(temp);
            }

        });

        comment.setOnClickListener((View v) -> {
            Intent intent = new Intent(this, CommentActivity.class);
            intent.putExtra("id", item.getId());
            intent.putExtra("commentAmounts", gac.getComments());
            startActivity(intent);
        });
    }

    /**
     * 显示赞数和评论数
     */
    private void showGoodAndComments(String id) {
        HttpUtil.sendRequestWithOkHttp("http://news-at.zhihu.com/api/4/story-extra/" + id, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data = response.body().string();
                gac = gson.fromJson(data, GoodAndComments.class);
            }
        });

        try {
            Thread.sleep(500);
            commentAmounts.setText(String.valueOf(gac.getComments()));//设置点赞数和评论数
            goodAmounts.setText(String.valueOf(gac.getPopularity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
package com.example.zhihudaily.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zhihudaily.R;
import com.example.zhihudaily.Bean.comments.*;
import com.example.zhihudaily.ui.adapter.CommentsAdapter;
import com.example.zhihudaily.utils.HttpUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.*;

public class CommentActivity extends AppCompatActivity {

    public static List<Comments> mAllComments=new ArrayList<>();
    Gson gson = new Gson();
    LongComments mLongComments=null;
    ShortComments mShortComments=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //沉浸式状态栏设置
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_comment);
        Log.d("wxc", "CommentActivity布局加载完成");
        TextView howManyComments = findViewById(R.id.howManyComments);
        RecyclerView commentsContent = findViewById(R.id.commentContent);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        commentsContent.setLayoutManager(linearLayoutManager);
        Log.d("wxc", "管理器加载完成");

        Intent intent_get = getIntent();
        int id = intent_get.getIntExtra("id", 0);
        int amounts = intent_get.getIntExtra("commentAmounts", 0);
        Log.d("wxc", "获取到了信息");

        howManyComments.setText(amounts + "条评论");
        Log.d("wxc", "显示了几条评论");
        try {
            checkComment(String.valueOf(id));
            Thread.sleep(500);
            integrate();
            Thread.sleep(500);
            CommentsAdapter adapter = new CommentsAdapter(mAllComments);
            commentsContent.setAdapter(adapter);
            Log.d("wxc", "setAdapter了");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 把长评论短评论读取进来
     *
     * @param id
     */
    private void checkComment(String id) {
        HttpUtil.sendRequestWithOkHttp("http://news-at.zhihu.com/api/4/story/" + id + "/long-comments", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data = response.body().string();
                mLongComments = gson.fromJson(data, LongComments.class);
                Log.d("wxc", "读取长评论完成");
            }
        });
        HttpUtil.sendRequestWithOkHttp("http://news-at.zhihu.com/api/4/story/" + id + "/short-comments", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data = response.body().string();
                mShortComments = gson.fromJson(data, ShortComments.class);
                Log.d("wxc", "读取短评论完成");
            }
        });
        Log.d("wxc", "读取长短评论完成");
    }

    /**
     * 整合到一个List
     */
    private void integrate() {
        mAllComments=mLongComments.getComments();
        mAllComments.addAll(mShortComments.getComments());
        Log.d("wxc", "整合完成");
    }
}
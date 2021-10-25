package com.example.zhihudaily.ui.adapter;

import android.content.Intent;
import android.net.Uri;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.zhihudaily.R;
import com.example.zhihudaily.ui.activity.ContentActivity;
import com.example.zhihudaily.Bean.latest.NewsItem;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * NOTE:
 *
 * @author wxc 2021/8/16
 * @version 1.0.0
 */
public class NewsAdapter extends BaseQuickAdapter<NewsItem,NewsAdapter.ViewHolder> implements LoadMoreModule {

    public NewsAdapter(int layoutId,List<NewsItem> list) {
        super(layoutId,list);
    }

    @Override
    protected void convert(@NotNull ViewHolder viewHolder, NewsItem newsItem) {
        //使用Gilde加载图片
        Glide.with(viewHolder.mView.getContext()).load(Uri.parse(newsItem.getImages().replaceAll("\\\\", ""))).into(viewHolder.newsImage);
        viewHolder.newsTitle.setText(newsItem.getTitle());
        viewHolder.newsHint.setText(newsItem.getHint());
    }

    static class ViewHolder extends BaseViewHolder {

        private final View mView;
        private final ImageView newsImage;
        private final TextView newsTitle;
        private final TextView newsHint;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            newsImage = itemView.findViewById(R.id.newsImage);
            newsTitle = itemView.findViewById(R.id.newsTitle);
            newsHint = itemView.findViewById(R.id.hint);
        }
    }
}

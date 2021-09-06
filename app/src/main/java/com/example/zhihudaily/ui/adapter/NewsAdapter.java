package com.example.zhihudaily.ui.adapter;

import android.content.Intent;
import android.net.Uri;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.zhihudaily.R;
import com.example.zhihudaily.ui.activity.ContentActivity;
import com.example.zhihudaily.Bean.latest.NewsItem;
import com.google.gson.Gson;

import java.util.List;

/**
 * NOTE:
 *
 * @author wxc 2021/8/16
 * @version 1.0.0
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private final List<NewsItem> mList;

    public NewsAdapter(List<NewsItem> list) {
        mList = list;
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_newslist, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position) {
        NewsItem item = mList.get(position);
        //使用Gilde加载图片
        Glide.with(holder.mView.getContext()).load(Uri.parse(item.getImages().replaceAll("\\\\", ""))).into(holder.newsImage);
        holder.newsTitle.setText(item.getTitle());
        holder.newsHint.setText(item.getHint());

        holder.mView.setOnClickListener((View v)->{
            Intent intent=new Intent(holder.mView.getContext(), ContentActivity.class);
            Gson gson=new Gson();
            intent.putExtra("Bean",gson.toJson(item));
            intent.putExtra("position",position);
            intent.putExtra("isImage",false);
            holder.mView.getContext().startActivity(intent);
        });
    }



    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

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

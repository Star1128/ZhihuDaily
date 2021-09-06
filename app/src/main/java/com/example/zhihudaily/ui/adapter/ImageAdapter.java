package com.example.zhihudaily.ui.adapter;

import android.content.Intent;
import android.net.Uri;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.zhihudaily.Bean.latest.CarouselItem;
import com.example.zhihudaily.R;
import com.example.zhihudaily.ui.activity.ContentActivity;
import com.google.gson.Gson;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

/**
 * NOTE:
 *
 * @author wxc 2021/8/20
 * @version 1.0.0
 */
public class ImageAdapter extends BannerAdapter<CarouselItem, ImageAdapter.ViewHolder> {

    public ImageAdapter(List<CarouselItem> list) {
        super(list);
    }

    @Override
    public ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_image_title, parent, false));
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        holder.view.setLayoutParams(params);
        return holder;
    }

    @Override
    public void onBindView(ViewHolder holder, CarouselItem data, int position, int size) {
        Glide.with(holder.view.getContext()).load(Uri.parse(data.getImage().replaceAll("\\\\", ""))).into(holder.image);
        holder.image_title.setText(data.getTitle());
        holder.image_author.setText(data.getHint());

        holder.view.setOnClickListener((View v) -> {
            Intent intent = new Intent(holder.view.getContext(), ContentActivity.class);
            Gson gson = new Gson();
            intent.putExtra("Bean", gson.toJson(data));
            intent.putExtra("position", position);
            intent.putExtra("isImage", true);
            holder.view.getContext().startActivity(intent);
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        ImageView image;
        TextView image_title;
        TextView image_author;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            this.image = view.findViewById(R.id.bigImage);
            image_title = view.findViewById(R.id.image_title);
            image_author = view.findViewById(R.id.image_author);
        }
    }
}

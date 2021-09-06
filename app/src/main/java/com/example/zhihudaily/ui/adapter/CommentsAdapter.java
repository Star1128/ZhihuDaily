package com.example.zhihudaily.ui.adapter;

import android.net.Uri;
import android.view.*;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.zhihudaily.R;
import com.example.zhihudaily.ui.activity.CommentActivity;
import com.example.zhihudaily.Bean.comments.Comments;

import java.util.List;

/**
 * NOTE:
 *
 * @author wxc 2021/8/19
 * @version 1.0.0
 */
public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    private final List<Comments> mCommentList;

    public CommentsAdapter(List<Comments> list) {
        mCommentList = list;
    }

    @NonNull
    @Override
    public CommentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        CommentsAdapter.ViewHolder holder = new CommentsAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsAdapter.ViewHolder holder, int position) {
        Comments comments = mCommentList.get(position);
        //加载用户头像
        Glide.with(holder.view.getContext()).load(Uri.parse(comments.getAvatar().replaceAll("\\\\", ""))).into(holder.userHead);
        //加载名字，评论，赞数，拇指样式
        holder.userName.setText(comments.getAuthor());
        holder.comment_content.setText(comments.getContent());
        holder.comment_goodAmounts.setText(String.valueOf(comments.getLikes()));
        if (comments.getIsGood())
            holder.comment_good.setImageDrawable(ContextCompat.getDrawable(holder.view.getContext(), R.drawable.good_blue));
        else
            holder.comment_good.setImageDrawable(ContextCompat.getDrawable(holder.view.getContext(), R.drawable.good_black));

        //点赞响应
        holder.comment_good.setOnClickListener((View v) -> {
            if (!comments.getIsGood()) {
                int plus = comments.getLikes() + 1;
                holder.comment_goodAmounts.setText(String.valueOf(plus));//赞数加一
                holder.comment_good.setImageDrawable(ContextCompat.getDrawable(holder.view.getContext(), R.drawable.good_blue));
                comments.setIsGood(true);
            } else {
                int minus = comments.getLikes() - 1;
                holder.comment_goodAmounts.setText(String.valueOf(minus));//赞数减一
                holder.comment_good.setImageDrawable(ContextCompat.getDrawable(holder.view.getContext(), R.drawable.good_black));
                comments.setIsGood(false);
            }
            //在全部评论里替换
            mCommentList.set(position, comments);
            CommentActivity.mAllComments.set(position, comments);
        });
    }

    @Override
    public int getItemCount() {
        return mCommentList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView userHead;
        private final TextView userName;
        private final TextView comment_content;
        private final TextView comment_goodAmounts;
        private final ImageButton comment_good;
        private final View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            userHead = itemView.findViewById(R.id.userHead);
            userName = itemView.findViewById(R.id.userName);
            comment_content = itemView.findViewById(R.id.comment_content);
            comment_good = itemView.findViewById(R.id.comment_good);
            comment_goodAmounts = itemView.findViewById(R.id.comment_good_amounts);
        }
    }
}

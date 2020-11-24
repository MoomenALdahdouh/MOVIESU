package com.moomen.movieyou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moomen.movieyou.R;
import com.moomen.movieyou.model.firebase.CommentsModel;

import java.util.ArrayList;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {
    public static final String CREDIT_ID = "CREDIT_ID";
    private Context context;
    private ArrayList<CommentsModel> commentsModelArrayList;
    private View view;

    public CommentsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CommentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        view = layoutInflater.inflate(R.layout.comment_item, parent, false);
        return new CommentsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsAdapter.ViewHolder holder, int position) {
        CommentsModel currentComments = commentsModelArrayList.get(position);
        //final ProgressBar progressBar = view.findViewById(R.id.progress_bar_credit_id);
        holder.userName.setText(currentComments.getUserName());
        holder.date.setText(currentComments.getDate());
        holder.comment.setText(currentComments.getComment());
    }

    @Override
    public int getItemCount() {
        return commentsModelArrayList.size();
    }

    public void setCommentsModelArrayList(ArrayList<CommentsModel> commentsModelArrayList) {
        this.commentsModelArrayList = commentsModelArrayList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView userName;
        TextView date;
        TextView comment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.textView_name_user_comment_id);
            date = itemView.findViewById(R.id.textView_date_comment_id);
            comment = itemView.findViewById(R.id.textView_user_comment_id);
        }
    }
}
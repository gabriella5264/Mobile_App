package com.example.imageviewer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imageviewer.model.Comment;

import network.CommentRepository;

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.ViewHolder>  {

    private Context context;

    public CommentListAdapter(Context context){
        super();
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return  CommentRepository.getInstance().commentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView comment_user;
        TextView comment_com;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            comment_com = itemView.findViewById(R.id.comment_comment);
            comment_user = itemView.findViewById(R.id.comment_username);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(context).inflate(R.layout.row_comment, parent, false);

        ViewHolder vh = new ViewHolder(row);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Comment cItem = CommentRepository.getInstance().commentList.get(position);
        holder.comment_user.setText((cItem.authorname));
        holder.comment_com.setText(cItem._content);

    }

}

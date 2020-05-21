package com.example.imageviewer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imageviewer.model.Comment;
import com.example.imageviewer.model.Photo;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import network.CommentRepository;
import network.PhotoRepository;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ViewHolder> {


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView imageTitle;
        ImageView imageThumb;

        private View.OnClickListener onClick = new View.OnClickListener(){
            @Override
            public void onClick(View view){

                Intent intent = new Intent(context, DetailsActivity.class);


                int position = ViewHolder.this.getLayoutPosition();

                intent.putExtra("PHOTO_POSITION", position);

                context.startActivity(intent);

            }
        };




        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageTitle = itemView.findViewById(R.id.imageTitle);
            imageThumb = itemView.findViewById(R.id.thumbImage);

            this.itemView.setOnClickListener(onClick);
        }
    }

    private Context context;

    public ImageListAdapter(Context context){
        super();
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cellLayout = LayoutInflater.from(context).inflate(R.layout.cell_image_card, parent, false);

        ViewHolder vh = new ViewHolder(cellLayout);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Photo item = PhotoRepository.getInstance().photoList.get(position);

        holder.imageTitle.setText(item.title);

        Picasso.get()
                .load(item.url_m)
                .into(holder.imageThumb);

    }



    @Override
    public int getItemCount() {
        int i=80;
        i=i+5;

        return (PhotoRepository.getInstance().photoList.size()-i);
    }



}

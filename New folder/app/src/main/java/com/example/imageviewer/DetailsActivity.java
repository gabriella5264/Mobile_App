package com.example.imageviewer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imageviewer.model.CommentResponse;
import com.example.imageviewer.model.Photo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.squareup.picasso.Picasso;

import network.CommentRepository;
import network.PhotoRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    TextView imageTitle;
    TextView description;
    TextView owners;
    ImageView imageThumb;
    ImageView ownerImg;
    Photo photo;
    private LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    FloatingActionButton shareButton;
    FloatingActionButton favouriteBtn;
    RecyclerView RvComment;
    private CommentListAdapter adapter;
    private CommentRepository repository;
    private EditText editText;


    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        int photoPosition = intent.getIntExtra("PHOTO_POSITION", 0);
        photo = PhotoRepository.getInstance().photoList.get(photoPosition);


        imageTitle = findViewById(R.id.imageTitle);
        imageThumb = findViewById(R.id.mainImage);

        description = findViewById(R.id.descriptiveText);

        owners = findViewById(R.id.ownerNameDetails);
        ownerImg = findViewById(R.id.ownerImgDetails);

        shareButton = findViewById((R.id.shareBtn));
        shareButton.setOnClickListener(shareListener);

        favouriteBtn = findViewById(R.id.favourite);
        favouriteBtn.setOnClickListener(addListener);

        description.setText(photo.description.content);
        imageTitle.setText(photo.title);
        owners.setText(photo.ownername);

        RvComment = findViewById(R.id.rv_comment);

        Picasso.get().load(photo.url_l).into(imageThumb);
        Picasso.get().load("https://flickr.com/buddyicons/"+photo.owner+".jpg").into(ownerImg);

        repository = CommentRepository.getInstance();
        recyclerView = (RecyclerView) findViewById(R.id.rv_comment);

        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);
        adapter = new CommentListAdapter(this);
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
        Call<CommentResponse> call = repository.getCommentResponse("8666443853");

        call.enqueue(commentsCallback);


    }

    private Callback<CommentResponse> commentsCallback = new Callback<CommentResponse>() {
        @Override
        public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
            CommentResponse resp = response.body();

            if (resp != null) {
                repository.commentList.addAll(resp.getCommentList());
                adapter.notifyDataSetChanged();
            } else {

            }
        }

        @Override
        public void onFailure(Call<CommentResponse> call, Throwable t) {
            ExampleDialog exampleDialog = new ExampleDialog();
            exampleDialog.show(getSupportFragmentManager(), "error dialog");
        }
    };



    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private View.OnClickListener shareListener = new  View.OnClickListener() {
        @Override
        public void onClick(View view) {Intent myIntent = new Intent(Intent.ACTION_SEND);
            myIntent.setType("text/plain");
            String shareBody = "Your body here";
            String shareSub = "Choose";
            myIntent.putExtra(Intent.EXTRA_SUBJECT, shareBody);
            myIntent.putExtra(Intent.EXTRA_TEXT, shareSub);
            startActivity(Intent.createChooser(myIntent, "share using"));
        }
    };

    int i = 0;
    private View.OnClickListener addListener = new  View.OnClickListener() {
        @Override
        public void onClick(View view) {Intent myIntent = new Intent(Intent.ACTION_SEND);
            switch (i)
            {
                case 0 :
                    favouriteBtn.setColorFilter(Color.RED);
                    i = 1;
                    Toast toast = Toast.makeText(getApplicationContext(), "Favorite item has been added", Toast.LENGTH_SHORT);
                    toast.show();
                    return;

                case 1 :
                    favouriteBtn.setColorFilter(Color.WHITE);
                    i=0;
                    Toast toast1 = Toast.makeText(getApplicationContext(), "Favorite item has been disabled", Toast.LENGTH_SHORT);
                    toast1.show();
                    return;

            }
        }
    };

}

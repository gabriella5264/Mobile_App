package network;

import com.example.imageviewer.model.Comment;
import com.example.imageviewer.model.CommentResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CommentRepository {

    private static CommentRepository instance;
    public static CommentRepository getInstance(){
        if(instance == null)
            instance = new CommentRepository();
        return instance;
    }

    private Retrofit retrofit;
    private FlickrService flickrService;

    public ArrayList<Comment> commentList = new ArrayList<>();

    public CommentRepository(){

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(FlickrService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        flickrService = retrofit.create(FlickrService.class);
    }

    public Call<CommentResponse> getCommentResponse(String photo_id){
        return flickrService.getCommentResponse(photo_id);
    }
}

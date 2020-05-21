package network;

import com.example.imageviewer.model.Photo;
import com.example.imageviewer.model.PhotosResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PhotoRepository {

    private static PhotoRepository instance;
    public static PhotoRepository getInstance(){
        if(instance == null)
            instance = new PhotoRepository();
        return instance;
    }

    private Retrofit retrofit;
    private FlickrService flickrService;

    public ArrayList<Photo> photoList = new ArrayList<>();

    public PhotoRepository(){

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(FlickrService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        flickrService = retrofit.create(FlickrService.class);
    }

    public Call<PhotosResponse> getInterestingPhotos(int page){
        return flickrService.getInterestingPhotos(page);
    }

}

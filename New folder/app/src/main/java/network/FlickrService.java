package network;

import com.example.imageviewer.model.CommentResponse;
import com.example.imageviewer.model.PhotosResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlickrService {
    static final String BASE_URL = "https://api.flickr.com/services/rest/";

    static final String API_KEY = "&api_key=bf7b0221d18039a5f67dcca42e28d4ee";
    static final String JSON_PARAMS = "&format=json&nojsoncallback=?";
    static final String PHOTO_LIST_PARAMS = "&extras=description,owner_name,url_m,url_l,url_o,date_taken";
    static final String GET_INTERESTING_PHOTOS = "?method=flickr.interestingness.getList" + API_KEY + JSON_PARAMS + PHOTO_LIST_PARAMS;
    static final String GET_COMMENT_RESPONSE = "?method=flickr.photos.comments.getList"+ API_KEY + JSON_PARAMS ;

    @GET(GET_COMMENT_RESPONSE)
    Call<CommentResponse> getCommentResponse(@Query("photo_id") String photo_id);

    @GET(GET_INTERESTING_PHOTOS)
    Call<PhotosResponse> getInterestingPhotos(@Query("page") Integer integer);
}

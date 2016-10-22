package io.github.syzygy2048.net;


import java.util.List;

import io.github.syzygy2048.model.Post;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Syzygy on 12.07.16.
 */
public interface PostService {
    @GET ("/posts")
    Call<List<Post>> getPosts();


}

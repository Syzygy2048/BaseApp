package io.github.syzygy2048;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.syzygy2048.events.ClickedEvent;
import io.github.syzygy2048.model.Post;
import io.github.syzygy2048.net.PostService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Inject
    @Named("daggerTest")
    String daggerWorking;

    @Inject
    PostAdapter postAdapter;

    @Inject
    PostService postService;

    @BindView(R.id.resultText)
    TextView resultText;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Inject
    Bus bus;
    @OnClick(R.id.requestButton)
    public void onClickRequest(){
        Call<List<Post>> request = postService.getPosts();
        request.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                postAdapter.updatePostData(response.body());
                bus.post(new ClickedEvent("event published"));
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                bus.post(new ClickedEvent("FAILURE"));
                Log.w(TAG, t);
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        ((BaseApplication)getApplication()).getAppComponent().inject(this);

        bus.register(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(postAdapter);
     }

    /**
     * Could make sense as an interface or class that is extended, but not necessary right now
     * And yes I realize that for this example I could just post a string.
     */
    @Subscribe
    public void eventReceived(ClickedEvent event){
        Log.e("MainActivity", "otto received event");
        Toast.makeText(this, "click event - " + event.getEventMessage(), Toast.LENGTH_LONG).show();
    }

    @Subscribe
    public void eventReceived(String event){
        Toast.makeText(this, "string event - " + event, Toast.LENGTH_LONG).show();
    }
}

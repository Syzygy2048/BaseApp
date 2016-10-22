package io.github.syzygy2048;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.io.IOException;
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
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

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
        new AsyncTask<Void, Void, Response<List<Post>>>(){
            @Override
            protected Response<List<Post>> doInBackground(Void... voids) {


                try {
                    Call<List<Post>> request = postService.getPosts();
                    Response<List<Post>> response = null;
                    response = request.execute();
                    return response;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Response<List<Post>> listResponse) {
                postAdapter.updatePostData(listResponse.body());
                bus.post(new ClickedEvent("event published"));
            }
        }.execute();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        ((BaseApplication)getApplication()).getAppComponent().inject(this);

        bus.register(this);

        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(postAdapter);
     }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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

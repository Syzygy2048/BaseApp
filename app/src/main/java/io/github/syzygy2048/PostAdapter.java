package io.github.syzygy2048;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.otto.Bus;
import com.squareup.otto.Produce;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.syzygy2048.model.Post;

/**
 * Created by Syzygy on 12.07.16.
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    List<Post> data;

    @Inject
    Bus bus;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.userId)
        TextView userId;
        @BindView(R.id.postId)
        TextView postId;
        @BindView(R.id.postHeader)
        TextView title;
        @BindView(R.id.postBody)
        TextView body;

        public ViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Inject
    public PostAdapter(Context context){
        ((BaseApplication)context.getApplicationContext()).getAppComponent().inject(this);
        bus.register(this);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_listitem, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post post = data.get(position);
        holder.userId.setText(String.valueOf(post.getUserId()));
        holder.postId.setText(String.valueOf(post.getId()));
        holder.title.setText(post.getTitle());
        holder.body.setText(post.getBody());
    }

    @Override
    public int getItemCount() {
        if(data == null)
            return 0;
        return data.size();
    }

    public void updatePostData(List<Post> data){
        this.data = data;
        notifyDataSetChanged();
    }

    @Produce
    public String produceEvent() {
        return "Post Adapter started up";
    }
}

package io.github.syzygy2048.d_injection.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.github.syzygy2048.PostAdapter;

/**
 * Created by Syzygy on 12.07.16.
 */
@Module
public class LazyModule {


    private final Context context;
    public LazyModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    PostAdapter providesPostAdapter(){
        return new PostAdapter(context);
    }
}

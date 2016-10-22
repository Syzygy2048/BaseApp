package io.github.syzygy2048.d_injection.module;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.github.syzygy2048.BaseApplication;
import io.github.syzygy2048.net.PostService;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Syzygy on 12.07.16.
 */
@Module
public class NetModule {
    String baseUrl;
    BaseApplication app;

    public NetModule(BaseApplication app, String baseUrl) {
        this.baseUrl = baseUrl;
        this.app = app;
    }

    @Provides
    @Singleton
    Cache provideOkHttpCache() {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(app.getCacheDir(), cacheSize);
        return cache;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Cache cache) {
        OkHttpClient client = new OkHttpClient.Builder().cache(cache).build();
        return client;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    PostService providePostService(Retrofit retrofit){
        return retrofit.create(PostService.class);
    }
}

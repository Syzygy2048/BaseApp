package io.github.syzygy2048.d_injection.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.squareup.otto.Bus;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.github.syzygy2048.BaseApplication;

/**
 * Created by Syzygy on 07.07.16.
 */

@Module
public class AppModule {
    private final BaseApplication app;

    private final Bus bus;

    public AppModule(BaseApplication app){
        this.app = app;
        bus = new Bus();
    }

    @Provides
    @Singleton
    Context providesApplicationContext(){
        return app;
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(){
        return PreferenceManager.getDefaultSharedPreferences(app);
    }

    @Provides
    @Singleton
    Bus providesBus(){
        return bus;
    }

    @Provides
    @Named("daggerTest")
    String providesString(){
        return "dagger working";
    }
}

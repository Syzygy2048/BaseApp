package io.github.syzygy2048;

import android.app.Application;

import io.github.syzygy2048.d_injection.component.AppComponent;
import io.github.syzygy2048.d_injection.component.DaggerAppComponent;
import io.github.syzygy2048.d_injection.module.AppModule;
import io.github.syzygy2048.d_injection.module.LazyModule;
import io.github.syzygy2048.d_injection.module.NetModule;


/**
 * Created by Syzygy on 07.07.16.
 */
public class BaseApplication extends Application{
    public static final String BASE_URL = "http://jsonplaceholder.typicode.com/";
    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        initDagger();

    }

    private void initDagger(){
        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(this, BASE_URL))
                .lazyModule(new LazyModule(this))
                .build();

        component.inject(this);



    }

    public AppComponent getAppComponent(){
        return component;
    }
}

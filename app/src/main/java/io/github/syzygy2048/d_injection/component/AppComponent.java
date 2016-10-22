package io.github.syzygy2048.d_injection.component;

import javax.inject.Singleton;

import dagger.Component;
import io.github.syzygy2048.BaseApplication;
import io.github.syzygy2048.MainActivity;
import io.github.syzygy2048.PostAdapter;
import io.github.syzygy2048.d_injection.module.AppModule;
import io.github.syzygy2048.d_injection.module.LazyModule;
import io.github.syzygy2048.d_injection.module.NetModule;

/**
 * Created by Syzygy on 07.07.16.
 */

@Singleton
@Component(modules = {AppModule.class, NetModule.class, LazyModule.class})
public interface AppComponent {

    void inject(BaseApplication app);
    void inject(MainActivity mainActivity);
    void inject(PostAdapter postAdapter);
}

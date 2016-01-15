package com.tolsma.ryan.airlinecheckin;

import android.app.Application;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.tolsma.ryan.airlinecheckin.components.ActivityComponent;
import com.tolsma.ryan.airlinecheckin.components.AppComponent;
import com.tolsma.ryan.airlinecheckin.components.DaggerActivityComponent;
import com.tolsma.ryan.airlinecheckin.components.DaggerAppComponent;
import com.tolsma.ryan.airlinecheckin.model.events.NotificationEvent;
import com.tolsma.ryan.airlinecheckin.modules.ActivityModule;
import com.tolsma.ryan.airlinecheckin.modules.AppModule;
import com.tolsma.ryan.airlinecheckin.modules.LoginModule;


/**
 * Created by ryantolsma on 12/30/15.
 */
public class CleanupApplication extends Application {

    private static AppComponent appComponent;
    private static ActivityComponent activityComponent;
    private Bus eventBus;

    public static AppComponent getAppComponent() {
        return appComponent;
    }


    public static ActivityComponent getActivityComponent() {
        return activityComponent;
    }

    public static void setActivityComponents(MainActivity ma) {

        activityComponent = DaggerActivityComponent.builder().appComponent(appComponent)
                .activityModule(new ActivityModule(ma)).build();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);

        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).
                loginModule(new LoginModule()).build();
        eventBus = appComponent.eventBus();
        eventBus.register(this);
    }


    @Subscribe
    public void deliverNotification(NotificationEvent ne) {
        NotificationManagerCompat manager = appComponent.notificationManager();
        NotificationCompat.Builder builder = appComponent.notificationBuilder();
        builder.setContentTitle(ne.getTitle())
                .setStyle(new NotificationCompat.BigTextStyle().bigText(ne.getMessage()))
                .setContentIntent(ne.getIntent());
        manager.notify(ne.getId(), builder.build());
    }


}

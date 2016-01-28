package com.tolsma.ryan.airlinecheckin.services;

import android.app.Application;

import com.squareup.otto.Bus;
import com.tolsma.ryan.airlinecheckin.MainActivity;
import com.tolsma.ryan.airlinecheckin.components.ActivityComponent;
import com.tolsma.ryan.airlinecheckin.components.AppComponent;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by ryan on 1/26/16.
 */
public class ServiceTestApplication extends Application {
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

        // activityComponent = DaggerActivityComponent.builder().appComponent(appComponent)
        //       .activityModule(new ActivityModule(ma)).build();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = mock(AppComponent.class);
        eventBus = new Bus();
        eventBus.register(this);
        when(appComponent.eventBus()).thenReturn(eventBus);
        when(appComponent.context()).thenReturn(this);


    }

}

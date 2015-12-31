package com.tolsma.ryan.airlinecheckin;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.tolsma.ryan.airlinecheckin.components.AppComponent;
import com.tolsma.ryan.airlinecheckin.components.DaggerAppComponent;
import com.tolsma.ryan.airlinecheckin.components.DaggerLoginComponent;
import com.tolsma.ryan.airlinecheckin.components.LoginComponent;
import com.tolsma.ryan.airlinecheckin.modules.AppModule;
import com.tolsma.ryan.airlinecheckin.modules.LoginModule;


/**
 * Created by ryantolsma on 12/30/15.
 */
public class CleanupApplication extends Application {

    private static AppComponent appComponent;
    private static LoginComponent loginComponent;

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    public static LoginComponent getLoginComponent() {
        return loginComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);


        appComponent = DaggerAppComponent.builder().appModule(new AppModule(new MainActivity())).build();
        loginComponent = DaggerLoginComponent.builder().appComponent(appComponent).loginModule(new LoginModule()).build();
    }


}

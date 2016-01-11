package com.tolsma.ryan.airlinecheckin.components;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.tolsma.ryan.airlinecheckin.MainActivity;
import com.tolsma.ryan.airlinecheckin.adapters.LoginListAdapter;
import com.tolsma.ryan.airlinecheckin.model.SouthwestLogins;
import com.tolsma.ryan.airlinecheckin.modules.AppModule;
import com.tolsma.ryan.airlinecheckin.modules.LoginModule;
import com.tolsma.ryan.airlinecheckin.services.LoginAlarmService;
import com.tolsma.ryan.airlinecheckin.services.retrofit.SouthwestAPI;
import com.tolsma.ryan.airlinecheckin.ui.LoginDialogFragment;
import com.tolsma.ryan.airlinecheckin.ui.LoginListFragment;

import javax.inject.Singleton;

import dagger.Component;
import io.realm.Realm;

/**
 * Created by ryantolsma on 12/29/15.
 */


@Singleton
@Component(modules = {AppModule.class, LoginModule.class})
public interface AppComponent {

    void inject(MainActivity mainActivity);

    void inject(LoginListFragment llf);

    void inject(LoginDialogFragment ldf);

    void inject(LoginListAdapter lla);

    void inject(LoginAlarmService las);

    void inject(SouthwestLogins swls);

    SouthwestLogins swLogins();

    Context context();

    Realm realm();

    SharedPreferences sharedPreference();

    SouthwestAPI southwestAPI();

    NotificationManagerCompat notificationManager();

    NotificationCompat.Builder notificationBuilder();
}

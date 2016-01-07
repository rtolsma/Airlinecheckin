package com.tolsma.ryan.airlinecheckin.components;


import android.app.AlarmManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.LayoutInflater;

import com.tolsma.ryan.airlinecheckin.MainActivity;
import com.tolsma.ryan.airlinecheckin.adapters.LoginListAdapter;
import com.tolsma.ryan.airlinecheckin.model.Logins;
import com.tolsma.ryan.airlinecheckin.modules.AppModule;
import com.tolsma.ryan.airlinecheckin.services.retrofit.SouthwestAPI;
import com.tolsma.ryan.airlinecheckin.ui.LoginDialogFragment;

import javax.inject.Singleton;

import dagger.Component;
import io.realm.Realm;

/**
 * Created by ryantolsma on 12/29/15.
 */


@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(Logins logins);

    void inject(MainActivity mainActivity);

    // void inject(LoginListFragment llf);

    void inject(LoginDialogFragment ldf);

    void inject(LoginListAdapter lla);

    Context context();

    Realm realm();

    SharedPreferences sharedPreference();

    LayoutInflater layoutInflater();

    MainActivity mainActivity();

    FragmentManager fragmentManager();

    FragmentTransaction fragmentTransaction();

    AlarmManager alarmManager();

    SouthwestAPI southwestAPI();

    NotificationManagerCompat notificationManager();

    NotificationCompat.Builder notificationBuilder();
}

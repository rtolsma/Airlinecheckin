package com.tolsma.ryan.airlinecheckin.components;


import android.content.Context;
import android.content.SharedPreferences;

import com.tolsma.ryan.airlinecheckin.modules.AppModule;

import javax.inject.Singleton;

import dagger.Component;
import io.realm.Realm;

/**
 * Created by ryantolsma on 12/29/15.
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    Context context();

    Realm realm();

    SharedPreferences sharedPreference();

}

package com.tolsma.ryan.airlinecheckin.modules;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

/**
 * Created by ryantolsma on 12/29/15.
 */
@Module
public class AppModule {
    Activity mActivity;

    public AppModule(Activity activity) {
        this.mActivity = activity;
    }

    @Provides
    @Singleton
    public Realm provideRealmInstance(Context ctx) {
        return Realm.getInstance(ctx);
    }

    @Provides
    @Singleton
    public Context provideActivityContext() {
        return this.mActivity;
    }

    @Provides
    @Singleton
    public SharedPreferences provideSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }
}

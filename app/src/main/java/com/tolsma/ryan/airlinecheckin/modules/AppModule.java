package com.tolsma.ryan.airlinecheckin.modules;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

/**
 * Created by ryantolsma on 12/29/15.
 */
@Module
public class AppModule {

    Context ctx;


    public AppModule(Context ctx) {
        this.ctx = ctx;
    }

    @Provides
    @Singleton
    public Realm provideRealmInstance(Context ctx) {
        return Realm.getInstance(ctx);
    }

    @Provides
    @Singleton
    public Context provideApplicationContext() {
        return this.ctx;
    }

    @Provides
    @Singleton
    public SharedPreferences provideSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    @Provides
    @Singleton
    public LayoutInflater provideLayoutInflater(Context ctx) {
        return (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Scope
    public @interface PerApp {
    }
}

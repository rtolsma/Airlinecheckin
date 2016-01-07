package com.tolsma.ryan.airlinecheckin.modules;

import android.app.AlarmManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.LayoutInflater;

import com.tolsma.ryan.airlinecheckin.MainActivity;
import com.tolsma.ryan.airlinecheckin.services.retrofit.SouthwestAPI;
import com.tolsma.ryan.airlinecheckin.utils.ConstantsConfig;
import com.tolsma.ryan.airlinecheckin.utils.RetrofitUtils;

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
    MainActivity ma;


    public AppModule(MainActivity mainActivity) {
        ma = mainActivity;
        ctx = ma.getApplication();
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

    @Provides
    @Singleton
    public MainActivity provideMainActivity() {
        return this.ma;
    }

    @Provides
    @Singleton
    public FragmentManager provideFragmentManager(Context ctx) {
        return
                ma.getSupportFragmentManager();
    }

    @Provides
    @Singleton
    public FragmentTransaction provideFragmentTransaction(FragmentManager fm) {
        return fm.beginTransaction();
    }

    @Provides
    @Singleton
    public AlarmManager provideAlarmManager(Context ctx) {
        return (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
    }

    @Provides
    @Singleton
    public SouthwestAPI provideSouthwestAPI() {
        return RetrofitUtils.createRetrofitService(SouthwestAPI.class, ConstantsConfig.SOUTHWEST_API);
    }

    @Provides
    @Singleton
    public NotificationManagerCompat provideNotificationMangager(Context ctx) {
        return (NotificationManagerCompat.from(ctx));
    }

    @Provides
    public NotificationCompat.Builder provideNotificationBuilder(Context ctx) {

        return new NotificationCompat.Builder(ctx)
                //TODO .setSmallIcon()
                ;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Scope
    public @interface PerApp {
    }
}

package com.tolsma.ryan.airlinecheckin.modules;

import android.app.AlarmManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;
import com.tolsma.ryan.airlinecheckin.R;
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
    Bus eventBus;


    public AppModule(Context context) {
        eventBus = new Bus(ThreadEnforcer.ANY);
        ctx = context;
    }


    @Provides
    @Singleton
    public Realm provideRealmInstance(Context ctx) {
        return Realm.getInstance(ctx);
    }

    @Provides
    @Singleton
    public Context provideApplicationContext() {
        return this.ctx.getApplicationContext();
    }


    @Provides
    @Singleton
    public SharedPreferences provideSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
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
                .setSmallIcon(R.mipmap.ic_launcher)
                ;
    }

    @Provides
    @Singleton
    public Bus provideEventBus() {
        return this.eventBus;
    }




    @Retention(RetentionPolicy.RUNTIME)
    @Scope
    public @interface PerApp {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Scope
    public @interface PerActivity {
    }

}

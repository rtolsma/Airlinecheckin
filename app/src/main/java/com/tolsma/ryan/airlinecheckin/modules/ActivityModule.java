package com.tolsma.ryan.airlinecheckin.modules;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;

import com.tolsma.ryan.airlinecheckin.MainActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ryan on 1/9/16.
 */
@Module
public class ActivityModule {
    MainActivity ma;

    public ActivityModule(MainActivity ma) {
        this.ma = ma;
    }


    @Provides
    @AppModule.PerActivity
    public LayoutInflater provideLayoutInflater() {
        return (LayoutInflater) ma.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Provides
    @AppModule.PerActivity
    public MainActivity provideMainActivity() {
        return this.ma;
    }

    @Provides
    @AppModule.PerActivity
    public FragmentManager provideFragmentManager() {
        return
                ma.getSupportFragmentManager();
    }

    /*   @Provides
       //@Singleton
       public Context provideActivityContext() {
           return ma;
       }
       */
    @Provides
    @AppModule.PerActivity
    public FragmentTransaction provideFragmentTransaction(FragmentManager fm) {
        return fm.beginTransaction();
    }

}

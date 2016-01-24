package com.tolsma.ryan.airlinecheckin.modules;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
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
        if (!ma.isAlive()) return (ma = null);

        return this.ma;
    }

    @Provides
    @AppModule.PerActivity
    public FragmentManager provideFragmentManager() {
        return
                ma.getFragmentManager();
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

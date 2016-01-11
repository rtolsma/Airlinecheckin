package com.tolsma.ryan.airlinecheckin.components;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;

import com.tolsma.ryan.airlinecheckin.MainActivity;
import com.tolsma.ryan.airlinecheckin.adapters.LoginListAdapter;
import com.tolsma.ryan.airlinecheckin.modules.ActivityModule;
import com.tolsma.ryan.airlinecheckin.modules.AppModule;
import com.tolsma.ryan.airlinecheckin.ui.LoginDialogFragment;
import com.tolsma.ryan.airlinecheckin.ui.LoginListFragment;

import dagger.Component;

/**
 * Created by ryan on 1/10/16.
 */
@AppModule.PerActivity
@Component(modules = {ActivityModule.class}, dependencies = {AppComponent.class})
public interface ActivityComponent {

    void inject(LoginListAdapter lla);

    void inject(MainActivity mainActivity);

    void inject(LoginDialogFragment ldf);

    void inject(LoginListFragment llf);

    LayoutInflater layoutInflater();

    FragmentManager fragmentManager();

    FragmentTransaction fragmentTransaction();

    MainActivity mainActivity();


}

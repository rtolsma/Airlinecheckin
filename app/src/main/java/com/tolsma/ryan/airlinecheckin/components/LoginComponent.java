package com.tolsma.ryan.airlinecheckin.components;

import com.tolsma.ryan.airlinecheckin.MainActivity;
import com.tolsma.ryan.airlinecheckin.model.Logins;
import com.tolsma.ryan.airlinecheckin.model.SouthwestLogins;
import com.tolsma.ryan.airlinecheckin.modules.AppModule;
import com.tolsma.ryan.airlinecheckin.modules.LoginModule;
import com.tolsma.ryan.airlinecheckin.services.LoginAlarmService;
import com.tolsma.ryan.airlinecheckin.ui.LoginDialogFragment;
import com.tolsma.ryan.airlinecheckin.ui.LoginListFragment;

import dagger.Component;

/**
 * Created by ryantolsma on 12/29/15.
 */

@AppModule.PerApp
@Component(dependencies = {AppComponent.class}, modules = {LoginModule.class})
public interface LoginComponent {
    void inject(LoginListFragment llf);
    void inject(LoginDialogFragment ldf);

    void inject(LoginAlarmService las);

    void inject(MainActivity ma);
    Logins logins();
    SouthwestLogins swLogins();
}


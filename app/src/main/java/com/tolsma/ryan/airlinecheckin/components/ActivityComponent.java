package com.tolsma.ryan.airlinecheckin.components;

import com.tolsma.ryan.airlinecheckin.model.Logins;
import com.tolsma.ryan.airlinecheckin.model.realmobjects.LoginEvent;
import com.tolsma.ryan.airlinecheckin.modules.LoginModule;

import dagger.Component;

/**
 * Created by ryantolsma on 12/29/15.
 */
@Component(dependencies = {AppComponent.class}, modules = {LoginModule.class})
public interface ActivityComponent {
    LoginEvent login();

    Logins logins();
}

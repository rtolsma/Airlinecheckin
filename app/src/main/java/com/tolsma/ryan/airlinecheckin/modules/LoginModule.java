package com.tolsma.ryan.airlinecheckin.modules;

import android.content.Context;

import com.tolsma.ryan.airlinecheckin.model.Logins;
import com.tolsma.ryan.airlinecheckin.model.SouthwestLogins;
import com.tolsma.ryan.airlinecheckin.model.realmobjects.SouthwestLoginEvent;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ryantolsma on 12/29/15.
 */
@Module
public class LoginModule {

    public LoginModule() {
    }


    @Provides
    Logins provideSouthwestLogins(Context ctx) {
        return SouthwestLogins.createFromRealm(ctx, SouthwestLoginEvent.class);
    }


}

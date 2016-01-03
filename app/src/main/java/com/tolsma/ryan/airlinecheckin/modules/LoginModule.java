package com.tolsma.ryan.airlinecheckin.modules;

import android.content.Context;

import com.tolsma.ryan.airlinecheckin.model.Logins;
import com.tolsma.ryan.airlinecheckin.model.SouthwestLogins;
import com.tolsma.ryan.airlinecheckin.model.realmobjects.SouthwestLoginEvent;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ryantolsma on 12/29/15.
 */
@Module
public class LoginModule {
    private static SouthwestLogins logins;
    public LoginModule() {
    }


    @Provides
    Logins provideLogins(Context ctx) {
        return SouthwestLogins.createFromRealm(ctx, SouthwestLoginEvent.class);
    }
    @Provides
    SouthwestLogins provideSouthwestLogins(Context ctx) {

        if(logins==null) {
           logins= SouthwestLogins.createFromRealm(ctx, SouthwestLoginEvent.class);
        }
        return logins;

    }

}

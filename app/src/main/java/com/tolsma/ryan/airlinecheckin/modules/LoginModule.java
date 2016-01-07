package com.tolsma.ryan.airlinecheckin.modules;

import android.content.Context;

import com.tolsma.ryan.airlinecheckin.model.Logins;
import com.tolsma.ryan.airlinecheckin.model.SouthwestLogins;
import com.tolsma.ryan.airlinecheckin.model.realmobjects.SouthwestLoginEvent;

import dagger.Module;
import dagger.Provides;
import hugo.weaving.DebugLog;

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

    @DebugLog
    @Provides
    SouthwestLogins provideSouthwestLogins(Context ctx) {

        if(logins==null) {
           logins= SouthwestLogins.createFromRealm(ctx, SouthwestLoginEvent.class);
        }

        return logins;

    }

}

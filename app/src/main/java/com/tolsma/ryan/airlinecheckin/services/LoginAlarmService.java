package com.tolsma.ryan.airlinecheckin.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import com.tolsma.ryan.airlinecheckin.model.SouthwestLogin;
import com.tolsma.ryan.airlinecheckin.model.realmobjects.SouthwestLoginEvent;
import com.tolsma.ryan.airlinecheckin.services.retrofit.SouthwestAPI;
import com.tolsma.ryan.airlinecheckin.utils.ConstantsConfig;
import com.tolsma.ryan.airlinecheckin.utils.RetrofitUtils;

import hugo.weaving.DebugLog;
import io.realm.Realm;

public class LoginAlarmService extends Service {

    public static final String southwestConfirmationCode = "confirmationCode";
    Realm realm;
    SouthwestAPI api;
    NotificationManagerCompat notificationManager;

    public LoginAlarmService() {

    }

    public void init() {
        //  CleanupApplication.getLoginComponent().inject(this);

    }

    @DebugLog
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        init();
        realm = Realm.getInstance(this);
        api = RetrofitUtils.createRetrofitService(SouthwestAPI.class, ConstantsConfig.SOUTHWEST_API);
        notificationManager = NotificationManagerCompat.from(this);
        SouthwestLogin southwestLogin = new SouthwestLogin(realm.where(SouthwestLoginEvent.class)
                .equalTo(southwestConfirmationCode,
                        intent.getStringExtra(ConstantsConfig.LOGIN_INTENT_ID)).findAllAsync().get(0));

        //  for( SouthwestLoginEvent sle:   realm.where(SouthwestLoginEvent.class).findAll())
        //         Log.d(getClass().getSimpleName(),"RealmRetrieval Confirmation code: "+ sle.getConfirmationCode());
        //Gets 0th element due, to there being a primary key
        Toast.makeText(this, "Started service from login with cc: " + southwestLogin.getConfirmationCode(),
                Toast.LENGTH_LONG).show();


        return 0;
    }



    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

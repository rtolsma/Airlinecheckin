package com.tolsma.ryan.airlinecheckin.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import com.tolsma.ryan.airlinecheckin.CleanupApplication;
import com.tolsma.ryan.airlinecheckin.MainActivity;
import com.tolsma.ryan.airlinecheckin.model.logins.SouthwestLogin;
import com.tolsma.ryan.airlinecheckin.model.realmobjects.SouthwestLoginEvent;
import com.tolsma.ryan.airlinecheckin.services.requests.SouthwestValidityRequest;
import com.tolsma.ryan.airlinecheckin.services.retrofit.SouthwestAPI;
import com.tolsma.ryan.airlinecheckin.utils.ConstantsConfig;
import com.tolsma.ryan.airlinecheckin.utils.RetrofitUtils;

import javax.inject.Inject;

import hugo.weaving.DebugLog;
import io.realm.Realm;

public class LoginAlarmService extends IntentService {

    //@Inject
    Realm realm;
    SouthwestAPI api;
    @Inject
    NotificationManagerCompat notificationManager;
    SouthwestLogin southwestLogin;
    MainActivity ma;

    public LoginAlarmService(String name) {
        super(name);
    }
    public LoginAlarmService() {
        super(LoginAlarmService.class.getSimpleName());
    }

    public void init() {
        //  CleanupApplication.getLoginComponent().inject(this);

    }

    @DebugLog
    @Override
    public void onHandleIntent(Intent intent) {
        init();
        CleanupApplication.getAppComponent().inject(this);
        realm = Realm.getInstance(this);
        // ma = CleanupApplication.getActivityComponent().mainActivity();
        api = RetrofitUtils.createRetrofitService(SouthwestAPI.class, ConstantsConfig.SOUTHWEST_API);
        //Get the corresponding request from Realm, and deletes it from storage
        southwestLogin = new SouthwestLogin(realm.where(SouthwestLoginEvent.class)
                .equalTo(ConstantsConfig.SOUTHWEST_CONFIRMATION_CODE,
                        intent.getStringExtra(ConstantsConfig.LOGIN_INTENT_ID)).findAll().get(0));


        //DEBUGGING, test if Realm retrieval is working, workaround for Toast
        sendToast("Starting IntentService with confirmation code: "
                + southwestLogin.getConfirmationCode(), Toast.LENGTH_SHORT);

        for (int i = 0; i < 5; i++) {
        //TODO change ! for debugging
        if (!SouthwestValidityRequest.isLoginValid(southwestLogin)) {
            //The login is valid
            //Can't use dagger because it may not be instantiated with MainActivity setActivityComponents
            SouthwestAPI api = RetrofitUtils.createRetrofitService(SouthwestAPI.class, ConstantsConfig.SOUTHWEST_API);

        }

        }


    }



    public void sendToast(String message, int length) {

        new Handler(getMainLooper()).post(() ->
                Toast.makeText(this, message, length).show());

    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

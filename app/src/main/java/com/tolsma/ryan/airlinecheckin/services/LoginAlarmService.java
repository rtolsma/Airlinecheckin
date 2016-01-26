package com.tolsma.ryan.airlinecheckin.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import com.squareup.otto.Bus;
import com.tolsma.ryan.airlinecheckin.CleanupApplication;
import com.tolsma.ryan.airlinecheckin.MainActivity;
import com.tolsma.ryan.airlinecheckin.R;
import com.tolsma.ryan.airlinecheckin.model.events.ToastEvent;
import com.tolsma.ryan.airlinecheckin.model.events.UpdateLoginListEvent;
import com.tolsma.ryan.airlinecheckin.model.logins.SouthwestLogin;
import com.tolsma.ryan.airlinecheckin.model.logins.SouthwestLogins;
import com.tolsma.ryan.airlinecheckin.model.realmobjects.SouthwestLoginEvent;
import com.tolsma.ryan.airlinecheckin.services.requests.SouthwestCheckinRequest;
import com.tolsma.ryan.airlinecheckin.services.requests.SouthwestDeliveryRequest;
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
    @Inject
    Bus eventBus;
    @Inject
    SharedPreferences prefs;

    public LoginAlarmService(String name) {
        super(name);
    }
    public LoginAlarmService() {
        super(LoginAlarmService.class.getSimpleName());
    }



    @DebugLog
    @Override
    public void onHandleIntent(Intent intent) {
        CleanupApplication.getAppComponent().inject(this);
        realm = Realm.getInstance(this);
        api = RetrofitUtils.createRetrofitService(SouthwestAPI.class, ConstantsConfig.SOUTHWEST_API);
        //Get the corresponding request from Realm, and deletes it from storage
        southwestLogin = new SouthwestLogin(realm.where(SouthwestLoginEvent.class)
                .equalTo(ConstantsConfig.SOUTHWEST_CONFIRMATION_CODE,
                        intent.getStringExtra(ConstantsConfig.LOGIN_INTENT_ID)).findAll().get(0));

        /*
        Some code to decipher what data is needed to be sent in the request
         */
        boolean emailDelivered = false, textDelivered = false;
        String emailAddress, textNumber;
        textNumber = prefs.getString(getString(R.string.text_number_preference), null);
        emailAddress = prefs.getString(getString(R.string.email_address_preference), null);


        //DEBUGGING, test if Realm retrieval is working, workaround for Toast
        eventBus.post(new ToastEvent("Starting IntentService with confirmation code: "
                + southwestLogin.getConfirmationCode(), Toast.LENGTH_SHORT));
        //try a couple of times
        for (int i = 0; i < 2; i++) {
            if (SouthwestValidityRequest.isLoginValid(southwestLogin)) {
            //The login is valid
                //Can't use dagger because it may not be
                // instantiated with MainActivity setActivityComponents
                if (SouthwestCheckinRequest.isCheckedIn()) {
                    //TODO setup shared prefs for getting email/text data


                    if (emailAddress != null) {
                        emailDelivered = SouthwestDeliveryRequest.isDeliveredIn(emailAddress, true);
                    }
                    if (textNumber != null) {
                        textDelivered = SouthwestDeliveryRequest.isDeliveredIn(textNumber, false);
                    }

                    if (emailDelivered || textDelivered) {
                        //On successful delivery, remove the login from the view
                        SouthwestLogins logins = CleanupApplication.getAppComponent().swLogins();
                        logins.remove(logins.indexOf(southwestLogin));
                        eventBus.post(new UpdateLoginListEvent());
                        return;
                    }

                }
        }

        }
        eventBus.post(new UpdateLoginListEvent());
    }





}

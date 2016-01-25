package com.tolsma.ryan.airlinecheckin.services.requests;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.squareup.okhttp.ResponseBody;
import com.squareup.otto.Bus;
import com.tolsma.ryan.airlinecheckin.CleanupApplication;
import com.tolsma.ryan.airlinecheckin.MainActivity;
import com.tolsma.ryan.airlinecheckin.components.AppComponent;
import com.tolsma.ryan.airlinecheckin.model.events.NotificationEvent;
import com.tolsma.ryan.airlinecheckin.services.retrofit.SouthwestAPI;
import com.tolsma.ryan.airlinecheckin.utils.ConstantsConfig;

import java.io.IOException;

import retrofit.Call;

/**
 * Created by ryan on 1/13/16.
 */
public class SouthwestDeliveryRequest implements Runnable {

    String data; //email or phone number
    boolean isEmail;

    public SouthwestDeliveryRequest(String data, boolean isEmail) {
        this.data = data;
        this.isEmail = isEmail;
    }

    public static boolean isDeliveredIn(String data, boolean isEmail) {
        AppComponent dap = CleanupApplication.getAppComponent();
        Context ctx = dap.context();
        Bus eventBus = dap.eventBus();
        NotificationEvent ne = new NotificationEvent();
        Call<ResponseBody> responseBodyCall;
        ResponseBody responseBody;
        String response;
        PendingIntent pi = PendingIntent.getActivity(ctx, 0,
                new Intent(ctx, MainActivity.class), PendingIntent.FLAG_ONE_SHOT);
        ne.setIntent(pi);
        ne.setId(data.hashCode());

        SouthwestAPI api = dap.southwestAPI();
        try {
            //Different requests based off whether we want email or text
            if (isEmail) {

                responseBodyCall = dap.southwestAPI().sendEmailDelivery(
                        ConstantsConfig.SOUTHWEST_OPTION_EMAIL, data,
                        ConstantsConfig.SOUTHWEST_BOOK_NOW);
                responseBody = responseBodyCall.execute().body();
                response = responseBody.string();

                /*
                Down below, check if webpage has errors as
                prescribed in the ConstantsConfig class
                 */
                if (response.contains(ConstantsConfig.SOUTHWEST_EMAIL_BLANK_ERROR)
                        || response.contains(ConstantsConfig.SOUTHWEST_EMAIL_FORMAT_ERROR)
                        ) {
                    ne.setTitle("Southwest Email Format Error");
                    ne.setMessage("The email that the boarding pass was specified to be sent to" +
                            "is incorrectly formatted, please go to the Southwest website to download" +
                            "your boarding pass. For future flights, please amend the email address" +
                            "in the settings window.");
                    eventBus.post(ne);
                    eventBus.post(ne.getTitleToastEvent());
                    return false;
                }
                return true;


            } else {
                //This section is for if the phoneNumber request should be sent
                responseBodyCall = dap.southwestAPI().sendTextMessageDelivery(
                        ConstantsConfig.SOUTHWEST_OPTION_TEXT,
                        data.substring(0, 3),
                        data.substring(3, 6),
                        data.substring(6),
                        ConstantsConfig.SOUTHWEST_BOOK_NOW
                );
                responseBody = responseBodyCall.execute().body();
                response = responseBody.string();

                if (response.contains(ConstantsConfig.SOUTHWEST_PHONE_BLANK_ERROR)
                        || response.contains(ConstantsConfig.SOUTHWEST_PHONE_INCOMPLETE_ERROR)) {

                    ne.setTitle("Southwest Phone Number Format Error");
                    ne.setMessage("The phone number that the boarding pass was supposed to be " +
                            "texted to was incorrectly formatted. Please go to the Southwest website" +
                            "in order to download your boarding pass. For future flights, please" +
                            "amend this format error by correctly specifying it in the setting window.");
                    eventBus.post(ne);
                    eventBus.post(ne.getTitleToastEvent());
                    return false;
                }


                return true;
            }

        } catch (IOException e) {
            ne.setTitle("Southwest Boarding Pass Delivery Error");
            String type = isEmail ? "email" : "phone number";
            ne.setMessage("There was an error in specifying the " + type
                    + "to deliver your boarding pass to. Please go online to manually download" +
                    "your boarding pass online. For future flights, please check your " +
                    "wireless connection settings.");
            eventBus.post(ne);
            eventBus.post(ne.getTitleToastEvent());
            return false;
        }

    }

    @Override
    public void run() {
        isDeliveredIn(data, isEmail);
    }

}

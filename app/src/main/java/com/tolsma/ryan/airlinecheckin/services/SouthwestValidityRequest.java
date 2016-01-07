package com.tolsma.ryan.airlinecheckin.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.squareup.okhttp.ResponseBody;
import com.tolsma.ryan.airlinecheckin.CleanupApplication;
import com.tolsma.ryan.airlinecheckin.MainActivity;
import com.tolsma.ryan.airlinecheckin.components.AppComponent;
import com.tolsma.ryan.airlinecheckin.model.SouthwestLogin;
import com.tolsma.ryan.airlinecheckin.model.realmobjects.SouthwestLoginEvent;
import com.tolsma.ryan.airlinecheckin.utils.ConstantsConfig;

import java.io.IOException;

import hugo.weaving.DebugLog;
import retrofit.Call;
import retrofit.Response;

/**
 * Created by ryan on 1/6/16.
 */
public class SouthwestValidityRequest implements Runnable {

    SouthwestLogin sl;

    public SouthwestValidityRequest(SouthwestLogin sl) {
        this.sl = sl;
    }

    public void run() {

        this.isLoginValid(sl);

    }

    @DebugLog
    public boolean isLoginValid(SouthwestLogin sl) {
        AppComponent dap = CleanupApplication.getAppComponent();
        Context ctx = dap.context();
        NotificationManagerCompat nmc = dap.notificationManager();
        NotificationCompat.Builder builder = dap.notificationBuilder();
        if (sl == null) return false; //Most likely, been deleted from listview since thread launch


        SouthwestLoginEvent sle = sl.getSouthwestLoginEvent();
        Call<ResponseBody> responseBodyCall = CleanupApplication.getAppComponent()
                .southwestAPI().sendLogin(
                        sl.getConfirmationCode(), sle.getFirstName(),
                        sle.getLastName(), ConstantsConfig.SOUTHWEST_CHECKIN_BUTTON
                );
        try {
            Response<ResponseBody> bodyResponse =
                    responseBodyCall.execute();


            ResponseBody responseBody = bodyResponse.body();
            String response = responseBody.string();
            Log.d(getClass().getSimpleName(), response.toString());
            PendingIntent pi = PendingIntent.getActivity(ctx, 0,
                    new Intent(ctx, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

            if (response == null) throw (new IOException("Response is null"));

            Log.d(this.getClass().getSimpleName(), response);
            if (response.contains(ConstantsConfig.SOUTHWEST_RESERVATION_ERROR)) {


                Notification notification = builder
                        .setContentTitle("Southwest Reservation Error")
                        .setContentText("The reservation with confirmation code: "
                                + sl.getConfirmationCode() + " does not exist with the currently specified data")
                        .setContentIntent(pi)
                        .build();
                nmc.notify(sl.getConfirmationCode().hashCode(), notification);
                return false;


            }
           /*TODO else if( response.contains(ConstantsConfig.SOUTHWEST_TIMING_ERROR)) {
                return true;
            }
*/

        } catch (IOException e) {

            builder.setContentTitle("Connection Issue")
                    .setContentText("There was an error in connecting to the internet to " +
                            "verify the validity of the reservation with confirmation code " +
                            sl.getConfirmationCode() + "\n Please check that you have internet access");

            nmc.notify(sl.getConfirmationCode().hashCode(), builder.build());
            return false;


        }


        return false;


    }

}

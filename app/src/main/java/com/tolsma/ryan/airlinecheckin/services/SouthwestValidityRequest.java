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

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

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

    public SouthwestValidityRequest() {
        this(null);
    }

    @DebugLog
    public static boolean isLoginValid(SouthwestLogin sl) {

        AppComponent dap = CleanupApplication.getAppComponent();
        Context ctx = dap.context();
        NotificationManagerCompat nmc = dap.notificationManager();
        NotificationCompat.Builder builder = dap.notificationBuilder();
        if (sl == null) return false; //Most likely, been deleted from listview since thread launch


        SouthwestLoginEvent sle = sl.getLoginEvent();
        Call<ResponseBody> responseBodyCall = CleanupApplication.getAppComponent()
                .southwestAPI().sendLogin(
                        sl.getConfirmationCode(), sle.getFirstName(),
                        sle.getLastName(), ConstantsConfig.SOUTHWEST_CHECKIN_BUTTON
                );
        try {
            Response<ResponseBody> bodyResponse =
                    responseBodyCall.execute();


            ResponseBody responseBody = bodyResponse.body();

            String response = responseBody == null ? null : responseBody.string();
            Log.d(SouthwestValidityRequest.class.getSimpleName(), response.toString());
            //For debugging
            File file = new File(ctx.getFilesDir().getPath() + "log.txt");
            PrintWriter pw = new PrintWriter(file);
            pw.println(response);


            PendingIntent pi = PendingIntent.getActivity(ctx, 0,
                    new Intent(ctx, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

            if (response == null) throw (new IOException("Response is null"));

            Log.d(SouthwestValidityRequest.class.getSimpleName(), response);
            if (response.contains(ConstantsConfig.SOUTHWEST_RESERVATION_ERROR)) {


                Notification notification = builder
                        .setContentTitle("Southwest Reservation Error")
                        .setStyle(new NotificationCompat.BigTextStyle().bigText("The reservation with confirmation code: "
                                + sl.getConfirmationCode() + " does not exist within the Southwest database"))
                        .setContentIntent(pi)
                        .build();
             /*  uiWorker=()-> Toast.makeText(ctx, "Reservation with confirmation code: "+sl.getConfirmationCode()+
                        "doesn't exist!!!", Toast.LENGTH_LONG).show();
            */


                nmc.notify(sl.getConfirmationCode().hashCode(), notification);
                return false;


            }
           /*TODO else if( response.contains(ConstantsConfig.SOUTHWEST_TIMING_ERROR)) {
                return true;
            }
*/

        } catch (IOException e) {

            builder.setContentTitle("Connection Issue")
                    .setStyle(new NotificationCompat.BigTextStyle().bigText("There was an error in connecting to the internet to " +
                            "verify the validity of the reservation with confirmation code " +
                            sl.getConfirmationCode() + "\n Please check that you have internet access"));

           /* Toast.makeText(ctx,"There was an error in verifying the reservation with confirmation code! "+sl.getConfirmationCode()
                    +" Please make sure that this reservation exists and that you have an active internet connection!", Toast.LENGTH_LONG)
                    .show();
                    */
            nmc.notify(sl.getConfirmationCode().hashCode(), builder.build());
            return false;


        }

        Notification notification = builder
                .setContentTitle("Unable to Validate Login Credentials")
                .setStyle(new NotificationCompat.BigTextStyle().bigText("For some reason, there was an error in verifying the " +
                        "credentials for the login with confirmation code: " + sl.getConfirmationCode()
                        + "\n This may be caused by a change in the Southwest Website, please notify" +
                        "me at ______ so that I may update the application. Please make sure that" +
                        "you don't forget to manually login at : " + sle.getFlightDate().toString() +
                        " if this error continues to persist")).build();
        nmc.notify(sl.getConfirmationCode().hashCode(), notification);
        return false;


    }

    //static Runnable uiWorker;
    public void run() {

        this.isLoginValid(sl);

    }





}

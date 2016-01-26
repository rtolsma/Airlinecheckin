package com.tolsma.ryan.airlinecheckin.services.requests;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.squareup.okhttp.ResponseBody;
import com.squareup.otto.Bus;
import com.tolsma.ryan.airlinecheckin.CleanupApplication;
import com.tolsma.ryan.airlinecheckin.MainActivity;
import com.tolsma.ryan.airlinecheckin.components.AppComponent;
import com.tolsma.ryan.airlinecheckin.model.events.NotificationEvent;
import com.tolsma.ryan.airlinecheckin.model.logins.SouthwestLogin;
import com.tolsma.ryan.airlinecheckin.model.realmobjects.SouthwestLoginEvent;
import com.tolsma.ryan.airlinecheckin.utils.ConstantsConfig;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

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
        Bus eventBus = dap.eventBus();
        NotificationEvent ne = new NotificationEvent();
        PendingIntent pi = PendingIntent.getActivity(ctx, 0,
                new Intent(ctx, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        if (sl == null) return false; //Most likely, been deleted from listview since thread launch


        SouthwestLoginEvent sle = sl.getLoginEvent();

        ne.setId(sle.getConfirmationCode().hashCode());
        ne.setIntent(pi);

        Call<ResponseBody> responseBodyCall = dap
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
            //TODO debugging stop this
            File file = new File(ctx.getFilesDir().getPath() + "log.txt");
            PrintWriter pw = new PrintWriter(file);
            pw.println(response);


            if (response == null) throw (new IOException("Response is null"));

            Log.d(SouthwestValidityRequest.class.getSimpleName(), response);
            if (response.contains(ConstantsConfig.SOUTHWEST_RESERVATION_ERROR)) {

                ne.setTitle("Southwest Reservation Error");
                ne.setMessage("The reservation with confirmation code: "
                        + sl.getConfirmationCode() + " does not exist within the Southwest database");
                eventBus.post(ne);
                eventBus.post(ne.getToastEvent());
                return false;


            } else if (response.contains(ConstantsConfig.SOUTHWEST_NAME_ERROR)) {
                ne.setTitle("Southwest Naming Error");
                ne.setMessage("The reservation with confirmation code: "
                        + sl.getConfirmationCode() + " has an incorrect name associated with it. Please" +
                        "make sure that all of your information has inputted correctly");
                eventBus.post(ne);
                eventBus.post(ne.getToastEvent());

                return false;
            } else if (response.contains(ConstantsConfig.SOUTHWEST_TIMING_ERROR)) {
                return true;
            }


        } catch (IOException e) {
            Crashlytics.logException(e);
            ne.setTitle("Connection Issue");
            ne.setMessage("There was an error in connecting to the internet to " +
                    "verify the validity of the reservation with confirmation code " +
                    sl.getConfirmationCode() + "\n Please check that you have internet access");

            eventBus.post(ne);
            eventBus.post(ne.getToastEvent());
            return false;


        }
//If it gets here, it may be within the 24 hour checkin limit, so check first

        Calendar cal = Calendar.getInstance();
        if (cal.getTimeInMillis() - sle.getFlightDate().getTime() > 24 * 60 * 60 * 1000) {
            ne.setTitle("Unable to Validate Login Credentials");
            ne.setMessage("For some reason, there was an error in verifying the " +
                    "credentials for the login with confirmation code: " + sl.getConfirmationCode()
                    + "\n This may be caused by a change in the Southwest Website, please notify" +
                    " me at ______ so that I may update the application. Please make sure that" +
                    " you don't forget to manually login at" + sle.getFlightDate().toString() +
                    " if this error continues to persist");

            eventBus.post(ne);
            eventBus.post(ne.getToastEvent());
            return false;
        }
        return true;


    }

    //static Runnable uiWorker;
    public void run() {

        isLoginValid(sl);

    }


}

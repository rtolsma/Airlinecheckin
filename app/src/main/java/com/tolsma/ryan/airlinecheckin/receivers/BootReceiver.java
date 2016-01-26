package com.tolsma.ryan.airlinecheckin.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.tolsma.ryan.airlinecheckin.CleanupApplication;
import com.tolsma.ryan.airlinecheckin.model.logins.SouthwestLogins;

/**
 * This class exists, so that when the system reboots, all of the alarms
 * for the logins are reset.
 */
public class BootReceiver extends BroadcastReceiver {
    public BootReceiver() {
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        SouthwestLogins logins = CleanupApplication.getAppComponent().swLogins();
        long now = System.currentTimeMillis();
        for (int i = 0; i < logins.size(); i++) {

            if (logins.get(i).getLoginEvent().getFlightDate().getTime() > now) {
                //logins.remove(i);
            } else {

                logins.get(i).setAlarm(context);
            }

        }

    }
}

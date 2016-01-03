package com.tolsma.ryan.airlinecheckin.model;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.tolsma.ryan.airlinecheckin.model.realmobjects.LoginEvent;
import com.tolsma.ryan.airlinecheckin.model.realmobjects.SouthwestLoginEvent;
import com.tolsma.ryan.airlinecheckin.services.LoginAlarmService;
import com.tolsma.ryan.airlinecheckin.utils.ConstantsConfig;

/**
 * Created by ryan on 12/22/15.
 */
public class SouthwestLogin extends Login {


    public static final String AIRLINE = "Southwest Airline";

    private SouthwestLoginEvent southwestLoginEvent;

    public SouthwestLogin(LoginEvent event) {
        SouthwestLoginEvent sle = new SouthwestLoginEvent();
        sle.setLastName(event.getLastName());
        sle.setFirstName(event.getFirstName());
        sle.setFlightDate(event.getFlightDate());
        this.southwestLoginEvent = southwestLoginEvent;

    }

    public SouthwestLogin(SouthwestLoginEvent southwestLoginEvent) {
        this.southwestLoginEvent = southwestLoginEvent;
    }



    public boolean setAlarm(Context ctx) {
        if (this.southwestLoginEvent.getConfirmationCode() != null) {


            AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);

            if (mAlarmIntent != null) {
                //Has been sent since last Realm retrieval
                alarmManager.cancel(mAlarmIntent);
                mAlarmIntent = null;
            }

            if (getSouthwestLoginEvent().getFlightDate() != null) {
                Intent i = new Intent(ctx, LoginAlarmService.class);
                i.putExtra(ConstantsConfig.LOGIN_INTENT_ID, getConfirmationCode());
                mAlarmIntent = PendingIntent.getService(ctx, 0, i, 0);

                long timeToAlarm = getSouthwestLoginEvent().getFlightDate().getTime() - 24 * 60 * 60 * 1000; //One day before flight
                if (Build.VERSION.SDK_INT >= 23)
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, timeToAlarm, mAlarmIntent);
                else if (Build.VERSION.SDK_INT >= 19)
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeToAlarm, mAlarmIntent);
                else
                    alarmManager.set(AlarmManager.RTC_WAKEUP, timeToAlarm, mAlarmIntent);

            }
        }
        return false;


    }

    public String getConfirmationCode() {
        return southwestLoginEvent.getConfirmationCode();
    }

    public void setConfirmationCode(String mConfirmationCode) {
        southwestLoginEvent.setConfirmationCode(mConfirmationCode);
    }

    public SouthwestLoginEvent getSouthwestLoginEvent() {
        return southwestLoginEvent;

    }

    public SouthwestLoginEvent setSouthwestLoginEvent(SouthwestLoginEvent event) {
        SouthwestLoginEvent temp = this.getSouthwestLoginEvent();
        this.southwestLoginEvent = event;
        return temp;

    }

}

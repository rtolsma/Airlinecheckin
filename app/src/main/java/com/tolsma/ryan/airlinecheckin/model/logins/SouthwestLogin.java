package com.tolsma.ryan.airlinecheckin.model.logins;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.tolsma.ryan.airlinecheckin.model.realmobjects.SouthwestLoginEvent;
import com.tolsma.ryan.airlinecheckin.services.LoginAlarmService;
import com.tolsma.ryan.airlinecheckin.utils.ConstantsConfig;

/**
 * Created by ryan on 12/22/15.
 */
public class SouthwestLogin {


    public static final String AIRLINE = "Southwest Airline";

    private SouthwestLoginEvent southwestLoginEvent;
    private PendingIntent mAlarmIntent;

    public SouthwestLogin(SouthwestLoginEvent southwestLoginEvent) {
        this.southwestLoginEvent = southwestLoginEvent;
    }

    public SouthwestLogin() {
        this(null);
    }




    public boolean setAlarm(Context ctx) {
        if (this.southwestLoginEvent.getConfirmationCode() != null) {


            AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);

            if (mAlarmIntent != null) {
                //Has been sent since last Realm retrieval
                alarmManager.cancel(mAlarmIntent);
                mAlarmIntent = null;
            }

            if (getLoginEvent().getFlightDate() != null) {
                Intent i = new Intent(ctx, LoginAlarmService.class);
                i.putExtra(ConstantsConfig.LOGIN_INTENT_ID, new String(getConfirmationCode()));
                mAlarmIntent = PendingIntent.getService(ctx, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

                long timeToAlarm = getLoginEvent().getFlightDate().getTime() - (ConstantsConfig.DAY_MILLLIS) - (50); //One day+1/10 sec. before flight
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

    public boolean cancelAlarm(Context ctx) {
        if (mAlarmIntent == null || ctx == null) return false;
        AlarmManager am = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        am.cancel(mAlarmIntent);
        return true;
    }

    public String getConfirmationCode() {
        return southwestLoginEvent.getConfirmationCode();
    }

    public void setConfirmationCode(String mConfirmationCode) {
        southwestLoginEvent.setConfirmationCode(mConfirmationCode);
    }

    public SouthwestLoginEvent getLoginEvent() {
        return southwestLoginEvent;

    }

    public SouthwestLoginEvent setLoginEvent(SouthwestLoginEvent event) {
        SouthwestLoginEvent temp = this.getLoginEvent();
        this.southwestLoginEvent = event;
        return temp;

    }

}

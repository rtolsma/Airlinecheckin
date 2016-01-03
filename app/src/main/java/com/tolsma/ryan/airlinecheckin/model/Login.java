package com.tolsma.ryan.airlinecheckin.model;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.tolsma.ryan.airlinecheckin.model.realmobjects.LoginEvent;
import com.tolsma.ryan.airlinecheckin.services.LoginAlarmService;
import com.tolsma.ryan.airlinecheckin.utils.ConstantsConfig;

/**
 * Created by ryantolsma on 12/29/15.
 */
public class Login {


    protected PendingIntent mAlarmIntent;
    private LoginEvent loginEvent; //Non-Protected, since subclasses will have other forms of LoginEvent and Realm Objects can't be subclassed


    public Login(LoginEvent loginEvent) {
        this.loginEvent = loginEvent;
    }

    public Login() {
        this.loginEvent = null;
    }
    /**
     * @param ctx Context to create AlarmManager
     * @return False if flight date is not set
     * <p>
     * This will set an Alarm for this LoginEvent object at the
     * given flight date. If an alarm has already been set,
     * then it will cancel that alarm, and update it with the correct
     * data
     */
    public boolean setAlarm(Context ctx, String id) {
        AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);

        if (mAlarmIntent != null) {
            //Has been sent since last Realm retrieval
            alarmManager.cancel(mAlarmIntent);
            mAlarmIntent = null;
        }

        if (loginEvent.getFlightDate() != null) {
            Intent i = new Intent(ctx, LoginAlarmService.class);
            i.putExtra(ConstantsConfig.LOGIN_INTENT_ID, id);
            mAlarmIntent = PendingIntent.getService(ctx, 0, i, 0);

            long timeToAlarm=loginEvent.getFlightDate().getTime()-24*60*60*1000; //One day before flight
            if (Build.VERSION.SDK_INT >= 23)
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,timeToAlarm, mAlarmIntent);
            else if (Build.VERSION.SDK_INT >= 19)
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeToAlarm, mAlarmIntent);
            else
                alarmManager.set(AlarmManager.RTC_WAKEUP, timeToAlarm, mAlarmIntent);

            return true;
        }
        return false; //No date has been set for the flight, so no alarm to be set

    }

    public boolean cancelAlarm(Context ctx) {
        if (mAlarmIntent == null) return false;//No alarm set, since intent is null

        AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(mAlarmIntent);
        return true;
    }

    public LoginEvent getLoginEvent() {
        return loginEvent;
    }

    public LoginEvent setLoginEvent(LoginEvent element) {
        LoginEvent temp = this.getLoginEvent();
        loginEvent = element;
        return temp;

    }
}

package com.tolsma.ryan.airlinecheckin.model;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.tolsma.ryan.airlinecheckin.services.LoginAlarmService;
import com.tolsma.ryan.airlinecheckin.utils.ConstantsConfig;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.Index;

/**
 * Created by ryan on 12/22/15.
 */
public abstract class Login extends RealmObject {
    @Index
    public String mFirstName;
    @Index
    public String mLastName;
    @Index
    public String mAirline;
    @Index
    public Date mFlightDate;
    private PendingIntent mAlarmIntent;


    public Login(String first, String last, Date date) {
        this.mFirstName=first;
        this.mLastName=last;
        this.mFlightDate = date;
    }

    /**
     * @param ctx Context to create AlarmManager
     * @return False if flight date is not set
     * <p>
     * This will set an Alarm for this Login object at the
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

        if (mFlightDate != null) {
            Intent i = new Intent(ctx, LoginAlarmService.class);
            i.putExtra(ConstantsConfig.LOGIN_INTENT_ID, id);
            mAlarmIntent = PendingIntent.getService(ctx, 0, i, 0);

            if (Build.VERSION.SDK_INT >= 23)
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, mFlightDate.getTime(), mAlarmIntent);
            else if (Build.VERSION.SDK_INT >= 19)
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, mFlightDate.getTime(), mAlarmIntent);
            else
                alarmManager.set(AlarmManager.RTC_WAKEUP, mFlightDate.getTime(), mAlarmIntent);

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


    public String getAirline() {
        return mAirline;
    }

    public void setAirline(String mAirline) {
        this.mAirline = mAirline;
    }



    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public Date getFlightDate() {
        return mFlightDate;
    }

    public void setFlightDate(Date mFlightDate) {
        this.mFlightDate = mFlightDate;
    }
}

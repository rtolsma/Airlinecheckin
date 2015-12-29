package com.tolsma.ryan.airlinecheckin.model;

import android.content.Context;

import java.util.Date;

import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ryan on 12/22/15.
 */
public class SouthwestLogin extends Login {

    @Ignore
    public final String mAirline="Southwest Airline";
    @PrimaryKey
    public String mConfirmationCode;


    public SouthwestLogin(String first, String last, String confirmationCode, Date date) {
        super(first, last, date);
        this.mConfirmationCode=confirmationCode;
    }

    public SouthwestLogin() {
        this(null, null, null, null);
    }


    public boolean setAlarm(Context ctx) {
        if (mConfirmationCode != null) {
            super.setAlarm(ctx, mConfirmationCode);
            return true;
        }
        return false;


    }

    public String getConfirmationCode() {
        return mConfirmationCode;
    }

    public void setConfirmationCode(String mConfirmationCode) {
        this.mConfirmationCode = mConfirmationCode;
    }


}

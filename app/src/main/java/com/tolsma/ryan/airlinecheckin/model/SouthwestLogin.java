package com.tolsma.ryan.airlinecheckin.model;

import android.content.Context;

import com.tolsma.ryan.airlinecheckin.model.realmobjects.LoginEvent;
import com.tolsma.ryan.airlinecheckin.model.realmobjects.SouthwestLoginEvent;

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
            super.setAlarm(ctx, southwestLoginEvent.getConfirmationCode());
            return true;
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

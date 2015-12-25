package com.tolsma.ryan.airlinecheckin.model;

/**
 * Created by ryan on 12/22/15.
 */
public class SouthwestLogin extends Login {


    public String mConfirmationCode;
    public final String mAirline="Southwest Airline";
    public SouthwestLogin(String first, String last, String confirmationCode) {
        super(first, last);
        this.mConfirmationCode=confirmationCode;
    }


    public String getConfirmationCode() {
        return mConfirmationCode;
    }

    public void setConfirmationCode(String mConfirmationCode) {
        this.mConfirmationCode = mConfirmationCode;
    }


}

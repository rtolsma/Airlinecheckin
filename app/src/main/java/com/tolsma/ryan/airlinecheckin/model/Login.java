package com.tolsma.ryan.airlinecheckin.model;

/**
 * Created by ryan on 12/22/15.
 */
public abstract class Login {

    public String mFirstName;
    public String mLastName;
    public String mAirline;
    //TODO add Joda DateTime
    //public DateTime mDateTime;

    public Login(String first, String last) {
        this.mFirstName=first;
        this.mLastName=last;
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
}

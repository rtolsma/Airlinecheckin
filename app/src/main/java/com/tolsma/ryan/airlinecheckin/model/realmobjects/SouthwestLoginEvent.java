package com.tolsma.ryan.airlinecheckin.model.realmobjects;

import com.tolsma.ryan.airlinecheckin.utils.ConstantsConfig;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ryantolsma on 12/29/15.
 */
public class SouthwestLoginEvent extends RealmObject {
    @Index
    private String firstName;
    @Index
    private String lastName;

    private String airline = ConstantsConfig.SOUTHWEST_AIRLINES;
    @Index
    private Date flightDate;
    @PrimaryKey
    private String confirmationCode;


    public SouthwestLoginEvent() {
        this(null, null, null, null);
    }

    public SouthwestLoginEvent(String first, String last, Date date, String confirmationCode) {
        this.firstName = first;
        this.lastName = last;
        this.flightDate = date;
        this.confirmationCode = confirmationCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public Date getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(Date flightDate) {
        this.flightDate = flightDate;
    }

    public String getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }
}



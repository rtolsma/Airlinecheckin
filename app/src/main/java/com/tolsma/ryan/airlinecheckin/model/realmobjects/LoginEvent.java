package com.tolsma.ryan.airlinecheckin.model.realmobjects;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.Index;

/**
 * Created by ryan on 12/22/15.
 */
public class LoginEvent extends RealmObject {
    @Index
    private String firstName;
    @Index
    private String lastName;

    @Index
    private Date flightDate;


    public LoginEvent() {
        this(null, null, null);
    }

    public LoginEvent(String first, String last, Date date) {
        this.firstName = first;
        this.lastName = last;
        this.flightDate = date;
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


    public Date getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(Date flightDate) {
        this.flightDate = flightDate;
    }
}

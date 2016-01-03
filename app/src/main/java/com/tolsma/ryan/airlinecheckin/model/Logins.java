package com.tolsma.ryan.airlinecheckin.model;

import android.content.Context;

import com.tolsma.ryan.airlinecheckin.CleanupApplication;
import com.tolsma.ryan.airlinecheckin.model.realmobjects.LoginEvent;
import com.tolsma.ryan.airlinecheckin.utils.RealmUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by ryantolsma on 12/29/15.
 */
public class Logins {
    @Inject
    Context ctx;
    @Inject
    Realm realm;
    private List<Login> loginList;

    public Logins() {
        loginList = new ArrayList<>();
        CleanupApplication.getAppComponent().inject(this);
    }

    public Logins(RealmResults<LoginEvent> results) {
        this();

        for (LoginEvent elem : results) {
            loginList.add(new Login(elem));
        }

    }

    public boolean contains(Login element) {
        return loginList.contains(element);
    }

    //Can't have 2 of same login, so check for equivalence
    public boolean add(LoginEvent element) {
        return add(new Login(element));
    }

    public boolean add(Login element) {
        if (!contains(element)) {
            loginList.add(element);
            RealmUtils.saveToRealm(ctx, element.getLoginEvent());
        return true;
        }
        return false;
    }

    public LoginEvent set(Login element, Login newElement) {
        realm.beginTransaction();
        RealmQuery results = filter(element);

        //Requires that above sorting process uniquely identifies the airlines!!Hopefully

        RealmObject ro = results.findAllAsync().remove(0);
        //Removes the related realm object from the loginList
        for (int i = 0; i < loginList.size(); i++) {

            if (loginList.get(i).equals(ro)) {
                loginList.remove(i);
            }

        }
        realm.commitTransaction();
        realm.close();
        this.add(newElement);

        return (LoginEvent) ro;

    }

    protected RealmQuery filter(Login element) {

        return realm.where(element.getLoginEvent().getClass()).equalTo("mFlightDate", element.getLoginEvent().getFlightDate()).
                equalTo("mFirstName", element.getLoginEvent().getFirstName())
                .equalTo("mLastName", element.getLoginEvent().getLastName());
    }

    public boolean add(Logins otherLogins) {
        if (otherLogins == null) return false;
        for (Login element : otherLogins.getList()) {
            this.add(element);
        }
        return true;
    }


    public List<Login> getList() {
        return this.loginList;
    }

    public int size() {
        return this.loginList.size();
    }

    public Login get(int index) {
        return this.loginList.get(index);
    }


    public void sort(Comparator<Login> c) {
        Collections.sort(loginList, c);
    }


}

package com.tolsma.ryan.airlinecheckin.model;

import android.content.Context;

import com.tolsma.ryan.airlinecheckin.model.realmobjects.SouthwestLoginEvent;
import com.tolsma.ryan.airlinecheckin.utils.RealmUtils;

import java.util.Collections;
import java.util.Comparator;

import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by ryantolsma on 12/29/15.
 */
public class SouthwestLogins extends Logins {

    public SouthwestLogins(RealmResults<SouthwestLoginEvent> results) {
        super();
        for (SouthwestLoginEvent e : results) {
            getList().add(new SouthwestLogin(e));
        }
    }


    public boolean add(SouthwestLogin southwestLogin) {
        if(!contains(southwestLogin)) {
            getList().add(southwestLogin);
            RealmUtils.saveToRealm(ctx, southwestLogin.getSouthwestLoginEvent());
            return true;
        }
        return false;



    }

    public static SouthwestLogins createFromRealm(Context ctx, Class<SouthwestLoginEvent> clazz) {
        return new SouthwestLogins(RealmUtils.getAllRealmResults(ctx, clazz));
    }

    public RealmQuery filter(SouthwestLogin element) {
        return super.filter(element).equalTo("confirmationCode", element.getSouthwestLoginEvent().getConfirmationCode());
    }

}

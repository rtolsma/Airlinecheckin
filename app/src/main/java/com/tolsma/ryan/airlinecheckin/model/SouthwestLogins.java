package com.tolsma.ryan.airlinecheckin.model;

import android.content.Context;
import android.util.Log;

import com.tolsma.ryan.airlinecheckin.model.realmobjects.SouthwestLoginEvent;
import com.tolsma.ryan.airlinecheckin.utils.RealmUtils;

import io.realm.RealmResults;

/**
 * Created by ryantolsma on 12/29/15.
 */
public class SouthwestLogins extends Logins {

    public SouthwestLogins() {
        super();
    }

    public SouthwestLogins(RealmResults<SouthwestLoginEvent> results) {
        this();
        for (SouthwestLoginEvent e : results) {
            add(new SouthwestLogin(e));
            Log.d(SouthwestLogins.class.getSimpleName(), e.getConfirmationCode());

        }
    }

    public static SouthwestLogins createFromRealm(Context ctx, Class<SouthwestLoginEvent> clazz) {
        SouthwestLogins swl = new SouthwestLogins(RealmUtils.getAllRealmResults(ctx, clazz));
        for (Login l : swl.getList()) {
            Log.d(SouthwestLogins.class.getSimpleName(), ((SouthwestLogin) l).getSouthwestLoginEvent()
                    .getConfirmationCode() + "   " + ((SouthwestLogin) l).getSouthwestLoginEvent().getFlightDate());
        }
        return swl;
    }

    public boolean add(SouthwestLogin southwestLogin) {
        if(!contains(southwestLogin)) {
            getList().add(southwestLogin);
            RealmUtils.saveToRealm(ctx, southwestLogin.getSouthwestLoginEvent());
            return true;
        }
        return false;



    }

    public int getIndex(SouthwestLogin sl) {
        return getIndex(sl.getSouthwestLoginEvent());
    }

    public int getIndex(SouthwestLoginEvent sle) {
        for (int i = 0; i < getList().size(); i++) {
            if (get(i).equals(sle)) return i;
        }
        return -1;

    }

    public boolean delete(int index) {

        SouthwestLogin sl = (SouthwestLogin) getList().remove(index);
        realm.beginTransaction();
        realm.where(SouthwestLoginEvent.class)
                .equalTo("confirmationCode", sl.getConfirmationCode())
                .findAll().clear();
        realm.commitTransaction();
        sl.cancelAlarm(ctx);
        return true;

    }

    public boolean delete(SouthwestLogin sl) {
        int i = getIndex(sl);
        if (i == -1) return false;

        else return delete(i);


    }
  /*  public RealmQuery filter(SouthwestLogin element) {
        SouthwestLoginEvent sle=element.getSouthwestLoginEvent();

        return realm.where(SouthwestLoginEvent.class)
         .equalTo("confirmationCode", element.getSouthwestLoginEvent().getConfirmationCode()).f;
    } */

}

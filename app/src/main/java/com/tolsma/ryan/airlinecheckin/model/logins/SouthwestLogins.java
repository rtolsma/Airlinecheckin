package com.tolsma.ryan.airlinecheckin.model.logins;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.tolsma.ryan.airlinecheckin.CleanupApplication;
import com.tolsma.ryan.airlinecheckin.model.realmobjects.SouthwestLoginEvent;
import com.tolsma.ryan.airlinecheckin.utils.ConstantsConfig;
import com.tolsma.ryan.airlinecheckin.utils.RealmUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by ryantolsma on 12/29/15.
 */
public class SouthwestLogins {
    @Inject
    Context ctx;
    @Inject
    Realm realm;
    private List<SouthwestLogin> loginList;

    public SouthwestLogins() {
        loginList = new ArrayList<>();
        CleanupApplication.getAppComponent().inject(this);
    }

    public SouthwestLogins(RealmResults<SouthwestLoginEvent> events) {
        this();
        for (SouthwestLoginEvent sle : events) {
            add(new SouthwestLogin(sle));
        }

    }

    public static SouthwestLogins createFromRealm(Context ctx, Class<SouthwestLoginEvent> clazz) {
        SouthwestLogins swl = new SouthwestLogins(RealmUtils.getAllRealmResults(ctx, clazz));
        for (SouthwestLogin l : swl.getList()) {
            Log.d(SouthwestLogins.class.getSimpleName(), l.getLoginEvent()
                    .getConfirmationCode() + "   " + l.getLoginEvent().getFlightDate());
        }
        return swl;
    }

    public List<SouthwestLogin> getList() {
        return loginList;
    }

    public int size() {
        return loginList.size();
    }

    public boolean contains(SouthwestLogin element) {
        return loginList.contains(element);
    }

    public boolean add(SouthwestLogin southwestLogin) {
        if(!contains(southwestLogin)) {
            getList().add(southwestLogin);
            RealmUtils.saveToRealm(ctx, southwestLogin.getLoginEvent());
            return true;
        }
        return false;
    }

    public boolean remove(int index) {
        SouthwestLogin sl = get(index);
        sl.cancelAlarm(ctx);
        deleteFromRealm(sl);
        loginList.remove(index);
        return true;
    }

    public SouthwestLogin get(int index) {
        return loginList.get(index);
    }

    public int indexOf(SouthwestLogin sl) {
        for (int i = 0; i < getList().size(); i++) {
            if (get(i).getLoginEvent().equals(sl.getLoginEvent())) return i;
        }
        return -1;
    }

    public int indexOf(String confirmationCode) {
        for (int i = 0; i < size(); i++) {
            if (get(i).getConfirmationCode().equals(confirmationCode))
                return i;

        }
        return -1;

    }

    /**
     * @param sl A login to delete
     *           <p>
     *           This method employs a kind of hacky scheme
     *           to ensure that every realm object is accessed
     *           from the same thread. Since these objects are
     *           only created from the main thread with the dialog
     *           fragment, I used a handler to get these transactions
     *           on the main thread also
     */
    private void deleteFromRealm(SouthwestLogin sl) {

        new Handler(Looper.getMainLooper()).post(
                () -> {
                    realm.beginTransaction();
                    realm.where(sl.getLoginEvent().getClass())
                            .equalTo(ConstantsConfig.SOUTHWEST_CONFIRMATION_CODE
                                    , sl.getConfirmationCode()).findAll().clear();
                    realm.commitTransaction();
                }
        );


    }

    public void sort(Comparator<SouthwestLogin> c) {

        if (size() < 2) return;
        Collections.sort(loginList, c);
    }

}

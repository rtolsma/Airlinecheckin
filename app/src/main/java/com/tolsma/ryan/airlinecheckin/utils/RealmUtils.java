package com.tolsma.ryan.airlinecheckin.utils;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by ryantolsma on 12/27/15.
 */
public class RealmUtils {

    public static <T extends RealmObject> void saveToRealm(Context ctx, T... realmObect) {

        Realm realm = Realm.getInstance(ctx);
        realm.beginTransaction();
        for (T element : realmObect) {
            realm.copyToRealmOrUpdate(element);

        }
        realm.commitTransaction();
        realm.close();


    }

    public static <T extends RealmObject> RealmResults<T> getAllRealmResults(Context ctx, Class<T> clazz) {
        Realm realm = Realm.getInstance(ctx);
        return realm.where(clazz).findAllAsync();
    }


}

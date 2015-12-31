package com.tolsma.ryan.airlinecheckin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.tolsma.ryan.airlinecheckin.CleanupApplication;
import com.tolsma.ryan.airlinecheckin.model.Login;
import com.tolsma.ryan.airlinecheckin.model.Logins;

import javax.inject.Inject;

/**
 * Created by ryantolsma on 12/29/15.
 */
public class LoginListAdapter extends BaseAdapter {

    @Inject
    LayoutInflater layoutInflater;
    @Inject
    Context ctx;
    private Logins logins;

    public LoginListAdapter(Logins logins) {
        this.logins = logins;
        CleanupApplication.getAppComponent().inject(this);
    }


    //TODO implement getView, but need to create XML first
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
        if (convertView == null) {


        }


        return convertView;
    }

    @Override
    public int getCount() {
        return logins.size();
    }

    @Override
    public long getItemId(int pos) {
        //I don't know what to do here???
        return pos;
    }

    @Override
    public Login getItem(int pos) {
        return logins.get(pos);
    }


    class LoginViewHolder {
        View v;

        public LoginViewHolder(View v) {
            this.v = v;
        }


    }
}

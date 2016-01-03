package com.tolsma.ryan.airlinecheckin.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tolsma.ryan.airlinecheckin.CleanupApplication;
import com.tolsma.ryan.airlinecheckin.R;
import com.tolsma.ryan.airlinecheckin.model.Login;
import com.tolsma.ryan.airlinecheckin.model.Logins;
import com.tolsma.ryan.airlinecheckin.model.SouthwestLogin;
import com.tolsma.ryan.airlinecheckin.model.realmobjects.LoginEvent;
import com.tolsma.ryan.airlinecheckin.model.realmobjects.SouthwestLoginEvent;

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
        LoginViewHolder loginViewHolder;
        SouthwestLoginEvent temp= ((SouthwestLogin)logins.get(position)).getSouthwestLoginEvent();
        if (convertView == null) {
            v=layoutInflater.inflate(R.layout.fragment_login_list_item, parent, false);
            //Create viewholder to decrease calls to findViewById by holding references to objects
            loginViewHolder=new LoginViewHolder();
            loginViewHolder.setAirline((TextView) v.findViewById(R.id.fragment_login_list_item_title));
            loginViewHolder.setNames((TextView) v.findViewById(R.id.fragment_login_list_item_name));
            loginViewHolder.setDate((TextView) v.findViewById(R.id.fragment_login_list_item_date));
            loginViewHolder.setConfirmationNumber((TextView) v.findViewById(R.id.fragment_login_list_item_confirmation));
            v.setTag(loginViewHolder);
            loginViewHolder.getAirline().setText("Southwest Airlines");
            loginViewHolder.getNames().setText(temp.getFirstName() + " " + temp.getLastName());
            loginViewHolder.getDate().setText(temp.getFlightDate().toString());
            loginViewHolder.getConfirmationNumber().setText( temp.getConfirmationCode() );
            convertView=v;
        } else {
            loginViewHolder= (LoginViewHolder) convertView.getTag();
            loginViewHolder.getAirline().setText("Southwest Airlines");
            loginViewHolder.getNames().setText(temp.getFirstName() + " " + temp.getLastName());
            loginViewHolder.getDate().setText(temp.getFlightDate().toString());
            loginViewHolder.getConfirmationNumber().setText( temp.getConfirmationCode() );




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
       TextView names;
        TextView date;
        TextView confirmationNumber;
        TextView airline;

        public LoginViewHolder() {}

        public TextView getNames() {
            return names;
        }

        public void setNames(TextView names) {
            this.names = names;
        }

        public TextView getDate() {
            return date;
        }

        public void setDate(TextView date) {
            this.date = date;
        }

        public TextView getConfirmationNumber() {
            return confirmationNumber;
        }

        public void setConfirmationNumber(TextView confirmationNumber) {
            this.confirmationNumber = confirmationNumber;
        }

        public TextView getAirline() {
            return airline;
        }

        public void setAirline(TextView airline) {
            this.airline = airline;
        }
    }
}

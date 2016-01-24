package com.tolsma.ryan.airlinecheckin.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tolsma.ryan.airlinecheckin.CleanupApplication;
import com.tolsma.ryan.airlinecheckin.R;
import com.tolsma.ryan.airlinecheckin.model.logins.SouthwestLogin;
import com.tolsma.ryan.airlinecheckin.model.logins.SouthwestLogins;
import com.tolsma.ryan.airlinecheckin.model.realmobjects.SouthwestLoginEvent;
import com.tolsma.ryan.airlinecheckin.utils.ConstantsConfig;

import javax.inject.Inject;

/**
 * Created by ryantolsma on 12/29/15.
 */
public class LoginListAdapter extends BaseAdapter {

    // @Inject
    LayoutInflater layoutInflater;
    @Inject
    Context ctx;
    private SouthwestLogins logins;

    public LoginListAdapter(SouthwestLogins logins) {
        this.logins = logins;
    }


    //TODO implement getView, but need to create XML first
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CleanupApplication.getActivityComponent().inject(this);
        layoutInflater = CleanupApplication.getActivityComponent().layoutInflater();


        View v;
        LoginViewHolder loginViewHolder;
        SouthwestLoginEvent temp = logins.get(position).getLoginEvent();
        if (convertView == null) {
            v=layoutInflater.inflate(R.layout.fragment_login_list_item, parent, false);
            //Create viewholder to decrease calls to findViewById by holding references to objects
            loginViewHolder=new LoginViewHolder();
            loginViewHolder.setAirline((TextView) v.findViewById(R.id.fragment_login_list_item_title));
            loginViewHolder.setNames((TextView) v.findViewById(R.id.fragment_login_list_item_name));
            loginViewHolder.setDate((TextView) v.findViewById(R.id.fragment_login_list_item_date));
            loginViewHolder.setConfirmationNumber((TextView) v.findViewById(R.id.fragment_login_list_item_confirmation));
            loginViewHolder.setCardView((CardView) v.findViewById(R.id.fragment_login_list_item_cardview));
            v.setTag(loginViewHolder);
            loginViewHolder.getAirline().setText(ConstantsConfig.SOUTHWEST_AIRLINES);
            loginViewHolder.getNames().setText(temp.getFirstName()
                    + " " + temp.getLastName());

            loginViewHolder.getDate().setText(temp.getFlightDate().toString());
            loginViewHolder.getConfirmationNumber()
                    .setText(temp.getConfirmationCode());

            loginViewHolder.getCardView().setMaxCardElevation(ConstantsConfig.CARDVIEW_ELEVATION);
            loginViewHolder.getCardView().setCardElevation(ConstantsConfig.CARDVIEW_ELEVATION);
            loginViewHolder.getCardView().setRadius(ConstantsConfig.CARDVIEW_RADIUS);
            loginViewHolder.getCardView().setUseCompatPadding(true);



            convertView=v;
        } else {
            loginViewHolder= (LoginViewHolder) convertView.getTag();
            loginViewHolder.getAirline().setText(ConstantsConfig.SOUTHWEST_AIRLINES);
            loginViewHolder.getNames().setText(temp.getFirstName()
                    + " " + temp.getLastName());

            loginViewHolder.getDate().setText(temp.getFlightDate().toString());
            loginViewHolder.getConfirmationNumber()
                    .setText(temp.getConfirmationCode());
            loginViewHolder.getCardView().setMaxCardElevation(ConstantsConfig.CARDVIEW_ELEVATION);
            loginViewHolder.getCardView().setCardElevation(ConstantsConfig.CARDVIEW_ELEVATION);
            loginViewHolder.getCardView().setRadius(ConstantsConfig.CARDVIEW_RADIUS);
            loginViewHolder.getCardView().setUseCompatPadding(true);



        }


        return convertView;
    }

    public void remove(String confirmationCode) {
        logins.remove(logins.indexOf(confirmationCode));
    }

    public void remove(SouthwestLogin sl) {
        int index = logins.indexOf(sl);
        remove(index);
    }

    public void remove(int index) {
        logins.remove(index);

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
    public SouthwestLogin getItem(int pos) {
        return logins.get(pos);
    }


    class LoginViewHolder {
       TextView names;
        TextView date;
        TextView confirmationNumber;
        TextView airline;
        CardView cardView;

        public LoginViewHolder() {}

        public CardView getCardView() {
            return cardView;
        }

        public void setCardView(CardView cardView) {
            this.cardView = cardView;
        }

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

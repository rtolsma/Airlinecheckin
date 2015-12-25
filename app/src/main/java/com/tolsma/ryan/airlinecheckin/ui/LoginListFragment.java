package com.tolsma.ryan.airlinecheckin.ui;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tolsma.ryan.airlinecheckin.R;


/**
 * A placeholder fragment containing a simple view.
 */
public class LoginListFragment extends Fragment {

    public LoginListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login_list, container, false);
    }

}

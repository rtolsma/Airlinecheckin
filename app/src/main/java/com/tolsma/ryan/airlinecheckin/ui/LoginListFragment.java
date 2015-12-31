package com.tolsma.ryan.airlinecheckin.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.tolsma.ryan.airlinecheckin.CleanupApplication;
import com.tolsma.ryan.airlinecheckin.R;
import com.tolsma.ryan.airlinecheckin.adapters.LoginListAdapter;
import com.tolsma.ryan.airlinecheckin.model.Logins;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * A placeholder fragment containing a simple view.
 */
public class LoginListFragment extends Fragment {

    @Bind(R.id.fragment_login_list_view)
    public ListView loginListView;
    @Inject
    Logins logins;

    LoginListAdapter listAdapter;

    @Inject
    FragmentManager fragmentManager;



    private LoginDialogFragment dialogFragment;
    public LoginListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ButterKnife.bind(this, container);
        CleanupApplication.getLoginComponent().inject(this);

        listAdapter = new LoginListAdapter(logins);
        loginListView.setAdapter(listAdapter);
        return inflater.inflate(R.layout.fragment_login_list, container, false);
    }

    public void showDialog() {
        dialogFragment = new LoginDialogFragment(false);
        dialogFragment.show(fragmentManager, dialogFragment.getClass().getSimpleName());
        dialogFragment.onComplete((login, toChange) -> {

            if (toChange) {
                //TODO, figure out how to implement resue of LoginDialogFragment
            }
            logins.add(login);
            loginListView.invalidate();
        });

    }
}

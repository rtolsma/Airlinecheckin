package com.tolsma.ryan.airlinecheckin.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.tolsma.ryan.airlinecheckin.R;
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
    private Logins logins;


    private LoginDialogFragment dialogFragment;
    public LoginListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ButterKnife.bind(this, container);

        return inflater.inflate(R.layout.fragment_login_list, container, false);
    }

    public void showDialog() {
        dialogFragment = new LoginDialogFragment();
        dialogFragment.show(getFragmentManager().beginTransaction(), dialogFragment.getClass().getSimpleName());
        dialogFragment.onComplete((login) -> {

            logins.add(login);
            loginListView.invalidate();
        });

    }
}

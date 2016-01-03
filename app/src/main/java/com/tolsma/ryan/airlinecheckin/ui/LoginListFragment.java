package com.tolsma.ryan.airlinecheckin.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.tolsma.ryan.airlinecheckin.CleanupApplication;
import com.tolsma.ryan.airlinecheckin.R;
import com.tolsma.ryan.airlinecheckin.adapters.LoginListAdapter;
import com.tolsma.ryan.airlinecheckin.model.Logins;
import com.tolsma.ryan.airlinecheckin.model.SouthwestLogin;
import com.tolsma.ryan.airlinecheckin.model.SouthwestLogins;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * A placeholder fragment containing a simple view.
 */
public class LoginListFragment extends Fragment {

    public static final String TAG=LoginListFragment.class.getSimpleName();
    @Bind(R.id.fragment_login_list_view)
    public ListView loginListView;
    public RelativeLayout loginListContainer;

    @Inject
    SouthwestLogins logins;

    @Inject
    Context ctx;
    LoginListAdapter listAdapter;

    @Inject
    FragmentManager fragmentManager;



    private LoginDialogFragment dialogFragment;
    public LoginListFragment() {
        CleanupApplication.getLoginComponent().inject(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        loginListContainer= (RelativeLayout) inflater.inflate(R.layout.fragment_login_list, container, false);
        ButterKnife.bind(this, loginListContainer);

        listAdapter = new LoginListAdapter(logins);
        loginListView.setAdapter(listAdapter);
        loginListView.setOnItemClickListener(itemListener );
        return loginListContainer;
    }


    public void showDialog(boolean isEdit, int pos) {
        dialogFragment = new LoginDialogFragment(isEdit, pos);
        dialogFragment.show(fragmentManager, dialogFragment.getClass().getSimpleName());
        dialogFragment.onComplete((login) -> {

            logins.add(login);
            login.setAlarm(ctx);
            logins.sort( (one, two) -> ((SouthwestLogin) one).getSouthwestLoginEvent().getFlightDate()
                    .compareTo(((SouthwestLogin)two).getSouthwestLoginEvent().getFlightDate()) );
            ((BaseAdapter)loginListView.getAdapter()).notifyDataSetChanged();
        });

    }

    AdapterView.OnItemClickListener itemListener= new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            showDialog(true, position);
        }
    };

}

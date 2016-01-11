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
import com.tolsma.ryan.airlinecheckin.model.SouthwestLogins;
import com.tolsma.ryan.airlinecheckin.services.SouthwestValidityRequest;

import javax.inject.Inject;

import butterknife.ButterKnife;


/**
 * A placeholder fragment containing a simple view.
 */
public class LoginListFragment extends Fragment {

    public static final String TAG=LoginListFragment.class.getSimpleName();
    // @Bind(R.id.fragment_login_list_view)
    public static ListView loginListView;
    static LoginListAdapter listAdapter;
    public RelativeLayout loginListContainer;
    @Inject
    SouthwestLogins logins;
    //@Inject @Named("activity")
    Context ctx;
    //@Inject
    FragmentManager fragmentManager;



    private LoginDialogFragment dialogFragment;
    AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            showDialog(true, position);
        }
    };

    public LoginListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        CleanupApplication.getAppComponent().inject(this);
        CleanupApplication.getActivityComponent().inject(this);

        fragmentManager = CleanupApplication.getActivityComponent().fragmentManager();
        ctx = CleanupApplication.getActivityComponent().mainActivity();
        //    CleanupApplication.getActivityComponent().inject(this);

        loginListContainer= (RelativeLayout) inflater.inflate(R.layout.fragment_login_list, container, false);
        ButterKnife.bind(this, loginListContainer);
        loginListView = (ListView) loginListContainer.findViewById(R.id.fragment_login_list_view);
        listAdapter = new LoginListAdapter(logins);
        loginListView.setAdapter(listAdapter);
        loginListView.setOnItemClickListener(itemListener);
        return loginListContainer;
    }

    public void showDialog(boolean isEdit, int pos) {
        dialogFragment = new LoginDialogFragment(isEdit, pos);
        dialogFragment.show(fragmentManager, dialogFragment.getClass().getSimpleName());
        dialogFragment.onComplete((login) -> {
            if (login != null) {
                logins.add(login);
                login.setAlarm(ctx);
                logins.sort((one, two) -> one.getLoginEvent().getFlightDate()
                        .compareTo(two.getLoginEvent().getFlightDate()));
            }

            ((BaseAdapter) loginListView.getAdapter()).notifyDataSetChanged();
            if (login != null)
                new Thread(new SouthwestValidityRequest(login)).start();


        });

    }

    public ListView getListView() {
        return loginListView;
    }

    public LoginListAdapter getLoginListAdapter() {
        return listAdapter;
    }

}

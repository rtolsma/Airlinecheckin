package com.tolsma.ryan.airlinecheckin;

import android.app.FragmentManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.tolsma.ryan.airlinecheckin.model.events.ToastEvent;
import com.tolsma.ryan.airlinecheckin.model.logins.SouthwestLogin;
import com.tolsma.ryan.airlinecheckin.model.logins.SouthwestLogins;
import com.tolsma.ryan.airlinecheckin.ui.ExtendedUI;
import com.tolsma.ryan.airlinecheckin.ui.LoginListFragment;
import com.tolsma.ryan.airlinecheckin.utils.RealmUtils;

import javax.inject.Inject;

import hugo.weaving.DebugLog;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements ExtendedUI {

    private static boolean isAlive = false;

    LoginListFragment loginListFragment;
    SettingsFragment settingsFragment;

    FragmentManager fm;

    @Inject
    Realm realm;
    @Inject
    Bus eventBus;

    SouthwestLogins logins;

    @DebugLog
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CleanupApplication.setActivityComponents(this);
        CleanupApplication.getAppComponent().inject(this);
        // CleanupApplication.getActivityComponent().inject(this);
        fm = CleanupApplication.getActivityComponent().fragmentManager();
        logins = CleanupApplication.getAppComponent().swLogins();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //On startup, open with the login list showing
        showLoginList();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                loginListFragment.showDialog(false, -1);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        isAlive = true;
        eventBus.register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        isAlive = false;
        eventBus.unregister(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            showSettingsFragment();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for (SouthwestLogin l : logins.getList()) {

            RealmUtils.saveToRealm(this, l.getLoginEvent());
        }

    }

    public void showSettingsFragment() {
        if (settingsFragment == null) settingsFragment = new SettingsFragment();

        fm.beginTransaction().replace(R.id.activity_main_fragment_container, settingsFragment, SettingsFragment.TAG)
                .addToBackStack(SettingsFragment.TAG).commit();

    }
    public void showLoginList() {
       if(loginListFragment==null)
        loginListFragment=new LoginListFragment();

        fm.beginTransaction().replace(R.id.activity_main_fragment_container, loginListFragment, LoginListFragment.TAG)
            .commit();
    }

    public LoginListFragment getLoginListFragment() {
        return loginListFragment;
    }

    @Override
    public String getTag() {
        return getClass().getSimpleName();
    }

    @Override
    public boolean isAlive() {
        return isAlive;
    }

    /*
    Below are some standard otto event subscriptions required for interacting with the UI
     */

    @Subscribe
    public void deliverToast(ToastEvent te) {
        //To be usable by background threads
        Handler h = new Handler(Looper.getMainLooper());
        h.post(() ->
                        Toast.makeText(this, te.getMessage(), te.getLength()).show()
        );
    }


}

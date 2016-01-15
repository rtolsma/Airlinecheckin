package com.tolsma.ryan.airlinecheckin;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by ryan on 1/13/16.
 */
public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.fragment_settings);
    }

}

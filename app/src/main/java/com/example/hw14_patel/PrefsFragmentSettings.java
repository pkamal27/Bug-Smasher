package com.example.hw14_patel;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import com.example.hw14_patel.R;


public class PrefsFragmentSettings extends PreferenceFragment  implements SharedPreferences.OnSharedPreferenceChangeListener {


    final static String TAG="PrefsFragmentSettings";

    public PrefsFragmentSettings (){}@Override
    public void onCreate (Bundle b) {
        super.onCreate(b);

        addPreferencesFromResource(R.xml.prefs_fragment_settings);

    }

    @Override
    public void onResume() {
        super.onResume();

        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        Preference pref = getPreferenceScreen().findPreference("highscore");

        String s=""+ Assets.highscore;
        pref.setSummary(s);

    }



    public void onSharedPreferenceChanged (SharedPreferences sharedPreferences, String key){


    }

}

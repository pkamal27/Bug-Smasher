package com.example.hw14_patel;

import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import com.example.hw14_patel.R;

import java.util.List;


public class PrefsActivity extends PreferenceActivity {

    public void onCreate(Bundle savedInstanceState){super.onCreate(savedInstanceState);}

    @Override
    protected boolean isValidFragment (String fragmentName) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB)
            return true;
        else if (PrefsFragmentSettings.class.getName().equals(fragmentName))
            return true;
        return false;
    }

    @Override
    public void onBuildHeaders (List<Header> target) {

        getFragmentManager().beginTransaction().replace(android.R.id.content,new PrefsFragmentSettings()).commit();
    }
}
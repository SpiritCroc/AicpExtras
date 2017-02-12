package com.lordclockan.aicpextras;

import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.support.v4.app.Fragment;

//import android.os.SystemProperties;

public class StatusBarFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().getFragmentManager().beginTransaction()
                .replace(R.id.content_main, new SettingsPreferenceFragment())
                .commit();
    }

    public static class SettingsPreferenceFragment extends PreferenceFragment {

        public SettingsPreferenceFragment() {
        }

        private String PREF_TRAFFIC = "traffic";
        private String PREF_CARRIE_LABEL = "carrierlabel";
        private String PREF_BATTERY_BAR = "batterybar";
        private String PREF_STATUSBAR_WEATHER = "statusbar_weather";

        private Preference mTraffic;
        private Preference mCarrierLabel;
        private Preference mBatteryBar;
        private Preference mStatusbarWeather;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.statusbar_layout);

            PreferenceScreen prefSet = getPreferenceScreen();
            ContentResolver resolver = getActivity().getContentResolver();

            mTraffic = prefSet.findPreference(PREF_TRAFFIC);
            mCarrierLabel = prefSet.findPreference(PREF_CARRIE_LABEL);
            mBatteryBar = prefSet.findPreference(PREF_BATTERY_BAR);
            mStatusbarWeather = prefSet.findPreference(PREF_STATUSBAR_WEATHER);
        }

        @Override
        public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
            if (preference == mTraffic) {
                //Intent intent = new Intent(getActivity(), Traffic.class);
                //getActivity().startActivity(intent);
            } else {
                return super.onPreferenceTreeClick(preferenceScreen, preference);
            }
            return false;
        }

        @Override
        public void onResume() {
            super.onResume();
        }
    }
}

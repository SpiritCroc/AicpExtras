package com.lordclockan.aicpextras;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.support.v4.app.Fragment;
import android.widget.Toast;

public class DisplayAnimationsActivity extends Fragment {

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

            private String TEST_CHECK = "test_check";

            private SwitchPreference mTestCheck;



            @Override
            public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);

                // Load the preferences from an XML resource
                addPreferencesFromResource(R.xml.display_anim_layout);

                PreferenceScreen prefSet = getPreferenceScreen();
                Activity activity = getActivity();

                final ContentResolver resolver = getActivity().getContentResolver();

                mTestCheck = (SwitchPreference) findPreference(TEST_CHECK);

                addListenerOnSwitch();


            }

            public void addListenerOnSwitch() {
                mTestCheck.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        if (preference == mTestCheck) {
                            boolean value = (Boolean) newValue;
                            if (value) {
                                Toast.makeText(getActivity(), "CheckBox is ON", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "CheckBox is OFF", Toast.LENGTH_SHORT).show();
                            }
                            return true;
                        }
                        return false;
                    }
                });
            }
        }
    }
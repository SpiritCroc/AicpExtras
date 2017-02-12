/*
 * Copyright (C) 2016 AICP
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lordclockan.aicpextras;

import android.app.Activity;
import android.content.ContentResolver;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;


import com.lordclockan.aicpextras.widget.SeekBarPreferenceCham;

public class SettingsActivity extends SubActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        //getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new SettingsActivityFragment()).commit();
    }

    public static class SettingsActivityFragment extends PreferenceFragment
            implements OnPreferenceChangeListener {

        private static final String TAG = "SettingsActivity";

        private static final String PREF_AE_NAV_DRAWER_OPACITY = "ae_drawer_opacity";
        private static final String PREF_AE_NAV_DRAWER_BG_COLOR = "ae_drawer_bg_color";
        private static final String PREF_AE_SETTINGS_RESTORE_DEFAULTS = "ae_settings_restore_defaults";

        private static final int DEFAULT_NAV_HEADER_COLOR = 0xFF303030;

        private SeekBarPreferenceCham mNavDrawerOpacity;
        private Preference mAeRestoreDefaults;

        ViewGroup viewGroup;

        @Override
        public void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            // Make this activity, full screen
           /* getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);*/

            // Hide the Title bar of this activity screen



            addPreferencesFromResource(R.xml.about_layout);

            viewGroup = (ViewGroup) ((ViewGroup) getActivity()
                    .findViewById(android.R.id.content)).getChildAt(0);

            PreferenceScreen prefSet = getPreferenceScreen();
            ContentResolver resolver = getActivity().getContentResolver();


            int intColor;
            String hexColor;


            mAeRestoreDefaults = prefSet.findPreference(PREF_AE_SETTINGS_RESTORE_DEFAULTS);
        }

        public boolean onPreferenceChange(Preference preference, Object newValue) {
            ContentResolver resolver = getActivity().getContentResolver();
            return false;
        }

        @Override
        public void onResume() {
            super.onResume();
        }

    }
}

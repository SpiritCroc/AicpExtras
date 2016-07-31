package com.lordclockan.aicpextras;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

//import android.os.SystemProperties;

public class AboutFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().getFragmentManager().beginTransaction()
                .replace(R.id.content_main, new SettingsPreferenceFragment())
                .commit();
    }

    public static class SettingsPreferenceFragment extends PreferenceFragment {

        private static final String TAG_FRAGMENT = "TAG";

        private String PREF_GCOMMUNITY = "gplus";
        private String PREF_AICP_DOWNLOADS = "aicp_downloads";
        private String PREF_AICP_GERRIT = "aicp_gerrit";
        private String PREF_AICP_CHANGELOG = "aicp_changelog";
        private String PREF_AICP_LOGCAT = "aicp_logcat";
        private static final String LOGCAT_FILE = new File(Environment
                .getExternalStorageDirectory(), "aicp_logcat.txt").getAbsolutePath();
        private static final String HASTE_KEY = new File(Environment
                .getExternalStorageDirectory(), "haste_key").getAbsolutePath();
        private static final String AICP_HASTE = "http://haste.aicp-rom.com/documents";
        private static final File sdCardDirectory = Environment.getExternalStorageDirectory();
        private static final File logcatFile = new File(sdCardDirectory, "aicp_logcat.txt");
        private static final File hasteKey = new File(sdCardDirectory, "haste_key");

        private String mDeviceName;

        private Preference mGcommunity;
        private Preference mAicpDownloads;
        private Preference mAicpGerrit;
        private Preference mAicpChangeLog;
        private Preference mAicpLogcat;

        public SettingsPreferenceFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.about_layout);

            PreferenceScreen prefSet = getPreferenceScreen();
            Activity activity = getActivity();

            final ContentResolver resolver = getActivity().getContentResolver();

            mGcommunity = prefSet.findPreference(PREF_GCOMMUNITY);
            mAicpDownloads = prefSet.findPreference(PREF_AICP_DOWNLOADS);
            mAicpGerrit = prefSet.findPreference(PREF_AICP_GERRIT);
            mAicpChangeLog = prefSet.findPreference(PREF_AICP_CHANGELOG);
            mAicpLogcat = prefSet.findPreference(PREF_AICP_LOGCAT);

        }

        @Override
        public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
            if (preference == mGcommunity) {
                String url = "https://plus.google.com/communities/101008638920580274588";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            } else if (preference == mAicpDownloads) {
                //getDeviceName();
                //String url = "http://dwnld.aicp-rom.com/?device=" + mDeviceName;
                String url = "http://dwnld.aicp-rom.com";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            } else if (preference == mAicpGerrit) {
                String url = "http://gerrit.aicp-rom.com/#/q/status:open";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            } else if (preference == mAicpChangeLog) {
                Intent intent = new Intent(getActivity(), ChangeLogActivity.class);
                getActivity().startActivity(intent);
            } else if (preference == mAicpLogcat) {
               // readHasteKey(getContext(), HASTE_KEY);
                logcatDialog();
            } else {
                return super.onPreferenceTreeClick(preferenceScreen, preference);
            }

            return false;
        }

        public void logcatDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.logcat_title);
            builder.setMessage(R.string.logcat_warning);

            builder.setPositiveButton(R.string.make_logcat, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                        //sharingIntent.setType("text/plain");
                       // String shareBody = readHasteKey(getContext(), HASTE_KEY);
                        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                    try {
                        sharingIntent.putExtra(Intent.EXTRA_TEXT, readHasteKey());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(logcat));
                        startActivity(Intent.createChooser(sharingIntent, "Share via"));
                    /*String filelocation= Environment.getExternalStorageDirectory() + "/aicp_logcat.txt";
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    //emailIntent.setType("vnd.android.cursor.dir/email");
                    String to[] = {"lordclockan@gmail.com"};
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
                    emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(logcat));
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                    emailIntent.setType("text/plain");
                    startActivity(Intent.createChooser(emailIntent , "Send email..."));*/
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

        public static String readHasteKey() throws IOException {
                FileReader fileReader = new FileReader(hasteKey);
                StringBuffer stringBuffer = new StringBuffer();
                int numCharsRead;
                char[] charArray = new char[1024];
                while ((numCharsRead = fileReader.read(charArray)) > 0) {
                    stringBuffer.append(charArray, 0, numCharsRead);
                }
                fileReader.close();
            return stringBuffer.toString();
        }

       /* private void showSnacbBar() {

            Snackbar firstWarning = Snackbar
                    .make(view, "Stock recents disabled!!!", Snackbar.LENGTH_LONG)
                    .setAction("What now?", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Snackbar secondWarning = Snackbar
                                    .make(view, "This will take you to MultiShit", Snackbar.LENGTH_LONG)
                                    .setAction("Tap", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent = new Intent(getActivity(), MainActivity.class);
                                            getActivity().startActivity(intent);
                                        }
                                    });
                            secondWarning.show();

                        }
                    });

            firstWarning.setActionTextColor(Color.RED);

            View sbView = firstWarning.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);

            firstWarning.show();
        }*/

        /*public void getDeviceName() {
            mDeviceName = SystemProperties.get("ro.product.device");
        }*/
    }
}

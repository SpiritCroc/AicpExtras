package com.lordclockan.aicpextras;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String NAV_ITEM_ID = "navItemId";
    private static final String TAG = "AicpExtras";

    private DrawerLayout mDrawer;
    private int id;
    private ImageView mNavHeaderLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeRed);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.content_main, new AboutFragment());
        tx.commit();

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent emailIntent =
                        new Intent(Intent.ACTION_SEND);
                String[] recipients = new String[]{"davor@losinj.com", "",};
                emailIntent.putExtra(Intent.EXTRA_EMAIL, recipients);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "AICP talk");
                emailIntent.setType("text/plain");
                startActivity(Intent.createChooser(emailIntent, getString(R.string.send_mail_intent)));
                finish();
            }
        });*/



        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getBackground().setAlpha(178);
        navigationView.setBackgroundColor(getResources().getColor(R.color.navDrawerBg, getTheme()));


        //navigationView.getHeaderView(0).findViewById(R.id.nav_header_layout).getBackground().setAlpha(50);

        String checkedColor = "#" + Settings.System.getInt(this.getApplicationContext().getContentResolver(),
                Settings.System.END_BUTTON_BEHAVIOR, 3);

        Log.v(TAG, checkedColor);

        // FOR NAVIGATION VIEW ITEM TEXT COLOR
        int[][] states = new int[][]{
                new int[]{-android.R.attr.state_checked},  // unchecked
                new int[]{android.R.attr.state_checked},   // checked
                new int[]{}                                // default
        };

        // Fill in color corresponding to state defined in state
        int[] colors = new int[]{
                Color.parseColor("#" + Integer.toHexString(getResources().getColor(R.color.navDrawerText, null))),
                Color.parseColor("#" + Integer.toHexString(getResources().getColor(R.color.navDrawerTextChecked, null))),
                Color.parseColor("#" + Integer.toHexString(getResources().getColor(R.color.navDrawerText, null))),
        };

        ColorStateList navigationViewColorStateList = new ColorStateList(states, colors);

        // apply to text color
        navigationView.setItemTextColor(navigationViewColorStateList);
        // apply to icon color
        navigationView.setItemIconTintList(navigationViewColorStateList);

        int colorAccent = getResources().getColor(R.color.colorAccent, null);

        navigationView.getHeaderView(0).findViewById(R.id.nav_header_layout).getBackground().setColorFilter(0xff456333, PorterDuff.Mode.SRC_ATOP);

        /*if (navigationView != null) {
            mNavHeaderLogo.setBackgroundColor(colorAccent);
        }*/


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            Intent intent = new Intent(this, SettingsActivity.class);
            this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        // update highlighted item in the navigation menu
        item.setChecked(true);
        id = item.getItemId();
        Fragment fragment = null;

        Class fragmentClass;

        switch (id) {
            case R.id.nav_display_animations:
                fragmentClass = DisplayAnimationsActivity.class;
                break;
            case R.id.nav_headsup:
                fragmentClass = HeadsUpFragment.class;
                break;
            case R.id.nav_notif_drawer:
                fragmentClass = NotificationsFragment.class;
                break;
            case R.id.nav_recents:
                fragmentClass = RecentsPanelFragment.class;
                break;
            case R.id.nav_statusbar:
                fragmentClass = StatusBarFragment.class;
                break;
            case R.id.nav_various:
                fragmentClass = VariousShitFragment.class;
                break;
            case R.id.nav_about:
                fragmentClass = AboutFragment.class;
                break;
            default:
                fragmentClass = AboutFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        RelativeLayout contentMain = (RelativeLayout) findViewById(R.id.content_main);
        contentMain.removeAllViewsInLayout();
        contentMain.invalidate();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();

        // Highlight the selected item, update the title, and close the drawer
        item.setChecked(true);
        setTitle(item.getTitle());
        mDrawer.closeDrawers();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(NAV_ITEM_ID, id);
    }
}
package com.example.carpark.views;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.carpark.MyVehicleFragment;
import com.example.carpark.ParkingHistoryFragment;
import com.example.carpark.R;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {

    //widgets
    private DrawerLayout mDrawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private boolean mToolBarNavigationListenerIsRegistered = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViews();
        setUpDefaultFragment();
        navigationClickListeners();
    }

    private void initViews() {
        toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        navigationView = findViewById(R.id.navigation_view);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void setUpDefaultFragment() {
        DefaultFragment defaultFragment = new DefaultFragment();
        setUpFragment(defaultFragment);
    }



    private void navigationClickListeners() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {

                    case R.id.nav_notification:
                        fragment = null;
                        break;

                    case R.id.nav_parking_history:
                        fragment = new ParkingHistoryFragment();
                        break;

                    case R.id.nav_pay:
                        fragment = new PaymentMethodsFragment();
                        break;

                    case R.id.nav_prom:
                        fragment = new PromotionFragment();
                        break;

                    case R.id.nav_car:
                        fragment = new MyVehicleFragment();
                        break;

                    case R.id.nav_settings:
                        fragment = new SettingsFragment();
                        break;


                }
                mDrawerLayout.closeDrawer(GravityCompat.START);
                navigationView.setCheckedItem(item);
                if (fragment != null) {
                    setUpFragment(fragment);
                    enableBackViews(true);
                }
                return true;
            }
        });

    }

    private void setUpFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, fragment).commit();

    }


    @Override
    public void onBackPressed() {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.home_frame);
        if(f instanceof DefaultFragment ){
            finish();
        }
        else {
            setUpDefaultFragment();
            enableBackViews(false);
        }
    }

    private void enableBackViews(boolean enable) {

        if (enable) {
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            toggle.setDrawerIndicatorEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            if (!mToolBarNavigationListenerIsRegistered) {
                toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });

                mToolBarNavigationListenerIsRegistered = true;
            }

        } else {
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            toggle.setDrawerIndicatorEnabled(true);
            toggle.setToolbarNavigationClickListener(null);
            mToolBarNavigationListenerIsRegistered = false;
        }
    }

}

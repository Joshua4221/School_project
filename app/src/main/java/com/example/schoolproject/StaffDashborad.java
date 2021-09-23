package com.example.schoolproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.schoolproject.MainFragment.Chat_Fragment;
import com.example.schoolproject.MainFragment.FeatureFragment;
import com.example.schoolproject.MainFragment.HomeFragment;
import com.example.schoolproject.MainFragment.ProfileFragment;
import com.example.schoolproject.MainFragment.RecordFragment;
import com.example.schoolproject.MainFragment.SettingFragment;
import com.example.schoolproject.MainFragment.StatusFragment;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

public class StaffDashborad extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_dashborad);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_views);

        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contatiner,
                    new HomeFragment()).commit();

            navigationView.setCheckedItem(R.id.nav_home);
        }

    }

    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contatiner,
                        new HomeFragment()).commit();
                break;
            case R.id.nav_chat:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contatiner,
                        new Chat_Fragment()).commit();
                break;
            case R.id.nav_status:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contatiner,
                        new StatusFragment()).commit();
                break;
            case R.id.nav_record:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contatiner,
                        new RecordFragment()).commit();
                break;
            case R.id.sport_features:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contatiner,
                        new FeatureFragment()).commit();
                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contatiner,
                        new ProfileFragment()).commit();
                break;
            case R.id.nav_setting:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contatiner,
                        new SettingFragment()).commit();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
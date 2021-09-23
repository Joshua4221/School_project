package com.example.schoolproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.FirebaseApp;


public class RegisterActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        FirebaseApp.initializeApp(RegisterActivity.this);



        TabLayout studstaff = findViewById(R.id.StudStaff);
        ViewPager viewPager = findViewById(R.id.viewpager);

        GenderAdapter genderAdapter = new GenderAdapter(getSupportFragmentManager(),
                studstaff.getTabCount());
        viewPager.setAdapter(genderAdapter);

        studstaff.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition() == 0) {
                    genderAdapter.notifyDataSetChanged();
                }else if(tab.getPosition() == 1) {
                    genderAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(studstaff));


    }
}
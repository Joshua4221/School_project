package com.example.schoolproject.Chat.FootBall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.schoolproject.Chat.FootBall.F_SAAT.SAATFOOTBALLActivity;
import com.example.schoolproject.Chat.FootBall.F_SEET.SEETFOOTBALLActivity;
import com.example.schoolproject.Chat.FootBall.F_SICT.SICTFOOTBALLActivity;
import com.example.schoolproject.Chat.FootBall.F_SOBS.SOBSFOOTBALLActivity;
import com.example.schoolproject.Chat.FootBall.F_SOESET.SOESETFOOTBALLActivity;
import com.example.schoolproject.Chat.FootBall.F_SOET.SOETFOOTBALLActivity;
import com.example.schoolproject.Chat.FootBall.F_SOHT.SOHTFOOTBALLActivity;
import com.example.schoolproject.Chat.FootBall.F_SOPS.SOPSFOOTBALLActivity;
import com.example.schoolproject.R;

public class FootballActivity extends AppCompatActivity {

    TextView saat, seet, sict, sobs, soeset, soet, soht, sops;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_football);

        saat = findViewById(R.id.saat);
        seet = findViewById(R.id.seet);
        sict = findViewById(R.id.sict);
        sobs = findViewById(R.id.sobs);
        soeset = findViewById(R.id.soeset);
        soet = findViewById(R.id.soet);
        soht = findViewById(R.id.soht);
        sops = findViewById(R.id.sops);

        saat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FootballActivity.this, SAATFOOTBALLActivity.class);
                startActivity(intent);
            }
        });

        seet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FootballActivity.this, SEETFOOTBALLActivity.class);
                startActivity(intent);
            }
        });

        sict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FootballActivity.this, SICTFOOTBALLActivity.class);
                startActivity(intent);
            }
        });

        sobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FootballActivity.this, SOBSFOOTBALLActivity.class);
                startActivity(intent);
            }
        });

        soeset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FootballActivity.this, SOESETFOOTBALLActivity.class);
                startActivity(intent);
            }
        });

        soet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FootballActivity.this, SOETFOOTBALLActivity.class);
                startActivity(intent);
            }
        });

        soht.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FootballActivity.this, SOHTFOOTBALLActivity.class);
                startActivity(intent);
            }
        });

        sops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FootballActivity.this, SOPSFOOTBALLActivity.class);
                startActivity(intent);
            }
        });


    }
}
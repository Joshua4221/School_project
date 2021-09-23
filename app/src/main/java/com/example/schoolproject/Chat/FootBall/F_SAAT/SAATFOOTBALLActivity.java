package com.example.schoolproject.Chat.FootBall.F_SAAT;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.schoolproject.Chat.FootBall.F_SAAT.Departments.AgriculturalEconomics;
import com.example.schoolproject.Chat.FootBall.F_SAAT.Departments.AgriculturalExtension;
import com.example.schoolproject.R;

public class SAATFOOTBALLActivity extends AppCompatActivity {

    TextView AgriculturalEconomic, AgriculturalExtension, AnimalScience, CropScience,
            FisheryAquaculture, ForestryWildlife, SoilScience;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saatfootballactivity);

        AgriculturalEconomic = findViewById(R.id.AgriculturalEconomics);
        AgriculturalExtension = findViewById(R.id.AgriculturalExtension);
        AnimalScience = findViewById(R.id.AnimalScience);
        CropScience = findViewById(R.id.CropScience);
        FisheryAquaculture = findViewById(R.id.FisheryAquaculture);
        ForestryWildlife = findViewById(R.id.ForestryWildlife);
        SoilScience = findViewById(R.id.SoilScience);

        AgriculturalEconomic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SAATFOOTBALLActivity.this, AgriculturalEconomics.class));
            }
        });

        AgriculturalExtension.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SAATFOOTBALLActivity.this, AgriculturalExtension.class);
                startActivity(intent);
            }
        });

//        AnimalScience.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(FootballActivity.this, SICTFOOTBALLActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        CropScience.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(FootballActivity.this, SOBSFOOTBALLActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        FisheryAquaculture.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(FootballActivity.this, SOESETFOOTBALLActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        ForestryWildlife.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(FootballActivity.this, SOETFOOTBALLActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        SoilScience.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(FootballActivity.this, SOHTFOOTBALLActivity.class);
//                startActivity(intent);
//            }
//        });

//        sops.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(FootballActivity.this, SOPSFOOTBALLActivity.class);
//                startActivity(intent);
//            }
//        });

    }
}
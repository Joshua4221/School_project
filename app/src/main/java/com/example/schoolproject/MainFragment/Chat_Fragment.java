package com.example.schoolproject.MainFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.schoolproject.Chat.BasketBall.BasketBAllActivity;
import com.example.schoolproject.Chat.FootBall.FootballActivity;
import com.example.schoolproject.Chat.TennisActivity;
import com.example.schoolproject.R;

public class Chat_Fragment extends Fragment {

    TextView football, basketball, tennisBall;

    public Chat_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_, container, false);

        football = view.findViewById(R.id.footblall);
        basketball = view.findViewById(R.id.basketball);
        tennisBall = view.findViewById(R.id.tennisBall);

        football.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), FootballActivity.class));
            }
        });

        basketball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), BasketBAllActivity.class));
            }
        });

        tennisBall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TennisActivity.class));
            }
        });

        return view;
    }
}
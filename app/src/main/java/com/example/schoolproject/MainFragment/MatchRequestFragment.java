package com.example.schoolproject.MainFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.schoolproject.Adapter.CaptainAdapter;
import com.example.schoolproject.Adapter.UserAdapter;
import com.example.schoolproject.Chat.FootBall.F_SAAT.Departments.AgriculturalEconomics;
import com.example.schoolproject.Model.User;
import com.example.schoolproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class MatchRequestFragment extends Fragment {

    RecyclerView recyclerView;

    CaptainAdapter userAdapter;
    List<User> usersList;
    DatabaseReference reference;
    FirebaseDatabase database;

    FirebaseAuth firebaseAuth;



    public MatchRequestFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_match_request, container, false);

        recyclerView = view.findViewById(R.id.user_recycleView);

        firebaseAuth = FirebaseAuth.getInstance();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        usersList = new ArrayList<>();

        getAgriculturalEconomicsUsers();

        return view;
    }

    private void getAgriculturalEconomicsUsers() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Student_User");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                usersList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    User users = dataSnapshot.getValue(User.class);
                    String depart = users.getDepartment();
                    String Agricultural_Economics = "Agricultural Economics";

                    assert users != null;
                    if (!users.getId().equals(user.getUid())){
//                        if(Agricultural_Economics.equals(depart)){
                            usersList.add(users);
//                        }
                    }
                }

                userAdapter = new CaptainAdapter(getActivity(), usersList);

                userAdapter.notifyDataSetChanged();

                recyclerView.setAdapter(userAdapter);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


    }
}
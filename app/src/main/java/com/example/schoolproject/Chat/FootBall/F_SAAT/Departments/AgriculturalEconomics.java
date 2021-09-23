package com.example.schoolproject.Chat.FootBall.F_SAAT.Departments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.schoolproject.Adapter.UserAdapter;
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

public class AgriculturalEconomics extends AppCompatActivity {

    RecyclerView recyclerView;

    UserAdapter userAdapter;
    List<User> usersList;
    DatabaseReference reference;
    FirebaseDatabase database;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agricultural_economics);

        recyclerView = findViewById(R.id.user_recycleView);

        firebaseAuth = FirebaseAuth.getInstance();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(AgriculturalEconomics.this));

        usersList = new ArrayList<>();

        getAgriculturalEconomicsUsers();
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
                    String department = "Agricultural Economics";

                    assert users != null;
                    if (!users.getId().equals(user.getUid())){
                        if(!depart.equals(department)){
                            usersList.add(users);
                        }
                    }
                }

                userAdapter = new UserAdapter(AgriculturalEconomics.this, usersList);

                userAdapter.notifyDataSetChanged();

                recyclerView.setAdapter(userAdapter);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


    }
}
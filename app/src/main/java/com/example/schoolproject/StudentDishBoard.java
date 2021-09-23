package com.example.schoolproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolproject.MainFragment.Chat_Fragment;
import com.example.schoolproject.MainFragment.HomeFragment;
import com.example.schoolproject.MainFragment.ProfileFragment;
import com.example.schoolproject.MainFragment.RecordFragment;
import com.example.schoolproject.MainFragment.SettingFragment;
import com.example.schoolproject.MainFragment.StatusFragment;
import com.example.schoolproject.Model.User;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.EventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentDishBoard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    FirebaseUser firebaseUser;
    FirebaseDatabase database;
    DatabaseReference reference;

    CircleImageView myImage;
    TextView first_name, last_name, name;

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dishboard);

        Toolbar toolbar = findViewById(R.id.toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        name = findViewById(R.id.name);


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        NavigationView navigationView = findViewById(R.id.nav_views);
        View headerView = navigationView.getHeaderView(0);
        first_name = headerView.findViewById(R.id.first_Name);
        last_name = headerView.findViewById(R.id.last_Name);
        myImage = headerView.findViewById(R.id.myImage);


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


        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Student_User").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

//                String first = "" + snapshot.child("First_Name").getValue();
//                name.setText(first);
//                first_name.setText(first);

                    User user = dataSnapshot.getValue(User.class);
                    assert user != null;
                    name.setText(user.getFirst_Name());
                    first_name.setText(user.getFirst_Name());
                    last_name.setText(user.getLast_Name());

                    try {
                        Picasso.get().load(user.getImageURL()).placeholder(R.mipmap.ic_launcher).into(myImage);
                    } catch (Exception e) {
                        Picasso.get().load(R.mipmap.ic_launcher).into(myImage);
                    }


            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

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
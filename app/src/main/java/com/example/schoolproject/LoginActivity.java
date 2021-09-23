  package com.example.schoolproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolproject.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class LoginActivity extends AppCompatActivity {

    TextView signUp;
    EditText StaffName, email, password, post;
    Button signIn;
    ArrayAdapter<String> directorsAdapter;
    FirebaseAuth auth;

    ProgressDialog progressDialog;
    String captain = "Captain of my level";

    FirebaseUser firebaseUser;
    DatabaseReference reference;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseApp.initializeApp(LoginActivity.this);

        signUp = findViewById(R.id.signUp);
        email = findViewById(R.id.Email);
        password = findViewById(R.id.Password);
        signIn = findViewById(R.id.signIn);
        post = findViewById(R.id.Post);
        StaffName = findViewById(R.id.StaffName);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });




        auth = FirebaseAuth.getInstance();


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Login User...");

        firebaseUser = auth.getCurrentUser();

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String posts = post.getText().toString();
                String staff = StaffName.getText().toString();
                String email_txt = email.getText().toString();
                String password_txt = password.getText().toString();

                if (TextUtils.isEmpty(email_txt) || TextUtils.isEmpty(password_txt)){
                    Toast.makeText(LoginActivity.this, "All Fields are Required", Toast.LENGTH_SHORT).show();
                }
                else if (!TextUtils.isEmpty(posts) && !TextUtils.isEmpty(email_txt) && !TextUtils.isEmpty(password_txt)){
                    progressDialog.show();
                    auth.signInWithEmailAndPassword(email_txt, password_txt)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<AuthResult> task) {

                                    if(task.isSuccessful()){
                                        progressDialog.dismiss();

                                        database = FirebaseDatabase.getInstance();
                                        reference = database.getReference("Student_User").child(firebaseUser.getUid());

                                        reference.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

//                                                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

//                                                    String Captain = "" + dataSnapshot.child("Post").getValue();

//                                                    if(captain.equals(Captain)){
//                                                        Intent intent = new Intent(LoginActivity.this, CaptainActivity.class);
//                                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                                        startActivity(intent);
//                                                    } else {
                                                        Intent intent = new Intent(LoginActivity.this, DirectorsDashboard.class);
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                        startActivity(intent);
//                                                    }

//                                                }

                                            }

                                            @Override
                                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                            }
                                        });


                                    } else {
                                        progressDialog.dismiss();
                                        Toast.makeText(LoginActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        };
                    });
                }

                else if (!TextUtils.isEmpty(staff) && !TextUtils.isEmpty(email_txt) && !TextUtils.isEmpty(password_txt)){
                    progressDialog.show();
                    auth.signInWithEmailAndPassword(email_txt, password_txt)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<AuthResult> task) {

                                    if(task.isSuccessful()){


                                        database = FirebaseDatabase.getInstance();
                                        reference = database.getReference("Staff_User").child(firebaseUser.getUid());

                                        reference.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                                    User user = dataSnapshot.getValue(User.class);

                                                    assert user != null;
                                                    if(staff.equals(user.getFirst_Name())){
                                                        Intent intent = new Intent(LoginActivity.this, StaffDashborad.class);
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                        startActivity(intent);
                                                    }
                                                    progressDialog.dismiss();
                                                    Toast.makeText(LoginActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                            }
                                        });

                                    } else {
                                        progressDialog.dismiss();
                                        Toast.makeText(LoginActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        };
                    });
                }
                else {
                    database = FirebaseDatabase.getInstance();
                    loginUser(email_txt,password_txt);
                }

            }
        });

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void loginUser(String email, String password){
        progressDialog.show();
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            Intent intent = new Intent(LoginActivity.this,StudentDishBoard.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            };
        });
    }
}
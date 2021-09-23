package com.example.schoolproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class StudentFragment extends Fragment {


    RadioGroup gender;
    RadioButton gend;
    AutoCompleteTextView Level, Faculty, Department, post, sport;
    EditText firstName, lastName, regNo, email,password, comfirmPassword;
    TextView signIn;
    Button signUp;
    ArrayAdapter<String> DepartmentAdapter, levelAdapter, facultyAdapter, departmentAdapter, directorsAdapter, sportsAdapter;

    FirebaseAuth auth;
    FirebaseFirestore db;
    FirebaseDatabase database;
    DatabaseReference reference;

    ProgressDialog progressDialog;

    public StudentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_student, container, false);

        FirebaseApp.initializeApp(getActivity());

        gender = (RadioGroup) view.findViewById(R.id.gender);
        Level = view.findViewById(R.id.Level);
        Faculty = view.findViewById(R.id.Faculty);
        Department = view.findViewById(R.id.Department);
        firstName = view.findViewById(R.id.FirstName);
        lastName = view.findViewById(R.id.LastName);
        regNo = view.findViewById(R.id.RegNo);
        email = view.findViewById(R.id.Email);
        password = view.findViewById(R.id.Password);
        comfirmPassword = view.findViewById(R.id.ConfirmPassword);
        signUp = view.findViewById(R.id.signUp);
        post = view.findViewById(R.id.Post);
        sport = view.findViewById(R.id.Sport);
        signIn = view.findViewById(R.id.signIn);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Registering User...");

        auth = FirebaseAuth.getInstance();


        String[] levelOption = getResources().getStringArray(R.array.level);
        String[] facultyOption = getResources().getStringArray(R.array.faculty);
        String[] DepartmentOption = getResources().getStringArray(R.array.department);
        String[] DirectorOption = getResources().getStringArray(R.array.directors);
        String[] SportOption = getResources().getStringArray(R.array.sports);


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
            }
        });

        levelAdapter = new ArrayAdapter<>(getActivity(), R.layout.dropdown_file, levelOption);
        Level.setText(levelAdapter.getItem(0).toString(),false);
        Level.setAdapter(levelAdapter);

        facultyAdapter = new ArrayAdapter<>(getActivity(), R.layout.dropdown_file, facultyOption);
        Faculty.setText(facultyAdapter.getItem(0).toString(),false);
        Faculty.setAdapter(facultyAdapter);

        departmentAdapter = new ArrayAdapter<>(getActivity(), R.layout.dropdown_file, DepartmentOption);
        Department.setText(departmentAdapter.getItem(0).toString(),false);
        Department.setAdapter(departmentAdapter);

        directorsAdapter = new ArrayAdapter<>(getActivity(), R.layout.dropdown_file, DirectorOption);
        post.setText(directorsAdapter.getItem(0).toString(),false);
        post.setAdapter(directorsAdapter);

        sportsAdapter = new ArrayAdapter<>(getActivity(), R.layout.dropdown_file, SportOption);
        sport.setText(sportsAdapter.getItem(0).toString(),false);
        sport.setAdapter(sportsAdapter);

        int gender_main = gender.getCheckedRadioButtonId();
        gend = view.findViewById(gender_main);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String gender_txt = gend.getText().toString();
                String firstName_txt = firstName.getText().toString();
                String lastName_txt = lastName.getText().toString();
                String regNo_txt = regNo.getText().toString();
                String level_txt = Level.getText().toString();
                String email_txt = email.getText().toString();
                String password_txt = password.getText().toString();
                String confirmpassword_txt = comfirmPassword.getText().toString();
                String faculty_txt = Faculty.getText().toString();
                String department_txt = Department.getText().toString();
                String post_txt = post.getText().toString();
                String sport_txt = sport.getText().toString();

                if (TextUtils.isEmpty(firstName_txt) || TextUtils.isEmpty(lastName_txt)
                        ||  TextUtils.isEmpty(level_txt) ||  TextUtils.isEmpty(email_txt) || TextUtils.isEmpty(password_txt)  || TextUtils.isEmpty(confirmpassword_txt)
                        ||  TextUtils.isEmpty(faculty_txt) || TextUtils.isEmpty(department_txt)
                        ||  TextUtils.isEmpty(post_txt) || TextUtils.isEmpty(sport_txt)){
                    Toast.makeText(getActivity(), "All Fields are Required", Toast.LENGTH_SHORT).show();
                } else if(!Patterns.EMAIL_ADDRESS.matcher(email_txt).matches()){
                    email.setError("Invalid Email");
                    email.setFocusable(true);
                } else if(!password_txt.equals(confirmpassword_txt)){
                    Toast.makeText(getActivity(), "password does not match", Toast.LENGTH_SHORT).show();
                } else if(password_txt.length() < 6){
                    password.setError("Password length must be at least 6 character");
                    password.setFocusable(true);
                }  else {

                    register(gender_txt, firstName_txt, lastName_txt, regNo_txt, level_txt,email_txt, password_txt,faculty_txt,
                            department_txt,post_txt,sport_txt);

                }
            }
        });

        return view;
    }

    private void register(String gender, String firstName,String lastName, String regNO, String level, String email, String password, String faculty,
                          String department, String post, String sport){

        progressDialog.show();

        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            assert firebaseUser != null;
                            String userId = firebaseUser.getUid();


                            HashMap<String, String> myUser = new HashMap<>();
                            myUser.put("id", userId);
                            myUser.put("gender", gender);
                            myUser.put("first_Name", firstName);
                            myUser.put("last_Name", lastName);
                            myUser.put("regNo", regNO);
                            myUser.put("level", level);
                            myUser.put("email", email);
                            myUser.put("faculty", faculty);
                            myUser.put("department", department);
                            myUser.put("post", post);
                            myUser.put("sports", sport);
                            myUser.put("imageURL", "default");

                            database = FirebaseDatabase.getInstance();
                            reference = database.getReference("Student_User");

                            reference.child(userId).setValue(myUser);

                        } else {
                            // If sign in fails, display a message to the user.
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {

                progressDialog.dismiss();
                Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

}
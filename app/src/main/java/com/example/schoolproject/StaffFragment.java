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

public class StaffFragment extends Fragment {

    EditText firstName, lastName, email, password, comfirmPassword;
    RadioGroup gender;
    RadioButton gend;
    TextView signInto;
    Button signUp;

    FirebaseAuth auth;
    FirebaseFirestore db;
    FirebaseDatabase database;
    DatabaseReference reference;

    ProgressDialog progressDialog;


    public StaffFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_staff, container, false);

        FirebaseApp.initializeApp(getActivity());

        gender = (RadioGroup) view.findViewById(R.id.gender);
        signInto = view.findViewById(R.id.signInto);
        firstName = view.findViewById(R.id.FirstName);
        lastName = view.findViewById(R.id.LastName);
        email = view.findViewById(R.id.Email);
        password = view.findViewById(R.id.Password);
        comfirmPassword = view.findViewById(R.id.ConfirmPassword);
        signUp = view.findViewById(R.id.signUp);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Registering User...");

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        signInto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
            }
        });

        int gender_main = gender.getCheckedRadioButtonId();
        gend = view.findViewById(gender_main);
        
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String gender_txt = gend.getText().toString();
                String firstName_txt = firstName.getText().toString();
                String lastName_txt = lastName.getText().toString();
                String email_txt = email.getText().toString();
                String password_txt = password.getText().toString();
                String confirmpassword_txt = comfirmPassword.getText().toString();

                if (TextUtils.isEmpty(firstName_txt) || TextUtils.isEmpty(lastName_txt)
                        ||TextUtils.isEmpty(email_txt) || TextUtils.isEmpty(password_txt)  
                        || TextUtils.isEmpty(confirmpassword_txt)){
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

                    register(gender_txt, firstName_txt, lastName_txt,email_txt, password_txt);

                }
                
            }
        });

        return view;
    }

    private void register(String gender_txt, String firstName_txt, String lastName_txt, String email_txt, String password_txt) {

        progressDialog.show();
        auth.createUserWithEmailAndPassword(email_txt,password_txt)
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
                            myUser.put("gender", gender_txt);
                            myUser.put("first_Name", firstName_txt);
                            myUser.put("last_Name", lastName_txt);
                            myUser.put("email", email_txt);
                            myUser.put("imageURL", "default");

                            database = FirebaseDatabase.getInstance();
                            reference = database.getReference("Staff_User");

                            reference.child(userId).setValue(myUser);


                        } else {

                            Toast.makeText(getActivity(), "you can't register with this email and password", Toast.LENGTH_SHORT).show();
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
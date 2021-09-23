package com.example.schoolproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.schoolproject.Model.ModelChat;
import com.example.schoolproject.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class SetMatch extends AppCompatActivity {

    Intent intent;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;

    TextView senderTeam, receiverTeam;

    String sender;
    String receiver;

    EditText date, time, dateTime, pitch, oppenentLevel, mainLevel;
    ProgressDialog progressDialog;

    Button setMatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_match);

        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        dateTime = findViewById(R.id.dateTime);
        senderTeam = findViewById(R.id.senderTeam);
        receiverTeam = findViewById(R.id.receiverTeam);
        pitch = findViewById(R.id.pitch);
        setMatch = findViewById(R.id.setMatch);
        mainLevel = findViewById(R.id.mainLevel);
        oppenentLevel = findViewById(R.id.oppenentLevel);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Match Setting...");

        date.setInputType(InputType.TYPE_NULL);
        time.setInputType(InputType.TYPE_NULL);
        dateTime.setInputType(InputType.TYPE_NULL);

        intent = getIntent();
        sender = intent.getStringExtra("myId");
        receiver = intent.getStringExtra("hisUid");

        auth = FirebaseAuth.getInstance();

        DatabaseReference referenceSender = FirebaseDatabase.getInstance().getReference("Student_User/"+sender);

        referenceSender.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                User senderUser = snapshot.getValue(User.class);
                String opponentDepartment = senderUser.getDepartment();

                senderTeam.setText(opponentDepartment);


            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        String senderT = senderTeam.getText().toString();
        String senderTeamL = senderT + " " + oppenentLevel;

        DatabaseReference referenceReceiver = FirebaseDatabase.getInstance().getReference("Student_User/"+receiver);

        referenceReceiver.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                User receiverUser = snapshot.getValue(User.class);
                String mainDepartment = receiverUser.getDepartment();

                receiverTeam.setText(mainDepartment);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        String receiverT = receiverTeam.getText().toString();
        String receiverTeamL = receiverT + " " + mainLevel;

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(date);
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog(time);
            }
        });

        dateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(dateTime);
            }
        });

        setMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();

                DatabaseReference setMatchReference = FirebaseDatabase.getInstance().getReference("Match_Arrangement");
                firebaseUser = auth.getCurrentUser();
                assert firebaseUser != null;
                String userId = firebaseUser.getUid();

//
                String receiverTeamMain = receiverTeamL.toString();
                String senderTeamMain = senderTeamL.toString();
                String dateMain = date.getText().toString();
                String timeMain = time.getText().toString();
                String pitchMain = pitch.getText().toString();


                HashMap<String, Object> myMatch = new HashMap<>();
                myMatch.put("id", userId);
                myMatch.put("opponentTeam", senderTeamMain);
                myMatch.put("mainTeam", receiverTeamMain);
                myMatch.put("date", dateMain);
                myMatch.put("time", timeMain);
                myMatch.put("pitch", pitchMain);
                myMatch.put("opponentScore", "");
                myMatch.put("mainTeamScore", "");

//                SetMatch chat = new SetMatch();
//                chat.setSender(userId);


                setMatchReference.child(userId).push().setValue(myMatch);
                Toast.makeText(SetMatch.this, "match has been successfully set", Toast.LENGTH_SHORT).show();

                progressDialog.dismiss();
            }
        });

    }

    private void showDateTimeDialog(EditText dateTime) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm");

                        dateTime.setText(simpleDateFormat.format(calendar.getTime()));

                    }
                };

                new TimePickerDialog(SetMatch.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
            }
        };
        new DatePickerDialog(SetMatch.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void showTimeDialog(EditText time) {
        Calendar calendar = Calendar.getInstance();

        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

                time.setText(simpleDateFormat.format(calendar.getTime()));

            }
        };

        new TimePickerDialog(SetMatch.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
    }

    private void showDateDialog(EditText date) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd");

                date.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };

        new DatePickerDialog(SetMatch.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}

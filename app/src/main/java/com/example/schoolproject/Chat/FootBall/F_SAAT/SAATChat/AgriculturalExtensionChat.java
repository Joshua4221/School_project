package com.example.schoolproject.Chat.FootBall.F_SAAT.SAATChat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolproject.Adapter.AdapterAgriculturalEx;
import com.example.schoolproject.Adapter.ChatAdapter;
import com.example.schoolproject.Model.ModelChat;
import com.example.schoolproject.Model.User;
import com.example.schoolproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AgriculturalExtensionChat extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    CircleImageView profileTv;
    TextView nameTv, userStatusTv;
    EditText messageEt;
    ImageButton sendBtn;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference usersDbRef;


    ValueEventListener seenListener;
    DatabaseReference userRefForSeen;

    List<ModelChat> chatList;
    ChatAdapter adapterChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agricultural_extension_chat);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");

        recyclerView = findViewById(R.id.chat_recyclerView);
        profileTv = findViewById(R.id.profileIv);
        nameTv = findViewById(R.id.nameTv);
        userStatusTv = findViewById(R.id.userStatusTv);
        messageEt = findViewById(R.id.messageEv);
        sendBtn = findViewById(R.id.sendBtn);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapterChat = new ChatAdapter(AgriculturalExtensionChat.this, chatList);
        recyclerView.setAdapter(adapterChat);

        Intent intent = getIntent();
        String hisUid = intent.getStringExtra("hisUid");

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        usersDbRef = firebaseDatabase.getReference("Student_User/"+hisUid);


        usersDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                User user = snapshot.getValue(User.class);

                nameTv.setText(user.getFirst_Name());
                String hisImage = user.getImageURL();

                try {
                    Picasso.get().load(hisImage).placeholder(R.drawable.messi).into(profileTv);
                } catch (Exception e) {
                    Picasso.get().load(R.drawable.messi).into(profileTv);
                }

                readMessage(firebaseUser.getUid(), hisUid, hisImage);
                seenMessage(firebaseUser.getUid(), hisUid);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageEt.getText().toString().trim();

//                if(TextUtils.isEmpty(message)){
//                    Toast.makeText(AgriculturalEconomicsChat.this, "Can not send empty message...", Toast.LENGTH_SHORT).show();
//                } else {
//                    sendMessage(message);
//                }
                if(!message.equals("")){
                    sendMessage(firebaseUser.getUid(), hisUid,message);
                } else {
                    Toast.makeText(AgriculturalExtensionChat.this, "Can not send empty message...", Toast.LENGTH_SHORT).show();
                }

                messageEt.setText("");
            }
        });

    }

    private void seenMessage(String myId, String hisUid) {

        userRefForSeen = FirebaseDatabase.getInstance().getReference("Chats");
        seenListener = userRefForSeen.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    ModelChat chat = dataSnapshot.getValue(ModelChat.class);
                    if(chat.getReceiver().equals(myId) && chat.getSender().equals(hisUid)){
                        HashMap<String, Object> hasSeenHashMap = new HashMap<>();
                        hasSeenHashMap.put("isSeen", true);

                        dataSnapshot.getRef().updateChildren(hasSeenHashMap);
                    }

                }


            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    private void readMessage(String myId, String hisUid, String hisImage) {
        chatList = new ArrayList<>();
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Chats");

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    ModelChat chats = dataSnapshot.getValue(ModelChat.class);
                    String receiver = dataSnapshot.child("receiver").getValue().toString();
                    String sender = dataSnapshot.child("sender").getValue().toString();
                    if(receiver.equals(myId) && sender.equals(hisUid) ||
                            receiver.equals(hisUid) && sender.equals(myId)){
                        chatList.add(chats);
                    }


//                    if(chats.getReceiver().equals(myId) && chats.getSender().equals(hisUid) ||
//                            chats.getReceiver().equals(hisUid) && chats.getSender().equals(myId)){
//                        chatList.add(chats);
//                    }
                }

                adapterChat.setChatList(chatList);

                adapterChat.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    private void sendMessage(String myId,String hisUid ,String message) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        String timestamp = String.valueOf(System.currentTimeMillis());

        HashMap<String, Object> myMessage = new HashMap<>();
        myMessage.put("sender", myId);
        myMessage.put("receiver", hisUid);
        myMessage.put("message", message);
        myMessage.put("timestamp", timestamp);
        myMessage.put("isSeen", false);

        databaseReference.child("Chats").push().setValue(myMessage);

        ModelChat chat = new ModelChat();
        chat.setSender(myId);
        chat.setMessage(message);
        chat.setReceiver(hisUid);
        chat.setSeen(false);
        chat.setTimestamp(timestamp);

        adapterChat.getChatList().add(chat);

        adapterChat.notifyItemInserted(adapterChat.getChatList().size()-1);

        recyclerView.scrollToPosition(adapterChat.getItemCount()-1);
    }

    @Override
    protected void onPause() {
        super.onPause();

        userRefForSeen.removeEventListener(seenListener);
    }

}
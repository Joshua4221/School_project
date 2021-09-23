package com.example.schoolproject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolproject.Model.ModelChat;
import com.example.schoolproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;
import com.example.schoolproject.SetMatch;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CapatainChatpage extends RecyclerView.Adapter<CapatainChatpage.MyHolder>{

    private static final int MSG_TYPE_LEFT = 0;
    private static final int MSG_TYPE_RIGHT = 1;
    private Context context;
    private List<ModelChat> chatList;
    private String imageUrl;

    private FirebaseUser fUser;

    public CapatainChatpage(Context context, List<ModelChat> chatList) {
        this.context = context;
        this.chatList = chatList;
    }

    public List<ModelChat> getChatList() {
        return chatList;
    }

    public void setChatList(List<ModelChat> chatList) {
        this.chatList = chatList;
    }

    @NotNull
    @Override
    public CapatainChatpage.MyHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(context).inflate(R.layout.row_chat_right, parent, false);
            return  new CapatainChatpage.MyHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.row_chat_left, parent, false);
            return  new CapatainChatpage.MyHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CapatainChatpage.MyHolder holder, int position) {

        ModelChat chat = chatList.get(position);

        String sender = chat.getSender();
        String receiver = chat.getReceiver();
        String message = chat.getMessage();
        String timeStamp = chat.getTimestamp();

        Calendar cal = Calendar.getInstance(Locale.ENGLISH);

        try {
            cal.setTimeInMillis(Long.parseLong(timeStamp));
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        String dateTime = DateFormat.format("dd/mm/yyyy hh:mm aa", cal).toString();

        holder.messageTv.setText(message);
        holder.timeTv.setText(dateTime);
        try {
            Picasso.get().load(imageUrl).placeholder(R.drawable.messi).into(holder.profileTv);
        } catch (Exception e){
            Picasso.get().load(R.drawable.messi).into(holder.profileTv);
        }

        if (position == chatList.size() - 1){
            if (chatList.get(position).isSeen()){
                holder.isSeenTv.setText("Seen");
            } else {
                holder.isSeenTv.setText("Delivered");
            }
        } else {
            holder.isSeenTv.setVisibility(View.GONE);
        }
        String jos = "joshua";
//        String message1 = chat.setMessage(jos);

            holder.messageTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if(holder.messageTv.equals("Can I have a match with you")) {
                        Intent intent = new Intent(context, SetMatch.class);
                        intent.putExtra("hisUid", receiver);
                        intent.putExtra("myId", sender);
                        context.startActivity(intent);
//                    }
                }

            });


    }

    @Override
    public int getItemCount() {
        return chatList ==  null ? 0 : chatList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        ImageView profileTv;
        TextView messageTv, timeTv, isSeenTv;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            profileTv = itemView.findViewById(R.id.profileIv);
            messageTv = itemView.findViewById(R.id.messageTv);
            timeTv = itemView.findViewById(R.id.timeTv);
            isSeenTv = itemView.findViewById(R.id.isSeentTv);
        }
    }

    public int getItemViewType(int position) {

        fUser = FirebaseAuth.getInstance().getCurrentUser();
        if(chatList.get(position).getSender().equals(fUser.getUid())){
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }
    }
}

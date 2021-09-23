package com.example.schoolproject.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
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

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyHolder>{

    private static final int MSG_TYPE_LEFT = 0;
    private static final int MSG_TYPE_RIGHT = 1;
    private Context context;
    private List<ModelChat> chatList;
    private String imageUrl;

    private FirebaseUser fUser;

    public ChatAdapter(Context context, List<ModelChat> chatList) {
        this.context = context;
        this.chatList = chatList;
    }

    public List<ModelChat> getChatList() {
        return chatList;
    }

    public void setChatList(List<ModelChat> chatList) {
        this.chatList = chatList;
    }

    @NonNull
    @NotNull
    @Override
    public ChatAdapter.MyHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(context).inflate(R.layout.row_chat_right, parent, false);
            return  new ChatAdapter.MyHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.row_chat_left, parent, false);
            return  new ChatAdapter.MyHolder(view);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull ChatAdapter.MyHolder holder, int position) {

        ModelChat chat = chatList.get(position);

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

    @Override
    public int getItemViewType(int position) {

        fUser = FirebaseAuth.getInstance().getCurrentUser();
        if(chatList.get(position).getSender().equals(fUser.getUid())){
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }
    }
}

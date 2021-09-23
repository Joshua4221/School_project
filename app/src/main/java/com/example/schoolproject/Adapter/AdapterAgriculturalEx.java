package com.example.schoolproject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolproject.Chat.FootBall.F_SAAT.SAATChat.AgriculturalEconomicsChat;
import com.example.schoolproject.Chat.FootBall.F_SAAT.SAATChat.AgriculturalExtensionChat;
import com.example.schoolproject.Model.User;
import com.example.schoolproject.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterAgriculturalEx extends RecyclerView.Adapter<AdapterAgriculturalEx.MyHolder>{

    Context context;
    List<User> userList;

    public AdapterAgriculturalEx(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @Override
    public @NotNull AdapterAgriculturalEx.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_users, parent, false);
        return new AdapterAgriculturalEx.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterAgriculturalEx.MyHolder holder, int position) {



//        User user = userList.get(position);
        String hisUID = userList.get(position).getId();
        String userImage = userList.get(position).getImageURL();
        String userName = userList.get(position).getFirst_Name();

        holder.nameTv.setText(userName);

        try{
            Picasso.get().load(userImage).placeholder(R.drawable.messi).into(holder.avatarTv);
        } catch (Exception e) {
            Picasso.get().load(R.drawable.messi).into(holder.avatarTv);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AgriculturalExtensionChat.class);
                intent.putExtra("hisUid", hisUID);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder {

        public ImageView avatarTv;
        public TextView nameTv;

        public MyHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            avatarTv = itemView.findViewById(R.id.avatarIv);
            nameTv = itemView.findViewById(R.id.nameTv);

        }
    }

}

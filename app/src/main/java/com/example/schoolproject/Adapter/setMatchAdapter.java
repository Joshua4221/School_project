package com.example.schoolproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolproject.Model.ModelChat;
import com.example.schoolproject.Model.SetModel;
import com.example.schoolproject.Model.User;
import com.example.schoolproject.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class setMatchAdapter extends RecyclerView.Adapter<setMatchAdapter.MyHolder> {

    Context context;
    List<SetModel> userList;

    public setMatchAdapter(Context context, List<SetModel> userList) {
        this.context = context;
        this.userList = userList;
    }
    public List<SetModel> getUserList() {
        return userList;
    }

    @Override
    public MyHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.set_match, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull setMatchAdapter.MyHolder holder, int position) {

        SetModel model = userList.get(position);
        String dateSet = model.getDate();
        String timeSet = model.getTime();
        String pitchSet = model.getPitch();
        String mainTeamSet = model.getMainTeam();
        String oppenentTeamSet = model.getOpponentTeam();
        String mainTeamScoreSet = model.getMainTeamScore();
        String oppenentTeamScoreSet = model.getOpponentScore();

        holder.mainTeam.setText(mainTeamSet);
        holder.mainTeamScore.setText(mainTeamScoreSet);
        holder.oppenentTeamScore.setText(oppenentTeamScoreSet);
        holder.oppenentTeam.setText(oppenentTeamSet);
        holder.date.setText(dateSet);
        holder.time.setText(timeSet);
        holder.pitch.setText(pitchSet);

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        TextView date, time, pitch, mainTeam, oppenentTeam, mainTeamScore, oppenentTeamScore;

        public MyHolder(@NonNull @NotNull View itemView) {

            super(itemView);

            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            pitch = itemView.findViewById(R.id.pitch);
            mainTeam = itemView.findViewById(R.id.mainTeam);
            oppenentTeam = itemView.findViewById(R.id.oppenentTeam);
            mainTeamScore = itemView.findViewById(R.id.mainTeamScore);
            oppenentTeamScore = itemView.findViewById(R.id.oppenentTeamScore);

        }
    }
}

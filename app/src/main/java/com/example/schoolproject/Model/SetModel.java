package com.example.schoolproject.Model;

public class SetModel {

    String id;
    String opponentTeam;
    String mainTeam;
    String date;
    String time;
    String pitch;
    String opponentScore;
    String mainTeamScore;

    public SetModel() {
    }

    public SetModel(String id, String opponentTeam, String mainTeam, String date, String time, String pitch, String opponentScore, String mainTeamScore) {
        this.id = id;
        this.opponentTeam = opponentTeam;
        this.mainTeam = mainTeam;
        this.date = date;
        this.time = time;
        this.pitch = pitch;
        this.opponentScore = opponentScore;
        this.mainTeamScore = mainTeamScore;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOpponentTeam() {
        return opponentTeam;
    }

    public void setOpponentTeam(String opponentTeam) {
        this.opponentTeam = opponentTeam;
    }

    public String getMainTeam() {
        return mainTeam;
    }

    public void setMainTeam(String mainTeam) {
        this.mainTeam = mainTeam;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPitch() {
        return pitch;
    }

    public void setPitch(String pitch) {
        this.pitch = pitch;
    }

    public String getOpponentScore() {
        return opponentScore;
    }

    public void setOpponentScore(String opponentScore) {
        this.opponentScore = opponentScore;
    }

    public String getMainTeamScore() {
        return mainTeamScore;
    }

    public void setMainTeamScore(String mainTeamScore) {
        this.mainTeamScore = mainTeamScore;
    }
}

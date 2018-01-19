package com.jyhd.black.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class GameData implements Serializable{

    private String date;
    @Id
    private String userId;
    private String gameName;
    private int interal;

    public GameData() {
        super();
    }

    public GameData(String date, String userId, String gameName, int interal) {
        this.date = date;
        this.userId = userId;
        this.gameName = gameName;
        this.interal = interal;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public int getInteral() {
        return interal;
    }

    public void setInteral(int interal) {
        this.interal = interal;
    }

    @Override
    public String toString() {
        return "GameData{" +
                "date='" + date + '\'' +
                ", userId='" + userId + '\'' +
                ", gameName='" + gameName + '\'' +
                ", interal=" + interal +
                '}';
    }
}

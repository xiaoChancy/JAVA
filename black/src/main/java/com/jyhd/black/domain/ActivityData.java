package com.jyhd.black.domain;

import javax.persistence.*;

@Entity
@Table(name = "activity_data")
public class ActivityData {

    @Column(name = "user_id")
    private String userId;
    @Id
    private String time;
    private int reward;
    private int integral;
    private int stage;
    private int ranking;
    private int status;
    private String remarks;

    public ActivityData() {
        super();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "ActivityDataDao{" +
                "userId='" + userId + '\'' +
                ", time='" + time + '\'' +
                ", reward=" + reward +
                ", integral=" + integral +
                ", stage=" + stage +
                ", ranking=" + ranking +
                ", status=" + status +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}

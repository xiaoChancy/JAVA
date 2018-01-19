package com.jyhd.black.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ranking_list")
public class RankingList {
    @Id
    @Column(name = "user_id")
    private String userId;
    @Column(name = "epg_id")
    private String epgId;
    private int integral;
    private short month;

    public RankingList() {
        super();
    }

    public RankingList(String userId, int integral, short month) {
        this.userId = userId;
        this.integral = integral;
        this.month = month;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEpgId() {
        return epgId;
    }

    public void setEpgId(String epgId) {
        this.epgId = epgId;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public short getMonth() {
        return month;
    }

    public void setMonth(short month) {
        this.month = month;
    }

    @Override
    public String toString() {
        return "RankingList{" +
                "userId='" + userId + '\'' +
                ", epgId='" + epgId + '\'' +
                ", integral=" + integral +
                ", month=" + month +
                '}';
    }
}

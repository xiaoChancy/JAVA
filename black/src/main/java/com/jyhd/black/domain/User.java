package com.jyhd.black.domain;

public class User {

    private String userId;
    private int ranking;
    private int integer;
    private int month;

    public User() {
        super();
    }

    public User(String userId, int ranking, int integer, int month) {
        this.userId = userId;
        this.ranking = ranking;
        this.integer = integer;
        this.month = month;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public int getInteger() {
        return integer;
    }

    public void setInteger(int integer) {
        this.integer = integer;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", ranking=" + ranking +
                ", integer=" + integer +
                '}';
    }
}

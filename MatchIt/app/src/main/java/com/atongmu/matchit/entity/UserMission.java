package com.atongmu.matchit.entity;

/**
 * 2016/7/15
 *
 * @author maofagui
 * @version 1.0
 */
public class UserMission {
    public static final String PROFILE="PROFILE";
    public static final String USERS="USERS";
    //用户名
    private String username;

    private String missionIds;

    public int next;
    //游戏记录
    private int missionId;
    //完成得到几星
    private int stars;
    //耗时
    private int timeSpent;

    public UserMission() {
    }

    public UserMission(String username, int next) {
        this.username = username;
        this.next = next;
    }

    public UserMission(String username, int next, int missionId, int stars, int timeSpent) {
        this.username = username;
        this.next = next;
        this.missionId = missionId;
        this.stars = stars;
        this.timeSpent = timeSpent;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getMissionId() {
        return missionId;
    }

    public void setMissionId(int missionId) {
        this.missionId = missionId;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public int getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(int timeSpent) {
        this.timeSpent = timeSpent;
    }
}

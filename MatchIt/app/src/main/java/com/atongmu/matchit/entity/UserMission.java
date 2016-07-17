package com.atongmu.matchit.entity;

/**
 * 2016/7/15
 *
 * @author maofagui
 * @version 1.0
 */
public class UserMission {
    //用户名
    private String username;
    //游戏记录
    private int missionId;
    //完成得到几星
    private int stars;
    //耗时
    private int timeSpent;

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

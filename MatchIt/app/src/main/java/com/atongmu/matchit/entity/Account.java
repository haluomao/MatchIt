package com.atongmu.matchit.entity;

/**
 * Created by mfg on 16/07/05.
 */
public class Account {
    public static final String ACCOUNT="ACCOUNT";
    public static final String UID="UID";
    public static final String NAME="NAME";
    public static final String STATUS="STATUS";
    public static final String MISSION_ID="MISSION_ID";

    private int uid;
    private String name;

    private int status;
    private int missionId;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getMissionId() {
        return missionId;
    }

    public void setMissionId(int missionId) {
        this.missionId = missionId;
    }
}

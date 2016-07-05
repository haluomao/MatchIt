package com.atongmu.matchit.entity;

/**
 * Created by mfg on 16/07/05.
 */
public class Mission {
    public static final String MISSION="MISSION";
    private int id;
    private String name;

    //完成得几星
    private int stars;
    //是否完成
    private int completed;
    //耗时
    private int timeSpent;

    //游戏设定
    //宽
    private int width;
    private int length;
    private int size;
    private String itemIds;
    //时间长度
    private int timeSpan;
    //奖励类型
    private int bonusId;

    public Mission(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    public int getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(int timeSpent) {
        this.timeSpent = timeSpent;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getItemIds() {
        return itemIds;
    }

    public void setItemIds(String itemIds) {
        this.itemIds = itemIds;
    }

    public int getTimeSpan() {
        return timeSpan;
    }

    public void setTimeSpan(int timeSpan) {
        this.timeSpan = timeSpan;
    }

    public int getBonusId() {
        return bonusId;
    }

    public void setBonusId(int bonusId) {
        this.bonusId = bonusId;
    }
}

package com.atongmu.matchit.entity;

import com.atongmu.matchit.common.Consts;

/**
 * Created by mfg on 16/07/05.
 */
public class Mission {
    public static final String MISSION = "MISSION";
    private Integer id;
    private String name;

    //游戏设定
    //宽
    private Integer width;
    private Integer height;
    private Integer size;
    private String itemIds;
    //时间长度
    private Integer time;
    //奖励类型
    private Integer bonusId;
    private Integer next;

    public Mission() {
    }

    public Mission(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getNext() {
        return next;
    }

    public void setNext(Integer next) {
        this.next = next;
    }


    public Integer getId() {
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

    public Integer getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Integer getSize() {
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

    public Integer getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public Integer getBonusId() {
        return bonusId;
    }

    public void setBonusId(int bonusId) {
        this.bonusId = bonusId;
    }

    public String turnStr(int[] ids) {
        if (null == ids) return null;
        StringBuilder res = new StringBuilder("");
        for (int i = 0; i < ids.length; i++) {
            if (i != 0)
                res.append(Consts.SEPARATOR);
            res.append(ids[i]);
        }
        return res.toString();
    }

    public int[] parseStr(String ids) {
        if (null == ids) return null;
        String[] idStrArr = ids.split(Consts.SEPARATOR);
        int[] res = new int[idStrArr.length];
        for (int i = 0; i < idStrArr.length; i++) {
            res[i] = Integer.parseInt(idStrArr[i]);
        }
        return res;
    }
}

package com.atongmu.matchit.entity;

/**
 * Created by mfg on 16/06/30.
 */
public class Solution {
    public static final int WRONG=-1;
    public static final int CAN_SOLVE=0;

    public static final int DOWN=1;
    public static final int UP=2;
    public static final int LEFT=3;
    public static final int RIGHT=4;

    public static final int DOWN_LEFT=10;
    public static final int DOWN_RIGHT=11;
    public static final int UP_LEFT=12;
    public static final int UP_RIGHT=13;

    public static final int LEFT_DOWN=14;
    public static final int LEFT_UP=15;
    public static final int RIGHT_DOWN=16;
    public static final int RIGHT_UP=17;

    public static final int DOWN_LEFT_UP=20;
    public static final int DOWN_LEFT_DOWN=21;
    public static final int DOWN_RIGHT_UP=22;
    public static final int DOWN_RIGHT_DOWN=23;

    public static final int UP_LEFT_UP=24;
    public static final int UP_LEFT_DOWN=25;
    public static final int UP_RIGHT_UP=26;
    public static final int UP_RIGHT_DOWN=27;

    public static final int LEFT_DOWN_LEFT=28;
    public static final int LEFT_DOWN_RIGHT=29;
    public static final int LEFT_UP_LEFT=30;
    public static final int LEFT_UP_RIGHT=31;

    public static final int RIGHT_DOWN_LEFT=32;
    public static final int RIGHT_DOWN_RIGHT=33;
    public static final int RIGHT_UP_LEFT=34;
    public static final int RIGHT_UP_RIGHT=35;

    private int value;
    private Position pos1;
    private Position pos2;

    public Solution() {
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Position getPos1() {
        return pos1;
    }

    public void setPos1(Position pos1) {
        this.pos1 = pos1;
    }

    public Position getPos2() {
        return pos2;
    }

    public void setPos2(Position pos2) {
        this.pos2 = pos2;
    }

//    public String get(int value) {
//        return value;
//    }

    @Override
    public String toString() {
        return "Solution [" +
                "value=" + value +
                ", pos1=" + pos1 +
                ", pos2=" + pos2 +
                ']';
    }
}

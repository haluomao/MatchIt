package com.atongmu.matchit.entity;

/**
 * Created by mfg on 16/06/29.
 */
public class Point {
    Position position;
    int value;

    public Point(){
        position = new Position();
    }
    public Point(int x, int y){
        position = new Position(x, y);
    }
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

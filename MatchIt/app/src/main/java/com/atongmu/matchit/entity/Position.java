package com.atongmu.matchit.entity;

/**
 * Created by mfg on 16/06/29.
 */
public class Position {
    private int x;
    private int y;

    public Position(){

    }
    public Position(int x, int y){
        this.x=x;
        this.y=y;
    }

    public Position(Position a){
        this.x=a.getX();
        this.y=a.getY();
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Position [" +
                "x=" + x +
                ", y=" + y +
                ']';
    }
}

package com.atongmu.matchit.entity;

import android.graphics.Bitmap;

/**
 *
 */
public class Item {
    Position position;
    Bitmap bitmap;
    int sizeL;
    int value=0;

    public Item() {
    }

    public Item(Position position) {
        this.position = position;
    }

    public Position getCenterPosition() {
        return new Position(position.getX()+sizeL/2, position.getY()+sizeL/2);
    }

    public static Position getCenterPosition(Position position, int sizeL) {
        return new Position(position.getX()+sizeL/2, position.getY()+sizeL/2);
    }

    public boolean equals(Item item){
        if(item==null) return false;
        return item.getPosition().equals(this.getPosition());
    }

    /**
     * 0--1
     * l  l
     * 2--3
     * @return
     */
    public Position[] getVertexPosition() {
        Position[] positions = new Position[4];
        positions[0] = position;
        positions[1] = new Position(position.getX(), position.getY()+sizeL);
        positions[2] = new Position(position.getX()+sizeL, position.getY()+sizeL);
        positions[3] = new Position(position.getX()+sizeL, position.getY());

        return positions;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getSizeL() {
        return sizeL;
    }

    public void setSizeL(int sizeL) {
        this.sizeL = sizeL;
    }

}

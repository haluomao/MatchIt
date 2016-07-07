package com.atongmu.matchit.entity;

import android.graphics.Bitmap;

/**
 * 2016/7/7
 *
 * @author maofagui
 * @version 1.0
 */
public class ImageButton {

    public Position position;
    public Bitmap[] bitmaps;
    public int status;
    public int size;

    public ImageButton(){
        status = 0;
        size= 30;
    }
}

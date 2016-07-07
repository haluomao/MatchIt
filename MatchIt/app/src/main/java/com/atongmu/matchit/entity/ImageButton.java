package com.atongmu.matchit.entity;

import android.graphics.Bitmap;

/**
 * 2016/7/7
 *
 * @author maofagui
 * @version 1.0
 */
public class ImageButton {

    public static final int BUTTON_MUSIC=1;
    public static final int BUTTON_SOUND=2;

    public static final int BUTTON_PAUSE=10;
    public static final int BUTTON_PROMPT=15;
    public static final int BUTTON_RETRY=11;
    public static final int BUTTON_BACK=20;

    public Position position;
    public Bitmap[] bitmaps;
    public int status;
    public int size;

    public int id;

    public ImageButton(){
        status = 0;
        size= 25;
    }
}

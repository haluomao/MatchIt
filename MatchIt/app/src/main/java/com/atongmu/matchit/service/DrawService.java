package com.atongmu.matchit.service;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.atongmu.matchit.entity.ImageButton;

/**
 * 2016/7/7
 *
 * @author maofagui
 * @version 1.0
 */
public class DrawService {
    public void drawButton(Canvas canvas, Paint paint, ImageButton imageButton){
        canvas.drawBitmap(imageButton.bitmaps[imageButton.status],
                imageButton.position.getX(),
                imageButton.position.getY(),
                paint);
    }

    public void switchButton(Canvas canvas, Paint paint, ImageButton imageButton){
        imageButton.status = (imageButton.status+1)%2;
        canvas.drawBitmap(imageButton.bitmaps[imageButton.status],
                imageButton.position.getX(),
                imageButton.position.getY(),
                paint);
    }
}

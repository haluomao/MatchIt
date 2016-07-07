package com.atongmu.matchit.service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.atongmu.matchit.R;
import com.atongmu.matchit.entity.ImageButton;

import java.util.ArrayList;
import java.util.List;

/**
 * 2016/7/7
 *
 * @author maofagui
 * @version 1.0
 */
public class ButtonsManager {
    ImageButton musicBtn;
    ImageButton soundBtn;

    ImageButton promptBtn;
    ImageButton pauseBtn;
    ImageButton retryBtn;
    ImageButton backBtn;

    List<ImageButton> buttonList;
    public ButtonsManager(View view){
        musicBtn = new ImageButton();
        musicBtn.bitmaps = new Bitmap[2];
        musicBtn.bitmaps[0] = BitmapFactory.decodeResource(view.getResources(), R.drawable.bgp);

        soundBtn = new ImageButton();

        promptBtn = new ImageButton();

        pauseBtn = new ImageButton();

        retryBtn = new ImageButton();

        backBtn = new ImageButton();

        buttonList = new ArrayList<ImageButton>();
        buttonList.add(musicBtn);
        buttonList.add(soundBtn);
        buttonList.add(pauseBtn);
        buttonList.add(retryBtn);
        buttonList.add(backBtn);

    }

    public ImageButton checkClick(int x, int y){
        for(ImageButton imageBtn:buttonList){
            if(x>= imageBtn.position.getX() && x<= imageBtn.position.getX()+imageBtn.size &&
                    y>= imageBtn.position.getY() && y<= imageBtn.position.getY()+imageBtn.size){
                return imageBtn;
            }
        }
        return null;
    }

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

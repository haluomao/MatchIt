package com.atongmu.matchit.service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.atongmu.matchit.R;
import com.atongmu.matchit.entity.ImageButton;
import com.atongmu.matchit.entity.Position;

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
        musicBtn.id=ImageButton.BUTTON_MUSIC;
        musicBtn.position = new Position(view.getWidth()-musicBtn.size*9, 5);
        musicBtn.bitmaps = new Bitmap[2];
        musicBtn.bitmaps[0] = BitmapFactory.decodeResource(view.getResources(), R.drawable.btn);
        musicBtn.bitmaps[1] = BitmapFactory.decodeResource(view.getResources(), R.drawable.btn);

        soundBtn = new ImageButton();
        soundBtn.id=ImageButton.BUTTON_SOUND;
        soundBtn.position = new Position(view.getWidth()-soundBtn.size*6, 5);
        soundBtn.bitmaps = new Bitmap[2];
        soundBtn.bitmaps[0] = BitmapFactory.decodeResource(view.getResources(), R.drawable.btn);
        soundBtn.bitmaps[1] = BitmapFactory.decodeResource(view.getResources(), R.drawable.btn);

        promptBtn = new ImageButton();
        promptBtn.id=ImageButton.BUTTON_PROMPT;
        promptBtn.position = new Position(view.getWidth()-promptBtn.size*3, 5);
        promptBtn.bitmaps = new Bitmap[2];
        promptBtn.bitmaps[0] = BitmapFactory.decodeResource(view.getResources(), R.drawable.btn);
        promptBtn.bitmaps[1] = BitmapFactory.decodeResource(view.getResources(), R.drawable.btn);

        pauseBtn = new ImageButton();
        pauseBtn.id=ImageButton.BUTTON_PAUSE;
        pauseBtn.position = new Position(view.getWidth() / 4, view.getHeight() - 20 - pauseBtn.size);
        pauseBtn.bitmaps = new Bitmap[2];
        pauseBtn.bitmaps[0] = BitmapFactory.decodeResource(view.getResources(), R.drawable.btn);
        pauseBtn.bitmaps[1] = BitmapFactory.decodeResource(view.getResources(), R.drawable.btn);

        retryBtn = new ImageButton();
        retryBtn.id=ImageButton.BUTTON_RETRY;
        retryBtn.position = new Position(view.getWidth() / 2, view.getHeight() - 20 - retryBtn.size);
        retryBtn.bitmaps = new Bitmap[2];
        retryBtn.bitmaps[0] = BitmapFactory.decodeResource(view.getResources(), R.drawable.btn);
        retryBtn.bitmaps[1] = BitmapFactory.decodeResource(view.getResources(), R.drawable.btn);

        backBtn = new ImageButton();
        backBtn.id=ImageButton.BUTTON_BACK;
        backBtn.position = new Position(view.getWidth() *3/ 4, view.getHeight() - 20 - backBtn.size);
        backBtn.bitmaps = new Bitmap[2];
        backBtn.bitmaps[0] = BitmapFactory.decodeResource(view.getResources(), R.drawable.btn);
        backBtn.bitmaps[1] = BitmapFactory.decodeResource(view.getResources(), R.drawable.btn);

        buttonList = new ArrayList<ImageButton>();
        buttonList.add(musicBtn);
        buttonList.add(soundBtn);
        buttonList.add(promptBtn);
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

    public void drawButtons(Canvas canvas, Paint paint){
        for(ImageButton imageButton:buttonList){
            canvas.drawBitmap(imageButton.bitmaps[imageButton.status],
                    imageButton.position.getX(),
                    imageButton.position.getY(),
                    paint);
        }
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

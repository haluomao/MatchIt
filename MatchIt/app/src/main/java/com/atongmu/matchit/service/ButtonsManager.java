package com.atongmu.matchit.service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
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
        musicBtn.bitmaps[0] = BitmapFactory.decodeResource(view.getResources(), R.drawable.music);
        musicBtn.bitmaps[1] = BitmapFactory.decodeResource(view.getResources(), R.drawable.music2);

        soundBtn = new ImageButton();
        soundBtn.id=ImageButton.BUTTON_SOUND;
        soundBtn.position = new Position(view.getWidth()-soundBtn.size*6, 5);
        soundBtn.bitmaps = new Bitmap[2];
        soundBtn.bitmaps[0] = BitmapFactory.decodeResource(view.getResources(), R.drawable.sound);
        soundBtn.bitmaps[1] = BitmapFactory.decodeResource(view.getResources(), R.drawable.sound2);

        promptBtn = new ImageButton();
        promptBtn.id=ImageButton.BUTTON_PROMPT;
        promptBtn.position = new Position(view.getWidth()-promptBtn.size*3, 5);
        promptBtn.bitmaps = new Bitmap[2];
        promptBtn.bitmaps[0] = BitmapFactory.decodeResource(view.getResources(), R.drawable.prompt);
        promptBtn.bitmaps[1] = BitmapFactory.decodeResource(view.getResources(), R.drawable.prompt);

        pauseBtn = new ImageButton();
        pauseBtn.id=ImageButton.BUTTON_PAUSE;
        pauseBtn.position = new Position(view.getWidth() / 4, view.getHeight() - 20 - pauseBtn.size);
        pauseBtn.bitmaps = new Bitmap[2];
        pauseBtn.bitmaps[0] = BitmapFactory.decodeResource(view.getResources(), R.drawable.pause0);
        pauseBtn.bitmaps[1] = BitmapFactory.decodeResource(view.getResources(), R.drawable.go);

        retryBtn = new ImageButton();
        retryBtn.id=ImageButton.BUTTON_RETRY;
        retryBtn.position = new Position(view.getWidth() / 2, view.getHeight() - 20 - retryBtn.size);
        retryBtn.bitmaps = new Bitmap[2];
        retryBtn.bitmaps[0] = BitmapFactory.decodeResource(view.getResources(), R.drawable.replay);
        retryBtn.bitmaps[1] = BitmapFactory.decodeResource(view.getResources(), R.drawable.replay);

        backBtn = new ImageButton();
        backBtn.id=ImageButton.BUTTON_BACK;
        backBtn.position = new Position(view.getWidth() *3/ 4, view.getHeight() - 20 - backBtn.size);
        backBtn.bitmaps = new Bitmap[2];
        backBtn.bitmaps[0] = BitmapFactory.decodeResource(view.getResources(), R.drawable.missions);
        backBtn.bitmaps[1] = BitmapFactory.decodeResource(view.getResources(), R.drawable.missions);

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
            if(ImageButton.BUTTON_PROMPT == imageButton.id && imageButton.status==1){
                int width = imageButton.bitmaps[1].getWidth();
                int height = imageButton.bitmaps[1].getHeight();
                Bitmap faceIconGreyBitmap = Bitmap
                        .createBitmap(width, height, Bitmap.Config.ARGB_8888);
                Canvas canvas2 = new Canvas(faceIconGreyBitmap);
                ColorMatrix colorMatrix = new ColorMatrix();
                colorMatrix.setSaturation(0);
                ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(
                        colorMatrix);
                ColorFilter tmp  = paint.getColorFilter();
                paint.setColorFilter(colorMatrixFilter);
                canvas2.drawBitmap(imageButton.bitmaps[1], 0, 0, paint);
                paint.setColorFilter(tmp);
                continue;
            }
            canvas.drawBitmap(imageButton.bitmaps[imageButton.status],
                    null,
                    new RectF(imageButton.position.getX(),
                            imageButton.position.getY(),
                            imageButton.position.getX()+imageButton.size,
                            imageButton.position.getY()+imageButton.size),
                    paint);
        }
    }

    public void drawButton(Canvas canvas, Paint paint, ImageButton imageButton){
        canvas.drawBitmap(imageButton.bitmaps[imageButton.status],
                null,
                new RectF(imageButton.position.getX(),
                        imageButton.position.getY(),
                        imageButton.position.getX()+imageButton.size,
                        imageButton.position.getY()+imageButton.size),
                paint);
    }

    public void switchButton(Canvas canvas, Paint paint, ImageButton imageButton){
        imageButton.status = (imageButton.status+1)%2;
        canvas.drawBitmap(imageButton.bitmaps[imageButton.status],
                null,
                new RectF(imageButton.position.getX(),
                        imageButton.position.getY(),
                        imageButton.position.getX()+imageButton.size,
                        imageButton.position.getY()+imageButton.size),
                paint);
    }

    public ImageButton getById(int id){
        for(ImageButton imageButton:buttonList)
            if(imageButton.id ==id)
                return imageButton;
        return null;
    }
}

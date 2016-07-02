package com.atongmu.matchit.android;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.view.MotionEvent;
import android.view.View;

import com.atongmu.matchit.R;
import com.atongmu.matchit.entity.Item;
import com.atongmu.matchit.entity.Position;
import com.atongmu.matchit.entity.Solution;

/**
 * Created by mfg on 16/07/01.
 */
public class DrawView extends View {
    Paint paint = new Paint();
    Item[][] items;
    int marginL = 1;
    Canvas canvas;
    int xxx = 10;
    int yyy = 10;

    public DrawView(Context context) {
        super(context);
        this.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                onClickHandler(view, motionEvent);
                return false;
            }
        });
    }

    public void choose(Canvas canvas, Item item) {
        //应该检查item是否还在？
        paint.setColor(Color.RED);
//        paint.setStyle(Paint.Style.FILL);
        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(2);
        Position[] vertex = item.getVertexPosition();
        Path path = new Path();
        path.moveTo(vertex[0].getX(), vertex[0].getY());// 此点为多边形的起点
        path.lineTo(vertex[1].getX(), vertex[1].getY());
        path.lineTo(vertex[2].getX(), vertex[2].getY());
        path.lineTo(vertex[3].getX(), vertex[3].getY());
        path.close();
        canvas.drawPath(path, paint);

        System.out.println("画线：" + vertex[0].getX() + "," + vertex[0].getY() + "-" +
                vertex[1].getX() + "," + vertex[1].getY());

        //canvas.drawLine(vertex[0].getX(), vertex[0].getY(),vertex[1].getX(), vertex[1].getY(), paint);
        paint.setColor(Color.RED);
//        paint.setStrokeWidth((float) 20.0);             //设置线宽
        System.out.println("画线："+xxx+","+yyy);
        canvas.drawLine(xxx++, yyy++, xxx + 100, yyy + 100, paint);
    }

    private Item findItem(int x, int y) {
        int x0 = items[0][0].getPosition().getX();
        int y0 = items[0][0].getPosition().getY();
        int length = items[0][0].getSizeL() + marginL;
        if (x < x0 || y < y0) return null;
        int xx = (x - x0) / length;
        int yy = (y - y0) / length;
        if (xx >= items.length || yy >= items[0].length) {
            return null;
        }
        System.out.println("Find:" + xx + "," + yy);
        return items[xx][yy];
    }

    private void onClickHandler(View view, MotionEvent event) {
        int eventAction = event.getAction();

        int clickX;
        int clickY;

        clickX = (int) event.getX();
        clickY = (int) event.getY();
        System.out.println(clickX + "-" + clickY);

        switch (eventAction) {
            case MotionEvent.ACTION_DOWN:
                clickX = (int) event.getX();
                clickY = (int) event.getY();
                System.out.println(clickX + "-" + clickY);
                Item item = findItem(clickX, clickY);
                if (item != null)
                    choose(canvas, item);
                invalidate();
                break;

            case MotionEvent.ACTION_MOVE:
                break;

            case MotionEvent.ACTION_UP:
                break;
        }
    }

    public void unchose_ex(Canvas canvas, Item item) {
        //应该检查item是否还在？
        //重绘一遍？
        canvas.drawBitmap(item.getBitmap(), item.getPosition().getX(), item.getPosition().getX(), paint);
    }

    public void unchoose(Canvas canvas, Item item) {
        //应该检查item是否还在？
        //重绘一遍？
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        //canvas.drawPaint(paint);
        Position[] vertex = item.getVertexPosition();
        Path path = new Path();
        path.moveTo(vertex[0].getX(), vertex[0].getY());// 此点为多边形的起点
        path.lineTo(vertex[1].getX(), vertex[1].getY());
        path.lineTo(vertex[2].getX(), vertex[2].getY());
        path.lineTo(vertex[3].getX(), vertex[3].getY());
        path.close();
        canvas.drawPath(path, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
    }

    public void connect(Canvas canvas, Item item1, Item item2, Solution solution) {
        //应该检查item是否还在？
        paint.setColor(Color.GREEN);
        Position position1 = item1.getCenterPosition();
        Position position2 = item2.getCenterPosition();
        if (solution.getPos1() != null) {
            canvas.drawLine(position1.getX(), position1.getY(), solution.getPos1().getX(),
                    solution.getPos1().getY(), paint);// 画线
            if (solution.getPos2() != null) {
                canvas.drawLine(solution.getPos1().getX(), solution.getPos1().getY(),
                        solution.getPos2().getX(), solution.getPos2().getY(), paint);// 画线
            } else {
                canvas.drawLine(solution.getPos1().getX(), solution.getPos1().getY(),
                        position2.getX(), position2.getY(), paint);// 画线
            }
        } else {
            canvas.drawLine(position1.getX(), position1.getY(),
                    position2.getX(), position2.getY(), paint);// 画线
        }
    }

    public void unconnect(Canvas canvas, Item item1, Item item2, Solution solution) {
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        Position position1 = item1.getCenterPosition();
        Position position2 = item2.getCenterPosition();
        if (solution.getPos1() != null) {
            canvas.drawLine(position1.getX(), position1.getY(), solution.getPos1().getX(),
                    solution.getPos1().getY(), paint);// 画线
            if (solution.getPos2() != null) {
                canvas.drawLine(solution.getPos1().getX(), solution.getPos1().getY(),
                        solution.getPos2().getX(), solution.getPos2().getY(), paint);// 画线
            } else {
                canvas.drawLine(solution.getPos1().getX(), solution.getPos1().getY(),
                        position2.getX(), position2.getY(), paint);// 画线
            }
        } else {
            canvas.drawLine(position1.getX(), position1.getY(),
                    position2.getX(), position2.getY(), paint);// 画线
        }
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
    }

    private Position[][] genRects(int viewL, int viewH, int mapL, int mapH, int itemL, int marginL) {
        Position[][] res = new Position[mapL][mapH];
        int res0X = (viewL + marginL - marginL * mapL - itemL * mapL) / 2;
        int res0Y = (viewH + marginL - marginL * mapH - itemL * mapH) / 2;
        for (int i = 0; i < mapL; i++) {
            for (int j = 0; j < mapH; j++) {
                res[i][j] = new Position(res0X + i * itemL + i * marginL, res0Y + j * itemL + i * marginL);
            }
        }
        return res;
    }

    private Item[][] genItems(Position[][] positions) {
        Item[][] res = new Item[positions.length][positions[0].length];
        for (int i = 0; i < positions.length; i++) {
            for (int j = 0; j < positions[0].length; j++) {
                res[i][j] = new Item(positions[i][j]);
                res[i][j].setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.hero_aatrox));
                res[i][j].setValue(1);
                res[i][j].setSizeL(80);
            }
        }
        return res;
    }

    private void drawMap() {
        System.out.println(this.getMeasuredWidth() + "," + this.getMeasuredHeight());
        paint.setColor(Color.GRAY);// 设置灰色
        paint.setStyle(Paint.Style.FILL);//设置填满

        for (int i = 0; i < items.length; i++) {
            for (int j = 0; j < items[0].length; j++) {

                System.out.println(items[i][j].getPosition().getX() + "," + items[i][j].getPosition().getY());

                Item item = items[i][j];
                Rect rect = new Rect(item.getPosition().getX(),
                        item.getPosition().getY(),
                        item.getPosition().getX() + item.getSizeL() - marginL,
                        item.getPosition().getY() + item.getSizeL() - marginL);
                canvas.drawBitmap(item.getBitmap(), null, rect, paint);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
        // 创建画笔

//        paint.setColor(Color.GRAY);// 设置灰色
//        paint.setStyle(Paint.Style.FILL);//设置填满

        paint.setColor(Color.RED);// 设置红色

        canvas.drawText("画圆：", 10, 20, paint);// 画文本
        canvas.drawCircle(60, 20, 10, paint);// 小圆

        int itemL = 80;
        Position[][] res = genRects(this.getMeasuredWidth(), this.getMeasuredHeight(),
                5, 6,
                itemL, 0);
        this.items = genItems(res);
        //绘制图形
        drawMap();
    }
}

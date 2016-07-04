package com.atongmu.matchit.android;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.atongmu.matchit.R;
import com.atongmu.matchit.algo.MatchIt;
import com.atongmu.matchit.entity.Item;
import com.atongmu.matchit.entity.Point;
import com.atongmu.matchit.entity.Position;
import com.atongmu.matchit.entity.Solution;

import static java.lang.Thread.sleep;

/**
 * Created by mfg on 16/07/03.
 */
public class DrawSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder holder;
    private RenderThread renderThread;
    private boolean isDraw =false;

    private Paint paint = new Paint();
    private Canvas canvas;
    private Item[][] items;
    private int marginL = 1;
    private int sizeL = 80;

    private Item oldItem;

    public DrawSurfaceView(Context context) {
        super(context);
        holder = this.getHolder();
        holder.addCallback(this);

        this.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                onClickHandler(view, motionEvent);
                return false;
            }
        });
        //创建一个绘图线程
        //renderThread = new RenderThread();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isDraw = true;
        items = genItems(5, 6);
        drawMap();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isDraw = false;
    }

    private void onClickHandler(View view, MotionEvent event) {
        int eventAction = event.getAction();

        int clickX;
        int clickY;

        clickX = (int) event.getX();
        clickY = (int) event.getY();
        System.out.println(clickX + "-" + clickY);

        try {
            canvas = holder.lockCanvas();
            drawItems(canvas);
            switch (eventAction) {
                //点击事件
                case MotionEvent.ACTION_DOWN:
                    clickX = (int) event.getX();
                    clickY = (int) event.getY();
                    System.out.println(clickX + "-" + clickY);
                    Item item = findItem(clickX, clickY);
                    if (item != null && item.getValue()!=0) {
                        if(null == oldItem){
                            oldItem = item;
                        }else if(item.getValue() == oldItem.getValue()){
                            //检查是否连通
                            Solution solution = check(items, item, oldItem);
                            //绘制连通
                            if(solution.getValue() == Solution.WRONG){
                                oldItem = item;
                            }else {
                                paintConnect(canvas, item, oldItem, solution);
                                drawItems(canvas);
                                oldItem = null;
                            }
                        }else {
                            oldItem = item;
                        }
                    }
                    if(null != oldItem)
                        choose(canvas, oldItem);
                    break;

                case MotionEvent.ACTION_MOVE:
                    break;

                case MotionEvent.ACTION_UP:
                    break;
            }
        } catch (Exception e) {
            Log.e("Wrong", e.toString());
        } finally {
            if (canvas != null) {
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }

    /**
     * 负责绘制背景中的元素
     * @param canvas
     */
    private void drawItems(Canvas canvas) {
        canvas.drawColor(Color.WHITE);

        paint.setColor(Color.GRAY);// 设置灰色
        paint.setStyle(Paint.Style.FILL);//设置填满

        Item item;
        for (int i = 0; i < items.length; i++) {
            for (int j = 0; j < items[0].length; j++) {
                System.out.println(items[i][j].getPosition().getX() + ","
                        + items[i][j].getPosition().getY());

                item = items[i][j];
                if(item.getValue() == 0)
                    continue;
                Rect rect = new Rect(item.getPosition().getX(),
                        item.getPosition().getY(),
                        item.getPosition().getX() + item.getSizeL() - marginL,
                        item.getPosition().getY() + item.getSizeL() - marginL);
                canvas.drawBitmap(item.getBitmap(), null, rect, paint);
            }
        }
    }

    /**
     * 绘制选择框
     * @param canvas
     * @param item
     */
    public void choose(Canvas canvas, Item item) {
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3.0f);
        Position[] vertex = item.getVertexPosition();
        Path path = new Path();
        path.moveTo(vertex[0].getX(), vertex[0].getY());
        path.lineTo(vertex[1].getX(), vertex[1].getY());
        path.lineTo(vertex[2].getX(), vertex[2].getY());
        path.lineTo(vertex[3].getX(), vertex[3].getY());
        path.close();
        canvas.drawPath(path, paint);
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

    public void paintConnect(Canvas canvas, Item item1, Item item2, Solution solution) throws Exception{
        choose(canvas, item2);
        connect(canvas, item1, item2, solution);
        sleep(500);
        clearItem(this.items, item1);
        clearItem(this.items, item2);
    }

    /**
     * 清除item的值
     * @param items
     * @param item
     */
    public void clearItem(Item[][] items, Item item){
        for (int i = 0; i < items.length; i++) {
            for (int j = 0; j < items[0].length; j++) {
                if(item.getPosition().equals(items[i][j].getPosition())){
                    items[i][j].setValue(0);
                    break;
                }
            }
        }
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

    /**
     * 检查是否连通
     * @param items
     * @param item1
     * @param item2
     * @return
     */
    private Solution check(Item[][] items, Item item1, Item item2){
        Position position1 = item1.getPosition();
        Position position2 = item2.getPosition();
        Point[][] points = new Point[items.length][items[0].length];
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points[0].length; j++) {
                points[i][j] = items[i][j].getPoint();
            }
        }
        return MatchIt.match(points, position1, position2);
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

    /**
     * 生成Items数组，带边框
     * @param x
     * @param y
     * @return
     */
    private Item[][] genItems(int x, int y) {
        Position[][] positions = genRects(this.getWidth(), this.getHeight(),
                x, y,
                sizeL, 0);
        int xL = positions.length+2;
        int yL = positions[0].length+2;
        Item[][] res = new Item[xL][yL];
        for (int i = 0; i < xL; i++) {
            for (int j = 0; j < yL; j++) {
                res[i][j] = new Item(positions[i][j]);
                if(i==0 || j==0 ||i==xL-1||j==yL-1){
                    res[i][j].setValue(0);
                }else {
                    res[i][j].setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.hero_aatrox));
                    res[i][j].setValue(1);
                    res[i][j].setSizeL(sizeL);
                }
            }
        }
        return res;
    }

    @Deprecated
    private Item[][] genItems_ex(int x, int y) {
        Position[][] positions = genRects(this.getWidth(), this.getHeight(),
                x, y,
                sizeL, 0);
        Item[][] res = new Item[positions.length][positions[0].length];
        for (int i = 0; i < positions.length; i++) {
            for (int j = 0; j < positions[0].length; j++) {
                res[i][j] = new Item(positions[i][j]);
                res[i][j].setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.hero_aatrox));
                res[i][j].setValue(1);
                res[i][j].setSizeL(sizeL);
            }
        }
        return res;
    }

    private void drawMap() {
        try {
            canvas = holder.lockCanvas();
            canvas.drawColor(Color.WHITE);

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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (canvas != null) {
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }

    private class RenderThread extends Thread {
        @Override
        public void run() {
            try {
                // 不停绘制界面
                while (isDraw) {

                    drawUI();
                    sleep(1000);

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 界面绘制
     */
    public void drawUI() {
        Canvas canvas = holder.lockCanvas();
        try {
            canvas.drawColor(Color.WHITE);
            paint.setColor(Color.RED);// 设置红色

            canvas.drawText("画圆：", 10, 20, paint);// 画文本
            canvas.drawCircle(60, 20, 10, paint);// 小圆

            drawCanvas(canvas);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            holder.unlockCanvasAndPost(canvas);
        }
    }

    private void drawCanvas(Canvas canvas) {
        // 在 canvas 上绘制需要的图形
        drawMap();
    }
}
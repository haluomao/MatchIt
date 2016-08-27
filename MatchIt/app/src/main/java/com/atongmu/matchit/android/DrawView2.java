package com.atongmu.matchit.android;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.view.MotionEvent;
import android.view.View;

import com.atongmu.matchit.entity.Item;
import com.atongmu.matchit.entity.Position;
import com.atongmu.matchit.entity.Solution;

/**
 * Created by mfg on 16/07/01.
 * 没用的类
 */
public class DrawView2 extends View {
    Paint paint = new Paint();
    Item[][]items;
    int marginL = 1;
    Canvas canvas;

    public DrawView2(Context context) {
        super(context);
        this.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onClick(view);
            }
        });
    }

    public void choose(Canvas canvas, Item item) {
        //应该检查item是否还在？
        paint.setColor(Color.GREEN);
        Position[] vertex = item.getVertexPosition();
        Path path = new Path();
        path.moveTo(vertex[0].getX(), vertex[0].getY());// 此点为多边形的起点
        path.lineTo(vertex[1].getX(), vertex[1].getY());
        path.lineTo(vertex[2].getX(), vertex[2].getY());
        path.lineTo(vertex[3].getX(), vertex[3].getY());
        path.close();
        canvas.drawPath(path, paint);
    }

    private Item findItem(int x, int y){
        int x0= items[0][0].getPosition().getX();
        int y0= items[0][0].getPosition().getY();
        int length = items[0][0].getSizeL()+marginL;
        if(x<x0||y<y0) return null;
        int yy = (x-x0)/length;
        int xx = (y-y0)/length;
        if(xx > items.length || yy>items[0].length){
            return null;
        }
        return items[xx][yy];
    }

    private void onClick(MotionEvent event){
        int eventAction = event.getAction();

        int clickX;
        int clickY;
        switch (eventAction) {
            case MotionEvent.ACTION_DOWN:
                clickX = (int)event.getX();
                clickY = (int)event.getY();
                System.out.println(clickX+"-"+clickY);
                Item item = findItem(clickX, clickY);
                choose(canvas, item);
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
        canvas.drawBitmap(item.getBitmap(), item.getPosition().getX(),item.getPosition().getX(), paint);
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
        if(solution.getPos1()!=null) {
            canvas.drawLine(position1.getX(), position1.getY(), solution.getPos1().getX(),
                    solution.getPos1().getY(), paint);// 画线
            if (solution.getPos2() != null) {
                canvas.drawLine(solution.getPos1().getX(), solution.getPos1().getY(),
                        solution.getPos2().getX(), solution.getPos2().getY(), paint);// 画线
            }else{
                canvas.drawLine(solution.getPos1().getX(), solution.getPos1().getY(),
                        position2.getX(), position2.getY(), paint);// 画线
            }
        }else{
            canvas.drawLine(position1.getX(), position1.getY(),
                    position2.getX(), position2.getY(), paint);// 画线
        }
    }

    public void unconnect(Canvas canvas, Item item1, Item item2, Solution solution) {
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        Position position1 = item1.getCenterPosition();
        Position position2 = item2.getCenterPosition();
        if(solution.getPos1()!=null) {
            canvas.drawLine(position1.getX(), position1.getY(), solution.getPos1().getX(),
                    solution.getPos1().getY(), paint);// 画线
            if (solution.getPos2() != null) {
                canvas.drawLine(solution.getPos1().getX(), solution.getPos1().getY(),
                        solution.getPos2().getX(), solution.getPos2().getY(), paint);// 画线
            }else{
                canvas.drawLine(solution.getPos1().getX(), solution.getPos1().getY(),
                        position2.getX(), position2.getY(), paint);// 画线
            }
        }else{
            canvas.drawLine(position1.getX(), position1.getY(),
                    position2.getX(), position2.getY(), paint);// 画线
        }
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
        /*
         * 方法 说明 drawRect 绘制矩形 drawCircle 绘制圆形 drawOval 绘制椭圆 drawPath 绘制任意多边形
         * drawLine 绘制直线 drawPoin 绘制点
         */
        // 创建画笔

        paint.setColor(Color.RED);// 设置红色

        canvas.drawText("画圆：", 10, 20, paint);// 画文本
        canvas.drawCircle(60, 20, 10, paint);// 小圆
        paint.setAntiAlias(true);// 设置画笔的锯齿效果。 true是去除，大家一看效果就明白了
        canvas.drawCircle(120, 20, 20, paint);// 大圆

        canvas.drawText("画线及弧线：", 10, 60, paint);
        paint.setColor(Color.GREEN);// 设置绿色
        canvas.drawLine(60, 40, 100, 40, paint);// 画线
        canvas.drawLine(110, 40, 190, 80, paint);// 斜线
        //画笑脸弧线
        paint.setStyle(Paint.Style.STROKE);//设置空心
        RectF oval1=new RectF(150,20,180,40);
        canvas.drawArc(oval1, 180, 180, false, paint);//小弧形
        oval1.set(190, 20, 220, 40);
        canvas.drawArc(oval1, 180, 180, false, paint);//小弧形
        oval1.set(160, 30, 210, 60);
        canvas.drawArc(oval1, 0, 180, false, paint);//小弧形

        canvas.drawText("画矩形：", 10, 80, paint);
        paint.setColor(Color.GRAY);// 设置灰色
        paint.setStyle(Paint.Style.FILL);//设置填满
        canvas.drawRect(60, 60, 80, 80, paint);// 正方形
        canvas.drawRect(60, 90, 160, 100, paint);// 长方形

        canvas.drawText("画扇形和椭圆:", 10, 120, paint);
        /* 设置渐变色 这个正方形的颜色是改变的 */
        Shader mShader = new LinearGradient(0, 0, 100, 100,
                new int[] { Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW,
                        Color.LTGRAY }, null, Shader.TileMode.REPEAT); // 一个材质,打造出一个线性梯度沿著一条线。
        paint.setShader(mShader);
        // paint.setColor(Color.BLUE);
        RectF oval2 = new RectF(60, 100, 200, 240);// 设置个新的长方形，扫描测量
        canvas.drawArc(oval2, 200, 130, true, paint);
        // 画弧，第一个参数是RectF：该类是第二个参数是角度的开始，第三个参数是多少度，第四个参数是真的时候画扇形，是假的时候画弧线
        //画椭圆，把oval改一下
        oval2.set(210,100,250,130);
        canvas.drawOval(oval2, paint);

        canvas.drawText("画三角形：", 10, 200, paint);
        // 绘制这个三角形,你可以绘制任意多边形
        Path path = new Path();
        path.moveTo(80, 200);// 此点为多边形的起点
        path.lineTo(120, 250);
        path.lineTo(80, 250);
        path.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path, paint);

        // 你可以绘制很多任意多边形，比如下面画六连形
        paint.reset();//重置
        paint.setColor(Color.LTGRAY);
        paint.setStyle(Paint.Style.STROKE);//设置空心
        Path path1=new Path();
        path1.moveTo(180, 200);
        path1.lineTo(200, 200);
        path1.lineTo(210, 210);
        path1.lineTo(200, 220);
        path1.lineTo(180, 220);
        path1.lineTo(170, 210);
        path1.close();//封闭
        canvas.drawPath(path1, paint);
        /*
         * Path类封装复合(多轮廓几何图形的路径
         * 由直线段*、二次曲线,和三次方曲线，也可画以油画。drawPath(路径、油漆),要么已填充的或抚摸
         * (基于油漆的风格),或者可以用于剪断或画画的文本在路径。
         */

        //画圆角矩形
        paint.setStyle(Paint.Style.FILL);//充满
        paint.setColor(Color.LTGRAY);
        paint.setAntiAlias(true);// 设置画笔的锯齿效果
        canvas.drawText("画圆角矩形:", 10, 260, paint);
        RectF oval3 = new RectF(80, 260, 200, 300);// 设置个新的长方形
        canvas.drawRoundRect(oval3, 20, 15, paint);//第二个参数是x半径，第三个参数是y半径

        //画贝塞尔曲线
        canvas.drawText("画贝塞尔曲线:", 10, 310, paint);
        paint.reset();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.GREEN);
        Path path2=new Path();
        path2.moveTo(100, 320);//设置Path的起点
        path2.quadTo(150, 310, 170, 400); //设置贝塞尔曲线的控制点坐标和终点坐标
        canvas.drawPath(path2, paint);//画出贝塞尔曲线

        //画点
        paint.setStyle(Paint.Style.FILL);
        canvas.drawText("画点：", 10, 390, paint);
        canvas.drawPoint(60, 390, paint);//画一个点
        canvas.drawPoints(new float[]{60,400,65,400,70,400}, paint);//画多个点

        //画图片，就是贴图
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gas);
//        canvas.drawBitmap(bitmap, 250,360, paint);
    }
}

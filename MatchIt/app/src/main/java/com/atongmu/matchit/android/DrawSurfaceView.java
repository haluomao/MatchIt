package com.atongmu.matchit.android;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.atongmu.matchit.R;
import com.atongmu.matchit.algo.MatchIt;
import com.atongmu.matchit.dao.Dao;
import com.atongmu.matchit.entity.Account;
import com.atongmu.matchit.entity.ImageButton;
import com.atongmu.matchit.entity.Item;
import com.atongmu.matchit.entity.Mission;
import com.atongmu.matchit.entity.Point;
import com.atongmu.matchit.entity.Position;
import com.atongmu.matchit.entity.Solution;
import com.atongmu.matchit.service.ButtonsManager;
import com.atongmu.matchit.util.SoundPlayer;

import static java.lang.Thread.sleep;

/**
 * Created by mfg on 16/07/03.
 */
public class DrawSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder holder;
    private RenderThread renderThread;
    private boolean pause = false;
    private boolean isDraw =false;

    private Paint paint = new Paint();
    private Canvas canvas;
    private Item[][] items;
    private int marginL = 1;
    private int sizeL = 80;

    private Item oldItem;

    //用于提示的两个图片
    private Item promptItem1;
    private Item promptItem2;

    private Dao dao;
    private Context context = null;
    private Mission mission=null;

    private ButtonsManager btnMgr;
    private Rect itemsRect;
    private Rect timerRect;
    static int[] picArr = {1,2,3};


    public DrawSurfaceView(Context context) {
        super(context);
        this.context = context;
        holder = this.getHolder();
        holder.addCallback(this);

        this.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                onClickHandler(view, motionEvent);
                return false;
            }
        });
        mission = loadMission();

        SoundPlayer.init(context);

        //创建一个绘图线程
        renderThread = new RenderThread();

    }

    public DrawSurfaceView(Context context, AttributeSet attrs){
        super(context,attrs);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isDraw = true;
        mission = getNextMission(loadMission());
        items = genItems(2, 6);

        itemsRect = new Rect(0,
                items[0][0].getPosition().getY(),
                this.getWidth(),
                items[0][items[0].length-1].getPosition().getY());

        btnMgr = new ButtonsManager(this);
        renderThread.start();
        SoundPlayer.startMusic();

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isDraw = false;
        SoundPlayer.pauseMusic();
    }

    private void onClickHandler(View view, MotionEvent event) {
        int eventAction = event.getAction();

        int clickX;
        int clickY;

        clickX = (int) event.getX();
        clickY = (int) event.getY();
        System.out.println(clickX + "-" + clickY);

        try {
            canvas = holder.lockCanvas(itemsRect);
            if(!pause) {
                drawItems(canvas);
            }
            switch (eventAction) {
                //点击事件
                case MotionEvent.ACTION_DOWN:
                    clickX = (int) event.getX();
                    clickY = (int) event.getY();
                    if(!pause) {
                        Item item = findItem(clickX, clickY);
                        handleItemClick(canvas, item);
                    }
                    handleBtnClick(canvas, clickX, clickY);
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

    private void handleBtnClick(Canvas canvas, int x, int y) throws Exception{
        ImageButton imageButton=btnMgr.checkClick(x, y);
        if(imageButton==null){
            return;
        }
        System.out.println("imageButton.id:"+imageButton.id);
        switch (imageButton.id){
            case ImageButton.BUTTON_MUSIC:
                btnMgr.switchButton(canvas, paint, imageButton);
                SoundPlayer.setMusicSt(imageButton.status==0?true:false);
                break;
            case ImageButton.BUTTON_SOUND:
                btnMgr.switchButton(canvas, paint, imageButton);
                SoundPlayer.setSoundSt(imageButton.status == 0 ? true : false);
                break;
            case ImageButton.BUTTON_PROMPT:
                prompt(canvas);
                break;
            case ImageButton.BUTTON_PAUSE:
                btnMgr.switchButton(canvas, paint, imageButton);
                pause = (imageButton.status != 0 ? true : false);
                break;
            case ImageButton.BUTTON_RETRY:

                break;
            case ImageButton.BUTTON_BACK:
                //
                break;
        }
        btnMgr.drawButton(canvas, paint, imageButton);
    }

    //处理提示
    private void prompt(Canvas canvas) {
        Solution solution = transfer(MatchIt.prompt(transfer(items)));
        if(Solution.WRONG != solution.getValue()){
            promptItem1 = items[solution.getPos1().getX()][solution.getPos1().getY()];
            promptItem2 = items[solution.getPos2().getX()][solution.getPos2().getY()];
            System.out.println("promptItem1:"+promptItem1);
            System.out.println("promptItem2:"+promptItem2);
        }else {
            //no solution
        }
    }

    private void handleItemClick(Canvas canvas, Item item) throws Exception{
        if (item != null && item.getValue()!=0) {
            if(null == oldItem){
                oldItem = item;
            }else if(item.getValue() == oldItem.getValue() &&
                    !item.getPosition().equals(oldItem.getPosition())){
                //检查是否连通
                Solution solution = check(items, item, oldItem);
                //绘制连通
                if(solution.getValue() == Solution.WRONG){
                    oldItem = item;
                }else {
                    drawConnect(canvas, item, oldItem, solution);
                    if(item.equals(promptItem1) || item.equals(promptItem2) ||
                            oldItem.equals(promptItem1) || oldItem.equals(promptItem2)){
                        promptItem1=promptItem2=null;
                    }
                    drawItems(canvas);
                    drawPrompt(canvas);
                    oldItem = null;
                    if(isEmpty(items)){
                        updateAccount(mission);
                        items = genItems(6, 7);
                        drawItems(canvas);
                    }
                }
            }else {
                oldItem = item;
            }
        }
    }
    /**
     * 负责绘制背景中的元素
     * @param canvas
     */
    private void drawItems(Canvas canvas) {
        paint.setColor(Color.GRAY);// 设置灰色
        paint.setStyle(Paint.Style.FILL);//设置填满

        Item item;
        for (int i = 0; i < items.length; i++) {
            for (int j = 0; j < items[0].length; j++) {
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

    /**
     * 绘制提示选择框
     * @param canvas
     */
    public void choosePrompt(Canvas canvas) {
        if(promptItem1==null || promptItem2==null) return;
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3.0f);
        Position[] vertex = promptItem1.getVertexPosition();
        Path path = new Path();
        path.moveTo(vertex[0].getX(), vertex[0].getY());
        path.lineTo(vertex[1].getX(), vertex[1].getY());
        path.lineTo(vertex[2].getX(), vertex[2].getY());
        path.lineTo(vertex[3].getX(), vertex[3].getY());
        path.close();
        canvas.drawPath(path, paint);

        Position[] vertex2 = promptItem2.getVertexPosition();
        Path path2 = new Path();
        path2.moveTo(vertex2[0].getX(), vertex2[0].getY());
        path2.lineTo(vertex2[1].getX(), vertex2[1].getY());
        path2.lineTo(vertex2[2].getX(), vertex2[2].getY());
        path2.lineTo(vertex2[3].getX(), vertex2[3].getY());
        path2.close();
        canvas.drawPath(path2, paint);
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
        paint.setColor(Color.GREEN);
        Position position1 = item1.getCenterPosition();
        Position position2 = item2.getCenterPosition();
        if (solution.getPos1() != null) {
            Position tmp = transfer(solution.getPos1());
            canvas.drawLine(position1.getX(), position1.getY(), tmp.getX(),
                    tmp.getY(), paint);// 画线
            if (solution.getPos2() != null) {
                Position tmp2 = transfer(solution.getPos2());
                canvas.drawLine(tmp.getX(), tmp.getY(),
                        tmp2.getX(), tmp2.getY(), paint);// 画线
            } else {
                canvas.drawLine(tmp.getX(), tmp.getY(),
                        position2.getX(), position2.getY(), paint);// 画线
            }
        } else {
            canvas.drawLine(position1.getX(), position1.getY(),
                    position2.getX(), position2.getY(), paint);// 画线
        }
    }

    public synchronized void drawConnect(Canvas canvas, Item item1, Item item2, Solution solution) throws Exception{
        choose(canvas, item2);
        flush();
        connect(canvas, item1, item2, solution);
        clearItem(this.items, item1);
        clearItem(this.items, item2);
        //printItem();
    }

    public void flush(){
        holder.unlockCanvasAndPost(canvas);
        canvas = holder.lockCanvas();
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

    /**
     * 查找 返回数组中的坐标
     * @param items
     * @param item
     */
    public Position findPosition(Item[][] items, Item item){
        for (int i = 0; i < items.length; i++) {
            for (int j = 0; j < items[0].length; j++) {
                if(item.getPosition().equals(items[i][j].getPosition())){
                    return new Position(j, i);
                }
            }
        }
        return null;
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
    public boolean isEmpty(Item[][]items){
        for(int i=0; i<items.length; i++){
            for(int j=0; j<items[0].length; j++){
                if(items[i][j].getValue()!=0){
                    return false;
                }
            }
        }
        return true;
    }
    private int genId(int resId){
        int res = 0;
        switch (resId){
            case 1:
                res = R.drawable.hero_aatrox;
                break;
            case 2:
                res = R.drawable.hero_ahri;
                break;
            case 3:
                res = R.drawable.hero_akali;
                break;
            default:
                res = R.drawable.hero_aatrox;
                break;
        }
        return res;
    }
    private Bitmap getRes(int id){
        return BitmapFactory.decodeResource(getResources(), genId(id));
    }
    /**
     * 将矩阵中的位置映射到地图中
     * @param position
     * @return
     */
    public Position transfer(Position position){
        return items[position.getX()][position.getY()].getCenterPosition();
    }

    public Solution transfer(Solution solution){
        if(null!=solution.getPos1())
            solution.setPos1(new Position(solution.getPos1().getY(), solution.getPos1().getX()));
        if(null!=solution.getPos2())
            solution.setPos2(new Position(solution.getPos2().getY(), solution.getPos2().getX()));
        return solution;
    }

    public Point[][] transfer(Item[][]items){
        Point[][] points = new Point[items[0].length][items.length];
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points[0].length; j++) {
                points[i][j] = new Point(new Position(i,j),items[j][i].getValue() );
            }
        }
        return points;
    }

    /**
     * 检查是否连通
     * @param items
     * @param item1
     * @param item2
     * @return
     */
    private Solution check(Item[][] items, Item item1, Item item2){
        Position position1 = findPosition(items, item1);
        System.out.println("position1:"+position1);
        Position position2 = findPosition(items, item2);
        System.out.println("position2:"+position2);
        Point[][] points = new Point[items[0].length][items.length];
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points[0].length; j++) {
                points[i][j] = new Point(new Position(i,j),items[j][i].getValue() );
            }
        }
        System.out.println("checking");
        printPoints(points);
        Solution solution =  MatchIt.match(points, position1, position2);
        return transfer(solution);
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

        int xL = x+2;
        int yL = y+2;

        Position[][] positions = genRects(
                this.getWidth(), this.getHeight(),
                xL, yL,
                sizeL, 0);
        int[][] values=MatchIt.genMap(x, y, picArr);
        Item[][] res = new Item[xL][yL];
        for (int i = 0; i < xL; i++) {
            for (int j = 0; j < yL; j++) {
                res[i][j] = new Item(positions[i][j]);
                if(i==0 || j==0 ||i==xL-1||j==yL-1){
                    res[i][j].setValue(0);
                    res[i][j].setSizeL(sizeL);
                }else {
                    res[i][j].setValue(values[i][j]);
                    res[i][j].setBitmap(getRes(res[i][j].getValue()));
                    res[i][j].setSizeL(sizeL);
                }
            }
        }
        return res;
    }

    public void printPoints(Point[][]points){
        System.out.print("points:");
        for (int i = 0; i < points.length; i++) {
            System.out.println();
            for (int j = 0; j < points[0].length; j++) {
                System.out.print(points[i][j].getValue() + " ");
            }
        }
        System.out.println("\n----");
    }

    public void printItem(){
        System.out.println("Items:");
        for (int i = 0; i < items.length; i++) {
            System.out.println();
            for (int j = 0; j < items[0].length; j++) {
                System.out.print(items[i][j].getValue()+" ");
            }
        }
        System.out.print("\n----\n");
    }

    private Mission loadMission(){
        dao = new Dao(context, Account.ACCOUNT);
        int missionId =0;
        if(-1 != dao.getInt(Account.MISSION_ID))
            missionId = dao.getInt(Account.MISSION_ID)+1;
        System.out.println("已读取文件！missionId："+missionId);
        return new Mission(missionId, "mission "+missionId);
    }

    private void updateAccount(Mission mission){
        dao = new Dao(context, Account.ACCOUNT);
        dao.put(Account.UID, 1);
        dao.put(Account.NAME, "huhansan");
        dao.put(Account.MISSION_ID, mission.getId());
        System.out.println("已更新文件！");
    }

    private Mission getNextMission(Mission mission){
        dao = new Dao(context, Mission.MISSION);
        //read from mission file
        return new Mission(mission.getId(), "mission "+mission.getId());
    }

    /************************************************/
    private class RenderThread extends Thread {
        @Override
        public void run() {
            try {
                // 不停绘制界面
                while (isDraw) {
                    drawUI();
                    sleep(10);
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
        Canvas canvas = holder.lockCanvas(null);
        try {
            canvas.drawColor(Color.WHITE);
            drawBackground(canvas);
            if(!pause) {
                drawItems(canvas);
                drawPrompt(canvas);
                drawChoose(canvas);
            }else{
                drawPause(canvas);
            }
            drawTimeBar(canvas);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            holder.unlockCanvasAndPost(canvas);
        }
    }

    public void drawBackground(Canvas canvas) {
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bgp),
                0, 0, paint);

        btnMgr.drawButtons(canvas, paint);

    }

    private long time = 60000;
    private long time1 = System.currentTimeMillis();
    private String timeText="0.00秒";
    public void drawTimeBar(Canvas canvas) {
        if(!pause){
            long tmp = System.currentTimeMillis();
            time = time-(tmp-time1);
            time1 = tmp;
        }

        if(time>0){
            timeText = time/1000+"秒";
        }
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(50.0f);//设置字体大小
        //paint.setTypeface(T);//设置字体类型
        canvas.drawText(timeText, 25, 60, paint);
    }


    public void drawChoose(Canvas canvas) {
        if(null!=oldItem)
            choose(canvas, oldItem);
    }

    public void drawPrompt(Canvas canvas){
        choosePrompt(canvas);
    }

    public void drawPause(Canvas canvas){
        if(pause){
            Bitmap pauseBitmap =BitmapFactory.decodeResource(getResources(), R.drawable.pause3);
            canvas.drawBitmap(pauseBitmap, null,
                    new Rect(this.getWidth()/2-200,
                            this.getHeight()/2-200,
                            this.getWidth()/2+200,
                            this.getHeight()/2+200),
                    paint);
        }
    }
}
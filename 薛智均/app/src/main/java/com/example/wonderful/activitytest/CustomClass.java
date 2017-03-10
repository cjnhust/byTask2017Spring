package com.example.wonderful.activitytest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import android.view.View.OnTouchListener;


public class CustomClass extends View implements OnTouchListener{
    protected void initScreenW_H() {
        screenHeight = getResources().getDisplayMetrics().heightPixels - 30;
        screenWidth = getResources().getDisplayMetrics().widthPixels;
    }

    public CustomClass(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(this);
        initScreenW_H();
    }

    public CustomClass(Context context) {
        super(context);
        setOnTouchListener(this);
        initScreenW_H();
    }

    public CustomClass(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnTouchListener(this);
        initScreenW_H();
    }

    public void SetNum(int textNum){
        this.textNum=textNum;
    }

    public void SetSlect(int select){this.select=select;}

    public void SetColor(int color){this.color=color;}

    Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);
    protected int screenWidth;
    protected int screenHeight;
    protected int lastX;
    protected int lastY;
    private int oriLeft;
    private int oriRight;
    private int oriTop;
    private int oriBottom;
    private int dragDirection;
    private static final int TOP = 0x15;
    private static final int LEFT = 0x16;
    private static final int BOTTOM = 0x17;
    private static final int RIGHT = 0x18;
    private static final int LEFT_TOP = 0x11;
    private static final int RIGHT_TOP = 0x12;
    private static final int LEFT_BOTTOM = 0x13;
    private static final int RIGHT_BOTTOM = 0x14;
    private static final int CENTER = 0x19;
    private int offset = 20;

    //重构OnDraw函数
    int textNum=3;  //用来储存多边形的边数
    int select=0;   //用来控制自定义模式
    int color=0;
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(4.0f);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(offset, offset, getWidth() - offset, getHeight()
                - offset, paint);


        if (select==2){
        drawStar(canvas,paint,color,getHeight()/12,textNum,false);
        canvas.translate(getHeight()/6,0);
        drawStar(canvas,paint,color,getHeight()/12,textNum,true);
        canvas.translate(-getHeight()/6,0);
        canvas.translate(0,getHeight()/2);
        }

    }

    //自定义函数用于绘制多边形
    private void drawStar(Canvas canvas,Paint paint,@ColorInt int color,float radius,int count,boolean isStar){
        canvas.translate(radius,radius);
        if((!isStar)&&count<3){
            Toast.makeText(getContext(),"当前输入错误，输入的边数必须大于等于3",Toast.LENGTH_SHORT).show();//通过getContext()来获取当前对象的上下文
            canvas.translate(-radius,-radius);
            return;
    }
        if(isStar&&count<5){
            canvas.translate(-radius,-radius);
            return;
        }
        canvas.rotate(-90);
        if(paint==null){
            paint=new Paint();
            }else{
            paint.reset();
        }
        Path path=new Path();
        float inerRadius=count%2==0?
                (radius*(cos(360/count/2)-sin(360/count/2)*sin(90-360/count)/cos(90-360/count)))
                :(radius*sin(360/count/4)/sin(180-360/count/2-360/count/4));
        for(int i=0;i<count;i++){
            if(i==0){
                path.moveTo(radius*cos(360/count*i),radius*sin(360/count*i));
                }else{
                path.lineTo(radius*cos(360/count*i),radius*sin(360/count*i));
                }
            if(isStar){
            path.lineTo(inerRadius*cos(360/count*i+360/count/2),inerRadius*sin(360/count*i+360/count/2));
            }
        }
        path.close();
        paint.setColor(color);
        canvas.drawPath(path,paint);
        canvas.rotate(90);
        canvas.translate(-radius,-radius);
    }


    //自定义sin和cos函数，在drawstar中调用
    float sin(int num) {
        return (float) Math.sin(num * Math.PI / 180);
    }

    float cos(int num){
        return (float) Math.cos(num*Math.PI/180);
        }

    /**
     * 初始化获取屏幕宽高
     */
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        /*获取屏幕位置以及手指动作*/
        int action = motionEvent.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            oriLeft = view.getLeft();
            oriRight = view.getRight();
            oriTop = view.getTop();
            oriBottom = view.getBottom();
            lastY = (int) motionEvent.getRawY();
            lastX = (int) motionEvent.getRawX();
            dragDirection = getDirection(view, (int) motionEvent.getX(),
                    (int) motionEvent.getY());
        }
        delDrag(view, motionEvent, action);
        invalidate();
        return false;
    }
    /**
     * 处理拖动事件
     */
    protected void delDrag(View v, MotionEvent event, int action) {
        switch (action) {
            case MotionEvent.ACTION_MOVE:
                int dx = (int) event.getRawX() - lastX;
                int dy = (int) event.getRawY() - lastY;
                switch (dragDirection) {
                    case LEFT: // 左边缘
                        left(v, dx);
                        break;
                    case RIGHT: // 右边缘
                        right(v, dx);
                        break;
                    case BOTTOM: // 下边缘
                        bottom(v, dy);
                        break;
                    case TOP: // 上边缘
                        top(v, dy);
                        break;
                    case CENTER: // 点击中心-->>移动
                        center(v, dx, dy);
                        break;
                    case LEFT_BOTTOM: // 左下
                        left(v, dx);
                        bottom(v, dy);
                        break;
                    case LEFT_TOP: // 左上
                        left(v, dx);
                        top(v, dy);
                        break;
                    case RIGHT_BOTTOM: // 右下
                        right(v, dx);
                        bottom(v, dy);
                        break;
                    case RIGHT_TOP: // 右上
                        right(v, dx);
                        top(v, dy);
                        break;
                }
                if (dragDirection != CENTER) {
                    v.layout(oriLeft, oriTop, oriRight, oriBottom);
                }
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                dragDirection = 0;
                break;
        }
    }

    /**
     * 触摸点为中心->>移动
     */
    private void center(View v, int dx, int dy) {
        int left = v.getLeft() + dx;
        int top = v.getTop() + dy;
        int right = v.getRight() + dx;
        int bottom = v.getBottom() + dy;
        if (left < -offset) {
            left = -offset;
            right = left + v.getWidth();
        }
        if (right > screenWidth + offset) {
            right = screenWidth + offset;
            left = right - v.getWidth();
        }
        if (top < -offset) {
            top = -offset;
            bottom = top + v.getHeight();
        }
        if (bottom > screenHeight + offset) {
            bottom = screenHeight + offset;
            top = bottom - v.getHeight();
        }
        v.layout(left, top, right, bottom);
    }

    /**
     * 触摸点为上边缘
     */
    private void top(View v, int dy) {
        oriTop += dy;
        if (oriTop < -offset) {
            oriTop = -offset;
        }
        if (oriBottom - oriTop - 2 * offset < 200) {
            oriTop = oriBottom - 2 * offset - 200;
        }
    }

    /**
     * 触摸点为下边缘
     */
    private void bottom(View v, int dy) {
        oriBottom += dy;
        if (oriBottom > screenHeight + offset) {
            oriBottom = screenHeight + offset;
        }
        if (oriBottom - oriTop - 2 * offset < 200) {
            oriBottom = 200 + oriTop + 2 * offset;
        }
    }

    /**
     * 触摸点为右边缘
     */
    private void right(View v, int dx) {
        oriRight += dx;
        if (oriRight > screenWidth + offset) {
            oriRight = screenWidth + offset;
        }
        if (oriRight - oriLeft - 2 * offset < 200) {
            oriRight = oriLeft + 2 * offset + 200;
        }
    }

    /**
     * 触摸点为左边缘
     */
    private void left(View v, int dx) {
        oriLeft += dx;
        if (oriLeft < -offset) {
            oriLeft = -offset;
        }
        if (oriRight - oriLeft - 2 * offset < 200) {
            oriLeft = oriRight - 2 * offset - 200;
        }
    }

    /**
     * 获取触摸点flag
     */
    protected int getDirection(View v, int x, int y) {
        int left = v.getLeft();
        int right = v.getRight();
        int bottom = v.getBottom();
        int top = v.getTop();
        if (x < 40 && y < 40) {
            return LEFT_TOP;
        }
        if (y < 40 && right - left - x < 40) {
            return RIGHT_TOP;
        }
        if (x < 40 && bottom - top - y < 40) {
            return LEFT_BOTTOM;
        }
        if (right - left - x < 40 && bottom - top - y < 40) {
            return RIGHT_BOTTOM;
        }
        if (x < 40) {
            return LEFT;
        }
        if (y < 40) {
            return TOP;
        }
        if (right - left - x < 40) {
            return RIGHT;
        }
        if (bottom - top - y < 40) {
            return BOTTOM;
        }
        return CENTER;
    }

}








package com.example.bingyantest;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.renderscript.Sampler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import static java.lang.String.valueOf;

/**
 * Created by HP on 2017/3/6.
 * 边的计算方法：利用多边形对应的外接园半径 R
 */

public class MyView extends android.support.v7.widget.AppCompatImageView {


    private Paint mpaint;
    private float radius, start_x1, start_y1;
    private float m = 30;
    private float UNIT = 360 / m;
    private float Xo,Yo;                    //记录最初的X 和 Y的位置
    int r = 255,g = 0,b = 0,a = 255;
    float mW;
    float mH;
    int judge = 0;

    public MyView(Context context) {
        this(context, null);
    }

    public MyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mpaint = new Paint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mW = getMeasuredWidth();
        mH = getMeasuredHeight();


        start_x1 = mW / 2;
        start_y1 = mH / 2;
        Xo = start_x1;
        Yo = start_y1;
        radius = Math.min(mW, mH) / 3;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mpaint.setStyle(Paint.Style.FILL);
        mpaint.setStrokeWidth(6);
        Path path = new Path();
        int j = (int) m;
        mpaint.setARGB(a,r,g,b);


        if (j >= 24) {                    //绘制圆形代替多边形
            canvas.drawCircle(Xo, Yo, radius, mpaint);
        } else if (m > 2 && m < 24){        //绘制多边形
            canvas.translate(Xo,Yo);

            for (int i = 0; i < m;i++){
                start_x1 = (float)(Math.cos(i * Math.toRadians(UNIT)) * radius);
                start_y1 = (float)(Math.sin(i * Math.toRadians(UNIT)) * radius);
                if (i == 0){
                    path.moveTo(start_x1,start_y1);
                }else {
                    path.lineTo(start_x1,start_y1);
                }
            }
            path.close();
            canvas.drawPath(path,mpaint);
            }
        }

    public void refresh(float m,int judge) {//获取rgb以及边的条数，刷新view
        this.m = m;
        this.judge = judge;
        UNIT = 360 / this.m;
        invalidate();
    }

    public void setR(int r){
        this.r = r;
        requestLayout();
        invalidate();
    }

    public void setG(int g){
        this.g = g;
        requestLayout();
        invalidate();
    }

    public void setB(int b){
        this.b = b;
        requestLayout();
        invalidate();
    }



    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    private int mode = NONE;

    private float beforeLenght;
    private float afterLenght;

    private int screenW;
    private int screenH;

    private int start_x;
    private int start_y;
    private int stop_x;
    private int stop_y;
    private TranslateAnimation trans;
    float gapLenght;

    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float)Math.sqrt(x*x + y*y);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mode = DRAG;
                stop_x = (int) event.getRawX();
                stop_y = (int) event.getRawY();
                start_x = (int) event.getX();
                start_y = stop_y - this.getTop();
                if(event.getPointerCount()==2)
                    beforeLenght = spacing(event);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                if (spacing(event) > 10f) {
                    mode = ZOOM;
                    beforeLenght = spacing(event);
                }
                break;
            case MotionEvent.ACTION_UP:

                trans = new TranslateAnimation(0, 0, 0, 0);
                trans.setDuration(500);
                this.startAnimation(trans);
                mode = NONE;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;
                break;
            case MotionEvent.ACTION_MOVE:
                /*处理拖动*/
                if (mode == DRAG) {
                    if(Math.abs(stop_x-start_x-getLeft())<88 && Math.abs(stop_y - start_y-getTop())<85)
                    {
                        this.setPosition(stop_x - start_x, stop_y - start_y, stop_x + this.getWidth() - start_x, stop_y - start_y + this.getHeight());
                        stop_x = (int) event.getRawX();
                        stop_y = (int) event.getRawY();
                    }
                }
                /*处理缩放*/
                else if (mode == ZOOM) {
                    if(spacing(event)>10f)
                    {
                        afterLenght = spacing(event);
                        gapLenght = afterLenght - beforeLenght;
                        if(gapLenght == 0) {
                            break;
                        }
                        else if(Math.abs(gapLenght)>5f)
                        {
                            this.setScale();
                            beforeLenght = afterLenght;
                        }
                    }
                }
                break;
        }
        Log.d("520", "onTouchEvent: "+radius);
        return true;
    }


    /**
     * 实现处理缩放
     */
    private void setScale() {
            ReSetRadius(radius,afterLenght,gapLenght);
    }

    /**
     * 实现处理拖动
     */
    private void setPosition(int left,int top,int right,int bottom) {
        this.layout(left,top,right,bottom);
    }

    private void ReSetRadius(float radius,float afterLenght,float gapLenght){
        this.radius = radius * afterLenght/(afterLenght - gapLenght);
        invalidate();
    }
}

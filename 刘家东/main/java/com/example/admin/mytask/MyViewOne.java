package com.example.admin.mytask;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by admin on 2017/3/5.
 */

public class MyViewOne extends View {
    private int n;//几边形
    private int width,height;//View的宽和高
    private int r;
    private float x, y;//n边形顶点坐标
    private float angle;//n边形角度

    public MyViewOne(Context context){
        super(context);
    }

    public MyViewOne(Context context, AttributeSet attrs){
        super(context,attrs);
        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.Num_n);
        this.n=typedArray.getInt(R.styleable.Num_n_n,1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        //初始化画笔
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        paint.setTextSize(30);
        //获取View的宽和高
        width = this.getWidth();
        height = this.getHeight();
        if (n>2&&n<41) {
            canvas.translate(width / 2, height / 2);//将画布移到中心
            Path path = new Path();
            r = 180;
            angle = (float) ((2 * Math.PI) / n);
            for (int i = 1; i <= n; i++) {
                x = (float) (Math.cos(i * angle) * r);
                y = (float) (Math.sin(i * angle) * r);
                if (i == 1) {
                    path.moveTo(x, y);
                } else {
                    path.lineTo(x, y);
                }
            }
            path.close();
            canvas.drawPath(path, paint);
        }
        else {
            if (n>40){
                canvas.translate(width / 2, height / 2);
                canvas.drawCircle(0,0,180,paint);
            }
            else {
                paint.setStrokeWidth(0);
                canvas.translate(0, 0);
                canvas.drawText("请输入大于2的边数", 0, height / 2, paint);
            }
        }
    }

    public void set_n(int num){
        this.n=num;
    }
}

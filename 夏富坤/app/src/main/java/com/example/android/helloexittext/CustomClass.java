package com.example.android.helloexittext;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;


/**
 * Created by Administrator on 2017/3/6.
 */

public class CustomClass extends ImageView {
    private String imagePath;
    public void setImagePath(String imagePath){
        this.imagePath=imagePath;
    }
    private int select=0;
    public void setSelect(int select){
        this.select=select;
    }
 public String getImagePath(){
     return imagePath;
 }
    public int getSelect(){
        return select;
    }
    Paint paint;

    public CustomClass(Context context) {
        super(context);
        init1();
    }

    public CustomClass(Context context, AttributeSet attrs) {

        super(context, attrs);
        init1();

    }

    public CustomClass(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

Bitmap bitmap;
  int mN=3;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomClass(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private static final String TAG = "CustomClass";

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//getDrawable()

       /* Paint paint=new Paint();
        Bitmap bitmap= BitmapFactory.decodeResource(this.getResources(),R.drawable.bg);
        canvas.drawBitmap(bitmap,0,0,paint);
        if (bitmap.isRecycled()){
            bitmap.recycle();*/



        Log.i(TAG, "onDraw: ");





            paint.setAntiAlias(true);

            drawStar(canvas,paint, Color.RED,getHeight()/2,mN,false);
            canvas.translate(getHeight()/12,0);
           /* int count=mN;
            float radius=getHeight()/2;
            canvas.rotate(-90);



            for (int i=0;i<count;i++){
                if (i==0){
                    path.moveTo(radius*cos(360/count*i),radius*sin(360/count*i));
                }else{
                    path.lineTo(radius*cos(360/count*i),radius*sin(360/count*i));
                }

            }
            paint.reset();
            path.close();
            paint.setColor(Color.RED);
            canvas.rotate(90);
            canvas.translate(-radius,-radius);
            canvas.drawPath(path,paint);
            canvas.translate(getHeight()/6,0);*/







    }
    private void drawStar(Canvas canvas, Paint paint, @ColorInt int color, float radius, int count, boolean isStar){
        canvas.translate(radius,radius);
        if ((!isStar) && count < 3){
            canvas.translate(-radius,-radius);
            return;
        }
        if (isStar && count < 5){
            canvas.translate(-radius,-radius);
            return;
        }
        canvas.rotate(-90);
       /* if (paint == null){
            paint = new Paint();
        }else{
            paint.reset();
        }*/
        Path path = new Path();
        float inerRadius = count%2==0?
                (radius*(cos(360/count/2)-sin(360/count/2)*sin(90-360/count)/cos(90-360/count)))
                :(radius*sin(360/count/4)/sin(180-360/count/2-360/count/4));
        for (int i=0;i<count;i++){
            if (i==0){
                path.moveTo(radius*cos(360/count*i),radius*sin(360/count*i));
            }else{
                path.lineTo(radius*cos(360/count*i),radius*sin(360/count*i));
            }
            if (isStar){
                path.lineTo(inerRadius*cos(360/count*i+360/count/2),inerRadius*sin(360/count*i+360/count/2));
            }
        }
        path.close();
        paint.setColor(color);
        canvas.drawPath(path,paint);
        canvas.rotate(90);
        canvas.translate(-radius,-radius);

    }



    /**
     * Math.sin的参数为弧度，使用起来不方便，重新封装一个根据角度求sin的方法
     * @param num 角度
     * @return
     */
    float sin(int num){
        return (float) Math.sin(num*Math.PI/180);
    }

    /**
     * 与sin同理
     */
    float cos(int num){
        return (float) Math.cos(num*Math.PI/180);
    }

    public void setMap(int n){
        if(n<=45)
     mN=n;
        else mN=45;
        invalidate();


    }
    public  void init1() {
        if (select == 1) {
            bitmap = BitmapFactory.decodeFile(getImagePath());

            BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
             paint = new Paint();
            paint.setShader(bitmapShader);


        }
        else paint=new Paint();
    }
public void hhh(){
    invalidate();;
}


}

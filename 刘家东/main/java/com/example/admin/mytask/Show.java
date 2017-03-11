package com.example.admin.mytask;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Created by admin on 2017/3/8.
 */

public class Show extends Activity{

    ImageView imageView;
    String path;
    Bitmap bitmap=null;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_show);
        intent=getIntent();
        Bundle bundle=intent.getExtras();
        path=bundle.getString("path");
        imageView=(ImageView) findViewById(R.id.show);

        //获取屏幕分辨率
        WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        bitmap=TaskTwoPicture.handle_bitmap(path,display.getWidth(),display.getHeight());
        imageView.setImageBitmap(bitmap);
    }

    public void sure(View view){
        if(bitmap != null && !bitmap.isRecycled()){
            bitmap.recycle();
            bitmap = null;
        }
        System.gc();
        intent.putExtra("path",path);
        setResult(0,intent);
        finish();
    }

    public void cancel(View view){
        if(bitmap != null && !bitmap.isRecycled()){
            bitmap.recycle();
            bitmap = null;
        }
        System.gc();
        intent.putExtra("path","");
        setResult(0,intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if(bitmap != null && !bitmap.isRecycled()){
            bitmap.recycle();
            bitmap = null;
        }
        System.gc();
        intent.putExtra("path","");
        setResult(0,intent);
        super.onBackPressed();
    }
}

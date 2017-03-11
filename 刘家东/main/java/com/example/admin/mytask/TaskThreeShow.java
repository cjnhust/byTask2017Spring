package com.example.admin.mytask;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by admin on 2017/3/9.
 */

public class TaskThreeShow extends Activity {

    private Bitmap bitmap=null;
    private MyViewThree myViewThree;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_three);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        String path=bundle.getString("path");
        if (path.isEmpty()){
            bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.empty_photo);
        }
        else {
            WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
            Display display = windowManager.getDefaultDisplay();
            bitmap=TaskTwoPicture.handle_bitmap(path,display.getWidth(),display.getHeight());
        }
        myViewThree=(MyViewThree) findViewById(R.id.myViewThree);
        myViewThree.setImageBitmap(bitmap);
    }

    @Override
    public void onBackPressed() {
        if(bitmap != null && !bitmap.isRecycled()){
            bitmap.recycle();
            bitmap = null;
        }
        System.gc();
        super.onBackPressed();
    }
}

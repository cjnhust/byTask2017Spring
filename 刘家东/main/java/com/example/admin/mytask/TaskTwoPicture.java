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

public class TaskTwoPicture extends Activity {
    private ImageView imageView;
    private Bitmap bitmap=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_two_picture_show);
        imageView= (ImageView) findViewById(R.id.picture_show);
    }

    public void picture_chose(View view){
        Intent intent=new Intent(this,PictureChose.class);
        startActivityForResult(intent,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==0&&resultCode==0){
            Bundle bundle=new Bundle();
            bundle=data.getExtras();
            String picture_path=bundle.getString("path");
            if (!picture_path.equals("")) {
                if(bitmap != null && !bitmap.isRecycled()){
                    bitmap.recycle();
                    bitmap = null;
                }
                System.gc();
                WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
                Display display = windowManager.getDefaultDisplay();
                bitmap=handle_bitmap(picture_path,display.getWidth(),display.getHeight());
                imageView.setImageBitmap(bitmap);
            }
        }
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

    public static Bitmap handle_bitmap(String path,int width,int height){
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path,bmOptions);

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.min(photoW /width, photoH /height);//通过比较获取较小的缩放比列

        bmOptions.inJustDecodeBounds = false;// 将inJustDecodeBounds置为false，设置bitmap的缩放比列
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        return BitmapFactory.decodeFile(path,bmOptions);//再次decode获取bitmap
    }
}

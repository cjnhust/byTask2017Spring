package com.example.marks.intern;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;

public class MyPhotoPicker extends AppCompatActivity implements AdapterView.OnItemClickListener{
    List<String> mImgs;
    MyGridViewAdapter adapter;
    GridView gridView;
    Intent intent1;
    String parentDir;
    private File mImgDir;
    private ProgressDialog mProgressDialog;
    private Handler mHandler = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            mProgressDialog.dismiss();
            mImgs = Arrays.asList(mImgDir.list(new FilenameFilter()
            {
                @Override
                public boolean accept(File dir, String filename)
                {
                    if (filename.endsWith(".jpg")||filename.endsWith(".png"))
                        return true;
                    return false;
                }
            }));

            adapter = new MyGridViewAdapter(getApplicationContext(), mImgs,parentDir);
            gridView.setAdapter(adapter);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_photo_picker);
        intent1=getIntent();
        parentDir=intent1.getStringExtra("ImageFolderName");
        init();
        getImages();
    }
    private void init(){
        gridView= (GridView) findViewById(R.id.gridView);
        gridView.setOnItemClickListener(this);


    }
    private void getImages() {
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "暂无外部存储", Toast.LENGTH_SHORT).show();
            return;
        }
        // 显示进度条
        mProgressDialog = ProgressDialog.show(this, null, "正在加载...");

        new Thread(new Runnable() {
            @Override
            public void run()
            {

                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = MyPhotoPicker.this
                        .getContentResolver();

                // 只查询jpeg和png的图片
                Cursor mCursor = mContentResolver.query(mImageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[] { "image/jpeg", "image/png" },
                        MediaStore.Images.Media.DATE_MODIFIED);
                while (mCursor.moveToNext())
                {
                    // 获取图片的路径
                    String path = mCursor.getString(mCursor
                            .getColumnIndex(MediaStore.Images.Media.DATA));

                    // 获取该图片的父路径名
                    File parentFile = new File(path).getParentFile();
                    if (parentFile == null)
                        continue;
                    String dirPath = parentFile.getAbsolutePath();
                    if (dirPath.equals(parentDir)){
                        mImgDir=parentFile;

                        break;
                    }


                }
                mCursor.close();



                // 通知Handler扫描图片完成

                mHandler.sendEmptyMessage(0x110);
            }
        }).start();

    }

        @Override
        public void onItemClick (AdapterView < ? > parent, View view,int position, long id){

            intent1.putExtra("PhotoString", parentDir+"/"+mImgs.get(position));
            setResult(RESULT_OK, intent1);
            finish();
        }


    }


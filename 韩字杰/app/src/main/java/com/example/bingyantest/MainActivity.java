package com.example.bingyantest;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private String con;//用于获取EditText中的字符串
    private Button button;
    private Button imgbutton;
    private EditText ed;
    int r,g,b;
    int a = 255;
    SeekBar mseekbar_r,mseekbar_g,mseekbar_b;
    private MyView myView;
    private static final int IMAGE = 1;
    Bitmap bitmap;
    ImageView imageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myView = (MyView)findViewById(R.id.my_view);
        mseekbar_r = (SeekBar)findViewById(R.id.rbg_r);
        mseekbar_b = (SeekBar)findViewById(R.id.rbg_b);
        mseekbar_g = (SeekBar)findViewById(R.id.rbg_g);
        imageView = (ImageView)findViewById(R.id.img);

        mseekbar_r.setMax(255);
        mseekbar_g.setMax(255);
        mseekbar_b.setMax(255);

        mseekbar_r.setOnSeekBarChangeListener(seekBarListener);
        mseekbar_g.setOnSeekBarChangeListener(seekBarListener);
        mseekbar_b.setOnSeekBarChangeListener(seekBarListener);

        button = (Button)findViewById(R.id.confirm);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed = (EditText)findViewById(R.id.con_n);
                con = ed.getText().toString();
                if (con.equals("")){//输入的不为数字，则toast
                    Toast.makeText(MainActivity.this,"请输入一个数字",Toast.LENGTH_LONG).show();
                }else if (con.equals("0")||con.equals("1")||con.equals("2")){
                    Toast.makeText(MainActivity.this,"请输入一个有效数字",Toast.LENGTH_LONG).show();
                } else {
                    float m = (float) Integer.parseInt(con);
                    myView.refresh(m,0);
                }
            }
        });
        imgbutton = (Button)findViewById(R.id.btnimg);
        imgbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int PICK_IMAGE_REQUEST = 1;
                //调用相册
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int PICK_IMAGE_REQUEST = 1;
        //获取图片路径
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    Log.d("520", "1");

                } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //加载图片


    private SeekBar.OnSeekBarChangeListener seekBarListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            switch (seekBar.getId()){
                case R.id.rbg_r:
                    r = progress;
                    myView.setR(r);
                    break;
                case R.id.rbg_b:
                    b = progress;
                    myView.setB(b);
                    break;
                case R.id.rbg_g:
                    g = progress;
                    myView.setG(g);
                    break;
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
    
}

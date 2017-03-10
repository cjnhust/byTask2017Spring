package com.example.a13371.androidtest;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.content.ClipboardManager;

public class MainActivity extends AppCompatActivity
        implements SeekBar.OnSeekBarChangeListener {

    public int number;
    DrawView drawView;
    EditText editText;
    final String TAG = "MainActivity";
    ImageView imageView;
    SeekBar sb_r,sb_g,sb_b;
    TextView textView;
    //声明剪贴板管理器
    ClipboardManager cm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        editText=(EditText)findViewById(R.id.editText);
        number=Integer.parseInt(editText.getText().toString());
        drawView=new DrawView(this);
        this.setContentView(R.layout.activity_main);
        cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        textView = (TextView) findViewById(R.id.textview);
        sb_r = (SeekBar) findViewById(R.id.sb_r);
        sb_g = (SeekBar) findViewById(R.id.sb_g);
        sb_b = (SeekBar) findViewById(R.id.sb_b);
        sb_r.setOnSeekBarChangeListener(this);
        sb_g.setOnSeekBarChangeListener(this);
        sb_b.setOnSeekBarChangeListener(this);
        textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //将TextView上显示的文字复制到剪贴板
                    cm.setText(textView.getText());
                    Toast.makeText(MainActivity.this, "已复制！", Toast.LENGTH_SHORT).show();
                }
            });
        }
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            //1.获取三个SeekBar的进度
            int r = sb_r.getProgress();
            int g = sb_g.getProgress();
            int b = sb_b.getProgress();
            //2.为ImageView设置背景色
            int rgb = Color.rgb(r, g, b);
            imageView.setBackgroundColor(rgb);
            //3.将颜色转换为16进制表示
            String s = Integer.toHexString(rgb);
            textView.setText("#"+s.substring(2).toUpperCase());
        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    }





package com.example.admin.mytask;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

    private PopupWindow popupWindow;
    private TextView color,picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View pop=this.getLayoutInflater().inflate(R.layout.popup_tasktwo,null);
        popupWindow=new PopupWindow(pop,300,250);
        popupWindow.setAnimationStyle(R.style.PopupAnimation);

        //关于popupwindow中组件点击事件的处理
        color=(TextView) pop.findViewById(R.id.color);
        picture=(TextView)  pop.findViewById(R.id.picture);
        color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                Intent intent1=new Intent(MainActivity.this,TaskTwoColor.class);
                startActivity(intent1);
            }
        });
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                Intent intent1=new Intent(MainActivity.this,TaskTwoPicture.class);
                startActivity(intent1);
            }
        });
    }

    public void button1(View view){
        if (!popupWindow.isShowing()) {
            Intent intent = new Intent(this, TaskOne.class);
            startActivity(intent);
        }
    }

    public void button2(View view){
        popupWindow.showAtLocation(view, Gravity.CENTER,0,0);
    }

    public void button3(View view){
        if (!popupWindow.isShowing()) {
            Intent intent = new Intent(this, TaskThree.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        if (popupWindow.isShowing()){
            popupWindow.dismiss();
        }
        else {
            super.onBackPressed();
        }
    }
}

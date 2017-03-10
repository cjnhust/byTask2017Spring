package com.example.wonderful.activitytest;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;

public class FirstActivity extends AppCompatActivity {
    CustomClass customClass;
    private int red;
    private int green;
    private int blue;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_item:
                Toast.makeText(this,"You clicked Add",Toast.LENGTH_SHORT).show();
                break;
            case R.id.remove_item:
                Toast.makeText(this,"You click to remove",Toast.LENGTH_SHORT).show();

                break;
            default:
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_layout);
        customClass=(CustomClass) findViewById(R.id.my_view);

        Button button1=(Button)findViewById(R.id.sure);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNum();
                getColor();
                customClass.invalidate();
            }
        });
        Button button2=(Button)findViewById(R.id.act);
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent =new Intent(FirstActivity.this,SecondActivity.class);
                startActivity(intent);
                customClass.invalidate();
            }
        });
        RadioGroup radioGroup=(RadioGroup)findViewById(R.id.select);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.select1:
                        customClass.SetSlect(1);
                        customClass.invalidate();
                        break;
                    case R.id.select2:
                        customClass.SetSlect(2);
                        customClass.invalidate();
                        break;
                }
            }
        });


    }

    private int num1=0;
    public void getNum(){
        EditText editText1=(EditText)findViewById(R.id.edittext);
        String num=editText1.getText().toString();
        num1=Integer.parseInt(num);
        customClass.SetNum(num1);
        customClass.invalidate();
    }

    public void getColor(){
        SeekBar seekBar1=(SeekBar)findViewById(R.id.color1);
        SeekBar seekBar2=(SeekBar)findViewById(R.id.color2);
        SeekBar seekBar3=(SeekBar)findViewById(R.id.color3);

        red=seekBar1.getProgress();
        green=seekBar2.getProgress();
        blue=seekBar3.getProgress();
        customClass.SetColor(Color.argb(100,red,green,blue));

    }


}



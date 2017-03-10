package com.example.marks.intern;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{
    CustomView customView;
    EditText editText;
    SeekBar seekBar1,seekBar2,seekBar3,seekBar4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        customView= (CustomView) findViewById(R.id.CustomView);
        editText= (EditText) findViewById(R.id.editText);
        seekBar1 = (SeekBar) findViewById(R.id.seekBar1);
        seekBar2= (SeekBar) findViewById(R.id.seekBar2);
        seekBar3 = (SeekBar) findViewById(R.id.seekBar3);
        seekBar4 = (SeekBar) findViewById(R.id.seekBar4);

        seekBar1.setOnSeekBarChangeListener(this);
        seekBar2.setOnSeekBarChangeListener(this);
        seekBar3.setOnSeekBarChangeListener(this);
        seekBar4.setOnSeekBarChangeListener(this);
    }
    public void onClick(View view){
        switch (view.getId()){
            case R.id.button:
                if (editText.length()!=0){
                    int polygon= Integer.parseInt(editText.getText().toString());
                    customView.setPolygon(polygon);
                }

                break;
            case R.id.button3:
                Intent intent=new Intent(this,MyPhotoFolder.class);
                startActivityForResult(intent,1997);
                break;
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1997&&resultCode==RESULT_OK&&null!=data){
            String picturePath = data.getStringExtra("PhotoString");

            Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
            customView.setShade(bitmap);
        }


    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()){
            case R.id.seekBar1:
                customView.setA(progress);
                break;
            case R.id.seekBar2:
                customView.setR(progress);
                break;
            case R.id.seekBar3:
                customView.setG(progress);
                break;
            case R.id.seekBar4:
                customView.setB(progress);
                break;

        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}

package com.example.admin.mytask;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;

/**
 * Created by admin on 2017/3/5.
 */

public class MyViewTwoColor extends LinearLayout {
    private int color1 = 0, color2 = 0, color3 = 0;
    private SeekBar seekBar1, seekBar2, seekBar3;
    private ImageView imageView;
    private FrameLayout frame;

    public MyViewTwoColor(final Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.task_two_color_view, this);
        frame = (FrameLayout) findViewById(R.id.frame);
        imageView = (ImageView) findViewById(R.id.imageview);
        seekBar1 = (SeekBar) findViewById(R.id.seekbar1);
        seekBar2 = (SeekBar) findViewById(R.id.seekbar2);
        seekBar3 = (SeekBar) findViewById(R.id.seekbar3);

        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (frame.isShown()) {
                    frame.setVisibility(GONE);
                } else {
                    frame.setVisibility(VISIBLE);
                }
            }
        });


        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                color1 = progress;
                imageView.setBackgroundColor(Color.rgb(color1, color2, color3));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                color2 = progress;
                imageView.setBackgroundColor(Color.rgb(color1, color2, color3));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                color3 = progress;
                imageView.setBackgroundColor(Color.rgb(color1, color2, color3));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}

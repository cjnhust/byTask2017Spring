package com.example.admin.mytask;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
/**
 * Created by admin on 2017/3/5.
 */
public class TaskTwoColor extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_two_color);
    }

    @Override
    public void onBackPressed() {
        if ((findViewById(R.id.frame).isShown())){
            (findViewById(R.id.frame)).setVisibility(View.GONE);
        }
        else {
            finish();
        }
    }
}

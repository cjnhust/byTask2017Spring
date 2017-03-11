package com.example.admin.mytask;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by admin on 2017/3/5.
 */
public class TaskOne extends Activity{

    private int n;
    private MyViewOne myViewOne;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_one);
    }

    public void task1_button(View view){
        //当键盘弹出的时候防止布局整体上移
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        //输入只能是合法数字
        if (!(((EditText) findViewById(R.id.num)).getText().toString().trim().matches("\\d+"))){
            Toast.makeText(this,"请输入数字。",Toast.LENGTH_SHORT).show();
        }
        else {
            n= Integer.parseInt(((EditText) findViewById(R.id.num)).getText().toString().trim());
            myViewOne=(MyViewOne) findViewById(R.id.myViewOne);
            myViewOne.set_n(n);
        }
    }
}

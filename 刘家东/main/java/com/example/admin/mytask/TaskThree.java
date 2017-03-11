package com.example.admin.mytask;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by admin on 2017/3/5.
 */
public class TaskThree extends Activity{

    private MyGridView gridView;
    private String picture_path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gridView=new MyGridView(this,null);
        LinearLayout linearLayout=new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        linearLayout.addView(gridView);
        setContentView(linearLayout);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                picture_path=MyGridView.paths.get(position);
                Intent intent = new Intent(TaskThree.this, TaskThreeShow.class);
                Bundle bundle = new Bundle();
                bundle.putString("path", picture_path);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}

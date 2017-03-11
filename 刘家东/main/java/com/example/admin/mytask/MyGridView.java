package com.example.admin.mytask;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * Created by admin on 2017/3/10.
 */

public class MyGridView extends GridView {

    public static ArrayList<String> paths;
    private String[] url;
    private ImageAdapter adapter;

    private Context context;


    public MyGridView(Context context){
        super(context);
    }

    public MyGridView(Context context, AttributeSet attrs){
        super(context,attrs);
        this.context=context;
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setNumColumns(4);
        paths = new ArrayList<>();
        paths.clear();
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            byte[] path = cursor.getBlob(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            paths.add(new String(path, 0, path.length - 1));
        }
        url =paths.toArray(new String[paths.size()]);
        adapter=new ImageAdapter(context, 0, url, this);
        setAdapter(adapter);
    }
}

package com.example.admin.mytask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

/**
 * Created by admin on 2017/3/8.
 */

public class PictureChose extends Activity {
    private MyGridView gridView;
    private PopupWindow popupConfirm;
    private String picture_path="";//显示图片的路径

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gridView=new MyGridView(this,null);
        LinearLayout linearLayout=new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        linearLayout.addView(gridView);
        setContentView(linearLayout);

        //建立确认的PopupWindow
        View popConfirm=getLayoutInflater().inflate(R.layout.popup_picture_confirm,null);
        popupConfirm = new PopupWindow(popConfirm, 360, 200);
        popupConfirm.setAnimationStyle(R.style.PopupAnimation);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!popupConfirm.isShowing()) {
                    picture_path=MyGridView.paths.get(position);
                    popupConfirm.showAtLocation(view, Gravity.CENTER, 0, 0);
                } else {
                    popupConfirm.dismiss();
                }
            }
        });

        Button ok=(Button)popConfirm.findViewById(R.id.pop_ok);
        Button show=(Button)popConfirm.findViewById(R.id.pop_show);
        Button no=(Button)popConfirm.findViewById(R.id.pop_no);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupConfirm.dismiss();
                try {
                    Thread.sleep(80);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent=getIntent();
                intent.putExtra("path",picture_path);
                PictureChose.this.setResult(0,intent);
                PictureChose.this.finish();
            }
        });
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupConfirm.dismiss();
                Intent intent = new Intent(PictureChose.this, Show.class);
                Bundle bundle = new Bundle();
                bundle.putString("path", picture_path);
                intent.putExtras(bundle);
                startActivityForResult(intent,0);
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupConfirm.dismiss();
                picture_path="";
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==0&&resultCode==0){
            Bundle bundle=new Bundle();
            bundle=data.getExtras();
            picture_path=bundle.getString("path");
            if (!picture_path.equals("")) {
                Intent intent=getIntent();
                intent.putExtra("path",picture_path);
                PictureChose.this.setResult(0,intent);
                PictureChose.this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (popupConfirm.isShowing()){
            popupConfirm.dismiss();
            picture_path="";
        }
        else {
            Intent intent=getIntent();
            intent.putExtra("path",picture_path);
            PictureChose.this.setResult(0,intent);
            super.onBackPressed();
        }
    }
}
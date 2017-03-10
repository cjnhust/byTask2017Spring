package com.example.marks.intern;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by marks on 2017/3/10.
 */

public class MyGridViewAdapter extends BaseAdapter{
    private Context mContext;
    private List<String> mData;

    private LayoutInflater mInflater;
    private String parentPath;
    public MyGridViewAdapter(Context context, List<String> mData,String parentPath)
    {
        this.mContext = context;
        this.mData = mData;
        this.parentPath=parentPath;
        mInflater = LayoutInflater.from(mContext);

    }

    @Override
    public int getCount()
    {
        return mData.size();
    }

    @Override
    public Object getItem(int position)
    {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent)
    {
        ViewHolder holder ;
        if (convertView == null)
        {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.grid_item, parent,
                    false);
            holder.mImageView = (ImageView) convertView
                    .findViewById(R.id.imageView2);
            convertView.setTag(holder);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mImageView
                .setImageResource(R.drawable.friends_sends_pictures_no);
        Glide.with(mContext)
                .load(parentPath+"/"+mData.get(position))
                .into(holder.mImageView);

        return convertView;
    }

    private final class ViewHolder
    {
        ImageView mImageView;
    }
}

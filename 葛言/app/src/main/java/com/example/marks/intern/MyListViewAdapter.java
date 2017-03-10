package com.example.marks.intern;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by marks on 2017/3/10.
 */

public class MyListViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<ImageFolder> mData;
    private ImageFolder folder;
    private LayoutInflater mInflater;
    public MyListViewAdapter(Context context, List<ImageFolder> mData)
    {
        this.mContext = context;
        this.mData = mData;
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
        this.folder=mData.get(position);
        return folder;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageFolder imageFolder= (ImageFolder) getItem(position);
        ViewHolder holder;
        if (convertView == null)
        {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.listviewitem, parent,
                    false);
            holder.mImageView = (ImageView) convertView
                    .findViewById(R.id.imageView);
            holder.mTextView = (TextView) convertView.findViewById(R.id.textView);

            convertView.setTag(holder);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mImageView
                .setImageResource(R.drawable.friends_sends_pictures_no);
        Glide.with(mContext)
                .load(imageFolder.getFirstImagePath())
                .into(holder.mImageView);
        holder.mTextView.setText(imageFolder.getName());
        return convertView;
    }
    private final class ViewHolder
    {
        ImageView mImageView;
        TextView mTextView;
    }

}

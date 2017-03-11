package com.example.admin.mytask;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by admin on 2017/3/8.
 */

public class ImageAdapter extends ArrayAdapter<String> implements AbsListView.OnScrollListener {


    private Set<BitmapWorkerTask> tasks;//记录所有正在下载或等待下载的任务，防止出现重复任务

    private LruCache<String, Bitmap> memoryCache;//图片缓存技术的核心类

    private GridView gridView;//GridView的实例

    private int firstItem;//第一张可见图片的下标
    private String[] url;

    private int itemCount;// 一屏有多少张图片可见

    private boolean isFirst = true;//记录是否刚打开程序，用于解决进入程序不滚动屏幕，不会下载图片的问题

    public ImageAdapter(Context context, int textViewResourceId, String[] objects,
                        GridView photoWall) {
        super(context, textViewResourceId, objects);
        url=objects;
        gridView = photoWall;
        tasks = new HashSet<BitmapWorkerTask>();
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;
        memoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount();
                //return bitmap.getAllocationByteCount();
            }
        };
        gridView.setOnScrollListener(this);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String url = getItem(position);
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.grid_item, null);
        } else {
            view = convertView;
        }
        final ImageView photo = (ImageView) view.findViewById(R.id.photo);
        photo.setTag(url);// 给ImageView设置一个Tag，保证异步加载图片时不会乱序
        setImageView(url, photo);
        return view;
    }

    //设置图片
    private void setImageView(String imageUrl, ImageView imageView) {
        Bitmap bitmap = getBitmapFromMemoryCache(imageUrl);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            imageView.setImageResource(R.drawable.empty_photo);
        }
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemoryCache(key) == null) {
            memoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemoryCache(String key) {
        return memoryCache.get(key);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE) {
            loadBitmaps(firstItem, itemCount);
        } else {
            cancelAllTasks();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        firstItem = firstVisibleItem;
        itemCount = visibleItemCount;
        if (isFirst && visibleItemCount > 0) {
            loadBitmaps(firstVisibleItem, visibleItemCount);
            isFirst = false;
        }
    }

    //加载图片
    private void loadBitmaps(int firstVisibleItem, int visibleItemCount) {
        try {
            for (int i = firstVisibleItem; i < firstVisibleItem + visibleItemCount; i++) {
                String imageUrl =url[i];
                Bitmap bitmap = getBitmapFromMemoryCache(imageUrl);
                if (bitmap == null) {
                    BitmapWorkerTask task = new BitmapWorkerTask();
                    tasks.add(task);
                    task.execute(imageUrl);
                } else {
                    ImageView imageView = (ImageView) gridView.findViewWithTag(imageUrl);//根据tag找到对应的imageview
                    if (imageView != null && bitmap != null) {
                        imageView.setImageBitmap(bitmap);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancelAllTasks() {
        if (tasks != null) {
            for (BitmapWorkerTask task : tasks) {
                task.cancel(false);
            }
        }
    }

    class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {

        private String imageUrl;

        @Override
        protected Bitmap doInBackground(String... params) {
            imageUrl=params[0];
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(imageUrl,bmOptions);
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;
            //通过比较获取较小的缩放比列
            int scaleFactor = Math.min(photoW / 80, photoH / 80);
            // 将inJustDecodeBounds置为false，设置bitmap的缩放比列
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;
            bmOptions.inPurgeable = true;
            //再次decode获取bitmap
            Bitmap bitmap = BitmapFactory.decodeFile(imageUrl,bmOptions);
            //Bitmap b= BitmapFactory.decodeFile(imageUrl);
            //Bitmap bitmap= ThumbnailUtils.extractThumbnail(b,120,120,ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
            if (bitmap != null) {
                // 图片下载完成后缓存到LrcCache中
                addBitmapToMemoryCache(params[0], bitmap);
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            ImageView imageView = (ImageView) gridView.findViewWithTag(imageUrl);// 根据Tag找到相应的ImageView控件，将下载好的图片显示出来。
            if (imageView != null && bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
            tasks.remove(this);
        }
    }
}
package com.wy.wyandroidsdk.photoView;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.github.chrisbanes.photoview.PhotoView;
import com.wy.wyandroidsdk.R;
import com.wy.wyandroidsdk.imageLoader.ImageLoaderManager;

import java.util.ArrayList;

/* 名称: WyAndroidSdk.com.wy.wyandroidsdk.photoView.PhotoPageAdapter
 * 用户: _VIEW
 * 时间: 2019/8/2,18:35
 * 描述: viewPager adapter
 */
public class PhotoPageAdapter extends PagerAdapter {
    private static final String TAG = "PhotoPageAdapter";
    private Context context;
    private ArrayList<String> photoList;
    private ImageLoaderManager imageLoaderManager;

    public PhotoPageAdapter(Context context, ArrayList<String> photoList) {
        this.context = context;
        this.photoList = photoList;
        imageLoaderManager = ImageLoaderManager.getInstance(context);
    }


    @Override
    public int getCount() {
        return photoList.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Log.e(TAG, "instantiateItem: ");
        PhotoView photoView = new PhotoView(context);
        imageLoaderManager.disPlayImage(photoView, photoList.get(position));
        container.addView(photoView,ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return photoView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
        Log.e(TAG, "destroyItem: ");
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}

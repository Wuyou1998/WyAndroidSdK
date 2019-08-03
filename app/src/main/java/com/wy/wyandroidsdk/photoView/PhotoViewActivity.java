package com.wy.wyandroidsdk.photoView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.wy.wyandroidsdk.R;
import com.wy.wyandroidsdk.share.ShareDialog;

import java.util.ArrayList;

public class PhotoViewActivity extends AppCompatActivity {
    //UI
    private ImageView iv_back, iv_share;
    private TextView tv_title;
    private ViewPager vp_photo;
    //Data
    private PhotoPageAdapter photoPageAdapter;
    private ArrayList<String> photoList;
    private int length = 0;
    public static final String PHOTO_LIST = "photo_list";
    private int photoIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        initData();
        initView();
        initEvents();
    }

    //初始化要显示的图片地址列表
    private void initData() {
        photoList = getIntent().getStringArrayListExtra(PHOTO_LIST);
        if (photoList != null)
            length = photoList.size();
    }

    private void initView() {
        iv_back = findViewById(R.id.iv_back);
        iv_share = findViewById(R.id.iv_share);
        tv_title = findViewById(R.id.tv_title);
        vp_photo = findViewById(R.id.vp_photo);
        vp_photo.setPageMargin(dp2px(30));
        photoPageAdapter = new PhotoPageAdapter(this, photoList);
        tv_title.setText("第1张," + "共" + photoPageAdapter.getCount() + "张");
        vp_photo.setAdapter(photoPageAdapter);
        vp_photo.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tv_title.setText("第" + (position + 1) + "张," + "共" + photoPageAdapter.getCount() + "张");
                photoIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initEvents() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        iv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareDialog dialog = new ShareDialog(PhotoViewActivity.this);
                dialog.setShareTitle("来自wuyou的分享");
                dialog.setShareText("wuyou向你分享了如下内容:" + photoList.get(photoIndex));
                dialog.setShareTitleUrl("https://www.imooc.com/");
                dialog.setImagePhoto(photoList.get(photoIndex));
                dialog.show();
            }
        });
    }

    private int dp2px(float dp) {
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dp * scale);
    }
}

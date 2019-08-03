package com.wy.wyandroidsdk.share;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.mob.MobSDK;
import com.wy.wyandroidsdk.R;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;

/**
 * Created by renzhiqiang on 16/8/13.
 */
public class ShareDialog extends Dialog implements View.OnClickListener {

    private Context mContext;
    private DisplayMetrics dm;

    /**
     * UI
     */
    private RelativeLayout mWeixinLayout;
    private RelativeLayout mWeixinMomentLayout;
    private RelativeLayout mQQLayout;
    private RelativeLayout mQZoneLayout;
    private RelativeLayout rl_more_share;
    private TextView mCancelView;

    /**
     * share relative
     */
    private int mShareType; //指定分享类型
    private String mShareTitle; //指定分享内容标题
    private String mShareText; //指定分享内容文本
    private String mSharePhoto; //指定分享本地图片
    private String mShareTileUrl;
    private String mShareSiteUrl;
    private String mShareSite;
    private String mUrl;
    private String mResourceUrl;


    public ShareDialog(Context context) {
        super(context, R.style.SheetDialogStyle);
        mContext = context;
        dm = mContext.getResources().getDisplayMetrics();
        MobSDK.init(context);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_share_layout);
        initView();
    }

    private void initView() {
        /**
         * 通过获取到dialog的window来控制dialog的宽高及位置
         */
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = dm.widthPixels; //设置宽度
        dialogWindow.setAttributes(lp);

        mWeixinLayout =  findViewById(R.id.weixin_layout);
        mWeixinLayout.setOnClickListener(this);
        mWeixinMomentLayout =  findViewById(R.id.moment_layout);
        mWeixinMomentLayout.setOnClickListener(this);
        mQQLayout =  findViewById(R.id.qq_layout);
        mQQLayout.setOnClickListener(this);
        mQZoneLayout =  findViewById(R.id.weibo_layout);
        mQZoneLayout.setOnClickListener(this);
        mCancelView =  findViewById(R.id.cancel_view);
        mCancelView.setOnClickListener(this);
        rl_more_share=findViewById(R.id.rl_more_share);
        rl_more_share.setOnClickListener(this);
    }

    public void setResourceUrl(String resourceUrl) {
        mResourceUrl = resourceUrl;
    }

    public void setShareTitle(String title) {
        mShareTitle = title;
    }

    public void setImagePhoto(String photo) {
        mSharePhoto = photo;
    }

    public void setShareType(int type) {
        mShareType = type;
    }

    public void setShareSite(String site) {
        mShareSite = site;
    }

    public void setShareTitleUrl(String titleUrl) {
        mShareTileUrl = titleUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public void setShareSiteUrl(String siteUrl) {
        mShareSiteUrl = siteUrl;
    }

    public void setShareText(String text) {
        mShareText = text;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.weixin_layout:
                shareData(ShareManager.PlatformType.WeChat);

                break;
            case R.id.moment_layout:
                shareData(ShareManager.PlatformType.WechatMoments);
                break;
            case R.id.qq_layout:
                shareData(ShareManager.PlatformType.QQ);
                break;
            case R.id.weibo_layout:
                shareData(ShareManager.PlatformType.SinaWeibo);
                break;
            case R.id.cancel_view:
                dismiss();
                break;
            case R.id.rl_more_share:
                if (TextUtils.isEmpty(mShareTitle)){
                    mShareTitle="这是标题";

                }
                if (TextUtils.isEmpty(mShareText)){
                    mShareText="这是内容";
                }
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,mShareText);
                mContext.startActivity(Intent.createChooser(intent,mShareTitle));
                dismiss();
                break;

        }
    }

    private PlatformActionListener mListener = new PlatformActionListener() {
        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            dismiss();
        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {
            Toast.makeText(mContext, "出错了", Toast.LENGTH_SHORT).show();
            dismiss();
        }

        @Override
        public void onCancel(Platform platform, int i) {
            dismiss();
        }
    };

    private void shareData(ShareManager.PlatformType platofrm) {
        ShareData mData = new ShareData();
        Platform.ShareParams params = new Platform.ShareParams();
        params.setTitle(mShareTitle);
        params.setText(mShareText);
        params.setImagePath(mSharePhoto);
        params.setUrl(mUrl);
        mData.mPlatformType = platofrm;
        mData.mShareParams = params;
        ShareManager.getInstance().shareData(mData, mListener);
    }
}

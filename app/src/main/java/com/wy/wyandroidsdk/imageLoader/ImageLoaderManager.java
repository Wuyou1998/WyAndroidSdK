package com.wy.wyandroidsdk.imageLoader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.wy.wyandroidsdk.R;

/* 名称: WyAndroidSdk.com.wy.wyandroidsdk.imageLoader.ImageLoaderManager
 * 用户: _VIEW
 * 时间: 2019/7/27,19:37
 * 描述: 初始化ImageLoader，用来加载网络图片
 */
public class ImageLoaderManager {
    private static final int THREAD_COUNT = 4;//UIL最多有4条线程
    private static final int PRIORITY = 2;//图片加载优先级
    private static final int DISK_CACHE_SIZE = 50 * 1024;//本地缓存
    private static final int CONNECTION_TIME_OUT = 5 * 1000;//连接超时时间
    private static final int READ_TIME_OUT = 30 * 1000;//读取超时时间

    private static ImageLoader loader = null;
    private static ImageLoaderManager loaderManager = null;

    private ImageLoaderManager(Context context) {
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(context)
                .threadPoolSize(THREAD_COUNT)
                .threadPriority(Thread.NORM_PRIORITY - PRIORITY)
                .denyCacheImageMultipleSizesInMemory()//防止缓存多套不同尺寸的图片
                .memoryCache(new WeakMemoryCache())
                .diskCacheSize(DISK_CACHE_SIZE)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())//MD5命名文件
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .defaultDisplayImageOptions(getDefaultOptions())
                .imageDownloader(new BaseImageDownloader(context, CONNECTION_TIME_OUT, READ_TIME_OUT))
                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(configuration);
        loader = ImageLoader.getInstance();
    }

    /**
     * 配置默认options
     *
     * @return DisplayImageOptions对象
     */
    private DisplayImageOptions getDefaultOptions() {
        return new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.tupian)//图片uri为空时加载
                .showImageOnFail(R.drawable.tupian)//加载错误时
                .cacheInMemory(true)//图片可以换存在内存
                .cacheOnDisk(true)//可以换存在硬盘
                .bitmapConfig(Bitmap.Config.RGB_565)//图片解码类型
                .decodingOptions(new BitmapFactory.Options())//图片解码配置
                .build();
    }

    /**
     * 加载图片API
     *
     * @param imageView imageView
     * @param url       url
     * @param options   配置信息
     * @param listener  监听器
     */
    public void disPlayImage(ImageView imageView, String url, DisplayImageOptions options, ImageLoadingListener listener) {
        if (loader != null) {
            loader.displayImage(url, imageView, options, listener);
        }
    }

    public void disPlayImage(ImageView imageView, String url) {
        disPlayImage(imageView, url, null, null);
    }

    public void disPlayImage(ImageView imageView, String url, ImageLoadingListener listener) {
        disPlayImage(imageView, url, null, listener);
    }

    public static ImageLoaderManager getInstance(Context context) {
        if (loaderManager == null) {
            synchronized (ImageLoaderManager.class) {
                if (loader == null) {
                    loaderManager = new ImageLoaderManager(context);
                }
            }
        }
        return loaderManager;
    }
}

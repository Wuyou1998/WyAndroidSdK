package com.wy.wyandroidsdk;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.github.chrisbanes.photoview.PhotoView;
import com.wy.wyandroidsdk.imageLoader.ImageLoaderManager;
import com.wy.wyandroidsdk.okHttp.HttpModule;
import com.wy.wyandroidsdk.photoView.PhotoViewActivity;
import com.wy.wyandroidsdk.zxing.android.CaptureActivity;
import com.wy.wyandroidsdk.zxing.common.Constant;

import java.util.ArrayList;;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermissions();
        initView();
    }

    private void initView() {
        findViewById(R.id.tv_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivityForResult(new Intent(MainActivity.this, CaptureActivity.class),1);
                ArrayList<String> arrayList=new ArrayList<>();
                arrayList.add("http://a.hiphotos.baidu.com/image/pic/item/4610b912c8fcc3cebba8b8e09c45d688d53f20fc.jpg");
                arrayList.add("http://c.hiphotos.baidu.com/image/h%3D300/sign=e961d6ff414a20a42e1e3ac7a0539847/d1a20cf431adcbefdc7ef0eba2af2edda2cc9f91.jpg");
                arrayList.add("http://a.hiphotos.baidu.com/image/pic/item/4610b912c8fcc3cebba8b8e09c45d688d53f20fc.jpg");
                arrayList.add("http://a.hiphotos.baidu.com/image/pic/item/4610b912c8fcc3cebba8b8e09c45d688d53f20fc.jpg");
                arrayList.add("http://a.hiphotos.baidu.com/image/pic/item/4610b912c8fcc3cebba8b8e09c45d688d53f20fc.jpg");
                Intent intent=new Intent(MainActivity.this, PhotoViewActivity.class);
                intent.putStringArrayListExtra(PhotoViewActivity.PHOTO_LIST,arrayList);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(this,data.getStringExtra(Constant.CODED_CONTENT),Toast.LENGTH_SHORT).show();
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA},1);
        }
    }
}

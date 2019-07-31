package com.wy.wyandroidsdk;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.wy.wyandroidsdk.widgets.WyVideoView;
import com.wy.wyandroidsdk.zxing.android.CaptureActivity;
import com.wy.wyandroidsdk.zxing.common.Constant;


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
        ConstraintLayout constraintLayout=findViewById(R.id.cl);
        WyVideoView wyVideoView=new WyVideoView(this,null,constraintLayout);
        wyVideoView.setListener(new WyVideoView.ADVideoPlayerListener() {
            @Override
            public void onBufferUpdate(int time) {

            }

            @Override
            public void onClickFullScreenBtn() {

            }

            @Override
            public void onClickVideo() {

            }

            @Override
            public void onClickBackBtn() {

            }

            @Override
            public void onClickPlay() {

            }

            @Override
            public void onAdVideoLoadSuccess() {

            }

            @Override
            public void onAdVideoLoadFailed() {

            }

            @Override
            public void onAdVideoLoadComplete() {

            }
        });
        wyVideoView.setDataSource("http://okxxzy.xzokzyzy.com/20190728/23849_9b8076fd/Avengers Endgame.2019.1080p.mp4");
        constraintLayout.addView(wyVideoView);

        findViewById(R.id.tv_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, CaptureActivity.class),1);
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

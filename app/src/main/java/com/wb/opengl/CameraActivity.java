package com.wb.opengl;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.media.ImageReader;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CameraActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "CameraActivity";

    //initView
    private TextView tv_pic;
    private TextView tv_video;
    private SurfaceView mPrevoewSurface;

    //申请两个权限，录音和文件读写
    //1、首先声明一个数组permissions，将需要的权限都放在里面
    String[] permissions = new String[]{Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA};
    //2、创建一个mPermissionList，逐个判断哪些权限未授予，未授予的权限存储到mPerrrmissionList中
    List<String> mPermissionList = new ArrayList<>();

    private final int mRequestCode = 100;//权限请求码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        checkAuthor();
        tv_pic = findViewById(R.id.tv_picture);
        tv_video = findViewById(R.id.tv_video);
        mPrevoewSurface = findViewById(R.id.surface_camera);
    }

    private void checkAuthor() {
        for (String permission:permissions){
            int checkPermission = checkSelfPermission(permission);
            if (checkPermission != PackageManager.PERMISSION_GRANTED || !PermisssionUtils.checkOpsPermission(this, permission)) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                    Toast.makeText(this, "请打开相关权限", Toast.LENGTH_LONG).show();
                    new AlertDialog.Builder(this)
                            .setTitle("相机权限")
                            .setMessage("当前应用需要开启拍照和录音的权限,是否继续?")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    ActivityCompat.requestPermissions(CameraActivity.this, permissions, mRequestCode);
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .show();
                } else {
                    mPermissionList.add(permission);
                }
            }
        }

        if(mPermissionList.size()>0){
                ActivityCompat.requestPermissions(CameraActivity.this, permissions, mRequestCode);
        }else {
            initCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case mRequestCode:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initCamera();
                } else {
                    Toast.makeText(CameraActivity.this, "权限被拒绝", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_picture:
                takePicture();
                break;
            case R.id.tv_video:
                recordVideo();
                break;
            default:
        }
    }

    private void recordVideo() {
    }

    private void takePicture() {
    }

    private void initCamera() {

    }

}

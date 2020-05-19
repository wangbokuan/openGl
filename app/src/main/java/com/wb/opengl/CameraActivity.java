package com.wb.opengl;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class CameraActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_pic;
    private TextView tv_video;
    private SurfaceView textureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        checkAuthor(Manifest.permission.CAMERA);
        checkAuthor(Manifest.permission.RECORD_AUDIO);
        tv_pic = findViewById(R.id.tv_picture);
        tv_video = findViewById(R.id.tv_video);
        textureView = findViewById(R.id.surface_camera);
    }

    private void checkAuthor(final String permission) {
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
                                requestAuthor(permission);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();
            } else {
                requestAuthor(permission);
            }
        } else {
            previewCamera();
        }
    }

    private void requestAuthor(String permission) {
        if (Manifest.permission.CAMERA.equals(permission)) {
            ActivityCompat.requestPermissions(CameraActivity.this, new String[]{Manifest.permission.CAMERA}, 123);
        } else {
            ActivityCompat.requestPermissions(CameraActivity.this, new String[]{Manifest.permission.RECORD_AUDIO}, 123);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 123:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    previewCamera();
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

    private void previewCamera() {

    }


}

package com.wb.opengl;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.ImageReader;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.util.Log;
import android.util.Size;
import android.view.Surface;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;


/**
 * @author wangbo
 * @since 2020/5/22
 */
public class CameraUtils {
    private static final String TAG = "CameraUtils";
    private Context mContext;
    private CameraManager mCameraManager;
    private CameraDevice mCameraDevice;
    private ImageReader mImageReader;
    private Handler mBackgroundHandler;
    private HandlerThread mBackgroundThread;
    private CameraCharacteristics mCameraCharacteristics;
    private int mCameraId = CameraCharacteristics.LENS_FACING_FRONT;
    private CaptureRequest.Builder mCaptureRequestBuilder;
    private CameraDevice.StateCallback mStateCallBack = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            mCameraDevice = camera;
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            releaseCamera();
        }

        @Override
        public void onError(@NonNull CameraDevice camera, int error) {
            releaseCamera();
        }
    };

    private CameraManager.AvailabilityCallback mAvailabilityCallback = new CameraManager.AvailabilityCallback() {
        @Override
        public void onCameraAvailable(@NonNull String cameraId) {
            super.onCameraAvailable(cameraId);
        }

        @Override
        public void onCameraUnavailable(@NonNull String cameraId) {
            super.onCameraUnavailable(cameraId);
        }
    };

    @RequiresPermission(Manifest.permission.CAMERA)
    public CameraUtils(Context context) {
        mContext = context;
        startBackThread();
        mCameraManager = (CameraManager) mContext.getSystemService(Context.CAMERA_SERVICE);
        mCameraManager.registerAvailabilityCallback(mAvailabilityCallback, mBackgroundHandler);
        openCamera();
    }

    @SuppressLint("MissingPermission")
    private void openCamera() {
        try {
            mCameraCharacteristics = mCameraManager.getCameraCharacteristics(Integer.toString(mCameraId));
            StreamConfigurationMap map = mCameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            Size largest = Collections.max(Arrays.asList(map.getOutputSizes(ImageFormat.JPEG)), new CompareSizeByArea());
            Log.d(TAG,"picture size:"+largest.getHeight()+" * "+largest.getWidth());
            mImageReader = ImageReader.newInstance(largest.getWidth(),largest.getHeight(),ImageFormat.JPEG,2);

            mCameraManager.openCamera(String.valueOf(mCameraId), mStateCallBack, mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }

    private void startBackThread() {
        mBackgroundThread = new HandlerThread("CameraManagerBack");
        mBackgroundThread.start();
        mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
    }

    private void stopBackThread() {
        try {
            mBackgroundThread.quitSafely();
            mBackgroundThread.join();
            mBackgroundThread = null;
            mBackgroundHandler = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void release() {
        mContext = null;
        mCameraManager.unregisterAvailabilityCallback(mAvailabilityCallback);
    }

    private void releaseCamera() {
        if (null != mCameraDevice) {
            mCameraDevice.close();
        }
        stopBackThread();
    }

    private void requestPreview(Surface mSurface) {
        try {
            mCaptureRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            mCaptureRequestBuilder.addTarget(mSurface);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    class CompareSizeByArea implements Comparator<Size> {

        @Override
        public int compare(Size lhs, Size rhs) {
            return Long.signum((long) (lhs.getWidth() * lhs.getHeight()) - (long) rhs.getWidth() * lhs.getHeight());
        }
    }
}

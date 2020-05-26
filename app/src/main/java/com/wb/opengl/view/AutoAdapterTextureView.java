package com.wb.opengl.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.TextureView;

import com.wb.opengl.utils.LogUtils;

/**
 * @author wangbo
 * @since 2020/5/25
 */
public class AutoAdapterTextureView extends TextureView {

    private int mRatioWidth = 0;
    private int mRatioHeight = 0;

    public AutoAdapterTextureView(Context context) {
        super(context);
    }

    public AutoAdapterTextureView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoAdapterTextureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AutoAdapterTextureView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setAspectRatio(int width, int height) {
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("Size cannot be negative.");
        }
        mRatioWidth = width;
        mRatioHeight = height;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        LogUtils.e("AutoAdapterTextureView","mRatioWidth:%d , mRatioHeight:%d",mRatioWidth,mRatioHeight);
        LogUtils.e("AutoAdapterTextureView","width:%d,height:%d",width,height);
        if (0 == mRatioWidth || 0 == mRatioHeight) {
            setMeasuredDimension(width, height);
        } else {
            int widthCompare = height * mRatioWidth / mRatioHeight;
            if (width < widthCompare) {
                int result = width * mRatioHeight / mRatioWidth;
                setMeasuredDimension(width, result);
                LogUtils.e("AutoAdapterTextureView","width:,%d,result:%d",width,result);
            } else {
                setMeasuredDimension(widthCompare, height);
                LogUtils.e("AutoAdapterTextureView","widthCompare:,%d,height:%d",widthCompare,height);
            }
        }

    }
}

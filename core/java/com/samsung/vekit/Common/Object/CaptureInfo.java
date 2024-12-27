package com.samsung.vekit.Common.Object;

import android.graphics.Bitmap;

import com.samsung.vekit.Listener.CaptureFrameTaskListener;

/* loaded from: classes6.dex */
public class CaptureInfo {
    private Bitmap bitmap;
    private int height;
    private CaptureFrameTaskListener listener;
    private int width;

    public CaptureInfo(int width, int height, CaptureFrameTaskListener listener, Bitmap bitmap) {
        this.width = width;
        this.height = height;
        this.listener = listener;
        this.bitmap = bitmap;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public CaptureFrameTaskListener getListener() {
        return this.listener;
    }

    public void setListener(CaptureFrameTaskListener listener) {
        this.listener = listener;
    }
}

package com.samsung.vekit.Common.Object;

import android.graphics.Bitmap;
import com.samsung.vekit.Listener.CaptureFrameTaskListener;

/* loaded from: classes6.dex */
public class CaptureInfo {
    private Bitmap bitmap;
    private int height;
    private CaptureFrameTaskListener listener;
    private int width;

    public CaptureInfo(int i, int i2, CaptureFrameTaskListener captureFrameTaskListener, Bitmap bitmap) {
        this.width = i;
        this.height = i2;
        this.listener = captureFrameTaskListener;
        this.bitmap = bitmap;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int i) {
        this.width = i;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int i) {
        this.height = i;
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

    public void setListener(CaptureFrameTaskListener captureFrameTaskListener) {
        this.listener = captureFrameTaskListener;
    }
}

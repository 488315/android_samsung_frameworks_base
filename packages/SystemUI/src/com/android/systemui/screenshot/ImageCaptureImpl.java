package com.android.systemui.screenshot;

import android.app.IActivityTaskManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.IWindowManager;
import android.window.ScreenCapture;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class ImageCaptureImpl implements ImageCapture {
    public final IWindowManager windowManager;

    public ImageCaptureImpl(IWindowManager iWindowManager, IActivityTaskManager iActivityTaskManager, CoroutineDispatcher coroutineDispatcher) {
        this.windowManager = iWindowManager;
    }

    public final Bitmap captureDisplay(int i, Rect rect) {
        ScreenCapture.CaptureArgs build = new ScreenCapture.CaptureArgs.Builder().setSourceCrop(rect).build();
        ScreenCapture.SynchronousScreenCaptureListener createSyncCaptureListener = ScreenCapture.createSyncCaptureListener();
        this.windowManager.captureDisplay(i, build, createSyncCaptureListener);
        ScreenCapture.ScreenshotHardwareBuffer buffer = createSyncCaptureListener.getBuffer();
        if (buffer != null) {
            return buffer.asBitmap();
        }
        return null;
    }
}

package com.android.systemui.screenshot;

import android.app.IActivityTaskManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.IWindowManager;
import android.window.ScreenCapture;
import kotlinx.coroutines.CoroutineDispatcher;

/* loaded from: classes2.dex */
public class ImageCaptureImpl implements ImageCapture {
    public final IWindowManager windowManager;

    public ImageCaptureImpl(IWindowManager iWindowManager, IActivityTaskManager iActivityTaskManager, CoroutineDispatcher coroutineDispatcher) {
        this.windowManager = iWindowManager;
    }

    public final Bitmap captureDisplay(int i, Rect rect) {
        ScreenCapture.CaptureArgs captureArgsBuild = new ScreenCapture.CaptureArgs.Builder().setSourceCrop(rect).build();
        ScreenCapture.SynchronousScreenCaptureListener synchronousScreenCaptureListenerCreateSyncCaptureListener = ScreenCapture.createSyncCaptureListener();
        this.windowManager.captureDisplay(i, captureArgsBuild, synchronousScreenCaptureListenerCreateSyncCaptureListener);
        ScreenCapture.ScreenshotHardwareBuffer buffer = synchronousScreenCaptureListenerCreateSyncCaptureListener.getBuffer();
        if (buffer != null) {
            return buffer.asBitmap();
        }
        return null;
    }
}

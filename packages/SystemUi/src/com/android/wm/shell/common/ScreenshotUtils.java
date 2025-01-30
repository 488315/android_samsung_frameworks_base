package com.android.wm.shell.common;

import android.graphics.Rect;
import android.view.SurfaceControl;
import android.window.ScreenCapture;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ScreenshotUtils {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class BufferConsumer implements Consumer {
        public final int mLayer;
        public final SurfaceControl mParentSurfaceControl;
        public SurfaceControl mScreenshot = null;
        public final SurfaceControl.Transaction mTransaction;

        public BufferConsumer(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, SurfaceControl surfaceControl2, int i) {
            this.mTransaction = transaction;
            this.mParentSurfaceControl = surfaceControl2;
            this.mLayer = i;
        }

        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            ScreenCapture.ScreenshotHardwareBuffer screenshotHardwareBuffer = (ScreenCapture.ScreenshotHardwareBuffer) obj;
            if (screenshotHardwareBuffer == null || screenshotHardwareBuffer.getHardwareBuffer() == null) {
                return;
            }
            SurfaceControl build = new SurfaceControl.Builder().setName("ScreenshotUtils screenshot").setFormat(-3).setSecure(screenshotHardwareBuffer.containsSecureLayers()).setCallsite("ScreenshotUtils.takeScreenshot").setBLASTLayer().build();
            this.mScreenshot = build;
            this.mTransaction.setBuffer(build, screenshotHardwareBuffer.getHardwareBuffer());
            this.mTransaction.setColorSpace(this.mScreenshot, screenshotHardwareBuffer.getColorSpace());
            this.mTransaction.reparent(this.mScreenshot, this.mParentSurfaceControl);
            this.mTransaction.setLayer(this.mScreenshot, this.mLayer);
            this.mTransaction.show(this.mScreenshot);
            this.mTransaction.apply();
        }
    }

    public static SurfaceControl takeScreenshot(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, SurfaceControl surfaceControl2, Rect rect, int i) {
        BufferConsumer bufferConsumer = new BufferConsumer(transaction, surfaceControl, surfaceControl2, i);
        bufferConsumer.accept(ScreenCapture.captureLayers(new ScreenCapture.LayerCaptureArgs.Builder(surfaceControl).setSourceCrop(rect).setCaptureSecureLayers(true).setAllowProtected(true).build()));
        return bufferConsumer.mScreenshot;
    }
}

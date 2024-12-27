package com.android.server.wm;

import android.graphics.BLASTBufferQueue;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.Surface;
import android.view.SurfaceControl;

import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class StrictModeFlash {
    public final BLASTBufferQueue mBlastBufferQueue;
    public boolean mDrawNeeded;
    public int mLastDH;
    public int mLastDW;
    public final Surface mSurface;
    public final SurfaceControl mSurfaceControl;

    public StrictModeFlash(DisplayContent displayContent, SurfaceControl.Transaction transaction) {
        SurfaceControl surfaceControl = null;
        try {
            surfaceControl =
                    displayContent
                            .makeOverlay()
                            .setName("StrictModeFlash")
                            .setBLASTLayer()
                            .setFormat(-3)
                            .setCallsite("StrictModeFlash")
                            .build();
            transaction.setLayer(surfaceControl, 1010000);
            transaction.setPosition(
                    surfaceControl,
                    FullScreenMagnificationGestureHandler.MAX_SCALE,
                    FullScreenMagnificationGestureHandler.MAX_SCALE);
            transaction.show(surfaceControl);
            InputMonitor.setTrustedOverlayInputInfo(
                    surfaceControl, transaction, displayContent.mDisplayId, "StrictModeFlash");
        } catch (Surface.OutOfResourcesException unused) {
        }
        SurfaceControl surfaceControl2 = surfaceControl;
        this.mSurfaceControl = surfaceControl2;
        this.mDrawNeeded = true;
        BLASTBufferQueue bLASTBufferQueue =
                new BLASTBufferQueue("StrictModeFlash", surfaceControl2, 1, 1, 1);
        this.mBlastBufferQueue = bLASTBufferQueue;
        this.mSurface = bLASTBufferQueue.createSurface();
    }

    public final void setVisibility(SurfaceControl.Transaction transaction, boolean z) {
        SurfaceControl surfaceControl = this.mSurfaceControl;
        if (surfaceControl == null) {
            return;
        }
        if (this.mDrawNeeded) {
            this.mDrawNeeded = false;
            int i = this.mLastDW;
            int i2 = this.mLastDH;
            this.mBlastBufferQueue.update(surfaceControl, i, i2, 1);
            Canvas canvas = null;
            try {
                canvas = this.mSurface.lockCanvas(null);
            } catch (Surface.OutOfResourcesException | IllegalArgumentException unused) {
            }
            if (canvas != null) {
                canvas.save();
                canvas.clipRect(new Rect(0, 0, i, 20));
                canvas.drawColor(-65536);
                canvas.restore();
                canvas.save();
                canvas.clipRect(new Rect(0, 0, 20, i2));
                canvas.drawColor(-65536);
                canvas.restore();
                canvas.save();
                canvas.clipRect(new Rect(i - 20, 0, i, i2));
                canvas.drawColor(-65536);
                canvas.restore();
                canvas.save();
                canvas.clipRect(new Rect(0, i2 - 20, i, i2));
                canvas.drawColor(-65536);
                canvas.restore();
                this.mSurface.unlockCanvasAndPost(canvas);
            }
        }
        if (z) {
            transaction.show(this.mSurfaceControl);
        } else {
            transaction.hide(this.mSurfaceControl);
        }
    }
}

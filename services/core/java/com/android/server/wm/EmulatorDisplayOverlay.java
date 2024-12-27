package com.android.server.wm;

import android.R;
import android.content.Context;
import android.graphics.BLASTBufferQueue;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceControl;

import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class EmulatorDisplayOverlay {
    public boolean mDrawNeeded;
    public int mLastDH;
    public int mLastDW;
    public final Drawable mOverlay;
    public int mRotation;
    public final Point mScreenSize;
    public final Surface mSurface;
    public final SurfaceControl mSurfaceControl;
    public boolean mVisible;

    public EmulatorDisplayOverlay(
            Context context,
            DisplayContent displayContent,
            int i,
            SurfaceControl.Transaction transaction) {
        Display display = displayContent.mDisplay;
        Point point = new Point();
        this.mScreenSize = point;
        display.getSize(point);
        SurfaceControl surfaceControl = null;
        try {
            surfaceControl =
                    displayContent
                            .makeOverlay()
                            .setName("EmulatorDisplayOverlay")
                            .setBLASTLayer()
                            .setFormat(-3)
                            .setCallsite("EmulatorDisplayOverlay")
                            .build();
            transaction.setLayer(surfaceControl, i);
            transaction.setPosition(
                    surfaceControl,
                    FullScreenMagnificationGestureHandler.MAX_SCALE,
                    FullScreenMagnificationGestureHandler.MAX_SCALE);
            transaction.show(surfaceControl);
            InputMonitor.setTrustedOverlayInputInfo(
                    surfaceControl,
                    transaction,
                    displayContent.mDisplayId,
                    "EmulatorDisplayOverlay");
        } catch (Surface.OutOfResourcesException unused) {
        }
        SurfaceControl surfaceControl2 = surfaceControl;
        this.mSurfaceControl = surfaceControl2;
        this.mDrawNeeded = true;
        this.mOverlay = context.getDrawable(R.drawable.fastscroll_thumb_material);
        Point point2 = this.mScreenSize;
        this.mSurface =
                new BLASTBufferQueue(
                                "EmulatorDisplayOverlay", surfaceControl2, point2.x, point2.y, 1)
                        .createSurface();
    }

    public final void drawIfNeeded(SurfaceControl.Transaction transaction) {
        if (this.mDrawNeeded && this.mVisible) {
            this.mDrawNeeded = false;
            Canvas canvas = null;
            try {
                canvas = this.mSurface.lockCanvas(null);
            } catch (Surface.OutOfResourcesException | IllegalArgumentException unused) {
            }
            if (canvas == null) {
                return;
            }
            canvas.drawColor(0, PorterDuff.Mode.SRC);
            transaction.setPosition(
                    this.mSurfaceControl,
                    FullScreenMagnificationGestureHandler.MAX_SCALE,
                    FullScreenMagnificationGestureHandler.MAX_SCALE);
            Point point = this.mScreenSize;
            int max = Math.max(point.x, point.y);
            this.mOverlay.setBounds(0, 0, max, max);
            this.mOverlay.draw(canvas);
            this.mSurface.unlockCanvasAndPost(canvas);
        }
    }
}

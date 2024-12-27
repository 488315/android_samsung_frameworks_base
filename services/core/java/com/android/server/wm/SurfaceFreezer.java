package com.android.server.wm;

import android.graphics.GraphicBuffer;
import android.graphics.Rect;
import android.view.SurfaceControl;
import android.window.ScreenCapture;

public final class SurfaceFreezer {
    public final WindowContainer mAnimatable;
    SurfaceControl mLeash;
    public final WindowManagerService mWmService;
    public Snapshot mSnapshot = null;
    public final Rect mFreezeBounds = new Rect();

    public final class Snapshot {
        public AnimationAdapter mAnimation;
        public SurfaceControl mSurfaceControl;

        public final void cancelAnimation(SurfaceControl.Transaction transaction, boolean z) {
            SurfaceControl surfaceControl;
            SurfaceControl surfaceControl2 = this.mSurfaceControl;
            AnimationAdapter animationAdapter = this.mAnimation;
            this.mAnimation = null;
            if (animationAdapter != null) {
                animationAdapter.onAnimationCancelled(surfaceControl2);
            }
            if (z || (surfaceControl = this.mSurfaceControl) == null) {
                return;
            }
            transaction.remove(surfaceControl);
            this.mSurfaceControl = null;
        }
    }

    public SurfaceFreezer(
            WindowContainer windowContainer, WindowManagerService windowManagerService) {
        this.mAnimatable = windowContainer;
        this.mWmService = windowManagerService;
    }

    public static ScreenCapture.ScreenshotHardwareBuffer createSnapshotBuffer(
            SurfaceControl surfaceControl, Rect rect) {
        Rect rect2;
        if (rect != null) {
            rect2 = new Rect(rect);
            rect2.offsetTo(0, 0);
        } else {
            rect2 = null;
        }
        return ScreenCapture.captureLayers(
                new ScreenCapture.LayerCaptureArgs.Builder(surfaceControl)
                        .setSourceCrop(rect2)
                        .setCaptureSecureLayers(true)
                        .setAllowProtected(true)
                        .build());
    }

    public GraphicBuffer createFromHardwareBufferInner(
            ScreenCapture.ScreenshotHardwareBuffer screenshotHardwareBuffer) {
        return GraphicBuffer.createFromHardwareBuffer(screenshotHardwareBuffer.getHardwareBuffer());
    }

    public ScreenCapture.ScreenshotHardwareBuffer createSnapshotBufferInner(
            SurfaceControl surfaceControl, Rect rect) {
        return createSnapshotBuffer(surfaceControl, rect);
    }

    public final boolean hasLeash() {
        return this.mLeash != null;
    }

    public final void unfreeze(SurfaceControl.Transaction transaction) {
        Snapshot snapshot = this.mSnapshot;
        if (snapshot != null) {
            snapshot.cancelAnimation(transaction, false);
            this.mSnapshot = null;
        }
        SurfaceControl surfaceControl = this.mLeash;
        WindowContainer windowContainer = this.mAnimatable;
        if (surfaceControl != null) {
            this.mLeash = null;
            if (SurfaceAnimator.removeLeash(transaction, windowContainer, surfaceControl, true)) {
                this.mWmService.scheduleAnimationLocked();
            }
        }
        windowContainer.onUnfrozen();
    }
}

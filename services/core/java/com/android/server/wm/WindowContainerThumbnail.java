package com.android.server.wm;

import android.graphics.ColorSpace;
import android.graphics.GraphicBuffer;
import android.graphics.Point;
import android.hardware.HardwareBuffer;
import android.view.SurfaceControl;
import android.view.animation.Animation;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.server.wm.SurfaceAnimator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class WindowContainerThumbnail implements SurfaceAnimator.Animatable {
    public final int mHeight;
    public final SurfaceAnimator mSurfaceAnimator;
    public SurfaceControl mSurfaceControl;
    public final int mWidth;
    public final WindowContainer mWindowContainer;

    public WindowContainerThumbnail(SurfaceControl.Transaction transaction, WindowContainer windowContainer, HardwareBuffer hardwareBuffer) {
        this.mWindowContainer = windowContainer;
        this.mSurfaceAnimator = new SurfaceAnimator(this, new SurfaceAnimator.OnAnimationFinishedCallback() { // from class: com.android.server.wm.WindowContainerThumbnail$$ExternalSyntheticLambda0
            @Override // com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback
            public final void onAnimationFinished(int i, AnimationAdapter animationAdapter) {
                WindowContainerThumbnail.this.getClass();
            }
        }, windowContainer.mWmService);
        this.mWidth = hardwareBuffer.getWidth();
        this.mHeight = hardwareBuffer.getHeight();
        SurfaceControl build = windowContainer.makeChildSurface(windowContainer.getTopChild()).setName("thumbnail anim: " + windowContainer.toString()).setBLASTLayer().setFormat(-3).setMetadata(2, windowContainer.getWindowingMode()).setMetadata(1, WindowManagerService.MY_UID).setCallsite("WindowContainerThumbnail").build();
        this.mSurfaceControl = build;
        if (ProtoLogImpl_54989576.Cache.WM_SHOW_TRANSACTIONS_enabled[2]) {
            ProtoLogImpl_54989576.i(ProtoLogGroup.WM_SHOW_TRANSACTIONS, -131600102855790053L, 0, null, String.valueOf(build));
        }
        transaction.setBuffer(this.mSurfaceControl, GraphicBuffer.createFromHardwareBuffer(hardwareBuffer));
        transaction.setColorSpace(this.mSurfaceControl, ColorSpace.get(ColorSpace.Named.SRGB));
        transaction.show(this.mSurfaceControl);
        transaction.setLayer(this.mSurfaceControl, Integer.MAX_VALUE);
    }

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public final void commitPendingTransaction() {
        this.mWindowContainer.commitPendingTransaction();
    }

    public final void destroy() {
        this.mSurfaceAnimator.cancelAnimation();
        this.mWindowContainer.getPendingTransaction().remove(this.mSurfaceControl);
        this.mSurfaceControl = null;
    }

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public final SurfaceControl getAnimationLeashParent() {
        return this.mWindowContainer.getAnimationLeashParent();
    }

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public final SurfaceControl getParentSurfaceControl() {
        return this.mWindowContainer.getParentSurfaceControl();
    }

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public final SurfaceControl getSurfaceControl() {
        return this.mSurfaceControl;
    }

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public final int getSurfaceHeight() {
        return this.mHeight;
    }

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public final int getSurfaceWidth() {
        return this.mWidth;
    }

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public final SurfaceControl.Transaction getSyncTransaction() {
        return this.mWindowContainer.getSyncTransaction();
    }

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public final SurfaceControl.Builder makeAnimationLeash() {
        WindowContainer windowContainer = this.mWindowContainer;
        return windowContainer.makeChildSurface(windowContainer.getTopChild());
    }

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public final void onAnimationLeashCreated(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
        transaction.setLayer(surfaceControl, Integer.MAX_VALUE);
    }

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public final void onAnimationLeashLost(SurfaceControl.Transaction transaction) {
        transaction.hide(this.mSurfaceControl);
    }

    public final void startAnimation(SurfaceControl.Transaction transaction, Animation animation, Point point) {
        animation.restrictDuration(10000L);
        WindowContainer windowContainer = this.mWindowContainer;
        animation.scaleCurrentDuration(windowContainer.mWmService.getTransitionAnimationScaleLocked());
        this.mSurfaceAnimator.startAnimation(transaction, new LocalAnimationAdapter(new WindowAnimationSpec(animation, point, windowContainer.getDisplayContent().mAppTransition.canSkipFirstFrame(), windowContainer.getDisplayContent().mWindowCornerRadius), windowContainer.mWmService.mSurfaceAnimationRunner), false, 8, null, null, null, null);
    }
}

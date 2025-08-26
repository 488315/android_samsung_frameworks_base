package com.android.wm.shell.pip2.phone;

import android.app.PictureInPictureParams;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.SystemProperties;
import android.view.SurfaceControl;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.wm.shell.common.ScreenshotUtils;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.pip.PipBoundsState;
import com.android.wm.shell.common.pip.PipDesktopState;
import com.android.wm.shell.pip2.PipSurfaceTransactionHelper;
import com.android.wm.shell.pip2.animation.PipAlphaAnimator;
import com.android.wm.shell.pip2.phone.PipTransitionState;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.samsung.android.knox.EnterpriseContainerCallback;
import java.util.Optional;

/* loaded from: classes3.dex */
public class PipScheduler implements PipTransitionState.PipTransitionStateChangedListener {
    public static final int EXTRA_CONTENT_OVERLAY_FADE_OUT_DELAY_MS = SystemProperties.getInt("persist.wm.debug.extra_content_overlay_fade_out_delay_ms", 400);
    public final Context mContext;
    public final ShellExecutor mMainExecutor;
    public PipAlphaAnimator mOverlayFadeoutAnimator;
    public PipAlphaAnimatorSupplier mPipAlphaAnimatorSupplier;
    public final PipBoundsState mPipBoundsState;
    public final PipDesktopState mPipDesktopState;
    public PipTaskListener$$ExternalSyntheticLambda3 mPipParamsSupplier;
    public final PipSurfaceTransactionHelper mPipSurfaceTransactionHelper;
    public PipTransition mPipTransitionController;
    public final PipTransitionState mPipTransitionState;
    public final Optional mSplitScreenControllerOptional;
    public PipSurfaceTransactionHelper.SurfaceControlTransactionFactory mSurfaceControlTransactionFactory;
    public PipTouchHandler$$ExternalSyntheticLambda1 mUpdateMovementBoundsRunnable;

    interface PipAlphaAnimatorSupplier {
        PipAlphaAnimator get(Context context, SurfaceControl surfaceControl);
    }

    public PipScheduler(Context context, PipBoundsState pipBoundsState, ShellExecutor shellExecutor, PipTransitionState pipTransitionState, Optional<SplitScreenController> optional, PipDesktopState pipDesktopState) {
        this.mContext = context;
        this.mPipBoundsState = pipBoundsState;
        this.mMainExecutor = shellExecutor;
        this.mPipTransitionState = pipTransitionState;
        pipTransitionState.addPipTransitionStateChangedListener(this);
        this.mPipDesktopState = pipDesktopState;
        this.mSplitScreenControllerOptional = optional;
        this.mSurfaceControlTransactionFactory = new PipSurfaceTransactionHelper.VsyncSurfaceControlTransactionFactory();
        this.mPipSurfaceTransactionHelper = new PipSurfaceTransactionHelper(context);
        this.mPipAlphaAnimatorSupplier = new PipScheduler$$ExternalSyntheticLambda0();
    }

    public PipAlphaAnimator getOverlayFadeoutAnimator() {
        return this.mOverlayFadeoutAnimator;
    }

    @Override // com.android.wm.shell.pip2.phone.PipTransitionState.PipTransitionStateChangedListener
    public final void onPipTransitionStateChanged(int i, int i2, Bundle bundle) {
        PipAlphaAnimator pipAlphaAnimator;
        if ((i2 == 4 || i2 == 7) && (pipAlphaAnimator = this.mOverlayFadeoutAnimator) != null && pipAlphaAnimator.isStarted()) {
            this.mOverlayFadeoutAnimator.end();
            this.mOverlayFadeoutAnimator = null;
        }
    }

    public final void scheduleAnimateResizePip(int i, boolean z, Rect rect) {
        PipTransitionState pipTransitionState = this.mPipTransitionState;
        WindowContainerToken pipTaskToken = pipTransitionState.getPipTaskToken();
        if (pipTaskToken == null || !pipTransitionState.isInPip()) {
            return;
        }
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        if (z) {
            windowContainerTransaction.deferConfigToTransitionEnd(pipTaskToken);
            PipBoundsState pipBoundsState = this.mPipBoundsState;
            if (pipBoundsState.getBounds().width() == rect.width() && pipBoundsState.getBounds().height() == rect.height()) {
                rect.bottom--;
            }
        }
        windowContainerTransaction.setBounds(pipTaskToken, rect);
        PipTransition pipTransition = this.mPipTransitionController;
        pipTransition.mResizeTransition = pipTransition.mTransitions.startTransition(EnterpriseContainerCallback.CONTAINER_CANCELLED, windowContainerTransaction, pipTransition);
        pipTransition.mBoundsChangeDuration = i;
    }

    public final void scheduleFinishResizePip(Rect rect) {
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        if (!pipBoundsState.getBounds().equals(rect)) {
            PipTaskListener$$ExternalSyntheticLambda3 pipTaskListener$$ExternalSyntheticLambda3 = this.mPipParamsSupplier;
            if (!(pipTaskListener$$ExternalSyntheticLambda3 == null ? new PictureInPictureParams.Builder().build() : pipTaskListener$$ExternalSyntheticLambda3.f$0.mPictureInPictureParams).isSeamlessResizeEnabled() && (pipBoundsState.getBounds().width() != rect.width() || pipBoundsState.getBounds().height() != rect.height())) {
                Rect rect2 = new Rect(rect);
                rect2.offsetTo(0, 0);
                SurfaceControl.Transaction transaction = ((PipSurfaceTransactionHelper.VsyncSurfaceControlTransactionFactory) this.mSurfaceControlTransactionFactory).getTransaction();
                SurfaceControl surfaceControl = this.mPipTransitionState.mPinnedTaskLeash;
                SurfaceControl surfaceControlTakeScreenshot = ScreenshotUtils.takeScreenshot(transaction, surfaceControl, surfaceControl, rect2, 2147483645);
                startOverlayFadeoutAnimation(surfaceControlTakeScreenshot, false, new PipScheduler$$ExternalSyntheticLambda2(this, surfaceControlTakeScreenshot, 0));
            }
            pipBoundsState.setBounds(rect);
            PipTouchHandler$$ExternalSyntheticLambda1 pipTouchHandler$$ExternalSyntheticLambda1 = this.mUpdateMovementBoundsRunnable;
            if (pipTouchHandler$$ExternalSyntheticLambda1 != null) {
                pipTouchHandler$$ExternalSyntheticLambda1.run();
            }
        }
        this.mPipTransitionController.finishTransition();
    }

    public final void scheduleUserResizePip(Rect rect, float f) {
        boolean zIsEmpty = rect.isEmpty();
        PipTransitionState pipTransitionState = this.mPipTransitionState;
        if (zIsEmpty || !pipTransitionState.isInPip()) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[3]) {
                ProtoLogImpl_1771455215.w(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 4739848556506594355L, 0, "PipScheduler", String.valueOf(rect), String.valueOf(pipTransitionState));
                return;
            }
            return;
        }
        SurfaceControl surfaceControl = pipTransitionState.mPinnedTaskLeash;
        SurfaceControl.Transaction transaction = ((PipSurfaceTransactionHelper.VsyncSurfaceControlTransactionFactory) this.mSurfaceControlTransactionFactory).getTransaction();
        Matrix matrix = new Matrix();
        float fWidth = rect.width();
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        float fWidth2 = fWidth / pipBoundsState.getBounds().width();
        matrix.setScale(fWidth2, fWidth2);
        matrix.postTranslate(rect.left, rect.top);
        matrix.postRotate(f, rect.centerX(), rect.centerY());
        Rect bounds = pipBoundsState.getBounds();
        this.mPipSurfaceTransactionHelper.getClass();
        transaction.setCornerRadius(surfaceControl, r8.mCornerRadius * ((float) (Math.hypot(bounds.width(), bounds.height()) / Math.hypot(rect.width(), rect.height()))));
        transaction.setMatrix(surfaceControl, matrix, new float[9]);
        transaction.apply();
    }

    public void setOverlayFadeoutAnimator(PipAlphaAnimator pipAlphaAnimator) {
        this.mOverlayFadeoutAnimator = pipAlphaAnimator;
    }

    public void setPipAlphaAnimatorSupplier(PipAlphaAnimatorSupplier pipAlphaAnimatorSupplier) {
        this.mPipAlphaAnimatorSupplier = pipAlphaAnimatorSupplier;
    }

    public void setSurfaceControlTransactionFactory(PipSurfaceTransactionHelper.SurfaceControlTransactionFactory surfaceControlTransactionFactory) {
        this.mSurfaceControlTransactionFactory = surfaceControlTransactionFactory;
    }

    public final void startOverlayFadeoutAnimation(SurfaceControl surfaceControl, boolean z, Runnable runnable) {
        PipAlphaAnimator pipAlphaAnimator = this.mPipAlphaAnimatorSupplier.get(this.mContext, surfaceControl);
        this.mOverlayFadeoutAnimator = pipAlphaAnimator;
        pipAlphaAnimator.setDuration(500L);
        this.mOverlayFadeoutAnimator.setStartDelay(z ? EXTRA_CONTENT_OVERLAY_FADE_OUT_DELAY_MS : 0L);
        PipAlphaAnimator pipAlphaAnimator2 = this.mOverlayFadeoutAnimator;
        pipAlphaAnimator2.mAnimationEndCallback = new PipScheduler$$ExternalSyntheticLambda2(this, runnable, 1);
        pipAlphaAnimator2.start();
    }
}

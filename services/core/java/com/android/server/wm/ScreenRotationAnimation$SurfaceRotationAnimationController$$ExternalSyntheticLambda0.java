package com.android.server.wm;

import android.R;

import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;

public final /* synthetic */
class ScreenRotationAnimation$SurfaceRotationAnimationController$$ExternalSyntheticLambda0
        implements SurfaceAnimator.OnAnimationFinishedCallback {
    public final /* synthetic */ ScreenRotationAnimation.SurfaceRotationAnimationController f$0;

    public /* synthetic */
    ScreenRotationAnimation$SurfaceRotationAnimationController$$ExternalSyntheticLambda0(
            ScreenRotationAnimation.SurfaceRotationAnimationController
                    surfaceRotationAnimationController) {
        this.f$0 = surfaceRotationAnimationController;
    }

    @Override // com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback
    public final void onAnimationFinished(int i, AnimationAdapter animationAdapter) {
        ScreenRotationAnimation.SurfaceRotationAnimationController
                surfaceRotationAnimationController = this.f$0;
        WindowManagerGlobalLock windowManagerGlobalLock =
                ScreenRotationAnimation.this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (surfaceRotationAnimationController.isAnimating()) {
                    if (ProtoLogImpl_54989576.Cache.WM_DEBUG_ORIENTATION_enabled[1]) {
                        long j = i;
                        SurfaceAnimator surfaceAnimator =
                                surfaceRotationAnimationController.mDisplayAnimator;
                        String valueOf =
                                String.valueOf(
                                        surfaceAnimator != null
                                                ? Boolean.valueOf(surfaceAnimator.isAnimating())
                                                : null);
                        SurfaceAnimator surfaceAnimator2 =
                                surfaceRotationAnimationController.mEnterBlackFrameAnimator;
                        String valueOf2 =
                                String.valueOf(
                                        surfaceAnimator2 != null
                                                ? Boolean.valueOf(surfaceAnimator2.isAnimating())
                                                : null);
                        SurfaceAnimator surfaceAnimator3 =
                                surfaceRotationAnimationController.mRotateScreenAnimator;
                        String valueOf3 =
                                String.valueOf(
                                        surfaceAnimator3 != null
                                                ? Boolean.valueOf(surfaceAnimator3.isAnimating())
                                                : null);
                        SurfaceAnimator surfaceAnimator4 =
                                surfaceRotationAnimationController.mScreenshotRotationAnimator;
                        ProtoLogImpl_54989576.v(
                                ProtoLogGroup.WM_DEBUG_ORIENTATION,
                                6883897856740637908L,
                                1,
                                null,
                                Long.valueOf(j),
                                valueOf,
                                valueOf2,
                                valueOf3,
                                String.valueOf(
                                        surfaceAnimator4 != null
                                                ? Boolean.valueOf(surfaceAnimator4.isAnimating())
                                                : null));
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                if (ProtoLogImpl_54989576.Cache.WM_DEBUG_ORIENTATION_enabled[0]) {
                    ProtoLogImpl_54989576.d(
                            ProtoLogGroup.WM_DEBUG_ORIENTATION,
                            -3943622313307983155L,
                            0,
                            null,
                            null);
                }
                surfaceRotationAnimationController.mEnterBlackFrameAnimator = null;
                surfaceRotationAnimationController.mScreenshotRotationAnimator = null;
                surfaceRotationAnimationController.mRotateScreenAnimator = null;
                ScreenRotationAnimation screenRotationAnimation = ScreenRotationAnimation.this;
                screenRotationAnimation.mService.mAnimator.mBulkUpdateParams |= 1;
                DisplayContent displayContent = screenRotationAnimation.mDisplayContent;
                if (displayContent.mScreenRotationAnimation == screenRotationAnimation) {
                    displayContent.setRotationAnimation(null);
                    DisplayRotationCompatPolicy displayRotationCompatPolicy =
                            ScreenRotationAnimation.this
                                    .mDisplayContent
                                    .mAppCompatCameraPolicy
                                    .mDisplayRotationCompatPolicy;
                    if (displayRotationCompatPolicy != null) {
                        ActivityRecord activityRecord =
                                displayRotationCompatPolicy.mDisplayContent.topRunningActivity(
                                        true);
                        if (displayRotationCompatPolicy.isTreatmentEnabledForDisplay()
                                && displayRotationCompatPolicy.isTreatmentEnabledForActivity(
                                        true, activityRecord)) {
                            displayRotationCompatPolicy.showToast(R.string.ime_action_default);
                        }
                    }
                } else {
                    screenRotationAnimation.kill();
                }
                ScreenRotationAnimation.this.mService.updateRotationUnchecked(false, false);
                WindowManagerService.resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }
}

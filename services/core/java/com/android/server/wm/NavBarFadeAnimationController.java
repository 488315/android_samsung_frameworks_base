package com.android.server.wm;

import android.view.SurfaceControl;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;

import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class NavBarFadeAnimationController extends FadeAnimationController {
    public static final Interpolator FADE_IN_INTERPOLATOR =
            new PathInterpolator(
                    FullScreenMagnificationGestureHandler.MAX_SCALE,
                    FullScreenMagnificationGestureHandler.MAX_SCALE,
                    FullScreenMagnificationGestureHandler.MAX_SCALE,
                    1.0f);
    public static final Interpolator FADE_OUT_INTERPOLATOR =
            new PathInterpolator(0.2f, FullScreenMagnificationGestureHandler.MAX_SCALE, 1.0f, 1.0f);
    public final Animation mFadeInAnimation;
    public SurfaceControl mFadeInParent;
    public final Animation mFadeOutAnimation;
    public SurfaceControl mFadeOutParent;
    public final WindowState mNavigationBar;
    public boolean mPlaySequentially;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NavFadeAnimationAdapter
            extends FadeAnimationController.FadeAnimationAdapter {
        public final SurfaceControl mParent;

        public NavFadeAnimationAdapter(
                FadeAnimationController.AnonymousClass1 anonymousClass1,
                SurfaceAnimationRunner surfaceAnimationRunner,
                boolean z,
                SurfaceControl surfaceControl) {
            super(anonymousClass1, surfaceAnimationRunner, z);
            this.mParent = surfaceControl;
        }

        @Override // com.android.server.wm.FadeAnimationController.FadeAnimationAdapter,
                  // com.android.server.wm.AnimationAdapter
        public final boolean shouldDeferAnimationFinish() {
            NavBarFadeAnimationController navBarFadeAnimationController =
                    NavBarFadeAnimationController.this;
            boolean z = navBarFadeAnimationController.mPlaySequentially;
            boolean z2 = this.mShow;
            if (!z) {
                return !z2;
            }
            if (z2) {
                return false;
            }
            navBarFadeAnimationController.fadeWindowToken(true);
            return false;
        }

        @Override // com.android.server.wm.LocalAnimationAdapter,
                  // com.android.server.wm.AnimationAdapter
        public final void startAnimation(
                SurfaceControl surfaceControl,
                SurfaceControl.Transaction transaction,
                int i,
                SurfaceAnimator.OnAnimationFinishedCallback onAnimationFinishedCallback) {
            super.startAnimation(surfaceControl, transaction, i, onAnimationFinishedCallback);
            SurfaceControl surfaceControl2 = this.mParent;
            if (surfaceControl2 == null || !surfaceControl2.isValid()) {
                return;
            }
            transaction.reparent(surfaceControl, this.mParent);
            transaction.setLayer(surfaceControl, Integer.MAX_VALUE);
        }
    }

    public NavBarFadeAnimationController(DisplayContent displayContent) {
        super(displayContent);
        this.mPlaySequentially = false;
        this.mNavigationBar = displayContent.mDisplayPolicy.mNavigationBar;
        AlphaAnimation alphaAnimation =
                new AlphaAnimation(FullScreenMagnificationGestureHandler.MAX_SCALE, 1.0f);
        this.mFadeInAnimation = alphaAnimation;
        alphaAnimation.setDuration(266L);
        alphaAnimation.setInterpolator(FADE_IN_INTERPOLATOR);
        AlphaAnimation alphaAnimation2 =
                new AlphaAnimation(1.0f, FullScreenMagnificationGestureHandler.MAX_SCALE);
        this.mFadeOutAnimation = alphaAnimation2;
        alphaAnimation2.setDuration(133L);
        alphaAnimation2.setInterpolator(FADE_OUT_INTERPOLATOR);
    }

    @Override // com.android.server.wm.FadeAnimationController
    public final FadeAnimationController.FadeAnimationAdapter createAdapter(
            FadeAnimationController.AnonymousClass1 anonymousClass1,
            boolean z,
            WindowToken windowToken) {
        return new NavFadeAnimationAdapter(
                anonymousClass1,
                windowToken.getSurfaceAnimationRunner(),
                z,
                z ? this.mFadeInParent : this.mFadeOutParent);
    }

    public final void fadeOutAndInSequentially(long j, SurfaceControl surfaceControl) {
        this.mPlaySequentially = true;
        if (j > 0) {
            long j2 = (2 * j) / 3;
            this.mFadeOutAnimation.setDuration(j - j2);
            this.mFadeInAnimation.setDuration(j2);
        }
        this.mFadeOutParent = null;
        this.mFadeInParent = surfaceControl;
        fadeWindowToken(false);
    }

    public final void fadeWindowToken(final boolean z) {
        AsyncRotationController asyncRotationController =
                this.mDisplayContent.getAsyncRotationController();
        Runnable runnable =
                new Runnable() { // from class:
                                 // com.android.server.wm.NavBarFadeAnimationController$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        NavBarFadeAnimationController navBarFadeAnimationController =
                                NavBarFadeAnimationController.this;
                        navBarFadeAnimationController.fadeWindowToken(
                                z, navBarFadeAnimationController.mNavigationBar.mToken, null);
                    }
                };
        if (asyncRotationController == null) {
            runnable.run();
            return;
        }
        AsyncRotationController.Operation operation =
                (AsyncRotationController.Operation)
                        asyncRotationController.mTargetWindowTokens.get(this.mNavigationBar.mToken);
        if (operation == null || operation.mAction != 2) {
            if (z) {
                asyncRotationController.mOnShowRunnable = runnable;
            } else {
                runnable.run();
            }
        }
    }

    @Override // com.android.server.wm.FadeAnimationController
    public final Animation getFadeInAnimation() {
        return this.mFadeInAnimation;
    }

    @Override // com.android.server.wm.FadeAnimationController
    public final Animation getFadeOutAnimation() {
        return this.mFadeOutAnimation;
    }
}

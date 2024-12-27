package com.android.server.accessibility.magnification;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class MagnificationThumbnail {
    public final WindowManager.LayoutParams mBackgroundParams;
    public final Context mContext;
    public final Handler mHandler;
    public boolean mIsFadingIn;
    public ObjectAnimator mThumbnailAnimator;
    public int mThumbnailHeight;
    public FrameLayout mThumbnailLayout;
    public View mThumbnailView;
    public int mThumbnailWidth;
    public boolean mVisible = false;
    public Rect mWindowBounds;
    public final WindowManager mWindowManager;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.accessibility.magnification.MagnificationThumbnail$1, reason: invalid class name */
    public final class AnonymousClass1 implements Animator.AnimatorListener {
        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationCancel(Animator animator) {}

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {}

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationRepeat(Animator animator) {}

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationStart(Animator animator) {}
    }

    public MagnificationThumbnail(Context context, WindowManager windowManager, Handler handler) {
        this.mContext = context;
        this.mWindowManager = windowManager;
        this.mHandler = handler;
        this.mWindowBounds = windowManager.getCurrentWindowMetrics().getBounds();
        WindowManager.LayoutParams layoutParams =
                new WindowManager.LayoutParams(-2, -2, 2027, 24, -2);
        layoutParams.inputFeatures = 1;
        layoutParams.gravity = 83;
        layoutParams.setFitInsetsTypes(
                WindowInsets.Type.ime() | WindowInsets.Type.navigationBars());
        layoutParams.receiveInsetsIgnoringZOrder = true;
        this.mBackgroundParams = layoutParams;
        this.mThumbnailWidth = 0;
        this.mThumbnailHeight = 0;
        handler.post(new MagnificationThumbnail$$ExternalSyntheticLambda0(this, 2));
    }

    public final void animateThumbnail(boolean z) {
        MagnificationThumbnail$$ExternalSyntheticLambda0
                magnificationThumbnail$$ExternalSyntheticLambda0 =
                        new MagnificationThumbnail$$ExternalSyntheticLambda0(this, 0);
        Handler handler = this.mHandler;
        handler.removeCallbacks(magnificationThumbnail$$ExternalSyntheticLambda0);
        if (z) {
            handler.postDelayed(
                    new MagnificationThumbnail$$ExternalSyntheticLambda0(this, 0), 500L);
        }
        if (z == this.mIsFadingIn) {
            return;
        }
        this.mIsFadingIn = z;
        if (z && !this.mVisible) {
            this.mWindowManager.addView(this.mThumbnailLayout, this.mBackgroundParams);
            this.mVisible = true;
        }
        ObjectAnimator objectAnimator = this.mThumbnailAnimator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        ObjectAnimator ofFloat =
                ObjectAnimator.ofFloat(
                        this.mThumbnailLayout,
                        "alpha",
                        z ? 1.0f : FullScreenMagnificationGestureHandler.MAX_SCALE);
        this.mThumbnailAnimator = ofFloat;
        ofFloat.setDuration(z ? 200L : 1000L);
        this.mThumbnailAnimator.setInterpolator(new LinearInterpolator());
        this.mThumbnailAnimator.addListener(new AnonymousClass1());
        this.mThumbnailAnimator.start();
    }

    public final void updateThumbnailMainThread(float f, float f2, float f3) {
        animateThumbnail(true);
        float scaleX = Float.isNaN(f) ? this.mThumbnailView.getScaleX() : 1.0f / f;
        if (!Float.isNaN(f)) {
            this.mThumbnailView.setScaleX(scaleX);
            this.mThumbnailView.setScaleY(scaleX);
        }
        if (Float.isNaN(f2)
                || Float.isNaN(f3)
                || this.mThumbnailWidth <= 0
                || this.mThumbnailHeight <= 0) {
            return;
        }
        float paddingTop = this.mThumbnailView.getPaddingTop();
        this.mThumbnailView.setTranslationX(
                (f2 * 0.14285715f) - ((this.mThumbnailWidth / 2.0f) + paddingTop));
        this.mThumbnailView.setTranslationY(
                (f3 * 0.14285715f) - ((this.mThumbnailHeight / 2.0f) + paddingTop));
    }
}

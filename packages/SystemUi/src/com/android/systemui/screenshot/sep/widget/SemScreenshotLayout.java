package com.android.systemui.screenshot.sep.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.PathInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.systemui.R;
import com.android.systemui.controls.management.ControlsListingControllerImpl$$ExternalSyntheticOutline0;
import com.android.systemui.screenshot.ScreenshotController;
import com.android.systemui.screenshot.sep.ScreenCaptureHelper;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class SemScreenshotLayout extends FrameLayout {
    public static final PathInterpolator CUSTOM_INTERPOLATOR = new PathInterpolator(0.7f, 0.0f, 0.7f, 1.0f);
    public static final PathInterpolator SINEINOUT70 = new PathInterpolator(0.33f, 0.0f, 0.3f, 1.0f);
    public final String TAG;
    public CaptureEffectView mAnimationView;
    public ScreenshotController.C23414 mCallback;
    public float mScreenDegrees;
    public ImageView mScreenshotImageView;

    public SemScreenshotLayout(Context context) {
        super(context);
        this.TAG = "Screenshot";
    }

    public final void addCaptureEffectViewInLayout(ScreenCaptureHelper screenCaptureHelper) {
        FrameLayout.LayoutParams layoutParams;
        boolean isB5ScreenEffect = screenCaptureHelper.isB5ScreenEffect();
        ControlsListingControllerImpl$$ExternalSyntheticOutline0.m117m("isB5ScreenEffect: ", isB5ScreenEffect, this.TAG);
        if (isB5ScreenEffect) {
            this.mAnimationView = new CaptureEffectViewB5(getContext());
        } else {
            this.mAnimationView = new CaptureEffectView(getContext());
        }
        this.mAnimationView.setDegree(screenCaptureHelper.screenDegrees);
        if (!(!screenCaptureHelper.isLetterBoxHide() || screenCaptureHelper.isNavigationBarVisible) || screenCaptureHelper.isB5CoverScreenInReverseMode()) {
            Rect screenshotEffectRect = screenCaptureHelper.getScreenshotEffectRect();
            FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(screenshotEffectRect.width(), screenshotEffectRect.height());
            layoutParams2.gravity = 80;
            layoutParams = layoutParams2;
        } else {
            layoutParams = new FrameLayout.LayoutParams(-1, -1);
        }
        this.mAnimationView.setLayoutParams(layoutParams);
        this.mScreenshotImageView.setLayoutParams(layoutParams);
        addView(this.mAnimationView);
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        return true;
    }

    public final void startAnimation(float f, float f2) {
        Resources resources = getResources();
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.global_screenshot_effect_width);
        int i = dimensionPixelSize / 2;
        int dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.global_screenshot_effect_round_x) - i;
        int dimensionPixelSize3 = resources.getDimensionPixelSize(R.dimen.global_screenshot_effect_round_y) - i;
        float f3 = dimensionPixelSize * 2;
        float f4 = f / (f - f3);
        float f5 = f2 / (f2 - f3);
        Log.i(this.TAG, "setupAndStartAnimation: screenWidth:" + f + " screenHeight:" + f2);
        CaptureEffectView captureEffectView = this.mAnimationView;
        if (captureEffectView == null) {
            Log.e(this.TAG, "mAnimationView is null");
            return;
        }
        float f6 = this.mScreenDegrees;
        if (f6 == 0.0f || f6 == 180.0f) {
            captureEffectView.setEffectParams(dimensionPixelSize, dimensionPixelSize2, dimensionPixelSize3);
        } else {
            captureEffectView.setEffectParams(dimensionPixelSize, dimensionPixelSize3, dimensionPixelSize2);
        }
        this.mAnimationView.setVisibility(4);
        ScaleAnimation scaleAnimation = new ScaleAnimation(f4, 1.0f, f5, 1.0f, 1, 0.5f, 1, 0.5f);
        ScaleAnimation scaleAnimation2 = new ScaleAnimation(1.0f, f4, 1.0f, f5, 1, 0.5f, 1, 0.5f);
        scaleAnimation.setInterpolator(SINEINOUT70);
        scaleAnimation.setDuration(150L);
        scaleAnimation2.setInterpolator(CUSTOM_INTERPOLATOR);
        scaleAnimation2.setDuration(167L);
        scaleAnimation2.setStartOffset(216L);
        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(scaleAnimation2);
        animationSet.setAnimationListener(new AnimationAnimationListenerC23561());
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        final AlphaAnimation alphaAnimation2 = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(150L);
        alphaAnimation2.setDuration(167L);
        alphaAnimation2.setStartOffset(66L);
        alphaAnimation2.setFillAfter(true);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.android.systemui.screenshot.sep.widget.SemScreenshotLayout.2
            @Override // android.view.animation.Animation.AnimationListener
            public final void onAnimationEnd(Animation animation) {
                SemScreenshotLayout.this.mScreenshotImageView.startAnimation(alphaAnimation2);
            }

            @Override // android.view.animation.Animation.AnimationListener
            public final void onAnimationStart(Animation animation) {
                SemScreenshotLayout.this.mScreenshotImageView.setVisibility(0);
            }

            @Override // android.view.animation.Animation.AnimationListener
            public final void onAnimationRepeat(Animation animation) {
            }
        });
        alphaAnimation2.setAnimationListener(new AnimationAnimationListenerC23583());
        this.mAnimationView.startAnimation(animationSet);
        this.mScreenshotImageView.startAnimation(alphaAnimation);
    }

    public SemScreenshotLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.TAG = "Screenshot";
    }

    public SemScreenshotLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.TAG = "Screenshot";
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.screenshot.sep.widget.SemScreenshotLayout$1 */
    public final class AnimationAnimationListenerC23561 implements Animation.AnimationListener {
        public AnimationAnimationListenerC23561() {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public final void onAnimationEnd(Animation animation) {
            SemScreenshotLayout.this.mAnimationView.setVisibility(8);
            Log.d(SemScreenshotLayout.this.TAG, "scaleAnimation: post a finishAnimation callback.");
            SemScreenshotLayout.this.post(new SemScreenshotLayout$1$$ExternalSyntheticLambda0(this, 0));
        }

        @Override // android.view.animation.Animation.AnimationListener
        public final void onAnimationStart(Animation animation) {
            SemScreenshotLayout.this.mAnimationView.setVisibility(0);
        }

        @Override // android.view.animation.Animation.AnimationListener
        public final void onAnimationRepeat(Animation animation) {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.screenshot.sep.widget.SemScreenshotLayout$3 */
    public final class AnimationAnimationListenerC23583 implements Animation.AnimationListener {
        public AnimationAnimationListenerC23583() {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public final void onAnimationEnd(Animation animation) {
            SemScreenshotLayout.this.mScreenshotImageView.setVisibility(4);
        }

        @Override // android.view.animation.Animation.AnimationListener
        public final void onAnimationStart(Animation animation) {
            Log.d(SemScreenshotLayout.this.TAG, "alphaOut: post a onDismiss callback.");
            new Handler(Looper.getMainLooper()).postDelayed(new SemScreenshotLayout$1$$ExternalSyntheticLambda0(this, 1), 233L);
        }

        @Override // android.view.animation.Animation.AnimationListener
        public final void onAnimationRepeat(Animation animation) {
        }
    }
}

package com.android.systemui.screenshot.sep.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.RoundedCorner;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.CubicBezierEasing$$ExternalSyntheticOutline0;
import com.android.settingslib.satellite.SatelliteDialogUtils$requestIsEnabled$2$1$1$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.screenshot.ScreenshotController;
import com.android.systemui.screenshot.sep.ScreenCaptureHelper;
import com.android.systemui.screenshot.sep.ScreenshotUtils;
import com.android.systemui.util.SettingsHelper;

public class SemScreenshotLayout extends FrameLayout {
    public static final PathInterpolator CUSTOM_INTERPOLATOR = new PathInterpolator(0.7f, 0.0f, 0.7f, 1.0f);
    public static final PathInterpolator SINEINOUT70 = new PathInterpolator(0.33f, 0.0f, 0.3f, 1.0f);
    public final String TAG;
    public CaptureEffectView mAnimationView;
    public ScreenshotController.AnonymousClass2 mCallback;
    public int mCornerRadius;
    public float mScreenDegrees;
    public ImageView mScreenshotImageView;

    public SemScreenshotLayout(Context context) {
        super(context);
        this.TAG = "Screenshot";
    }

    public final void addCaptureEffectViewInLayout(ScreenCaptureHelper screenCaptureHelper) {
        FrameLayout.LayoutParams layoutParams;
        boolean isB5ScreenEffect = screenCaptureHelper.isB5ScreenEffect();
        SatelliteDialogUtils$requestIsEnabled$2$1$1$$ExternalSyntheticOutline0.m("isB5ScreenEffect: ", this.TAG, isB5ScreenEffect);
        if (isB5ScreenEffect) {
            this.mAnimationView = new CaptureEffectViewB5(getContext());
        } else {
            this.mAnimationView = new CaptureEffectView(getContext());
        }
        this.mAnimationView.setDegree(screenCaptureHelper.screenDegrees);
        if ((!screenCaptureHelper.isLetterBoxHide() || screenCaptureHelper.isNavigationBarVisible) && !screenCaptureHelper.isB5CoverScreenInReverseMode()) {
            layoutParams = new FrameLayout.LayoutParams(-1, -1);
        } else {
            Rect screenshotEffectRect = screenCaptureHelper.getScreenshotEffectRect();
            FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(screenshotEffectRect.width(), screenshotEffectRect.height());
            layoutParams2.gravity = 80;
            layoutParams = layoutParams2;
        }
        this.mAnimationView.setLayoutParams(layoutParams);
        this.mScreenshotImageView.setLayoutParams(layoutParams);
        addView(this.mAnimationView);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        RoundedCorner roundedCorner;
        if (!ScreenshotUtils.isDesktopMode(((FrameLayout) this).mContext) && (roundedCorner = getRootWindowInsets().getRoundedCorner(0)) != null) {
            this.mCornerRadius = roundedCorner.getRadius();
        }
        super.onLayout(z, i, i2, i3, i4);
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        return true;
    }

    public final void startAnimation(float f, float f2) {
        Resources resources = getResources();
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.gradient_screenshot_effect_width);
        if (Settings.System.getIntForUser(((FrameLayout) this).mContext.getContentResolver(), SettingsHelper.INDEX_ONE_HAND_MODE_RUNNING, 0, -2) == 1 || ScreenshotUtils.isDesktopMode(((FrameLayout) this).mContext)) {
            this.mCornerRadius = resources.getDimensionPixelSize(R.dimen.global_screenshot_effect_round_one_handed_mode);
        }
        final int i = this.mCornerRadius;
        TooltipPopup$$ExternalSyntheticOutline0.m(this.mCornerRadius, this.TAG, CubicBezierEasing$$ExternalSyntheticOutline0.m("setupAndStartAnimation: screenWidth:", f, " screenHeight:", f2, " cornerRadius:"));
        CaptureEffectView captureEffectView = this.mAnimationView;
        if (captureEffectView == null) {
            Log.e(this.TAG, "mAnimationView is null");
            return;
        }
        float f3 = this.mScreenDegrees;
        if (f3 == 0.0f || f3 == 180.0f) {
            captureEffectView.setEffectParams(dimensionPixelSize, i, i);
        } else {
            captureEffectView.setEffectParams(dimensionPixelSize, i, i);
        }
        this.mAnimationView.setVisibility(4);
        ValueAnimator ofInt = ValueAnimator.ofInt(0, dimensionPixelSize);
        final int i2 = 0;
        ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(this) { // from class: com.android.systemui.screenshot.sep.widget.SemScreenshotLayout$$ExternalSyntheticLambda0
            public final /* synthetic */ SemScreenshotLayout f$0;

            {
                this.f$0 = this;
            }

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                switch (i2) {
                    case 0:
                        SemScreenshotLayout semScreenshotLayout = this.f$0;
                        semScreenshotLayout.mAnimationView.setEffectParams(((Integer) valueAnimator.getAnimatedValue()).intValue(), i, i);
                        semScreenshotLayout.mAnimationView.invalidate();
                        break;
                    default:
                        SemScreenshotLayout semScreenshotLayout2 = this.f$0;
                        semScreenshotLayout2.mAnimationView.setEffectParams(((Integer) valueAnimator.getAnimatedValue()).intValue(), i, i);
                        semScreenshotLayout2.mAnimationView.invalidate();
                        break;
                }
            }
        });
        ofInt.setInterpolator(SINEINOUT70);
        ofInt.setDuration(150L);
        ValueAnimator ofInt2 = ValueAnimator.ofInt(dimensionPixelSize, 0);
        final int i3 = 1;
        ofInt2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(this) { // from class: com.android.systemui.screenshot.sep.widget.SemScreenshotLayout$$ExternalSyntheticLambda0
            public final /* synthetic */ SemScreenshotLayout f$0;

            {
                this.f$0 = this;
            }

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                switch (i3) {
                    case 0:
                        SemScreenshotLayout semScreenshotLayout = this.f$0;
                        semScreenshotLayout.mAnimationView.setEffectParams(((Integer) valueAnimator.getAnimatedValue()).intValue(), i, i);
                        semScreenshotLayout.mAnimationView.invalidate();
                        break;
                    default:
                        SemScreenshotLayout semScreenshotLayout2 = this.f$0;
                        semScreenshotLayout2.mAnimationView.setEffectParams(((Integer) valueAnimator.getAnimatedValue()).intValue(), i, i);
                        semScreenshotLayout2.mAnimationView.invalidate();
                        break;
                }
            }
        });
        ofInt2.setInterpolator(CUSTOM_INTERPOLATOR);
        ofInt2.setDuration(167L);
        ofInt2.setStartDelay(216L);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ofInt, ofInt2);
        animatorSet.addListener(new AnonymousClass1());
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
        alphaAnimation2.setAnimationListener(new AnonymousClass3());
        animatorSet.start();
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

    /* renamed from: com.android.systemui.screenshot.sep.widget.SemScreenshotLayout$1, reason: invalid class name */
    public final class AnonymousClass1 implements Animator.AnimatorListener {
        public AnonymousClass1() {
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            SemScreenshotLayout.this.mAnimationView.setVisibility(8);
            Log.d(SemScreenshotLayout.this.TAG, "scaleAnimation: post a finishAnimation callback.");
            SemScreenshotLayout.this.post(new SemScreenshotLayout$1$$ExternalSyntheticLambda0(this, 0));
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationStart(Animator animator) {
            SemScreenshotLayout.this.mAnimationView.setVisibility(0);
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationCancel(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationRepeat(Animator animator) {
        }
    }

    /* renamed from: com.android.systemui.screenshot.sep.widget.SemScreenshotLayout$3, reason: invalid class name */
    public final class AnonymousClass3 implements Animation.AnimationListener {
        public AnonymousClass3() {
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

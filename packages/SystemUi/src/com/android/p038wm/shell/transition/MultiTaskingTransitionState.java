package com.android.p038wm.shell.transition;

import android.app.ActivityManager;
import android.content.res.Configuration;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Slog;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.window.TransitionInfo;
import com.android.internal.policy.TransitionAnimation;
import com.android.p038wm.shell.common.DisplayController;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.util.InterpolatorUtils;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MultiTaskingTransitionState {
    public Animation mAnimation;
    public boolean mAnimationLoaded;
    public TransitionInfo.AnimationOptions mAnimationOptions;
    public TransitionInfo.Change mChange;
    public final DisplayController mDisplayController;
    public boolean mHasCustomDisplayChangeTransition;
    public boolean mIsEnter;
    public boolean mIsFreeformMovingBehindSplitScreen;
    public ActivityManager.RunningTaskInfo mOpeningAppsEdgeTaskInfo;
    public boolean mSeparatedFromCustomDisplayChange;
    public ActivityManager.RunningTaskInfo mTaskInfo;
    public final TransitionAnimation mTransitAnimation;
    public int mTransitionType = 0;
    public final Configuration mConfiguration = new Configuration();
    public int mTaskId = -1;
    public int mDisplayId = -1;
    public int mWindowingMode = 0;
    public int mMinimizeAnimState = 0;
    public final PointF mMinimizePoint = new PointF();
    public boolean mIsPopOverAnimationNeeded = false;
    public float mFreeformStashScale = 1.0f;
    public int mForceHidingTransit = 0;

    public MultiTaskingTransitionState(TransitionAnimation transitionAnimation, DisplayController displayController) {
        this.mTransitAnimation = transitionAnimation;
        this.mDisplayController = displayController;
    }

    public final Animation createMinimizeAnimation(boolean z, PointF pointF, Rect rect, float f, boolean z2) {
        float f2;
        float f3;
        float f4;
        float f5;
        Configuration configuration;
        float f6;
        ScaleAnimation scaleAnimation;
        float f7;
        float f8;
        ScaleAnimation scaleAnimation2;
        boolean z3 = CoreRune.MT_NEW_DEX_SHELL_TRANSITION;
        Configuration configuration2 = this.mConfiguration;
        boolean z4 = z3 && configuration2.isNewDexMode();
        float width = rect.width() / 2.0f;
        float height = rect.height() / 2.0f;
        float f9 = 0.2f;
        float f10 = z ? z4 ? 0.0f : 0.2f : f;
        if (z) {
            f9 = f;
        } else if (z4) {
            f9 = 0.0f;
        }
        int centerY = (int) (rect.centerY() * f);
        float centerX = pointF.x - (rect.centerX() + (z2 ? rect.width() - (rect.width() * f) : 0.0f));
        float f11 = pointF.y - centerY;
        float f12 = z ? centerX : 0.0f;
        if (z) {
            centerX = 0.0f;
        }
        float f13 = z ? f11 : 0.0f;
        if (z) {
            f11 = 0.0f;
        }
        float f14 = z ? 0.0f : 1.0f;
        float f15 = z ? 1.0f : 0.0f;
        if (f <= 0.0f || f >= 1.0f) {
            f2 = f14;
            f3 = f15;
            f4 = f13;
            f5 = f12;
            configuration = configuration2;
            f6 = centerX;
            scaleAnimation = new ScaleAnimation(f10, f9, f10, f9, width, height);
        } else {
            if (z2) {
                f7 = f15;
                f8 = f14;
                scaleAnimation2 = new ScaleAnimation(f10, f9, f10, f9, 1, 1.0f, 1, 0.0f);
            } else {
                f7 = f15;
                f8 = f14;
                scaleAnimation2 = new ScaleAnimation(f10, f9, f10, f9);
            }
            configuration = configuration2;
            scaleAnimation = scaleAnimation2;
            f3 = f7;
            f4 = f13;
            f5 = f12;
            f6 = centerX;
            f2 = f8;
        }
        TranslateAnimation translateAnimation = new TranslateAnimation(f5, f6, f4, f11);
        AlphaAnimation alphaAnimation = new AlphaAnimation(f2, f3);
        translateAnimation.setInterpolator(z4 ? InterpolatorUtils.ONE_EASING : InterpolatorUtils.SINE_IN_OUT_80);
        alphaAnimation.setInterpolator(z4 ? InterpolatorUtils.ONE_EASING : InterpolatorUtils.SINE_IN_OUT_33);
        scaleAnimation.setInterpolator(z4 ? InterpolatorUtils.ONE_EASING : InterpolatorUtils.SINE_IN_OUT_80);
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(alphaAnimation);
        animationSet.setFillAfter(true);
        animationSet.setFillEnabled(true);
        animationSet.setDuration((z || this.mForceHidingTransit != 2) ? (CoreRune.MT_NEW_DEX_SHELL_TRANSITION && configuration.isNewDexMode()) ? 450L : 250L : 0L);
        if (z) {
            animationSet.setZAdjustment(1);
        }
        return animationSet;
    }

    public final Rect getBounds() {
        return new Rect(this.mChange.getEndAbsBounds());
    }

    public final boolean isActivityTypeHome() {
        ActivityManager.RunningTaskInfo runningTaskInfo = this.mTaskInfo;
        return (runningTaskInfo != null && runningTaskInfo.getActivityType() == 2) || this.mConfiguration.windowConfiguration.getActivityType() == 2;
    }

    public final boolean isClosingTransitionType() {
        int i = this.mTransitionType;
        return i == 2 || i == 4;
    }

    public final Animation loadAnimationFromResources(int i) {
        return this.mTransitAnimation.loadAnimationRes("android", i);
    }

    public final void reset() {
        this.mAnimation = null;
        this.mAnimationLoaded = false;
        this.mTransitionType = 0;
        this.mIsEnter = false;
        this.mConfiguration.setToDefaults();
        this.mChange = null;
        this.mDisplayId = -1;
        this.mTaskInfo = null;
        this.mTaskId = -1;
        this.mWindowingMode = 0;
        this.mAnimationOptions = null;
        if (CoreRune.MW_SPLIT_SHELL_TRANSITION) {
            this.mOpeningAppsEdgeTaskInfo = null;
        }
        if (CoreRune.MW_FREEFORM_SHELL_TRANSITION) {
            this.mIsFreeformMovingBehindSplitScreen = false;
        }
        if (CoreRune.MW_SHELL_DISPLAY_CHANGE_TRANSITION) {
            this.mHasCustomDisplayChangeTransition = false;
            this.mSeparatedFromCustomDisplayChange = false;
        }
        if (CoreRune.MW_FREEFORM_MINIMIZE_SHELL_TRANSITION) {
            this.mMinimizeAnimState = 0;
            this.mMinimizePoint.set(0.0f, 0.0f);
        }
    }

    public final void setAnimation(Animation animation) {
        if (animation == null) {
            Slog.w("MultiTaskingTransitionState", "setAnimation: failed, animation is null");
        } else {
            this.mAnimation = animation;
            this.mAnimationLoaded = true;
        }
    }

    public final String toString() {
        String str;
        if (this.mForceHidingTransit != 0) {
            str = ", mForceHidingTransit=" + MultiWindowManager.forceHidingTransitToString(this.mForceHidingTransit);
        } else {
            str = "";
        }
        StringBuilder sb = new StringBuilder("{Type=");
        sb.append(this.mTransitionType);
        sb.append(", mIsEnter=");
        sb.append(this.mIsEnter);
        sb.append(", mDisplayId=");
        sb.append(this.mDisplayId);
        sb.append(", mTaskId=");
        sb.append(this.mTaskId);
        sb.append(", mWindowingMode=");
        sb.append(this.mWindowingMode);
        sb.append(", mAnimationOptions=");
        sb.append(this.mAnimationOptions);
        sb.append(", mHasCustomDisplayChangeTransition=");
        sb.append(this.mHasCustomDisplayChangeTransition);
        sb.append(", mSeparatedFromCustomDisplayChange=");
        sb.append(this.mSeparatedFromCustomDisplayChange);
        sb.append(this.mIsFreeformMovingBehindSplitScreen ? ", mFreeformMovingBehindSplit=true" : "");
        sb.append(str);
        sb.append(this.mAnimation == AnimationLoader.NO_ANIMATION ? ", NO_AMIM" : "");
        sb.append('}');
        return sb.toString();
    }
}

package com.android.wm.shell.transition;

import android.app.ActivityManager;
import android.content.res.Configuration;
import android.graphics.PointF;
import android.graphics.Rect;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.PathInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.window.TransitionInfo;
import androidx.compose.runtime.OpaqueKey$$ExternalSyntheticOutline0;
import com.android.internal.policy.TransitionAnimation;
import com.android.wm.shell.common.DisplayController;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.util.InterpolatorUtils;

/* loaded from: classes3.dex */
public class MultiTaskingTransitionState {
    public Animation mAnimation;
    public boolean mAnimationLoaded;
    public TransitionInfo.Change mChange;
    public final DisplayController mDisplayController;
    public boolean mHasCustomDisplayChangeTransition;
    public boolean mIsEnter;
    public ActivityManager.RunningTaskInfo mOpeningAppsEdgeTaskInfo;
    public boolean mSeparatedFromCustomDisplayChange;
    public ActivityManager.RunningTaskInfo mTaskInfo;
    public final TransitionAnimation mTransitAnimation;
    public int mTransitionType = 0;
    public final Configuration mConfiguration = new Configuration();
    public int mTaskId = -1;
    public int mDisplayId = -1;
    public int mWindowingMode = 0;
    public int mForceHidingTransit = 0;
    public int mMinimizeAnimState = 0;
    public final PointF mMinimizePoint = new PointF();
    public float mFreeformStashScale = 1.0f;
    public boolean mIsPopOverAnimationNeeded = false;

    public MultiTaskingTransitionState(TransitionAnimation transitionAnimation, DisplayController displayController) {
        this.mTransitAnimation = transitionAnimation;
        this.mDisplayController = displayController;
    }

    public final Animation createMinimizeAnimation(boolean z, PointF pointF, Rect rect, float f, boolean z2) {
        ScaleAnimation scaleAnimation;
        float f2;
        float f3;
        float f4;
        int i;
        float f5;
        ScaleAnimation scaleAnimation2;
        float fWidth = rect.width() / 2.0f;
        float fHeight = rect.height() / 2.0f;
        float f6 = z ? 0.2f : f;
        float f7 = z ? f : 0.2f;
        int iCenterX = rect.centerX();
        int iCenterY = (int) (rect.centerY() * f);
        boolean z3 = CoreRune.MW_CAPTION_FREEFORM_STASH;
        float fWidth2 = pointF.x - (iCenterX + ((z3 && z2) ? rect.width() - (rect.width() * f) : 0.0f));
        float f8 = pointF.y - iCenterY;
        float f9 = z ? fWidth2 : 0.0f;
        float f10 = z ? 0.0f : fWidth2;
        float f11 = z ? f8 : 0.0f;
        if (z) {
            f8 = 0.0f;
        }
        float f12 = z ? 0.0f : 1.0f;
        float f13 = z ? 1.0f : 0.0f;
        if (!z3 || f <= 0.0f || f >= 1.0f) {
            float f14 = f13;
            float f15 = f6;
            float f16 = f12;
            float f17 = f7;
            float f18 = f7;
            f2 = f14;
            float f19 = f6;
            f3 = f16;
            f4 = f11;
            scaleAnimation = new ScaleAnimation(f19, f18, f15, f17, fWidth, fHeight);
        } else {
            if (z2) {
                f5 = f13;
                scaleAnimation2 = new ScaleAnimation(f6, f7, f6, f7, 1, 1.0f, 1, 0.0f);
            } else {
                f5 = f13;
                scaleAnimation2 = new ScaleAnimation(f6, f7, f6, f7);
            }
            f2 = f5;
            f3 = f12;
            scaleAnimation = scaleAnimation2;
            f4 = f11;
        }
        TranslateAnimation translateAnimation = new TranslateAnimation(f9, f10, f4, f8);
        AlphaAnimation alphaAnimation = new AlphaAnimation(f3, f2);
        PathInterpolator pathInterpolator = InterpolatorUtils.SINE_IN_OUT_80;
        translateAnimation.setInterpolator(pathInterpolator);
        alphaAnimation.setInterpolator(InterpolatorUtils.SINE_IN_OUT_33);
        scaleAnimation.setInterpolator(pathInterpolator);
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(alphaAnimation);
        animationSet.setFillAfter(true);
        animationSet.setFillEnabled(true);
        animationSet.setDuration((z || !((i = this.mForceHidingTransit) == 2 || i == 4)) ? 250L : 0L);
        if (z) {
            animationSet.setZAdjustment(1);
        }
        return animationSet;
    }

    public final Rect getBounds() {
        return new Rect(this.mChange.getEndAbsBounds());
    }

    public final boolean isClosingTransitionType() {
        int i = this.mTransitionType;
        return i == 2 || i == 4;
    }

    public final boolean isOpeningTransitionType() {
        int i = this.mTransitionType;
        return i == 1 || i == 3;
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
        if (CoreRune.MW_SHELL_DISPLAY_CHANGE_TRANSITION) {
            this.mHasCustomDisplayChangeTransition = false;
            this.mSeparatedFromCustomDisplayChange = false;
        }
        if (CoreRune.MW_FREEFORM_MINIMIZE_SHELL_TRANSITION) {
            this.mMinimizeAnimState = 0;
            this.mMinimizePoint.set(0.0f, 0.0f);
        }
        if (CoreRune.MW_SPLIT_SHELL_TRANSITION) {
            this.mOpeningAppsEdgeTaskInfo = null;
        }
    }

    public final void setAnimation(Animation animation) {
        this.mAnimation = animation;
        this.mAnimationLoaded = true;
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
        sb.append(str);
        return OpaqueKey$$ExternalSyntheticOutline0.m(sb, this.mAnimation == AnimationLoader.NO_ANIMATION ? ", NO_AMIM" : "", '}');
    }
}

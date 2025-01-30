package com.android.systemui.statusbar.phone.fragment;

import android.content.res.Resources;
import androidx.core.animation.Animator;
import androidx.core.animation.AnimatorSet;
import androidx.core.animation.ValueAnimator;
import com.android.systemui.R;
import com.android.systemui.statusbar.events.SystemStatusAnimationCallback;
import com.android.systemui.statusbar.events.SystemStatusAnimationSchedulerKt;
import com.android.systemui.util.animation.AnimationUtil;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class StatusBarSystemEventDefaultAnimator implements SystemStatusAnimationCallback {
    public boolean isAnimationRunning;
    public final Function1 onAlphaChanged;
    public final Function1 onTranslationXChanged;
    public final int translationXIn;
    public final int translationXOut;

    public StatusBarSystemEventDefaultAnimator(Resources resources, Function1 function1, Function1 function12) {
        this(resources, function1, function12, false, 8, null);
    }

    @Override // com.android.systemui.statusbar.events.SystemStatusAnimationCallback
    public final Animator onSystemEventAnimationBegin(boolean z) {
        this.isAnimationRunning = true;
        final ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        AnimationUtil.Companion.getClass();
        ofFloat.setDuration(AnimationUtil.Companion.getFrames(23));
        ofFloat.setInterpolator(SystemStatusAnimationSchedulerKt.STATUS_BAR_X_MOVE_OUT);
        ofFloat.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.fragment.StatusBarSystemEventDefaultAnimator$onSystemEventAnimationBegin$moveOut$1$1
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                StatusBarSystemEventDefaultAnimator.this.onTranslationXChanged.invoke(Float.valueOf(-(((Float) ofFloat.getAnimatedValue()).floatValue() * r2.translationXIn)));
            }
        });
        final ValueAnimator ofFloat2 = ValueAnimator.ofFloat(1.0f, 0.0f);
        ofFloat2.setDuration(AnimationUtil.Companion.getFrames(8));
        ofFloat2.setInterpolator(null);
        ofFloat2.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.fragment.StatusBarSystemEventDefaultAnimator$onSystemEventAnimationBegin$alphaOut$1$1
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                StatusBarSystemEventDefaultAnimator.this.onAlphaChanged.invoke((Float) ofFloat2.getAnimatedValue());
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ofFloat, ofFloat2);
        return animatorSet;
    }

    @Override // com.android.systemui.statusbar.events.SystemStatusAnimationCallback
    public final Animator onSystemEventAnimationFinish(boolean z, boolean z2) {
        this.onTranslationXChanged.invoke(Float.valueOf(this.translationXOut));
        final ValueAnimator ofFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
        AnimationUtil.Companion.getClass();
        ofFloat.setDuration(AnimationUtil.Companion.getFrames(23));
        ofFloat.setStartDelay(AnimationUtil.Companion.getFrames(7));
        ofFloat.setInterpolator(SystemStatusAnimationSchedulerKt.STATUS_BAR_X_MOVE_IN);
        ofFloat.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.fragment.StatusBarSystemEventDefaultAnimator$onSystemEventAnimationFinish$moveIn$1$1
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                StatusBarSystemEventDefaultAnimator.this.onTranslationXChanged.invoke(Float.valueOf(((Float) ofFloat.getAnimatedValue()).floatValue() * r2.translationXOut));
            }
        });
        final ValueAnimator ofFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat2.setDuration(AnimationUtil.Companion.getFrames(5));
        ofFloat2.setStartDelay(AnimationUtil.Companion.getFrames(11));
        ofFloat2.setInterpolator(null);
        ofFloat2.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.fragment.StatusBarSystemEventDefaultAnimator$onSystemEventAnimationFinish$alphaIn$1$1
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                StatusBarSystemEventDefaultAnimator.this.onAlphaChanged.invoke((Float) ofFloat2.getAnimatedValue());
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        if (z2) {
            animatorSet.playTogether(ofFloat);
        } else {
            animatorSet.playTogether(ofFloat, ofFloat2);
        }
        animatorSet.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.statusbar.phone.fragment.StatusBarSystemEventDefaultAnimator$onSystemEventAnimationFinish$$inlined$doOnEnd$1
            @Override // androidx.core.animation.Animator.AnimatorListener
            public final void onAnimationEnd$1(Animator animator) {
                StatusBarSystemEventDefaultAnimator.this.isAnimationRunning = false;
            }

            @Override // androidx.core.animation.Animator.AnimatorListener
            public final void onAnimationCancel() {
            }

            @Override // androidx.core.animation.Animator.AnimatorListener
            public final void onAnimationRepeat() {
            }

            @Override // androidx.core.animation.Animator.AnimatorListener
            public final void onAnimationStart$1() {
            }
        });
        animatorSet.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.statusbar.phone.fragment.StatusBarSystemEventDefaultAnimator$onSystemEventAnimationFinish$$inlined$doOnCancel$1
            @Override // androidx.core.animation.Animator.AnimatorListener
            public final void onAnimationCancel() {
                StatusBarSystemEventDefaultAnimator.this.isAnimationRunning = false;
            }

            @Override // androidx.core.animation.Animator.AnimatorListener
            public final void onAnimationEnd$1(Animator animator) {
            }

            @Override // androidx.core.animation.Animator.AnimatorListener
            public final void onAnimationRepeat() {
            }

            @Override // androidx.core.animation.Animator.AnimatorListener
            public final void onAnimationStart$1() {
            }
        });
        return animatorSet;
    }

    public StatusBarSystemEventDefaultAnimator(Resources resources, Function1 function1, Function1 function12, boolean z) {
        this.onAlphaChanged = function1;
        this.onTranslationXChanged = function12;
        this.isAnimationRunning = z;
        this.translationXIn = resources.getDimensionPixelSize(R.dimen.ongoing_appops_chip_animation_in_status_bar_translation_x);
        this.translationXOut = resources.getDimensionPixelSize(R.dimen.ongoing_appops_chip_animation_out_status_bar_translation_x);
    }

    public /* synthetic */ StatusBarSystemEventDefaultAnimator(Resources resources, Function1 function1, Function1 function12, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(resources, function1, function12, (i & 8) != 0 ? false : z);
    }
}

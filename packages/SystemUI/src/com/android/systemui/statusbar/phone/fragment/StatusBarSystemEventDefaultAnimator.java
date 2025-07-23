package com.android.systemui.statusbar.phone.fragment;

import android.content.res.Resources;
import androidx.core.animation.Animator;
import androidx.core.animation.AnimatorSet;
import androidx.core.animation.ValueAnimator;
import com.android.systemui.R;
import com.android.systemui.statusbar.events.SpringAnimatorSet;
import com.android.systemui.statusbar.events.SystemStatusAnimationCallback;
import com.android.systemui.statusbar.events.SystemStatusAnimationSchedulerKt;
import com.android.systemui.util.animation.AnimationUtil;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class StatusBarSystemEventDefaultAnimator implements SystemStatusAnimationCallback {
    public static final Companion Companion = new Companion(null);
    public boolean isAnimationRunning;
    public final Function1 onAlphaChanged;
    public final Function1 onTranslationXChanged;
    public final int translationXIn;
    public final int translationXOut;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public StatusBarSystemEventDefaultAnimator(Resources resources, Function1 function1, Function1 function12) {
        this(resources, function1, function12, false, 8, null);
    }

    @Override // com.android.systemui.statusbar.events.SystemStatusAnimationCallback
    public final SpringAnimatorSet onSystemEventAnimationBegin(boolean z, boolean z2) {
        this.isAnimationRunning = true;
        Companion.getClass();
        final int i = this.translationXIn;
        final Function1 function1 = this.onTranslationXChanged;
        final Function1 function12 = this.onAlphaChanged;
        final ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        AnimationUtil.Companion companion = AnimationUtil.Companion;
        ofFloat.setDuration(companion.getFrames(23));
        ofFloat.setInterpolator(SystemStatusAnimationSchedulerKt.STATUS_BAR_X_MOVE_OUT);
        ofFloat.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.fragment.StatusBarSystemEventDefaultAnimator$Companion$getDefaultStatusBarAnimationForChipEnter$moveOut$1$1
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                Function1.this.mo779invoke(Float.valueOf(-(((Float) ofFloat.getAnimatedValue()).floatValue() * i)));
            }
        });
        final ValueAnimator ofFloat2 = ValueAnimator.ofFloat(1.0f, 0.0f);
        ofFloat2.setDuration(companion.getFrames(8));
        ofFloat2.setInterpolator(null);
        ofFloat2.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.fragment.StatusBarSystemEventDefaultAnimator$Companion$getDefaultStatusBarAnimationForChipEnter$alphaOut$1$1
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                Function1.this.mo779invoke((Float) ofFloat2.getAnimatedValue());
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        if (z2) {
            final ValueAnimator ofFloat3 = ValueAnimator.ofFloat(1.0f, 0.0f);
            ofFloat3.setDuration(100L);
            ofFloat3.setInterpolator(null);
            ofFloat3.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.fragment.StatusBarSystemEventDefaultAnimator$Companion$getDefaultStatusBarAnimationForChipEnter$batteryAlphaOut$1$1
                @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                public final void onAnimationUpdate(Animator animator) {
                    Function1.this.mo779invoke((Float) ofFloat3.getAnimatedValue());
                }
            });
            animatorSet.playTogether(ofFloat3);
        } else {
            animatorSet.playTogether(ofFloat, ofFloat2);
        }
        SpringAnimatorSet springAnimatorSet = new SpringAnimatorSet();
        springAnimatorSet.playTogether(animatorSet);
        return springAnimatorSet;
    }

    @Override // com.android.systemui.statusbar.events.SystemStatusAnimationCallback
    public final SpringAnimatorSet onSystemEventAnimationFinish(boolean z, boolean z2, boolean z3) {
        final int i = this.translationXOut;
        Float valueOf = Float.valueOf(i);
        final Function1 function1 = this.onTranslationXChanged;
        function1.mo779invoke(valueOf);
        Companion.getClass();
        final Function1 function12 = this.onAlphaChanged;
        final ValueAnimator ofFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
        AnimationUtil.Companion companion = AnimationUtil.Companion;
        ofFloat.setDuration(companion.getFrames(23));
        ofFloat.setStartDelay(companion.getFrames(7));
        ofFloat.setInterpolator(SystemStatusAnimationSchedulerKt.STATUS_BAR_X_MOVE_IN);
        ofFloat.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.fragment.StatusBarSystemEventDefaultAnimator$Companion$getDefaultStatusBarAnimationForChipExit$moveIn$1$1
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                Function1.this.mo779invoke(Float.valueOf(((Float) ofFloat.getAnimatedValue()).floatValue() * i));
            }
        });
        final ValueAnimator ofFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat2.setDuration(companion.getFrames(5));
        ofFloat2.setStartDelay(companion.getFrames(11));
        ofFloat2.setInterpolator(null);
        ofFloat2.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.fragment.StatusBarSystemEventDefaultAnimator$Companion$getDefaultStatusBarAnimationForChipExit$alphaIn$1$1
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                Function1.this.mo779invoke((Float) ofFloat2.getAnimatedValue());
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        if (z2) {
            animatorSet.playTogether(ofFloat);
        } else if (z3) {
            function1.mo779invoke(Float.valueOf(0.0f));
            final ValueAnimator ofFloat3 = ValueAnimator.ofFloat(0.0f, 1.0f);
            ofFloat3.setDuration(300L);
            ofFloat3.setStartDelay(companion.getFrames(13));
            ofFloat3.setInterpolator(null);
            ofFloat3.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.fragment.StatusBarSystemEventDefaultAnimator$Companion$getDefaultStatusBarAnimationForChipExit$batteryAlphaIn$1$1
                @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                public final void onAnimationUpdate(Animator animator) {
                    Function1.this.mo779invoke((Float) ofFloat3.getAnimatedValue());
                }
            });
            animatorSet.playTogether(ofFloat3);
        } else {
            animatorSet.playTogether(ofFloat, ofFloat2);
        }
        animatorSet.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.statusbar.phone.fragment.StatusBarSystemEventDefaultAnimator$onSystemEventAnimationFinish$$inlined$doOnEnd$1
            @Override // androidx.core.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                StatusBarSystemEventDefaultAnimator.this.isAnimationRunning = false;
            }

            @Override // androidx.core.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
            }

            @Override // androidx.core.animation.Animator.AnimatorListener
            public final void onAnimationRepeat(Animator animator) {
            }

            @Override // androidx.core.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
            }
        });
        animatorSet.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.statusbar.phone.fragment.StatusBarSystemEventDefaultAnimator$onSystemEventAnimationFinish$$inlined$doOnCancel$1
            @Override // androidx.core.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                StatusBarSystemEventDefaultAnimator.this.isAnimationRunning = false;
            }

            @Override // androidx.core.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
            }

            @Override // androidx.core.animation.Animator.AnimatorListener
            public final void onAnimationRepeat(Animator animator) {
            }

            @Override // androidx.core.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
            }
        });
        SpringAnimatorSet springAnimatorSet = new SpringAnimatorSet();
        springAnimatorSet.playTogether(animatorSet);
        return springAnimatorSet;
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

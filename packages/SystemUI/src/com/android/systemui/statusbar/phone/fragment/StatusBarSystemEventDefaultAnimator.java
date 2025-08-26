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

/* loaded from: classes3.dex */
public class StatusBarSystemEventDefaultAnimator implements SystemStatusAnimationCallback {
    public static final Companion Companion = new Companion(null);
    public boolean isAnimationRunning;
    public final Function1 onAlphaChanged;
    public final Function1 onTranslationXChanged;
    public final int translationXIn;
    public final int translationXOut;

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
        final ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        AnimationUtil.Companion companion = AnimationUtil.Companion;
        valueAnimatorOfFloat.setDuration(companion.getFrames(23));
        valueAnimatorOfFloat.setInterpolator(SystemStatusAnimationSchedulerKt.STATUS_BAR_X_MOVE_OUT);
        valueAnimatorOfFloat.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.fragment.StatusBarSystemEventDefaultAnimator$Companion$getDefaultStatusBarAnimationForChipEnter$moveOut$1$1
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                function1.mo781invoke(Float.valueOf(-(((Float) valueAnimatorOfFloat.getAnimatedValue()).floatValue() * i)));
            }
        });
        final ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(1.0f, 0.0f);
        valueAnimatorOfFloat2.setDuration(companion.getFrames(8));
        valueAnimatorOfFloat2.setInterpolator(null);
        valueAnimatorOfFloat2.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.fragment.StatusBarSystemEventDefaultAnimator$Companion$getDefaultStatusBarAnimationForChipEnter$alphaOut$1$1
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                function12.mo781invoke((Float) valueAnimatorOfFloat2.getAnimatedValue());
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        if (z2) {
            final ValueAnimator valueAnimatorOfFloat3 = ValueAnimator.ofFloat(1.0f, 0.0f);
            valueAnimatorOfFloat3.setDuration(100L);
            valueAnimatorOfFloat3.setInterpolator(null);
            valueAnimatorOfFloat3.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.fragment.StatusBarSystemEventDefaultAnimator$Companion$getDefaultStatusBarAnimationForChipEnter$batteryAlphaOut$1$1
                @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                public final void onAnimationUpdate(Animator animator) {
                    function12.mo781invoke((Float) valueAnimatorOfFloat3.getAnimatedValue());
                }
            });
            animatorSet.playTogether(valueAnimatorOfFloat3);
        } else {
            animatorSet.playTogether(valueAnimatorOfFloat, valueAnimatorOfFloat2);
        }
        SpringAnimatorSet springAnimatorSet = new SpringAnimatorSet();
        springAnimatorSet.playTogether(animatorSet);
        return springAnimatorSet;
    }

    @Override // com.android.systemui.statusbar.events.SystemStatusAnimationCallback
    public final SpringAnimatorSet onSystemEventAnimationFinish(boolean z, boolean z2, boolean z3) {
        final int i = this.translationXOut;
        Float fValueOf = Float.valueOf(i);
        final Function1 function1 = this.onTranslationXChanged;
        function1.mo781invoke(fValueOf);
        Companion.getClass();
        final Function1 function12 = this.onAlphaChanged;
        final ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
        AnimationUtil.Companion companion = AnimationUtil.Companion;
        valueAnimatorOfFloat.setDuration(companion.getFrames(23));
        valueAnimatorOfFloat.setStartDelay(companion.getFrames(7));
        valueAnimatorOfFloat.setInterpolator(SystemStatusAnimationSchedulerKt.STATUS_BAR_X_MOVE_IN);
        valueAnimatorOfFloat.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.fragment.StatusBarSystemEventDefaultAnimator$Companion$getDefaultStatusBarAnimationForChipExit$moveIn$1$1
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                function1.mo781invoke(Float.valueOf(((Float) valueAnimatorOfFloat.getAnimatedValue()).floatValue() * i));
            }
        });
        final ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat2.setDuration(companion.getFrames(5));
        valueAnimatorOfFloat2.setStartDelay(companion.getFrames(11));
        valueAnimatorOfFloat2.setInterpolator(null);
        valueAnimatorOfFloat2.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.fragment.StatusBarSystemEventDefaultAnimator$Companion$getDefaultStatusBarAnimationForChipExit$alphaIn$1$1
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                function12.mo781invoke((Float) valueAnimatorOfFloat2.getAnimatedValue());
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        if (z2) {
            animatorSet.playTogether(valueAnimatorOfFloat);
        } else if (z3) {
            function1.mo781invoke(Float.valueOf(0.0f));
            final ValueAnimator valueAnimatorOfFloat3 = ValueAnimator.ofFloat(0.0f, 1.0f);
            valueAnimatorOfFloat3.setDuration(200L);
            valueAnimatorOfFloat3.setStartDelay(companion.getFrames(5));
            valueAnimatorOfFloat3.setInterpolator(null);
            valueAnimatorOfFloat3.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.fragment.StatusBarSystemEventDefaultAnimator$Companion$getDefaultStatusBarAnimationForChipExit$batteryAlphaIn$1$1
                @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                public final void onAnimationUpdate(Animator animator) {
                    function12.mo781invoke((Float) valueAnimatorOfFloat3.getAnimatedValue());
                }
            });
            animatorSet.playTogether(valueAnimatorOfFloat3);
        } else {
            animatorSet.playTogether(valueAnimatorOfFloat, valueAnimatorOfFloat2);
        }
        animatorSet.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.statusbar.phone.fragment.StatusBarSystemEventDefaultAnimator$onSystemEventAnimationFinish$$inlined$doOnEnd$1
            @Override // androidx.core.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                this.this$0.isAnimationRunning = false;
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
                this.this$0.isAnimationRunning = false;
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

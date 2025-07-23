package com.android.wm.shell.shared.animation;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.PointFEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Choreographer;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.wm.shell.shared.animation.WindowAnimator;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class MinimizeAnimator {
    public static final WindowAnimator.BoundsAnimationParams minimizeBoundsAnimationDef;

    static {
        new MinimizeAnimator();
        minimizeBoundsAnimationDef = new WindowAnimator.BoundsAnimationParams(200L, 0.0f, 12.0f, 0.0f, 0.97f, Interpolators.STANDARD_ACCELERATE, 10, null);
    }

    private MinimizeAnimator() {
    }

    public static final Animator create(final Context context, final TransitionInfo.Change change, final SurfaceControl.Transaction transaction, final Function1 function1, final InteractionJankMonitor interactionJankMonitor, final Handler handler) {
        WindowAnimator windowAnimator = WindowAnimator.INSTANCE;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        windowAnimator.getClass();
        Rect endAbsBounds = change.getEndAbsBounds();
        final WindowAnimator.BoundsAnimationParams boundsAnimationParams = minimizeBoundsAnimationDef;
        PointF position = WindowAnimator.getPosition(displayMetrics, endAbsBounds, boundsAnimationParams.startScale, boundsAnimationParams.startOffsetYDp);
        final SurfaceControl leash = change.getLeash();
        ValueAnimator ofObject = ValueAnimator.ofObject(new PointFEvaluator(), position, WindowAnimator.getPosition(displayMetrics, change.getEndAbsBounds(), boundsAnimationParams.endScale, boundsAnimationParams.endOffsetYDp));
        ofObject.setDuration(boundsAnimationParams.durationMs);
        ofObject.setInterpolator(boundsAnimationParams.interpolator);
        ofObject.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.shared.animation.WindowAnimator$createBoundsAnimator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                PointF pointF = (PointF) valueAnimator.getAnimatedValue();
                WindowAnimator windowAnimator2 = WindowAnimator.INSTANCE;
                WindowAnimator.BoundsAnimationParams boundsAnimationParams2 = WindowAnimator.BoundsAnimationParams.this;
                float f = boundsAnimationParams2.startScale;
                float f2 = boundsAnimationParams2.endScale;
                float animatedFraction = valueAnimator.getAnimatedFraction();
                windowAnimator2.getClass();
                if (0.0f > animatedFraction || animatedFraction > 1.0f) {
                    throw new IllegalArgumentException("Failed requirement.");
                }
                float m$1 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(f2, f, animatedFraction, f);
                transaction.setPosition(leash, pointF.x, pointF.y).setScale(leash, m$1, m$1).setFrameTimeline(Choreographer.getInstance().getVsyncId()).apply();
            }
        });
        ValueAnimator ofFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
        ofFloat.setDuration(100L);
        ofFloat.setInterpolator(Interpolators.LINEAR);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.shared.animation.MinimizeAnimator$create$alphaAnimator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                transaction.setAlpha(change.getLeash(), ((Float) valueAnimator.getAnimatedValue()).floatValue()).setFrameTimeline(Choreographer.getInstance().getVsyncId()).apply();
            }
        });
        Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() { // from class: com.android.wm.shell.shared.animation.MinimizeAnimator$create$listener$1
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                interactionJankMonitor.cancel(109);
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                interactionJankMonitor.end(109);
                function1.mo779invoke(animator);
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                interactionJankMonitor.begin(change.getLeash(), context, handler, 109);
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationRepeat(Animator animator) {
            }
        };
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ofObject, ofFloat);
        animatorSet.addListener(animatorListener);
        return animatorSet;
    }
}

package com.android.keyguard;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.MathUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import com.android.app.animation.Interpolators;
import com.android.systemui.LsRune;
import com.android.systemui.util.DeviceType;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Ref$IntRef;
import kotlin.jvm.internal.Ref$ObjectRef;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardSecurityViewTransition extends Transition {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public static void captureValues(TransitionValues transitionValues) {
        Rect rect = new Rect();
        rect.left = transitionValues.view.getLeft();
        rect.top = transitionValues.view.getTop();
        rect.right = transitionValues.view.getRight();
        rect.bottom = transitionValues.view.getBottom();
        transitionValues.values.put("securityViewLocation:bounds", rect);
    }

    @Override // android.transition.Transition
    public final void captureEndValues(TransitionValues transitionValues) {
        if (transitionValues != null) {
            captureValues(transitionValues);
        }
    }

    @Override // android.transition.Transition
    public final void captureStartValues(TransitionValues transitionValues) {
        if (transitionValues != null) {
            captureValues(transitionValues);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v1, types: [T, android.animation.ValueAnimator] */
    @Override // android.transition.Transition
    public final Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        if (viewGroup == null || transitionValues == null || transitionValues2 == null) {
            return null;
        }
        final Interpolator loadInterpolator = AnimationUtils.loadInterpolator(viewGroup.getContext(), R.interpolator.fast_out_extra_slow_in);
        final Interpolator interpolator = Interpolators.FAST_OUT_LINEAR_IN;
        final Interpolator interpolator2 = Interpolators.LINEAR_OUT_SLOW_IN;
        final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        ?? ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        ref$ObjectRef.element = ofFloat;
        boolean z = LsRune.SECURITY_ARROW_VIEW;
        ofFloat.setDuration(z ? 360L : 500L);
        ((ValueAnimator) ref$ObjectRef.element).setInterpolator(Interpolators.LINEAR);
        final Rect rect = (Rect) transitionValues.values.get("securityViewLocation:bounds");
        final Rect rect2 = (Rect) transitionValues2.values.get("securityViewLocation:bounds");
        final View view = transitionValues.view;
        final Ref$IntRef ref$IntRef = new Ref$IntRef();
        ref$IntRef.element = (int) viewGroup.getResources().getDimension(com.android.systemui.R.dimen.security_shift_animation_translation);
        if (z && DeviceType.isTablet()) {
            ref$IntRef.element /= viewGroup.getResources().getConfiguration().orientation == 1 ? 3 : 2;
        }
        final boolean z2 = view.hasOverlappingRendering() && view.getLayerType() != 2;
        if (z2) {
            view.setLayerType(2, null);
        }
        final float alpha = view.getAlpha();
        ((ValueAnimator) ref$ObjectRef.element).addListener(new AnimatorListenerAdapter() { // from class: com.android.keyguard.KeyguardSecurityViewTransition$createAnimator$1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                ref$ObjectRef.element = null;
                if (z2) {
                    view.setLayerType(0, null);
                }
            }
        });
        ((ValueAnimator) ref$ObjectRef.element).addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.keyguard.KeyguardSecurityViewTransition$createAnimator$2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                boolean z3 = valueAnimator.getAnimatedFraction() < 0.2f;
                float interpolation = loadInterpolator.getInterpolation(valueAnimator.getAnimatedFraction());
                int i = ref$IntRef.element;
                int i2 = (int) (interpolation * i);
                int i3 = i - i2;
                if (rect2.left < rect.left) {
                    i2 = -i2;
                    i3 = -i3;
                }
                if (z3) {
                    float interpolation2 = interpolator.getInterpolation(MathUtils.constrainedMap(1.0f, 0.0f, 0.0f, 0.2f, valueAnimator.getAnimatedFraction()));
                    if (!LsRune.SECURITY_ARROW_VIEW) {
                        view.setAlpha(interpolation2 * alpha);
                    }
                    View view2 = view;
                    if (view2 instanceof KeyguardSecurityViewFlipper) {
                        Rect rect3 = rect;
                        view2.setLeftTopRightBottom(rect3.left + i2, rect3.top, rect3.right + i2, rect3.bottom);
                        return;
                    } else {
                        Rect rect4 = rect;
                        view2.setLeftTopRightBottom(rect4.left, rect4.top, rect4.right, rect4.bottom);
                        return;
                    }
                }
                float interpolation3 = interpolator2.getInterpolation(MathUtils.constrainedMap(0.0f, 1.0f, 0.2f, 1.0f, valueAnimator.getAnimatedFraction()));
                if (!LsRune.SECURITY_ARROW_VIEW) {
                    view.setAlpha(interpolation3);
                }
                View view3 = view;
                if (view3 instanceof KeyguardSecurityViewFlipper) {
                    Rect rect5 = rect2;
                    view3.setLeftTopRightBottom(rect5.left - i3, rect5.top, rect5.right - i3, rect5.bottom);
                } else {
                    Rect rect6 = rect2;
                    view3.setLeftTopRightBottom(rect6.left, rect6.top, rect6.right, rect6.bottom);
                }
            }
        });
        ((ValueAnimator) ref$ObjectRef.element).start();
        return (Animator) ref$ObjectRef.element;
    }

    @Override // android.transition.Transition
    public final String[] getTransitionProperties() {
        return new String[]{"securityViewLocation:bounds"};
    }
}

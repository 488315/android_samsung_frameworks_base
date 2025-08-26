package com.android.systemui.statusbar.phone.ongoingactivity.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.transition.Transition;
import android.transition.TransitionListenerAdapter;
import android.transition.TransitionValues;
import android.transition.Visibility;
import android.util.Log;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.PathInterpolator;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class VisibilityTransition extends Visibility {

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public static final void access$endAction(VisibilityTransition visibilityTransition, View view) {
        visibilityTransition.getClass();
        Log.d("VisibilityTransition", "endAction()");
        view.setScaleX(1.0f);
        view.setScaleY(1.0f);
        view.setAlpha(1.0f);
    }

    public static float getStartScaleValue(TransitionValues transitionValues, String str, float f) {
        Map map;
        Object obj = (transitionValues == null || (map = transitionValues.values) == null) ? null : map.get(str);
        Float f2 = obj instanceof Float ? (Float) obj : null;
        return f2 != null ? f2.floatValue() : f;
    }

    @Override // android.transition.Visibility, android.transition.Transition
    public final void captureStartValues(TransitionValues transitionValues) {
        super.captureStartValues(transitionValues);
        transitionValues.values.put("com.samsung.transition:scale:scaleX", Float.valueOf(transitionValues.view.getScaleX()));
        transitionValues.values.put("com.samsung.transition:scale:scaleY", Float.valueOf(transitionValues.view.getScaleY()));
        transitionValues.values.put("com.samsung.transition:scale:alpha", Float.valueOf(transitionValues.view.getTransitionAlpha()));
    }

    public final Animator createScaleAnimation(boolean z, final View view, float f, float f2, float f3, float f4, float f5, float f6) {
        PathInterpolator pathInterpolator = new PathInterpolator(0.33f, 1.0f, 0.68f, 1.0f);
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.SCALE_X, f, f3);
        objectAnimatorOfFloat.setInterpolator(pathInterpolator);
        objectAnimatorOfFloat.setDuration(z ? 500L : 200L);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.SCALE_Y, f2, f4);
        objectAnimatorOfFloat2.setInterpolator(pathInterpolator);
        objectAnimatorOfFloat2.setDuration(z ? 500L : 200L);
        ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.ALPHA, f5, f6);
        objectAnimatorOfFloat3.setInterpolator(pathInterpolator);
        objectAnimatorOfFloat3.setDuration(z ? 500L : 200L);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimatorOfFloat3, objectAnimatorOfFloat, objectAnimatorOfFloat2);
        if (view != null) {
            animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.animation.VisibilityTransition$getAnimatorListener$1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    Log.d("VisibilityTransition", "onAnimationEnd");
                    VisibilityTransition.access$endAction(this.this$0, view);
                }
            });
            addListener(new TransitionListenerAdapter() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.animation.VisibilityTransition$getTransitionListener$1
                @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
                public final void onTransitionCancel(Transition transition) {
                    Log.d("VisibilityTransition", "onTransitionCancel");
                    VisibilityTransition.access$endAction(this.this$0, view);
                }

                @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
                public final void onTransitionEnd(Transition transition) {
                    Log.d("VisibilityTransition", "onTransitionEnd");
                    VisibilityTransition.access$endAction(this.this$0, view);
                }
            });
        }
        return animatorSet;
    }

    @Override // android.transition.Visibility
    public final Animator onAppear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        Log.d("VisibilityTransition", "onAppear");
        float startScaleValue = getStartScaleValue(transitionValues, "com.samsung.transition:scale:scaleX", 0.0f);
        float startScaleValue2 = getStartScaleValue(transitionValues, "com.samsung.transition:scale:scaleY", 0.0f);
        float startScaleValue3 = getStartScaleValue(transitionValues, "com.samsung.transition:scale:alpha", 0.0f);
        return createScaleAnimation(true, view, startScaleValue == 1.0f ? 0.0f : startScaleValue, startScaleValue2 == 1.0f ? 0.0f : startScaleValue2, 1.0f, 1.0f, startScaleValue3 == 1.0f ? 0.0f : startScaleValue3, 1.0f);
    }

    @Override // android.transition.Visibility
    public final Animator onDisappear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        return createScaleAnimation(false, view, getStartScaleValue(transitionValues, "com.samsung.transition:scale:scaleX", 1.0f), getStartScaleValue(transitionValues, "com.samsung.transition:scale:scaleY", 1.0f), 0.0f, 0.0f, getStartScaleValue(transitionValues, "com.samsung.transition:scale:alpha", 1.0f), 0.0f);
    }
}

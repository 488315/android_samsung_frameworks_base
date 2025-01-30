package com.android.systemui.statusbar.notification;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.util.ArrayMap;
import android.util.Property;
import android.view.View;
import android.view.animation.Interpolator;
import com.android.app.animation.Interpolators;
import com.android.systemui.statusbar.notification.stack.AnimationFilter;
import com.android.systemui.statusbar.notification.stack.AnimationProperties;
import com.android.systemui.statusbar.notification.stack.ViewState;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PropertyAnimator {
    public static void cancelAnimation(View view, AnimatableProperty animatableProperty) {
        ValueAnimator valueAnimator = (ValueAnimator) view.getTag(animatableProperty.getAnimatorTag());
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
    }

    public static void setProperty(View view, AnimatableProperty animatableProperty, float f, AnimationProperties animationProperties, boolean z) {
        int animatorTag = animatableProperty.getAnimatorTag();
        ViewState.C29791 c29791 = ViewState.NO_NEW_ANIMATIONS;
        if (((ValueAnimator) view.getTag(animatorTag)) == null && !z) {
            animatableProperty.getProperty().set(view, Float.valueOf(f));
            return;
        }
        if (!z) {
            animationProperties = null;
        }
        startAnimation(view, animatableProperty, f, animationProperties);
    }

    public static void startAnimation(final View view, AnimatableProperty animatableProperty, float f, AnimationProperties animationProperties) {
        final Property property = animatableProperty.getProperty();
        final int animationStartTag = animatableProperty.getAnimationStartTag();
        final int animationEndTag = animatableProperty.getAnimationEndTag();
        ViewState.C29791 c29791 = ViewState.NO_NEW_ANIMATIONS;
        Float f2 = (Float) view.getTag(animationStartTag);
        Float f3 = (Float) view.getTag(animationEndTag);
        if (f3 == null || f3.floatValue() != f) {
            final int animatorTag = animatableProperty.getAnimatorTag();
            ValueAnimator valueAnimator = (ValueAnimator) view.getTag(animatorTag);
            AnimationFilter animationFilter = animationProperties != null ? animationProperties.getAnimationFilter() : null;
            if (animationFilter == null || !animationFilter.shouldAnimateProperty(property)) {
                if (valueAnimator == null) {
                    property.set(view, Float.valueOf(f));
                    return;
                }
                PropertyValuesHolder[] values = valueAnimator.getValues();
                float floatValue = f2.floatValue() + (f - f3.floatValue());
                values[0].setFloatValues(floatValue, f);
                view.setTag(animationStartTag, Float.valueOf(floatValue));
                view.setTag(animationEndTag, Float.valueOf(f));
                valueAnimator.setCurrentPlayTime(valueAnimator.getCurrentPlayTime());
                return;
            }
            Float f4 = (Float) property.get(view);
            AnimatorListenerAdapter animationFinishListener = animationProperties.getAnimationFinishListener(property);
            if (f4.equals(Float.valueOf(f))) {
                if (valueAnimator != null) {
                    valueAnimator.cancel();
                }
                if (animationFinishListener != null) {
                    animationFinishListener.onAnimationEnd(null);
                    return;
                }
                return;
            }
            ValueAnimator ofFloat = ValueAnimator.ofFloat(f4.floatValue(), f);
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.notification.PropertyAnimator$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    property.set(view, (Float) valueAnimator2.getAnimatedValue());
                }
            });
            ArrayMap arrayMap = animationProperties.mInterpolatorMap;
            Interpolator interpolator = arrayMap != null ? (Interpolator) arrayMap.get(property) : null;
            if (interpolator == null) {
                interpolator = Interpolators.FAST_OUT_SLOW_IN;
            }
            ofFloat.setInterpolator(interpolator);
            ofFloat.setDuration(ViewState.cancelAnimatorAndGetNewDuration(animationProperties.duration, valueAnimator));
            if (animationProperties.delay > 0 && (valueAnimator == null || valueAnimator.getAnimatedFraction() == 0.0f)) {
                ofFloat.setStartDelay(animationProperties.delay);
            }
            if (animationFinishListener != null) {
                ofFloat.addListener(animationFinishListener);
            }
            ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.notification.PropertyAnimator.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    view.setTag(animatorTag, null);
                    view.setTag(animationStartTag, null);
                    view.setTag(animationEndTag, null);
                }
            });
            ViewState.startAnimator(ofFloat, animationFinishListener);
            view.setTag(animatorTag, ofFloat);
            view.setTag(animationStartTag, f4);
            view.setTag(animationEndTag, Float.valueOf(f));
        }
    }
}

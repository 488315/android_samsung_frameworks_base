package com.android.systemui.statusbar.notification.stack;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Property;
import android.view.View;
import android.view.animation.Interpolator;
import androidx.constraintlayout.core.ArrayLinkedVariables$$ExternalSyntheticOutline0;
import com.android.app.animation.Interpolators;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.AnimatableProperty;
import com.android.systemui.statusbar.notification.NotificationFadeAware;
import com.android.systemui.statusbar.notification.PropertyAnimator;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.policy.HeadsUpUtil;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class ViewState implements Dumpable {
    public boolean gone;
    public boolean hidden;
    public float mAlpha;
    public float mScaleX = 1.0f;
    public float mScaleY = 1.0f;
    public float mXTranslation;
    public float mYTranslation;
    public float mZTranslation;
    public static final C29791 NO_NEW_ANIMATIONS = new AnimationProperties() { // from class: com.android.systemui.statusbar.notification.stack.ViewState.1
        public final AnimationFilter mAnimationFilter = new AnimationFilter();

        @Override // com.android.systemui.statusbar.notification.stack.AnimationProperties
        public final AnimationFilter getAnimationFilter() {
            return this.mAnimationFilter;
        }
    };
    public static final int TAG_ANIMATOR_TRANSLATION_X = R.id.translation_x_animator_tag;
    public static final int TAG_ANIMATOR_TRANSLATION_Y = R.id.translation_y_animator_tag;
    public static final int TAG_ANIMATOR_TRANSLATION_Z = R.id.translation_z_animator_tag;
    public static final int TAG_ANIMATOR_ALPHA = R.id.alpha_animator_tag;
    public static final int TAG_END_TRANSLATION_X = R.id.translation_x_animator_end_value_tag;
    public static final int TAG_END_TRANSLATION_Y = R.id.translation_y_animator_end_value_tag;
    public static final int TAG_END_TRANSLATION_Z = R.id.translation_z_animator_end_value_tag;
    public static final int TAG_END_ALPHA = R.id.alpha_animator_end_value_tag;
    public static final int TAG_START_TRANSLATION_X = R.id.translation_x_animator_start_value_tag;
    public static final int TAG_START_TRANSLATION_Y = R.id.translation_y_animator_start_value_tag;
    public static final int TAG_START_TRANSLATION_Z = R.id.translation_z_animator_start_value_tag;
    public static final int TAG_START_ALPHA = R.id.alpha_animator_start_value_tag;
    public static final C29802 SCALE_X_PROPERTY = new AnimatableProperty() { // from class: com.android.systemui.statusbar.notification.stack.ViewState.2
        @Override // com.android.systemui.statusbar.notification.AnimatableProperty
        public final int getAnimationEndTag() {
            return R.id.scale_x_animator_end_value_tag;
        }

        @Override // com.android.systemui.statusbar.notification.AnimatableProperty
        public final int getAnimationStartTag() {
            return R.id.scale_x_animator_start_value_tag;
        }

        @Override // com.android.systemui.statusbar.notification.AnimatableProperty
        public final int getAnimatorTag() {
            return R.id.scale_x_animator_tag;
        }

        @Override // com.android.systemui.statusbar.notification.AnimatableProperty
        public final Property getProperty() {
            return View.SCALE_X;
        }
    };
    public static final C29813 SCALE_Y_PROPERTY = new AnimatableProperty() { // from class: com.android.systemui.statusbar.notification.stack.ViewState.3
        @Override // com.android.systemui.statusbar.notification.AnimatableProperty
        public final int getAnimationEndTag() {
            return R.id.scale_y_animator_end_value_tag;
        }

        @Override // com.android.systemui.statusbar.notification.AnimatableProperty
        public final int getAnimationStartTag() {
            return R.id.scale_y_animator_start_value_tag;
        }

        @Override // com.android.systemui.statusbar.notification.AnimatableProperty
        public final int getAnimatorTag() {
            return R.id.scale_y_animator_tag;
        }

        @Override // com.android.systemui.statusbar.notification.AnimatableProperty
        public final Property getProperty() {
            return View.SCALE_Y;
        }
    };

    public static void abortAnimation(View view, int i) {
        Animator animator = (Animator) view.getTag(i);
        if (animator != null) {
            animator.cancel();
        }
    }

    public static long cancelAnimatorAndGetNewDuration(long j, ValueAnimator valueAnimator) {
        if (valueAnimator == null) {
            return j;
        }
        long max = Math.max(valueAnimator.getDuration() - valueAnimator.getCurrentPlayTime(), j);
        valueAnimator.cancel();
        return max;
    }

    public static float getFinalTranslationY(ExpandableView expandableView) {
        if (expandableView == null) {
            return 0.0f;
        }
        return ((ValueAnimator) expandableView.getTag(TAG_ANIMATOR_TRANSLATION_Y)) == null ? expandableView.getTranslationY() : ((Float) expandableView.getTag(TAG_END_TRANSLATION_Y)).floatValue();
    }

    public static boolean isAnimating(View view, AnimatableProperty animatableProperty) {
        return view.getTag(animatableProperty.getAnimatorTag()) != null;
    }

    public static boolean isValidFloat(float f, String str) {
        if (!Float.isNaN(f)) {
            return true;
        }
        Log.wtf("StackViewState", "Cannot set property " + str + " to NaN");
        return false;
    }

    public static void startAnimator(Animator animator, AnimatorListenerAdapter animatorListenerAdapter) {
        if (animatorListenerAdapter != null) {
            animatorListenerAdapter.onAnimationStart(animator);
        }
        animator.start();
    }

    public void animateTo(View view, AnimationProperties animationProperties) {
        boolean z = view.getVisibility() == 0;
        float f = this.mAlpha;
        if (!z && ((f != 0.0f || view.getAlpha() != 0.0f) && !this.gone && !this.hidden)) {
            view.setVisibility(0);
        }
        boolean z2 = this.mAlpha != view.getAlpha();
        if (view instanceof ExpandableView) {
            z2 &= !((ExpandableView) view).mWillBeGone;
        }
        if (view.getTranslationX() != this.mXTranslation) {
            startXTranslationAnimation(view, animationProperties);
        } else {
            abortAnimation(view, TAG_ANIMATOR_TRANSLATION_X);
        }
        if (view.getTranslationY() != this.mYTranslation) {
            startYTranslationAnimation(view, animationProperties);
        } else {
            abortAnimation(view, TAG_ANIMATOR_TRANSLATION_Y);
        }
        if (view.getTranslationZ() != this.mZTranslation) {
            startZTranslationAnimation(view, animationProperties);
        } else {
            abortAnimation(view, TAG_ANIMATOR_TRANSLATION_Z);
        }
        float scaleX = view.getScaleX();
        float f2 = this.mScaleX;
        C29802 c29802 = SCALE_X_PROPERTY;
        if (scaleX != f2) {
            PropertyAnimator.startAnimation(view, c29802, f2, animationProperties);
        } else {
            c29802.getClass();
            abortAnimation(view, R.id.scale_x_animator_tag);
        }
        float scaleY = view.getScaleY();
        float f3 = this.mScaleY;
        C29813 c29813 = SCALE_Y_PROPERTY;
        if (scaleY != f3) {
            PropertyAnimator.startAnimation(view, c29813, f3, animationProperties);
        } else {
            c29813.getClass();
            abortAnimation(view, R.id.scale_y_animator_tag);
        }
        if (z2) {
            startAlphaAnimation(view, animationProperties);
        } else {
            abortAnimation(view, TAG_ANIMATOR_ALPHA);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void applyToView(View view) {
        if (this.gone) {
            return;
        }
        boolean z = view.getTag(TAG_ANIMATOR_TRANSLATION_X) != null;
        C29791 c29791 = NO_NEW_ANIMATIONS;
        if (z) {
            startXTranslationAnimation(view, c29791);
        } else {
            float translationX = view.getTranslationX();
            float f = this.mXTranslation;
            if (translationX != f) {
                view.setTranslationX(f);
            }
        }
        if (view.getTag(TAG_ANIMATOR_TRANSLATION_Y) != null) {
            startYTranslationAnimation(view, c29791);
        } else {
            float translationY = view.getTranslationY();
            float f2 = this.mYTranslation;
            if (translationY != f2) {
                view.setTranslationY(f2);
            }
        }
        if (view.getTag(TAG_ANIMATOR_TRANSLATION_Z) != null) {
            startZTranslationAnimation(view, c29791);
        } else {
            float translationZ = view.getTranslationZ();
            float f3 = this.mZTranslation;
            if (translationZ != f3) {
                view.setTranslationZ(f3);
            }
        }
        C29802 c29802 = SCALE_X_PROPERTY;
        if (isAnimating(view, c29802)) {
            PropertyAnimator.startAnimation(view, c29802, this.mScaleX, c29791);
        } else {
            float scaleX = view.getScaleX();
            float f4 = this.mScaleX;
            if (scaleX != f4) {
                view.setScaleX(f4);
            }
        }
        C29813 c29813 = SCALE_Y_PROPERTY;
        if (isAnimating(view, c29813)) {
            PropertyAnimator.startAnimation(view, c29813, this.mScaleY, c29791);
        } else {
            float scaleY = view.getScaleY();
            float f5 = this.mScaleY;
            if (scaleY != f5) {
                view.setScaleY(f5);
            }
        }
        int visibility = view.getVisibility();
        boolean z2 = this.mAlpha == 0.0f || (this.hidden && !(isAnimating(view) && visibility == 0));
        if (view.getTag(TAG_ANIMATOR_ALPHA) != null) {
            startAlphaAnimation(view, c29791);
        } else {
            float alpha = view.getAlpha();
            float f6 = this.mAlpha;
            if (alpha != f6) {
                boolean z3 = (z2 || ((f6 > 1.0f ? 1 : (f6 == 1.0f ? 0 : -1)) == 0)) ? false : true;
                if (view instanceof NotificationFadeAware.FadeOptimizedNotification) {
                    ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) ((NotificationFadeAware.FadeOptimizedNotification) view);
                    if (expandableNotificationRow.mIsFaded != z3) {
                        expandableNotificationRow.setNotificationFaded(z3);
                    }
                } else {
                    boolean z4 = z3 && view.hasOverlappingRendering();
                    int layerType = view.getLayerType();
                    int i = z4 ? 2 : 0;
                    if (layerType != i) {
                        view.setLayerType(i, null);
                    }
                }
                view.setAlpha(this.mAlpha);
            }
        }
        int i2 = z2 ? 4 : 0;
        if (i2 != visibility) {
            if ((view instanceof ExpandableView) && ((ExpandableView) view).mWillBeGone) {
                return;
            }
            view.setVisibility(i2);
        }
    }

    public void cancelAnimations(View view) {
        Animator animator = (Animator) view.getTag(TAG_ANIMATOR_TRANSLATION_X);
        if (animator != null) {
            animator.cancel();
        }
        Animator animator2 = (Animator) view.getTag(TAG_ANIMATOR_TRANSLATION_Y);
        if (animator2 != null) {
            animator2.cancel();
        }
        Animator animator3 = (Animator) view.getTag(TAG_ANIMATOR_TRANSLATION_Z);
        if (animator3 != null) {
            animator3.cancel();
        }
        Animator animator4 = (Animator) view.getTag(TAG_ANIMATOR_ALPHA);
        if (animator4 != null) {
            animator4.cancel();
        }
    }

    public void copyFrom(ViewState viewState) {
        this.mAlpha = viewState.mAlpha;
        this.mXTranslation = viewState.mXTranslation;
        this.mYTranslation = viewState.mYTranslation;
        this.mZTranslation = viewState.mZTranslation;
        this.gone = viewState.gone;
        this.hidden = viewState.hidden;
        this.mScaleX = viewState.mScaleX;
        this.mScaleY = viewState.mScaleY;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        StringBuilder m18m = ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m("ViewState { ");
        boolean z = true;
        for (Class<?> cls = getClass(); cls != null; cls = cls.getSuperclass()) {
            for (Field field : cls.getDeclaredFields()) {
                int modifiers = field.getModifiers();
                if (!Modifier.isStatic(modifiers) && !field.isSynthetic() && !Modifier.isTransient(modifiers)) {
                    if (!z) {
                        m18m.append(", ");
                    }
                    try {
                        m18m.append(field.getName());
                        m18m.append(": ");
                        field.setAccessible(true);
                        m18m.append(field.get(this));
                    } catch (IllegalAccessException unused) {
                    }
                    z = false;
                }
            }
        }
        m18m.append(" }");
        printWriter.print(m18m);
    }

    public void initFrom(View view) {
        this.mAlpha = view.getAlpha();
        this.mXTranslation = view.getTranslationX();
        this.mYTranslation = view.getTranslationY();
        this.mZTranslation = view.getTranslationZ();
        this.gone = view.getVisibility() == 8;
        this.hidden = view.getVisibility() == 4;
        this.mScaleX = view.getScaleX();
        this.mScaleY = view.getScaleY();
    }

    public void onYTranslationAnimationFinished(View view) {
        if (!this.hidden || this.gone) {
            return;
        }
        view.setVisibility(4);
    }

    public final void setAlpha(float f) {
        if (isValidFloat(f, "alpha")) {
            this.mAlpha = f;
        }
    }

    public final void setXTranslation(float f) {
        if (isValidFloat(f, "xTranslation")) {
            this.mXTranslation = f;
        }
    }

    public final void setYTranslation(float f) {
        if (isValidFloat(f, "yTranslation")) {
            this.mYTranslation = f;
        }
    }

    public final void setZTranslation(float f) {
        if (isValidFloat(f, "zTranslation")) {
            this.mZTranslation = f;
        }
    }

    public final void startAlphaAnimation(final View view, AnimationProperties animationProperties) {
        int i = TAG_START_ALPHA;
        Float f = (Float) view.getTag(i);
        int i2 = TAG_END_ALPHA;
        Float f2 = (Float) view.getTag(i2);
        final float f3 = this.mAlpha;
        if (f2 == null || f2.floatValue() != f3) {
            int i3 = TAG_ANIMATOR_ALPHA;
            ObjectAnimator objectAnimator = (ObjectAnimator) view.getTag(i3);
            if (!animationProperties.getAnimationFilter().animateAlpha) {
                if (objectAnimator != null) {
                    PropertyValuesHolder[] values = objectAnimator.getValues();
                    float floatValue = f.floatValue() + (f3 - f2.floatValue());
                    values[0].setFloatValues(floatValue, f3);
                    view.setTag(i, Float.valueOf(floatValue));
                    view.setTag(i2, Float.valueOf(f3));
                    objectAnimator.setCurrentPlayTime(objectAnimator.getCurrentPlayTime());
                    return;
                }
                view.setAlpha(f3);
                if (f3 == 0.0f) {
                    view.setVisibility(4);
                }
            }
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.ALPHA, view.getAlpha(), f3);
            ofFloat.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
            view.setLayerType(2, null);
            ofFloat.addListener(new AnimatorListenerAdapter(this) { // from class: com.android.systemui.statusbar.notification.stack.ViewState.4
                public boolean mWasCancelled;

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationCancel(Animator animator) {
                    this.mWasCancelled = true;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    view.setLayerType(0, null);
                    if (f3 == 0.0f && !this.mWasCancelled) {
                        view.setVisibility(4);
                    }
                    view.setTag(ViewState.TAG_ANIMATOR_ALPHA, null);
                    view.setTag(ViewState.TAG_START_ALPHA, null);
                    view.setTag(ViewState.TAG_END_ALPHA, null);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator) {
                    this.mWasCancelled = false;
                }
            });
            ofFloat.setDuration(cancelAnimatorAndGetNewDuration(animationProperties.duration, objectAnimator));
            if (animationProperties.delay > 0 && (objectAnimator == null || objectAnimator.getAnimatedFraction() == 0.0f)) {
                ofFloat.setStartDelay(animationProperties.delay);
            }
            AnimatorListenerAdapter animationFinishListener = animationProperties.getAnimationFinishListener(View.ALPHA);
            if (animationFinishListener != null) {
                ofFloat.addListener(animationFinishListener);
            }
            startAnimator(ofFloat, animationFinishListener);
            view.setTag(i3, ofFloat);
            view.setTag(i, Float.valueOf(view.getAlpha()));
            view.setTag(i2, Float.valueOf(f3));
        }
    }

    public final void startXTranslationAnimation(final View view, AnimationProperties animationProperties) {
        int i = TAG_START_TRANSLATION_X;
        Float f = (Float) view.getTag(i);
        int i2 = TAG_END_TRANSLATION_X;
        Float f2 = (Float) view.getTag(i2);
        float f3 = this.mXTranslation;
        if (f2 == null || f2.floatValue() != f3) {
            int i3 = TAG_ANIMATOR_TRANSLATION_X;
            ObjectAnimator objectAnimator = (ObjectAnimator) view.getTag(i3);
            if (!animationProperties.getAnimationFilter().animateX) {
                if (objectAnimator == null) {
                    view.setTranslationX(f3);
                    return;
                }
                PropertyValuesHolder[] values = objectAnimator.getValues();
                float floatValue = f.floatValue() + (f3 - f2.floatValue());
                values[0].setFloatValues(floatValue, f3);
                view.setTag(i, Float.valueOf(floatValue));
                view.setTag(i2, Float.valueOf(f3));
                objectAnimator.setCurrentPlayTime(objectAnimator.getCurrentPlayTime());
                return;
            }
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.TRANSLATION_X, view.getTranslationX(), f3);
            Property property = View.TRANSLATION_X;
            ArrayMap arrayMap = animationProperties.mInterpolatorMap;
            Interpolator interpolator = arrayMap != null ? (Interpolator) arrayMap.get(property) : null;
            if (interpolator == null) {
                interpolator = Interpolators.FAST_OUT_SLOW_IN;
            }
            ofFloat.setInterpolator(interpolator);
            ofFloat.setDuration(cancelAnimatorAndGetNewDuration(animationProperties.duration, objectAnimator));
            if (animationProperties.delay > 0 && (objectAnimator == null || objectAnimator.getAnimatedFraction() == 0.0f)) {
                ofFloat.setStartDelay(animationProperties.delay);
            }
            AnimatorListenerAdapter animationFinishListener = animationProperties.getAnimationFinishListener(View.TRANSLATION_X);
            if (animationFinishListener != null) {
                ofFloat.addListener(animationFinishListener);
            }
            ofFloat.addListener(new AnimatorListenerAdapter(this) { // from class: com.android.systemui.statusbar.notification.stack.ViewState.6
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    view.setTag(ViewState.TAG_ANIMATOR_TRANSLATION_X, null);
                    view.setTag(ViewState.TAG_START_TRANSLATION_X, null);
                    view.setTag(ViewState.TAG_END_TRANSLATION_X, null);
                }
            });
            startAnimator(ofFloat, animationFinishListener);
            view.setTag(i3, ofFloat);
            view.setTag(i, Float.valueOf(view.getTranslationX()));
            view.setTag(i2, Float.valueOf(f3));
        }
    }

    public final void startYTranslationAnimation(final View view, AnimationProperties animationProperties) {
        int i = TAG_START_TRANSLATION_Y;
        Float f = (Float) view.getTag(i);
        int i2 = TAG_END_TRANSLATION_Y;
        Float f2 = (Float) view.getTag(i2);
        float f3 = this.mYTranslation;
        if (f2 == null || f2.floatValue() != f3) {
            int i3 = TAG_ANIMATOR_TRANSLATION_Y;
            ObjectAnimator objectAnimator = (ObjectAnimator) view.getTag(i3);
            AnimationFilter animationFilter = animationProperties.getAnimationFilter();
            if (!(animationFilter.animateY || animationFilter.animateYViews.contains(view))) {
                if (objectAnimator == null) {
                    view.setTranslationY(f3);
                    return;
                }
                PropertyValuesHolder[] values = objectAnimator.getValues();
                float floatValue = f.floatValue() + (f3 - f2.floatValue());
                values[0].setFloatValues(floatValue, f3);
                view.setTag(i, Float.valueOf(floatValue));
                view.setTag(i2, Float.valueOf(f3));
                objectAnimator.setCurrentPlayTime(objectAnimator.getCurrentPlayTime());
                return;
            }
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.TRANSLATION_Y, view.getTranslationY(), f3);
            Property property = View.TRANSLATION_Y;
            ArrayMap arrayMap = animationProperties.mInterpolatorMap;
            Interpolator interpolator = arrayMap != null ? (Interpolator) arrayMap.get(property) : null;
            if (interpolator == null) {
                interpolator = Interpolators.FAST_OUT_SLOW_IN;
            }
            ofFloat.setInterpolator(interpolator);
            ofFloat.setDuration(cancelAnimatorAndGetNewDuration(animationProperties.duration, objectAnimator));
            if (animationProperties.delay > 0 && (objectAnimator == null || objectAnimator.getAnimatedFraction() == 0.0f)) {
                ofFloat.setStartDelay(animationProperties.delay);
            }
            AnimatorListenerAdapter animationFinishListener = animationProperties.getAnimationFinishListener(View.TRANSLATION_Y);
            if (animationFinishListener != null) {
                ofFloat.addListener(animationFinishListener);
            }
            ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.notification.stack.ViewState.7
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    HeadsUpUtil.setNeedsHeadsUpDisappearAnimationAfterClick(view, false);
                    view.setTag(ViewState.TAG_ANIMATOR_TRANSLATION_Y, null);
                    view.setTag(ViewState.TAG_START_TRANSLATION_Y, null);
                    view.setTag(ViewState.TAG_END_TRANSLATION_Y, null);
                    ViewState.this.onYTranslationAnimationFinished(view);
                }
            });
            startAnimator(ofFloat, animationFinishListener);
            view.setTag(i3, ofFloat);
            view.setTag(i, Float.valueOf(view.getTranslationY()));
            view.setTag(i2, Float.valueOf(f3));
        }
    }

    public final void startZTranslationAnimation(final View view, AnimationProperties animationProperties) {
        int i = TAG_START_TRANSLATION_Z;
        Float f = (Float) view.getTag(i);
        int i2 = TAG_END_TRANSLATION_Z;
        Float f2 = (Float) view.getTag(i2);
        float f3 = this.mZTranslation;
        if (f2 == null || f2.floatValue() != f3) {
            int i3 = TAG_ANIMATOR_TRANSLATION_Z;
            ObjectAnimator objectAnimator = (ObjectAnimator) view.getTag(i3);
            if (!animationProperties.getAnimationFilter().animateZ) {
                if (objectAnimator != null) {
                    PropertyValuesHolder[] values = objectAnimator.getValues();
                    float floatValue = f.floatValue() + (f3 - f2.floatValue());
                    values[0].setFloatValues(floatValue, f3);
                    view.setTag(i, Float.valueOf(floatValue));
                    view.setTag(i2, Float.valueOf(f3));
                    objectAnimator.setCurrentPlayTime(objectAnimator.getCurrentPlayTime());
                    return;
                }
                view.setTranslationZ(f3);
            }
            Property property = View.TRANSLATION_Z;
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) property, view.getTranslationZ(), f3);
            ofFloat.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
            ofFloat.setDuration(cancelAnimatorAndGetNewDuration(animationProperties.duration, objectAnimator));
            if (animationProperties.delay > 0 && (objectAnimator == null || objectAnimator.getAnimatedFraction() == 0.0f)) {
                ofFloat.setStartDelay(animationProperties.delay);
            }
            AnimatorListenerAdapter animationFinishListener = animationProperties.getAnimationFinishListener(property);
            if (animationFinishListener != null) {
                ofFloat.addListener(animationFinishListener);
            }
            ofFloat.addListener(new AnimatorListenerAdapter(this) { // from class: com.android.systemui.statusbar.notification.stack.ViewState.5
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    view.setTag(ViewState.TAG_ANIMATOR_TRANSLATION_Z, null);
                    view.setTag(ViewState.TAG_START_TRANSLATION_Z, null);
                    view.setTag(ViewState.TAG_END_TRANSLATION_Z, null);
                }
            });
            startAnimator(ofFloat, animationFinishListener);
            view.setTag(i3, ofFloat);
            view.setTag(i, Float.valueOf(view.getTranslationZ()));
            view.setTag(i2, Float.valueOf(f3));
        }
    }

    public final boolean isAnimating(View view) {
        if (view.getTag(TAG_ANIMATOR_TRANSLATION_X) != null) {
            return true;
        }
        if (view.getTag(TAG_ANIMATOR_TRANSLATION_Y) != null) {
            return true;
        }
        if (view.getTag(TAG_ANIMATOR_TRANSLATION_Z) != null) {
            return true;
        }
        return (view.getTag(TAG_ANIMATOR_ALPHA) != null) || isAnimating(view, SCALE_X_PROPERTY) || isAnimating(view, SCALE_Y_PROPERTY);
    }
}

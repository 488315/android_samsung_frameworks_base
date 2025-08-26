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
import androidx.compose.ui.autofill.PopulateViewStructure_androidKt$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.app.animation.Interpolators;
import com.android.internal.dynamicanimation.animation.DynamicAnimation;
import com.android.internal.dynamicanimation.animation.SpringAnimation;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.AnimatableProperty;
import com.android.systemui.statusbar.notification.NotificationFadeAware;
import com.android.systemui.statusbar.notification.PhysicsProperty;
import com.android.systemui.statusbar.notification.PhysicsPropertyAnimator;
import com.android.systemui.statusbar.notification.PropertyAnimator;
import com.android.systemui.statusbar.notification.PropertyData;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.stack.ViewState;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/* loaded from: classes3.dex */
public class ViewState implements Dumpable {
    public boolean gone;
    public boolean hidden;
    public float mAlpha;
    public float mScaleX;
    public float mScaleY;
    public boolean mUsePhysicsForMovement;
    public float mXTranslation;
    public float mYTranslation;
    public float mZTranslation;
    public static final AnonymousClass1 NO_NEW_ANIMATIONS = new AnimationProperties() { // from class: com.android.systemui.statusbar.notification.stack.ViewState.1
        public final AnimationFilter mAnimationFilter = new AnimationFilter();

        @Override // com.android.systemui.statusbar.notification.stack.AnimationProperties
        public final AnimationFilter getAnimationFilter() {
            return this.mAnimationFilter;
        }
    };
    public static final int TAG_ANIMATOR_TRANSLATION_X = R.id.translation_x_animator_tag;
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
    public static final AnonymousClass2 SCALE_X_PROPERTY = new AnimatableProperty() { // from class: com.android.systemui.statusbar.notification.stack.ViewState.2
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
    public static final AnonymousClass3 SCALE_Y_PROPERTY = new AnimatableProperty() { // from class: com.android.systemui.statusbar.notification.stack.ViewState.3
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

    public ViewState() {
        this(false);
    }

    public static void abortAnimation(View view, int i) {
        Object tag = view.getTag(i);
        if (tag != null) {
            if (tag instanceof Animator) {
                ((Animator) tag).cancel();
                return;
            }
            if (tag instanceof PropertyData) {
                PropertyData propertyData = (PropertyData) tag;
                view.removeCallbacks(propertyData.delayRunnable);
                SpringAnimation springAnimation = propertyData.animator;
                if (springAnimation != null) {
                    springAnimation.cancel();
                }
            }
        }
    }

    public static long cancelAnimatorAndGetNewDuration(long j, ValueAnimator valueAnimator) {
        if (valueAnimator == null) {
            return j;
        }
        long jMax = Math.max(valueAnimator.getDuration() - valueAnimator.getCurrentPlayTime(), j);
        valueAnimator.cancel();
        return jMax;
    }

    public static boolean isAnimating(View view) {
        return isAnimating(view, TAG_ANIMATOR_TRANSLATION_X) || isAnimating(view, PhysicsPropertyAnimator.TAG_ANIMATOR_TRANSLATION_Y) || isAnimating(view, TAG_ANIMATOR_TRANSLATION_Z) || isAnimating(view, TAG_ANIMATOR_ALPHA) || isAnimating(view, SCALE_X_PROPERTY) || isAnimating(view, SCALE_Y_PROPERTY);
    }

    public static boolean isValidFloat(String str, float f) {
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

    /* JADX WARN: Removed duplicated region for block: B:50:0x00c6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void animateTo(View view, AnimationProperties animationProperties) {
        boolean z = false;
        boolean z2 = view.getVisibility() == 0;
        float f = this.mAlpha;
        if (!z2 && ((f != 0.0f || view.getAlpha() != 0.0f) && !this.gone && !this.hidden)) {
            view.setVisibility(0);
        }
        boolean z3 = this.mAlpha != view.getAlpha();
        if (view instanceof ExpandableView) {
            z3 &= true ^ ((ExpandableView) view).mWillBeGone;
        }
        if (view.getTranslationX() != this.mXTranslation) {
            startXTranslationAnimation(view, animationProperties);
        } else {
            abortAnimation(view, TAG_ANIMATOR_TRANSLATION_X);
        }
        if (view.getTranslationY() != this.mYTranslation) {
            startYTranslationAnimation(view, animationProperties);
        } else {
            abortAnimation(view, PhysicsPropertyAnimator.TAG_ANIMATOR_TRANSLATION_Y);
        }
        if (view.getTranslationZ() != this.mZTranslation) {
            startZTranslationAnimation(view, animationProperties);
        } else {
            abortAnimation(view, TAG_ANIMATOR_TRANSLATION_Z);
        }
        float scaleX = view.getScaleX();
        float f2 = this.mScaleX;
        AnonymousClass2 anonymousClass2 = SCALE_X_PROPERTY;
        if (scaleX != f2) {
            PropertyAnimator.startAnimation(view, anonymousClass2, f2, animationProperties);
        } else {
            anonymousClass2.getClass();
            abortAnimation(view, R.id.scale_x_animator_tag);
        }
        float scaleY = view.getScaleY();
        float f3 = this.mScaleY;
        AnonymousClass3 anonymousClass3 = SCALE_Y_PROPERTY;
        if (scaleY != f3) {
            PropertyAnimator.startAnimation(view, anonymousClass3, f3, animationProperties);
        } else {
            anonymousClass3.getClass();
            abortAnimation(view, R.id.scale_y_animator_tag);
        }
        if (z3 && (view instanceof ExpandableNotificationRow)) {
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
            if (expandableNotificationRow.mHeadsupDisappearRunning) {
                ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder(" does not change alpha during headsup animation away "), expandableNotificationRow.mLoggingKey, "ExpandableNotifRow");
            }
        } else {
            z = z3;
        }
        if (z) {
            startAlphaAnimation(view, animationProperties);
        } else {
            abortAnimation(view, TAG_ANIMATOR_ALPHA);
        }
    }

    public void applyToView(View view) {
        View view2;
        if (this.gone) {
            return;
        }
        boolean zIsAnimating = isAnimating(view, TAG_ANIMATOR_TRANSLATION_X);
        AnonymousClass1 anonymousClass1 = NO_NEW_ANIMATIONS;
        if (zIsAnimating) {
            startXTranslationAnimation(view, anonymousClass1);
        } else {
            float translationX = view.getTranslationX();
            float f = this.mXTranslation;
            if (translationX != f) {
                view.setTranslationX(f);
            }
        }
        if (this.mUsePhysicsForMovement) {
            PhysicsProperty physicsProperty = PhysicsPropertyAnimator.Y_TRANSLATION;
            float f2 = this.mYTranslation;
            PhysicsPropertyAnimator.Companion.getClass();
            view2 = view;
            PhysicsPropertyAnimator.Companion.setProperty(view2, physicsProperty, f2, null, false, null);
        } else {
            view2 = view;
            if (isAnimating(view2, PhysicsPropertyAnimator.TAG_ANIMATOR_TRANSLATION_Y)) {
                startYTranslationAnimation(view2, anonymousClass1);
            } else {
                float translationY = view2.getTranslationY();
                float f3 = this.mYTranslation;
                if (translationY != f3) {
                    view2.setTranslationY(f3);
                }
            }
        }
        if (isAnimating(view2, TAG_ANIMATOR_TRANSLATION_Z)) {
            startZTranslationAnimation(view2, anonymousClass1);
        } else {
            float translationZ = view2.getTranslationZ();
            float f4 = this.mZTranslation;
            if (translationZ != f4) {
                view2.setTranslationZ(f4);
            }
        }
        AnonymousClass2 anonymousClass2 = SCALE_X_PROPERTY;
        if (isAnimating(view2, anonymousClass2)) {
            PropertyAnimator.startAnimation(view2, anonymousClass2, this.mScaleX, anonymousClass1);
        } else {
            float scaleX = view2.getScaleX();
            float f5 = this.mScaleX;
            if (scaleX != f5) {
                view2.setScaleX(f5);
            }
        }
        AnonymousClass3 anonymousClass3 = SCALE_Y_PROPERTY;
        if (isAnimating(view2, anonymousClass3)) {
            PropertyAnimator.startAnimation(view2, anonymousClass3, this.mScaleY, anonymousClass1);
        } else {
            float scaleY = view2.getScaleY();
            float f6 = this.mScaleY;
            if (scaleY != f6) {
                view2.setScaleY(f6);
            }
        }
        int visibility = view2.getVisibility();
        boolean z = this.mAlpha == 0.0f || (this.hidden && !(isAnimating(view2) && visibility == 0));
        if (isAnimating(view2, TAG_ANIMATOR_ALPHA)) {
            startAlphaAnimation(view2, anonymousClass1);
        } else {
            float alpha = view2.getAlpha();
            float f7 = this.mAlpha;
            if (alpha != f7) {
                boolean z2 = (z || ((f7 > 1.0f ? 1 : (f7 == 1.0f ? 0 : -1)) == 0)) ? false : true;
                if (view2 instanceof NotificationFadeAware.FadeOptimizedNotification) {
                    ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) ((NotificationFadeAware.FadeOptimizedNotification) view2);
                    if (expandableNotificationRow.mIsFaded != z2) {
                        expandableNotificationRow.setNotificationFaded(z2);
                    }
                } else {
                    boolean z3 = z2 && view2.hasOverlappingRendering();
                    int layerType = view2.getLayerType();
                    int i = z3 ? 2 : 0;
                    if (layerType != i) {
                        view2.setLayerType(i, null);
                    }
                }
                view2.setAlpha(this.mAlpha);
            }
        }
        int i2 = z ? 4 : 0;
        if (i2 != visibility) {
            if ((view2 instanceof ExpandableView) && ((ExpandableView) view2).mWillBeGone) {
                return;
            }
            view2.setVisibility(i2);
        }
    }

    public void cancelAnimations(View view) {
        abortAnimation(view, TAG_ANIMATOR_TRANSLATION_X);
        abortAnimation(view, PhysicsPropertyAnimator.TAG_ANIMATOR_TRANSLATION_Y);
        abortAnimation(view, TAG_ANIMATOR_TRANSLATION_Z);
        abortAnimation(view, TAG_ANIMATOR_ALPHA);
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
        this.mUsePhysicsForMovement = viewState.mUsePhysicsForMovement;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        StringBuilder sbM = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m("ViewState { ");
        boolean z = true;
        for (Class<?> superclass = getClass(); superclass != null; superclass = superclass.getSuperclass()) {
            for (Field field : superclass.getDeclaredFields()) {
                int modifiers = field.getModifiers();
                if (!Modifier.isStatic(modifiers) && !field.isSynthetic() && !Modifier.isTransient(modifiers)) {
                    if (!z) {
                        sbM.append(", ");
                    }
                    try {
                        sbM.append(field.getName());
                        sbM.append(": ");
                        field.setAccessible(true);
                        sbM.append(field.get(this));
                    } catch (IllegalAccessException unused) {
                    }
                    z = false;
                }
            }
        }
        sbM.append(" }");
        printWriter.print(sbM);
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
        if (isValidFloat("alpha", f)) {
            this.mAlpha = f;
        }
    }

    public final void setScaleX(float f) {
        if (isValidFloat("scaleX", f)) {
            this.mScaleX = f;
        }
    }

    public final void setXTranslation(float f) {
        if (isValidFloat("xTranslation", f)) {
            this.mXTranslation = f;
        }
    }

    public final void setYTranslation(float f) {
        if (isValidFloat("yTranslation", f)) {
            this.mYTranslation = f;
        }
    }

    public final void setZTranslation(float f) {
        if (isValidFloat("zTranslation", f)) {
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
                    float fFloatValue = f.floatValue() + (f3 - f2.floatValue());
                    values[0].setFloatValues(fFloatValue, f3);
                    view.setTag(i, Float.valueOf(fFloatValue));
                    view.setTag(i2, Float.valueOf(f3));
                    objectAnimator.setCurrentPlayTime(objectAnimator.getCurrentPlayTime());
                    return;
                }
                view.setAlpha(f3);
                if (f3 == 0.0f) {
                    view.setVisibility(4);
                }
            }
            Property property = View.ALPHA;
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) property, view.getAlpha(), f3);
            objectAnimatorOfFloat.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
            view.setLayerType(2, null);
            objectAnimatorOfFloat.addListener(new AnimatorListenerAdapter(this) { // from class: com.android.systemui.statusbar.notification.stack.ViewState.4
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
            objectAnimatorOfFloat.setDuration(cancelAnimatorAndGetNewDuration(animationProperties.duration, objectAnimator));
            if (animationProperties.delay > 0 && (objectAnimator == null || objectAnimator.getAnimatedFraction() == 0.0f)) {
                objectAnimatorOfFloat.setStartDelay(animationProperties.delay);
            }
            AnimatorListenerAdapter animationFinishListener = animationProperties.getAnimationFinishListener(property);
            if (animationFinishListener != null) {
                objectAnimatorOfFloat.addListener(animationFinishListener);
            }
            startAnimator(objectAnimatorOfFloat, animationFinishListener);
            view.setTag(i3, objectAnimatorOfFloat);
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
                float fFloatValue = f.floatValue() + (f3 - f2.floatValue());
                values[0].setFloatValues(fFloatValue, f3);
                view.setTag(i, Float.valueOf(fFloatValue));
                view.setTag(i2, Float.valueOf(f3));
                objectAnimator.setCurrentPlayTime(objectAnimator.getCurrentPlayTime());
                return;
            }
            Property property = View.TRANSLATION_X;
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) property, view.getTranslationX(), f3);
            ArrayMap arrayMap = animationProperties.mInterpolatorMap;
            Interpolator interpolator = arrayMap != null ? (Interpolator) arrayMap.get(property) : null;
            if (interpolator == null) {
                interpolator = Interpolators.FAST_OUT_SLOW_IN;
            }
            objectAnimatorOfFloat.setInterpolator(interpolator);
            objectAnimatorOfFloat.setDuration(cancelAnimatorAndGetNewDuration(animationProperties.duration, objectAnimator));
            if (animationProperties.delay > 0 && (objectAnimator == null || objectAnimator.getAnimatedFraction() == 0.0f)) {
                objectAnimatorOfFloat.setStartDelay(animationProperties.delay);
            }
            AnimatorListenerAdapter animationFinishListener = animationProperties.getAnimationFinishListener(property);
            if (animationFinishListener != null) {
                objectAnimatorOfFloat.addListener(animationFinishListener);
            }
            objectAnimatorOfFloat.addListener(new AnimatorListenerAdapter(this) { // from class: com.android.systemui.statusbar.notification.stack.ViewState.6
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    view.setTag(ViewState.TAG_ANIMATOR_TRANSLATION_X, null);
                    view.setTag(ViewState.TAG_START_TRANSLATION_X, null);
                    view.setTag(ViewState.TAG_END_TRANSLATION_X, null);
                }
            });
            startAnimator(objectAnimatorOfFloat, animationFinishListener);
            view.setTag(i3, objectAnimatorOfFloat);
            view.setTag(i, Float.valueOf(view.getTranslationX()));
            view.setTag(i2, Float.valueOf(f3));
        }
    }

    public final void startYTranslationAnimation(final View view, AnimationProperties animationProperties) {
        DynamicAnimation.OnAnimationEndListener onAnimationEndListener;
        if (this.mUsePhysicsForMovement) {
            onAnimationEndListener = isAnimating(view, PhysicsPropertyAnimator.TAG_ANIMATOR_TRANSLATION_Y) ? null : new DynamicAnimation.OnAnimationEndListener() { // from class: com.android.systemui.statusbar.notification.stack.ViewState$$ExternalSyntheticLambda0
                public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z, float f, float f2) {
                    ViewState viewState = this.f$0;
                    View view2 = view;
                    ViewState.AnonymousClass1 anonymousClass1 = ViewState.NO_NEW_ANIMATIONS;
                    viewState.getClass();
                    if (z) {
                        return;
                    }
                    view2.setTag(R.id.is_clicked_heads_up_tag, null);
                    viewState.onYTranslationAnimationFinished(view2);
                }
            };
            PhysicsProperty physicsProperty = PhysicsPropertyAnimator.Y_TRANSLATION;
            float f = this.mYTranslation;
            boolean z = animationProperties.getAnimationFilter().animateY;
            PhysicsPropertyAnimator.Companion.getClass();
            PhysicsPropertyAnimator.Companion.setProperty(view, physicsProperty, f, animationProperties, z, onAnimationEndListener);
            return;
        }
        int i = TAG_START_TRANSLATION_Y;
        Float f2 = (Float) view.getTag(i);
        int i2 = TAG_END_TRANSLATION_Y;
        Float f3 = (Float) view.getTag(i2);
        float f4 = this.mYTranslation;
        if (f3 == null || f3.floatValue() != f4) {
            int i3 = PhysicsPropertyAnimator.TAG_ANIMATOR_TRANSLATION_Y;
            ObjectAnimator objectAnimator = (ObjectAnimator) view.getTag(i3);
            if (!animationProperties.getAnimationFilter().animateY) {
                if (objectAnimator == null) {
                    view.setTranslationY(f4);
                    return;
                }
                PropertyValuesHolder[] values = objectAnimator.getValues();
                float fFloatValue = f2.floatValue() + (f4 - f3.floatValue());
                values[0].setFloatValues(fFloatValue, f4);
                view.setTag(i, Float.valueOf(fFloatValue));
                view.setTag(i2, Float.valueOf(f4));
                objectAnimator.setCurrentPlayTime(objectAnimator.getCurrentPlayTime());
                return;
            }
            Property property = View.TRANSLATION_Y;
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) property, view.getTranslationY(), f4);
            ArrayMap arrayMap = animationProperties.mInterpolatorMap;
            onAnimationEndListener = arrayMap != null ? (Interpolator) arrayMap.get(property) : null;
            if (onAnimationEndListener == null) {
                onAnimationEndListener = Interpolators.FAST_OUT_SLOW_IN;
            }
            objectAnimatorOfFloat.setInterpolator(onAnimationEndListener);
            objectAnimatorOfFloat.setDuration(cancelAnimatorAndGetNewDuration(animationProperties.duration, objectAnimator));
            if (animationProperties.delay > 0 && (objectAnimator == null || objectAnimator.getAnimatedFraction() == 0.0f)) {
                objectAnimatorOfFloat.setStartDelay(animationProperties.delay);
            }
            AnimatorListenerAdapter animationFinishListener = animationProperties.getAnimationFinishListener(property);
            if (animationFinishListener != null) {
                objectAnimatorOfFloat.addListener(animationFinishListener);
            }
            objectAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.notification.stack.ViewState.7
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    view.setTag(R.id.is_clicked_heads_up_tag, null);
                    view.setTag(PhysicsPropertyAnimator.TAG_ANIMATOR_TRANSLATION_Y, null);
                    view.setTag(ViewState.TAG_START_TRANSLATION_Y, null);
                    view.setTag(ViewState.TAG_END_TRANSLATION_Y, null);
                    ViewState.this.onYTranslationAnimationFinished(view);
                }
            });
            startAnimator(objectAnimatorOfFloat, animationFinishListener);
            view.setTag(i3, objectAnimatorOfFloat);
            view.setTag(i, Float.valueOf(view.getTranslationY()));
            view.setTag(i2, Float.valueOf(f4));
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
                    float fFloatValue = f.floatValue() + (f3 - f2.floatValue());
                    values[0].setFloatValues(fFloatValue, f3);
                    view.setTag(i, Float.valueOf(fFloatValue));
                    view.setTag(i2, Float.valueOf(f3));
                    objectAnimator.setCurrentPlayTime(objectAnimator.getCurrentPlayTime());
                    return;
                }
                view.setTranslationZ(f3);
            }
            Property property = View.TRANSLATION_Z;
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) property, view.getTranslationZ(), f3);
            objectAnimatorOfFloat.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
            objectAnimatorOfFloat.setDuration(cancelAnimatorAndGetNewDuration(animationProperties.duration, objectAnimator));
            if (animationProperties.delay > 0 && (objectAnimator == null || objectAnimator.getAnimatedFraction() == 0.0f)) {
                objectAnimatorOfFloat.setStartDelay(animationProperties.delay);
            }
            AnimatorListenerAdapter animationFinishListener = animationProperties.getAnimationFinishListener(property);
            if (animationFinishListener != null) {
                objectAnimatorOfFloat.addListener(animationFinishListener);
            }
            objectAnimatorOfFloat.addListener(new AnimatorListenerAdapter(this) { // from class: com.android.systemui.statusbar.notification.stack.ViewState.5
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    view.setTag(ViewState.TAG_ANIMATOR_TRANSLATION_Z, null);
                    view.setTag(ViewState.TAG_START_TRANSLATION_Z, null);
                    view.setTag(ViewState.TAG_END_TRANSLATION_Z, null);
                }
            });
            startAnimator(objectAnimatorOfFloat, animationFinishListener);
            view.setTag(i3, objectAnimatorOfFloat);
            view.setTag(i, Float.valueOf(view.getTranslationZ()));
            view.setTag(i2, Float.valueOf(f3));
        }
    }

    public ViewState(boolean z) {
        this.mScaleX = 1.0f;
        this.mScaleY = 1.0f;
        this.mUsePhysicsForMovement = z;
    }

    public static boolean isAnimating(View view, AnimatableProperty animatableProperty) {
        Object tag = view.getTag(animatableProperty.getAnimatorTag());
        return tag instanceof PropertyData ? ((PropertyData) tag).animator != null : tag != null;
    }

    public static boolean isAnimating(View view, int i) {
        Object tag = view.getTag(i);
        return tag instanceof PropertyData ? ((PropertyData) tag).animator != null : tag != null;
    }
}

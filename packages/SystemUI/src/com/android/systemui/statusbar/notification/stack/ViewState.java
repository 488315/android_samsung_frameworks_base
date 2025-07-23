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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        long max = Math.max(valueAnimator.getDuration() - valueAnimator.getCurrentPlayTime(), j);
        valueAnimator.cancel();
        return max;
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

    /* JADX WARN: Removed duplicated region for block: B:43:0x00c9  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00cd  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void animateTo(android.view.View r6, com.android.systemui.statusbar.notification.stack.AnimationProperties r7) {
        /*
            r5 = this;
            int r0 = r6.getVisibility()
            r1 = 1
            r2 = 0
            if (r0 != 0) goto La
            r0 = r1
            goto Lb
        La:
            r0 = r2
        Lb:
            float r3 = r5.mAlpha
            if (r0 != 0) goto L27
            r0 = 0
            int r3 = (r3 > r0 ? 1 : (r3 == r0 ? 0 : -1))
            if (r3 != 0) goto L1c
            float r3 = r6.getAlpha()
            int r0 = (r3 > r0 ? 1 : (r3 == r0 ? 0 : -1))
            if (r0 == 0) goto L27
        L1c:
            boolean r0 = r5.gone
            if (r0 != 0) goto L27
            boolean r0 = r5.hidden
            if (r0 != 0) goto L27
            r6.setVisibility(r2)
        L27:
            float r0 = r6.getAlpha()
            float r3 = r5.mAlpha
            int r0 = (r3 > r0 ? 1 : (r3 == r0 ? 0 : -1))
            if (r0 == 0) goto L33
            r0 = r1
            goto L34
        L33:
            r0 = r2
        L34:
            boolean r3 = r6 instanceof com.android.systemui.statusbar.notification.row.ExpandableView
            if (r3 == 0) goto L3f
            r3 = r6
            com.android.systemui.statusbar.notification.row.ExpandableView r3 = (com.android.systemui.statusbar.notification.row.ExpandableView) r3
            boolean r3 = r3.mWillBeGone
            r1 = r1 ^ r3
            r0 = r0 & r1
        L3f:
            float r1 = r6.getTranslationX()
            float r3 = r5.mXTranslation
            int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r1 == 0) goto L4d
            r5.startXTranslationAnimation(r6, r7)
            goto L52
        L4d:
            int r1 = com.android.systemui.statusbar.notification.stack.ViewState.TAG_ANIMATOR_TRANSLATION_X
            abortAnimation(r6, r1)
        L52:
            float r1 = r6.getTranslationY()
            float r3 = r5.mYTranslation
            int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r1 == 0) goto L60
            r5.startYTranslationAnimation(r6, r7)
            goto L65
        L60:
            int r1 = com.android.systemui.statusbar.notification.PhysicsPropertyAnimator.TAG_ANIMATOR_TRANSLATION_Y
            abortAnimation(r6, r1)
        L65:
            float r1 = r6.getTranslationZ()
            float r3 = r5.mZTranslation
            int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r1 == 0) goto L73
            r5.startZTranslationAnimation(r6, r7)
            goto L78
        L73:
            int r1 = com.android.systemui.statusbar.notification.stack.ViewState.TAG_ANIMATOR_TRANSLATION_Z
            abortAnimation(r6, r1)
        L78:
            float r1 = r6.getScaleX()
            float r3 = r5.mScaleX
            int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            com.android.systemui.statusbar.notification.stack.ViewState$2 r4 = com.android.systemui.statusbar.notification.stack.ViewState.SCALE_X_PROPERTY
            if (r1 == 0) goto L88
            com.android.systemui.statusbar.notification.PropertyAnimator.startAnimation(r6, r4, r3, r7)
            goto L91
        L88:
            r4.getClass()
            r1 = 2131364538(0x7f0a0aba, float:1.8348916E38)
            abortAnimation(r6, r1)
        L91:
            float r1 = r6.getScaleY()
            float r3 = r5.mScaleY
            int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            com.android.systemui.statusbar.notification.stack.ViewState$3 r4 = com.android.systemui.statusbar.notification.stack.ViewState.SCALE_Y_PROPERTY
            if (r1 == 0) goto La1
            com.android.systemui.statusbar.notification.PropertyAnimator.startAnimation(r6, r4, r3, r7)
            goto Laa
        La1:
            r4.getClass()
            r1 = 2131364542(0x7f0a0abe, float:1.8348924E38)
            abortAnimation(r6, r1)
        Laa:
            if (r0 == 0) goto Lc6
            boolean r1 = r6 instanceof com.android.systemui.statusbar.notification.row.ExpandableNotificationRow
            if (r1 == 0) goto Lc6
            r1 = r6
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r1 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r1
            boolean r3 = r1.mHeadsupDisappearRunning
            if (r3 == 0) goto Lc6
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r3 = " does not change alpha during headsup animation away "
            r0.<init>(r3)
            java.lang.String r1 = r1.mLoggingKey
            java.lang.String r3 = "ExpandableNotifRow"
            androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0.m(r0, r1, r3)
            goto Lc7
        Lc6:
            r2 = r0
        Lc7:
            if (r2 == 0) goto Lcd
            r5.startAlphaAnimation(r6, r7)
            return
        Lcd:
            int r5 = com.android.systemui.statusbar.notification.stack.ViewState.TAG_ANIMATOR_ALPHA
            abortAnimation(r6, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.ViewState.animateTo(android.view.View, com.android.systemui.statusbar.notification.stack.AnimationProperties):void");
    }

    public void applyToView(View view) {
        View view2;
        if (this.gone) {
            return;
        }
        boolean isAnimating = isAnimating(view, TAG_ANIMATOR_TRANSLATION_X);
        AnonymousClass1 anonymousClass1 = NO_NEW_ANIMATIONS;
        if (isAnimating) {
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
        StringBuilder m = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m("ViewState { ");
        boolean z = true;
        for (Class<?> cls = getClass(); cls != null; cls = cls.getSuperclass()) {
            for (Field field : cls.getDeclaredFields()) {
                int modifiers = field.getModifiers();
                if (!Modifier.isStatic(modifiers) && !field.isSynthetic() && !Modifier.isTransient(modifiers)) {
                    if (!z) {
                        m.append(", ");
                    }
                    try {
                        m.append(field.getName());
                        m.append(": ");
                        field.setAccessible(true);
                        m.append(field.get(this));
                    } catch (IllegalAccessException unused) {
                    }
                    z = false;
                }
            }
        }
        m.append(" }");
        printWriter.print(m);
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
            Property property = View.ALPHA;
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) property, view.getAlpha(), f3);
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
            AnimatorListenerAdapter animationFinishListener = animationProperties.getAnimationFinishListener(property);
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
            Property property = View.TRANSLATION_X;
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) property, view.getTranslationX(), f3);
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
            AnimatorListenerAdapter animationFinishListener = animationProperties.getAnimationFinishListener(property);
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
        DynamicAnimation.OnAnimationEndListener onAnimationEndListener;
        if (this.mUsePhysicsForMovement) {
            onAnimationEndListener = isAnimating(view, PhysicsPropertyAnimator.TAG_ANIMATOR_TRANSLATION_Y) ? null : new DynamicAnimation.OnAnimationEndListener() { // from class: com.android.systemui.statusbar.notification.stack.ViewState$$ExternalSyntheticLambda0
                public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z, float f, float f2) {
                    ViewState viewState = ViewState.this;
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
                float floatValue = f2.floatValue() + (f4 - f3.floatValue());
                values[0].setFloatValues(floatValue, f4);
                view.setTag(i, Float.valueOf(floatValue));
                view.setTag(i2, Float.valueOf(f4));
                objectAnimator.setCurrentPlayTime(objectAnimator.getCurrentPlayTime());
                return;
            }
            Property property = View.TRANSLATION_Y;
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) property, view.getTranslationY(), f4);
            ArrayMap arrayMap = animationProperties.mInterpolatorMap;
            onAnimationEndListener = arrayMap != null ? (Interpolator) arrayMap.get(property) : null;
            if (onAnimationEndListener == null) {
                onAnimationEndListener = Interpolators.FAST_OUT_SLOW_IN;
            }
            ofFloat.setInterpolator(onAnimationEndListener);
            ofFloat.setDuration(cancelAnimatorAndGetNewDuration(animationProperties.duration, objectAnimator));
            if (animationProperties.delay > 0 && (objectAnimator == null || objectAnimator.getAnimatedFraction() == 0.0f)) {
                ofFloat.setStartDelay(animationProperties.delay);
            }
            AnimatorListenerAdapter animationFinishListener = animationProperties.getAnimationFinishListener(property);
            if (animationFinishListener != null) {
                ofFloat.addListener(animationFinishListener);
            }
            ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.notification.stack.ViewState.7
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    view.setTag(R.id.is_clicked_heads_up_tag, null);
                    view.setTag(PhysicsPropertyAnimator.TAG_ANIMATOR_TRANSLATION_Y, null);
                    view.setTag(ViewState.TAG_START_TRANSLATION_Y, null);
                    view.setTag(ViewState.TAG_END_TRANSLATION_Y, null);
                    ViewState.this.onYTranslationAnimationFinished(view);
                }
            });
            startAnimator(ofFloat, animationFinishListener);
            view.setTag(i3, ofFloat);
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

package com.android.systemui.statusbar.notification;

import android.util.Property;
import android.view.View;
import com.android.internal.dynamicanimation.animation.DynamicAnimation;
import com.android.internal.dynamicanimation.animation.SpringAnimation;
import com.android.internal.dynamicanimation.animation.SpringForce;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.stack.AnimationProperties;
import com.android.systemui.statusbar.notification.stack.StackStateAnimator$1$$ExternalSyntheticLambda0;
import java.util.function.Consumer;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Ref$ObjectRef;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class PhysicsPropertyAnimator {
    public static final Companion Companion = new Companion(null);
    public static final int TAG_ANIMATOR_TRANSLATION_Y = R.id.translation_y_animator_tag;
    public static final PhysicsProperty Y_TRANSLATION = new PhysicsProperty(R.id.translation_y_animator_tag, View.TRANSLATION_Y, false, 4, null);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX WARN: Type inference failed for: r3v0, types: [T, com.android.internal.dynamicanimation.animation.SpringAnimation] */
        /* JADX WARN: Type inference failed for: r3v12, types: [T, com.android.internal.dynamicanimation.animation.SpringAnimation] */
        public static void setProperty(View view, PhysicsProperty physicsProperty, float f, AnimationProperties animationProperties, boolean z, DynamicAnimation.OnAnimationEndListener onAnimationEndListener) {
            DynamicAnimation.OnAnimationUpdateListener onAnimationUpdateListener;
            Consumer animationStartListener;
            if (!z) {
                physicsProperty.getClass();
                PropertyData obtainPropertyData = PhysicsPropertyAnimatorKt.obtainPropertyData(view, physicsProperty);
                if (obtainPropertyData.finalValue == f) {
                    return;
                }
                obtainPropertyData.finalValue = f;
                physicsProperty.property.set(view, Float.valueOf(f + obtainPropertyData.offset));
                return;
            }
            Property property = physicsProperty.property;
            final PropertyData obtainPropertyData2 = PhysicsPropertyAnimatorKt.obtainPropertyData(view, physicsProperty);
            float f2 = obtainPropertyData2.finalValue;
            if (f2 == f) {
                return;
            }
            obtainPropertyData2.finalValue = f;
            final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
            ?? r3 = obtainPropertyData2.animator;
            ref$ObjectRef.element = r3;
            if (r3 == 0) {
                ?? springAnimation = new SpringAnimation(view, physicsProperty.offsetProperty);
                ref$ObjectRef.element = springAnimation;
                obtainPropertyData2.animator = springAnimation;
                DynamicAnimation.OnAnimationEndListener animationEndListener = animationProperties != null ? animationProperties.getAnimationEndListener(physicsProperty.property) : null;
                if (animationEndListener != null) {
                    ((SpringAnimation) ref$ObjectRef.element).addEndListener(animationEndListener);
                }
                if (animationProperties != null && (animationStartListener = animationProperties.getAnimationStartListener()) != null) {
                    ((StackStateAnimator$1$$ExternalSyntheticLambda0) animationStartListener).accept(ref$ObjectRef.element);
                }
                ((SpringAnimation) ref$ObjectRef.element).addEndListener(new DynamicAnimation.OnAnimationEndListener() { // from class: com.android.systemui.statusbar.notification.PhysicsPropertyAnimatorKt$startAnimation$1
                    public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z2, float f3, float f4) {
                        PropertyData propertyData = PropertyData.this;
                        propertyData.animator = null;
                        propertyData.doubleOvershootAvoidingListener = null;
                        propertyData.offset = 0.0f;
                    }
                });
            }
            boolean z2 = physicsProperty.avoidDoubleOvershoot;
            if (z2 && obtainPropertyData2.doubleOvershootAvoidingListener == null) {
                DynamicAnimation.OnAnimationUpdateListener onAnimationUpdateListener2 = new DynamicAnimation.OnAnimationUpdateListener() { // from class: com.android.systemui.statusbar.notification.PhysicsPropertyAnimatorKt$startAnimation$2
                    public final void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f3, float f4) {
                        boolean z3 = Math.signum(f4) == Math.signum(PropertyData.this.startOffset);
                        boolean z4 = ((SpringAnimation) ref$ObjectRef.element).getSpring().getDampingRatio() == 1.0f;
                        boolean z5 = Math.signum(f3) == Math.signum(PropertyData.this.startOffset);
                        if (!z5 && z3 && !z4) {
                            ((SpringAnimation) ref$ObjectRef.element).getSpring().setDampingRatio(1.0f);
                        } else if (z5) {
                            if (z4 || z3) {
                                ((SpringAnimation) ref$ObjectRef.element).skipToEnd();
                            }
                        }
                    }
                };
                obtainPropertyData2.doubleOvershootAvoidingListener = onAnimationUpdateListener2;
                ((SpringAnimation) ref$ObjectRef.element).addUpdateListener(onAnimationUpdateListener2);
            } else if (!z2 && (onAnimationUpdateListener = obtainPropertyData2.doubleOvershootAvoidingListener) != null) {
                ((SpringAnimation) ref$ObjectRef.element).removeUpdateListener(onAnimationUpdateListener);
            }
            SpringAnimation springAnimation2 = (SpringAnimation) ref$ObjectRef.element;
            PhysicsPropertyAnimator.Companion.getClass();
            springAnimation2.setSpring(new SpringForce().setStiffness(380.0f).setDampingRatio(0.68f).setFinalPosition(0.0f));
            if (onAnimationEndListener != null) {
                ((SpringAnimation) ref$ObjectRef.element).addEndListener(onAnimationEndListener);
            }
            float f3 = (f2 - f) + obtainPropertyData2.offset;
            obtainPropertyData2.offset = f3;
            obtainPropertyData2.startOffset = f3;
            property.set(view, Float.valueOf(f + f3));
            view.removeCallbacks(obtainPropertyData2.delayRunnable);
            ((SpringAnimation) ref$ObjectRef.element).setStartValue(f3);
            Runnable runnable = new Runnable() { // from class: com.android.systemui.statusbar.notification.PhysicsPropertyAnimatorKt$startAnimation$startRunnable$1
                @Override // java.lang.Runnable
                public final void run() {
                    ((SpringAnimation) ref$ObjectRef.element).animateToFinalPosition(0.0f);
                    obtainPropertyData2.delayRunnable = null;
                    ((SpringAnimation) ref$ObjectRef.element).start();
                }
            };
            if (animationProperties == null || animationProperties.delay <= 0 || ((SpringAnimation) ref$ObjectRef.element).isRunning()) {
                runnable.run();
            } else {
                obtainPropertyData2.delayRunnable = runnable;
                view.postDelayed(runnable, animationProperties.delay);
            }
        }

        private Companion() {
        }
    }
}

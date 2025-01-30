package com.google.android.material.floatingactionbutton;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Property;
import android.view.View;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.animation.AnimatorSetCompat;
import com.google.android.material.animation.MotionSpec;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class BaseMotionStrategy implements MotionStrategy {
    public final Context context;
    public MotionSpec defaultMotionSpec;
    public final ExtendedFloatingActionButton fab;
    public final ArrayList listeners = new ArrayList();
    public MotionSpec motionSpec;
    public final AnimatorTracker tracker;

    public BaseMotionStrategy(ExtendedFloatingActionButton extendedFloatingActionButton, AnimatorTracker animatorTracker) {
        this.fab = extendedFloatingActionButton;
        this.context = extendedFloatingActionButton.getContext();
        this.tracker = animatorTracker;
    }

    @Override // com.google.android.material.floatingactionbutton.MotionStrategy
    public AnimatorSet createAnimator() {
        MotionSpec motionSpec = this.motionSpec;
        if (motionSpec == null) {
            if (this.defaultMotionSpec == null) {
                this.defaultMotionSpec = MotionSpec.createFromResource(getDefaultMotionSpecResource(), this.context);
            }
            motionSpec = this.defaultMotionSpec;
            motionSpec.getClass();
        }
        return createAnimator(motionSpec);
    }

    @Override // com.google.android.material.floatingactionbutton.MotionStrategy
    public void onAnimationCancel() {
        this.tracker.currentAnimator = null;
    }

    @Override // com.google.android.material.floatingactionbutton.MotionStrategy
    public void onAnimationEnd() {
        this.tracker.currentAnimator = null;
    }

    @Override // com.google.android.material.floatingactionbutton.MotionStrategy
    public void onAnimationStart(Animator animator) {
        AnimatorTracker animatorTracker = this.tracker;
        Animator animator2 = animatorTracker.currentAnimator;
        if (animator2 != null) {
            animator2.cancel();
        }
        animatorTracker.currentAnimator = animator;
    }

    public final AnimatorSet createAnimator(MotionSpec motionSpec) {
        ArrayList arrayList = new ArrayList();
        boolean hasPropertyValues = motionSpec.hasPropertyValues("opacity");
        ExtendedFloatingActionButton extendedFloatingActionButton = this.fab;
        if (hasPropertyValues) {
            arrayList.add(motionSpec.getAnimator("opacity", extendedFloatingActionButton, View.ALPHA));
        }
        if (motionSpec.hasPropertyValues("scale")) {
            arrayList.add(motionSpec.getAnimator("scale", extendedFloatingActionButton, View.SCALE_Y));
            arrayList.add(motionSpec.getAnimator("scale", extendedFloatingActionButton, View.SCALE_X));
        }
        if (motionSpec.hasPropertyValues("width")) {
            arrayList.add(motionSpec.getAnimator("width", extendedFloatingActionButton, ExtendedFloatingActionButton.WIDTH));
        }
        if (motionSpec.hasPropertyValues("height")) {
            arrayList.add(motionSpec.getAnimator("height", extendedFloatingActionButton, ExtendedFloatingActionButton.HEIGHT));
        }
        if (motionSpec.hasPropertyValues("paddingStart")) {
            arrayList.add(motionSpec.getAnimator("paddingStart", extendedFloatingActionButton, ExtendedFloatingActionButton.PADDING_START));
        }
        if (motionSpec.hasPropertyValues("paddingEnd")) {
            arrayList.add(motionSpec.getAnimator("paddingEnd", extendedFloatingActionButton, ExtendedFloatingActionButton.PADDING_END));
        }
        if (motionSpec.hasPropertyValues("labelOpacity")) {
            arrayList.add(motionSpec.getAnimator("labelOpacity", extendedFloatingActionButton, new Property(Float.class, "LABEL_OPACITY_PROPERTY") { // from class: com.google.android.material.floatingactionbutton.BaseMotionStrategy.1
                @Override // android.util.Property
                public final Object get(Object obj) {
                    ExtendedFloatingActionButton extendedFloatingActionButton2 = (ExtendedFloatingActionButton) obj;
                    float alpha = (Color.alpha(extendedFloatingActionButton2.getCurrentTextColor()) / 255.0f) / Color.alpha(extendedFloatingActionButton2.originalTextCsl.getColorForState(extendedFloatingActionButton2.getDrawableState(), BaseMotionStrategy.this.fab.originalTextCsl.getDefaultColor()));
                    TimeInterpolator timeInterpolator = AnimationUtils.LINEAR_INTERPOLATOR;
                    return Float.valueOf((alpha * 1.0f) + 0.0f);
                }

                @Override // android.util.Property
                public final void set(Object obj, Object obj2) {
                    ExtendedFloatingActionButton extendedFloatingActionButton2 = (ExtendedFloatingActionButton) obj;
                    Float f = (Float) obj2;
                    int colorForState = extendedFloatingActionButton2.originalTextCsl.getColorForState(extendedFloatingActionButton2.getDrawableState(), BaseMotionStrategy.this.fab.originalTextCsl.getDefaultColor());
                    float floatValue = f.floatValue();
                    TimeInterpolator timeInterpolator = AnimationUtils.LINEAR_INTERPOLATOR;
                    ColorStateList valueOf = ColorStateList.valueOf(Color.argb((int) (((((Color.alpha(colorForState) / 255.0f) - 0.0f) * floatValue) + 0.0f) * 255.0f), Color.red(colorForState), Color.green(colorForState), Color.blue(colorForState)));
                    if (f.floatValue() == 1.0f) {
                        extendedFloatingActionButton2.silentlyUpdateTextColor(extendedFloatingActionButton2.originalTextCsl);
                    } else {
                        extendedFloatingActionButton2.silentlyUpdateTextColor(valueOf);
                    }
                }
            }));
        }
        AnimatorSet animatorSet = new AnimatorSet();
        AnimatorSetCompat.playTogether(animatorSet, arrayList);
        return animatorSet;
    }
}

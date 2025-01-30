package com.google.android.material.animation;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;
import android.util.Property;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import androidx.collection.SimpleArrayMap;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MotionSpec {
    public final SimpleArrayMap timings = new SimpleArrayMap();
    public final SimpleArrayMap propertyValues = new SimpleArrayMap();

    public static MotionSpec createFromAttribute(Context context, TypedArray typedArray, int i) {
        int resourceId;
        if (!typedArray.hasValue(i) || (resourceId = typedArray.getResourceId(i, 0)) == 0) {
            return null;
        }
        return createFromResource(resourceId, context);
    }

    public static MotionSpec createFromResource(int i, Context context) {
        try {
            Animator loadAnimator = AnimatorInflater.loadAnimator(context, i);
            if (loadAnimator instanceof AnimatorSet) {
                return createSpecFromAnimators(((AnimatorSet) loadAnimator).getChildAnimations());
            }
            if (loadAnimator == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            arrayList.add(loadAnimator);
            return createSpecFromAnimators(arrayList);
        } catch (Exception e) {
            Log.w("MotionSpec", "Can't load animation resource ID #0x" + Integer.toHexString(i), e);
            return null;
        }
    }

    public static MotionSpec createSpecFromAnimators(List list) {
        MotionSpec motionSpec = new MotionSpec();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            Animator animator = (Animator) list.get(i);
            if (!(animator instanceof ObjectAnimator)) {
                throw new IllegalArgumentException("Animator must be an ObjectAnimator: " + animator);
            }
            ObjectAnimator objectAnimator = (ObjectAnimator) animator;
            motionSpec.setPropertyValues(objectAnimator.getPropertyName(), objectAnimator.getValues());
            String propertyName = objectAnimator.getPropertyName();
            long startDelay = objectAnimator.getStartDelay();
            long duration = objectAnimator.getDuration();
            TimeInterpolator interpolator = objectAnimator.getInterpolator();
            if ((interpolator instanceof AccelerateDecelerateInterpolator) || interpolator == null) {
                interpolator = AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR;
            } else if (interpolator instanceof AccelerateInterpolator) {
                interpolator = AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR;
            } else if (interpolator instanceof DecelerateInterpolator) {
                interpolator = AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR;
            }
            MotionTiming motionTiming = new MotionTiming(startDelay, duration, interpolator);
            motionTiming.repeatCount = objectAnimator.getRepeatCount();
            motionTiming.repeatMode = objectAnimator.getRepeatMode();
            motionSpec.timings.put(propertyName, motionTiming);
        }
        return motionSpec;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof MotionSpec) {
            return this.timings.equals(((MotionSpec) obj).timings);
        }
        return false;
    }

    public final ObjectAnimator getAnimator(String str, ExtendedFloatingActionButton extendedFloatingActionButton, Property property) {
        ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(extendedFloatingActionButton, getPropertyValues(str));
        ofPropertyValuesHolder.setProperty(property);
        getTiming(str).apply(ofPropertyValuesHolder);
        return ofPropertyValuesHolder;
    }

    public final PropertyValuesHolder[] getPropertyValues(String str) {
        if (!hasPropertyValues(str)) {
            throw new IllegalArgumentException();
        }
        PropertyValuesHolder[] propertyValuesHolderArr = (PropertyValuesHolder[]) this.propertyValues.get(str);
        PropertyValuesHolder[] propertyValuesHolderArr2 = new PropertyValuesHolder[propertyValuesHolderArr.length];
        for (int i = 0; i < propertyValuesHolderArr.length; i++) {
            propertyValuesHolderArr2[i] = propertyValuesHolderArr[i].clone();
        }
        return propertyValuesHolderArr2;
    }

    public final MotionTiming getTiming(String str) {
        SimpleArrayMap simpleArrayMap = this.timings;
        if (simpleArrayMap.get(str) != null) {
            return (MotionTiming) simpleArrayMap.get(str);
        }
        throw new IllegalArgumentException();
    }

    public final boolean hasPropertyValues(String str) {
        return this.propertyValues.get(str) != null;
    }

    public final int hashCode() {
        return this.timings.hashCode();
    }

    public final void setPropertyValues(String str, PropertyValuesHolder[] propertyValuesHolderArr) {
        this.propertyValues.put(str, propertyValuesHolderArr);
    }

    public final String toString() {
        return "\n" + MotionSpec.class.getName() + '{' + Integer.toHexString(System.identityHashCode(this)) + " timings: " + this.timings + "}\n";
    }
}

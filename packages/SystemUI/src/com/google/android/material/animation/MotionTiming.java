package com.google.android.material.animation;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class MotionTiming {
    public final long delay;
    public final long duration;
    public final TimeInterpolator interpolator;
    public int repeatCount;
    public int repeatMode;

    public MotionTiming(long j, long j2) {
        this.interpolator = null;
        this.repeatCount = 0;
        this.repeatMode = 1;
        this.delay = j;
        this.duration = j2;
    }

    public final void apply(Animator animator) {
        animator.setStartDelay(this.delay);
        animator.setDuration(this.duration);
        animator.setInterpolator(getInterpolator());
        if (animator instanceof ValueAnimator) {
            ValueAnimator valueAnimator = (ValueAnimator) animator;
            valueAnimator.setRepeatCount(this.repeatCount);
            valueAnimator.setRepeatMode(this.repeatMode);
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MotionTiming)) {
            return false;
        }
        MotionTiming motionTiming = (MotionTiming) obj;
        if (this.delay == motionTiming.delay && this.duration == motionTiming.duration && this.repeatCount == motionTiming.repeatCount && this.repeatMode == motionTiming.repeatMode) {
            return getInterpolator().getClass().equals(motionTiming.getInterpolator().getClass());
        }
        return false;
    }

    public final TimeInterpolator getInterpolator() {
        TimeInterpolator timeInterpolator = this.interpolator;
        return timeInterpolator != null ? timeInterpolator : AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR;
    }

    public final int hashCode() {
        long j = this.delay;
        long j2 = this.duration;
        return ((((getInterpolator().getClass().hashCode() + (((((int) (j ^ (j >>> 32))) * 31) + ((int) ((j2 >>> 32) ^ j2))) * 31)) * 31) + this.repeatCount) * 31) + this.repeatMode;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("\n");
        sb.append(getClass().getName());
        sb.append('{');
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" delay: ");
        sb.append(this.delay);
        sb.append(" duration: ");
        sb.append(this.duration);
        sb.append(" interpolator: ");
        sb.append(getInterpolator().getClass());
        sb.append(" repeatCount: ");
        sb.append(this.repeatCount);
        sb.append(" repeatMode: ");
        return ReorderTile$$ExternalSyntheticOutline0.m(this.repeatMode, "}\n", sb);
    }

    public MotionTiming(long j, long j2, TimeInterpolator timeInterpolator) {
        this.repeatCount = 0;
        this.repeatMode = 1;
        this.delay = j;
        this.duration = j2;
        this.interpolator = timeInterpolator;
    }
}

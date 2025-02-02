package com.airbnb.lottie.value;

import android.graphics.PointF;
import android.view.animation.Interpolator;
import com.airbnb.lottie.LottieComposition;

/* loaded from: classes.dex */
public class Keyframe<T> {
    private final LottieComposition composition;
    public Float endFrame;
    private float endProgress;
    public T endValue;
    private float endValueFloat;
    private int endValueInt;
    public final Interpolator interpolator;
    public PointF pathCp1;
    public PointF pathCp2;
    public final float startFrame;
    private float startProgress;
    public final T startValue;
    private float startValueFloat;
    private int startValueInt;

    public Keyframe(LottieComposition lottieComposition, T t, T t2, Interpolator interpolator, float f, Float f2) {
        this.startValueFloat = -3987645.8f;
        this.endValueFloat = -3987645.8f;
        this.startValueInt = 784923401;
        this.endValueInt = 784923401;
        this.startProgress = Float.MIN_VALUE;
        this.endProgress = Float.MIN_VALUE;
        this.pathCp1 = null;
        this.pathCp2 = null;
        this.composition = lottieComposition;
        this.startValue = t;
        this.endValue = t2;
        this.interpolator = interpolator;
        this.startFrame = f;
        this.endFrame = f2;
    }

    public final float getEndProgress() {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            return 1.0f;
        }
        if (this.endProgress == Float.MIN_VALUE) {
            if (this.endFrame == null) {
                this.endProgress = 1.0f;
            } else {
                this.endProgress = ((this.endFrame.floatValue() - this.startFrame) / lottieComposition.getDurationFrames()) + getStartProgress();
            }
        }
        return this.endProgress;
    }

    public final float getEndValueFloat() {
        if (this.endValueFloat == -3987645.8f) {
            this.endValueFloat = ((Float) this.endValue).floatValue();
        }
        return this.endValueFloat;
    }

    public final int getEndValueInt() {
        if (this.endValueInt == 784923401) {
            this.endValueInt = ((Integer) this.endValue).intValue();
        }
        return this.endValueInt;
    }

    public final float getStartProgress() {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            return 0.0f;
        }
        if (this.startProgress == Float.MIN_VALUE) {
            this.startProgress = (this.startFrame - lottieComposition.getStartFrame()) / lottieComposition.getDurationFrames();
        }
        return this.startProgress;
    }

    public final float getStartValueFloat() {
        if (this.startValueFloat == -3987645.8f) {
            this.startValueFloat = ((Float) this.startValue).floatValue();
        }
        return this.startValueFloat;
    }

    public final int getStartValueInt() {
        if (this.startValueInt == 784923401) {
            this.startValueInt = ((Integer) this.startValue).intValue();
        }
        return this.startValueInt;
    }

    public final boolean isStatic() {
        return this.interpolator == null;
    }

    public final String toString() {
        return "Keyframe{startValue=" + this.startValue + ", endValue=" + this.endValue + ", startFrame=" + this.startFrame + ", endFrame=" + this.endFrame + ", interpolator=" + this.interpolator + '}';
    }

    public Keyframe(T t) {
        this.startValueFloat = -3987645.8f;
        this.endValueFloat = -3987645.8f;
        this.startValueInt = 784923401;
        this.endValueInt = 784923401;
        this.startProgress = Float.MIN_VALUE;
        this.endProgress = Float.MIN_VALUE;
        this.pathCp1 = null;
        this.pathCp2 = null;
        this.composition = null;
        this.startValue = t;
        this.endValue = t;
        this.interpolator = null;
        this.startFrame = Float.MIN_VALUE;
        this.endFrame = Float.valueOf(Float.MAX_VALUE);
    }
}

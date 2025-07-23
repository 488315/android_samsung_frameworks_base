package com.samsung.vekit.Common.Object;

import com.samsung.vekit.Common.Type.InterpolationType;

/* loaded from: classes6.dex */
public class KeyFrame<T> {
    protected BezierControlPoint bezierControlPoint;
    protected InterpolationType interpolationType;
    private T target;
    private long time;

    @Deprecated
    public long getDuration() {
        return 0L;
    }

    @Deprecated
    public T getTo() {
        return null;
    }

    @Deprecated
    public void setDuration(long j) {
    }

    @Deprecated
    public void setFrom(T t) {
    }

    @Deprecated
    public void setTo(T t) {
    }

    public KeyFrame(T t, long j) {
        this.bezierControlPoint = new BezierControlPoint();
        this.target = t;
        this.time = j;
        this.interpolationType = InterpolationType.LINEAR;
    }

    public KeyFrame(T t, long j, InterpolationType interpolationType) {
        this.bezierControlPoint = new BezierControlPoint();
        this.target = t;
        this.time = j;
        this.interpolationType = interpolationType;
    }

    public KeyFrame(T t, long j, InterpolationType interpolationType, BezierControlPoint bezierControlPoint) {
        new BezierControlPoint();
        this.target = t;
        this.time = j;
        this.interpolationType = interpolationType;
        this.bezierControlPoint = bezierControlPoint;
    }

    public void setInterpolationType(InterpolationType interpolationType) {
        this.interpolationType = interpolationType;
    }

    public InterpolationType getInterpolationType() {
        return this.interpolationType;
    }

    public void setBezierControlPoint(float f, float f2, float f3, float f4) {
        this.bezierControlPoint.setValues(f, f2, f3, f4);
    }

    public BezierControlPoint getBezierControlPoint() {
        return this.bezierControlPoint;
    }

    public T getTarget() {
        return this.target;
    }

    public void setTarget(T t) {
        this.target = t;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long j) {
        this.time = j;
    }

    @Deprecated
    public KeyFrame(T t, T t2, long j) {
        this.bezierControlPoint = new BezierControlPoint();
    }

    @Deprecated
    public KeyFrame(T t, T t2, long j, InterpolationType interpolationType) {
        this.bezierControlPoint = new BezierControlPoint();
    }

    @Deprecated
    public KeyFrame(T t, T t2, long j, InterpolationType interpolationType, BezierControlPoint bezierControlPoint) {
        this.bezierControlPoint = new BezierControlPoint();
    }
}

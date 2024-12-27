package com.samsung.vekit.Common.Object;

import com.samsung.vekit.Common.Type.InterpolationType;

/* loaded from: classes6.dex */
public class KeyFrame<T> {
    protected BezierControlPoint bezierControlPoint;
    protected InterpolationType interpolationType;
    private T target;
    private long time;

    public KeyFrame(T target, long time) {
        this.bezierControlPoint = new BezierControlPoint();
        this.target = target;
        this.time = time;
        this.interpolationType = InterpolationType.LINEAR;
    }

    public KeyFrame(T target, long time, InterpolationType interpolationType) {
        this.bezierControlPoint = new BezierControlPoint();
        this.target = target;
        this.time = time;
        this.interpolationType = interpolationType;
    }

    public KeyFrame(
            T target,
            long time,
            InterpolationType interpolationType,
            BezierControlPoint bezierControlPoint) {
        this.bezierControlPoint = new BezierControlPoint();
        this.target = target;
        this.time = time;
        this.interpolationType = interpolationType;
        this.bezierControlPoint = bezierControlPoint;
    }

    public void setInterpolationType(InterpolationType interpolationType) {
        this.interpolationType = interpolationType;
    }

    public InterpolationType getInterpolationType() {
        return this.interpolationType;
    }

    public void setBezierControlPoint(
            float controlPointX1,
            float controlPointY1,
            float controlPointX2,
            float controlPointY2) {
        this.bezierControlPoint.setValues(
                controlPointX1, controlPointY1, controlPointX2, controlPointY2);
    }

    public BezierControlPoint getBezierControlPoint() {
        return this.bezierControlPoint;
    }

    public T getTarget() {
        return this.target;
    }

    public void setTarget(T target) {
        this.target = target;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Deprecated
    public KeyFrame(T from, T to, long duration) {
        this.bezierControlPoint = new BezierControlPoint();
    }

    @Deprecated
    public KeyFrame(T from, T to, long duration, InterpolationType interpolationType) {
        this.bezierControlPoint = new BezierControlPoint();
    }

    @Deprecated
    public KeyFrame(
            T from,
            T to,
            long duration,
            InterpolationType interpolationType,
            BezierControlPoint bezierControlPoint) {
        this.bezierControlPoint = new BezierControlPoint();
    }

    @Deprecated
    public void setFrom(T from) {}

    @Deprecated
    public T getTo() {
        return null;
    }

    @Deprecated
    public void setTo(T to) {}

    @Deprecated
    public long getDuration() {
        return 0L;
    }

    @Deprecated
    public void setDuration(long duration) {}
}

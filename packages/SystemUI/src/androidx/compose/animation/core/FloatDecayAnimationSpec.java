package androidx.compose.animation.core;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface FloatDecayAnimationSpec {
    float getAbsVelocityThreshold();

    long getDurationNanos(float f);

    float getTargetValue(float f, float f2);

    float getValueFromNanos(float f, float f2, long j);

    float getVelocityFromNanos(float f, long j);
}

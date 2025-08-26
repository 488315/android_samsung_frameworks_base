package androidx.compose.animation.core;

/* loaded from: classes.dex */
public interface FloatDecayAnimationSpec {
    float getAbsVelocityThreshold();

    long getDurationNanos(float f);

    float getTargetValue(float f, float f2);

    float getValueFromNanos(float f, float f2, long j);

    float getVelocityFromNanos(float f, long j);
}

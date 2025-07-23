package androidx.compose.animation.core;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface FloatAnimationSpec extends AnimationSpec<Float> {
    long getDurationNanos(float f, float f2, float f3);

    default float getEndVelocity(float f, float f2, float f3) {
        return getVelocityFromNanos(getDurationNanos(f, f2, f3), f, f2, f3);
    }

    float getValueFromNanos(long j, float f, float f2, float f3);

    float getVelocityFromNanos(long j, float f, float f2, float f3);

    @Override // androidx.compose.animation.core.AnimationSpec
    default VectorizedAnimationSpec vectorize(TwoWayConverter twoWayConverter) {
        return new VectorizedFloatAnimationSpec(this);
    }
}

package androidx.compose.animation.core;

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

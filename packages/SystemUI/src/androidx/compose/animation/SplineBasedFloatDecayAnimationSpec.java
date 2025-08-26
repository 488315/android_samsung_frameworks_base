package androidx.compose.animation;

import androidx.compose.animation.FlingCalculator;
import androidx.compose.animation.core.FloatDecayAnimationSpec;
import androidx.compose.ui.unit.Density;

/* loaded from: classes.dex */
public final class SplineBasedFloatDecayAnimationSpec implements FloatDecayAnimationSpec {
    public final FlingCalculator flingCalculator;

    public SplineBasedFloatDecayAnimationSpec(Density density) {
        this.flingCalculator = new FlingCalculator(SplineBasedFloatDecayAnimationSpec_androidKt.platformFlingScrollFriction, density);
    }

    @Override // androidx.compose.animation.core.FloatDecayAnimationSpec
    public final float getAbsVelocityThreshold() {
        return 0.0f;
    }

    @Override // androidx.compose.animation.core.FloatDecayAnimationSpec
    public final long getDurationNanos(float f) {
        return ((long) (Math.exp(this.flingCalculator.getSplineDeceleration(f) / (FlingCalculatorKt.DecelerationRate - 1.0d)) * 1000.0d)) * 1000000;
    }

    @Override // androidx.compose.animation.core.FloatDecayAnimationSpec
    public final float getTargetValue(float f, float f2) {
        double splineDeceleration = this.flingCalculator.getSplineDeceleration(f2);
        double d = FlingCalculatorKt.DecelerationRate;
        return (Math.signum(f2) * ((float) (Math.exp((d / (d - 1.0d)) * splineDeceleration) * r8.friction * r8.magicPhysicalCoefficient))) + f;
    }

    @Override // androidx.compose.animation.core.FloatDecayAnimationSpec
    public final float getValueFromNanos(float f, float f2, long j) {
        long j2 = j / 1000000;
        FlingCalculator.FlingInfo flingInfo = this.flingCalculator.flingInfo(f2);
        long j3 = flingInfo.duration;
        float f3 = j3 > 0 ? j2 / j3 : 1.0f;
        float fSignum = Math.signum(flingInfo.initialVelocity) * flingInfo.distance;
        AndroidFlingSpline.INSTANCE.getClass();
        return (fSignum * AndroidFlingSpline.flingPosition(f3).distanceCoefficient) + f;
    }

    @Override // androidx.compose.animation.core.FloatDecayAnimationSpec
    public final float getVelocityFromNanos(float f, long j) {
        long j2 = j / 1000000;
        FlingCalculator.FlingInfo flingInfo = this.flingCalculator.flingInfo(f);
        long j3 = flingInfo.duration;
        float f2 = j3 > 0 ? j2 / j3 : 1.0f;
        AndroidFlingSpline.INSTANCE.getClass();
        return (((Math.signum(flingInfo.initialVelocity) * AndroidFlingSpline.flingPosition(f2).velocityCoefficient) * flingInfo.distance) / j3) * 1000.0f;
    }
}

package androidx.compose.animation;

import androidx.compose.ui.unit.Density;

/* loaded from: classes.dex */
public final class FlingCalculator {
    public final float friction;
    public final float magicPhysicalCoefficient;

    public final class FlingInfo {
        public final float distance;
        public final long duration;
        public final float initialVelocity;

        public FlingInfo(float f, float f2, long j) {
            this.initialVelocity = f;
            this.distance = f2;
            this.duration = j;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof FlingInfo)) {
                return false;
            }
            FlingInfo flingInfo = (FlingInfo) obj;
            return Float.compare(this.initialVelocity, flingInfo.initialVelocity) == 0 && Float.compare(this.distance, flingInfo.distance) == 0 && this.duration == flingInfo.duration;
        }

        public final int hashCode() {
            return Long.hashCode(this.duration) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.distance, Float.hashCode(this.initialVelocity) * 31, 31);
        }

        public final String toString() {
            return "FlingInfo(initialVelocity=" + this.initialVelocity + ", distance=" + this.distance + ", duration=" + this.duration + ')';
        }
    }

    public FlingCalculator(float f, Density density) {
        this.friction = f;
        float density2 = density.getDensity();
        float f2 = FlingCalculatorKt.DecelerationRate;
        this.magicPhysicalCoefficient = density2 * 386.0878f * 160.0f * 0.84f;
    }

    public final FlingInfo flingInfo(float f) {
        double splineDeceleration = getSplineDeceleration(f);
        double d = FlingCalculatorKt.DecelerationRate;
        double d2 = d - 1.0d;
        return new FlingInfo(f, (float) (Math.exp((d / d2) * splineDeceleration) * this.friction * this.magicPhysicalCoefficient), (long) (Math.exp(splineDeceleration / d2) * 1000.0d));
    }

    public final double getSplineDeceleration(float f) {
        AndroidFlingSpline androidFlingSpline = AndroidFlingSpline.INSTANCE;
        float f2 = this.friction * this.magicPhysicalCoefficient;
        androidFlingSpline.getClass();
        return Math.log((Math.abs(f) * 0.35f) / f2);
    }
}

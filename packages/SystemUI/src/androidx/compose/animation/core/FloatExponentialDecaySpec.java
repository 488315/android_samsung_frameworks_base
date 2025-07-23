package androidx.compose.animation.core;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class FloatExponentialDecaySpec implements FloatDecayAnimationSpec {
    public final float absVelocityThreshold;
    public final float friction;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public FloatExponentialDecaySpec() {
        /*
            r3 = this;
            r0 = 3
            r1 = 0
            r2 = 0
            r3.<init>(r2, r2, r0, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.animation.core.FloatExponentialDecaySpec.<init>():void");
    }

    @Override // androidx.compose.animation.core.FloatDecayAnimationSpec
    public final float getAbsVelocityThreshold() {
        return this.absVelocityThreshold;
    }

    @Override // androidx.compose.animation.core.FloatDecayAnimationSpec
    public final long getDurationNanos(float f) {
        return ((long) ((((float) Math.log(this.absVelocityThreshold / Math.abs(f))) * 1000.0f) / this.friction)) * 1000000;
    }

    @Override // androidx.compose.animation.core.FloatDecayAnimationSpec
    public final float getTargetValue(float f, float f2) {
        if (Math.abs(f2) <= this.absVelocityThreshold) {
            return f;
        }
        double log = Math.log(Math.abs(r1 / f2));
        float f3 = this.friction;
        return ((f2 / f3) * ((float) Math.exp((f3 * ((log / f3) * 1000)) / 1000.0f))) + (f - (f2 / f3));
    }

    @Override // androidx.compose.animation.core.FloatDecayAnimationSpec
    public final float getValueFromNanos(float f, float f2, long j) {
        float f3 = this.friction;
        return ((f2 / f3) * ((float) Math.exp((f3 * (j / 1000000)) / 1000.0f))) + (f - (f2 / f3));
    }

    @Override // androidx.compose.animation.core.FloatDecayAnimationSpec
    public final float getVelocityFromNanos(float f, long j) {
        return f * ((float) Math.exp(((j / 1000000) / 1000.0f) * this.friction));
    }

    public FloatExponentialDecaySpec(float f, float f2) {
        this.absVelocityThreshold = Math.max(1.0E-7f, Math.abs(f2));
        this.friction = Math.max(1.0E-4f, f) * (-4.2f);
    }

    public /* synthetic */ FloatExponentialDecaySpec(float f, float f2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 1.0f : f, (i & 2) != 0 ? 0.1f : f2);
    }
}

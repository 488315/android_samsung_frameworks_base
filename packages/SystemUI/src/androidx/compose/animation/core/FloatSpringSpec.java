package androidx.compose.animation.core;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class FloatSpringSpec implements FloatAnimationSpec {
    public final SpringSimulation spring;
    public final float visibilityThreshold;

    public FloatSpringSpec() {
        this(0.0f, 0.0f, 0.0f, 7, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x0130  */
    @Override // androidx.compose.animation.core.FloatAnimationSpec
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final long getDurationNanos(float r32, float r33, float r34) {
        /*
            Method dump skipped, instructions count: 590
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.animation.core.FloatSpringSpec.getDurationNanos(float, float, float):long");
    }

    @Override // androidx.compose.animation.core.FloatAnimationSpec
    public final float getEndVelocity(float f, float f2, float f3) {
        return 0.0f;
    }

    @Override // androidx.compose.animation.core.FloatAnimationSpec
    public final float getValueFromNanos(long j, float f, float f2, float f3) {
        SpringSimulation springSimulation = this.spring;
        springSimulation.finalPosition = f2;
        return Float.intBitsToFloat((int) (springSimulation.m11updateValuesIJZedt4$animation_core(f, f3, j / 1000000) >> 32));
    }

    @Override // androidx.compose.animation.core.FloatAnimationSpec
    public final float getVelocityFromNanos(long j, float f, float f2, float f3) {
        SpringSimulation springSimulation = this.spring;
        springSimulation.finalPosition = f2;
        return Float.intBitsToFloat((int) (springSimulation.m11updateValuesIJZedt4$animation_core(f, f3, j / 1000000) & 4294967295L));
    }

    public FloatSpringSpec(float f, float f2, float f3) {
        this.visibilityThreshold = f3;
        SpringSimulation springSimulation = new SpringSimulation(1.0f);
        if (f < 0.0f) {
            PreconditionsKt.throwIllegalArgumentException("Damping ratio must be non-negative");
        }
        springSimulation.dampingRatio = f;
        double d = springSimulation.naturalFreq;
        if (((float) (d * d)) <= 0.0f) {
            PreconditionsKt.throwIllegalArgumentException("Spring stiffness constant must be positive.");
        }
        springSimulation.naturalFreq = Math.sqrt(f2);
        this.spring = springSimulation;
    }

    public /* synthetic */ FloatSpringSpec(float f, float f2, float f3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 1.0f : f, (i & 2) != 0 ? 1500.0f : f2, (i & 4) != 0 ? 0.01f : f3);
    }
}

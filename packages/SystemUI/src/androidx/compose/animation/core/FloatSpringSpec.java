package androidx.compose.animation.core;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class FloatSpringSpec implements FloatAnimationSpec {
    public final SpringSimulation spring;
    public final float visibilityThreshold;

    public FloatSpringSpec() {
        this(0.0f, 0.0f, 0.0f, 7, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:46:0x0130  */
    @Override // androidx.compose.animation.core.FloatAnimationSpec
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final long getDurationNanos(float f, float f2, float f3) {
        double dLog;
        long j;
        double d;
        SpringSimulation springSimulation = this.spring;
        double d2 = springSimulation.naturalFreq;
        float f4 = (float) (d2 * d2);
        float f5 = springSimulation.dampingRatio;
        float f6 = this.visibilityThreshold;
        float f7 = (f - f2) / f6;
        float f8 = f3 / f6;
        if (f5 == 0.0f) {
            j = 9223372036854L;
        } else {
            double d3 = f4;
            double d4 = f5;
            double d5 = f8;
            double d6 = f7;
            double d7 = 1.0f;
            double dSqrt = d4 * 2.0d * Math.sqrt(d3);
            double d8 = (dSqrt * dSqrt) - (d3 * 4.0d);
            double dSqrt2 = d8 < 0.0d ? 0.0d : Math.sqrt(d8);
            double d9 = -dSqrt;
            double d10 = (d9 + dSqrt2) * 0.5d;
            double dSqrt3 = (d8 < 0.0d ? Math.sqrt(Math.abs(d8)) : 0.0d) * 0.5d;
            double d11 = (d9 - dSqrt2) * 0.5d;
            if (d6 == 0.0d && d5 == 0.0d) {
                j = 0;
            } else {
                if (d6 < 0.0d) {
                    d5 = -d5;
                }
                double dAbs = Math.abs(d6);
                double dAbs2 = Double.MAX_VALUE;
                if (d4 > 1.0d) {
                    double d12 = (d10 * dAbs) - d5;
                    double d13 = d10 - d11;
                    double d14 = d12 / d13;
                    double d15 = dAbs - d14;
                    dLog = Math.log(Math.abs(d7 / d15)) / d10;
                    double dLog2 = Math.log(Math.abs(d7 / d14)) / d11;
                    if ((Double.doubleToRawLongBits(dLog) & Long.MAX_VALUE) >= 9218868437227405312L) {
                        dLog = dLog2;
                    } else if ((Double.doubleToRawLongBits(dLog2) & Long.MAX_VALUE) < 9218868437227405312L) {
                        dLog = Math.max(dLog, dLog2);
                    }
                    double d16 = d15 * d10;
                    double dLog3 = Math.log(d16 / ((-d14) * d11)) / (d11 - d10);
                    if (Double.isNaN(dLog3) || dLog3 <= 0.0d) {
                        d7 = -d7;
                        d = d14 * d11;
                        if (Math.abs((Math.exp(d11 * dLog) * d) + (Math.exp(d10 * dLog) * d16)) >= 1.0E-4d) {
                            int i = 0;
                            while (dAbs2 > 0.001d && i < 100) {
                                i++;
                                double d17 = d10 * dLog;
                                double d18 = d11 * dLog;
                                double dExp = dLog - ((((Math.exp(d18) * d14) + (Math.exp(d17) * d15)) + d7) / ((Math.exp(d18) * d) + (Math.exp(d17) * d16)));
                                dAbs2 = Math.abs(dLog - dExp);
                                dLog = dExp;
                            }
                        }
                    } else {
                        if (dLog3 > 0.0d) {
                            if ((-((Math.exp(dLog3 * d11) * d14) + (Math.exp(d10 * dLog3) * d15))) < d7) {
                                if (d14 > 0.0d && d15 < 0.0d) {
                                    dLog = 0.0d;
                                }
                                d7 = -d7;
                                d = d14 * d11;
                                if (Math.abs((Math.exp(d11 * dLog) * d) + (Math.exp(d10 * dLog) * d16)) >= 1.0E-4d) {
                                }
                            }
                        }
                        dLog = Math.log((-((d14 * d11) * d11)) / (d16 * d10)) / d13;
                        d = d14 * d11;
                        if (Math.abs((Math.exp(d11 * dLog) * d) + (Math.exp(d10 * dLog) * d16)) >= 1.0E-4d) {
                        }
                    }
                } else if (d4 < 1.0d) {
                    double d19 = (d5 - (d10 * dAbs)) / dSqrt3;
                    dLog = Math.log(d7 / Math.sqrt((d19 * d19) + (dAbs * dAbs))) / d10;
                } else {
                    double d20 = d10 * dAbs;
                    double d21 = d5 - d20;
                    double dLog4 = Math.log(Math.abs(d7 / dAbs)) / d10;
                    double dLog5 = Math.log(Math.abs(d7 / d21));
                    double dLog6 = dLog5;
                    for (int i2 = 0; i2 < 6; i2++) {
                        dLog6 = dLog5 - Math.log(Math.abs(dLog6 / d10));
                    }
                    double d22 = dLog6 / d10;
                    if ((Double.doubleToRawLongBits(dLog4) & Long.MAX_VALUE) >= 9218868437227405312L) {
                        dLog4 = d22;
                    } else if ((Double.doubleToRawLongBits(d22) & Long.MAX_VALUE) < 9218868437227405312L) {
                        dLog4 = Math.max(dLog4, d22);
                    }
                    double d23 = (-(d20 + d21)) / (d10 * d21);
                    double d24 = d10 * d23;
                    double dExp2 = (Math.exp(d24) * d21 * d23) + (Math.exp(d24) * dAbs);
                    if (Double.isNaN(d23) || d23 <= 0.0d) {
                        d7 = -d7;
                    } else if (d23 <= 0.0d || (-dExp2) >= d7) {
                        dLog4 = (-(2.0d / d10)) - (dAbs / d21);
                    } else {
                        d7 = -d7;
                        dLog4 = (d21 >= 0.0d || dAbs <= 0.0d) ? dLog4 : 0.0d;
                    }
                    dLog = dLog4;
                    int i3 = 0;
                    while (dAbs2 > 0.001d && i3 < 100) {
                        i3++;
                        double d25 = d10 * dLog;
                        double dExp3 = dLog - (((Math.exp(d25) * ((d21 * dLog) + dAbs)) + d7) / (Math.exp(d25) * (((1 + d25) * d21) + d20)));
                        dAbs2 = Math.abs(dLog - dExp3);
                        dLog = dExp3;
                    }
                }
                j = (long) (dLog * 1000.0d);
            }
        }
        return j * 1000000;
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

package androidx.compose.ui.graphics;

import android.graphics.SweepGradient;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class GradientColorLongVerifier {
    public static final GradientColorLongVerifier INSTANCE = new GradientColorLongVerifier();

    private GradientColorLongVerifier() {
    }

    /* renamed from: createLinearGradientColorLong-VjE6UOU, reason: not valid java name */
    public final android.graphics.LinearGradient m473createLinearGradientColorLongVjE6UOU(long j, long j2, long[] jArr, float[] fArr, int i) {
        return new android.graphics.LinearGradient(Float.intBitsToFloat((int) (j >> 32)), Float.intBitsToFloat((int) (j & 4294967295L)), Float.intBitsToFloat((int) (j2 >> 32)), Float.intBitsToFloat((int) (j2 & 4294967295L)), jArr, fArr, AndroidTileMode_androidKt.m447toAndroidTileMode0vamqd0(i));
    }

    /* renamed from: createRadialGradientColorLong-8uybcMk, reason: not valid java name */
    public final android.graphics.RadialGradient m474createRadialGradientColorLong8uybcMk(long j, float f, long[] jArr, float[] fArr, int i) {
        return new android.graphics.RadialGradient(Float.intBitsToFloat((int) (j >> 32)), Float.intBitsToFloat((int) (j & 4294967295L)), f, jArr, fArr, AndroidTileMode_androidKt.m447toAndroidTileMode0vamqd0(i));
    }

    /* renamed from: createSweepGradientColorLong-9KIMszo, reason: not valid java name */
    public final SweepGradient m475createSweepGradientColorLong9KIMszo(long j, long[] jArr, float[] fArr) {
        return new SweepGradient(Float.intBitsToFloat((int) (j >> 32)), Float.intBitsToFloat((int) (j & 4294967295L)), jArr, fArr);
    }
}

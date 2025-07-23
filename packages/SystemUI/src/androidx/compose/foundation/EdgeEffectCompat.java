package androidx.compose.foundation;

import android.widget.EdgeEffect;
import androidx.compose.ui.unit.Density;
import kotlin.math.MathKt__MathJVMKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class EdgeEffectCompat {
    public static final EdgeEffectCompat INSTANCE = new EdgeEffectCompat();

    private EdgeEffectCompat() {
    }

    public static float absorbToRelaxIfNeeded(EdgeEffect edgeEffect, float f, float f2, Density density) {
        float f3 = EdgeEffectCompat_androidKt.PlatformFlingScrollFriction;
        double density2 = density.getDensity() * 386.0878f * 160.0f * 0.84f;
        double d = EdgeEffectCompat_androidKt.PlatformFlingScrollFriction * density2;
        if (((float) (Math.exp((EdgeEffectCompat_androidKt.DecelerationRate / EdgeEffectCompat_androidKt.DecelMinusOne) * Math.log((Math.abs(f) * 0.35f) / d)) * d)) > getDistanceCompat(edgeEffect) * f2) {
            return 0.0f;
        }
        edgeEffect.onAbsorb(MathKt__MathJVMKt.roundToInt(f));
        return f;
    }

    public static float getDistanceCompat(EdgeEffect edgeEffect) {
        Api31Impl.INSTANCE.getClass();
        try {
            return edgeEffect.getDistance();
        } catch (Throwable unused) {
            return 0.0f;
        }
    }

    public static float onPullDistanceCompat(EdgeEffect edgeEffect, float f, float f2) {
        Api31Impl.INSTANCE.getClass();
        try {
            return edgeEffect.onPullDistance(f, f2);
        } catch (Throwable unused) {
            edgeEffect.onPull(f, f2);
            return 0.0f;
        }
    }

    public static void onReleaseWithOppositeDelta(EdgeEffect edgeEffect, float f) {
        if (!(edgeEffect instanceof GlowEdgeEffectCompat)) {
            edgeEffect.onRelease();
            return;
        }
        GlowEdgeEffectCompat glowEdgeEffectCompat = (GlowEdgeEffectCompat) edgeEffect;
        float f2 = glowEdgeEffectCompat.oppositeReleaseDelta + f;
        glowEdgeEffectCompat.oppositeReleaseDelta = f2;
        if (Math.abs(f2) > glowEdgeEffectCompat.oppositeReleaseDeltaThreshold) {
            glowEdgeEffectCompat.onRelease();
        }
    }
}

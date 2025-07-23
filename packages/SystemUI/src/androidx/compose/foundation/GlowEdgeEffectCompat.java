package androidx.compose.foundation;

import android.content.Context;
import android.widget.EdgeEffect;
import androidx.compose.ui.unit.AndroidDensity_androidKt;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class GlowEdgeEffectCompat extends EdgeEffect {
    public float oppositeReleaseDelta;
    public final float oppositeReleaseDeltaThreshold;

    public GlowEdgeEffectCompat(Context context) {
        super(context);
        Density Density = AndroidDensity_androidKt.Density(context);
        Dp.Companion companion = Dp.Companion;
        this.oppositeReleaseDeltaThreshold = Density.getDensity() * 1;
    }

    @Override // android.widget.EdgeEffect
    public final void onAbsorb(int i) {
        this.oppositeReleaseDelta = 0.0f;
        super.onAbsorb(i);
    }

    @Override // android.widget.EdgeEffect
    public final void onPull(float f, float f2) {
        this.oppositeReleaseDelta = 0.0f;
        super.onPull(f, f2);
    }

    @Override // android.widget.EdgeEffect
    public final void onRelease() {
        this.oppositeReleaseDelta = 0.0f;
        super.onRelease();
    }

    @Override // android.widget.EdgeEffect
    public final void onPull(float f) {
        this.oppositeReleaseDelta = 0.0f;
        super.onPull(f);
    }
}

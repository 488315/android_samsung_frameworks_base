package androidx.compose.ui.unit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class DensityKt {
    public static final Density Density(float f, float f2) {
        return new DensityImpl(f, f2);
    }

    public static Density Density$default(float f) {
        return new DensityImpl(f, 1.0f);
    }
}

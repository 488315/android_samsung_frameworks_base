package androidx.compose.ui.unit;

/* loaded from: classes.dex */
public abstract class DensityKt {
    public static final Density Density(float f, float f2) {
        return new DensityImpl(f, f2);
    }

    public static Density Density$default(float f) {
        return new DensityImpl(f, 1.0f);
    }
}

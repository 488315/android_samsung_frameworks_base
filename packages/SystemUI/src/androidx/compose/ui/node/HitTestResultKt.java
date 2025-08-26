package androidx.compose.ui.node;

/* loaded from: classes.dex */
public abstract class HitTestResultKt {
    public static final long DistanceAndFlags(float f, boolean z, boolean z2) {
        return (((z ? 1L : 0L) | (z2 ? 2L : 0L)) & 4294967295L) | (Float.floatToRawIntBits(f) << 32);
    }
}

package androidx.compose.ui.node;

/* loaded from: classes.dex */
public final class DistanceAndFlags {
    public final long packedValue;

    /* renamed from: compareTo-9YPOF3E, reason: not valid java name */
    public static final int m635compareTo9YPOF3E(long j, long j2) {
        boolean zM638isInLayerimpl = m638isInLayerimpl(j);
        if (zM638isInLayerimpl != m638isInLayerimpl(j2)) {
            return zM638isInLayerimpl ? -1 : 1;
        }
        return (Math.min(m636getDistanceimpl(j), m636getDistanceimpl(j2)) >= 0.0f && m637isInExpandedBoundsimpl(j) != m637isInExpandedBoundsimpl(j2)) ? m637isInExpandedBoundsimpl(j) ? -1 : 1 : (int) Math.signum(m636getDistanceimpl(j) - m636getDistanceimpl(j2));
    }

    /* renamed from: getDistance-impl, reason: not valid java name */
    public static final float m636getDistanceimpl(long j) {
        return Float.intBitsToFloat((int) (j >> 32));
    }

    /* renamed from: isInExpandedBounds-impl, reason: not valid java name */
    public static final boolean m637isInExpandedBoundsimpl(long j) {
        return (j & 2) != 0;
    }

    /* renamed from: isInLayer-impl, reason: not valid java name */
    public static final boolean m638isInLayerimpl(long j) {
        return (j & 1) != 0;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof DistanceAndFlags) {
            return this.packedValue == ((DistanceAndFlags) obj).packedValue;
        }
        return false;
    }

    public final int hashCode() {
        return Long.hashCode(this.packedValue);
    }

    public final String toString() {
        return "DistanceAndFlags(packedValue=" + this.packedValue + ')';
    }
}

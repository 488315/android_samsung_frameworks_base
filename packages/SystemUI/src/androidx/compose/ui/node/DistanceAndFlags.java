package androidx.compose.ui.node;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class DistanceAndFlags {
    public final long packedValue;

    /* renamed from: compareTo-9YPOF3E, reason: not valid java name */
    public static final int m633compareTo9YPOF3E(long j, long j2) {
        boolean m636isInLayerimpl = m636isInLayerimpl(j);
        if (m636isInLayerimpl != m636isInLayerimpl(j2)) {
            return m636isInLayerimpl ? -1 : 1;
        }
        return (Math.min(m634getDistanceimpl(j), m634getDistanceimpl(j2)) >= 0.0f && m635isInExpandedBoundsimpl(j) != m635isInExpandedBoundsimpl(j2)) ? m635isInExpandedBoundsimpl(j) ? -1 : 1 : (int) Math.signum(m634getDistanceimpl(j) - m634getDistanceimpl(j2));
    }

    /* renamed from: getDistance-impl, reason: not valid java name */
    public static final float m634getDistanceimpl(long j) {
        return Float.intBitsToFloat((int) (j >> 32));
    }

    /* renamed from: isInExpandedBounds-impl, reason: not valid java name */
    public static final boolean m635isInExpandedBoundsimpl(long j) {
        return (j & 2) != 0;
    }

    /* renamed from: isInLayer-impl, reason: not valid java name */
    public static final boolean m636isInLayerimpl(long j) {
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

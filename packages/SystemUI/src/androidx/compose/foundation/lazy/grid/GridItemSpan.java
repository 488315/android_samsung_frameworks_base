package androidx.compose.foundation.lazy.grid;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class GridItemSpan {
    public final long packedValue;

    private /* synthetic */ GridItemSpan(long j) {
        this.packedValue = j;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ GridItemSpan m157boximpl(long j) {
        return new GridItemSpan(j);
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m158toStringimpl(long j) {
        return "GridItemSpan(packedValue=" + j + ')';
    }

    public final boolean equals(Object obj) {
        if (obj instanceof GridItemSpan) {
            return this.packedValue == ((GridItemSpan) obj).packedValue;
        }
        return false;
    }

    public final int hashCode() {
        return Long.hashCode(this.packedValue);
    }

    public final String toString() {
        return m158toStringimpl(this.packedValue);
    }
}

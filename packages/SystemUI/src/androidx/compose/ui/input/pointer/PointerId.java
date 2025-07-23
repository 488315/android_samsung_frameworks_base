package androidx.compose.ui.input.pointer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PointerId {
    public final long value;

    private /* synthetic */ PointerId(long j) {
        this.value = j;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ PointerId m590boximpl(long j) {
        return new PointerId(j);
    }

    /* renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m591equalsimpl0(long j, long j2) {
        return j == j2;
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m592toStringimpl(long j) {
        return "PointerId(value=" + j + ')';
    }

    public final boolean equals(Object obj) {
        if (obj instanceof PointerId) {
            return this.value == ((PointerId) obj).value;
        }
        return false;
    }

    public final int hashCode() {
        return Long.hashCode(this.value);
    }

    public final String toString() {
        return m592toStringimpl(this.value);
    }
}

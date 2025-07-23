package androidx.compose.ui.unit;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class IntSize {
    public static final Companion Companion = new Companion(null);
    public final long packedValue;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    private /* synthetic */ IntSize(long j) {
        this.packedValue = j;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ IntSize m859boximpl(long j) {
        return new IntSize(j);
    }

    /* renamed from: equals-impl, reason: not valid java name */
    public static boolean m860equalsimpl(long j, Object obj) {
        return (obj instanceof IntSize) && j == ((IntSize) obj).packedValue;
    }

    /* renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m861equalsimpl0(long j, long j2) {
        return j == j2;
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m862toStringimpl(long j) {
        return ((int) (j >> 32)) + " x " + ((int) (j & 4294967295L));
    }

    public final boolean equals(Object obj) {
        return m860equalsimpl(this.packedValue, obj);
    }

    public final int hashCode() {
        return Long.hashCode(this.packedValue);
    }

    public final String toString() {
        return m862toStringimpl(this.packedValue);
    }
}

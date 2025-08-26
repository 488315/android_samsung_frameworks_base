package androidx.compose.ui.unit;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class IntSize {
    public static final Companion Companion = new Companion(null);
    public final long packedValue;

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
    public static final /* synthetic */ IntSize m861boximpl(long j) {
        return new IntSize(j);
    }

    /* renamed from: equals-impl, reason: not valid java name */
    public static boolean m862equalsimpl(long j, Object obj) {
        return (obj instanceof IntSize) && j == ((IntSize) obj).packedValue;
    }

    /* renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m863equalsimpl0(long j, long j2) {
        return j == j2;
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m864toStringimpl(long j) {
        return ((int) (j >> 32)) + " x " + ((int) (j & 4294967295L));
    }

    public final boolean equals(Object obj) {
        return m862equalsimpl(this.packedValue, obj);
    }

    public final int hashCode() {
        return Long.hashCode(this.packedValue);
    }

    public final String toString() {
        return m864toStringimpl(this.packedValue);
    }
}

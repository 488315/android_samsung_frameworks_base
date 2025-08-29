package kotlin;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.CharsKt__CharJVMKt;

/* loaded from: classes4.dex */
public final class ULong implements Comparable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final long data;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    private /* synthetic */ ULong(long j) {
        this.data = j;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ ULong m3445boximpl(long j) {
        return new ULong(j);
    }

    /* renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m3446equalsimpl0(long j, long j2) {
        return j == j2;
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m3447toStringimpl(long j) {
        if (j >= 0) {
            CharsKt__CharJVMKt.checkRadix(10);
            return Long.toString(j, 10);
        }
        long j2 = 10;
        long j3 = ((j >>> 1) / j2) << 1;
        long j4 = j - (j3 * j2);
        if (j4 >= j2) {
            j4 -= j2;
            j3++;
        }
        StringBuilder sb = new StringBuilder();
        CharsKt__CharJVMKt.checkRadix(10);
        sb.append(Long.toString(j3, 10));
        CharsKt__CharJVMKt.checkRadix(10);
        sb.append(Long.toString(j4, 10));
        return sb.toString();
    }

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        long j = ((ULong) obj).data;
        long j2 = this.data ^ Long.MIN_VALUE;
        long j3 = j ^ Long.MIN_VALUE;
        if (j2 < j3) {
            return -1;
        }
        return j2 == j3 ? 0 : 1;
    }

    public final boolean equals(Object obj) {
        return (obj instanceof ULong) && this.data == ((ULong) obj).data;
    }

    public final int hashCode() {
        return Long.hashCode(this.data);
    }

    public final String toString() {
        return m3447toStringimpl(this.data);
    }
}

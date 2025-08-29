package kotlin;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public final class UInt implements Comparable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final int data;

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

    private /* synthetic */ UInt(int i) {
        this.data = i;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ UInt m3444boximpl(int i) {
        return new UInt(i);
    }

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        return Intrinsics.compare(this.data ^ Integer.MIN_VALUE, ((UInt) obj).data ^ Integer.MIN_VALUE);
    }

    public final boolean equals(Object obj) {
        return (obj instanceof UInt) && this.data == ((UInt) obj).data;
    }

    public final int hashCode() {
        return Integer.hashCode(this.data);
    }

    public final String toString() {
        return String.valueOf(this.data & 4294967295L);
    }
}

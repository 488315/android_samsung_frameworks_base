package kotlin;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public final class UShort implements Comparable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final short data;

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

    private /* synthetic */ UShort(short s) {
        this.data = s;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ UShort m3448boximpl(short s) {
        return new UShort(s);
    }

    @Override // java.lang.Comparable
    public final /* synthetic */ int compareTo(Object obj) {
        return Intrinsics.compare(this.data & 65535, ((UShort) obj).data & 65535);
    }

    public final boolean equals(Object obj) {
        return (obj instanceof UShort) && this.data == ((UShort) obj).data;
    }

    public final int hashCode() {
        return Short.hashCode(this.data);
    }

    public final String toString() {
        return String.valueOf(this.data & 65535);
    }
}

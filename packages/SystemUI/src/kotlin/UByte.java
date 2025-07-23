package kotlin;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class UByte implements Comparable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final byte data;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    private /* synthetic */ UByte(byte b) {
        this.data = b;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ UByte m3424boximpl(byte b) {
        return new UByte(b);
    }

    @Override // java.lang.Comparable
    public final /* synthetic */ int compareTo(Object obj) {
        return Intrinsics.compare(this.data & 255, ((UByte) obj).data & 255);
    }

    public final boolean equals(Object obj) {
        return (obj instanceof UByte) && this.data == ((UByte) obj).data;
    }

    public final int hashCode() {
        return Byte.hashCode(this.data);
    }

    public final String toString() {
        return String.valueOf(this.data & 255);
    }
}

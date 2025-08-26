package kotlin.uuid;

import java.io.Serializable;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.Charsets;

/* loaded from: classes4.dex */
public final class Uuid implements Serializable {
    public static final Companion Companion = new Companion(null);
    public static final Uuid NIL = new Uuid(0, 0);
    private final long leastSignificantBits;
    private final long mostSignificantBits;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public Uuid(long j, long j2) {
        this.mostSignificantBits = j;
        this.leastSignificantBits = j2;
    }

    private final Object writeReplace() {
        return new UuidSerialized(this.mostSignificantBits, this.leastSignificantBits);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Uuid)) {
            return false;
        }
        Uuid uuid = (Uuid) obj;
        return this.mostSignificantBits == uuid.mostSignificantBits && this.leastSignificantBits == uuid.leastSignificantBits;
    }

    public final int hashCode() {
        long j = this.mostSignificantBits ^ this.leastSignificantBits;
        return ((int) (j >> 32)) ^ ((int) j);
    }

    public final String toString() {
        byte[] bArr = new byte[36];
        UuidKt__UuidKt.access$formatBytesInto(this.leastSignificantBits, bArr, 24, 6);
        bArr[23] = 45;
        UuidKt__UuidKt.access$formatBytesInto(this.leastSignificantBits >>> 48, bArr, 19, 2);
        bArr[18] = 45;
        UuidKt__UuidKt.access$formatBytesInto(this.mostSignificantBits, bArr, 14, 2);
        bArr[13] = 45;
        UuidKt__UuidKt.access$formatBytesInto(this.mostSignificantBits >>> 16, bArr, 9, 2);
        bArr[8] = 45;
        UuidKt__UuidKt.access$formatBytesInto(this.mostSignificantBits >>> 32, bArr, 0, 4);
        return new String(bArr, Charsets.UTF_8);
    }
}

package okio;

import kotlin.collections.ArraysKt___ArraysJvmKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class Segment {
    public final byte[] data;
    public int limit;
    public Segment next;
    public final boolean owner;
    public int pos;
    public Segment prev;
    public boolean shared;

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

    public Segment() {
        this.data = new byte[8192];
        this.owner = true;
        this.shared = false;
    }

    public final Segment pop() {
        Segment segment = this.next;
        if (segment == this) {
            segment = null;
        }
        Segment segment2 = this.prev;
        segment2.getClass();
        segment2.next = this.next;
        Segment segment3 = this.next;
        segment3.getClass();
        segment3.prev = this.prev;
        this.next = null;
        this.prev = null;
        return segment;
    }

    public final void push(Segment segment) {
        segment.prev = this;
        segment.next = this.next;
        Segment segment2 = this.next;
        segment2.getClass();
        segment2.prev = segment;
        this.next = segment;
    }

    public final Segment sharedCopy() {
        this.shared = true;
        return new Segment(this.data, this.pos, this.limit, true, false);
    }

    public final void writeTo(Segment segment, int i) {
        if (!segment.owner) {
            throw new IllegalStateException("only owner can write");
        }
        int i2 = segment.limit;
        int i3 = i2 + i;
        byte[] bArr = segment.data;
        if (i3 > 8192) {
            if (segment.shared) {
                throw new IllegalArgumentException();
            }
            int i4 = segment.pos;
            if (i3 - i4 > 8192) {
                throw new IllegalArgumentException();
            }
            ArraysKt___ArraysJvmKt.copyInto$default(bArr, i4, i2, bArr, 2);
            segment.limit -= segment.pos;
            segment.pos = 0;
        }
        int i5 = segment.limit;
        int i6 = this.pos;
        System.arraycopy(this.data, i6, bArr, i5, (i6 + i) - i6);
        segment.limit += i;
        this.pos += i;
    }

    public Segment(byte[] bArr, int i, int i2, boolean z, boolean z2) {
        this.data = bArr;
        this.pos = i;
        this.limit = i2;
        this.shared = z;
        this.owner = z2;
    }
}

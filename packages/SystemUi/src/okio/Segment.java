package okio;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class Segment {
    public final byte[] data;
    public int limit;
    public Segment next;
    public final boolean owner;
    public int pos;
    public Segment prev;
    public boolean shared;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
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
        Segment segment2 = segment != this ? segment : null;
        Segment segment3 = this.prev;
        if (segment3 == null) {
            Intrinsics.throwNpe();
            throw null;
        }
        segment3.next = segment;
        Segment segment4 = this.next;
        if (segment4 == null) {
            Intrinsics.throwNpe();
            throw null;
        }
        segment4.prev = segment3;
        this.next = null;
        this.prev = null;
        return segment2;
    }

    public final void push(Segment segment) {
        segment.prev = this;
        segment.next = this.next;
        Segment segment2 = this.next;
        if (segment2 == null) {
            Intrinsics.throwNpe();
            throw null;
        }
        segment2.prev = segment;
        this.next = segment;
    }

    public final Segment sharedCopy() {
        this.shared = true;
        return new Segment(this.data, this.pos, this.limit, true, false);
    }

    public final void writeTo(Segment segment, int i) {
        if (!segment.owner) {
            throw new IllegalStateException("only owner can write".toString());
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
            System.arraycopy(bArr, i4, bArr, 0, i2 - i4);
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

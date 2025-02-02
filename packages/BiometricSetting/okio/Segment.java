package okio;

import javax.annotation.Nullable;

/* loaded from: classes.dex */
final class Segment {
    final byte[] data;
    int limit;
    Segment next;
    boolean owner;
    int pos;
    Segment prev;
    boolean shared;

    Segment() {
        this.data = new byte[8192];
        this.owner = true;
        this.shared = false;
    }

    @Nullable
    public final Segment pop() {
        Segment segment = this.next;
        Segment segment2 = segment != this ? segment : null;
        Segment segment3 = this.prev;
        segment3.next = segment;
        this.next.prev = segment3;
        this.next = null;
        this.prev = null;
        return segment2;
    }

    public final void push(Segment segment) {
        segment.prev = this;
        segment.next = this.next;
        this.next.prev = segment;
        this.next = segment;
    }

    final Segment sharedCopy() {
        this.shared = true;
        return new Segment(this.data, this.pos, this.limit);
    }

    public final void writeTo(Segment segment, int i) {
        if (!segment.owner) {
            throw new IllegalArgumentException();
        }
        int i2 = segment.limit;
        int i3 = i2 + i;
        byte[] bArr = segment.data;
        if (i3 > 8192) {
            if (segment.shared) {
                throw new IllegalArgumentException();
            }
            int i4 = segment.pos;
            if ((i2 + i) - i4 > 8192) {
                throw new IllegalArgumentException();
            }
            System.arraycopy(bArr, i4, bArr, 0, i2 - i4);
            segment.limit -= segment.pos;
            segment.pos = 0;
        }
        System.arraycopy(this.data, this.pos, bArr, segment.limit, i);
        segment.limit += i;
        this.pos += i;
    }

    Segment(byte[] bArr, int i, int i2) {
        this.data = bArr;
        this.pos = i;
        this.limit = i2;
        this.shared = true;
        this.owner = false;
    }
}

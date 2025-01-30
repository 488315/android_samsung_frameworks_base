package androidx.collection;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CircularArray {
    public int capacityBitmask;
    public Object[] elements;
    public int head;
    public int tail;

    public CircularArray() {
        this(0, 1, null);
    }

    public final void doubleCapacity() {
        Object[] objArr = this.elements;
        int length = objArr.length;
        int i = this.head;
        int i2 = length - i;
        int i3 = length << 1;
        if (i3 < 0) {
            throw new RuntimeException("Max array capacity exceeded");
        }
        Object[] objArr2 = new Object[i3];
        System.arraycopy(objArr, i, objArr2, 0, length - i);
        System.arraycopy(this.elements, 0, objArr2, i2, this.head - 0);
        this.elements = objArr2;
        this.head = 0;
        this.tail = length;
        this.capacityBitmask = i3 - 1;
    }

    public final void removeFromEnd(int i) {
        if (i <= 0) {
            return;
        }
        if (i > size()) {
            int i2 = CollectionPlatformUtils.$r8$clinit;
            throw new ArrayIndexOutOfBoundsException();
        }
        int i3 = this.tail;
        int i4 = i < i3 ? i3 - i : 0;
        for (int i5 = i4; i5 < i3; i5++) {
            this.elements[i5] = null;
        }
        int i6 = this.tail;
        int i7 = i6 - i4;
        int i8 = i - i7;
        this.tail = i6 - i7;
        if (i8 > 0) {
            int length = this.elements.length;
            this.tail = length;
            int i9 = length - i8;
            for (int i10 = i9; i10 < length; i10++) {
                this.elements[i10] = null;
            }
            this.tail = i9;
        }
    }

    public final void removeFromStart(int i) {
        if (i <= 0) {
            return;
        }
        if (i > size()) {
            int i2 = CollectionPlatformUtils.$r8$clinit;
            throw new ArrayIndexOutOfBoundsException();
        }
        int length = this.elements.length;
        int i3 = this.head;
        if (i < length - i3) {
            length = i3 + i;
        }
        while (i3 < length) {
            this.elements[i3] = null;
            i3++;
        }
        int i4 = this.head;
        int i5 = length - i4;
        int i6 = i - i5;
        this.head = this.capacityBitmask & (i4 + i5);
        if (i6 > 0) {
            for (int i7 = 0; i7 < i6; i7++) {
                this.elements[i7] = null;
            }
            this.head = i6;
        }
    }

    public final int size() {
        return this.capacityBitmask & (this.tail - this.head);
    }

    public CircularArray(int i) {
        if (!(i >= 1)) {
            throw new IllegalArgumentException("capacity must be >= 1".toString());
        }
        if (!(i <= 1073741824)) {
            throw new IllegalArgumentException("capacity must be <= 2^30".toString());
        }
        i = Integer.bitCount(i) != 1 ? Integer.highestOneBit(i - 1) << 1 : i;
        this.capacityBitmask = i - 1;
        this.elements = new Object[i];
    }

    public /* synthetic */ CircularArray(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 8 : i);
    }
}

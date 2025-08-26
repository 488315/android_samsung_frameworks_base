package androidx.collection;

import androidx.collection.internal.RuntimeHelpersKt;
import kotlin.collections.ArraysKt___ArraysJvmKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class CircularIntArray {
    public int capacityBitmask;
    public int[] elements;
    public int tail;

    public CircularIntArray() {
        this(0, 1, null);
    }

    public final void addLast(int i) {
        int[] iArr = this.elements;
        int i2 = this.tail;
        iArr[i2] = i;
        int i3 = this.capacityBitmask & (i2 + 1);
        this.tail = i3;
        if (i3 == 0) {
            int length = iArr.length;
            int i4 = length << 1;
            if (i4 < 0) {
                throw new RuntimeException("Max array capacity exceeded");
            }
            int[] iArr2 = new int[i4];
            ArraysKt___ArraysJvmKt.copyInto(0, 0, length, iArr, iArr2);
            ArraysKt___ArraysJvmKt.copyInto(length, 0, 0, this.elements, iArr2);
            this.elements = iArr2;
            this.tail = length;
            this.capacityBitmask = i4 - 1;
        }
    }

    public CircularIntArray(int i) {
        if (!(i >= 1)) {
            RuntimeHelpersKt.throwIllegalArgumentException("capacity must be >= 1");
            throw null;
        }
        if (!(i <= 1073741824)) {
            RuntimeHelpersKt.throwIllegalArgumentException("capacity must be <= 2^30");
            throw null;
        }
        i = Integer.bitCount(i) != 1 ? Integer.highestOneBit(i - 1) << 1 : i;
        this.capacityBitmask = i - 1;
        this.elements = new int[i];
    }

    public /* synthetic */ CircularIntArray(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 8 : i);
    }
}

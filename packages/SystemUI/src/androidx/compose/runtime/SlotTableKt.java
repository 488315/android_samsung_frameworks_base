package androidx.compose.runtime;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class SlotTableKt {
    public static final int access$groupSize(int i, int[] iArr) {
        return iArr[(i * 5) + 3];
    }

    public static final int access$locationOf(ArrayList arrayList, int i, int i2) {
        int search = search(arrayList, i, i2);
        return search >= 0 ? search : -(search + 1);
    }

    public static final int access$slotAnchor(int i, int[] iArr) {
        int i2 = i * 5;
        return Integer.bitCount(iArr[i2 + 1] >> 28) + iArr[i2 + 4];
    }

    public static final void access$updateNodeCount(int i, int i2, int[] iArr) {
        if (i2 >= 0) {
        }
        int i3 = (i * 5) + 1;
        iArr[i3] = i2 | (iArr[i3] & (-67108864));
    }

    public static final int search(ArrayList arrayList, int i, int i2) {
        int size = arrayList.size() - 1;
        int i3 = 0;
        while (i3 <= size) {
            int i4 = (i3 + size) >>> 1;
            int i5 = ((Anchor) arrayList.get(i4)).location;
            if (i5 < 0) {
                i5 += i2;
            }
            int compare = Intrinsics.compare(i5, i);
            if (compare < 0) {
                i3 = i4 + 1;
            } else {
                if (compare <= 0) {
                    return i4;
                }
                size = i4 - 1;
            }
        }
        return -(i3 + 1);
    }

    public static final void throwConcurrentModificationException() {
        throw new ConcurrentModificationException();
    }
}

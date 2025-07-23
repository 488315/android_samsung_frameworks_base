package androidx.collection;

import androidx.collection.internal.ContainerHelpersKt;
import java.util.ConcurrentModificationException;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ArraySetKt {
    public static final int indexOf(ArraySet arraySet, Object obj, int i) {
        int i2 = arraySet._size;
        if (i2 == 0) {
            return -1;
        }
        try {
            int binarySearch = ContainerHelpersKt.binarySearch(i2, i, arraySet.hashes);
            if (binarySearch < 0 || Intrinsics.areEqual(obj, arraySet.array[binarySearch])) {
                return binarySearch;
            }
            int i3 = binarySearch + 1;
            while (i3 < i2 && arraySet.hashes[i3] == i) {
                if (Intrinsics.areEqual(obj, arraySet.array[i3])) {
                    return i3;
                }
                i3++;
            }
            for (int i4 = binarySearch - 1; i4 >= 0 && arraySet.hashes[i4] == i; i4--) {
                if (Intrinsics.areEqual(obj, arraySet.array[i4])) {
                    return i4;
                }
            }
            return ~i3;
        } catch (IndexOutOfBoundsException unused) {
            throw new ConcurrentModificationException();
        }
    }
}

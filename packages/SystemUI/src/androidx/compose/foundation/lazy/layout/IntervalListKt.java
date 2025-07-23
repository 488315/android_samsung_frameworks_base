package androidx.compose.foundation.lazy.layout;

import androidx.appcompat.widget.AbsActionBarView$$ExternalSyntheticOutline0;
import androidx.compose.runtime.collection.MutableVector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class IntervalListKt {
    public static final int access$binarySearch(int i, MutableVector mutableVector) {
        int i2 = mutableVector.size - 1;
        int i3 = 0;
        while (i3 < i2) {
            int m = AbsActionBarView$$ExternalSyntheticOutline0.m(i2, i3, 2, i3);
            Object[] objArr = mutableVector.content;
            int i4 = ((IntervalList$Interval) objArr[m]).startIndex;
            if (i4 != i) {
                if (i4 < i) {
                    i3 = m + 1;
                    if (i < ((IntervalList$Interval) objArr[i3]).startIndex) {
                    }
                } else {
                    i2 = m - 1;
                }
            }
            return m;
        }
        return i3;
    }
}

package androidx.compose.foundation.lazy.layout;

import androidx.appcompat.widget.AbsActionBarView$$ExternalSyntheticOutline0;
import androidx.compose.runtime.collection.MutableVector;

/* loaded from: classes.dex */
public abstract class IntervalListKt {
    public static final int access$binarySearch(int i, MutableVector mutableVector) {
        int i2 = mutableVector.size - 1;
        int i3 = 0;
        while (i3 < i2) {
            int iM = AbsActionBarView$$ExternalSyntheticOutline0.m(i2, i3, 2, i3);
            Object[] objArr = mutableVector.content;
            int i4 = ((IntervalList$Interval) objArr[iM]).startIndex;
            if (i4 != i) {
                if (i4 < i) {
                    i3 = iM + 1;
                    if (i < ((IntervalList$Interval) objArr[i3]).startIndex) {
                    }
                } else {
                    i2 = iM - 1;
                }
            }
            return iM;
        }
        return i3;
    }
}

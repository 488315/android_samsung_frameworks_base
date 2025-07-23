package androidx.compose.foundation.lazy.layout;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.collection.MutableObjectIntMap;
import androidx.collection.ObjectIntMapKt;
import androidx.compose.foundation.internal.InlineClassHelperKt;
import androidx.compose.runtime.collection.MutableVector;
import kotlin.jvm.functions.Function1;
import kotlin.ranges.IntRange;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class NearestRangeKeyIndexMap implements LazyLayoutKeyIndexMap {
    public final Object[] keys;
    public final int keysStartIndex;
    public final MutableObjectIntMap map;

    public NearestRangeKeyIndexMap(IntRange intRange, LazyLayoutIntervalContent<?> lazyLayoutIntervalContent) {
        MutableIntervalList intervals$1 = lazyLayoutIntervalContent.getIntervals$1();
        final int i = intRange.first;
        if (i < 0) {
            InlineClassHelperKt.throwIllegalStateException("negative nearestRange.first");
        }
        final int min = Math.min(intRange.last, intervals$1.size - 1);
        if (min < i) {
            this.map = ObjectIntMapKt.EmptyObjectIntMap;
            this.keys = new Object[0];
            this.keysStartIndex = 0;
            return;
        }
        int i2 = (min - i) + 1;
        this.keys = new Object[i2];
        this.keysStartIndex = i;
        final MutableObjectIntMap mutableObjectIntMap = new MutableObjectIntMap(i2);
        Function1 function1 = new Function1() { // from class: androidx.compose.foundation.lazy.layout.NearestRangeKeyIndexMap$2$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            /* JADX WARN: Code restructure failed: missing block: B:5:0x002b, code lost:
            
                if (r3 == null) goto L7;
             */
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Object mo779invoke(java.lang.Object r7) {
                /*
                    r6 = this;
                    androidx.compose.foundation.lazy.layout.IntervalList$Interval r7 = (androidx.compose.foundation.lazy.layout.IntervalList$Interval) r7
                    java.lang.Object r0 = r7.value
                    androidx.compose.foundation.lazy.layout.LazyLayoutIntervalContent$Interval r0 = (androidx.compose.foundation.lazy.layout.LazyLayoutIntervalContent.Interval) r0
                    kotlin.jvm.functions.Function1 r0 = r0.getKey()
                    int r1 = r1
                    int r2 = r7.startIndex
                    int r1 = java.lang.Math.max(r1, r2)
                    int r3 = r2
                    int r7 = r7.size
                    int r7 = r7 + r2
                    int r7 = r7 + (-1)
                    int r7 = java.lang.Math.min(r3, r7)
                    if (r1 > r7) goto L46
                L1f:
                    if (r0 == 0) goto L2d
                    int r3 = r1 - r2
                    java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
                    java.lang.Object r3 = r0.mo779invoke(r3)
                    if (r3 != 0) goto L32
                L2d:
                    androidx.compose.foundation.lazy.layout.DefaultLazyKey r3 = new androidx.compose.foundation.lazy.layout.DefaultLazyKey
                    r3.<init>(r1)
                L32:
                    androidx.collection.MutableObjectIntMap r4 = r3
                    r4.set(r1, r3)
                    androidx.compose.foundation.lazy.layout.NearestRangeKeyIndexMap r4 = r4
                    java.lang.Object[] r5 = r4.keys
                    int r4 = r4.keysStartIndex
                    int r4 = r1 - r4
                    r5[r4] = r3
                    if (r1 == r7) goto L46
                    int r1 = r1 + 1
                    goto L1f
                L46:
                    kotlin.Unit r6 = kotlin.Unit.INSTANCE
                    return r6
                */
                throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.lazy.layout.NearestRangeKeyIndexMap$2$1.mo779invoke(java.lang.Object):java.lang.Object");
            }
        };
        if (i < 0 || i >= intervals$1.size) {
            StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Index ", ", size ");
            m.append(intervals$1.size);
            InlineClassHelperKt.throwIndexOutOfBoundsException(m.toString());
        }
        if (min < 0 || min >= intervals$1.size) {
            StringBuilder m2 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(min, "Index ", ", size ");
            m2.append(intervals$1.size);
            InlineClassHelperKt.throwIndexOutOfBoundsException(m2.toString());
        }
        if (min < i) {
            InlineClassHelperKt.throwIllegalArgumentException("toIndex (" + min + ") should be not smaller than fromIndex (" + i + ')');
        }
        MutableVector mutableVector = intervals$1.intervals;
        int access$binarySearch = IntervalListKt.access$binarySearch(i, mutableVector);
        int i3 = ((IntervalList$Interval) mutableVector.content[access$binarySearch]).startIndex;
        while (i3 <= min) {
            IntervalList$Interval intervalList$Interval = (IntervalList$Interval) mutableVector.content[access$binarySearch];
            function1.mo779invoke(intervalList$Interval);
            i3 += intervalList$Interval.size;
            access$binarySearch++;
        }
        this.map = mutableObjectIntMap;
    }

    public final int getIndex(Object obj) {
        MutableObjectIntMap mutableObjectIntMap = this.map;
        int findKeyIndex = mutableObjectIntMap.findKeyIndex(obj);
        if (findKeyIndex >= 0) {
            return mutableObjectIntMap.values[findKeyIndex];
        }
        return -1;
    }

    public final Object getKey(int i) {
        int i2 = i - this.keysStartIndex;
        if (i2 < 0) {
            return null;
        }
        Object[] objArr = this.keys;
        if (i2 <= objArr.length - 1) {
            return objArr[i2];
        }
        return null;
    }
}

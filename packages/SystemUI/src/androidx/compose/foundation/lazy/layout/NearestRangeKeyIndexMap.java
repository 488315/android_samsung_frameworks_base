package androidx.compose.foundation.lazy.layout;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.collection.MutableObjectIntMap;
import androidx.collection.ObjectIntMapKt;
import androidx.compose.foundation.internal.InlineClassHelperKt;
import androidx.compose.foundation.lazy.layout.LazyLayoutIntervalContent;
import androidx.compose.runtime.collection.MutableVector;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.ranges.IntRange;

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
        final int iMin = Math.min(intRange.last, intervals$1.size - 1);
        if (iMin < i) {
            this.map = ObjectIntMapKt.EmptyObjectIntMap;
            this.keys = new Object[0];
            this.keysStartIndex = 0;
            return;
        }
        int i2 = (iMin - i) + 1;
        this.keys = new Object[i2];
        this.keysStartIndex = i;
        final MutableObjectIntMap mutableObjectIntMap = new MutableObjectIntMap(i2);
        Function1 function1 = new Function1() { // from class: androidx.compose.foundation.lazy.layout.NearestRangeKeyIndexMap$2$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            /* JADX WARN: Removed duplicated region for block: B:10:0x0043 A[LOOP:0: B:4:0x001f->B:10:0x0043, LOOP_END] */
            /* JADX WARN: Removed duplicated region for block: B:13:0x0046 A[EDGE_INSN: B:13:0x0046->B:11:0x0046 BREAK  A[LOOP:0: B:4:0x001f->B:10:0x0043], SYNTHETIC] */
            /* JADX WARN: Removed duplicated region for block: B:7:0x002d  */
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object mo781invoke(Object obj) {
                Object defaultLazyKey;
                IntervalList$Interval intervalList$Interval = (IntervalList$Interval) obj;
                Function1 key = ((LazyLayoutIntervalContent.Interval) intervalList$Interval.value).getKey();
                int i3 = i;
                int i4 = intervalList$Interval.startIndex;
                int iMax = Math.max(i3, i4);
                int iMin2 = Math.min(iMin, (intervalList$Interval.size + i4) - 1);
                if (iMax <= iMin2) {
                    while (true) {
                        if (key == null) {
                            defaultLazyKey = new DefaultLazyKey(iMax);
                            mutableObjectIntMap.set(iMax, defaultLazyKey);
                            NearestRangeKeyIndexMap nearestRangeKeyIndexMap = this;
                            nearestRangeKeyIndexMap.keys[iMax - nearestRangeKeyIndexMap.keysStartIndex] = defaultLazyKey;
                            if (iMax != iMin2) {
                                break;
                            }
                            iMax++;
                        } else {
                            defaultLazyKey = key.mo781invoke(Integer.valueOf(iMax - i4));
                            if (defaultLazyKey == null) {
                            }
                            mutableObjectIntMap.set(iMax, defaultLazyKey);
                            NearestRangeKeyIndexMap nearestRangeKeyIndexMap2 = this;
                            nearestRangeKeyIndexMap2.keys[iMax - nearestRangeKeyIndexMap2.keysStartIndex] = defaultLazyKey;
                            if (iMax != iMin2) {
                            }
                        }
                    }
                }
                return Unit.INSTANCE;
            }
        };
        if (i < 0 || i >= intervals$1.size) {
            StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Index ", ", size ");
            sbM.append(intervals$1.size);
            InlineClassHelperKt.throwIndexOutOfBoundsException(sbM.toString());
        }
        if (iMin < 0 || iMin >= intervals$1.size) {
            StringBuilder sbM2 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(iMin, "Index ", ", size ");
            sbM2.append(intervals$1.size);
            InlineClassHelperKt.throwIndexOutOfBoundsException(sbM2.toString());
        }
        if (iMin < i) {
            InlineClassHelperKt.throwIllegalArgumentException("toIndex (" + iMin + ") should be not smaller than fromIndex (" + i + ')');
        }
        MutableVector mutableVector = intervals$1.intervals;
        int iAccess$binarySearch = IntervalListKt.access$binarySearch(i, mutableVector);
        int i3 = ((IntervalList$Interval) mutableVector.content[iAccess$binarySearch]).startIndex;
        while (i3 <= iMin) {
            IntervalList$Interval intervalList$Interval = (IntervalList$Interval) mutableVector.content[iAccess$binarySearch];
            function1.mo781invoke(intervalList$Interval);
            i3 += intervalList$Interval.size;
            iAccess$binarySearch++;
        }
        this.map = mutableObjectIntMap;
    }

    public final int getIndex(Object obj) {
        MutableObjectIntMap mutableObjectIntMap = this.map;
        int iFindKeyIndex = mutableObjectIntMap.findKeyIndex(obj);
        if (iFindKeyIndex >= 0) {
            return mutableObjectIntMap.values[iFindKeyIndex];
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

package androidx.compose.foundation.lazy.layout;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.compose.foundation.internal.InlineClassHelperKt;
import androidx.compose.foundation.lazy.layout.LazyLayoutIntervalContent;
import androidx.compose.runtime.collection.MutableVector;

/* loaded from: classes.dex */
public final class MutableIntervalList<T> {
    public final MutableVector intervals = new MutableVector(new IntervalList$Interval[16], 0);
    public IntervalList$Interval lastInterval;
    public int size;

    public final void addInterval(int i, LazyLayoutIntervalContent.Interval interval) {
        if (i < 0) {
            InlineClassHelperKt.throwIllegalArgumentException("size should be >=0");
        }
        if (i == 0) {
            return;
        }
        IntervalList$Interval intervalList$Interval = new IntervalList$Interval(this.size, i, interval);
        this.size += i;
        this.intervals.add(intervalList$Interval);
    }

    public final IntervalList$Interval get(int i) {
        if (i < 0 || i >= this.size) {
            StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Index ", ", size ");
            sbM.append(this.size);
            InlineClassHelperKt.throwIndexOutOfBoundsException(sbM.toString());
        }
        IntervalList$Interval intervalList$Interval = this.lastInterval;
        if (intervalList$Interval != null) {
            int i2 = intervalList$Interval.startIndex;
            if (i < intervalList$Interval.size + i2 && i2 <= i) {
                return intervalList$Interval;
            }
        }
        MutableVector mutableVector = this.intervals;
        IntervalList$Interval intervalList$Interval2 = (IntervalList$Interval) mutableVector.content[IntervalListKt.access$binarySearch(i, mutableVector)];
        this.lastInterval = intervalList$Interval2;
        return intervalList$Interval2;
    }
}

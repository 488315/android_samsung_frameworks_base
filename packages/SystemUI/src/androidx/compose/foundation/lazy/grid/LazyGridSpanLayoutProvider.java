package androidx.compose.foundation.lazy.grid;

import androidx.compose.foundation.internal.InlineClassHelperKt;
import androidx.compose.foundation.lazy.grid.LazyGridSpanLayoutProvider;
import androidx.compose.foundation.lazy.layout.IntervalList$Interval;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class LazyGridSpanLayoutProvider {
    public final ArrayList buckets;
    public final List cachedBucket;
    public int cachedBucketIndex;
    public final LazyGridIntervalContent gridContent;
    public int lastLineIndex;
    public int lastLineStartItemIndex;
    public int lastLineStartKnownSpan;
    public List previousDefaultSpans;
    public int slotsPerLine;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class LazyGridItemSpanScopeImpl implements LazyGridItemSpanScope {
        public static final LazyGridItemSpanScopeImpl INSTANCE = new LazyGridItemSpanScopeImpl();

        private LazyGridItemSpanScopeImpl() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class LineConfiguration {
        public final int firstItemIndex;
        public final List spans;

        public LineConfiguration(int i, List<GridItemSpan> list) {
            this.firstItemIndex = i;
            this.spans = list;
        }
    }

    public LazyGridSpanLayoutProvider(LazyGridIntervalContent lazyGridIntervalContent) {
        this.gridContent = lazyGridIntervalContent;
        ArrayList arrayList = new ArrayList();
        int i = 0;
        arrayList.add(new Bucket(i, i, 2, null));
        this.buckets = arrayList;
        this.cachedBucketIndex = -1;
        this.cachedBucket = new ArrayList();
        this.previousDefaultSpans = EmptyList.INSTANCE;
    }

    public final int getBucketSize() {
        return ((int) Math.sqrt((getTotalSize() * 1.0d) / this.slotsPerLine)) + 1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x00a6, code lost:
    
        if (r7 < r6) goto L34;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final androidx.compose.foundation.lazy.grid.LazyGridSpanLayoutProvider.LineConfiguration getLineConfiguration(int r11) {
        /*
            Method dump skipped, instructions count: 374
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.lazy.grid.LazyGridSpanLayoutProvider.getLineConfiguration(int):androidx.compose.foundation.lazy.grid.LazyGridSpanLayoutProvider$LineConfiguration");
    }

    public final int getLineIndexOfItem(final int i) {
        int i2;
        int i3 = 0;
        if (getTotalSize() <= 0) {
            return 0;
        }
        if (i >= getTotalSize()) {
            InlineClassHelperKt.throwIllegalArgumentException("ItemIndex > total count");
        }
        if (!this.gridContent.hasCustomSpans) {
            return i / this.slotsPerLine;
        }
        ArrayList arrayList = this.buckets;
        Function1 function1 = new Function1() { // from class: androidx.compose.foundation.lazy.grid.LazyGridSpanLayoutProvider$getLineIndexOfItem$lowerBoundBucket$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                return Integer.valueOf(((LazyGridSpanLayoutProvider.Bucket) obj).firstItemIndex - i);
            }
        };
        int size = arrayList.size();
        CollectionsKt__CollectionsKt.rangeCheck$CollectionsKt__CollectionsKt(arrayList.size(), size);
        int i4 = size - 1;
        int i5 = 0;
        while (true) {
            if (i5 > i4) {
                i2 = -(i5 + 1);
                break;
            }
            i2 = (i5 + i4) >>> 1;
            int intValue = ((Number) function1.mo779invoke(arrayList.get(i2))).intValue();
            if (intValue >= 0) {
                if (intValue <= 0) {
                    break;
                }
                i4 = i2 - 1;
            } else {
                i5 = i2 + 1;
            }
        }
        int i6 = 2;
        if (i2 < 0) {
            i2 = (-i2) - 2;
        }
        int bucketSize = getBucketSize() * i2;
        int i7 = ((Bucket) this.buckets.get(i2)).firstItemIndex;
        if (i7 > i) {
            InlineClassHelperKt.throwIllegalArgumentException("currentItemIndex > itemIndex");
        }
        int i8 = 0;
        while (true) {
            if (i7 >= i) {
                break;
            }
            int i9 = i7 + 1;
            int spanOf = spanOf(i7);
            i8 += spanOf;
            int i10 = this.slotsPerLine;
            if (i8 >= i10) {
                if (i8 == i10) {
                    bucketSize++;
                    i8 = 0;
                } else {
                    bucketSize++;
                    i8 = spanOf;
                }
            }
            if (bucketSize % getBucketSize() == 0 && bucketSize / getBucketSize() >= this.buckets.size()) {
                this.buckets.add(new Bucket(i9 - (i8 <= 0 ? 0 : 1), i3, i6, null));
            }
            i7 = i9;
        }
        return spanOf(i) + i8 > this.slotsPerLine ? bucketSize + 1 : bucketSize;
    }

    public final int getTotalSize() {
        return this.gridContent.intervals.size;
    }

    public final int spanOf(int i) {
        LazyGridItemSpanScopeImpl lazyGridItemSpanScopeImpl = LazyGridItemSpanScopeImpl.INSTANCE;
        lazyGridItemSpanScopeImpl.getClass();
        IntervalList$Interval intervalList$Interval = this.gridContent.intervals.get(i);
        return (int) ((GridItemSpan) ((LazyGridInterval) intervalList$Interval.value).span.invoke(lazyGridItemSpanScopeImpl, Integer.valueOf(i - intervalList$Interval.startIndex))).packedValue;
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class Bucket {
        public final int firstItemIndex;
        public final int firstItemKnownSpan;

        public Bucket(int i, int i2) {
            this.firstItemIndex = i;
            this.firstItemKnownSpan = i2;
        }

        public /* synthetic */ Bucket(int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
            this(i, (i3 & 2) != 0 ? 0 : i2);
        }
    }
}

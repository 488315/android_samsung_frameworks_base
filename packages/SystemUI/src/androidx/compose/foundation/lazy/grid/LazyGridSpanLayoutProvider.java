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

    final class LazyGridItemSpanScopeImpl implements LazyGridItemSpanScope {
        public static final LazyGridItemSpanScopeImpl INSTANCE = new LazyGridItemSpanScopeImpl();

        private LazyGridItemSpanScopeImpl() {
        }
    }

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

    /* JADX WARN: Removed duplicated region for block: B:33:0x00a9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final LineConfiguration getLineConfiguration(int i) {
        int i2;
        boolean z;
        int i3;
        int i4;
        List list;
        if (!this.gridContent.hasCustomSpans) {
            int i5 = this.slotsPerLine;
            int i6 = i * i5;
            int totalSize = getTotalSize() - i6;
            if (i5 > totalSize) {
                i5 = totalSize;
            }
            if (i5 < 0) {
                i5 = 0;
            }
            if (i5 == this.previousDefaultSpans.size()) {
                list = this.previousDefaultSpans;
            } else {
                ArrayList arrayList = new ArrayList(i5);
                for (int i7 = 0; i7 < i5; i7++) {
                    arrayList.add(GridItemSpan.m158boximpl(LazyGridSpanKt.GridItemSpan(1)));
                }
                this.previousDefaultSpans = arrayList;
                list = arrayList;
            }
            return new LineConfiguration(i6, list);
        }
        int iMin = Math.min(i / getBucketSize(), this.buckets.size() - 1);
        int bucketSize = getBucketSize() * iMin;
        int iIntValue = ((Bucket) this.buckets.get(iMin)).firstItemIndex;
        int iSpanOf = ((Bucket) this.buckets.get(iMin)).firstItemKnownSpan;
        int i8 = this.lastLineIndex;
        if (bucketSize <= i8 && i8 <= i) {
            iIntValue = this.lastLineStartItemIndex;
            iSpanOf = this.lastLineStartKnownSpan;
            bucketSize = i8;
        } else if (iMin == this.cachedBucketIndex && (i2 = i - bucketSize) < ((ArrayList) this.cachedBucket).size()) {
            iIntValue = ((Number) ((ArrayList) this.cachedBucket).get(i2)).intValue();
            bucketSize = i;
            iSpanOf = 0;
        }
        if (bucketSize % getBucketSize() == 0) {
            int i9 = i - bucketSize;
            z = 2 <= i9 && i9 < getBucketSize();
        }
        if (z) {
            this.cachedBucketIndex = iMin;
            ((ArrayList) this.cachedBucket).clear();
        }
        if (bucketSize > i) {
            InlineClassHelperKt.throwIllegalStateException("currentLine (" + bucketSize + ") > lineIndex (" + i + ')');
        }
        while (bucketSize < i && iIntValue < getTotalSize()) {
            if (z) {
                ((ArrayList) this.cachedBucket).add(Integer.valueOf(iIntValue));
            }
            int i10 = 0;
            while (i10 < this.slotsPerLine && iIntValue < getTotalSize()) {
                if (iSpanOf == 0) {
                    i4 = iSpanOf;
                    iSpanOf = spanOf(iIntValue);
                } else {
                    i4 = 0;
                }
                i10 += iSpanOf;
                if (i10 > this.slotsPerLine) {
                    break;
                }
                iIntValue++;
                iSpanOf = i4;
            }
            bucketSize++;
            if (bucketSize % getBucketSize() == 0 && iIntValue < getTotalSize()) {
                if (this.buckets.size() != bucketSize / getBucketSize()) {
                    InlineClassHelperKt.throwIllegalStateException("invalid starting point");
                }
                this.buckets.add(new Bucket(iIntValue, iSpanOf));
            }
        }
        this.lastLineIndex = i;
        this.lastLineStartItemIndex = iIntValue;
        this.lastLineStartKnownSpan = iSpanOf;
        ArrayList arrayList2 = new ArrayList();
        int i11 = 0;
        int i12 = iIntValue;
        while (i11 < this.slotsPerLine && i12 < getTotalSize()) {
            if (iSpanOf == 0) {
                int i13 = iSpanOf;
                iSpanOf = spanOf(i12);
                i3 = i13;
            } else {
                i3 = 0;
            }
            i11 += iSpanOf;
            if (i11 > this.slotsPerLine) {
                break;
            }
            i12++;
            arrayList2.add(GridItemSpan.m158boximpl(LazyGridSpanKt.GridItemSpan(iSpanOf)));
            iSpanOf = i3;
        }
        return new LineConfiguration(iIntValue, arrayList2);
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
            public final Object mo781invoke(Object obj) {
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
            int iIntValue = ((Number) function1.mo781invoke(arrayList.get(i2))).intValue();
            if (iIntValue >= 0) {
                if (iIntValue <= 0) {
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
            int iSpanOf = spanOf(i7);
            i8 += iSpanOf;
            int i10 = this.slotsPerLine;
            if (i8 >= i10) {
                if (i8 == i10) {
                    bucketSize++;
                    i8 = 0;
                } else {
                    bucketSize++;
                    i8 = iSpanOf;
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

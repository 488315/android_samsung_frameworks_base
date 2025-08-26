package androidx.compose.foundation.lazy;

import androidx.collection.IntIntMapKt;
import androidx.collection.IntObjectMapKt;
import androidx.collection.IntSetKt;
import androidx.collection.MutableIntIntMap;
import androidx.collection.MutableIntObjectMap;
import androidx.collection.MutableIntSet;
import androidx.compose.foundation.gestures.snapping.LazyListSnapLayoutInfoProviderKt;
import androidx.compose.foundation.lazy.layout.LazyLayoutCacheWindow;
import androidx.compose.foundation.lazy.layout.LazyLayoutPrefetchState;
import androidx.compose.foundation.lazy.layout.NestedPrefetchScope;
import androidx.compose.foundation.lazy.layout.PrefetchScheduler;
import androidx.compose.ui.unit.Density;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class CacheWindowListPrefetchStrategy implements LazyListPrefetchStrategy {
    public final MutableIntSet indicesToRemove;
    public final PrefetchScheduler prefetchScheduler;
    public int prefetchWindowEndIndex;
    public final MutableIntObjectMap prefetchWindowHandles;
    public int prefetchWindowStartIndex;
    public boolean shouldRefillWindow;
    public final MutableIntIntMap windowCache;

    public CacheWindowListPrefetchStrategy(LazyLayoutCacheWindow lazyLayoutCacheWindow, Density density, PrefetchScheduler prefetchScheduler) {
        this.prefetchScheduler = prefetchScheduler;
        this.prefetchWindowHandles = IntObjectMapKt.mutableIntObjectMapOf();
        int[] iArr = IntSetKt.EmptyIntArray;
        this.indicesToRemove = new MutableIntSet(0, 1, null);
        int i = IntIntMapKt.$r8$clinit;
        this.windowCache = new MutableIntIntMap(0, 1, null);
        this.prefetchWindowStartIndex = Integer.MAX_VALUE;
        this.prefetchWindowEndIndex = Integer.MIN_VALUE;
    }

    public static void updateCacheWindow(LazyListLayoutInfo lazyListLayoutInfo) {
        LazyListMeasureResult lazyListMeasureResult = (LazyListMeasureResult) lazyListLayoutInfo;
        if (lazyListMeasureResult.visibleItemsInfo.isEmpty()) {
            return;
        }
        LazyListItemInfo lazyListItemInfo = (LazyListItemInfo) CollectionsKt___CollectionsKt.first(lazyListMeasureResult.visibleItemsInfo);
        LazyListItemInfo lazyListItemInfo2 = (LazyListItemInfo) CollectionsKt___CollectionsKt.last(lazyListMeasureResult.visibleItemsInfo);
        LazyListSnapLayoutInfoProviderKt.getSingleAxisViewportSize(lazyListLayoutInfo);
        int offset = lazyListItemInfo.getOffset() + (-lazyListMeasureResult.viewportStartOffset);
        if (offset > 0) {
            offset = 0;
        }
        int size = lazyListItemInfo2.getSize() + lazyListItemInfo2.getOffset() + lazyListMeasureResult.mainAxisItemSpacing;
        Math.abs(offset);
        Math.abs(size - lazyListMeasureResult.viewportEndOffset);
        throw null;
    }

    @Override // androidx.compose.foundation.lazy.LazyListPrefetchStrategy
    public final PrefetchScheduler getPrefetchScheduler() {
        return this.prefetchScheduler;
    }

    @Override // androidx.compose.foundation.lazy.LazyListPrefetchStrategy
    public final void onScroll(LazyListState$prefetchScope$1 lazyListState$prefetchScope$1, float f, LazyListLayoutInfo lazyListLayoutInfo) {
        updateCacheWindow(lazyListLayoutInfo);
    }

    @Override // androidx.compose.foundation.lazy.LazyListPrefetchStrategy
    public final void onVisibleItemsUpdated(LazyListState$prefetchScope$1 lazyListState$prefetchScope$1, LazyListMeasureResult lazyListMeasureResult) {
        int i = lazyListMeasureResult.totalItemsCount;
        boolean zIsEmpty = lazyListMeasureResult.visibleItemsInfo.isEmpty();
        MutableIntObjectMap mutableIntObjectMap = this.prefetchWindowHandles;
        MutableIntIntMap mutableIntIntMap = this.windowCache;
        if (!zIsEmpty) {
            List list = lazyListMeasureResult.visibleItemsInfo;
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                LazyListItemInfo lazyListItemInfo = (LazyListItemInfo) list.get(i2);
                int index = lazyListItemInfo.getIndex();
                int size2 = lazyListItemInfo.getSize();
                if (mutableIntIntMap.findKeyIndex(index) >= 0 && mutableIntIntMap.get(index) != size2) {
                    this.shouldRefillWindow = true;
                }
                mutableIntIntMap.set(index, size2);
                this.prefetchWindowStartIndex = Math.min(this.prefetchWindowStartIndex, index);
                this.prefetchWindowEndIndex = Math.max(this.prefetchWindowEndIndex, index);
                LazyLayoutPrefetchState.PrefetchHandle prefetchHandle = (LazyLayoutPrefetchState.PrefetchHandle) mutableIntObjectMap.remove(index);
                if (prefetchHandle != null) {
                    prefetchHandle.cancel();
                }
            }
            if (this.shouldRefillWindow) {
                updateCacheWindow(lazyListMeasureResult);
                this.shouldRefillWindow = false;
                return;
            }
            return;
        }
        this.prefetchWindowStartIndex = Integer.MAX_VALUE;
        this.prefetchWindowEndIndex = Integer.MIN_VALUE;
        mutableIntIntMap.clear();
        long[] jArr = mutableIntObjectMap.metadata;
        int length = jArr.length - 2;
        if (length < 0) {
            return;
        }
        int i3 = 0;
        while (true) {
            long j = jArr[i3];
            if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                int i4 = 8 - ((~(i3 - length)) >>> 31);
                for (int i5 = 0; i5 < i4; i5++) {
                    if ((255 & j) < 128) {
                        int i6 = (i3 << 3) + i5;
                        int i7 = mutableIntObjectMap.keys[i6];
                        ((LazyLayoutPrefetchState.PrefetchHandle) mutableIntObjectMap.values[i6]).cancel();
                        mutableIntObjectMap.removeValueAt(i6);
                    }
                    j >>= 8;
                }
                if (i4 != 8) {
                    return;
                }
            }
            if (i3 == length) {
                return;
            } else {
                i3++;
            }
        }
    }

    public /* synthetic */ CacheWindowListPrefetchStrategy(LazyLayoutCacheWindow lazyLayoutCacheWindow, Density density, PrefetchScheduler prefetchScheduler, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(lazyLayoutCacheWindow, density, (i & 4) != 0 ? null : prefetchScheduler);
    }

    @Override // androidx.compose.foundation.lazy.LazyListPrefetchStrategy
    public final void onNestedPrefetch(NestedPrefetchScope nestedPrefetchScope, int i) {
    }
}

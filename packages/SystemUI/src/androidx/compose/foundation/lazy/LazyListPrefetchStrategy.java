package androidx.compose.foundation.lazy;

import androidx.compose.foundation.lazy.layout.NestedPrefetchScope;
import androidx.compose.foundation.lazy.layout.PrefetchScheduler;

/* loaded from: classes.dex */
public interface LazyListPrefetchStrategy {
    default PrefetchScheduler getPrefetchScheduler() {
        return null;
    }

    void onNestedPrefetch(NestedPrefetchScope nestedPrefetchScope, int i);

    void onScroll(LazyListState$prefetchScope$1 lazyListState$prefetchScope$1, float f, LazyListLayoutInfo lazyListLayoutInfo);

    void onVisibleItemsUpdated(LazyListState$prefetchScope$1 lazyListState$prefetchScope$1, LazyListMeasureResult lazyListMeasureResult);
}

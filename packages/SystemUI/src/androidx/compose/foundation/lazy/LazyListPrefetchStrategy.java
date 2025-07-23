package androidx.compose.foundation.lazy;

import androidx.compose.foundation.lazy.layout.NestedPrefetchScope;
import androidx.compose.foundation.lazy.layout.PrefetchScheduler;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface LazyListPrefetchStrategy {
    default PrefetchScheduler getPrefetchScheduler() {
        return null;
    }

    void onNestedPrefetch(NestedPrefetchScope nestedPrefetchScope, int i);

    void onScroll(LazyListState$prefetchScope$1 lazyListState$prefetchScope$1, float f, LazyListLayoutInfo lazyListLayoutInfo);

    void onVisibleItemsUpdated(LazyListState$prefetchScope$1 lazyListState$prefetchScope$1, LazyListMeasureResult lazyListMeasureResult);
}

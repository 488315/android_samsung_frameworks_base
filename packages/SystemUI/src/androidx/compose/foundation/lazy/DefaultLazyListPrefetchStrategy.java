package androidx.compose.foundation.lazy;

import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.lazy.layout.LazyLayoutPrefetchState;
import androidx.compose.foundation.lazy.layout.NestedPrefetchScope;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.snapshots.Snapshot;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class DefaultLazyListPrefetchStrategy implements LazyListPrefetchStrategy {
    public LazyLayoutPrefetchState.PrefetchHandle currentPrefetchHandle;
    public int indexToPrefetch;
    public final int nestedPrefetchItemCount;
    public boolean wasScrollingForward;

    public DefaultLazyListPrefetchStrategy() {
        this(0, 1, null);
    }

    @Override // androidx.compose.foundation.lazy.LazyListPrefetchStrategy
    public final void onNestedPrefetch(NestedPrefetchScope nestedPrefetchScope, int i) {
        for (int i2 = 0; i2 < this.nestedPrefetchItemCount; i2++) {
            nestedPrefetchScope.schedulePrefetch(i + i2);
        }
    }

    @Override // androidx.compose.foundation.lazy.LazyListPrefetchStrategy
    public final void onScroll(LazyListState$prefetchScope$1 lazyListState$prefetchScope$1, float f, LazyListLayoutInfo lazyListLayoutInfo) {
        LazyLayoutPrefetchState.PrefetchHandle prefetchHandle;
        LazyLayoutPrefetchState.PrefetchHandle prefetchHandle2;
        LazyLayoutPrefetchState.PrefetchHandle prefetchHandle3;
        LazyListMeasureResult lazyListMeasureResult = (LazyListMeasureResult) lazyListLayoutInfo;
        if (lazyListMeasureResult.visibleItemsInfo.isEmpty()) {
            return;
        }
        boolean z = f < 0.0f;
        int index = z ? ((LazyListItemInfo) CollectionsKt___CollectionsKt.last(lazyListMeasureResult.visibleItemsInfo)).getIndex() + 1 : ((LazyListItemInfo) CollectionsKt___CollectionsKt.first(lazyListMeasureResult.visibleItemsInfo)).getIndex() - 1;
        if (index < 0 || index >= lazyListMeasureResult.totalItemsCount) {
            return;
        }
        if (index != this.indexToPrefetch) {
            if (this.wasScrollingForward != z && (prefetchHandle3 = this.currentPrefetchHandle) != null) {
                prefetchHandle3.cancel();
            }
            this.wasScrollingForward = z;
            this.indexToPrefetch = index;
            final Function1 function1 = null;
            lazyListState$prefetchScope$1.getClass();
            Snapshot.Companion companion = Snapshot.Companion;
            LazyListState lazyListState = lazyListState$prefetchScope$1.this$0;
            companion.getClass();
            Snapshot currentThreadSnapshot = Snapshot.Companion.getCurrentThreadSnapshot();
            Function1 readObserver = currentThreadSnapshot != null ? currentThreadSnapshot.getReadObserver() : null;
            Snapshot makeCurrentNonObservable = Snapshot.Companion.makeCurrentNonObservable(currentThreadSnapshot);
            try {
                final LazyListMeasureResult lazyListMeasureResult2 = (LazyListMeasureResult) ((SnapshotMutableStateImpl) lazyListState.layoutInfoState).getValue();
                Snapshot.Companion.restoreNonObservable(currentThreadSnapshot, makeCurrentNonObservable, readObserver);
                this.currentPrefetchHandle = lazyListState.prefetchState.m170schedulePrefetchVKLhPVY(index, lazyListMeasureResult2.childConstraints, new Function1() { // from class: androidx.compose.foundation.lazy.LazyListState$prefetchScope$1$schedulePrefetch$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj) {
                        LazyLayoutPrefetchState.LazyLayoutPrefetchResultScope lazyLayoutPrefetchResultScope = (LazyLayoutPrefetchState.LazyLayoutPrefetchResultScope) obj;
                        if (Function1.this != null) {
                            int placeablesCount = lazyLayoutPrefetchResultScope.getPlaceablesCount();
                            LazyListMeasureResult lazyListMeasureResult3 = lazyListMeasureResult2;
                            int i = 0;
                            for (int i2 = 0; i2 < placeablesCount; i2++) {
                                i += (int) (lazyListMeasureResult3.orientation == Orientation.Vertical ? lazyLayoutPrefetchResultScope.mo171getSizeYEO4UFw(i2) & 4294967295L : lazyLayoutPrefetchResultScope.mo171getSizeYEO4UFw(i2) >> 32);
                            }
                            Function1.this.mo779invoke(Integer.valueOf(i));
                        }
                        return Unit.INSTANCE;
                    }
                });
            } catch (Throwable th) {
                Snapshot.Companion.restoreNonObservable(currentThreadSnapshot, makeCurrentNonObservable, readObserver);
                throw th;
            }
        }
        if (!z) {
            if (lazyListMeasureResult.viewportStartOffset - ((LazyListItemInfo) CollectionsKt___CollectionsKt.first(lazyListMeasureResult.visibleItemsInfo)).getOffset() >= f || (prefetchHandle = this.currentPrefetchHandle) == null) {
                return;
            }
            prefetchHandle.markAsUrgent();
            return;
        }
        LazyListItemInfo lazyListItemInfo = (LazyListItemInfo) CollectionsKt___CollectionsKt.last(lazyListMeasureResult.visibleItemsInfo);
        if (((lazyListItemInfo.getSize() + lazyListItemInfo.getOffset()) + lazyListMeasureResult.mainAxisItemSpacing) - lazyListMeasureResult.viewportEndOffset >= (-f) || (prefetchHandle2 = this.currentPrefetchHandle) == null) {
            return;
        }
        prefetchHandle2.markAsUrgent();
    }

    @Override // androidx.compose.foundation.lazy.LazyListPrefetchStrategy
    public final void onVisibleItemsUpdated(LazyListState$prefetchScope$1 lazyListState$prefetchScope$1, LazyListMeasureResult lazyListMeasureResult) {
        if (this.indexToPrefetch == -1 || lazyListMeasureResult.visibleItemsInfo.isEmpty()) {
            return;
        }
        if (this.indexToPrefetch != (this.wasScrollingForward ? ((LazyListItemInfo) CollectionsKt___CollectionsKt.last(lazyListMeasureResult.visibleItemsInfo)).getIndex() + 1 : ((LazyListItemInfo) CollectionsKt___CollectionsKt.first(lazyListMeasureResult.visibleItemsInfo)).getIndex() - 1)) {
            this.indexToPrefetch = -1;
            LazyLayoutPrefetchState.PrefetchHandle prefetchHandle = this.currentPrefetchHandle;
            if (prefetchHandle != null) {
                prefetchHandle.cancel();
            }
            this.currentPrefetchHandle = null;
        }
    }

    public DefaultLazyListPrefetchStrategy(int i) {
        this.nestedPrefetchItemCount = i;
        this.indexToPrefetch = -1;
    }

    public /* synthetic */ DefaultLazyListPrefetchStrategy(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 2 : i);
    }
}

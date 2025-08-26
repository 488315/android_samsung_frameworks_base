package androidx.compose.foundation.lazy.layout;

import androidx.compose.foundation.lazy.layout.PrefetchHandleProvider;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class LazyLayoutPrefetchState {
    public final Function1 onNestedPrefetch;
    public PrefetchHandleProvider prefetchHandleProvider;
    public final PrefetchMetrics prefetchMetrics;
    public final PrefetchScheduler prefetchScheduler;

    public interface LazyLayoutPrefetchResultScope {
        int getPlaceablesCount();

        /* renamed from: getSize-YEO4UFw, reason: not valid java name */
        long mo172getSizeYEO4UFw(int i);
    }

    final class NestedPrefetchScopeImpl implements NestedPrefetchScope {
        public final List _requests = new ArrayList();

        public NestedPrefetchScopeImpl() {
        }

        @Override // androidx.compose.foundation.lazy.layout.NestedPrefetchScope
        public final void schedulePrefetch(int i) {
            long j = LazyLayoutPrefetchStateKt.ZeroConstraints;
            LazyLayoutPrefetchState lazyLayoutPrefetchState = LazyLayoutPrefetchState.this;
            PrefetchHandleProvider prefetchHandleProvider = lazyLayoutPrefetchState.prefetchHandleProvider;
            if (prefetchHandleProvider == null) {
                return;
            }
            List list = this._requests;
            ArrayList arrayList = (ArrayList) list;
            arrayList.add(new PrefetchHandleProvider.HandleAndRequestImpl(prefetchHandleProvider, i, j, lazyLayoutPrefetchState.prefetchMetrics, null, null));
        }
    }

    public interface PrefetchHandle {
        void cancel();

        void markAsUrgent();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public LazyLayoutPrefetchState() {
        this(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
    }

    /* renamed from: schedulePrefetch-VKLhPVY, reason: not valid java name */
    public final PrefetchHandle m171schedulePrefetchVKLhPVY(int i, long j, Function1 function1) {
        PrefetchHandleProvider prefetchHandleProvider = this.prefetchHandleProvider;
        if (prefetchHandleProvider == null) {
            return DummyHandle.INSTANCE;
        }
        PrefetchHandleProvider.HandleAndRequestImpl handleAndRequestImpl = new PrefetchHandleProvider.HandleAndRequestImpl(prefetchHandleProvider, i, j, this.prefetchMetrics, function1, null);
        prefetchHandleProvider.executor.schedulePrefetch(handleAndRequestImpl);
        return handleAndRequestImpl;
    }

    public LazyLayoutPrefetchState(PrefetchScheduler prefetchScheduler, Function1 function1) {
        this.prefetchScheduler = prefetchScheduler;
        this.onNestedPrefetch = function1;
        this.prefetchMetrics = new PrefetchMetrics();
    }

    public /* synthetic */ LazyLayoutPrefetchState(PrefetchScheduler prefetchScheduler, Function1 function1, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : prefetchScheduler, (i & 2) != 0 ? null : function1);
    }
}

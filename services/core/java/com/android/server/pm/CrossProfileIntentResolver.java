package com.android.server.pm;

import android.content.IntentFilter;

import com.android.server.utils.SnapshotCache;

import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class CrossProfileIntentResolver extends WatchedIntentResolver {
    public final SnapshotCache mSnapshot;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.pm.CrossProfileIntentResolver$1, reason: invalid class name */
    public final class AnonymousClass1 extends SnapshotCache {
        @Override // com.android.server.utils.SnapshotCache
        public final Object createSnapshot() {
            return new CrossProfileIntentResolver((CrossProfileIntentResolver) this.mSource);
        }
    }

    public CrossProfileIntentResolver() {
        this.mSnapshot = new AnonymousClass1(this, this, null);
    }

    public CrossProfileIntentResolver(CrossProfileIntentResolver crossProfileIntentResolver) {
        copyFrom(crossProfileIntentResolver);
        this.mSnapshot = new SnapshotCache.Auto();
    }

    @Override // com.android.server.IntentResolver
    public final IntentFilter getIntentFilter(Object obj) {
        return ((CrossProfileIntentFilter) obj).mFilter;
    }

    @Override // com.android.server.IntentResolver
    public final boolean isPackageForFilter(String str, Object obj) {
        return (((CrossProfileIntentFilter) obj).mFlags & 8) != 0;
    }

    @Override // com.android.server.IntentResolver
    public final Object[] newArray(int i) {
        return new CrossProfileIntentFilter[i];
    }

    @Override // com.android.server.utils.Snappable
    public final Object snapshot() {
        return (CrossProfileIntentResolver) this.mSnapshot.snapshot();
    }

    @Override // com.android.server.IntentResolver
    public final Object snapshot(Object obj) {
        CrossProfileIntentFilter crossProfileIntentFilter = (CrossProfileIntentFilter) obj;
        if (crossProfileIntentFilter == null) {
            return null;
        }
        return (CrossProfileIntentFilter) crossProfileIntentFilter.mSnapshot.snapshot();
    }

    @Override // com.android.server.pm.WatchedIntentResolver, com.android.server.IntentResolver
    public final void sortResults(List list) {}
}

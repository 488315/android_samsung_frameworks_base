package com.android.server.pm;

import android.content.IntentFilter;

import com.android.server.utils.SnapshotCache;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PersistentPreferredIntentResolver extends WatchedIntentResolver {
    public final SnapshotCache mSnapshot;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.pm.PersistentPreferredIntentResolver$1, reason: invalid class name */
    public final class AnonymousClass1 extends SnapshotCache {
        @Override // com.android.server.utils.SnapshotCache
        public final Object createSnapshot() {
            return new PersistentPreferredIntentResolver(
                    (PersistentPreferredIntentResolver) this.mSource);
        }
    }

    public PersistentPreferredIntentResolver() {
        this.mSnapshot = new AnonymousClass1(this, this, null);
    }

    public PersistentPreferredIntentResolver(
            PersistentPreferredIntentResolver persistentPreferredIntentResolver) {
        copyFrom(persistentPreferredIntentResolver);
        this.mSnapshot = new SnapshotCache.Auto();
    }

    @Override // com.android.server.IntentResolver
    public final IntentFilter getIntentFilter(Object obj) {
        return ((PersistentPreferredActivity) obj).mFilter;
    }

    @Override // com.android.server.IntentResolver
    public final boolean isPackageForFilter(String str, Object obj) {
        return str.equals(((PersistentPreferredActivity) obj).mComponent.getPackageName());
    }

    @Override // com.android.server.IntentResolver
    public final Object[] newArray(int i) {
        return new PersistentPreferredActivity[i];
    }

    @Override // com.android.server.utils.Snappable
    public final Object snapshot() {
        return (PersistentPreferredIntentResolver) this.mSnapshot.snapshot();
    }

    @Override // com.android.server.IntentResolver
    public final Object snapshot(Object obj) {
        PersistentPreferredActivity persistentPreferredActivity = (PersistentPreferredActivity) obj;
        if (persistentPreferredActivity == null) {
            return null;
        }
        return (PersistentPreferredActivity) persistentPreferredActivity.mSnapshot.snapshot();
    }
}

package com.android.server.pm;

import android.content.ComponentName;

import com.android.modules.utils.TypedXmlPullParser;
import com.android.server.utils.SnapshotCache;

public final class PreferredActivity extends WatchedIntentFilter {
    public final PreferredComponent mPref;
    public final SnapshotCache mSnapshot;

    /* renamed from: com.android.server.pm.PreferredActivity$1, reason: invalid class name */
    public final class AnonymousClass1 extends SnapshotCache {
        @Override // com.android.server.utils.SnapshotCache
        public final Object createSnapshot() {
            PreferredActivity preferredActivity =
                    new PreferredActivity((PreferredActivity) this.mSource);
            preferredActivity.seal();
            return preferredActivity;
        }
    }

    public PreferredActivity(TypedXmlPullParser typedXmlPullParser) {
        this.mPref = new PreferredComponent(this, typedXmlPullParser);
        this.mSnapshot = new AnonymousClass1(this, this, null);
    }

    public PreferredActivity(PreferredActivity preferredActivity) {
        super(preferredActivity);
        this.mPref = preferredActivity.mPref;
        this.mSnapshot = new SnapshotCache.Auto();
    }

    public PreferredActivity(
            WatchedIntentFilter watchedIntentFilter,
            int i,
            ComponentName[] componentNameArr,
            ComponentName componentName,
            boolean z) {
        super(watchedIntentFilter.mFilter);
        this.mPref = new PreferredComponent(this, i, componentNameArr, componentName, z);
        this.mSnapshot = new AnonymousClass1(this, this, null);
    }

    @Override // com.android.server.pm.WatchedIntentFilter, com.android.server.utils.Snappable
    public final WatchedIntentFilter snapshot() {
        return (PreferredActivity) this.mSnapshot.snapshot();
    }

    @Override // com.android.server.pm.WatchedIntentFilter, com.android.server.utils.Snappable
    public final Object snapshot() {
        return (PreferredActivity) this.mSnapshot.snapshot();
    }

    public final String toString() {
        return "PreferredActivity{0x"
                + Integer.toHexString(System.identityHashCode(this))
                + " "
                + this.mPref.mComponent.flattenToShortString()
                + "}";
    }
}

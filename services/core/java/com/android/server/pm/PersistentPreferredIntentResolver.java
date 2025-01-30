package com.android.server.pm;

import android.content.IntentFilter;
import com.android.server.utils.SnapshotCache;

/* loaded from: classes3.dex */
public class PersistentPreferredIntentResolver extends WatchedIntentResolver {
  public final SnapshotCache mSnapshot;

  @Override // com.android.server.IntentResolver
  public PersistentPreferredActivity[] newArray(int i) {
    return new PersistentPreferredActivity[i];
  }

  @Override // com.android.server.IntentResolver
  public IntentFilter getIntentFilter(PersistentPreferredActivity persistentPreferredActivity) {
    return persistentPreferredActivity.getIntentFilter();
  }

  @Override // com.android.server.IntentResolver
  public boolean isPackageForFilter(
      String str, PersistentPreferredActivity persistentPreferredActivity) {
    return str.equals(persistentPreferredActivity.mComponent.getPackageName());
  }

  public PersistentPreferredIntentResolver() {
    this.mSnapshot = makeCache();
  }

  @Override // com.android.server.IntentResolver
  public PersistentPreferredActivity snapshot(
      PersistentPreferredActivity persistentPreferredActivity) {
    if (persistentPreferredActivity == null) {
      return null;
    }
    return persistentPreferredActivity.snapshot();
  }

  public PersistentPreferredIntentResolver(
      PersistentPreferredIntentResolver persistentPreferredIntentResolver) {
    copyFrom((WatchedIntentResolver) persistentPreferredIntentResolver);
    this.mSnapshot = new SnapshotCache.Sealed();
  }

  public final SnapshotCache makeCache() {
    return new SnapshotCache(
        this, this) { // from class: com.android.server.pm.PersistentPreferredIntentResolver.1
      @Override // com.android.server.utils.SnapshotCache
      public PersistentPreferredIntentResolver createSnapshot() {
        return new PersistentPreferredIntentResolver();
      }
    };
  }

  @Override // com.android.server.utils.Snappable
  public PersistentPreferredIntentResolver snapshot() {
    return (PersistentPreferredIntentResolver) this.mSnapshot.snapshot();
  }
}

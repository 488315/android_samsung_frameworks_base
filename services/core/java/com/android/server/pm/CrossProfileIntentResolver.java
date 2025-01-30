package com.android.server.pm;

import android.content.IntentFilter;
import com.android.server.utils.SnapshotCache;
import java.util.List;

/* loaded from: classes3.dex */
public class CrossProfileIntentResolver extends WatchedIntentResolver {
  public final SnapshotCache mSnapshot;

  @Override // com.android.server.pm.WatchedIntentResolver, com.android.server.IntentResolver
  public void sortResults(List list) {}

  @Override // com.android.server.IntentResolver
  public CrossProfileIntentFilter[] newArray(int i) {
    return new CrossProfileIntentFilter[i];
  }

  @Override // com.android.server.IntentResolver
  public boolean isPackageForFilter(String str, CrossProfileIntentFilter crossProfileIntentFilter) {
    return (crossProfileIntentFilter.mFlags & 8) != 0;
  }

  @Override // com.android.server.IntentResolver
  public IntentFilter getIntentFilter(CrossProfileIntentFilter crossProfileIntentFilter) {
    return crossProfileIntentFilter.getIntentFilter();
  }

  public CrossProfileIntentResolver() {
    this.mSnapshot = makeCache();
  }

  @Override // com.android.server.IntentResolver
  public CrossProfileIntentFilter snapshot(CrossProfileIntentFilter crossProfileIntentFilter) {
    if (crossProfileIntentFilter == null) {
      return null;
    }
    return crossProfileIntentFilter.snapshot();
  }

  public CrossProfileIntentResolver(CrossProfileIntentResolver crossProfileIntentResolver) {
    copyFrom((WatchedIntentResolver) crossProfileIntentResolver);
    this.mSnapshot = new SnapshotCache.Sealed();
  }

  public final SnapshotCache makeCache() {
    return new SnapshotCache(
        this, this) { // from class: com.android.server.pm.CrossProfileIntentResolver.1
      @Override // com.android.server.utils.SnapshotCache
      public CrossProfileIntentResolver createSnapshot() {
        return new CrossProfileIntentResolver();
      }
    };
  }

  @Override // com.android.server.utils.Snappable
  public CrossProfileIntentResolver snapshot() {
    return (CrossProfileIntentResolver) this.mSnapshot.snapshot();
  }
}

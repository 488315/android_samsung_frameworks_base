package com.android.server.pm;

import android.content.IntentFilter;
import com.android.server.utils.SnapshotCache;
import java.io.PrintWriter;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class PreferredIntentResolver extends WatchedIntentResolver {
  public final SnapshotCache mSnapshot;

  @Override // com.android.server.IntentResolver
  public PreferredActivity[] newArray(int i) {
    return new PreferredActivity[i];
  }

  @Override // com.android.server.IntentResolver
  public boolean isPackageForFilter(String str, PreferredActivity preferredActivity) {
    return str.equals(preferredActivity.mPref.mComponent.getPackageName());
  }

  @Override // com.android.server.IntentResolver
  public void dumpFilter(PrintWriter printWriter, String str, PreferredActivity preferredActivity) {
    preferredActivity.mPref.dump(printWriter, str, preferredActivity);
  }

  @Override // com.android.server.IntentResolver
  public IntentFilter getIntentFilter(PreferredActivity preferredActivity) {
    return preferredActivity.getIntentFilter();
  }

  public boolean shouldAddPreferredActivity(PreferredActivity preferredActivity) {
    ArrayList findFilters = findFilters(preferredActivity);
    if (findFilters != null && !findFilters.isEmpty()) {
      if (!preferredActivity.mPref.mAlways) {
        return false;
      }
      int size = findFilters.size();
      for (int i = 0; i < size; i++) {
        PreferredComponent preferredComponent = ((PreferredActivity) findFilters.get(i)).mPref;
        if (preferredComponent.mAlways) {
          int i2 = preferredComponent.mMatch;
          PreferredComponent preferredComponent2 = preferredActivity.mPref;
          if (i2 == (preferredComponent2.mMatch & 268369920)
              && preferredComponent.sameSet(preferredComponent2)) {
            return false;
          }
        }
      }
    }
    return true;
  }

  public PreferredIntentResolver() {
    this.mSnapshot = makeCache();
  }

  @Override // com.android.server.IntentResolver
  public PreferredActivity snapshot(PreferredActivity preferredActivity) {
    if (preferredActivity == null) {
      return null;
    }
    return preferredActivity.snapshot();
  }

  public PreferredIntentResolver(PreferredIntentResolver preferredIntentResolver) {
    copyFrom((WatchedIntentResolver) preferredIntentResolver);
    this.mSnapshot = new SnapshotCache.Sealed();
  }

  public final SnapshotCache makeCache() {
    return new SnapshotCache(
        this, this) { // from class: com.android.server.pm.PreferredIntentResolver.1
      @Override // com.android.server.utils.SnapshotCache
      public PreferredIntentResolver createSnapshot() {
        return new PreferredIntentResolver();
      }
    };
  }

  @Override // com.android.server.utils.Snappable
  public PreferredIntentResolver snapshot() {
    return (PreferredIntentResolver) this.mSnapshot.snapshot();
  }
}

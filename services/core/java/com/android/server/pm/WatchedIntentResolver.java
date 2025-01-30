package com.android.server.pm;

import com.android.server.IntentResolver;
import com.android.server.pm.snapshot.PackageDataSnapshot;
import com.android.server.utils.Snappable;
import com.android.server.utils.Watchable;
import com.android.server.utils.WatchableImpl;
import com.android.server.utils.Watcher;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class WatchedIntentResolver extends IntentResolver implements Watchable, Snappable {
  public static final Comparator sResolvePrioritySorter =
      new Comparator() { // from class: com.android.server.pm.WatchedIntentResolver.2
        @Override // java.util.Comparator
        public int compare(
            WatchedIntentFilter watchedIntentFilter, WatchedIntentFilter watchedIntentFilter2) {
          int priority = watchedIntentFilter.getPriority();
          int priority2 = watchedIntentFilter2.getPriority();
          if (priority > priority2) {
            return -1;
          }
          return priority < priority2 ? 1 : 0;
        }
      };
  public final Watchable mWatchable = new WatchableImpl();
  public final Watcher mWatcher =
      new Watcher() { // from class: com.android.server.pm.WatchedIntentResolver.1
        @Override // com.android.server.utils.Watcher
        public void onChange(Watchable watchable) {
          WatchedIntentResolver.this.dispatchChange(watchable);
        }
      };

  @Override // com.android.server.utils.Watchable
  public void registerObserver(Watcher watcher) {
    this.mWatchable.registerObserver(watcher);
  }

  @Override // com.android.server.utils.Watchable
  public void unregisterObserver(Watcher watcher) {
    this.mWatchable.unregisterObserver(watcher);
  }

  @Override // com.android.server.utils.Watchable
  public boolean isRegisteredObserver(Watcher watcher) {
    return this.mWatchable.isRegisteredObserver(watcher);
  }

  @Override // com.android.server.utils.Watchable
  public void dispatchChange(Watchable watchable) {
    this.mWatchable.dispatchChange(watchable);
  }

  public void onChanged() {
    dispatchChange(this);
  }

  @Override // com.android.server.IntentResolver
  public void addFilter(
      PackageDataSnapshot packageDataSnapshot, WatchedIntentFilter watchedIntentFilter) {
    super.addFilter(packageDataSnapshot, (Object) watchedIntentFilter);
    watchedIntentFilter.registerObserver(this.mWatcher);
    onChanged();
  }

  @Override // com.android.server.IntentResolver
  public void removeFilter(WatchedIntentFilter watchedIntentFilter) {
    watchedIntentFilter.unregisterObserver(this.mWatcher);
    super.removeFilter((Object) watchedIntentFilter);
    onChanged();
  }

  @Override // com.android.server.IntentResolver
  public void removeFilterInternal(WatchedIntentFilter watchedIntentFilter) {
    watchedIntentFilter.unregisterObserver(this.mWatcher);
    super.removeFilterInternal((Object) watchedIntentFilter);
    onChanged();
  }

  @Override // com.android.server.IntentResolver
  public void sortResults(List list) {
    Collections.sort(list, sResolvePrioritySorter);
  }

  public ArrayList findFilters(WatchedIntentFilter watchedIntentFilter) {
    return super.findFilters(watchedIntentFilter.getIntentFilter());
  }

  public void copyFrom(WatchedIntentResolver watchedIntentResolver) {
    super.copyFrom((IntentResolver) watchedIntentResolver);
  }
}

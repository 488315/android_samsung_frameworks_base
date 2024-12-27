package com.android.server.pm;

import android.content.IntentFilter;

import com.android.server.IntentResolver;
import com.android.server.pm.snapshot.PackageDataSnapshot;
import com.android.server.utils.Snappable;
import com.android.server.utils.Watchable;
import com.android.server.utils.WatchableImpl;
import com.android.server.utils.Watcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class WatchedIntentResolver extends IntentResolver implements Watchable, Snappable {
    public static final AnonymousClass2 sResolvePrioritySorter = new AnonymousClass2();
    public final WatchableImpl mWatchable = new WatchableImpl();
    public final AnonymousClass1 mWatcher =
            new Watcher() { // from class: com.android.server.pm.WatchedIntentResolver.1
                @Override // com.android.server.utils.Watcher
                public final void onChange(Watchable watchable) {
                    WatchedIntentResolver.this.dispatchChange(watchable);
                }
            };

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.pm.WatchedIntentResolver$2, reason: invalid class name */
    public final class AnonymousClass2 implements Comparator {
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            int priority = ((WatchedIntentFilter) obj).mFilter.getPriority();
            int priority2 = ((WatchedIntentFilter) obj2).mFilter.getPriority();
            if (priority > priority2) {
                return -1;
            }
            return priority < priority2 ? 1 : 0;
        }
    }

    @Override // com.android.server.IntentResolver
    public final void addFilter(
            PackageDataSnapshot packageDataSnapshot, WatchedIntentFilter watchedIntentFilter) {
        super.addFilter(packageDataSnapshot, (Object) watchedIntentFilter);
        watchedIntentFilter.registerObserver(this.mWatcher);
        dispatchChange(this);
    }

    @Override // com.android.server.utils.Watchable
    public final void dispatchChange(Watchable watchable) {
        this.mWatchable.dispatchChange(watchable);
    }

    public final ArrayList findFilters(WatchedIntentFilter watchedIntentFilter) {
        IntentFilter intentFilter$3 = watchedIntentFilter.getIntentFilter$3();
        if (intentFilter$3.countDataSchemes() == 1) {
            return collectFilters(
                    (Object[]) this.mSchemeToFilter.get(intentFilter$3.getDataScheme(0)),
                    intentFilter$3);
        }
        if (intentFilter$3.countDataTypes() != 0 && intentFilter$3.countActions() == 1) {
            return collectFilters(
                    (Object[]) this.mTypedActionToFilter.get(intentFilter$3.getAction(0)),
                    intentFilter$3);
        }
        if (intentFilter$3.countDataTypes() == 0
                && intentFilter$3.countDataSchemes() == 0
                && intentFilter$3.countActions() == 1) {
            return collectFilters(
                    (Object[]) this.mActionToFilter.get(intentFilter$3.getAction(0)),
                    intentFilter$3);
        }
        Iterator it = this.mFilters.iterator();
        ArrayList arrayList = null;
        while (it.hasNext()) {
            Object next = it.next();
            if (IntentFilter.filterEquals(getIntentFilter(next), intentFilter$3)) {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.add(next);
            }
        }
        return arrayList;
    }

    @Override // com.android.server.utils.Watchable
    public final boolean isRegisteredObserver(Watcher watcher) {
        return this.mWatchable.isRegisteredObserver(watcher);
    }

    @Override // com.android.server.utils.Watchable
    public final void registerObserver(Watcher watcher) {
        this.mWatchable.registerObserver(watcher);
    }

    @Override // com.android.server.IntentResolver
    public final void removeFilter(WatchedIntentFilter watchedIntentFilter) {
        watchedIntentFilter.unregisterObserver(this.mWatcher);
        super.removeFilter((Object) watchedIntentFilter);
        dispatchChange(this);
    }

    @Override // com.android.server.IntentResolver
    public final void removeFilterInternal(Object obj) {
        WatchedIntentFilter watchedIntentFilter = (WatchedIntentFilter) obj;
        watchedIntentFilter.unregisterObserver(this.mWatcher);
        super.removeFilterInternal(watchedIntentFilter);
        dispatchChange(this);
    }

    @Override // com.android.server.IntentResolver
    public void sortResults(List list) {
        Collections.sort(list, sResolvePrioritySorter);
    }

    @Override // com.android.server.utils.Watchable
    public final void unregisterObserver(Watcher watcher) {
        this.mWatchable.unregisterObserver(watcher);
    }
}

package com.android.server.utils;

import java.util.ArrayList;

/* loaded from: classes3.dex */
public class WatchedArrayList extends WatchableImpl implements Snappable {
    public final Watcher mObserver;
    public final ArrayList mStorage;
    public volatile boolean mWatching;

    public final void onChanged() {
        dispatchChange(this);
    }

    public final void registerChild(Object obj) {
        if (this.mWatching && (obj instanceof Watchable)) {
            ((Watchable) obj).registerObserver(this.mObserver);
        }
    }

    public final void unregisterChild(Object obj) {
        if (this.mWatching && (obj instanceof Watchable)) {
            ((Watchable) obj).unregisterObserver(this.mObserver);
        }
    }

    public final void unregisterChildIf(Object obj) {
        if (this.mWatching && (obj instanceof Watchable) && !this.mStorage.contains(obj)) {
            ((Watchable) obj).unregisterObserver(this.mObserver);
        }
    }

    @Override // com.android.server.utils.WatchableImpl, com.android.server.utils.Watchable
    public void registerObserver(Watcher watcher) {
        super.registerObserver(watcher);
        if (registeredObserverCount() == 1) {
            this.mWatching = true;
            int size = this.mStorage.size();
            for (int i = 0; i < size; i++) {
                registerChild(this.mStorage.get(i));
            }
        }
    }

    @Override // com.android.server.utils.WatchableImpl, com.android.server.utils.Watchable
    public void unregisterObserver(Watcher watcher) {
        super.unregisterObserver(watcher);
        if (registeredObserverCount() == 0) {
            int size = this.mStorage.size();
            for (int i = 0; i < size; i++) {
                unregisterChild(this.mStorage.get(i));
            }
            this.mWatching = false;
        }
    }

    public WatchedArrayList() {
        this(0);
    }

    public WatchedArrayList(int i) {
        this.mWatching = false;
        this.mObserver = new Watcher() { // from class: com.android.server.utils.WatchedArrayList.1
            @Override // com.android.server.utils.Watcher
            public void onChange(Watchable watchable) {
                WatchedArrayList.this.dispatchChange(watchable);
            }
        };
        this.mStorage = new ArrayList(i);
    }

    public ArrayList untrackedStorage() {
        return this.mStorage;
    }

    public boolean add(Object obj) {
        boolean add = this.mStorage.add(obj);
        registerChild(obj);
        onChanged();
        return add;
    }

    public void clear() {
        if (this.mWatching) {
            int size = this.mStorage.size();
            for (int i = 0; i < size; i++) {
                unregisterChild(this.mStorage.get(i));
            }
        }
        this.mStorage.clear();
        onChanged();
    }

    public Object get(int i) {
        return this.mStorage.get(i);
    }

    public Object set(int i, Object obj) {
        Object obj2 = this.mStorage.set(i, obj);
        if (obj != obj2) {
            unregisterChildIf(obj2);
            registerChild(obj);
            onChanged();
        }
        return obj2;
    }

    public int size() {
        return this.mStorage.size();
    }

    public boolean equals(Object obj) {
        if (obj instanceof WatchedArrayList) {
            return this.mStorage.equals(((WatchedArrayList) obj).mStorage);
        }
        return false;
    }

    public int hashCode() {
        return this.mStorage.hashCode();
    }

    @Override // com.android.server.utils.Snappable
    public WatchedArrayList snapshot() {
        WatchedArrayList watchedArrayList = new WatchedArrayList(size());
        snapshot(watchedArrayList, this);
        return watchedArrayList;
    }

    public static void snapshot(WatchedArrayList watchedArrayList, WatchedArrayList watchedArrayList2) {
        if (watchedArrayList.size() != 0) {
            throw new IllegalArgumentException("snapshot destination is not empty");
        }
        int size = watchedArrayList2.size();
        watchedArrayList.mStorage.ensureCapacity(size);
        for (int i = 0; i < size; i++) {
            watchedArrayList.mStorage.add(Snapshots.maybeSnapshot(watchedArrayList2.get(i)));
        }
        watchedArrayList.seal();
    }
}

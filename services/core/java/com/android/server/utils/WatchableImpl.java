package com.android.server.utils;

import java.util.ArrayList;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class WatchableImpl implements Watchable {
    public final ArrayList mObservers = new ArrayList();
    public boolean mSealed = false;

    @Override // com.android.server.utils.Watchable
    public final void dispatchChange(Watchable watchable) {
        synchronized (this.mObservers) {
            try {
                if (this.mSealed) {
                    throw new IllegalStateException("attempt to change a sealed object");
                }
                int size = this.mObservers.size();
                for (int i = 0; i < size; i++) {
                    ((Watcher) this.mObservers.get(i)).onChange(watchable);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.utils.Watchable
    public final boolean isRegisteredObserver(Watcher watcher) {
        boolean contains;
        synchronized (this.mObservers) {
            contains = this.mObservers.contains(watcher);
        }
        return contains;
    }

    @Override // com.android.server.utils.Watchable
    public void registerObserver(Watcher watcher) {
        Objects.requireNonNull(watcher, "observer may not be null");
        synchronized (this.mObservers) {
            try {
                if (!this.mObservers.contains(watcher)) {
                    this.mObservers.add(watcher);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void seal() {
        synchronized (this.mObservers) {
            this.mSealed = true;
        }
    }

    @Override // com.android.server.utils.Watchable
    public void unregisterObserver(Watcher watcher) {
        Objects.requireNonNull(watcher, "observer may not be null");
        synchronized (this.mObservers) {
            this.mObservers.remove(watcher);
        }
    }
}

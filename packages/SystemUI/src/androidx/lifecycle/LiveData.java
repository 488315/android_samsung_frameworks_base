package androidx.lifecycle;

import android.os.Handler;
import android.os.Looper;
import androidx.arch.core.executor.ArchTaskExecutor;
import androidx.arch.core.executor.DefaultTaskExecutor;
import androidx.arch.core.internal.SafeIterableMap;
import androidx.arch.core.internal.SafeIterableMap.IteratorWithAdditions;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class LiveData {
    static final Object NOT_SET = new Object();
    static final int START_VERSION = -1;
    int mActiveCount;
    private boolean mChangingActiveState;
    private volatile Object mData;
    final Object mDataLock;
    private boolean mDispatchInvalidated;
    private boolean mDispatchingValue;
    private SafeIterableMap mObservers;
    volatile Object mPendingData;
    private final Runnable mPostValueRunnable;
    private int mVersion;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class AlwaysActiveObserver extends ObserverWrapper {
        public AlwaysActiveObserver(LiveData liveData, Observer observer) {
            super(observer);
        }

        @Override // androidx.lifecycle.LiveData.ObserverWrapper
        public final boolean shouldBeActive() {
            return true;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class LifecycleBoundObserver extends ObserverWrapper implements LifecycleEventObserver {
        public final LifecycleOwner mOwner;

        public LifecycleBoundObserver(LifecycleOwner lifecycleOwner, Observer observer) {
            super(observer);
            this.mOwner = lifecycleOwner;
        }

        @Override // androidx.lifecycle.LiveData.ObserverWrapper
        public final void detachObserver() {
            this.mOwner.getLifecycle().removeObserver(this);
        }

        @Override // androidx.lifecycle.LiveData.ObserverWrapper
        public final boolean isAttachedTo(LifecycleOwner lifecycleOwner) {
            return this.mOwner == lifecycleOwner;
        }

        @Override // androidx.lifecycle.LifecycleEventObserver
        public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
            LifecycleOwner lifecycleOwner2 = this.mOwner;
            Lifecycle.State currentState = lifecycleOwner2.getLifecycle().getCurrentState();
            if (currentState == Lifecycle.State.DESTROYED) {
                LiveData.this.removeObserver(this.mObserver);
                return;
            }
            Lifecycle.State state = null;
            while (state != currentState) {
                activeStateChanged(shouldBeActive());
                state = currentState;
                currentState = lifecycleOwner2.getLifecycle().getCurrentState();
            }
        }

        @Override // androidx.lifecycle.LiveData.ObserverWrapper
        public final boolean shouldBeActive() {
            return this.mOwner.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED);
        }
    }

    public LiveData(Object obj) {
        this.mDataLock = new Object();
        this.mObservers = new SafeIterableMap();
        this.mActiveCount = 0;
        this.mPendingData = NOT_SET;
        this.mPostValueRunnable = new Runnable() { // from class: androidx.lifecycle.LiveData.1
            @Override // java.lang.Runnable
            public final void run() {
                Object obj2;
                synchronized (LiveData.this.mDataLock) {
                    obj2 = LiveData.this.mPendingData;
                    LiveData.this.mPendingData = LiveData.NOT_SET;
                }
                LiveData.this.setValue(obj2);
            }
        };
        this.mData = obj;
        this.mVersion = 0;
    }

    public static void assertMainThread(String str) {
        ArchTaskExecutor.getInstance().mDelegate.getClass();
        if (Looper.getMainLooper().getThread() != Thread.currentThread()) {
            throw new IllegalStateException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Cannot invoke ", str, " on a background thread"));
        }
    }

    public void changeActiveCounter(int i) {
        int i2 = this.mActiveCount;
        this.mActiveCount = i + i2;
        if (this.mChangingActiveState) {
            return;
        }
        this.mChangingActiveState = true;
        while (true) {
            try {
                int i3 = this.mActiveCount;
                if (i2 == i3) {
                    this.mChangingActiveState = false;
                    return;
                }
                boolean z = i2 == 0 && i3 > 0;
                boolean z2 = i2 > 0 && i3 == 0;
                if (z) {
                    onActive();
                } else if (z2) {
                    onInactive();
                }
                i2 = i3;
            } catch (Throwable th) {
                this.mChangingActiveState = false;
                throw th;
            }
        }
    }

    public final void considerNotify(ObserverWrapper observerWrapper) {
        if (observerWrapper.mActive) {
            if (!observerWrapper.shouldBeActive()) {
                observerWrapper.activeStateChanged(false);
                return;
            }
            int i = observerWrapper.mLastVersion;
            int i2 = this.mVersion;
            if (i >= i2) {
                return;
            }
            observerWrapper.mLastVersion = i2;
            observerWrapper.mObserver.onChanged(this.mData);
        }
    }

    public void dispatchingValue(ObserverWrapper observerWrapper) {
        if (this.mDispatchingValue) {
            this.mDispatchInvalidated = true;
            return;
        }
        this.mDispatchingValue = true;
        do {
            this.mDispatchInvalidated = false;
            if (observerWrapper != null) {
                considerNotify(observerWrapper);
                observerWrapper = null;
            } else {
                SafeIterableMap safeIterableMap = this.mObservers;
                safeIterableMap.getClass();
                SafeIterableMap.IteratorWithAdditions iteratorWithAdditions = safeIterableMap.new IteratorWithAdditions();
                safeIterableMap.mIterators.put(iteratorWithAdditions, Boolean.FALSE);
                while (iteratorWithAdditions.hasNext()) {
                    considerNotify((ObserverWrapper) ((Map.Entry) iteratorWithAdditions.next()).getValue());
                    if (this.mDispatchInvalidated) {
                        break;
                    }
                }
            }
        } while (this.mDispatchInvalidated);
        this.mDispatchingValue = false;
    }

    public Object getValue() {
        Object obj = this.mData;
        if (obj != NOT_SET) {
            return obj;
        }
        return null;
    }

    public int getVersion() {
        return this.mVersion;
    }

    public boolean hasActiveObservers() {
        return this.mActiveCount > 0;
    }

    public boolean hasObservers() {
        return this.mObservers.mSize > 0;
    }

    public boolean isInitialized() {
        return this.mData != NOT_SET;
    }

    public void observe(LifecycleOwner lifecycleOwner, Observer observer) {
        assertMainThread("observe");
        if (lifecycleOwner.getLifecycle().getCurrentState() == Lifecycle.State.DESTROYED) {
            return;
        }
        LifecycleBoundObserver lifecycleBoundObserver = new LifecycleBoundObserver(lifecycleOwner, observer);
        ObserverWrapper observerWrapper = (ObserverWrapper) this.mObservers.putIfAbsent(observer, lifecycleBoundObserver);
        if (observerWrapper != null && !observerWrapper.isAttachedTo(lifecycleOwner)) {
            throw new IllegalArgumentException("Cannot add the same observer with different lifecycles");
        }
        if (observerWrapper != null) {
            return;
        }
        lifecycleOwner.getLifecycle().addObserver(lifecycleBoundObserver);
    }

    public void observeForever(Observer observer) {
        assertMainThread("observeForever");
        AlwaysActiveObserver alwaysActiveObserver = new AlwaysActiveObserver(this, observer);
        ObserverWrapper observerWrapper = (ObserverWrapper) this.mObservers.putIfAbsent(observer, alwaysActiveObserver);
        if (observerWrapper instanceof LifecycleBoundObserver) {
            throw new IllegalArgumentException("Cannot add the same observer with different lifecycles");
        }
        if (observerWrapper != null) {
            return;
        }
        alwaysActiveObserver.activeStateChanged(true);
    }

    public void postValue(Object obj) {
        boolean z;
        synchronized (this.mDataLock) {
            z = this.mPendingData == NOT_SET;
            this.mPendingData = obj;
        }
        if (z) {
            ArchTaskExecutor archTaskExecutor = ArchTaskExecutor.getInstance();
            Runnable runnable = this.mPostValueRunnable;
            DefaultTaskExecutor defaultTaskExecutor = archTaskExecutor.mDelegate;
            if (defaultTaskExecutor.mMainHandler == null) {
                synchronized (defaultTaskExecutor.mLock) {
                    try {
                        if (defaultTaskExecutor.mMainHandler == null) {
                            defaultTaskExecutor.mMainHandler = Handler.createAsync(Looper.getMainLooper());
                        }
                    } finally {
                    }
                }
            }
            defaultTaskExecutor.mMainHandler.post(runnable);
        }
    }

    public void removeObserver(Observer observer) {
        assertMainThread("removeObserver");
        ObserverWrapper observerWrapper = (ObserverWrapper) this.mObservers.remove(observer);
        if (observerWrapper == null) {
            return;
        }
        observerWrapper.detachObserver();
        observerWrapper.activeStateChanged(false);
    }

    public void removeObservers(LifecycleOwner lifecycleOwner) {
        assertMainThread("removeObservers");
        Iterator it = this.mObservers.iterator();
        while (true) {
            SafeIterableMap.ListIterator listIterator = (SafeIterableMap.ListIterator) it;
            if (!listIterator.hasNext()) {
                return;
            }
            Map.Entry entry = (Map.Entry) listIterator.next();
            if (((ObserverWrapper) entry.getValue()).isAttachedTo(lifecycleOwner)) {
                removeObserver((Observer) entry.getKey());
            }
        }
    }

    public void setValue(Object obj) {
        assertMainThread("setValue");
        this.mVersion++;
        this.mData = obj;
        dispatchingValue(null);
    }

    public LiveData() {
        this.mDataLock = new Object();
        this.mObservers = new SafeIterableMap();
        this.mActiveCount = 0;
        Object obj = NOT_SET;
        this.mPendingData = obj;
        this.mPostValueRunnable = new Runnable() { // from class: androidx.lifecycle.LiveData.1
            @Override // java.lang.Runnable
            public final void run() {
                Object obj2;
                synchronized (LiveData.this.mDataLock) {
                    obj2 = LiveData.this.mPendingData;
                    LiveData.this.mPendingData = LiveData.NOT_SET;
                }
                LiveData.this.setValue(obj2);
            }
        };
        this.mData = obj;
        this.mVersion = -1;
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class ObserverWrapper {
        public boolean mActive;
        public int mLastVersion = -1;
        public final Observer mObserver;

        public ObserverWrapper(Observer observer) {
            this.mObserver = observer;
        }

        public final void activeStateChanged(boolean z) {
            if (z == this.mActive) {
                return;
            }
            this.mActive = z;
            int i = z ? 1 : -1;
            LiveData liveData = LiveData.this;
            liveData.changeActiveCounter(i);
            if (this.mActive) {
                liveData.dispatchingValue(this);
            }
        }

        public boolean isAttachedTo(LifecycleOwner lifecycleOwner) {
            return false;
        }

        public abstract boolean shouldBeActive();

        public void detachObserver() {
        }
    }

    public void onActive() {
    }

    public void onInactive() {
    }
}

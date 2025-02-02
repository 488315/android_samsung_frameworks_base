package androidx.lifecycle;

import androidx.arch.core.executor.ArchTaskExecutor;
import androidx.arch.core.internal.SafeIterableMap;
import androidx.constraintlayout.helper.widget.LogJson$JsonWriter$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import java.util.Map;

/* loaded from: classes.dex */
public abstract class LiveData<T> {
    static final Object NOT_SET = new Object();
    private volatile Object mData;
    private boolean mDispatchInvalidated;
    private boolean mDispatchingValue;
    volatile Object mPendingData;
    private int mVersion;
    final Object mDataLock = new Object();
    private SafeIterableMap<Object, LiveData<T>.ObserverWrapper> mObservers = new SafeIterableMap<>();
    int mActiveCount = 0;

    class LifecycleBoundObserver extends LiveData<T>.ObserverWrapper implements LifecycleEventObserver {
        LifecycleBoundObserver() {
            throw null;
        }

        @Override // androidx.lifecycle.LifecycleEventObserver
        public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
            throw null;
        }

        @Override // androidx.lifecycle.LiveData.ObserverWrapper
        final boolean shouldBeActive() {
            throw null;
        }
    }

    private abstract class ObserverWrapper {
        boolean mActive;
        int mLastVersion;

        ObserverWrapper() {
            throw null;
        }

        final void activeStateChanged(boolean z) {
            if (z == this.mActive) {
                return;
            }
            this.mActive = z;
            throw null;
        }

        abstract boolean shouldBeActive();
    }

    public LiveData() {
        Object obj = NOT_SET;
        this.mPendingData = obj;
        this.mData = obj;
        this.mVersion = -1;
    }

    static void assertMainThread(String str) {
        if (!ArchTaskExecutor.getInstance().isMainThread()) {
            throw new IllegalStateException(LogJson$JsonWriter$$ExternalSyntheticOutline0.m4m("Cannot invoke ", str, " on a background thread"));
        }
    }

    final void dispatchingValue(LiveData<T>.ObserverWrapper observerWrapper) {
        if (this.mDispatchingValue) {
            this.mDispatchInvalidated = true;
            return;
        }
        this.mDispatchingValue = true;
        do {
            this.mDispatchInvalidated = false;
            if (observerWrapper != null) {
                if (observerWrapper.mActive) {
                    if (observerWrapper.shouldBeActive()) {
                        int i = observerWrapper.mLastVersion;
                        int i2 = this.mVersion;
                        if (i < i2) {
                            observerWrapper.mLastVersion = i2;
                            throw null;
                        }
                    } else {
                        observerWrapper.activeStateChanged(false);
                    }
                }
                observerWrapper = null;
            } else {
                SafeIterableMap<Object, LiveData<T>.ObserverWrapper>.IteratorWithAdditions iteratorWithAdditions = this.mObservers.iteratorWithAdditions();
                while (iteratorWithAdditions.hasNext()) {
                    ObserverWrapper observerWrapper2 = (ObserverWrapper) ((Map.Entry) iteratorWithAdditions.next()).getValue();
                    if (observerWrapper2.mActive) {
                        if (observerWrapper2.shouldBeActive()) {
                            int i3 = observerWrapper2.mLastVersion;
                            int i4 = this.mVersion;
                            if (i3 < i4) {
                                observerWrapper2.mLastVersion = i4;
                                throw null;
                            }
                        } else {
                            observerWrapper2.activeStateChanged(false);
                        }
                    }
                    if (this.mDispatchInvalidated) {
                        break;
                    }
                }
            }
        } while (this.mDispatchInvalidated);
        this.mDispatchingValue = false;
    }

    protected void setValue(T t) {
        assertMainThread("setValue");
        this.mVersion++;
        this.mData = t;
        dispatchingValue(null);
    }
}

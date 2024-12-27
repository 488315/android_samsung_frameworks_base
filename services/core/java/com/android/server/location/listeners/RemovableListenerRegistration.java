package com.android.server.location.listeners;

import com.android.internal.listeners.ListenerExecutor;

import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class RemovableListenerRegistration implements ListenerExecutor {
    public boolean mActive;
    public boolean mActiveMotionControl;
    public final Executor mExecutor;
    public volatile Object mKey;
    public volatile Object mListener;
    public final AtomicBoolean mRemoved;

    public RemovableListenerRegistration(Executor executor, Object obj) {
        Objects.requireNonNull(executor);
        this.mExecutor = executor;
        this.mActive = false;
        Objects.requireNonNull(obj);
        this.mListener = obj;
        this.mRemoved = new AtomicBoolean(false);
    }

    public final boolean equals(Object obj) {
        return this == obj;
    }

    public final void executeOperation(ListenerExecutor.ListenerOperation listenerOperation) {
        executeSafely(
                this.mExecutor,
                new Supplier() { // from class:
                                 // com.android.server.location.listeners.ListenerRegistration$$ExternalSyntheticLambda0
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return RemovableListenerRegistration.this.mListener;
                    }
                },
                listenerOperation,
                new ListenerExecutor
                        .FailureCallback() { // from class:
                                             // com.android.server.location.listeners.ListenerRegistration$$ExternalSyntheticLambda1
                    public final void onFailure(
                            ListenerExecutor.ListenerOperation listenerOperation2, Exception exc) {
                        RemovableListenerRegistration.this.onOperationFailure(
                                listenerOperation2, exc);
                    }
                });
    }

    public abstract ListenerMultiplexer getOwner();

    public String getTag() {
        return "ListenerRegistration";
    }

    public abstract void onActive();

    public void onInactive() {}

    public void onListenerUnregister() {}

    public abstract void onOperationFailure(
            ListenerExecutor.ListenerOperation listenerOperation, Exception exc);

    public abstract void onRegister();

    public abstract void onUnregister();

    public final void remove() {
        Object obj = this.mKey;
        if (obj == null || this.mRemoved.getAndSet(true)) {
            return;
        }
        getOwner().removeRegistration(obj, this);
    }
}

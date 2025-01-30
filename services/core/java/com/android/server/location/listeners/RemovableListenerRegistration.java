package com.android.server.location.listeners;

import com.android.internal.listeners.ListenerExecutor;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes2.dex */
public abstract class RemovableListenerRegistration extends ListenerRegistration {
    public volatile Object mKey;
    public final AtomicBoolean mRemoved;

    public abstract ListenerMultiplexer getOwner();

    public void onRegister() {
    }

    public void onRemove(boolean z) {
    }

    public RemovableListenerRegistration(Executor executor, Object obj) {
        super(executor, obj);
        this.mRemoved = new AtomicBoolean(false);
    }

    public final Object getKey() {
        Object obj = this.mKey;
        Objects.requireNonNull(obj);
        return obj;
    }

    public final void remove() {
        remove(true);
    }

    public final void remove(boolean z) {
        final Object obj = this.mKey;
        if (obj == null || this.mRemoved.getAndSet(true)) {
            return;
        }
        onRemove(z);
        if (z) {
            getOwner().removeRegistration(obj, this);
        } else {
            executeOperation(new ListenerExecutor.ListenerOperation() { // from class: com.android.server.location.listeners.RemovableListenerRegistration$$ExternalSyntheticLambda0
                public final void operate(Object obj2) {
                    RemovableListenerRegistration.this.lambda$remove$0(obj, obj2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$remove$0(Object obj, Object obj2) {
        getOwner().removeRegistration(obj, this);
    }

    @Override // com.android.server.location.listeners.ListenerRegistration
    public final void onRegister(Object obj) {
        super.onRegister(obj);
        Objects.requireNonNull(obj);
        this.mKey = obj;
        onRegister();
    }

    @Override // com.android.server.location.listeners.ListenerRegistration
    public void onUnregister() {
        this.mKey = null;
        super.onUnregister();
    }
}

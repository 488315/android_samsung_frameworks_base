package com.android.server.location.listeners;

import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.android.internal.listeners.ListenerExecutor;

import java.util.NoSuchElementException;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class BinderListenerRegistration extends RemovableListenerRegistration
        implements IBinder.DeathRecipient {
    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied() {
        try {
            if (Log.isLoggable(getTag(), 3)) {
                Log.d(getTag(), "binder registration " + this + " died");
            }
            remove();
        } catch (RuntimeException e) {
            throw new AssertionError(e);
        }
    }

    public abstract IBinder getBinderFromKey(Object obj);

    @Override // com.android.server.location.listeners.RemovableListenerRegistration
    public final void onOperationFailure(
            ListenerExecutor.ListenerOperation listenerOperation, Exception exc) {
        if (!(exc instanceof RemoteException)) {
            throw new AssertionError(exc);
        }
        Log.w(getTag(), "registration " + this + " removed", exc);
        remove();
    }

    @Override // com.android.server.location.listeners.RemovableListenerRegistration
    public void onRegister() {
        try {
            Object obj = this.mKey;
            Objects.requireNonNull(obj);
            getBinderFromKey(obj).linkToDeath(this, 0);
        } catch (RemoteException unused) {
            remove();
        }
    }

    @Override // com.android.server.location.listeners.RemovableListenerRegistration
    public void onUnregister() {
        try {
            Object obj = this.mKey;
            Objects.requireNonNull(obj);
            getBinderFromKey(obj).unlinkToDeath(this, 0);
        } catch (NoSuchElementException e) {
            Log.w(getTag(), "failed to unregister binder death listener", e);
        }
        this.mKey = null;
    }
}

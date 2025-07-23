package com.android.internal.listeners;

import android.os.RemoteException;
import android.util.ArrayMap;
import com.android.internal.listeners.ListenerTransport;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;

/* loaded from: classes5.dex */
public abstract class ListenerTransportManager<TTransport extends ListenerTransport<?>> {
    private final Map<Object, WeakReference<TTransport>> mRegistrations;

    protected abstract void registerTransport(TTransport ttransport) throws RemoteException;

    protected abstract void unregisterTransport(TTransport ttransport) throws RemoteException;

    protected ListenerTransportManager(boolean z) {
        if (z) {
            this.mRegistrations = new WeakHashMap();
        } else {
            this.mRegistrations = new ArrayMap();
        }
    }

    public final void addListener(Object obj, TTransport ttransport) {
        try {
            synchronized (this.mRegistrations) {
                WeakReference<TTransport> weakReference = this.mRegistrations.get(obj);
                TTransport ttransport2 = weakReference != null ? weakReference.get() : null;
                if (ttransport2 == null) {
                    registerTransport(ttransport);
                } else {
                    registerTransport(ttransport, ttransport2);
                    ttransport2.unregister();
                }
                this.mRegistrations.put(obj, new WeakReference<>(ttransport));
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public final void removeListener(Object obj) {
        TTransport ttransport;
        try {
            synchronized (this.mRegistrations) {
                WeakReference<TTransport> remove = this.mRegistrations.remove(obj);
                if (remove != null && (ttransport = remove.get()) != null) {
                    ttransport.unregister();
                    unregisterTransport(ttransport);
                }
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    protected void registerTransport(TTransport ttransport, TTransport ttransport2) throws RemoteException {
        registerTransport(ttransport);
        try {
            unregisterTransport(ttransport2);
        } catch (RemoteException e) {
            try {
                unregisterTransport(ttransport);
            } catch (RemoteException e2) {
                e.addSuppressed(e2);
            }
            throw e;
        }
    }
}

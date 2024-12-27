package com.android.server.location.listeners;

import android.util.ArrayMap;
import android.util.ArraySet;
import com.android.internal.listeners.ListenerExecutor;
import com.android.internal.util.Preconditions;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class ListenerMultiplexer {
    public Object mMerged;
    public final Object mMultiplexerLock = new Object();
    public final ArrayMap mRegistrations = new ArrayMap();
    public final UpdateServiceBuffer mUpdateServiceBuffer = new UpdateServiceBuffer();
    public final ReentrancyGuard mReentrancyGuard = new ReentrancyGuard();
    public int mActiveRegistrationsCount = 0;
    public boolean mServiceRegistered = false;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ReentrancyGuard implements AutoCloseable {
        public int mGuardCount = 0;
        public ArraySet mScheduledRemovals = null;

        public ReentrancyGuard() {
        }

        public final void acquire() {
            synchronized (ListenerMultiplexer.this.mMultiplexerLock) {
                this.mGuardCount++;
            }
        }

        @Override // java.lang.AutoCloseable
        public final void close() {
            synchronized (ListenerMultiplexer.this.mMultiplexerLock) {
                try {
                    Preconditions.checkState(this.mGuardCount > 0);
                    int i = this.mGuardCount - 1;
                    this.mGuardCount = i;
                    ArraySet arraySet = null;
                    if (i == 0) {
                        ArraySet arraySet2 = this.mScheduledRemovals;
                        this.mScheduledRemovals = null;
                        arraySet = arraySet2;
                    }
                    if (arraySet == null) {
                        return;
                    }
                    UpdateServiceBuffer updateServiceBuffer = ListenerMultiplexer.this.mUpdateServiceBuffer;
                    updateServiceBuffer.acquire();
                    try {
                        int size = arraySet.size();
                        for (int i2 = 0; i2 < size; i2++) {
                            Map.Entry entry = (Map.Entry) arraySet.valueAt(i2);
                            ListenerMultiplexer.this.removeRegistration(entry.getKey(), (RemovableListenerRegistration) entry.getValue());
                        }
                        updateServiceBuffer.close();
                    } finally {
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final boolean isReentrant() {
            boolean z;
            synchronized (ListenerMultiplexer.this.mMultiplexerLock) {
                z = this.mGuardCount != 0;
            }
            return z;
        }

        public final void markForRemoval(Object obj, RemovableListenerRegistration removableListenerRegistration) {
            synchronized (ListenerMultiplexer.this.mMultiplexerLock) {
                try {
                    Preconditions.checkState(isReentrant());
                    if (this.mScheduledRemovals == null) {
                        this.mScheduledRemovals = new ArraySet(ListenerMultiplexer.this.mRegistrations.size());
                    }
                    this.mScheduledRemovals.add(new AbstractMap.SimpleImmutableEntry(obj, removableListenerRegistration));
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UpdateServiceBuffer implements AutoCloseable {
        public int mBufferCount = 0;
        public boolean mUpdateServiceRequired = false;

        public UpdateServiceBuffer() {
        }

        public final synchronized void acquire() {
            this.mBufferCount++;
        }

        @Override // java.lang.AutoCloseable
        public final void close() {
            boolean z;
            synchronized (this) {
                try {
                    z = false;
                    Preconditions.checkState(this.mBufferCount > 0);
                    int i = this.mBufferCount - 1;
                    this.mBufferCount = i;
                    if (i == 0) {
                        boolean z2 = this.mUpdateServiceRequired;
                        this.mUpdateServiceRequired = false;
                        z = z2;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (z) {
                ListenerMultiplexer.this.updateService();
            }
        }
    }

    public final void deliverToListeners(ListenerExecutor.ListenerOperation listenerOperation) {
        synchronized (this.mMultiplexerLock) {
            ReentrancyGuard reentrancyGuard = this.mReentrancyGuard;
            reentrancyGuard.acquire();
            try {
                int size = this.mRegistrations.size();
                for (int i = 0; i < size; i++) {
                    RemovableListenerRegistration removableListenerRegistration = (RemovableListenerRegistration) this.mRegistrations.valueAt(i);
                    if (removableListenerRegistration.mActive || removableListenerRegistration.mActiveMotionControl) {
                        removableListenerRegistration.executeOperation(listenerOperation);
                    }
                }
                reentrancyGuard.close();
            } finally {
            }
        }
    }

    public final void deliverToListeners(Function function) {
        ListenerExecutor.ListenerOperation listenerOperation;
        synchronized (this.mMultiplexerLock) {
            ReentrancyGuard reentrancyGuard = this.mReentrancyGuard;
            reentrancyGuard.acquire();
            try {
                int size = this.mRegistrations.size();
                for (int i = 0; i < size; i++) {
                    RemovableListenerRegistration removableListenerRegistration = (RemovableListenerRegistration) this.mRegistrations.valueAt(i);
                    if ((removableListenerRegistration.mActive || removableListenerRegistration.mActiveMotionControl) && (listenerOperation = (ListenerExecutor.ListenerOperation) function.apply(removableListenerRegistration)) != null) {
                        removableListenerRegistration.executeOperation(listenerOperation);
                    }
                }
                reentrancyGuard.close();
            } finally {
            }
        }
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        synchronized (this.mMultiplexerLock) {
            try {
                printWriter.print("service: ");
                printWriter.print(getServiceState());
                printWriter.println();
                if (!this.mRegistrations.isEmpty()) {
                    printWriter.println("listeners:");
                    int size = this.mRegistrations.size();
                    for (int i = 0; i < size; i++) {
                        RemovableListenerRegistration removableListenerRegistration = (RemovableListenerRegistration) this.mRegistrations.valueAt(i);
                        printWriter.print("  ");
                        printWriter.print(removableListenerRegistration);
                        if (removableListenerRegistration.mActive) {
                            printWriter.println();
                        } else {
                            printWriter.println(" (inactive)");
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean findRegistration(Predicate predicate) {
        synchronized (this.mMultiplexerLock) {
            try {
                ReentrancyGuard reentrancyGuard = this.mReentrancyGuard;
                reentrancyGuard.acquire();
                try {
                    int size = this.mRegistrations.size();
                    for (int i = 0; i < size; i++) {
                        if (predicate.test((RemovableListenerRegistration) this.mRegistrations.valueAt(i))) {
                            reentrancyGuard.close();
                            return true;
                        }
                    }
                    reentrancyGuard.close();
                    return false;
                } finally {
                    try {
                        reentrancyGuard.close();
                    } catch (Throwable th) {
                        th.addSuppressed(th);
                    }
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    public final int getRegistrationCountWith(Predicate predicate) {
        int i;
        synchronized (this.mMultiplexerLock) {
            try {
                int size = this.mRegistrations.size();
                i = 0;
                for (int i2 = 0; i2 < size; i2++) {
                    if (predicate.test((RemovableListenerRegistration) this.mRegistrations.valueAt(i2))) {
                        i++;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return i;
    }

    public String getServiceState() {
        if (!this.mServiceRegistered) {
            return "unregistered";
        }
        Object obj = this.mMerged;
        return obj != null ? obj.toString() : "registered";
    }

    public abstract boolean isActive(RemovableListenerRegistration removableListenerRegistration);

    public abstract Object mergeRegistrations(Collection collection);

    public void onActive() {
    }

    public void onHalRestarted() {
        resetService();
    }

    public void onInactive() {
    }

    public void onRegister() {
    }

    public final void onRegistrationActiveChanged(RemovableListenerRegistration removableListenerRegistration) {
        boolean z = removableListenerRegistration.mListener != null && isActive(removableListenerRegistration);
        if (z != removableListenerRegistration.mActive) {
            removableListenerRegistration.mActive = z;
            if (z) {
                int i = this.mActiveRegistrationsCount + 1;
                this.mActiveRegistrationsCount = i;
                if (i == 1) {
                    onActive();
                }
                removableListenerRegistration.onActive();
            } else {
                removableListenerRegistration.onInactive();
                int i2 = this.mActiveRegistrationsCount - 1;
                this.mActiveRegistrationsCount = i2;
                if (i2 == 0) {
                    onInactive();
                }
            }
            updateService();
        }
    }

    public abstract void onRegistrationAdded(Object obj, RemovableListenerRegistration removableListenerRegistration);

    public abstract void onRegistrationRemoved(Object obj, RemovableListenerRegistration removableListenerRegistration);

    public void onRegistrationReplaced(Object obj, RemovableListenerRegistration removableListenerRegistration, Object obj2, RemovableListenerRegistration removableListenerRegistration2) {
        onRegistrationRemoved(obj, removableListenerRegistration);
        onRegistrationAdded(obj2, removableListenerRegistration2);
    }

    public void onSettingChanged() {
        updateService();
    }

    public void onTransferUnregisteredRegistration(RemovableListenerRegistration removableListenerRegistration) {
    }

    public void onUnregister() {
    }

    public final void putRegistration(Object obj, RemovableListenerRegistration removableListenerRegistration) {
        Objects.requireNonNull(obj);
        Objects.requireNonNull(removableListenerRegistration);
        synchronized (this.mMultiplexerLock) {
            Preconditions.checkState(!this.mReentrancyGuard.isReentrant());
            Preconditions.checkArgument(true);
            UpdateServiceBuffer updateServiceBuffer = this.mUpdateServiceBuffer;
            updateServiceBuffer.acquire();
            try {
                ReentrancyGuard reentrancyGuard = this.mReentrancyGuard;
                reentrancyGuard.acquire();
                try {
                    boolean isEmpty = this.mRegistrations.isEmpty();
                    int indexOfKey = this.mRegistrations.indexOfKey(obj);
                    RemovableListenerRegistration removableListenerRegistration2 = null;
                    if (indexOfKey >= 0) {
                        RemovableListenerRegistration removableListenerRegistration3 = (RemovableListenerRegistration) this.mRegistrations.valueAt(indexOfKey);
                        removableListenerRegistration3.mListener = null;
                        removableListenerRegistration3.onListenerUnregister();
                        onTransferUnregisteredRegistration(removableListenerRegistration3);
                        onRegistrationActiveChanged(removableListenerRegistration3);
                        removableListenerRegistration3.onUnregister();
                        removableListenerRegistration2 = removableListenerRegistration3;
                    }
                    if (indexOfKey >= 0) {
                        this.mRegistrations.setValueAt(indexOfKey, removableListenerRegistration);
                    } else {
                        this.mRegistrations.put(obj, removableListenerRegistration);
                    }
                    if (isEmpty) {
                        onRegister();
                    }
                    removableListenerRegistration.mKey = obj;
                    removableListenerRegistration.onRegister();
                    if (removableListenerRegistration2 == null) {
                        onRegistrationAdded(obj, removableListenerRegistration);
                    } else {
                        onRegistrationReplaced(obj, removableListenerRegistration2, obj, removableListenerRegistration);
                    }
                    onRegistrationActiveChanged(removableListenerRegistration);
                    reentrancyGuard.close();
                    updateServiceBuffer.close();
                } finally {
                }
            } finally {
            }
        }
    }

    public abstract boolean registerWithService(Collection collection, Object obj);

    public final void removeRegistration(int i) {
        Object keyAt = this.mRegistrations.keyAt(i);
        RemovableListenerRegistration removableListenerRegistration = (RemovableListenerRegistration) this.mRegistrations.valueAt(i);
        UpdateServiceBuffer updateServiceBuffer = this.mUpdateServiceBuffer;
        updateServiceBuffer.acquire();
        try {
            ReentrancyGuard reentrancyGuard = this.mReentrancyGuard;
            reentrancyGuard.acquire();
            try {
                removableListenerRegistration.mListener = null;
                removableListenerRegistration.onListenerUnregister();
                onTransferUnregisteredRegistration(removableListenerRegistration);
                onRegistrationActiveChanged(removableListenerRegistration);
                onRegistrationRemoved(keyAt, removableListenerRegistration);
                removableListenerRegistration.onUnregister();
                this.mRegistrations.removeAt(i);
                if (this.mRegistrations.isEmpty()) {
                    onUnregister();
                }
                reentrancyGuard.close();
                updateServiceBuffer.close();
            } finally {
            }
        } catch (Throwable th) {
            try {
                updateServiceBuffer.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public final void removeRegistration(Object obj) {
        synchronized (this.mMultiplexerLock) {
            try {
                Preconditions.checkState(!this.mReentrancyGuard.isReentrant());
                int indexOfKey = this.mRegistrations.indexOfKey(obj);
                if (indexOfKey < 0) {
                    return;
                }
                removeRegistration(indexOfKey);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void removeRegistration(Object obj, RemovableListenerRegistration removableListenerRegistration) {
        synchronized (this.mMultiplexerLock) {
            try {
                int indexOfKey = this.mRegistrations.indexOfKey(obj);
                if (indexOfKey < 0) {
                    return;
                }
                RemovableListenerRegistration removableListenerRegistration2 = (RemovableListenerRegistration) this.mRegistrations.valueAt(indexOfKey);
                if (removableListenerRegistration2 != removableListenerRegistration) {
                    return;
                }
                if (this.mReentrancyGuard.isReentrant()) {
                    removableListenerRegistration2.mListener = null;
                    removableListenerRegistration2.onListenerUnregister();
                    onTransferUnregisteredRegistration(removableListenerRegistration2);
                    onRegistrationActiveChanged(removableListenerRegistration2);
                    this.mReentrancyGuard.markForRemoval(obj, removableListenerRegistration2);
                } else {
                    removeRegistration(indexOfKey);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void removeRegistrationIf(Predicate predicate) {
        synchronized (this.mMultiplexerLock) {
            Preconditions.checkState(!this.mReentrancyGuard.isReentrant());
            UpdateServiceBuffer updateServiceBuffer = this.mUpdateServiceBuffer;
            updateServiceBuffer.acquire();
            try {
                ReentrancyGuard reentrancyGuard = this.mReentrancyGuard;
                reentrancyGuard.acquire();
                try {
                    int size = this.mRegistrations.size();
                    for (int i = 0; i < size; i++) {
                        Object keyAt = this.mRegistrations.keyAt(i);
                        if (predicate.test(keyAt)) {
                            removeRegistration(keyAt, (RemovableListenerRegistration) this.mRegistrations.valueAt(i));
                        }
                    }
                    reentrancyGuard.close();
                    updateServiceBuffer.close();
                } finally {
                }
            } finally {
            }
        }
    }

    public boolean reregisterWithService(Object obj, Object obj2, Collection collection) {
        return registerWithService(collection, obj2);
    }

    public final void resetService() {
        synchronized (this.mMultiplexerLock) {
            try {
                if (this.mServiceRegistered) {
                    this.mMerged = null;
                    this.mServiceRegistered = false;
                    unregisterWithService();
                    updateService();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public abstract void unregisterWithService();

    public final boolean updateRegistration(Predicate predicate, Object obj) {
        synchronized (this.mMultiplexerLock) {
            UpdateServiceBuffer updateServiceBuffer = this.mUpdateServiceBuffer;
            updateServiceBuffer.acquire();
            try {
                ReentrancyGuard reentrancyGuard = this.mReentrancyGuard;
                reentrancyGuard.acquire();
                try {
                    int indexOfKey = this.mRegistrations.indexOfKey(obj);
                    if (indexOfKey < 0) {
                        reentrancyGuard.close();
                        updateServiceBuffer.close();
                        return false;
                    }
                    RemovableListenerRegistration removableListenerRegistration = (RemovableListenerRegistration) this.mRegistrations.valueAt(indexOfKey);
                    if (predicate.test(removableListenerRegistration)) {
                        onRegistrationActiveChanged(removableListenerRegistration);
                    }
                    reentrancyGuard.close();
                    updateServiceBuffer.close();
                    return true;
                } finally {
                }
            } finally {
            }
        }
    }

    public final void updateRegistrations(Predicate predicate) {
        synchronized (this.mMultiplexerLock) {
            UpdateServiceBuffer updateServiceBuffer = this.mUpdateServiceBuffer;
            updateServiceBuffer.acquire();
            try {
                ReentrancyGuard reentrancyGuard = this.mReentrancyGuard;
                reentrancyGuard.acquire();
                try {
                    int size = this.mRegistrations.size();
                    for (int i = 0; i < size; i++) {
                        RemovableListenerRegistration removableListenerRegistration = (RemovableListenerRegistration) this.mRegistrations.valueAt(i);
                        if (predicate.test(removableListenerRegistration)) {
                            onRegistrationActiveChanged(removableListenerRegistration);
                        }
                    }
                    reentrancyGuard.close();
                    updateServiceBuffer.close();
                } finally {
                }
            } finally {
            }
        }
    }

    public final void updateService() {
        boolean z;
        boolean z2;
        synchronized (this.mMultiplexerLock) {
            UpdateServiceBuffer updateServiceBuffer = this.mUpdateServiceBuffer;
            synchronized (updateServiceBuffer) {
                z = updateServiceBuffer.mBufferCount != 0;
            }
            if (z) {
                UpdateServiceBuffer updateServiceBuffer2 = this.mUpdateServiceBuffer;
                synchronized (updateServiceBuffer2) {
                    synchronized (updateServiceBuffer2) {
                        z2 = updateServiceBuffer2.mBufferCount != 0;
                    }
                    return;
                }
                Preconditions.checkState(z2);
                updateServiceBuffer2.mUpdateServiceRequired = true;
                return;
            }
            int size = this.mRegistrations.size();
            ArrayList arrayList = new ArrayList(size);
            for (int i = 0; i < size; i++) {
                RemovableListenerRegistration removableListenerRegistration = (RemovableListenerRegistration) this.mRegistrations.valueAt(i);
                if (removableListenerRegistration.mActive) {
                    arrayList.add(removableListenerRegistration);
                }
            }
            if (!arrayList.isEmpty()) {
                Object mergeRegistrations = mergeRegistrations(arrayList);
                if (!this.mServiceRegistered) {
                    boolean registerWithService = registerWithService(arrayList, mergeRegistrations);
                    this.mServiceRegistered = registerWithService;
                    this.mMerged = registerWithService ? mergeRegistrations : null;
                } else if (!Objects.equals(mergeRegistrations, this.mMerged)) {
                    boolean reregisterWithService = reregisterWithService(this.mMerged, mergeRegistrations, arrayList);
                    this.mServiceRegistered = reregisterWithService;
                    this.mMerged = reregisterWithService ? mergeRegistrations : null;
                }
            } else if (this.mServiceRegistered) {
                this.mMerged = null;
                this.mServiceRegistered = false;
                unregisterWithService();
            }
        }
    }
}

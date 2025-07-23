package androidx.room;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.util.Log;
import androidx.room.IMultiInstanceInvalidationService;
import androidx.room.InvalidationTracker;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Pair;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.internal.ContextScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class MultiInstanceInvalidationClient {
    public final Context appContext;
    public int clientId;
    public final CoroutineScope coroutineScope;
    public final SharedFlowImpl invalidatedTables;
    public final MultiInstanceInvalidationClient$invalidationCallback$1 invalidationCallback;
    public IMultiInstanceInvalidationService invalidationService;
    public final InvalidationTracker invalidationTracker;
    public final String name;
    public final MultiInstanceInvalidationClient$observer$1 observer;
    public final MultiInstanceInvalidationClient$serviceConnection$1 serviceConnection;
    public final AtomicBoolean stopped;

    /* JADX WARN: Type inference failed for: r1v10, types: [androidx.room.MultiInstanceInvalidationClient$serviceConnection$1] */
    /* JADX WARN: Type inference failed for: r1v8, types: [androidx.room.MultiInstanceInvalidationClient$observer$1] */
    public MultiInstanceInvalidationClient(Context context, String str, InvalidationTracker invalidationTracker) {
        this.name = str;
        this.invalidationTracker = invalidationTracker;
        this.appContext = context.getApplicationContext();
        ContextScope contextScope = invalidationTracker.database.coroutineScope;
        this.coroutineScope = contextScope == null ? null : contextScope;
        this.stopped = new AtomicBoolean(true);
        this.invalidatedTables = SharedFlowKt.MutableSharedFlow(0, 0, BufferOverflow.SUSPEND);
        final String[] strArr = invalidationTracker.tableNames;
        this.observer = new InvalidationTracker.Observer(strArr) { // from class: androidx.room.MultiInstanceInvalidationClient$observer$1
            @Override // androidx.room.InvalidationTracker.Observer
            public final void onInvalidated(Set set) {
                MultiInstanceInvalidationClient multiInstanceInvalidationClient = MultiInstanceInvalidationClient.this;
                if (multiInstanceInvalidationClient.stopped.get()) {
                    return;
                }
                try {
                    IMultiInstanceInvalidationService iMultiInstanceInvalidationService = multiInstanceInvalidationClient.invalidationService;
                    if (iMultiInstanceInvalidationService != null) {
                        iMultiInstanceInvalidationService.broadcastInvalidation((String[]) set.toArray(new String[0]), multiInstanceInvalidationClient.clientId);
                    }
                } catch (RemoteException e) {
                    Log.w("ROOM", "Cannot broadcast invalidation", e);
                }
            }
        };
        this.invalidationCallback = new MultiInstanceInvalidationClient$invalidationCallback$1(this);
        this.serviceConnection = new ServiceConnection() { // from class: androidx.room.MultiInstanceInvalidationClient$serviceConnection$1
            @Override // android.content.ServiceConnection
            public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                IMultiInstanceInvalidationService proxy;
                MultiInstanceInvalidationClient multiInstanceInvalidationClient = MultiInstanceInvalidationClient.this;
                int i = IMultiInstanceInvalidationService.Stub.$r8$clinit;
                if (iBinder == null) {
                    proxy = null;
                } else {
                    IInterface queryLocalInterface = iBinder.queryLocalInterface(IMultiInstanceInvalidationService.DESCRIPTOR);
                    proxy = (queryLocalInterface == null || !(queryLocalInterface instanceof IMultiInstanceInvalidationService)) ? new IMultiInstanceInvalidationService.Stub.Proxy(iBinder) : (IMultiInstanceInvalidationService) queryLocalInterface;
                }
                multiInstanceInvalidationClient.invalidationService = proxy;
                MultiInstanceInvalidationClient multiInstanceInvalidationClient2 = MultiInstanceInvalidationClient.this;
                multiInstanceInvalidationClient2.getClass();
                try {
                    IMultiInstanceInvalidationService iMultiInstanceInvalidationService = multiInstanceInvalidationClient2.invalidationService;
                    if (iMultiInstanceInvalidationService != null) {
                        multiInstanceInvalidationClient2.clientId = iMultiInstanceInvalidationService.registerCallback(multiInstanceInvalidationClient2.invalidationCallback, multiInstanceInvalidationClient2.name);
                    }
                } catch (RemoteException e) {
                    Log.w("ROOM", "Cannot register multi-instance invalidation callback", e);
                }
            }

            @Override // android.content.ServiceConnection
            public final void onServiceDisconnected(ComponentName componentName) {
                MultiInstanceInvalidationClient.this.invalidationService = null;
            }
        };
    }

    public final void start(Intent intent) {
        if (this.stopped.compareAndSet(true, false)) {
            this.appContext.bindService(intent, this.serviceConnection, 1);
            InvalidationTracker invalidationTracker = this.invalidationTracker;
            invalidationTracker.getClass();
            MultiInstanceInvalidationClient$observer$1 multiInstanceInvalidationClient$observer$1 = this.observer;
            multiInstanceInvalidationClient$observer$1.getClass();
            TriggerBasedInvalidationTracker triggerBasedInvalidationTracker = invalidationTracker.implementation;
            Pair validateTableNames$room_runtime_release = triggerBasedInvalidationTracker.validateTableNames$room_runtime_release(multiInstanceInvalidationClient$observer$1.tables);
            String[] strArr = (String[]) validateTableNames$room_runtime_release.component1();
            int[] iArr = (int[]) validateTableNames$room_runtime_release.component2();
            ObserverWrapper observerWrapper = new ObserverWrapper(multiInstanceInvalidationClient$observer$1, iArr, strArr);
            ReentrantLock reentrantLock = invalidationTracker.observerMapLock;
            reentrantLock.lock();
            try {
                ObserverWrapper observerWrapper2 = invalidationTracker.observerMap.containsKey(multiInstanceInvalidationClient$observer$1) ? (ObserverWrapper) MapsKt__MapsKt.getValue(multiInstanceInvalidationClient$observer$1, invalidationTracker.observerMap) : (ObserverWrapper) invalidationTracker.observerMap.put(multiInstanceInvalidationClient$observer$1, observerWrapper);
                reentrantLock.unlock();
                if (observerWrapper2 == null) {
                    triggerBasedInvalidationTracker.observedTableStates.onObserverAdded$room_runtime_release(iArr);
                }
            } catch (Throwable th) {
                reentrantLock.unlock();
                throw th;
            }
        }
    }

    public final void stop() {
        if (this.stopped.compareAndSet(false, true)) {
            MultiInstanceInvalidationClient$observer$1 multiInstanceInvalidationClient$observer$1 = this.observer;
            InvalidationTracker invalidationTracker = this.invalidationTracker;
            ReentrantLock reentrantLock = invalidationTracker.observerMapLock;
            reentrantLock.lock();
            try {
                ObserverWrapper observerWrapper = (ObserverWrapper) invalidationTracker.observerMap.remove(multiInstanceInvalidationClient$observer$1);
                if (observerWrapper != null && invalidationTracker.implementation.observedTableStates.onObserverRemoved$room_runtime_release(observerWrapper.tableIds)) {
                    BuildersKt.runBlocking(EmptyCoroutineContext.INSTANCE, new InvalidationTracker$removeObserver$1(invalidationTracker, null));
                }
                try {
                    IMultiInstanceInvalidationService iMultiInstanceInvalidationService = this.invalidationService;
                    if (iMultiInstanceInvalidationService != null) {
                        iMultiInstanceInvalidationService.unregisterCallback(this.invalidationCallback, this.clientId);
                    }
                } catch (RemoteException e) {
                    Log.w("ROOM", "Cannot unregister multi-instance invalidation callback", e);
                }
                this.appContext.unbindService(this.serviceConnection);
            } finally {
                reentrantLock.unlock();
            }
        }
    }
}

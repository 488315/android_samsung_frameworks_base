package com.android.server.backup.transport;

import android.app.backup.IBackupManagerMonitor;
import android.content.pm.PackageInfo;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.util.Slog;
import com.android.internal.backup.IBackupTransport;
import com.android.internal.infra.AndroidFuture;
import com.android.server.backup.BackupAndRestoreFeatureFlags;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class BackupTransportClient {
    public final IBackupTransport mTransportBinder;
    public final TransportStatusCallbackPool mCallbackPool = new TransportStatusCallbackPool();
    public final TransportFutures mTransportFutures = new TransportFutures();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TransportFutures {
        public final Object mActiveFuturesLock = new Object();
        public final Set mActiveFutures = new HashSet();

        public final AndroidFuture newFuture() {
            AndroidFuture androidFuture = new AndroidFuture();
            synchronized (this.mActiveFuturesLock) {
                ((HashSet) this.mActiveFutures).add(androidFuture);
            }
            return androidFuture;
        }

        public final void remove(AndroidFuture androidFuture) {
            synchronized (this.mActiveFuturesLock) {
                ((HashSet) this.mActiveFutures).remove(androidFuture);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TransportStatusCallbackPool {
        public final Object mPoolLock = new Object();
        public final Queue mCallbackPool = new ArrayDeque();
        public final Set mActiveCallbacks = new HashSet();

        public final TransportStatusCallback acquire() {
            TransportStatusCallback transportStatusCallback;
            synchronized (this.mPoolLock) {
                try {
                    transportStatusCallback = (TransportStatusCallback) ((ArrayDeque) this.mCallbackPool).poll();
                    if (transportStatusCallback == null) {
                        transportStatusCallback = new TransportStatusCallback();
                    }
                    synchronized (transportStatusCallback) {
                        transportStatusCallback.mHasCompletedOperation = false;
                        transportStatusCallback.mOperationStatus = 0;
                    }
                    ((HashSet) this.mActiveCallbacks).add(transportStatusCallback);
                } catch (Throwable th) {
                    throw th;
                }
            }
            return transportStatusCallback;
        }

        public final void recycle(TransportStatusCallback transportStatusCallback) {
            synchronized (this.mPoolLock) {
                try {
                    ((HashSet) this.mActiveCallbacks).remove(transportStatusCallback);
                    if (((ArrayDeque) this.mCallbackPool).size() > 100) {
                        Slog.d("BackupTransportClient", "TransportStatusCallback pool size exceeded");
                    } else {
                        ((ArrayDeque) this.mCallbackPool).add(transportStatusCallback);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public BackupTransportClient(IBackupTransport iBackupTransport) {
        this.mTransportBinder = iBackupTransport;
    }

    public final void abortFullRestore() {
        TransportStatusCallbackPool transportStatusCallbackPool = this.mCallbackPool;
        TransportStatusCallback acquire = transportStatusCallbackPool.acquire();
        try {
            this.mTransportBinder.abortFullRestore(acquire);
            acquire.getOperationStatus();
        } finally {
            transportStatusCallbackPool.recycle(acquire);
        }
    }

    public final void clearBackupData(PackageInfo packageInfo) {
        TransportStatusCallbackPool transportStatusCallbackPool = this.mCallbackPool;
        TransportStatusCallback acquire = transportStatusCallbackPool.acquire();
        try {
            this.mTransportBinder.clearBackupData(packageInfo, acquire);
            acquire.getOperationStatus();
        } finally {
            transportStatusCallbackPool.recycle(acquire);
        }
    }

    public final int finishBackup() {
        TransportStatusCallbackPool transportStatusCallbackPool = this.mCallbackPool;
        TransportStatusCallback acquire = transportStatusCallbackPool.acquire();
        try {
            this.mTransportBinder.finishBackup(acquire);
            return acquire.getOperationStatus();
        } finally {
            transportStatusCallbackPool.recycle(acquire);
        }
    }

    public final IBackupManagerMonitor getBackupManagerMonitor() {
        AndroidFuture newFuture = this.mTransportFutures.newFuture();
        this.mTransportBinder.getBackupManagerMonitor(newFuture);
        return IBackupManagerMonitor.Stub.asInterface((IBinder) getFutureResult(newFuture));
    }

    public final long getBackupQuota(String str, boolean z) {
        AndroidFuture newFuture = this.mTransportFutures.newFuture();
        this.mTransportBinder.getBackupQuota(str, z, newFuture);
        Long l = (Long) getFutureResult(newFuture);
        if (l == null) {
            return -1000L;
        }
        return l.longValue();
    }

    public final Object getFutureResult(AndroidFuture androidFuture) {
        TransportFutures transportFutures = this.mTransportFutures;
        try {
            try {
                return androidFuture.get(BackupAndRestoreFeatureFlags.getBackupTransportFutureTimeoutMillis(), TimeUnit.MILLISECONDS);
            } catch (InterruptedException | CancellationException | ExecutionException | TimeoutException e) {
                Slog.w("BackupTransportClient", "Failed to get result from transport:", e);
                transportFutures.remove(androidFuture);
                return null;
            }
        } finally {
            transportFutures.remove(androidFuture);
        }
    }

    public final int getNextFullRestoreDataChunk(ParcelFileDescriptor parcelFileDescriptor) {
        TransportStatusCallbackPool transportStatusCallbackPool = this.mCallbackPool;
        TransportStatusCallback acquire = transportStatusCallbackPool.acquire();
        try {
            this.mTransportBinder.getNextFullRestoreDataChunk(parcelFileDescriptor, acquire);
            return acquire.getOperationStatus();
        } finally {
            transportStatusCallbackPool.recycle(acquire);
        }
    }

    public final int getTransportFlags() {
        AndroidFuture newFuture = this.mTransportFutures.newFuture();
        this.mTransportBinder.getTransportFlags(newFuture);
        Integer num = (Integer) getFutureResult(newFuture);
        if (num == null) {
            return -1000;
        }
        return num.intValue();
    }

    public final String name() {
        AndroidFuture newFuture = this.mTransportFutures.newFuture();
        this.mTransportBinder.name(newFuture);
        return (String) getFutureResult(newFuture);
    }

    public final void onBecomingUnusable() {
        TransportStatusCallbackPool transportStatusCallbackPool = this.mCallbackPool;
        synchronized (transportStatusCallbackPool.mPoolLock) {
            Iterator it = ((HashSet) transportStatusCallbackPool.mActiveCallbacks).iterator();
            while (it.hasNext()) {
                TransportStatusCallback transportStatusCallback = (TransportStatusCallback) it.next();
                try {
                    transportStatusCallback.onOperationCompleteWithStatus(-1000);
                    transportStatusCallback.getOperationStatus();
                } catch (RemoteException unused) {
                }
                if (((ArrayDeque) transportStatusCallbackPool.mCallbackPool).size() < 100) {
                    ((ArrayDeque) transportStatusCallbackPool.mCallbackPool).add(transportStatusCallback);
                }
            }
            ((HashSet) transportStatusCallbackPool.mActiveCallbacks).clear();
        }
        TransportFutures transportFutures = this.mTransportFutures;
        synchronized (transportFutures.mActiveFuturesLock) {
            Iterator it2 = ((HashSet) transportFutures.mActiveFutures).iterator();
            while (it2.hasNext()) {
                try {
                    ((AndroidFuture) it2.next()).cancel(true);
                } catch (CancellationException unused2) {
                }
            }
            ((HashSet) transportFutures.mActiveFutures).clear();
        }
    }

    public final int performFullBackup(PackageInfo packageInfo, ParcelFileDescriptor parcelFileDescriptor, int i) {
        TransportStatusCallbackPool transportStatusCallbackPool = this.mCallbackPool;
        TransportStatusCallback acquire = transportStatusCallbackPool.acquire();
        try {
            this.mTransportBinder.performFullBackup(packageInfo, parcelFileDescriptor, i, acquire);
            return acquire.getOperationStatus();
        } finally {
            transportStatusCallbackPool.recycle(acquire);
        }
    }

    public final long requestFullBackupTime() {
        AndroidFuture newFuture = this.mTransportFutures.newFuture();
        this.mTransportBinder.requestFullBackupTime(newFuture);
        Long l = (Long) getFutureResult(newFuture);
        if (l == null) {
            return -1000L;
        }
        return l.longValue();
    }

    public final int sendBackupData(int i) {
        TransportStatusCallbackPool transportStatusCallbackPool = this.mCallbackPool;
        TransportStatusCallback acquire = transportStatusCallbackPool.acquire();
        this.mTransportBinder.sendBackupData(i, acquire);
        try {
            return acquire.getOperationStatus();
        } finally {
            transportStatusCallbackPool.recycle(acquire);
        }
    }
}

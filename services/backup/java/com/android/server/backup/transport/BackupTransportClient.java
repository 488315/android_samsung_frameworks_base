package com.android.server.backup.transport;

import android.app.backup.IBackupManagerMonitor;
import android.app.backup.RestoreDescription;
import android.content.Intent;
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
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* loaded from: classes.dex */
public class BackupTransportClient {
  public final IBackupTransport mTransportBinder;
  public final TransportStatusCallbackPool mCallbackPool = new TransportStatusCallbackPool();
  public final TransportFutures mTransportFutures = new TransportFutures();

  /* JADX WARN: Multi-variable type inference failed */
  public BackupTransportClient(IBackupTransport iBackupTransport) {
    this.mTransportBinder = iBackupTransport;
  }

  public String name() {
    AndroidFuture newFuture = this.mTransportFutures.newFuture();
    this.mTransportBinder.name(newFuture);
    return (String) getFutureResult(newFuture);
  }

  public Intent configurationIntent() {
    AndroidFuture newFuture = this.mTransportFutures.newFuture();
    this.mTransportBinder.configurationIntent(newFuture);
    return (Intent) getFutureResult(newFuture);
  }

  public String currentDestinationString() {
    AndroidFuture newFuture = this.mTransportFutures.newFuture();
    this.mTransportBinder.currentDestinationString(newFuture);
    return (String) getFutureResult(newFuture);
  }

  public Intent dataManagementIntent() {
    AndroidFuture newFuture = this.mTransportFutures.newFuture();
    this.mTransportBinder.dataManagementIntent(newFuture);
    return (Intent) getFutureResult(newFuture);
  }

  public CharSequence dataManagementIntentLabel() {
    AndroidFuture newFuture = this.mTransportFutures.newFuture();
    this.mTransportBinder.dataManagementIntentLabel(newFuture);
    return (CharSequence) getFutureResult(newFuture);
  }

  public String transportDirName() {
    AndroidFuture newFuture = this.mTransportFutures.newFuture();
    this.mTransportBinder.transportDirName(newFuture);
    return (String) getFutureResult(newFuture);
  }

  public int initializeDevice() {
    TransportStatusCallback acquire = this.mCallbackPool.acquire();
    try {
      this.mTransportBinder.initializeDevice(acquire);
      return acquire.getOperationStatus();
    } finally {
      this.mCallbackPool.recycle(acquire);
    }
  }

  public int clearBackupData(PackageInfo packageInfo) {
    TransportStatusCallback acquire = this.mCallbackPool.acquire();
    try {
      this.mTransportBinder.clearBackupData(packageInfo, acquire);
      return acquire.getOperationStatus();
    } finally {
      this.mCallbackPool.recycle(acquire);
    }
  }

  public int finishBackup() {
    TransportStatusCallback acquire = this.mCallbackPool.acquire();
    try {
      this.mTransportBinder.finishBackup(acquire);
      return acquire.getOperationStatus();
    } finally {
      this.mCallbackPool.recycle(acquire);
    }
  }

  public long requestBackupTime() {
    AndroidFuture newFuture = this.mTransportFutures.newFuture();
    this.mTransportBinder.requestBackupTime(newFuture);
    Long l = (Long) getFutureResult(newFuture);
    if (l == null) {
      return -1000L;
    }
    return l.longValue();
  }

  public int performBackup(
      PackageInfo packageInfo, ParcelFileDescriptor parcelFileDescriptor, int i) {
    TransportStatusCallback acquire = this.mCallbackPool.acquire();
    try {
      this.mTransportBinder.performBackup(packageInfo, parcelFileDescriptor, i, acquire);
      return acquire.getOperationStatus();
    } finally {
      this.mCallbackPool.recycle(acquire);
    }
  }

  public List getAvailableRestoreSets() {
    AndroidFuture newFuture = this.mTransportFutures.newFuture();
    this.mTransportBinder.getAvailableRestoreSets(newFuture);
    return (List) getFutureResult(newFuture);
  }

  public long getCurrentRestoreSet() {
    AndroidFuture newFuture = this.mTransportFutures.newFuture();
    this.mTransportBinder.getCurrentRestoreSet(newFuture);
    Long l = (Long) getFutureResult(newFuture);
    if (l == null) {
      return -1000L;
    }
    return l.longValue();
  }

  public int startRestore(long j, PackageInfo[] packageInfoArr) {
    TransportStatusCallback acquire = this.mCallbackPool.acquire();
    try {
      this.mTransportBinder.startRestore(j, packageInfoArr, acquire);
      return acquire.getOperationStatus();
    } finally {
      this.mCallbackPool.recycle(acquire);
    }
  }

  public RestoreDescription nextRestorePackage() {
    AndroidFuture newFuture = this.mTransportFutures.newFuture();
    this.mTransportBinder.nextRestorePackage(newFuture);
    return (RestoreDescription) getFutureResult(newFuture);
  }

  public int getRestoreData(ParcelFileDescriptor parcelFileDescriptor) {
    TransportStatusCallback acquire = this.mCallbackPool.acquire();
    try {
      this.mTransportBinder.getRestoreData(parcelFileDescriptor, acquire);
      return acquire.getOperationStatus();
    } finally {
      this.mCallbackPool.recycle(acquire);
    }
  }

  public void finishRestore() {
    TransportStatusCallback acquire = this.mCallbackPool.acquire();
    try {
      this.mTransportBinder.finishRestore(acquire);
      acquire.getOperationStatus();
    } finally {
      this.mCallbackPool.recycle(acquire);
    }
  }

  public long requestFullBackupTime() {
    AndroidFuture newFuture = this.mTransportFutures.newFuture();
    this.mTransportBinder.requestFullBackupTime(newFuture);
    Long l = (Long) getFutureResult(newFuture);
    if (l == null) {
      return -1000L;
    }
    return l.longValue();
  }

  public int performFullBackup(
      PackageInfo packageInfo, ParcelFileDescriptor parcelFileDescriptor, int i) {
    TransportStatusCallback acquire = this.mCallbackPool.acquire();
    try {
      this.mTransportBinder.performFullBackup(packageInfo, parcelFileDescriptor, i, acquire);
      return acquire.getOperationStatus();
    } finally {
      this.mCallbackPool.recycle(acquire);
    }
  }

  public int checkFullBackupSize(long j) {
    TransportStatusCallback acquire = this.mCallbackPool.acquire();
    try {
      this.mTransportBinder.checkFullBackupSize(j, acquire);
      return acquire.getOperationStatus();
    } finally {
      this.mCallbackPool.recycle(acquire);
    }
  }

  public int sendBackupData(int i) {
    TransportStatusCallback acquire = this.mCallbackPool.acquire();
    this.mTransportBinder.sendBackupData(i, acquire);
    try {
      return acquire.getOperationStatus();
    } finally {
      this.mCallbackPool.recycle(acquire);
    }
  }

  public void cancelFullBackup() {
    TransportStatusCallback acquire = this.mCallbackPool.acquire();
    try {
      this.mTransportBinder.cancelFullBackup(acquire);
      acquire.getOperationStatus();
    } finally {
      this.mCallbackPool.recycle(acquire);
    }
  }

  public boolean isAppEligibleForBackup(PackageInfo packageInfo, boolean z) {
    AndroidFuture newFuture = this.mTransportFutures.newFuture();
    this.mTransportBinder.isAppEligibleForBackup(packageInfo, z, newFuture);
    Boolean bool = (Boolean) getFutureResult(newFuture);
    return bool != null && bool.booleanValue();
  }

  public long getBackupQuota(String str, boolean z) {
    AndroidFuture newFuture = this.mTransportFutures.newFuture();
    this.mTransportBinder.getBackupQuota(str, z, newFuture);
    Long l = (Long) getFutureResult(newFuture);
    if (l == null) {
      return -1000L;
    }
    return l.longValue();
  }

  public int getNextFullRestoreDataChunk(ParcelFileDescriptor parcelFileDescriptor) {
    TransportStatusCallback acquire = this.mCallbackPool.acquire();
    try {
      this.mTransportBinder.getNextFullRestoreDataChunk(parcelFileDescriptor, acquire);
      return acquire.getOperationStatus();
    } finally {
      this.mCallbackPool.recycle(acquire);
    }
  }

  public int abortFullRestore() {
    TransportStatusCallback acquire = this.mCallbackPool.acquire();
    try {
      this.mTransportBinder.abortFullRestore(acquire);
      return acquire.getOperationStatus();
    } finally {
      this.mCallbackPool.recycle(acquire);
    }
  }

  public int getTransportFlags() {
    AndroidFuture newFuture = this.mTransportFutures.newFuture();
    this.mTransportBinder.getTransportFlags(newFuture);
    Integer num = (Integer) getFutureResult(newFuture);
    if (num == null) {
      return -1000;
    }
    return num.intValue();
  }

  public IBackupManagerMonitor getBackupManagerMonitor() {
    AndroidFuture newFuture = this.mTransportFutures.newFuture();
    this.mTransportBinder.getBackupManagerMonitor(newFuture);
    return IBackupManagerMonitor.Stub.asInterface((IBinder) getFutureResult(newFuture));
  }

  public void onBecomingUnusable() {
    this.mCallbackPool.cancelActiveCallbacks();
    this.mTransportFutures.cancelActiveFutures();
  }

  public final Object getFutureResult(AndroidFuture androidFuture) {
    try {
      try {
        return androidFuture.get(
            BackupAndRestoreFeatureFlags.getBackupTransportFutureTimeoutMillis(),
            TimeUnit.MILLISECONDS);
      } catch (InterruptedException
          | CancellationException
          | ExecutionException
          | TimeoutException e) {
        Slog.w("BackupTransportClient", "Failed to get result from transport:", e);
        this.mTransportFutures.remove(androidFuture);
        return null;
      }
    } finally {
      this.mTransportFutures.remove(androidFuture);
    }
  }

  public class TransportFutures {
    public final Set mActiveFutures;
    public final Object mActiveFuturesLock;

    public TransportFutures() {
      this.mActiveFuturesLock = new Object();
      this.mActiveFutures = new HashSet();
    }

    public AndroidFuture newFuture() {
      AndroidFuture androidFuture = new AndroidFuture();
      synchronized (this.mActiveFuturesLock) {
        this.mActiveFutures.add(androidFuture);
      }
      return androidFuture;
    }

    public void remove(AndroidFuture androidFuture) {
      synchronized (this.mActiveFuturesLock) {
        this.mActiveFutures.remove(androidFuture);
      }
    }

    public void cancelActiveFutures() {
      synchronized (this.mActiveFuturesLock) {
        Iterator it = this.mActiveFutures.iterator();
        while (it.hasNext()) {
          try {
            ((AndroidFuture) it.next()).cancel(true);
          } catch (CancellationException unused) {
          }
        }
        this.mActiveFutures.clear();
      }
    }
  }

  public class TransportStatusCallbackPool {
    public final Set mActiveCallbacks;
    public final Queue mCallbackPool;
    public final Object mPoolLock;

    public TransportStatusCallbackPool() {
      this.mPoolLock = new Object();
      this.mCallbackPool = new ArrayDeque();
      this.mActiveCallbacks = new HashSet();
    }

    public TransportStatusCallback acquire() {
      TransportStatusCallback transportStatusCallback;
      synchronized (this.mPoolLock) {
        transportStatusCallback = (TransportStatusCallback) this.mCallbackPool.poll();
        if (transportStatusCallback == null) {
          transportStatusCallback = new TransportStatusCallback();
        }
        transportStatusCallback.reset();
        this.mActiveCallbacks.add(transportStatusCallback);
      }
      return transportStatusCallback;
    }

    public void recycle(TransportStatusCallback transportStatusCallback) {
      synchronized (this.mPoolLock) {
        this.mActiveCallbacks.remove(transportStatusCallback);
        if (this.mCallbackPool.size() > 100) {
          Slog.d("BackupTransportClient", "TransportStatusCallback pool size exceeded");
        } else {
          this.mCallbackPool.add(transportStatusCallback);
        }
      }
    }

    public void cancelActiveCallbacks() {
      synchronized (this.mPoolLock) {
        for (TransportStatusCallback transportStatusCallback : this.mActiveCallbacks) {
          try {
            transportStatusCallback.onOperationCompleteWithStatus(-1000);
            transportStatusCallback.getOperationStatus();
          } catch (RemoteException unused) {
          }
          if (this.mCallbackPool.size() < 100) {
            this.mCallbackPool.add(transportStatusCallback);
          }
        }
        this.mActiveCallbacks.clear();
      }
    }
  }
}

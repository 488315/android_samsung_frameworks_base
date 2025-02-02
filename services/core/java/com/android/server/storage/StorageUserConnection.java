package com.android.server.storage;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.ParcelableException;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.storage.StorageManagerInternal;
import android.os.storage.StorageVolume;
import android.service.storage.IExternalStorageService;
import android.util.SparseArray;
import android.util.sysfwutil.Slog;
import com.android.internal.util.Preconditions;
import com.android.server.LocalServices;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

/* loaded from: classes3.dex */
public final class StorageUserConnection {
  public final Context mContext;
  public final HandlerThread mHandlerThread;
  public final StorageSessionController mSessionController;
  public final StorageManagerInternal mSmInternal;
  public final int mUserId;
  public final Object mSessionsLock = new Object();
  public final ActiveConnection mActiveConnection = new ActiveConnection();
  public final Map mSessions = new HashMap();
  public final SparseArray mUidsBlockedOnIo = new SparseArray();

  public interface AsyncStorageServiceCall {
    void run(IExternalStorageService iExternalStorageService, RemoteCallback remoteCallback);
  }

  public StorageUserConnection(
      Context context, int i, StorageSessionController storageSessionController) {
    Objects.requireNonNull(context);
    this.mContext = context;
    int checkArgumentNonnegative = Preconditions.checkArgumentNonnegative(i);
    this.mUserId = checkArgumentNonnegative;
    this.mSessionController = storageSessionController;
    this.mSmInternal =
        (StorageManagerInternal) LocalServices.getService(StorageManagerInternal.class);
    HandlerThread handlerThread =
        new HandlerThread("StorageUserConnectionThread-" + checkArgumentNonnegative);
    this.mHandlerThread = handlerThread;
    handlerThread.start();
  }

  public void startSession(
      String str, ParcelFileDescriptor parcelFileDescriptor, String str2, String str3) {
    Objects.requireNonNull(str);
    Objects.requireNonNull(parcelFileDescriptor);
    Objects.requireNonNull(str2);
    Objects.requireNonNull(str3);
    Session session = new Session(str, str2, str3);
    synchronized (this.mSessionsLock) {
      Preconditions.checkArgument(!this.mSessions.containsKey(str));
      this.mSessions.put(str, session);
    }
    this.mActiveConnection.startSession(session, parcelFileDescriptor);
  }

  public void notifyVolumeStateChanged(String str, StorageVolume storageVolume) {
    Objects.requireNonNull(str);
    Objects.requireNonNull(storageVolume);
    synchronized (this.mSessionsLock) {
      if (!this.mSessions.containsKey(str)) {
        Slog.i("StorageUserConnection", "No session found for sessionId: " + str);
        return;
      }
      this.mActiveConnection.notifyVolumeStateChanged(str, storageVolume);
    }
  }

  public void freeCache(String str, long j) {
    synchronized (this.mSessionsLock) {
      Iterator it = this.mSessions.keySet().iterator();
      while (it.hasNext()) {
        this.mActiveConnection.freeCache((String) it.next(), str, j);
      }
    }
  }

  public void notifyAnrDelayStarted(String str, int i, int i2, int i3) {
    List primaryVolumeIds = this.mSmInternal.getPrimaryVolumeIds();
    synchronized (this.mSessionsLock) {
      Iterator it = this.mSessions.keySet().iterator();
      while (it.hasNext()) {
        if (primaryVolumeIds.contains((String) it.next())) {
          this.mActiveConnection.notifyAnrDelayStarted(str, i, i2, i3);
          return;
        }
      }
    }
  }

  public Session removeSession(String str) {
    Session session;
    synchronized (this.mSessionsLock) {
      this.mUidsBlockedOnIo.clear();
      session = (Session) this.mSessions.remove(str);
    }
    return session;
  }

  public void removeSessionAndWait(String str) {
    Session removeSession = removeSession(str);
    if (removeSession == null) {
      Slog.i("StorageUserConnection", "No session found for id: " + str);
      return;
    }
    Slog.i("StorageUserConnection", "Waiting for session end " + removeSession + " ...");
    this.mActiveConnection.endSession(removeSession);
  }

  public void resetUserSessions() {
    synchronized (this.mSessionsLock) {
      if (this.mSessions.isEmpty()) {
        return;
      }
      this.mSmInternal.resetUser(this.mUserId);
    }
  }

  public void removeAllSessions() {
    synchronized (this.mSessionsLock) {
      Slog.i(
          "StorageUserConnection",
          "Removing  " + this.mSessions.size() + " sessions for user: " + this.mUserId + "...");
      this.mSessions.clear();
    }
  }

  public void close() {
    this.mActiveConnection.close();
    this.mHandlerThread.quit();
  }

  public Set getAllSessionIds() {
    HashSet hashSet;
    synchronized (this.mSessionsLock) {
      hashSet = new HashSet(this.mSessions.keySet());
    }
    return hashSet;
  }

  public void notifyAppIoBlocked(String str, int i, int i2, int i3) {
    synchronized (this.mSessionsLock) {
      this.mUidsBlockedOnIo.put(
          i, Integer.valueOf(((Integer) this.mUidsBlockedOnIo.get(i, 0)).intValue() + 1));
    }
  }

  public void notifyAppIoResumed(String str, int i, int i2, int i3) {
    synchronized (this.mSessionsLock) {
      int intValue = ((Integer) this.mUidsBlockedOnIo.get(i, 0)).intValue();
      if (intValue == 0) {
        Slog.w("StorageUserConnection", "Unexpected app IO resumption for uid: " + i);
      }
      if (intValue <= 1) {
        this.mUidsBlockedOnIo.remove(i);
      } else {
        this.mUidsBlockedOnIo.put(i, Integer.valueOf(intValue - 1));
      }
    }
  }

  public boolean isAppIoBlocked(int i) {
    boolean contains;
    synchronized (this.mSessionsLock) {
      contains = this.mUidsBlockedOnIo.contains(i);
    }
    return contains;
  }

  public final class ActiveConnection implements AutoCloseable {
    public final Object mLock;
    public final ArrayList mOutstandingOps;
    public CompletableFuture mRemoteFuture;
    public ServiceConnection mServiceConnection;

    public ActiveConnection() {
      this.mLock = new Object();
      this.mOutstandingOps = new ArrayList();
    }

    @Override // java.lang.AutoCloseable
    public void close() {
      ServiceConnection serviceConnection;
      synchronized (this.mLock) {
        Slog.i(
            "StorageUserConnection",
            "Closing connection for user " + StorageUserConnection.this.mUserId);
        serviceConnection = this.mServiceConnection;
        this.mServiceConnection = null;
        CompletableFuture completableFuture = this.mRemoteFuture;
        if (completableFuture != null) {
          completableFuture.cancel(true);
          this.mRemoteFuture = null;
        }
        Iterator it = this.mOutstandingOps.iterator();
        while (it.hasNext()) {
          ((CompletableFuture) it.next()).cancel(true);
        }
        this.mOutstandingOps.clear();
      }
      if (serviceConnection != null) {
        try {
          StorageUserConnection.this.mContext.unbindService(serviceConnection);
        } catch (Exception e) {
          Slog.w("StorageUserConnection", "Failed to unbind service", e);
        }
      }
    }

    public final void asyncBestEffort(Consumer consumer) {
      synchronized (this.mLock) {
        CompletableFuture completableFuture = this.mRemoteFuture;
        if (completableFuture == null) {
          Slog.w("StorageUserConnection", "Dropping async request service is not bound");
          return;
        }
        IExternalStorageService iExternalStorageService =
            (IExternalStorageService) completableFuture.getNow(null);
        if (iExternalStorageService == null) {
          Slog.w("StorageUserConnection", "Dropping async request service is not connected");
        } else {
          consumer.accept(iExternalStorageService);
        }
      }
    }

    public final void waitForAsyncVoid(AsyncStorageServiceCall asyncStorageServiceCall) {
      final CompletableFuture completableFuture = new CompletableFuture();
      waitForAsync(
          asyncStorageServiceCall,
          new RemoteCallback(
              new RemoteCallback
                  .OnResultListener() { // from class:
                                        // com.android.server.storage.StorageUserConnection$ActiveConnection$$ExternalSyntheticLambda3
                public final void onResult(Bundle bundle) {
                  StorageUserConnection.ActiveConnection.this.lambda$waitForAsyncVoid$0(
                      completableFuture, bundle);
                }
              }),
          completableFuture,
          this.mOutstandingOps,
          20L);
    }

    public final Object waitForAsync(
        final AsyncStorageServiceCall asyncStorageServiceCall,
        final RemoteCallback remoteCallback,
        final CompletableFuture completableFuture,
        ArrayList arrayList,
        long j) {
      CompletableFuture connectIfNeeded = connectIfNeeded();
      try {
        synchronized (this.mLock) {
          arrayList.add(completableFuture);
        }
        Object obj =
            connectIfNeeded
                .thenCompose(
                    new Function() { // from class:
                                     // com.android.server.storage.StorageUserConnection$ActiveConnection$$ExternalSyntheticLambda5
                      @Override // java.util.function.Function
                      public final Object apply(Object obj2) {
                        CompletionStage lambda$waitForAsync$1;
                        lambda$waitForAsync$1 =
                            StorageUserConnection.ActiveConnection.lambda$waitForAsync$1(
                                StorageUserConnection.AsyncStorageServiceCall.this,
                                remoteCallback,
                                completableFuture,
                                (IExternalStorageService) obj2);
                        return lambda$waitForAsync$1;
                      }
                    })
                .get(j, TimeUnit.SECONDS);
        synchronized (this.mLock) {
          arrayList.remove(completableFuture);
        }
        return obj;
      } catch (Throwable th) {
        synchronized (this.mLock) {
          arrayList.remove(completableFuture);
          throw th;
        }
      }
    }

    public static /* synthetic */ CompletionStage lambda$waitForAsync$1(
        AsyncStorageServiceCall asyncStorageServiceCall,
        RemoteCallback remoteCallback,
        CompletableFuture completableFuture,
        IExternalStorageService iExternalStorageService) {
      try {
        asyncStorageServiceCall.run(iExternalStorageService, remoteCallback);
      } catch (RemoteException e) {
        completableFuture.completeExceptionally(e);
      }
      return completableFuture;
    }

    public static /* synthetic */ void lambda$startSession$2(
        Session session,
        ParcelFileDescriptor parcelFileDescriptor,
        IExternalStorageService iExternalStorageService,
        RemoteCallback remoteCallback) {
      iExternalStorageService.startSession(
          session.sessionId,
          3,
          parcelFileDescriptor,
          session.upperPath,
          session.lowerPath,
          remoteCallback);
    }

    public void startSession(
        final Session session, final ParcelFileDescriptor parcelFileDescriptor) {
      try {
        try {
          waitForAsyncVoid(
              new AsyncStorageServiceCall() { // from class:
                                              // com.android.server.storage.StorageUserConnection$ActiveConnection$$ExternalSyntheticLambda1
                @Override // com.android.server.storage.StorageUserConnection.AsyncStorageServiceCall
                public final void run(
                    IExternalStorageService iExternalStorageService,
                    RemoteCallback remoteCallback) {
                  StorageUserConnection.ActiveConnection.lambda$startSession$2(
                      StorageUserConnection.Session.this,
                      parcelFileDescriptor,
                      iExternalStorageService,
                      remoteCallback);
                }
              });
        } catch (Exception e) {
          throw new StorageSessionController.ExternalStorageServiceException(
              "Failed to start session: " + session, e);
        }
      } finally {
        try {
          parcelFileDescriptor.close();
        } catch (IOException unused) {
        }
      }
    }

    public void endSession(final Session session) {
      try {
        waitForAsyncVoid(
            new AsyncStorageServiceCall() { // from class:
                                            // com.android.server.storage.StorageUserConnection$ActiveConnection$$ExternalSyntheticLambda2
              @Override // com.android.server.storage.StorageUserConnection.AsyncStorageServiceCall
              public final void run(
                  IExternalStorageService iExternalStorageService, RemoteCallback remoteCallback) {
                StorageUserConnection.ActiveConnection.lambda$endSession$3(
                    StorageUserConnection.Session.this, iExternalStorageService, remoteCallback);
              }
            });
      } catch (Exception e) {
        throw new StorageSessionController.ExternalStorageServiceException(
            "Failed to end session: " + session, e);
      }
    }

    public static /* synthetic */ void lambda$endSession$3(
        Session session,
        IExternalStorageService iExternalStorageService,
        RemoteCallback remoteCallback) {
      iExternalStorageService.endSession(session.sessionId, remoteCallback);
    }

    public void notifyVolumeStateChanged(final String str, final StorageVolume storageVolume) {
      try {
        waitForAsyncVoid(
            new AsyncStorageServiceCall() { // from class:
                                            // com.android.server.storage.StorageUserConnection$ActiveConnection$$ExternalSyntheticLambda4
              @Override // com.android.server.storage.StorageUserConnection.AsyncStorageServiceCall
              public final void run(
                  IExternalStorageService iExternalStorageService, RemoteCallback remoteCallback) {
                iExternalStorageService.notifyVolumeStateChanged(
                    str, storageVolume, remoteCallback);
              }
            });
      } catch (Exception e) {
        throw new StorageSessionController.ExternalStorageServiceException(
            "Failed to notify volume state changed for vol : " + storageVolume, e);
      }
    }

    public void freeCache(final String str, final String str2, final long j) {
      try {
        waitForAsyncVoid(
            new AsyncStorageServiceCall() { // from class:
                                            // com.android.server.storage.StorageUserConnection$ActiveConnection$$ExternalSyntheticLambda0
              @Override // com.android.server.storage.StorageUserConnection.AsyncStorageServiceCall
              public final void run(
                  IExternalStorageService iExternalStorageService, RemoteCallback remoteCallback) {
                iExternalStorageService.freeCache(str, str2, j, remoteCallback);
              }
            });
      } catch (Exception e) {
        throw new StorageSessionController.ExternalStorageServiceException(
            "Failed to free " + j + " bytes for volumeUuid : " + str2, e);
      }
    }

    public void notifyAnrDelayStarted(final String str, final int i, final int i2, final int i3) {
      asyncBestEffort(
          new Consumer() { // from class:
                           // com.android.server.storage.StorageUserConnection$ActiveConnection$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              StorageUserConnection.ActiveConnection.lambda$notifyAnrDelayStarted$6(
                  str, i, i2, i3, (IExternalStorageService) obj);
            }
          });
    }

    public static /* synthetic */ void lambda$notifyAnrDelayStarted$6(
        String str, int i, int i2, int i3, IExternalStorageService iExternalStorageService) {
      try {
        iExternalStorageService.notifyAnrDelayStarted(str, i, i2, i3);
      } catch (RemoteException e) {
        Slog.w("StorageUserConnection", "Failed to notify ANR delay started", e);
      }
    }

    /* renamed from: setResult, reason: merged with bridge method [inline-methods] */
    public final void lambda$waitForAsyncVoid$0(
        Bundle bundle, CompletableFuture completableFuture) {
      ParcelableException parcelableException =
          (ParcelableException)
              bundle.getParcelable(
                  "android.service.storage.extra.error", ParcelableException.class);
      if (parcelableException != null) {
        completableFuture.completeExceptionally(parcelableException);
      } else {
        completableFuture.complete(null);
      }
    }

    public final CompletableFuture connectIfNeeded() {
      ComponentName externalStorageServiceComponentName =
          StorageUserConnection.this.mSessionController.getExternalStorageServiceComponentName();
      if (externalStorageServiceComponentName == null) {
        throw new StorageSessionController.ExternalStorageServiceException(
            "Not ready to bind to the ExternalStorageService for user "
                + StorageUserConnection.this.mUserId);
      }
      synchronized (this.mLock) {
        CompletableFuture completableFuture = this.mRemoteFuture;
        if (completableFuture != null) {
          return completableFuture;
        }
        final CompletableFuture completableFuture2 = new CompletableFuture();
        this.mServiceConnection =
            new ServiceConnection() { // from class:
                                      // com.android.server.storage.StorageUserConnection.ActiveConnection.1
              @Override // android.content.ServiceConnection
              public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                Slog.i(
                    "StorageUserConnection",
                    "Service: ["
                        + componentName
                        + "] connected. User ["
                        + StorageUserConnection.this.mUserId
                        + "]");
                handleConnection(iBinder);
              }

              @Override // android.content.ServiceConnection
              public void onServiceDisconnected(ComponentName componentName) {
                Slog.i(
                    "StorageUserConnection",
                    "Service: ["
                        + componentName
                        + "] disconnected. User ["
                        + StorageUserConnection.this.mUserId
                        + "]");
                handleDisconnection();
              }

              @Override // android.content.ServiceConnection
              public void onBindingDied(ComponentName componentName) {
                Slog.i(
                    "StorageUserConnection",
                    "Service: ["
                        + componentName
                        + "] died. User ["
                        + StorageUserConnection.this.mUserId
                        + "]");
                handleDisconnection();
              }

              @Override // android.content.ServiceConnection
              public void onNullBinding(ComponentName componentName) {
                Slog.wtf(
                    "StorageUserConnection",
                    "Service: ["
                        + componentName
                        + "] is null. User ["
                        + StorageUserConnection.this.mUserId
                        + "]");
              }

              public final void handleConnection(IBinder iBinder) {
                synchronized (ActiveConnection.this.mLock) {
                  completableFuture2.complete(IExternalStorageService.Stub.asInterface(iBinder));
                }
              }

              public final void handleDisconnection() {
                ActiveConnection.this.close();
                StorageUserConnection.this.resetUserSessions();
              }
            };
        Slog.i(
            "StorageUserConnection",
            "Binding to the ExternalStorageService for user " + StorageUserConnection.this.mUserId);
        if (StorageUserConnection.this.mContext.bindServiceAsUser(
            new Intent().setComponent(externalStorageServiceComponentName),
            this.mServiceConnection,
            65,
            StorageUserConnection.this.mHandlerThread.getThreadHandler(),
            UserHandle.of(StorageUserConnection.this.mUserId))) {
          Slog.i(
              "StorageUserConnection",
              "Bound to the ExternalStorageService for user " + StorageUserConnection.this.mUserId);
          this.mRemoteFuture = completableFuture2;
          return completableFuture2;
        }
        throw new StorageSessionController.ExternalStorageServiceException(
            "Failed to bind to the ExternalStorageService for user "
                + StorageUserConnection.this.mUserId);
      }
    }
  }

  public final class Session {
    public final String lowerPath;
    public final String sessionId;
    public final String upperPath;

    public Session(String str, String str2, String str3) {
      this.sessionId = str;
      this.upperPath = str2;
      this.lowerPath = str3;
    }

    public String toString() {
      return "[SessionId: "
          + this.sessionId
          + ". UpperPath: "
          + this.upperPath
          + ". LowerPath: "
          + this.lowerPath
          + "]";
    }
  }
}

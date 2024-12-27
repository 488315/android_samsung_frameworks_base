package com.android.server.storage;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.IBinder;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

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

    public final class ActiveConnection implements AutoCloseable {
        public final Object mLock = new Object();
        public final ArrayList mOutstandingOps = new ArrayList();
        public CompletableFuture mRemoteFuture;
        public AnonymousClass1 mServiceConnection;

        public ActiveConnection() {}

        @Override // java.lang.AutoCloseable
        public final void close() {
            AnonymousClass1 anonymousClass1;
            synchronized (this.mLock) {
                try {
                    Slog.i(
                            "StorageUserConnection",
                            "Closing connection for user " + StorageUserConnection.this.mUserId);
                    anonymousClass1 = this.mServiceConnection;
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
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (anonymousClass1 != null) {
                try {
                    StorageUserConnection.this.mContext.unbindService(anonymousClass1);
                } catch (Exception e) {
                    Slog.w("StorageUserConnection", "Failed to unbind service", e);
                }
            }
        }

        public final void freeCache(final String str, final long j, final String str2) {
            try {
                waitForAsyncVoid(
                        new AsyncStorageServiceCall() { // from class:
                                                        // com.android.server.storage.StorageUserConnection$ActiveConnection$$ExternalSyntheticLambda0
                            @Override // com.android.server.storage.StorageUserConnection.AsyncStorageServiceCall
                            public final void run(
                                    IExternalStorageService iExternalStorageService,
                                    RemoteCallback remoteCallback) {
                                iExternalStorageService.freeCache(str, str2, j, remoteCallback);
                            }
                        });
            } catch (Exception e) {
                throw new StorageSessionController.ExternalStorageServiceException(
                        "Failed to free " + j + " bytes for volumeUuid : " + str2, e);
            }
        }

        public final void notifyAnrDelayStarted(int i, String str) {
            synchronized (this.mLock) {
                try {
                    CompletableFuture completableFuture = this.mRemoteFuture;
                    if (completableFuture == null) {
                        Slog.w(
                                "StorageUserConnection",
                                "Dropping async request service is not bound");
                        return;
                    }
                    IExternalStorageService iExternalStorageService =
                            (IExternalStorageService) completableFuture.getNow(null);
                    if (iExternalStorageService == null) {
                        Slog.w(
                                "StorageUserConnection",
                                "Dropping async request service is not connected");
                        return;
                    }
                    try {
                        iExternalStorageService.notifyAnrDelayStarted(str, i, 0, 1);
                    } catch (RemoteException e) {
                        Slog.w("StorageUserConnection", "Failed to notify ANR delay started", e);
                    }
                } finally {
                }
            }
        }

        public final void waitForAsyncVoid(final AsyncStorageServiceCall asyncStorageServiceCall) {
            final CompletableFuture completableFuture;
            final CompletableFuture completableFuture2 = new CompletableFuture();
            final RemoteCallback remoteCallback =
                    new RemoteCallback(
                            new RemoteCallback
                                    .OnResultListener() { // from class:
                                                          // com.android.server.storage.StorageUserConnection$ActiveConnection$$ExternalSyntheticLambda3
                                public final void onResult(Bundle bundle) {
                                    StorageUserConnection.ActiveConnection activeConnection =
                                            StorageUserConnection.ActiveConnection.this;
                                    CompletableFuture completableFuture3 = completableFuture2;
                                    activeConnection.getClass();
                                    ParcelableException parcelableException =
                                            (ParcelableException)
                                                    bundle.getParcelable(
                                                            "android.service.storage.extra.error",
                                                            ParcelableException.class);
                                    if (parcelableException != null) {
                                        completableFuture3.completeExceptionally(
                                                parcelableException);
                                    } else {
                                        completableFuture3.complete(null);
                                    }
                                }
                            });
            ArrayList arrayList = this.mOutstandingOps;
            ComponentName componentName =
                    StorageUserConnection.this.mSessionController.mExternalStorageServiceComponent;
            if (componentName == null) {
                throw new StorageSessionController.ExternalStorageServiceException(
                        "Not ready to bind to the ExternalStorageService for user "
                                + StorageUserConnection.this.mUserId);
            }
            synchronized (this.mLock) {
                try {
                    completableFuture = this.mRemoteFuture;
                    if (completableFuture == null) {
                        completableFuture = new CompletableFuture();
                        this.mServiceConnection =
                                new ServiceConnection() { // from class:
                                                          // com.android.server.storage.StorageUserConnection.ActiveConnection.1
                                    public final void handleDisconnection() {
                                        ActiveConnection.this.close();
                                        StorageUserConnection storageUserConnection =
                                                StorageUserConnection.this;
                                        synchronized (storageUserConnection.mSessionsLock) {
                                            try {
                                                if (((HashMap) storageUserConnection.mSessions)
                                                        .isEmpty()) {
                                                    return;
                                                }
                                                storageUserConnection.mSmInternal.resetUser(
                                                        storageUserConnection.mUserId);
                                            } finally {
                                            }
                                        }
                                    }

                                    @Override // android.content.ServiceConnection
                                    public final void onBindingDied(ComponentName componentName2) {
                                        Slog.i(
                                                "StorageUserConnection",
                                                "Service: ["
                                                        + componentName2
                                                        + "] died. User ["
                                                        + StorageUserConnection.this.mUserId
                                                        + "]");
                                        handleDisconnection();
                                    }

                                    @Override // android.content.ServiceConnection
                                    public final void onNullBinding(ComponentName componentName2) {
                                        Slog.wtf(
                                                "StorageUserConnection",
                                                "Service: ["
                                                        + componentName2
                                                        + "] is null. User ["
                                                        + StorageUserConnection.this.mUserId
                                                        + "]");
                                    }

                                    @Override // android.content.ServiceConnection
                                    public final void onServiceConnected(
                                            ComponentName componentName2, IBinder iBinder) {
                                        Slog.i(
                                                "StorageUserConnection",
                                                "Service: ["
                                                        + componentName2
                                                        + "] connected. User ["
                                                        + StorageUserConnection.this.mUserId
                                                        + "]");
                                        synchronized (ActiveConnection.this.mLock) {
                                            completableFuture.complete(
                                                    IExternalStorageService.Stub.asInterface(
                                                            iBinder));
                                        }
                                    }

                                    @Override // android.content.ServiceConnection
                                    public final void onServiceDisconnected(
                                            ComponentName componentName2) {
                                        Slog.i(
                                                "StorageUserConnection",
                                                "Service: ["
                                                        + componentName2
                                                        + "] disconnected. User ["
                                                        + StorageUserConnection.this.mUserId
                                                        + "]");
                                        handleDisconnection();
                                    }
                                };
                        Slog.i(
                                "StorageUserConnection",
                                "Binding to the ExternalStorageService for user "
                                        + StorageUserConnection.this.mUserId);
                        if (!StorageUserConnection.this.mContext.bindServiceAsUser(
                                new Intent().setComponent(componentName),
                                this.mServiceConnection,
                                65,
                                StorageUserConnection.this.mHandlerThread.getThreadHandler(),
                                UserHandle.of(StorageUserConnection.this.mUserId))) {
                            throw new StorageSessionController.ExternalStorageServiceException(
                                    "Failed to bind to the ExternalStorageService for user "
                                            + StorageUserConnection.this.mUserId);
                        }
                        Slog.i(
                                "StorageUserConnection",
                                "Bound to the ExternalStorageService for user "
                                        + StorageUserConnection.this.mUserId);
                        this.mRemoteFuture = completableFuture;
                    }
                } finally {
                }
            }
            try {
                synchronized (this.mLock) {
                    arrayList.add(completableFuture2);
                }
                completableFuture
                        .thenCompose(
                                new Function() { // from class:
                                                 // com.android.server.storage.StorageUserConnection$ActiveConnection$$ExternalSyntheticLambda5
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        StorageUserConnection.AsyncStorageServiceCall
                                                asyncStorageServiceCall2 =
                                                        StorageUserConnection
                                                                .AsyncStorageServiceCall.this;
                                        RemoteCallback remoteCallback2 = remoteCallback;
                                        CompletableFuture completableFuture3 = completableFuture2;
                                        try {
                                            asyncStorageServiceCall2.run(
                                                    (IExternalStorageService) obj, remoteCallback2);
                                        } catch (RemoteException e) {
                                            completableFuture3.completeExceptionally(e);
                                        }
                                        return completableFuture3;
                                    }
                                })
                        .get(20L, TimeUnit.SECONDS);
                synchronized (this.mLock) {
                    arrayList.remove(completableFuture2);
                }
            } catch (Throwable th) {
                synchronized (this.mLock) {
                    arrayList.remove(completableFuture2);
                    throw th;
                }
            }
        }
    }

    public interface AsyncStorageServiceCall {
        void run(IExternalStorageService iExternalStorageService, RemoteCallback remoteCallback);
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

        public final String toString() {
            StringBuilder sb = new StringBuilder("[SessionId: ");
            sb.append(this.sessionId);
            sb.append(". UpperPath: ");
            sb.append(this.upperPath);
            sb.append(". LowerPath: ");
            return AudioOffloadInfo$$ExternalSyntheticOutline0.m(sb, this.lowerPath, "]");
        }
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
                new HandlerThread(
                        VibrationParam$1$$ExternalSyntheticOutline0.m(
                                checkArgumentNonnegative, "StorageUserConnectionThread-"));
        this.mHandlerThread = handlerThread;
        handlerThread.start();
    }

    public final void notifyVolumeStateChanged(String str, StorageVolume storageVolume) {
        Objects.requireNonNull(str);
        Objects.requireNonNull(storageVolume);
        synchronized (this.mSessionsLock) {
            try {
                if (!((HashMap) this.mSessions).containsKey(str)) {
                    Slog.i("StorageUserConnection", "No session found for sessionId: ".concat(str));
                    return;
                }
                ActiveConnection activeConnection = this.mActiveConnection;
                activeConnection.getClass();
                try {
                    activeConnection.waitForAsyncVoid(
                            new StorageUserConnection$ActiveConnection$$ExternalSyntheticLambda1(
                                    str, storageVolume));
                } catch (Exception e) {
                    throw new StorageSessionController.ExternalStorageServiceException(
                            "Failed to notify volume state changed for vol : " + storageVolume, e);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final Session removeSession(String str) {
        Session session;
        synchronized (this.mSessionsLock) {
            this.mUidsBlockedOnIo.clear();
            session = (Session) ((HashMap) this.mSessions).remove(str);
        }
        return session;
    }

    public final void removeSessionAndWait(String str) {
        final Session removeSession = removeSession(str);
        if (removeSession == null) {
            Slog.i("StorageUserConnection", "No session found for id: " + str);
            return;
        }
        Slog.i("StorageUserConnection", "Waiting for session end " + removeSession + " ...");
        ActiveConnection activeConnection = this.mActiveConnection;
        activeConnection.getClass();
        try {
            activeConnection.waitForAsyncVoid(
                    new AsyncStorageServiceCall() { // from class:
                                                    // com.android.server.storage.StorageUserConnection$ActiveConnection$$ExternalSyntheticLambda2
                        @Override // com.android.server.storage.StorageUserConnection.AsyncStorageServiceCall
                        public final void run(
                                IExternalStorageService iExternalStorageService,
                                RemoteCallback remoteCallback) {
                            iExternalStorageService.endSession(
                                    StorageUserConnection.Session.this.sessionId, remoteCallback);
                        }
                    });
        } catch (Exception e) {
            throw new StorageSessionController.ExternalStorageServiceException(
                    "Failed to end session: " + removeSession, e);
        }
    }
}

package com.android.server.pm;

import android.app.IInstantAppResolver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.InstantAppRequestInfo;
import android.content.pm.InstantAppResolveInfo;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IRemoteCallback;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Slog;
import android.util.TimedRemoteCaller;

import com.android.internal.os.BackgroundThread;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeoutException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class InstantAppResolverConnection implements IBinder.DeathRecipient {
    public static final long BIND_SERVICE_TIMEOUT_MS;
    public static final long CALL_SERVICE_TIMEOUT_MS;
    public static final boolean DEBUG_INSTANT;
    public final Context mContext;
    public final Intent mIntent;
    public IInstantAppResolver mRemoteInstance;
    public final Object mLock = new Object();
    public final GetInstantAppResolveInfoCaller mGetInstantAppResolveInfoCaller =
            new GetInstantAppResolveInfoCaller();
    public final MyServiceConnection mServiceConnection = new MyServiceConnection();
    public int mBindState = 0;
    public final Handler mBgHandler = BackgroundThread.getHandler();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class ConnectionException extends Exception {
        public final int failure;

        public ConnectionException(int i) {
            this.failure = i;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class GetInstantAppResolveInfoCaller extends TimedRemoteCaller {
        public final AnonymousClass1 mCallback;

        /* JADX WARN: Type inference failed for: r0v1, types: [com.android.server.pm.InstantAppResolverConnection$GetInstantAppResolveInfoCaller$1] */
        public GetInstantAppResolveInfoCaller() {
            super(InstantAppResolverConnection.CALL_SERVICE_TIMEOUT_MS);
            this.mCallback =
                    new IRemoteCallback
                            .Stub() { // from class:
                                      // com.android.server.pm.InstantAppResolverConnection.GetInstantAppResolveInfoCaller.1
                        public final void sendResult(Bundle bundle) {
                            GetInstantAppResolveInfoCaller.this.onRemoteMethodResult(
                                    bundle.getParcelableArrayList(
                                            "android.app.extra.RESOLVE_INFO",
                                            InstantAppResolveInfo.class),
                                    bundle.getInt("android.app.extra.SEQUENCE", -1));
                        }
                    };
        }

        public final List getInstantAppResolveInfoList(
                IInstantAppResolver iInstantAppResolver,
                InstantAppRequestInfo instantAppRequestInfo) {
            int onBeforeRemoteCall = onBeforeRemoteCall();
            iInstantAppResolver.getInstantAppResolveInfoList(
                    instantAppRequestInfo, onBeforeRemoteCall, this.mCallback);
            return (List) getResultTimed(onBeforeRemoteCall);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MyServiceConnection implements ServiceConnection {
        public MyServiceConnection() {}

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            if (InstantAppResolverConnection.DEBUG_INSTANT) {
                Slog.d("PackageManager", "Connected to instant app resolver");
            }
            synchronized (InstantAppResolverConnection.this.mLock) {
                InstantAppResolverConnection.this.mRemoteInstance =
                        IInstantAppResolver.Stub.asInterface(iBinder);
                InstantAppResolverConnection instantAppResolverConnection =
                        InstantAppResolverConnection.this;
                if (instantAppResolverConnection.mBindState == 2) {
                    instantAppResolverConnection.mBindState = 0;
                }
                try {
                    iBinder.linkToDeath(instantAppResolverConnection, 0);
                } catch (RemoteException unused) {
                    InstantAppResolverConnection.this.handleBinderDiedLocked();
                }
                InstantAppResolverConnection.this.mLock.notifyAll();
            }
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            if (InstantAppResolverConnection.DEBUG_INSTANT) {
                Slog.d("PackageManager", "Disconnected from instant app resolver");
            }
            synchronized (InstantAppResolverConnection.this.mLock) {
                InstantAppResolverConnection.this.handleBinderDiedLocked();
            }
        }
    }

    static {
        boolean z = Build.IS_ENG;
        BIND_SERVICE_TIMEOUT_MS = z ? 500L : 300L;
        CALL_SERVICE_TIMEOUT_MS = z ? 200L : 100L;
        DEBUG_INSTANT = Build.IS_DEBUGGABLE;
    }

    public InstantAppResolverConnection(Context context, ComponentName componentName) {
        this.mContext = context;
        this.mIntent =
                new Intent("android.intent.action.RESOLVE_INSTANT_APP_PACKAGE")
                        .setComponent(componentName);
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x012a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.app.IInstantAppResolver bind(java.lang.String r13) {
        /*
            Method dump skipped, instructions count: 320
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.InstantAppResolverConnection.bind(java.lang.String):android.app.IInstantAppResolver");
    }

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied() {
        if (DEBUG_INSTANT) {
            Slog.d("PackageManager", "Binder to instant app resolver died");
        }
        synchronized (this.mLock) {
            handleBinderDiedLocked();
        }
        this.mBgHandler.post(new InstantAppResolverConnection$$ExternalSyntheticLambda0(this));
    }

    public final List getInstantAppResolveInfoList(InstantAppRequestInfo instantAppRequestInfo) {
        try {
            if (Thread.currentThread() == this.mContext.getMainLooper().getThread()) {
                throw new RuntimeException("Cannot invoke on the main thread");
            }
            try {
                try {
                    String token = instantAppRequestInfo.getToken();
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        try {
                            List instantAppResolveInfoList =
                                    this.mGetInstantAppResolveInfoCaller
                                            .getInstantAppResolveInfoList(
                                                    bind(token), instantAppRequestInfo);
                            synchronized (this.mLock) {
                                this.mLock.notifyAll();
                            }
                            return instantAppResolveInfoList;
                        } catch (RemoteException unused) {
                            synchronized (this.mLock) {
                                this.mLock.notifyAll();
                                return null;
                            }
                        } catch (TimeoutException unused2) {
                            throw new ConnectionException(2);
                        }
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (InterruptedException unused3) {
                    throw new ConnectionException(3);
                }
            } catch (TimeoutException unused4) {
                throw new ConnectionException(1);
            }
        } catch (Throwable th) {
            synchronized (this.mLock) {
                this.mLock.notifyAll();
                throw th;
            }
        }
    }

    public final void handleBinderDiedLocked() {
        IInstantAppResolver iInstantAppResolver = this.mRemoteInstance;
        if (iInstantAppResolver != null) {
            try {
                iInstantAppResolver.asBinder().unlinkToDeath(this, 0);
            } catch (NoSuchElementException unused) {
            }
        }
        this.mRemoteInstance = null;
        try {
            this.mContext.unbindService(this.mServiceConnection);
        } catch (Exception unused2) {
        }
    }

    public final void waitForBindLocked(String str) {
        long uptimeMillis = SystemClock.uptimeMillis();
        while (this.mBindState != 0 && this.mRemoteInstance == null) {
            long uptimeMillis2 =
                    BIND_SERVICE_TIMEOUT_MS - (SystemClock.uptimeMillis() - uptimeMillis);
            if (uptimeMillis2 <= 0) {
                throw new TimeoutException(
                        XmlUtils$$ExternalSyntheticOutline0.m(
                                "[", str, "] Didn't bind to resolver in time!"));
            }
            this.mLock.wait(uptimeMillis2);
        }
    }
}

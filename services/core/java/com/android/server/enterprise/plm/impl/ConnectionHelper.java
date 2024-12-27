package com.android.server.enterprise.plm.impl;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.UiModeManagerService$13$$ExternalSyntheticOutline0;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ConnectionHelper extends Handler {
    public static final /* synthetic */ int $r8$clinit = 0;
    public int mBindCounter;
    public final AnonymousClass2 mBindRetryRunnable;
    public IBinder mBinder;
    public final String mClassName;
    public final AnonymousClass1 mConnection;
    public final BindServiceImpl.AnonymousClass1 mConnectionStateListener;
    public final Context mContext;
    public boolean mHasCallbacks;
    public final String mPackageName;
    public int mProcessId;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.enterprise.plm.impl.ConnectionHelper$1, reason: invalid class name */
    public final class AnonymousClass1 implements ServiceConnection {
        public AnonymousClass1() {}

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(
                final ComponentName componentName, final IBinder iBinder) {
            ConnectionHelper.this.post(
                    new Runnable() { // from class:
                                     // com.android.server.enterprise.plm.impl.ConnectionHelper$1$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            BindServiceImpl bindServiceImpl;
                            Handler handler;
                            final ConnectionHelper.AnonymousClass1 anonymousClass1 =
                                    ConnectionHelper.AnonymousClass1.this;
                            final ComponentName componentName2 = componentName;
                            IBinder iBinder2 = iBinder;
                            anonymousClass1.getClass();
                            try {
                                int i = ConnectionHelper.$r8$clinit;
                                Log.i(
                                        "ConnectionHelper",
                                        "(!) bound to service " + componentName2.getClassName());
                                ConnectionHelper connectionHelper = ConnectionHelper.this;
                                connectionHelper.mBinder = iBinder2;
                                connectionHelper.mProcessId =
                                        ConnectionHelper.m519$$Nest$mgetPidFromPackageName(
                                                connectionHelper, connectionHelper.mPackageName);
                                ConnectionHelper connectionHelper2 = ConnectionHelper.this;
                                if (connectionHelper2.mBinder != null) {
                                    connectionHelper2.resetBindTimer(true);
                                    BindServiceImpl.AnonymousClass1 anonymousClass12 =
                                            ConnectionHelper.this.mConnectionStateListener;
                                    if (anonymousClass12 != null
                                            && (handler =
                                                            (bindServiceImpl = BindServiceImpl.this)
                                                                    .mObserver)
                                                    != null) {
                                        handler.sendEmptyMessage(bindServiceImpl.mAliveEvent);
                                    }
                                    ConnectionHelper.this.mBinder.linkToDeath(
                                            new IBinder
                                                    .DeathRecipient() { // from class:
                                                                        // com.android.server.enterprise.plm.impl.ConnectionHelper$1$$ExternalSyntheticLambda2
                                                @Override // android.os.IBinder.DeathRecipient
                                                public final void binderDied() {
                                                    ConnectionHelper.AnonymousClass1
                                                            anonymousClass13 =
                                                                    ConnectionHelper.AnonymousClass1
                                                                            .this;
                                                    ConnectionHelper.this.post(
                                                            new ConnectionHelper$1$$ExternalSyntheticLambda0(
                                                                    anonymousClass13,
                                                                    componentName2,
                                                                    1));
                                                }
                                            },
                                            0);
                                }
                            } catch (Throwable th) {
                                int i2 = ConnectionHelper.$r8$clinit;
                                Log.e("ConnectionHelper", th.toString());
                                ConnectionHelper.this.resetBindTimer(false);
                            }
                        }
                    });
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            ConnectionHelper.this.post(
                    new ConnectionHelper$1$$ExternalSyntheticLambda0(this, componentName, 0));
        }
    }

    /* renamed from: -$$Nest$mgetPidFromPackageName, reason: not valid java name */
    public static int m519$$Nest$mgetPidFromPackageName(
            ConnectionHelper connectionHelper, String str) {
        String[] strArr;
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses =
                ((ActivityManager) connectionHelper.mContext.getSystemService("activity"))
                        .getRunningAppProcesses();
        if (runningAppProcesses != null) {
            Iterator<ActivityManager.RunningAppProcessInfo> it = runningAppProcesses.iterator();
            while (it.hasNext()) {
                ActivityManager.RunningAppProcessInfo next = it.next();
                if (((next == null || (strArr = next.pkgList) == null)
                                ? new ArrayList()
                                : Arrays.asList(strArr))
                        .contains(str)) {
                    UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                            Preconditions$$ExternalSyntheticOutline0.m(str, ":"),
                            next.pid,
                            "ConnectionHelper");
                    return next.pid;
                }
            }
        }
        return -1;
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.server.enterprise.plm.impl.ConnectionHelper$2] */
    public ConnectionHelper(
            Context context,
            String str,
            String str2,
            BindServiceImpl.AnonymousClass1 anonymousClass1) {
        super(Looper.myLooper());
        this.mConnection = new AnonymousClass1();
        this.mBindRetryRunnable =
                new Runnable() { // from class:
                                 // com.android.server.enterprise.plm.impl.ConnectionHelper.2
                    @Override // java.lang.Runnable
                    public final void run() {
                        int i = ConnectionHelper.$r8$clinit;
                        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                                new StringBuilder("(*) retry bind to service "),
                                ConnectionHelper.this.mClassName,
                                "ConnectionHelper");
                        ConnectionHelper connectionHelper = ConnectionHelper.this;
                        IBinder iBinder = connectionHelper.mBinder;
                        if (iBinder == null) {
                            int i2 = connectionHelper.mBindCounter - 1;
                            connectionHelper.mBindCounter = i2;
                            if (i2 >= 0) {
                                Log.e(
                                        "ConnectionHelper",
                                        "timeout expired, keep retrying... ("
                                                + ConnectionHelper.this.mBindCounter
                                                + ")");
                                ConnectionHelper connectionHelper2 = ConnectionHelper.this;
                                connectionHelper2.getClass();
                                connectionHelper2.post(
                                        new ConnectionHelper$$ExternalSyntheticLambda0(
                                                connectionHelper2, false));
                                return;
                            }
                        }
                        if (iBinder != null) {
                            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                                    new StringBuilder(
                                            "bind timeout expired, but already bound to service "),
                                    ConnectionHelper.this.mClassName,
                                    "ConnectionHelper");
                        } else {
                            Log.e("ConnectionHelper", "bind timeout expired, stop retrying!");
                        }
                        ConnectionHelper.this.resetBindTimer(true);
                    }
                };
        this.mContext = context;
        this.mPackageName = str;
        this.mClassName = str2;
        this.mConnectionStateListener = anonymousClass1;
        this.mBinder = null;
        this.mProcessId = -1;
        this.mBindCounter = 4;
        this.mHasCallbacks = false;
    }

    public final void resetBindTimer(boolean z) {
        StringBuilder sb = new StringBuilder("reset bind timer for ");
        sb.append(this.mClassName);
        sb.append(z ? " with " : " without ");
        sb.append("counter reset");
        Log.i("ConnectionHelper", sb.toString());
        try {
            if (this.mHasCallbacks) {
                removeCallbacks(this.mBindRetryRunnable);
                this.mHasCallbacks = false;
            }
            if (z) {
                this.mBindCounter = 4;
            }
        } catch (Throwable th) {
            Log.e("ConnectionHelper", th.toString());
            th.printStackTrace();
        }
    }
}

package com.samsung.android.sdk.scs.base.connection;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import com.samsung.android.sdk.scs.base.utils.Log;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class ServiceExecutor extends ThreadPoolExecutor implements InternalServiceConnectionListener, Application.ActivityLifecycleCallbacks {
    private static final boolean CONNECTION_TIMER_ON = false;
    private static final String TAG = "ScsApi@ServiceExecutor";
    private final Condition mConnectionCondition;
    private final InternalServiceConnectionListener mConnectionListener;
    private final ReentrantLock mConnectionLock;
    private TimerTask mConnectionManagementTask;
    protected ConnectionManager mConnectionManager;
    protected final Context mContext;
    private boolean mIsConnected;
    private final AtomicInteger mTaskCount;

    /* renamed from: -$$Nest$munlockConnection, reason: not valid java name */
    public static void m3305$$Nest$munlockConnection(ServiceExecutor serviceExecutor, boolean z, String str) {
        serviceExecutor.mConnectionLock.lock();
        try {
            serviceExecutor.mIsConnected = z;
            Log.d(TAG, str);
            serviceExecutor.mConnectionCondition.signalAll();
        } finally {
            serviceExecutor.mConnectionLock.unlock();
        }
    }

    public ServiceExecutor(Context context, int i, int i2, long j, TimeUnit timeUnit, BlockingQueue<Runnable> blockingQueue) {
        super(i, i2, j, timeUnit, blockingQueue);
        ReentrantLock reentrantLock = new ReentrantLock();
        this.mConnectionLock = reentrantLock;
        this.mConnectionCondition = reentrantLock.newCondition();
        this.mIsConnected = false;
        this.mConnectionListener = new InternalServiceConnectionListener() { // from class: com.samsung.android.sdk.scs.base.connection.ServiceExecutor.1
            @Override // com.samsung.android.sdk.scs.base.connection.InternalServiceConnectionListener
            public final void onConnected(ComponentName componentName, IBinder iBinder) {
                Log.d(ServiceExecutor.TAG, "onConnected");
                ServiceExecutor serviceExecutor = ServiceExecutor.this;
                serviceExecutor.onConnected(componentName, iBinder);
                ServiceExecutor.m3305$$Nest$munlockConnection(serviceExecutor, true, "connected, signal all");
            }

            @Override // com.samsung.android.sdk.scs.base.connection.InternalServiceConnectionListener
            public final void onDisconnected(ComponentName componentName) {
                Log.d(ServiceExecutor.TAG, "onDisconnected");
                ServiceExecutor serviceExecutor = ServiceExecutor.this;
                serviceExecutor.onDisconnected(componentName);
                ServiceExecutor.m3305$$Nest$munlockConnection(serviceExecutor, false, "disconnected, signal all");
            }

            @Override // com.samsung.android.sdk.scs.base.connection.InternalServiceConnectionListener
            public final void onError() {
                Log.d(ServiceExecutor.TAG, "onError");
                ServiceExecutor serviceExecutor = ServiceExecutor.this;
                serviceExecutor.onError();
                ServiceExecutor.m3305$$Nest$munlockConnection(serviceExecutor, false, "onError, signal all");
            }
        };
        allowCoreThreadTimeOut(true);
        Log.d(TAG, "use application context");
        this.mContext = context.getApplicationContext();
        this.mTaskCount = new AtomicInteger(0);
        this.mConnectionManager = new ConnectionManager();
        Log.d(TAG, "ServiceExecutor. ctor()");
    }

    @Override // java.util.concurrent.ThreadPoolExecutor
    public void afterExecute(Runnable runnable, Throwable th) {
        super.afterExecute(runnable, th);
        this.mTaskCount.getAndDecrement();
        Log.d(TAG, "afterExecute(). mTaskCount: " + this.mTaskCount);
    }

    /* JADX WARN: Removed duplicated region for block: B:55:0x01be  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x021f  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0225  */
    @Override // java.util.concurrent.ThreadPoolExecutor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void beforeExecute(java.lang.Thread r12, java.lang.Runnable r13) {
        /*
            Method dump skipped, instructions count: 823
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.sdk.scs.base.connection.ServiceExecutor.beforeExecute(java.lang.Thread, java.lang.Runnable):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0072  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean connect(android.content.Context r4, android.content.Intent r5, com.samsung.android.sdk.scs.base.connection.InternalServiceConnectionListener r6) {
        /*
            r3 = this;
            java.lang.String r0 = "ScsApi@ServiceExecutor"
            java.lang.String r1 = "connect"
            com.samsung.android.sdk.scs.base.utils.Log.d(r0, r1)
            com.samsung.android.sdk.scs.base.connection.ConnectionManager r0 = r3.mConnectionManager
            boolean r0 = r0.isServiceConnected()
            r1 = 1
            if (r0 == 0) goto L11
            return r1
        L11:
            com.samsung.android.sdk.scs.base.connection.ConnectionManager r3 = r3.mConnectionManager
            r3.mInternalServiceConnectionListener = r6
            boolean r6 = r3.isServiceConnected()
            java.lang.String r0 = "ScsApi@ConnectionManager"
            if (r6 == 0) goto L23
            java.lang.String r3 = "just return already bound service obj"
            com.samsung.android.sdk.scs.base.utils.Log.d(r0, r3)
            return r1
        L23:
            r6 = 0
            if (r4 != 0) goto L2d
            java.lang.String r4 = "Context is null"
            com.samsung.android.sdk.scs.base.utils.Log.e(r0, r4)
        L2b:
            r1 = r6
            goto L5f
        L2d:
            if (r5 != 0) goto L35
            java.lang.String r4 = "Intent is null"
            com.samsung.android.sdk.scs.base.utils.Log.e(r0, r4)
            goto L2b
        L35:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r2 = "connectToService mIsConnected = "
            r6.<init>(r2)
            boolean r2 = r3.mIsConnected
            r6.append(r2)
            java.lang.String r6 = r6.toString()
            com.samsung.android.sdk.scs.base.utils.Log.d(r0, r6)
            boolean r6 = r3.mIsConnected
            if (r6 != 0) goto L5a
            java.lang.String r6 = "Binding service with app context"
            com.samsung.android.sdk.scs.base.utils.Log.d(r0, r6)
            r3.mContext = r4
            com.samsung.android.sdk.scs.base.connection.ConnectionManager$1 r6 = r3.mServiceConnection
            boolean r1 = r4.bindService(r5, r6, r1)
            goto L5f
        L5a:
            java.lang.String r4 = "already bound"
            com.samsung.android.sdk.scs.base.utils.Log.d(r0, r4)
        L5f:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "connectToService result : "
            r4.<init>(r5)
            r4.append(r1)
            java.lang.String r4 = r4.toString()
            com.samsung.android.sdk.scs.base.utils.Log.d(r0, r4)
            if (r1 != 0) goto L77
            r4 = 3
            r5 = 0
            r3.notifyServiceConnection(r4, r5, r5)
        L77:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.sdk.scs.base.connection.ServiceExecutor.connect(android.content.Context, android.content.Intent, com.samsung.android.sdk.scs.base.connection.InternalServiceConnectionListener):boolean");
    }

    public void deInit() {
        Log.d(TAG, "deInit");
        ConnectionManager connectionManager = this.mConnectionManager;
        if (connectionManager != null) {
            connectionManager.disconnect();
        }
    }

    @Override // java.util.concurrent.ThreadPoolExecutor
    public void finalize() {
        super.finalize();
        Log.d(TAG, "finalize");
        ConnectionManager connectionManager = this.mConnectionManager;
        if (connectionManager != null) {
            connectionManager.disconnect();
        }
    }

    public abstract Intent getServiceIntent();

    public boolean isConnected() {
        return this.mConnectionManager.isServiceConnected();
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
        Log.d(TAG, "onActivityDestroyed");
        deInit();
    }

    public ServiceExecutor(Activity activity, int i, int i2, long j, TimeUnit timeUnit, BlockingQueue<Runnable> blockingQueue) {
        super(i, i2, j, timeUnit, blockingQueue);
        ReentrantLock reentrantLock = new ReentrantLock();
        this.mConnectionLock = reentrantLock;
        this.mConnectionCondition = reentrantLock.newCondition();
        this.mIsConnected = false;
        this.mConnectionListener = new InternalServiceConnectionListener() { // from class: com.samsung.android.sdk.scs.base.connection.ServiceExecutor.1
            @Override // com.samsung.android.sdk.scs.base.connection.InternalServiceConnectionListener
            public final void onConnected(ComponentName componentName, IBinder iBinder) {
                Log.d(ServiceExecutor.TAG, "onConnected");
                ServiceExecutor serviceExecutor = ServiceExecutor.this;
                serviceExecutor.onConnected(componentName, iBinder);
                ServiceExecutor.m3305$$Nest$munlockConnection(serviceExecutor, true, "connected, signal all");
            }

            @Override // com.samsung.android.sdk.scs.base.connection.InternalServiceConnectionListener
            public final void onDisconnected(ComponentName componentName) {
                Log.d(ServiceExecutor.TAG, "onDisconnected");
                ServiceExecutor serviceExecutor = ServiceExecutor.this;
                serviceExecutor.onDisconnected(componentName);
                ServiceExecutor.m3305$$Nest$munlockConnection(serviceExecutor, false, "disconnected, signal all");
            }

            @Override // com.samsung.android.sdk.scs.base.connection.InternalServiceConnectionListener
            public final void onError() {
                Log.d(ServiceExecutor.TAG, "onError");
                ServiceExecutor serviceExecutor = ServiceExecutor.this;
                serviceExecutor.onError();
                ServiceExecutor.m3305$$Nest$munlockConnection(serviceExecutor, false, "onError, signal all");
            }
        };
        allowCoreThreadTimeOut(true);
        Log.d(TAG, "use activity context");
        this.mContext = activity;
        activity.registerActivityLifecycleCallbacks(this);
        this.mTaskCount = new AtomicInteger(0);
        this.mConnectionManager = new ConnectionManager();
        Log.d(TAG, "ServiceExecutor. ctor()");
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }
}

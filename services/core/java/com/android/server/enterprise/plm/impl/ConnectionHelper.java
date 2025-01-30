package com.android.server.enterprise.plm.impl;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.enterprise.plm.impl.ConnectionHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class ConnectionHelper extends Handler {
    public static final String TAG = ConnectionHelper.class.getSimpleName();
    public int mBindCounter;
    public final Runnable mBindRetryRunnable;
    public IBinder mBinder;
    public final String mClassName;
    public final ServiceConnection mConnection;
    public final ConnectionStateListener mConnectionStateListener;
    public final Context mContext;
    public boolean mHasCallbacks;
    public final String mPackageName;
    public int mProcessId;

    public interface ConnectionStateListener {
        void onConnect();

        void onDisconnect();
    }

    /* renamed from: com.android.server.enterprise.plm.impl.ConnectionHelper$1 */
    public class ServiceConnectionC14561 implements ServiceConnection {
        public ServiceConnectionC14561() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(final ComponentName componentName, final IBinder iBinder) {
            ConnectionHelper.this.post(new Runnable() { // from class: com.android.server.enterprise.plm.impl.ConnectionHelper$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ConnectionHelper.ServiceConnectionC14561.this.lambda$onServiceConnected$2(componentName, iBinder);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onServiceConnected$2(final ComponentName componentName, IBinder iBinder) {
            try {
                Log.i(ConnectionHelper.TAG, "(!) bound to service " + componentName.getClassName());
                ConnectionHelper.this.mBinder = iBinder;
                ConnectionHelper connectionHelper = ConnectionHelper.this;
                connectionHelper.mProcessId = connectionHelper.getPidFromPackageName(connectionHelper.mPackageName);
                if (ConnectionHelper.this.mBinder != null) {
                    ConnectionHelper.this.resetBindTimer(true);
                    if (ConnectionHelper.this.mConnectionStateListener != null) {
                        ConnectionHelper.this.mConnectionStateListener.onConnect();
                    }
                    ConnectionHelper.this.mBinder.linkToDeath(new IBinder.DeathRecipient() { // from class: com.android.server.enterprise.plm.impl.ConnectionHelper$1$$ExternalSyntheticLambda2
                        @Override // android.os.IBinder.DeathRecipient
                        public final void binderDied() {
                            ConnectionHelper.ServiceConnectionC14561.this.lambda$onServiceConnected$1(componentName);
                        }
                    }, 0);
                }
            } catch (Throwable th) {
                Log.e(ConnectionHelper.TAG, th.toString());
                ConnectionHelper.this.resetBindTimer(false);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onServiceConnected$1(final ComponentName componentName) {
            ConnectionHelper.this.post(new Runnable() { // from class: com.android.server.enterprise.plm.impl.ConnectionHelper$1$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    ConnectionHelper.ServiceConnectionC14561.this.lambda$onServiceConnected$0(componentName);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onServiceConnected$0(ComponentName componentName) {
            Log.i(ConnectionHelper.TAG, "binder died");
            onServiceDisconnected(componentName);
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(final ComponentName componentName) {
            ConnectionHelper.this.post(new Runnable() { // from class: com.android.server.enterprise.plm.impl.ConnectionHelper$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    ConnectionHelper.ServiceConnectionC14561.this.lambda$onServiceDisconnected$3(componentName);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onServiceDisconnected$3(ComponentName componentName) {
            try {
                Log.i(ConnectionHelper.TAG, "(!) unbound from service " + componentName.getClassName());
                if (ConnectionHelper.this.mBinder != null) {
                    if (ConnectionHelper.this.mBinder.isBinderAlive()) {
                        ConnectionHelper.this.mContext.unbindService(ConnectionHelper.this.mConnection);
                    }
                    ConnectionHelper.this.mBinder = null;
                    ConnectionHelper.this.mProcessId = -1;
                    if (ConnectionHelper.this.mConnectionStateListener != null) {
                        ConnectionHelper.this.mConnectionStateListener.onDisconnect();
                    }
                }
            } catch (Throwable th) {
                Log.e(ConnectionHelper.TAG, th.toString());
                ConnectionHelper.this.resetBindTimer(false);
            }
        }
    }

    public ConnectionHelper(Context context, String str, String str2, ConnectionStateListener connectionStateListener) {
        super(Looper.myLooper());
        this.mConnection = new ServiceConnectionC14561();
        this.mBindRetryRunnable = new Runnable() { // from class: com.android.server.enterprise.plm.impl.ConnectionHelper.2
            @Override // java.lang.Runnable
            public void run() {
                Log.i(ConnectionHelper.TAG, "(*) retry bind to service " + ConnectionHelper.this.mClassName);
                if (ConnectionHelper.this.mBinder == null) {
                    ConnectionHelper connectionHelper = ConnectionHelper.this;
                    int i = connectionHelper.mBindCounter - 1;
                    connectionHelper.mBindCounter = i;
                    if (i >= 0) {
                        Log.e(ConnectionHelper.TAG, "timeout expired, keep retrying... (" + ConnectionHelper.this.mBindCounter + ")");
                        if (ConnectionHelper.this.bindService(false)) {
                            return;
                        }
                        Log.e(ConnectionHelper.TAG, "bind failed while retrying!");
                        return;
                    }
                }
                if (ConnectionHelper.this.mBinder != null) {
                    Log.i(ConnectionHelper.TAG, "bind timeout expired, but already bound to service " + ConnectionHelper.this.mClassName);
                } else {
                    Log.e(ConnectionHelper.TAG, "bind timeout expired, stop retrying!");
                }
                ConnectionHelper.this.resetBindTimer(true);
            }
        };
        this.mContext = context;
        this.mPackageName = str;
        this.mClassName = str2;
        this.mConnectionStateListener = connectionStateListener;
        this.mBinder = null;
        this.mProcessId = -1;
        this.mBindCounter = 4;
        this.mHasCallbacks = false;
    }

    public void clear() {
        post(new Runnable() { // from class: com.android.server.enterprise.plm.impl.ConnectionHelper$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                ConnectionHelper.this.lambda$clear$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$clear$0() {
        try {
            Log.i(TAG, "clear");
            IBinder iBinder = this.mBinder;
            if (iBinder != null) {
                if (iBinder.isBinderAlive()) {
                    this.mContext.unbindService(this.mConnection);
                }
                this.mBinder = null;
            }
        } finally {
            try {
            } finally {
            }
        }
    }

    public IBinder getBinder() {
        return this.mBinder;
    }

    public int getProcessId() {
        if (isConnected()) {
            return this.mProcessId;
        }
        return -1;
    }

    public boolean isConnected() {
        return this.mBinder != null;
    }

    public boolean bindService() {
        return bindService(true);
    }

    public final boolean bindService(final boolean z) {
        post(new Runnable() { // from class: com.android.server.enterprise.plm.impl.ConnectionHelper$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ConnectionHelper.this.lambda$bindService$1(z);
            }
        });
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bindService$1(boolean z) {
        try {
            if (this.mBinder != null) {
                Log.i(TAG, "already bound to service " + this.mClassName);
                return;
            }
            Bundle bundle = new Bundle();
            Intent intent = new Intent();
            intent.setClassName(this.mPackageName, this.mClassName);
            intent.putExtras(bundle);
            String str = TAG;
            Log.i(str, "(*) bind to service " + this.mClassName);
            if (!this.mContext.bindService(intent, this.mConnection, 1)) {
                Log.e(str, "bind failed!");
            } else {
                resetBindTimer(z);
                scheduleBindTimer();
            }
        } catch (Throwable th) {
            Log.e(TAG, th.toString());
            th.printStackTrace();
        }
    }

    public final void resetBindTimer(boolean z) {
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("reset bind timer for ");
        sb.append(this.mClassName);
        sb.append(z ? " with " : " without ");
        sb.append("counter reset");
        Log.i(str, sb.toString());
        try {
            if (this.mHasCallbacks) {
                removeCallbacks(this.mBindRetryRunnable);
                this.mHasCallbacks = false;
            }
            if (z) {
                this.mBindCounter = 4;
            }
        } catch (Throwable th) {
            Log.e(TAG, th.toString());
            th.printStackTrace();
        }
    }

    public final int getPidFromPackageName(String str) {
        String[] strArr;
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) this.mContext.getSystemService("activity")).getRunningAppProcesses();
        if (runningAppProcesses == null) {
            return -1;
        }
        Iterator<ActivityManager.RunningAppProcessInfo> it = runningAppProcesses.iterator();
        while (it.hasNext()) {
            ActivityManager.RunningAppProcessInfo next = it.next();
            if (((next == null || (strArr = next.pkgList) == null) ? new ArrayList() : Arrays.asList(strArr)).contains(str)) {
                Log.i(TAG, str + XmlUtils.STRING_ARRAY_SEPARATOR + next.pid);
                return next.pid;
            }
        }
        return -1;
    }

    public final void scheduleBindTimer() {
        Log.i(TAG, "schedule bind timer for 30000 secs");
        postDelayed(this.mBindRetryRunnable, 30000L);
        this.mHasCallbacks = true;
    }
}

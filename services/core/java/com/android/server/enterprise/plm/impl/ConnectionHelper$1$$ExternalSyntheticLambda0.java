package com.android.server.enterprise.plm.impl;

import android.content.ComponentName;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class ConnectionHelper$1$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ConnectionHelper.AnonymousClass1 f$0;
    public final /* synthetic */ ComponentName f$1;

    public /* synthetic */ ConnectionHelper$1$$ExternalSyntheticLambda0(
            ConnectionHelper.AnonymousClass1 anonymousClass1, ComponentName componentName, int i) {
        this.$r8$classId = i;
        this.f$0 = anonymousClass1;
        this.f$1 = componentName;
    }

    @Override // java.lang.Runnable
    public final void run() {
        BindServiceImpl bindServiceImpl;
        Handler handler;
        switch (this.$r8$classId) {
            case 0:
                ConnectionHelper.AnonymousClass1 anonymousClass1 = this.f$0;
                ComponentName componentName = this.f$1;
                anonymousClass1.getClass();
                try {
                    int i = ConnectionHelper.$r8$clinit;
                    Log.i(
                            "ConnectionHelper",
                            "(!) unbound from service " + componentName.getClassName());
                    IBinder iBinder = ConnectionHelper.this.mBinder;
                    if (iBinder != null) {
                        if (iBinder.isBinderAlive()) {
                            ConnectionHelper connectionHelper = ConnectionHelper.this;
                            connectionHelper.mContext.unbindService(connectionHelper.mConnection);
                        }
                        ConnectionHelper connectionHelper2 = ConnectionHelper.this;
                        connectionHelper2.mBinder = null;
                        connectionHelper2.mProcessId = -1;
                        BindServiceImpl.AnonymousClass1 anonymousClass12 =
                                connectionHelper2.mConnectionStateListener;
                        if (anonymousClass12 != null
                                && (handler = (bindServiceImpl = BindServiceImpl.this).mObserver)
                                        != null) {
                            handler.sendEmptyMessage(bindServiceImpl.mDeathEvent);
                            break;
                        }
                    }
                } catch (Throwable th) {
                    int i2 = ConnectionHelper.$r8$clinit;
                    Log.e("ConnectionHelper", th.toString());
                    ConnectionHelper.this.resetBindTimer(false);
                    return;
                }
                break;
            default:
                ConnectionHelper.AnonymousClass1 anonymousClass13 = this.f$0;
                ComponentName componentName2 = this.f$1;
                anonymousClass13.getClass();
                int i3 = ConnectionHelper.$r8$clinit;
                Log.i("ConnectionHelper", "binder died");
                anonymousClass13.onServiceDisconnected(componentName2);
                break;
        }
    }
}

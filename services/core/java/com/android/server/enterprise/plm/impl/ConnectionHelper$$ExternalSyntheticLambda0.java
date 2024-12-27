package com.android.server.enterprise.plm.impl;

import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class ConnectionHelper$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ ConnectionHelper f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ ConnectionHelper$$ExternalSyntheticLambda0(
            ConnectionHelper connectionHelper, boolean z) {
        this.f$0 = connectionHelper;
        this.f$1 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        ConnectionHelper connectionHelper = this.f$0;
        boolean z = this.f$1;
        connectionHelper.getClass();
        try {
            IBinder iBinder = connectionHelper.mBinder;
            String str = connectionHelper.mClassName;
            if (iBinder != null) {
                Log.i("ConnectionHelper", "already bound to service " + str);
            } else {
                Bundle bundle = new Bundle();
                Intent intent = new Intent();
                intent.setClassName(connectionHelper.mPackageName, str);
                intent.putExtras(bundle);
                Log.i("ConnectionHelper", "(*) bind to service " + str);
                if (connectionHelper.mContext.bindService(
                        intent, connectionHelper.mConnection, 1)) {
                    connectionHelper.resetBindTimer(z);
                    Log.i("ConnectionHelper", "schedule bind timer for 30000 secs");
                    connectionHelper.postDelayed(connectionHelper.mBindRetryRunnable, 30000L);
                    connectionHelper.mHasCallbacks = true;
                } else {
                    Log.e("ConnectionHelper", "bind failed!");
                }
            }
        } catch (Throwable th) {
            Log.e("ConnectionHelper", th.toString());
            th.printStackTrace();
        }
    }
}

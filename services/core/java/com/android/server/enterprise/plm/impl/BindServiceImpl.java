package com.android.server.enterprise.plm.impl;

import android.content.Context;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import com.android.server.enterprise.plm.IStateDelegate;
import com.android.server.enterprise.plm.common.Utils;
import com.android.server.enterprise.plm.context.ProcessContext;
import com.android.server.enterprise.plm.impl.ConnectionHelper;

/* loaded from: classes2.dex */
public class BindServiceImpl extends KeepAliveImpl {
    public static final String TAG = "BindServiceImpl";
    public int mAliveEvent;
    public ConnectionHelper mConnectionHelper;
    public int mDeathEvent;
    public Handler mObserver;

    public BindServiceImpl(Context context, ProcessContext processContext) {
        super(context, processContext);
        this.mConnectionHelper = null;
        this.mObserver = null;
    }

    @Override // com.android.server.enterprise.plm.impl.KeepAliveImpl
    public IBinder getBinder() {
        return getConnectionHelper().getBinder();
    }

    @Override // com.android.server.enterprise.plm.impl.KeepAliveImpl
    public int getProcessId() {
        return getConnectionHelper().getProcessId();
    }

    @Override // com.android.server.enterprise.plm.impl.KeepAliveImpl
    public boolean isAlive() {
        return getConnectionHelper().isConnected();
    }

    @Override // com.android.server.enterprise.plm.impl.KeepAliveImpl
    public boolean needToKeepAlive(IStateDelegate iStateDelegate) {
        try {
            return this.mProcessContext.needToKeepAlive(iStateDelegate);
        } catch (Throwable th) {
            Log.e(TAG, th.toString());
            return false;
        }
    }

    @Override // com.android.server.enterprise.plm.impl.KeepAliveImpl
    public boolean needToCleanUpOnConditionNotMet() {
        return this.mProcessContext.needToCleanUpOnConditionNotMet();
    }

    @Override // com.android.server.enterprise.plm.impl.KeepAliveImpl
    public boolean start() {
        if (!Utils.hasService(this.mContext, this.mProcessContext.getPackageName(), this.mProcessContext.getServiceName())) {
            Log.i(TAG, "failed to find " + this.mProcessContext.getServiceName());
            return false;
        }
        return getConnectionHelper().bindService();
    }

    @Override // com.android.server.enterprise.plm.impl.KeepAliveImpl
    public boolean stop() {
        clearConnectionHelper();
        return true;
    }

    @Override // com.android.server.enterprise.plm.impl.KeepAliveImpl
    public void registerObserver(Handler handler, int i, int i2) {
        this.mObserver = handler;
        this.mAliveEvent = i;
        this.mDeathEvent = i2;
    }

    @Override // com.android.server.enterprise.plm.impl.KeepAliveImpl
    public void unregisterObserver(Handler handler) {
        this.mObserver = null;
    }

    public final void clearConnectionHelper() {
        ConnectionHelper connectionHelper = this.mConnectionHelper;
        if (connectionHelper != null) {
            connectionHelper.clear();
            this.mConnectionHelper = null;
        }
    }

    public final ConnectionHelper getConnectionHelper() {
        if (this.mConnectionHelper == null) {
            this.mConnectionHelper = new ConnectionHelper(this.mContext, this.mProcessContext.getPackageName(), this.mProcessContext.getServiceName(), new ConnectionHelper.ConnectionStateListener() { // from class: com.android.server.enterprise.plm.impl.BindServiceImpl.1
                @Override // com.android.server.enterprise.plm.impl.ConnectionHelper.ConnectionStateListener
                public void onConnect() {
                    if (BindServiceImpl.this.mObserver != null) {
                        BindServiceImpl.this.mObserver.sendEmptyMessage(BindServiceImpl.this.mAliveEvent);
                    }
                }

                @Override // com.android.server.enterprise.plm.impl.ConnectionHelper.ConnectionStateListener
                public void onDisconnect() {
                    if (BindServiceImpl.this.mObserver != null) {
                        BindServiceImpl.this.mObserver.sendEmptyMessage(BindServiceImpl.this.mDeathEvent);
                    }
                }
            });
        }
        return this.mConnectionHelper;
    }
}

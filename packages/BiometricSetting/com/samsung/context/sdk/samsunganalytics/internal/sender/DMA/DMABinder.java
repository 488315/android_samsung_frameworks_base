package com.samsung.context.sdk.samsunganalytics.internal.sender.DMA;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.samsung.context.sdk.samsunganalytics.internal.Callback;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.sec.android.diagmonagent.p001sa.IDMAInterface;

/* loaded from: classes.dex */
public final class DMABinder {
    private Context context;
    private IDMAInterface dmaInterface;
    private ServiceConnection serviceConnection;
    private boolean isTokenFail = false;
    private boolean isBind = false;

    public DMABinder(Context context, final Callback<Void, String> callback) {
        this.context = context;
        this.serviceConnection = new ServiceConnection() { // from class: com.samsung.context.sdk.samsunganalytics.internal.sender.DMA.DMABinder.1
            @Override // android.content.ServiceConnection
            public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                try {
                    DMABinder.this.dmaInterface = IDMAInterface.Stub.asInterface(iBinder);
                    String checkToken = DMABinder.this.dmaInterface.checkToken();
                    if (checkToken == null) {
                        DMABinder.this.unBind();
                        DMABinder.this.isTokenFail = true;
                        Debug.LogD("DMABinder", "Token failed");
                    } else {
                        DMABinder.this.isTokenFail = false;
                        callback.onResult(checkToken);
                        Debug.LogD("DMABinder", "DMA connected");
                    }
                } catch (Exception e) {
                    DMABinder.this.unBind();
                    DMABinder.this.isTokenFail = true;
                    Debug.LogException(e.getClass(), e);
                    e.printStackTrace();
                }
            }

            @Override // android.content.ServiceConnection
            public final void onServiceDisconnected(ComponentName componentName) {
                DMABinder.this.dmaInterface = null;
            }
        };
    }

    public final void bind() {
        if (this.isBind || this.isTokenFail) {
            return;
        }
        try {
            Intent intent = new Intent();
            intent.setClassName("com.sec.android.diagmonagent", "com.sec.android.diagmonagent.sa.receiver.SALogReceiverService");
            this.isBind = this.context.bindService(intent, this.serviceConnection, 1);
            Debug.LogD("DMABinder", "bind " + this.isBind);
        } catch (Exception e) {
            Debug.LogException(e.getClass(), e);
        }
    }

    public final IDMAInterface getDmaInterface() {
        return this.dmaInterface;
    }

    public final boolean isBind() {
        return this.isBind;
    }

    public final boolean isTokenfail() {
        return this.isTokenFail;
    }

    public final void unBind() {
        if (this.dmaInterface == null || !this.isBind) {
            return;
        }
        try {
            this.context.unbindService(this.serviceConnection);
            this.isBind = false;
            Debug.LogD("DMABinder", "unbind");
        } catch (Exception e) {
            Debug.LogException(e.getClass(), e);
        }
    }
}

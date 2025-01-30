package com.samsung.context.sdk.samsunganalytics.internal.sender.DMA;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.IInterface;
import com.samsung.context.sdk.samsunganalytics.internal.Callback;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.sec.android.diagmonagent.p051sa.IDMAInterface;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class DMABinder {
    public final Context context;
    public IDMAInterface dmaInterface;
    public final ServiceConnectionC47921 serviceConnection;
    public boolean isTokenFail = false;
    public boolean isBind = false;

    /* JADX WARN: Type inference failed for: r2v1, types: [com.samsung.context.sdk.samsunganalytics.internal.sender.DMA.DMABinder$1] */
    public DMABinder(Context context, final Callback callback) {
        this.context = context;
        this.serviceConnection = new ServiceConnection() { // from class: com.samsung.context.sdk.samsunganalytics.internal.sender.DMA.DMABinder.1
            @Override // android.content.ServiceConnection
            public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                IDMAInterface proxy;
                try {
                    DMABinder dMABinder = DMABinder.this;
                    int i = IDMAInterface.Stub.$r8$clinit;
                    if (iBinder == null) {
                        proxy = null;
                    } else {
                        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.sec.android.diagmonagent.sa.IDMAInterface");
                        proxy = (queryLocalInterface == null || !(queryLocalInterface instanceof IDMAInterface)) ? new IDMAInterface.Stub.Proxy(iBinder) : (IDMAInterface) queryLocalInterface;
                    }
                    dMABinder.dmaInterface = proxy;
                    String checkToken = ((IDMAInterface.Stub.Proxy) DMABinder.this.dmaInterface).checkToken();
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

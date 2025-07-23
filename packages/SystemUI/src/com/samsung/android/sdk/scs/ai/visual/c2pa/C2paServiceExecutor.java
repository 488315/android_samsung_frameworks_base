package com.samsung.android.sdk.scs.ai.visual.c2pa;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import com.samsung.android.sdk.scs.base.connection.ServiceExecutor;
import com.samsung.android.sdk.scs.base.utils.Log;
import com.samsung.android.visual.ai.sdkcommon.IDpsC2pa;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class C2paServiceExecutor extends ServiceExecutor {
    private static final String TAG = "ScsApi@C2PAServiceExecutor";
    private final IBinder.DeathRecipient deathRecipient;
    private IDpsC2pa mC2PAService;

    public C2paServiceExecutor(Context context) {
        super(context, 1, 2, 60L, TimeUnit.SECONDS, new LinkedBlockingDeque());
        this.deathRecipient = new IBinder.DeathRecipient() { // from class: com.samsung.android.sdk.scs.ai.visual.c2pa.C2paServiceExecutor.1
            @Override // android.os.IBinder.DeathRecipient
            public void binderDied() {
                Log.d(C2paServiceExecutor.TAG, "binderDied deathRecipient callback");
                C2paServiceExecutor.this.mC2PAService.asBinder().unlinkToDeath(C2paServiceExecutor.this.deathRecipient, 0);
            }
        };
    }

    public IDpsC2pa getC2PAService() {
        return this.mC2PAService;
    }

    @Override // com.samsung.android.sdk.scs.base.connection.ServiceExecutor
    public Intent getServiceIntent() {
        Intent intent = new Intent("visual.intent.action.BIND_C2PA_SERVICE");
        intent.setPackage("com.samsung.android.visual.cloudcore");
        return intent;
    }

    @Override // com.samsung.android.sdk.scs.base.connection.InternalServiceConnectionListener
    public void onConnected(ComponentName componentName, IBinder iBinder) {
        IDpsC2pa proxy;
        Log.d(TAG, "onServiceConnected");
        int i = IDpsC2pa.Stub.$r8$clinit;
        if (iBinder == null) {
            proxy = null;
        } else {
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.samsung.android.visual.ai.sdkcommon.IDpsC2pa");
            proxy = (queryLocalInterface == null || !(queryLocalInterface instanceof IDpsC2pa)) ? new IDpsC2pa.Stub.Proxy(iBinder) : (IDpsC2pa) queryLocalInterface;
        }
        this.mC2PAService = proxy;
        try {
            proxy.asBinder().linkToDeath(this.deathRecipient, 0);
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException");
            e.printStackTrace();
        }
    }

    @Override // com.samsung.android.sdk.scs.base.connection.InternalServiceConnectionListener
    public void onDisconnected(ComponentName componentName) {
        Log.d(TAG, "onServiceDisconnected " + componentName);
        this.mC2PAService = null;
    }

    @Override // com.samsung.android.sdk.scs.base.connection.InternalServiceConnectionListener
    public /* bridge */ /* synthetic */ void onError() {
    }
}

package com.samsung.android.sdk.scs.ai.language.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import com.samsung.android.sdk.scs.base.connection.ServiceExecutor;
import com.samsung.android.sdk.scs.base.utils.Log;
import com.samsung.android.sivs.ai.sdkcommon.language.ISmartReplyService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class SmartReplyServiceExecutor extends ServiceExecutor {
    public final Context context;
    public final C47611 deathRecipient;
    public ISmartReplyService service;

    /* JADX WARN: Type inference failed for: r0v1, types: [com.samsung.android.sdk.scs.ai.language.service.SmartReplyServiceExecutor$1] */
    public SmartReplyServiceExecutor(Context context) {
        super(context, 1, 1, 60L, TimeUnit.SECONDS, new LinkedBlockingDeque());
        this.deathRecipient = new IBinder.DeathRecipient() { // from class: com.samsung.android.sdk.scs.ai.language.service.SmartReplyServiceExecutor.1
            @Override // android.os.IBinder.DeathRecipient
            public final void binderDied() {
                Log.m266d("SmartReplyServiceExecutor", "binderDied deathRecipient callback");
                SmartReplyServiceExecutor.this.service.asBinder().unlinkToDeath(SmartReplyServiceExecutor.this.deathRecipient, 0);
            }
        };
        this.context = context.getApplicationContext();
    }

    @Override // com.samsung.android.sdk.scs.base.connection.ServiceExecutor
    public final Intent getServiceIntent() {
        Intent intent = new Intent("android.intellivoiceservice.SmartReplyService");
        intent.setPackage("com.samsung.android.intellivoiceservice");
        return intent;
    }

    @Override // com.samsung.android.sdk.scs.base.connection.InternalServiceConnectionListener
    public final void onConnected(ComponentName componentName, IBinder iBinder) {
        ISmartReplyService proxy;
        Log.m266d("SmartReplyServiceExecutor", "onServiceConnected");
        int i = ISmartReplyService.Stub.$r8$clinit;
        if (iBinder == null) {
            proxy = null;
        } else {
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.samsung.android.sivs.ai.sdkcommon.language.ISmartReplyService");
            proxy = (queryLocalInterface == null || !(queryLocalInterface instanceof ISmartReplyService)) ? new ISmartReplyService.Stub.Proxy(iBinder) : (ISmartReplyService) queryLocalInterface;
        }
        this.service = proxy;
        try {
            proxy.asBinder().linkToDeath(this.deathRecipient, 0);
        } catch (RemoteException e) {
            Log.m267e("SmartReplyServiceExecutor", "RemoteException");
            e.printStackTrace();
        }
    }

    @Override // com.samsung.android.sdk.scs.base.connection.InternalServiceConnectionListener
    public final void onDisconnected(ComponentName componentName) {
        this.service = null;
    }
}

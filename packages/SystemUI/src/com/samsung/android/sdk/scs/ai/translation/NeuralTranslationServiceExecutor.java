package com.samsung.android.sdk.scs.ai.translation;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import com.samsung.android.sdk.scs.base.connection.ServiceExecutor;
import com.samsung.android.sdk.scs.base.utils.Log;
import com.samsung.android.sivs.ai.sdkcommon.translation.INeuralTranslationService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
class NeuralTranslationServiceExecutor extends ServiceExecutor {
    public final AnonymousClass1 deathRecipient;
    public INeuralTranslationService translationService;

    /* JADX WARN: Type inference failed for: r8v1, types: [com.samsung.android.sdk.scs.ai.translation.NeuralTranslationServiceExecutor$1] */
    public NeuralTranslationServiceExecutor(Context context) {
        super(context, 1, 1, 60L, TimeUnit.SECONDS, new LinkedBlockingDeque());
        this.deathRecipient = new IBinder.DeathRecipient() { // from class: com.samsung.android.sdk.scs.ai.translation.NeuralTranslationServiceExecutor.1
            @Override // android.os.IBinder.DeathRecipient
            public final void binderDied() {
                Log.d("ScsApi@TranslationServiceExecutor", "binderDied deathRecipient callback");
                NeuralTranslationServiceExecutor.this.translationService.asBinder().unlinkToDeath(NeuralTranslationServiceExecutor.this.deathRecipient, 0);
            }
        };
        context.getApplicationContext();
    }

    @Override // com.samsung.android.sdk.scs.base.connection.ServiceExecutor
    public final Intent getServiceIntent() {
        Intent intent = new Intent("intellivoiceservice.intent.action.BIND_TRANSLATION");
        intent.setPackage("com.samsung.android.intellivoiceservice");
        return intent;
    }

    @Override // com.samsung.android.sdk.scs.base.connection.InternalServiceConnectionListener
    public final void onConnected(ComponentName componentName, IBinder iBinder) throws RemoteException {
        INeuralTranslationService proxy;
        int i = INeuralTranslationService.Stub.$r8$clinit;
        if (iBinder == null) {
            proxy = null;
        } else {
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.samsung.android.sivs.ai.sdkcommon.translation.INeuralTranslationService");
            proxy = (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof INeuralTranslationService)) ? new INeuralTranslationService.Stub.Proxy(iBinder) : (INeuralTranslationService) iInterfaceQueryLocalInterface;
        }
        this.translationService = proxy;
        try {
            proxy.asBinder().linkToDeath(this.deathRecipient, 0);
        } catch (RemoteException e) {
            Log.e("ScsApi@TranslationServiceExecutor", "RemoteException");
            e.printStackTrace();
        }
    }

    @Override // com.samsung.android.sdk.scs.base.connection.InternalServiceConnectionListener
    public final void onDisconnected(ComponentName componentName) {
        Log.d("ScsApi@TranslationServiceExecutor", "onServiceDisconnected " + componentName);
        this.translationService = null;
    }
}

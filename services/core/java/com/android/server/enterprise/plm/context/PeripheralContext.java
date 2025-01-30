package com.android.server.enterprise.plm.context;

import android.content.Context;
import android.util.Log;
import com.android.server.enterprise.plm.IStateDelegate;

/* loaded from: classes2.dex */
public class PeripheralContext extends ProcessContext {
    public static final String TAG = "PeripheralContext";

    @Override // com.android.server.enterprise.plm.context.ProcessContext
    public String getDisplayName() {
        return "PeripheralService";
    }

    @Override // com.android.server.enterprise.plm.context.ProcessContext
    public String getPackageName() {
        return "com.samsung.android.peripheral.framework";
    }

    @Override // com.android.server.enterprise.plm.context.ProcessContext
    public String getServiceName() {
        return "com.samsung.android.peripheral.framework.core.PeripheralService";
    }

    @Override // com.android.server.enterprise.plm.context.ProcessContext
    public boolean needToSupportThisDevice() {
        return true;
    }

    public PeripheralContext(Context context) {
        super(context);
    }

    @Override // com.android.server.enterprise.plm.context.ProcessContext
    public boolean needToKeepProcessAlive(IStateDelegate iStateDelegate) {
        boolean isKlmActivated = iStateDelegate.isKlmActivated();
        boolean isEdmServiceReady = iStateDelegate.isEdmServiceReady();
        String str = TAG;
        Log.d(str, "klm activated : " + isKlmActivated + ", edm service ready : " + isEdmServiceReady);
        boolean z = isKlmActivated || isEdmServiceReady;
        Log.i(str, "keep alive " + z);
        return z;
    }
}

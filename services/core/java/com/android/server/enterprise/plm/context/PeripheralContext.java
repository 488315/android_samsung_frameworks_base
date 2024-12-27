package com.android.server.enterprise.plm.context;

import android.util.Log;
import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.enterprise.plm.IStateDelegate;
import com.android.server.enterprise.plm.ProcessStateTracker;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class PeripheralContext extends ProcessContext {
    @Override // com.android.server.enterprise.plm.context.ProcessContext
    public final String getDisplayName() {
        return "PeripheralService";
    }

    @Override // com.android.server.enterprise.plm.context.ProcessContext
    public final String getPackageName() {
        return "com.samsung.android.peripheral.framework";
    }

    @Override // com.android.server.enterprise.plm.context.ProcessContext
    public final String getServiceName() {
        return "com.samsung.android.peripheral.framework.core.PeripheralService";
    }

    @Override // com.android.server.enterprise.plm.context.ProcessContext
    public final boolean needToKeepProcessAlive(IStateDelegate iStateDelegate) {
        ProcessStateTracker processStateTracker = (ProcessStateTracker) iStateDelegate;
        boolean isKlmActivated = processStateTracker.isKlmActivated();
        boolean z = processStateTracker.mSystemStateTracker.mEdmServiceReady;
        Log.d("SystemStateTracker", "isEdmServiceReady : " + z);
        Log.d("PeripheralContext", "klm activated : " + isKlmActivated + ", edm service ready : " + z);
        boolean z2 = isKlmActivated || z;
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m("keep alive ", "PeripheralContext", z2);
        return z2;
    }
}

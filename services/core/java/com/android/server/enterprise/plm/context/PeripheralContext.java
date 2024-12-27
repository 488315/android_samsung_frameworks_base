package com.android.server.enterprise.plm.context;

import android.util.Log;

import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.enterprise.plm.IStateDelegate;
import com.android.server.enterprise.plm.ProcessStateTracker;

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
        Log.d(
                "PeripheralContext",
                "klm activated : " + isKlmActivated + ", edm service ready : " + z);
        boolean z2 = isKlmActivated || z;
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "keep alive ", "PeripheralContext", z2);
        return z2;
    }
}

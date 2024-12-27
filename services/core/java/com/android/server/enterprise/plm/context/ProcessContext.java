package com.android.server.enterprise.plm.context;

import android.os.UserManager;
import android.util.Log;

import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.enterprise.plm.IStateDelegate;
import com.android.server.enterprise.plm.ProcessStateTracker;

public abstract class ProcessContext {
    public abstract String getDisplayName();

    public abstract String getPackageName();

    public abstract String getServiceName();

    public final boolean needToKeepAlive(IStateDelegate iStateDelegate) {
        UserManager userManager =
                (UserManager)
                        ((ProcessStateTracker) iStateDelegate)
                                .mSystemStateTracker.mContext.getSystemService("user");
        boolean z = false;
        boolean z2 = userManager != null && userManager.isUserUnlocked();
        Log.d("SystemStateTracker", "isUserUnlocked : " + z2);
        Log.d("ProcessContext", "user unlocked : " + z2);
        if (z2 && needToKeepProcessAlive(iStateDelegate)) {
            z = true;
        }
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "keep alive ", "ProcessContext", z);
        return z;
    }

    public abstract boolean needToKeepProcessAlive(IStateDelegate iStateDelegate);
}

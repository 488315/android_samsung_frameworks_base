package com.android.server.enterprise.plm.context;

import android.os.UserManager;
import android.util.Log;

import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.enterprise.plm.IStateDelegate;
import com.android.server.enterprise.plm.ProcessStateTracker;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
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

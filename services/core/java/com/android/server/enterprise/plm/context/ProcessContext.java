package com.android.server.enterprise.plm.context;

import android.content.Context;
import android.util.Log;
import com.android.server.enterprise.plm.IStateDelegate;

/* loaded from: classes2.dex */
public abstract class ProcessContext {
    public static final String TAG = "ProcessContext";
    public final Context mContext;

    public abstract String getDisplayName();

    public abstract String getPackageName();

    public abstract String getServiceName();

    public boolean needToCleanUpOnConditionNotMet() {
        return true;
    }

    public abstract boolean needToKeepProcessAlive(IStateDelegate iStateDelegate);

    public abstract boolean needToSupportThisDevice();

    public ProcessContext(Context context) {
        this.mContext = context;
    }

    public final boolean needToKeepAlive(IStateDelegate iStateDelegate) {
        boolean z = false;
        if (!needToSupportThisDevice()) {
            return false;
        }
        boolean isUserUnlocked = iStateDelegate.isUserUnlocked();
        String str = TAG;
        Log.d(str, "user unlocked : " + isUserUnlocked);
        if (isUserUnlocked && needToKeepProcessAlive(iStateDelegate)) {
            z = true;
        }
        Log.i(str, "keep alive " + z);
        return z;
    }
}

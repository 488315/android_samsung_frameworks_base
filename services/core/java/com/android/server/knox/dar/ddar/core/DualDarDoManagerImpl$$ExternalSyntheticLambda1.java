package com.android.server.knox.dar.ddar.core;

import android.content.Intent;

import com.android.internal.util.FunctionalUtils;

public final /* synthetic */ class DualDarDoManagerImpl$$ExternalSyntheticLambda1
        implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ DualDarDoManagerImpl f$0;

    public /* synthetic */ DualDarDoManagerImpl$$ExternalSyntheticLambda1(
            DualDarDoManagerImpl dualDarDoManagerImpl) {
        this.f$0 = dualDarDoManagerImpl;
    }

    public final void runOrThrow() {
        DualDarDoManagerImpl dualDarDoManagerImpl = this.f$0;
        dualDarDoManagerImpl.getClass();
        Intent intent = new Intent("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED");
        intent.setFlags(1073741824);
        dualDarDoManagerImpl.mInjector.mContext.sendBroadcast(intent);
    }
}

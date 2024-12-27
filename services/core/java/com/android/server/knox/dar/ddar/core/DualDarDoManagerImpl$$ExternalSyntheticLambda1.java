package com.android.server.knox.dar.ddar.core;

import android.content.Intent;

import com.android.internal.util.FunctionalUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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

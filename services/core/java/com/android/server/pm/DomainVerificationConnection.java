package com.android.server.pm;

import android.os.Message;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class DomainVerificationConnection {
    public final PackageManagerService mPm;
    public final UserManagerInternal mUmInternal;

    public DomainVerificationConnection(PackageManagerService packageManagerService) {
        this.mPm = packageManagerService;
        this.mUmInternal = (UserManagerInternal) packageManagerService.mInjector.mGetLocalServiceProducer.produce(UserManagerInternal.class);
    }

    public final void schedule(int i, Object obj) {
        PackageManagerService packageManagerService = this.mPm;
        Message obtainMessage = packageManagerService.mHandler.obtainMessage(27);
        obtainMessage.arg1 = i;
        obtainMessage.obj = obj;
        packageManagerService.mHandler.sendMessage(obtainMessage);
    }

    public final void scheduleWriteSettings() {
        this.mPm.scheduleWriteSettings();
    }
}

package com.android.server.power;

import android.os.UserHandle;

public final /* synthetic */ class PowerManagerService$LocalService$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ PowerManagerService$LocalService$$ExternalSyntheticLambda0(
            int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((PowerManagerService.LocalService) obj)
                        .this$0.mIsPowerBoostInvokedFromLocal.set(false);
                break;
            case 1:
                ((PowerManagerService.BinderService) obj).this$0.mIsUserActivityInvoked.set(false);
                break;
            default:
                PowerManagerService powerManagerService =
                        ((PowerManagerService.AnonymousClass1) obj).this$0;
                powerManagerService.mContext.sendBroadcastAsUser(
                        powerManagerService.mInternalDisplayOffByPowerKeyIntent, UserHandle.ALL);
                break;
        }
    }
}

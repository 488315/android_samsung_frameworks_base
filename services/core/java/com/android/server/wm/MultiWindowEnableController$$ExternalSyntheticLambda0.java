package com.android.server.wm;

import android.content.Intent;
import android.os.UserHandle;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class MultiWindowEnableController$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MultiWindowEnableController f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ boolean f$2;
    public final /* synthetic */ int f$3;
    public final /* synthetic */ boolean f$4;

    public /* synthetic */ MultiWindowEnableController$$ExternalSyntheticLambda0(
            MultiWindowEnableController multiWindowEnableController,
            String str,
            boolean z,
            int i,
            boolean z2,
            int i2) {
        this.$r8$classId = i2;
        this.f$0 = multiWindowEnableController;
        this.f$1 = str;
        this.f$2 = z;
        this.f$3 = i;
        this.f$4 = z2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                MultiWindowEnableController multiWindowEnableController = this.f$0;
                multiWindowEnableController.mH.post(
                        new MultiWindowEnableController$$ExternalSyntheticLambda0(
                                multiWindowEnableController,
                                this.f$1,
                                this.f$2,
                                this.f$3,
                                this.f$4,
                                1));
                break;
            default:
                MultiWindowEnableController multiWindowEnableController2 = this.f$0;
                String str = this.f$1;
                boolean z = this.f$2;
                int i = this.f$3;
                boolean z2 = this.f$4;
                multiWindowEnableController2.getClass();
                Intent intent =
                        new Intent("com.samsung.android.action.MULTI_WINDOW_ENABLE_CHANGED");
                intent.addFlags(1073741824);
                intent.putExtra("com.samsung.android.extra.MULTI_WINDOW_ENABLE_REQUESTER", str);
                intent.putExtra("com.samsung.android.extra.MULTI_WINDOW_ENABLED", z);
                intent.putExtra("com.samsung.android.extra.MULTI_WINDOW_ENABLED_USER_ID", i);
                intent.putExtra("com.samsung.android.extra.IN_MULTI_WINDOW_MODE", z2);
                multiWindowEnableController2.mAtm.mContext.sendBroadcastAsUser(
                        intent,
                        UserHandle.ALL,
                        "com.samsung.android.permission.MULTI_WINDOW_MONITOR",
                        -1);
                break;
        }
    }
}

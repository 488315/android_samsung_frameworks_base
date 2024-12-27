package com.android.server.attention;

import android.content.Intent;
import android.os.UserHandle;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class AttentionManagerService$$ExternalSyntheticLambda2
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AttentionManagerService f$0;

    public /* synthetic */ AttentionManagerService$$ExternalSyntheticLambda2(
            AttentionManagerService attentionManagerService, int i) {
        this.$r8$classId = i;
        this.f$0 = attentionManagerService;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        AttentionManagerService attentionManagerService = this.f$0;
        switch (i) {
            case 0:
                attentionManagerService.getClass();
                Intent component =
                        new Intent("android.service.attention.AttentionService")
                                .setComponent(attentionManagerService.mComponentName);
                attentionManagerService.mContext.bindServiceAsUser(
                        component,
                        attentionManagerService.mConnection,
                        67112961,
                        UserHandle.CURRENT);
                break;
            default:
                attentionManagerService.mContext.unbindService(attentionManagerService.mConnection);
                break;
        }
    }
}

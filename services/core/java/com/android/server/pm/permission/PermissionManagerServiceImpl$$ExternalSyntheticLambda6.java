package com.android.server.pm.permission;

import android.os.UserHandle;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class PermissionManagerServiceImpl$$ExternalSyntheticLambda6
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ PermissionManagerServiceImpl$$ExternalSyntheticLambda6(
            int i, int i2, int i3) {
        this.$r8$classId = i3;
        this.f$0 = i;
        this.f$1 = i2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                PermissionManagerServiceImpl.killUid(this.f$0, this.f$1, "permissions revoked");
                break;
            case 1:
                int i = this.f$0;
                PermissionManagerServiceImpl.killUid(
                        UserHandle.getAppId(i), this.f$1, "permissions revoked");
                break;
            default:
                PermissionManagerServiceImpl.killUid(
                        this.f$0, this.f$1, "permission grant or revoke changed gids");
                break;
        }
    }
}

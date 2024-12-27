package com.android.server.permission.access.permission;

import android.os.RemoteException;
import android.permission.IOnPermissionsChangeListener;
import android.util.ArrayMap;
import android.util.Slog;
import android.util.SparseBooleanArray;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PermissionService$restoreDelayedRuntimePermissions$3 implements Consumer {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ int $userId;
    public final /* synthetic */ Object this$0;

    public PermissionService$restoreDelayedRuntimePermissions$3(int i, String str) {
        this.$userId = i;
        this.this$0 = str;
    }

    public PermissionService$restoreDelayedRuntimePermissions$3(
            PermissionService permissionService, int i) {
        this.this$0 = permissionService;
        this.$userId = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                if (((Boolean) obj).booleanValue()) {
                    return;
                }
                PermissionService permissionService = (PermissionService) this.this$0;
                SparseBooleanArray sparseBooleanArray =
                        permissionService.isDelayedPermissionBackupFinished;
                int i = this.$userId;
                synchronized (sparseBooleanArray) {
                    permissionService.isDelayedPermissionBackupFinished.put(i, true);
                }
                return;
            default:
                try {
                    ((IOnPermissionsChangeListener) obj)
                            .onPermissionsChanged(this.$userId, (String) this.this$0);
                    return;
                } catch (RemoteException e) {
                    ArrayMap arrayMap = PermissionService.FULLER_PERMISSIONS;
                    Slog.e(
                            "PermissionService",
                            "Error when calling OnPermissionsChangeListener",
                            e);
                    return;
                }
        }
    }
}

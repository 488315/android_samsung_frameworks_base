package com.android.server.policy;

import java.util.function.BiConsumer;

public final /* synthetic */ class PermissionPolicyService$$ExternalSyntheticLambda4
        implements BiConsumer {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        PermissionPolicyService permissionPolicyService = (PermissionPolicyService) obj;
        Integer num = (Integer) obj2;
        switch (this.$r8$classId) {
            case 0:
                permissionPolicyService.synchronizeUidPermissionsAndAppOps(num.intValue());
                break;
            default:
                permissionPolicyService.resetAppOpPermissionsIfNotRequestedForUid(num.intValue());
                break;
        }
    }
}

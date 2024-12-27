package com.android.server.policy;

import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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

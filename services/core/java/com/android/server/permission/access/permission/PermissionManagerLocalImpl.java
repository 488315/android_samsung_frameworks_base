package com.android.server.permission.access.permission;

import android.os.Build;

import com.android.server.permission.PermissionManagerLocal;
import com.android.server.permission.access.AccessCheckingService;
import com.android.server.permission.access.SchemePolicy;
import com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PermissionManagerLocalImpl implements PermissionManagerLocal {
    public final AppIdPermissionPolicy policy;

    public PermissionManagerLocalImpl(AccessCheckingService accessCheckingService) {
        SchemePolicy
                schemePolicy$frameworks__base__services__permission__android_common__services_permission_pre_jarjar =
                        accessCheckingService
                                .getSchemePolicy$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(
                                        "uid", "permission");
        Intrinsics.checkNotNull(
                "null cannot be cast to non-null type"
                    + " com.android.server.permission.access.permission.AppIdPermissionPolicy",
                schemePolicy$frameworks__base__services__permission__android_common__services_permission_pre_jarjar);
        this.policy =
                (AppIdPermissionPolicy)
                        schemePolicy$frameworks__base__services__permission__android_common__services_permission_pre_jarjar;
    }

    @Override // com.android.server.permission.PermissionManagerLocal
    public final boolean isSignaturePermissionAllowlistForceEnforced() {
        if (Build.isDebuggable()) {
            return this.policy.isSignaturePermissionAllowlistForceEnforced;
        }
        throw new IllegalStateException("Check failed.".toString());
    }

    @Override // com.android.server.permission.PermissionManagerLocal
    public final void setSignaturePermissionAllowlistForceEnforced(boolean z) {
        if (!Build.isDebuggable()) {
            throw new IllegalStateException("Check failed.".toString());
        }
        this.policy.isSignaturePermissionAllowlistForceEnforced = z;
    }
}

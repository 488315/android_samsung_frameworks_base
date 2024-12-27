package com.android.server.pm.permission;

import com.android.internal.util.function.TriFunction;

public final /* synthetic */ class PermissionManagerService$$ExternalSyntheticLambda1
        implements TriFunction {
    public final /* synthetic */ PermissionManagerServiceInterface f$0;

    public final Object apply(Object obj, Object obj2, Object obj3) {
        return Integer.valueOf(
                this.f$0.checkUidPermission(
                        ((Integer) obj).intValue(), (String) obj2, (String) obj3));
    }
}

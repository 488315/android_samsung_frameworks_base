package com.android.server.pm.permission;

import com.android.internal.util.function.QuadFunction;

public final /* synthetic */ class PermissionManagerService$$ExternalSyntheticLambda0
        implements QuadFunction {
    public final /* synthetic */ PermissionManagerServiceInterface f$0;

    public final Object apply(Object obj, Object obj2, Object obj3, Object obj4) {
        return Integer.valueOf(
                this.f$0.checkPermission(
                        (String) obj, (String) obj2, (String) obj3, ((Integer) obj4).intValue()));
    }
}

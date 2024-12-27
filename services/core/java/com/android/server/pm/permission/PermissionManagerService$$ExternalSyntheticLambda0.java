package com.android.server.pm.permission;

import com.android.internal.util.function.QuadFunction;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class PermissionManagerService$$ExternalSyntheticLambda0
        implements QuadFunction {
    public final /* synthetic */ PermissionManagerServiceInterface f$0;

    public final Object apply(Object obj, Object obj2, Object obj3, Object obj4) {
        return Integer.valueOf(
                this.f$0.checkPermission(
                        (String) obj, (String) obj2, (String) obj3, ((Integer) obj4).intValue()));
    }
}

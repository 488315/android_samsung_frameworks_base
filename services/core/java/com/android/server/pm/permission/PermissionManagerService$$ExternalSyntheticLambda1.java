package com.android.server.pm.permission;

import com.android.internal.util.function.TriFunction;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class PermissionManagerService$$ExternalSyntheticLambda1 implements TriFunction {
    public final /* synthetic */ PermissionManagerServiceInterface f$0;

    public final Object apply(Object obj, Object obj2, Object obj3) {
        return Integer.valueOf(this.f$0.checkUidPermission(((Integer) obj).intValue(), (String) obj2, (String) obj3));
    }
}

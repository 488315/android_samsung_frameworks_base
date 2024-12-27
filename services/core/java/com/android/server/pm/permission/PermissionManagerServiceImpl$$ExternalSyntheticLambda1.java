package com.android.server.pm.permission;

import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;

import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class PermissionManagerServiceImpl$$ExternalSyntheticLambda1
        implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PermissionManagerServiceImpl f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ PermissionManagerServiceImpl$$ExternalSyntheticLambda1(
            PermissionManagerServiceImpl permissionManagerServiceImpl, int i, int i2, int i3) {
        this.$r8$classId = i3;
        this.f$0 = permissionManagerServiceImpl;
        this.f$1 = i;
        this.f$2 = i2;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                PermissionManagerServiceImpl permissionManagerServiceImpl = this.f$0;
                return permissionManagerServiceImpl.mPackageManagerInt.filterAppAccess(
                        this.f$1, this.f$2, ((PermissionInfo) obj).packageName, false);
            default:
                PermissionManagerServiceImpl permissionManagerServiceImpl2 = this.f$0;
                return permissionManagerServiceImpl2.mPackageManagerInt.filterAppAccess(
                        this.f$1, this.f$2, ((PermissionGroupInfo) obj).packageName, false);
        }
    }
}

package com.android.server.pm.permission;

import com.android.server.pm.pkg.AndroidPackage;

import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class PermissionManagerService$$ExternalSyntheticLambda2
        implements Consumer {
    public final /* synthetic */ int f$0;
    public final /* synthetic */ List f$1;

    public /* synthetic */ PermissionManagerService$$ExternalSyntheticLambda2(int i, List list) {
        this.f$0 = i;
        this.f$1 = list;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.f$0;
        List list = this.f$1;
        AndroidPackage androidPackage = (AndroidPackage) obj;
        if (androidPackage.getAutoRevokePermissions() == i) {
            list.add(androidPackage.getPackageName());
        }
    }
}

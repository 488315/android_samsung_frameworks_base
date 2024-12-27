package com.android.server.pm;

import com.android.internal.pm.parsing.pkg.AndroidPackageInternal;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.pm.pkg.PackageStateUnserialized;
import com.android.server.pm.pkg.mutate.PackageStateMutator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public final /* synthetic */ class PackageManagerService$$ExternalSyntheticLambda40
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ PackageManagerService$$ExternalSyntheticLambda40(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                String str = (String) obj2;
                PackageSetting packageSetting =
                        ((PackageStateMutator.StateWriteWrapper) obj).mState;
                if (packageSetting != null) {
                    PackageStateUnserialized packageStateUnserialized = packageSetting.pkgState;
                    packageStateUnserialized.overrideSeInfo = str;
                    packageStateUnserialized.mPackageSetting.onChanged$2();
                    break;
                }
                break;
            case 1:
                List list = (List) obj2;
                AndroidPackageInternal pkg = ((PackageStateInternal) obj).getPkg();
                if (pkg != null && !pkg.isResourceOverlay()) {
                    list.add(pkg.getPackageName());
                    break;
                }
                break;
            default:
                ArrayList arrayList = (ArrayList) obj2;
                AndroidPackageInternal pkg2 = ((PackageStateInternal) obj).getPkg();
                if (pkg2 != null) {
                    arrayList.add(pkg2.getPackageName());
                    break;
                }
                break;
        }
    }
}

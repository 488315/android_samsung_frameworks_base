package com.android.server.pm;

import android.util.ArraySet;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.pm.pkg.mutate.PackageStateMutator;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class PackageManagerService$$ExternalSyntheticLambda47 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ PackageManagerService$$ExternalSyntheticLambda47(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                PackageManagerService packageManagerService = (PackageManagerService) this.f$0;
                int[] iArr = (int[]) this.f$1;
                PackageStateInternal packageStateInternal = (PackageStateInternal) obj;
                packageManagerService.getClass();
                if (packageStateInternal.getAndroidPackage() == null || packageStateInternal.isSystem()) {
                    return;
                }
                for (int i : iArr) {
                    if (packageStateInternal.getUserStateOrDefault(i).isInstantApp() && packageStateInternal.getUserStateOrDefault(i).isInstalled()) {
                        packageManagerService.mInstantAppRegistry.addInstantApp(i, packageStateInternal.getAppId());
                    }
                }
                return;
            default:
                String str = (String) this.f$0;
                ArraySet arraySet = (ArraySet) this.f$1;
                PackageSetting packageSetting = ((PackageStateMutator.StateWriteWrapper) obj).mState;
                if (packageSetting != null) {
                    Map map = packageSetting.mimeGroups;
                    Set set = map == null ? null : (Set) map.get(str);
                    if (set == null) {
                        StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("Unknown MIME group ", str, " for package ");
                        m.append(packageSetting.mName);
                        throw new IllegalArgumentException(m.toString());
                    }
                    boolean z = !arraySet.equals(set);
                    packageSetting.mimeGroups.put(str, arraySet);
                    if (z) {
                        packageSetting.onChanged$2();
                        return;
                    }
                    return;
                }
                return;
        }
    }
}

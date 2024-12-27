package com.android.server.pm;

import com.android.server.pm.pkg.AndroidPackage;

import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class InstantAppRegistry$$ExternalSyntheticLambda2
        implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ InstantAppRegistry$$ExternalSyntheticLambda2(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                return ((InstantAppRegistry.UninstalledInstantAppState) obj)
                        .mInstantAppInfo
                        .getPackageName()
                        .equals(((AndroidPackage) obj2).getPackageName());
            default:
                return ((InstantAppRegistry.UninstalledInstantAppState) obj)
                        .mInstantAppInfo
                        .getPackageName()
                        .equals((String) obj2);
        }
    }
}

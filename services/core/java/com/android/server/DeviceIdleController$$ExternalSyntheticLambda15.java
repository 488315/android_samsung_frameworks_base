package com.android.server;

import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class DeviceIdleController$$ExternalSyntheticLambda15
        implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DeviceIdleController f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ DeviceIdleController$$ExternalSyntheticLambda15(
            DeviceIdleController deviceIdleController, int i, int i2, int i3) {
        this.$r8$classId = i3;
        this.f$0 = deviceIdleController;
        this.f$1 = i;
        this.f$2 = i2;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return !this.f$0.mPackageManagerInternal.filterAppAccess(
                        this.f$1, this.f$2, (String) obj, true);
            case 1:
                return !this.f$0.mPackageManagerInternal.filterAppAccess(
                        this.f$1, this.f$2, (String) obj, true);
            case 2:
                return !this.f$0.mPackageManagerInternal.filterAppAccess(
                        this.f$1, this.f$2, (String) obj, true);
            case 3:
                return !this.f$0.mPackageManagerInternal.filterAppAccess(
                        this.f$1, this.f$2, (String) obj, true);
            case 4:
                return !this.f$0.mPackageManagerInternal.filterAppAccess(
                        this.f$1, this.f$2, (String) obj, true);
            default:
                return !this.f$0.mPackageManagerInternal.filterAppAccess(
                        this.f$1, this.f$2, (String) obj, true);
        }
    }
}

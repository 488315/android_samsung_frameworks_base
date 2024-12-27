package com.android.server.devicepolicy;

import android.content.pm.PackageManagerInternal;
import android.content.pm.UserInfo;
import android.os.Binder;
import com.android.server.pm.Computer;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.SuspendPackageHelper;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class DevicePolicyManagerService$$ExternalSyntheticLambda179 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda179(int i, int i2, Object obj) {
        this.$r8$classId = i2;
        this.f$0 = obj;
        this.f$1 = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                DevicePolicyManagerService devicePolicyManagerService = (DevicePolicyManagerService) this.f$0;
                int i = this.f$1;
                devicePolicyManagerService.getClass();
                int i2 = ((UserInfo) obj).id;
                return i2 == i || !devicePolicyManagerService.mLockPatternUtils.isSeparateProfileChallengeEnabled(i2);
            case 1:
                DevicePolicyManagerService devicePolicyManagerService2 = (DevicePolicyManagerService) this.f$0;
                int i3 = this.f$1;
                devicePolicyManagerService2.getClass();
                int i4 = ((UserInfo) obj).id;
                return i4 == i3 || !devicePolicyManagerService2.mLockPatternUtils.isSeparateProfileChallengeEnabled(i4);
            default:
                PackageManagerService.PackageManagerInternalImpl packageManagerInternalImpl = (PackageManagerService.PackageManagerInternalImpl) ((PackageManagerInternal) this.f$0);
                SuspendPackageHelper suspendPackageHelper = PackageManagerService.this.mSuspendPackageHelper;
                Computer snapshotComputer = packageManagerInternalImpl.mService.snapshotComputer();
                int callingUid = Binder.getCallingUid();
                suspendPackageHelper.getClass();
                return "android".equals(SuspendPackageHelper.getSuspendingPackage(this.f$1, callingUid, snapshotComputer, (String) obj));
        }
    }
}

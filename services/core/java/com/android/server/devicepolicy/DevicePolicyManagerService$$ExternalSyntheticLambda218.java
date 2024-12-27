package com.android.server.devicepolicy;

import android.content.pm.UserInfo;

import com.android.internal.util.FunctionalUtils;

import java.util.ArrayList;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class DevicePolicyManagerService$$ExternalSyntheticLambda218
        implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DevicePolicyManagerService f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ ArrayList f$2;
    public final /* synthetic */ Predicate f$3;

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda218(
            DevicePolicyManagerService devicePolicyManagerService,
            int i,
            ArrayList arrayList,
            Predicate predicate,
            int i2) {
        this.$r8$classId = i2;
        this.f$0 = devicePolicyManagerService;
        this.f$1 = i;
        this.f$2 = arrayList;
        this.f$3 = predicate;
    }

    public final void runOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                DevicePolicyManagerService devicePolicyManagerService = this.f$0;
                int i = this.f$1;
                ArrayList arrayList = this.f$2;
                Predicate predicate = this.f$3;
                for (UserInfo userInfo : devicePolicyManagerService.mUserManager.getProfiles(i)) {
                    DevicePolicyData userDataUnchecked =
                            devicePolicyManagerService.getUserDataUnchecked(userInfo.id);
                    if (userInfo.id == i) {
                        arrayList.addAll(userDataUnchecked.mAdminList);
                    } else if (userInfo.isManagedProfile()) {
                        for (int i2 = 0; i2 < userDataUnchecked.mAdminList.size(); i2++) {
                            ActiveAdmin activeAdmin =
                                    (ActiveAdmin) userDataUnchecked.mAdminList.get(i2);
                            if (activeAdmin.parentAdmin != null) {
                                arrayList.add(activeAdmin.getParentActiveAdmin());
                            }
                            if (predicate.test(userInfo)) {
                                arrayList.add(activeAdmin);
                            }
                        }
                    }
                }
                break;
            default:
                DevicePolicyManagerService devicePolicyManagerService2 = this.f$0;
                int i3 = this.f$1;
                ArrayList arrayList2 = this.f$2;
                Predicate predicate2 = this.f$3;
                for (UserInfo userInfo2 :
                        devicePolicyManagerService2.mUserManager.getProfiles(i3)) {
                    DevicePolicyData userDataUnchecked2 =
                            devicePolicyManagerService2.getUserDataUnchecked(userInfo2.id);
                    if (userInfo2.id == i3) {
                        arrayList2.addAll(userDataUnchecked2.mAdminList);
                        ActiveAdmin activeAdmin2 = userDataUnchecked2.mPermissionBasedAdmin;
                        if (activeAdmin2 != null) {
                            arrayList2.add(activeAdmin2);
                        }
                    } else if (userInfo2.isManagedProfile()) {
                        for (int i4 = 0; i4 < userDataUnchecked2.mAdminList.size(); i4++) {
                            ActiveAdmin activeAdmin3 =
                                    (ActiveAdmin) userDataUnchecked2.mAdminList.get(i4);
                            if (activeAdmin3.parentAdmin != null) {
                                arrayList2.add(activeAdmin3.getParentActiveAdmin());
                            }
                            if (predicate2.test(userInfo2)) {
                                arrayList2.add(activeAdmin3);
                            }
                        }
                        if (userDataUnchecked2.mPermissionBasedAdmin != null
                                && predicate2.test(userInfo2)) {
                            arrayList2.add(userDataUnchecked2.mPermissionBasedAdmin);
                        }
                    }
                }
                break;
        }
    }
}

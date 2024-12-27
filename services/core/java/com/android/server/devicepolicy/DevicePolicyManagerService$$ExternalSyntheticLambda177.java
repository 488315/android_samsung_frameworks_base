package com.android.server.devicepolicy;

import android.app.role.RoleManager;
import android.content.pm.UserInfo;
import android.os.Bundle;
import android.os.UserHandle;
import android.telephony.TelephonyManager;
import android.telephony.data.ApnSetting;
import com.android.internal.util.FunctionalUtils;
import com.android.server.LocalServices;
import com.android.server.devicepolicy.DevicePolicyManagerService;
import com.android.server.pm.UserManagerInternal;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class DevicePolicyManagerService$$ExternalSyntheticLambda177 implements FunctionalUtils.ThrowingSupplier {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ Object f$2;
    public final /* synthetic */ Object f$3;

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda177(DevicePolicyManagerService.LocalService localService, String str, int i, List list) {
        this.f$0 = localService;
        this.f$3 = str;
        this.f$1 = i;
        this.f$2 = list;
    }

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda177(DevicePolicyManagerService devicePolicyManagerService, int i, RoleManager roleManager, String str) {
        this.f$0 = devicePolicyManagerService;
        this.f$1 = i;
        this.f$2 = roleManager;
        this.f$3 = str;
    }

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda177(DevicePolicyManagerService devicePolicyManagerService, TelephonyManager telephonyManager, int i, ApnSetting apnSetting) {
        this.f$0 = devicePolicyManagerService;
        this.f$2 = telephonyManager;
        this.f$1 = i;
        this.f$3 = apnSetting;
    }

    public final Object getOrThrow() {
        List of;
        switch (this.$r8$classId) {
            case 0:
                DevicePolicyManagerService devicePolicyManagerService = (DevicePolicyManagerService) this.f$0;
                int i = this.f$1;
                RoleManager roleManager = (RoleManager) this.f$2;
                String str = (String) this.f$3;
                if (i == -1) {
                    devicePolicyManagerService.mInjector.getClass();
                    of = ((UserManagerInternal) LocalServices.getService(UserManagerInternal.class)).getUsers(true);
                } else {
                    devicePolicyManagerService.getClass();
                    of = List.of(new UserInfo(i, (String) null, 0));
                }
                Iterator it = of.iterator();
                while (it.hasNext()) {
                    List roleHoldersAsUser = roleManager.getRoleHoldersAsUser(str, ((UserInfo) it.next()).getUserHandle());
                    if (!roleHoldersAsUser.isEmpty()) {
                        return (String) roleHoldersAsUser.get(0);
                    }
                }
                return null;
            case 1:
                DevicePolicyManagerService.LocalService localService = (DevicePolicyManagerService.LocalService) this.f$0;
                String str2 = (String) this.f$3;
                int i2 = this.f$1;
                List list = (List) this.f$2;
                Bundle applicationRestrictions = localService.this$0.mUserManager.getApplicationRestrictions(str2, UserHandle.of(i2));
                if (applicationRestrictions != null && !applicationRestrictions.isEmpty()) {
                    list.add(applicationRestrictions);
                }
                return list;
            default:
                DevicePolicyManagerService devicePolicyManagerService2 = (DevicePolicyManagerService) this.f$0;
                return Boolean.valueOf(((TelephonyManager) this.f$2).modifyDevicePolicyOverrideApn(devicePolicyManagerService2.mContext, this.f$1, (ApnSetting) this.f$3));
        }
    }
}

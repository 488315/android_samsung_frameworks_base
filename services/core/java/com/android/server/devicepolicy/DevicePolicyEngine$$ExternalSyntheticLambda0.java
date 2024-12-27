package com.android.server.devicepolicy;

import android.app.AppGlobals;
import android.app.admin.IntentFilterPolicyKey;
import android.app.admin.PolicyValue;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.IPackageManager;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.UserHandle;

import com.android.internal.util.FunctionalUtils;
import com.android.server.utils.Slogf;

import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class DevicePolicyEngine$$ExternalSyntheticLambda0
        implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ DevicePolicyEngine f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ Object f$3;

    public /* synthetic */ DevicePolicyEngine$$ExternalSyntheticLambda0(
            DevicePolicyEngine devicePolicyEngine, int i, String str, String str2) {
        this.f$0 = devicePolicyEngine;
        this.f$2 = i;
        this.f$1 = str;
        this.f$3 = str2;
    }

    public /* synthetic */ DevicePolicyEngine$$ExternalSyntheticLambda0(
            DevicePolicyEngine devicePolicyEngine, Intent intent, int i, Bundle bundle) {
        this.f$0 = devicePolicyEngine;
        this.f$1 = intent;
        this.f$2 = i;
        this.f$3 = bundle;
    }

    public final void runOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                DevicePolicyEngine devicePolicyEngine = this.f$0;
                Intent intent = (Intent) this.f$1;
                int i = this.f$2;
                devicePolicyEngine.mContext.sendBroadcastAsUser(
                        intent, new UserHandle(i), null, (Bundle) this.f$3);
                return;
            default:
                DevicePolicyEngine devicePolicyEngine2 = this.f$0;
                int i2 = this.f$2;
                String str = (String) this.f$1;
                String str2 = (String) this.f$3;
                Set<EnforcingAdmin> enforcingAdminsOnUser =
                        devicePolicyEngine2.getEnforcingAdminsOnUser(i2);
                if (str != null) {
                    for (EnforcingAdmin enforcingAdmin : enforcingAdminsOnUser) {
                        if (str.equals(enforcingAdmin.mPackageName)) {
                            devicePolicyEngine2.removePoliciesForAdmin(enforcingAdmin);
                            return;
                        }
                    }
                }
                for (EnforcingAdmin enforcingAdmin2 : enforcingAdminsOnUser) {
                    if (str2 == null || str2.equals(enforcingAdmin2.mPackageName)) {
                        try {
                            if (AppGlobals.getPackageManager()
                                            .getPackageInfo(enforcingAdmin2.mPackageName, 0L, i2)
                                    == null) {
                                Slogf.i(
                                        "DevicePolicyEngine",
                                        String.format(
                                                "Admin package %s not found for user %d, removing"
                                                    + " admin policies",
                                                enforcingAdmin2.mPackageName, Integer.valueOf(i2)));
                                devicePolicyEngine2.removePoliciesForAdmin(enforcingAdmin2);
                                return;
                            }
                        } catch (RemoteException e) {
                            Slogf.wtf("DevicePolicyEngine", "Error handling package changes", e);
                        }
                    }
                }
                if (str2 != null) {
                    for (EnforcingAdmin enforcingAdmin3 :
                            devicePolicyEngine2.getEnforcingAdminsOnUser(i2)) {
                        if (!enforcingAdmin3.hasAuthority("enterprise")
                                && str2.equals(enforcingAdmin3.mPackageName)) {
                            devicePolicyEngine2.mDeviceAdminServiceController.startServiceForAdmin(
                                    i2, str2);
                        }
                    }
                    for (IntentFilterPolicyKey intentFilterPolicyKey :
                            devicePolicyEngine2.getLocalPolicyKeysSetByAllAdmins(
                                    PolicyDefinition.GENERIC_PERSISTENT_PREFERRED_ACTIVITY, i2)) {
                        if (!(intentFilterPolicyKey instanceof IntentFilterPolicyKey)) {
                            throw new IllegalStateException(
                                    "PolicyKey for PERSISTENT_PREFERRED_ACTIVITY is not of type"
                                        + " IntentFilterPolicyKey");
                        }
                        IntentFilter intentFilter = intentFilterPolicyKey.getIntentFilter();
                        Objects.requireNonNull(intentFilter);
                        PolicyDefinition PERSISTENT_PREFERRED_ACTIVITY =
                                PolicyDefinition.PERSISTENT_PREFERRED_ACTIVITY(intentFilter);
                        LinkedHashMap localPoliciesSetByAdmins =
                                devicePolicyEngine2.getLocalPoliciesSetByAdmins(
                                        PERSISTENT_PREFERRED_ACTIVITY, i2);
                        IPackageManager packageManager = AppGlobals.getPackageManager();
                        for (EnforcingAdmin enforcingAdmin4 : localPoliciesSetByAdmins.keySet()) {
                            if (((PolicyValue) localPoliciesSetByAdmins.get(enforcingAdmin4))
                                                    .getValue()
                                            != null
                                    && ((ComponentName)
                                                    ((PolicyValue)
                                                                    localPoliciesSetByAdmins.get(
                                                                            enforcingAdmin4))
                                                            .getValue())
                                            .getPackageName()
                                            .equals(str2)) {
                                try {
                                    if (packageManager.getPackageInfo(str2, 0L, i2) != null
                                            && packageManager.getActivityInfo(
                                                            (ComponentName)
                                                                    ((PolicyValue)
                                                                                    localPoliciesSetByAdmins
                                                                                            .get(
                                                                                                    enforcingAdmin4))
                                                                            .getValue(),
                                                            0L,
                                                            i2)
                                                    != null) {}
                                    Slogf.e(
                                            "DevicePolicyEngine",
                                            String.format(
                                                    "Persistent preferred activity in package %s"
                                                        + " not found for user %d, removing policy"
                                                        + " for admin",
                                                    str2, Integer.valueOf(i2)));
                                    devicePolicyEngine2.removeLocalPolicy(
                                            PERSISTENT_PREFERRED_ACTIVITY, enforcingAdmin4, i2);
                                } catch (RemoteException e2) {
                                    Slogf.wtf(
                                            "DevicePolicyEngine",
                                            "Error handling package changes",
                                            e2);
                                }
                            }
                        }
                    }
                    return;
                }
                return;
        }
    }
}

package com.android.server.devicepolicy;

import android.app.admin.PolicyValue;
import android.content.pm.UserInfo;

import com.android.internal.util.FunctionalUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class DevicePolicyEngine$$ExternalSyntheticLambda2
        implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ DevicePolicyEngine f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ PolicyValue f$2;
    public final /* synthetic */ PolicyDefinition f$3;
    public final /* synthetic */ EnforcingAdmin f$4;

    public /* synthetic */ DevicePolicyEngine$$ExternalSyntheticLambda2(
            DevicePolicyEngine devicePolicyEngine,
            int i,
            PolicyValue policyValue,
            PolicyDefinition policyDefinition,
            EnforcingAdmin enforcingAdmin) {
        this.f$0 = devicePolicyEngine;
        this.f$1 = i;
        this.f$2 = policyValue;
        this.f$3 = policyDefinition;
        this.f$4 = enforcingAdmin;
    }

    public final void runOrThrow() {
        DevicePolicyEngine devicePolicyEngine = this.f$0;
        int i = this.f$1;
        PolicyValue policyValue = this.f$2;
        PolicyDefinition policyDefinition = this.f$3;
        EnforcingAdmin enforcingAdmin = this.f$4;
        for (UserInfo userInfo : devicePolicyEngine.mUserManager.getProfiles(i)) {
            int identifier = userInfo.getUserHandle().getIdentifier();
            UserInfo profileParent = devicePolicyEngine.mUserManager.getProfileParent(identifier);
            if (identifier != i
                    && profileParent != null
                    && profileParent.getUserHandle().getIdentifier() == i
                    && devicePolicyEngine.mUserManager.getUserProperties(userInfo.getUserHandle())
                            != null
                    && devicePolicyEngine
                                    .mUserManager
                                    .getUserProperties(userInfo.getUserHandle())
                                    .getInheritDevicePolicy()
                            == 1) {
                if (policyValue != null) {
                    devicePolicyEngine.setLocalPolicy(
                            policyDefinition, enforcingAdmin, policyValue, identifier, false);
                } else {
                    devicePolicyEngine.removeLocalPolicy(
                            policyDefinition, enforcingAdmin, identifier);
                }
            }
        }
    }
}

package com.android.server.devicepolicy;

import android.app.admin.BooleanPolicyValue;
import android.app.admin.PolicyValue;
import android.content.pm.UserInfo;
import android.content.pm.UserProperties;

import com.android.internal.util.FunctionalUtils;

import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class DevicePolicyEngine$$ExternalSyntheticLambda3
        implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DevicePolicyEngine f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ DevicePolicyEngine$$ExternalSyntheticLambda3(
            DevicePolicyEngine devicePolicyEngine, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = devicePolicyEngine;
        this.f$1 = obj;
    }

    public final void runOrThrow() {
        int i;
        UserInfo profileParent;
        switch (this.$r8$classId) {
            case 0:
                DevicePolicyEngine devicePolicyEngine = this.f$0;
                PolicyDefinition policyDefinition = (PolicyDefinition) this.f$1;
                devicePolicyEngine.getClass();
                PolicyValue booleanPolicyValue = new BooleanPolicyValue(false);
                try {
                    booleanPolicyValue =
                            devicePolicyEngine.getGlobalPolicyStateLocked(policyDefinition)
                                    .mCurrentResolvedPolicy;
                } catch (IllegalArgumentException unused) {
                }
                devicePolicyEngine.enforcePolicy(policyDefinition, booleanPolicyValue, -1);
                for (UserInfo userInfo : devicePolicyEngine.mUserManager.getUsers()) {
                    PolicyValue booleanPolicyValue2 = new BooleanPolicyValue(false);
                    try {
                        booleanPolicyValue2 =
                                devicePolicyEngine.getLocalPolicyStateLocked(
                                                policyDefinition, userInfo.id)
                                        .mCurrentResolvedPolicy;
                    } catch (IllegalArgumentException unused2) {
                    }
                    devicePolicyEngine.enforcePolicy(
                            policyDefinition, booleanPolicyValue2, userInfo.id);
                }
                return;
            default:
                DevicePolicyEngine devicePolicyEngine2 = this.f$0;
                UserInfo userInfo2 = (UserInfo) this.f$1;
                UserProperties userProperties =
                        devicePolicyEngine2.mUserManager.getUserProperties(
                                userInfo2.getUserHandle());
                if (userProperties == null
                        || userProperties.getInheritDevicePolicy() != 1
                        || (profileParent =
                                        devicePolicyEngine2.mUserManager.getProfileParent(
                                                (i = userInfo2.id)))
                                == null
                        || profileParent.getUserHandle().getIdentifier() == i) {
                    return;
                }
                synchronized (devicePolicyEngine2.mLock) {
                    try {
                        if (devicePolicyEngine2.mLocalPolicies.contains(
                                profileParent.getUserHandle().getIdentifier())) {
                            Iterator it =
                                    ((Map)
                                                    devicePolicyEngine2.mLocalPolicies.get(
                                                            profileParent
                                                                    .getUserHandle()
                                                                    .getIdentifier()))
                                            .entrySet()
                                            .iterator();
                            while (it.hasNext()) {
                                PolicyState policyState =
                                        (PolicyState) ((Map.Entry) it.next()).getValue();
                                if ((policyState.mPolicyDefinition.mPolicyFlags & 4) != 0) {
                                    for (Map.Entry entry :
                                            policyState.getPoliciesSetByAdmins().entrySet()) {
                                        devicePolicyEngine2.setLocalPolicy(
                                                policyState.mPolicyDefinition,
                                                (EnforcingAdmin) entry.getKey(),
                                                (PolicyValue) entry.getValue(),
                                                i,
                                                false);
                                    }
                                }
                            }
                            return;
                        }
                        return;
                    } finally {
                    }
                }
        }
    }
}

package com.android.server.devicepolicy;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.UserHandle;

import com.android.internal.util.FunctionalUtils;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;

import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class DevicePolicyEngine$$ExternalSyntheticLambda1
        implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DevicePolicyEngine f$0;
    public final /* synthetic */ Intent f$1;
    public final /* synthetic */ EnforcingAdmin f$2;
    public final /* synthetic */ PolicyDefinition f$3;
    public final /* synthetic */ int f$4;
    public final /* synthetic */ int f$5;

    public /* synthetic */ DevicePolicyEngine$$ExternalSyntheticLambda1(
            DevicePolicyEngine devicePolicyEngine,
            Intent intent,
            EnforcingAdmin enforcingAdmin,
            PolicyDefinition policyDefinition,
            int i,
            int i2,
            int i3) {
        this.$r8$classId = i3;
        this.f$0 = devicePolicyEngine;
        this.f$1 = intent;
        this.f$2 = enforcingAdmin;
        this.f$3 = policyDefinition;
        this.f$4 = i;
        this.f$5 = i2;
    }

    public final void runOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                final DevicePolicyEngine devicePolicyEngine = this.f$0;
                Intent intent = this.f$1;
                EnforcingAdmin enforcingAdmin = this.f$2;
                PolicyDefinition policyDefinition = this.f$3;
                int i = this.f$4;
                int i2 = this.f$5;
                List queryBroadcastReceiversAsUser =
                        devicePolicyEngine
                                .mContext
                                .getPackageManager()
                                .queryBroadcastReceiversAsUser(
                                        intent,
                                        PackageManager.ResolveInfoFlags.of(2L),
                                        enforcingAdmin.mUserId);
                if (!queryBroadcastReceiversAsUser.isEmpty()) {
                    Bundle bundle = new Bundle();
                    policyDefinition.mPolicyKey.writeToBundle(bundle);
                    final int i3 = enforcingAdmin.mUserId;
                    int i4 = -3;
                    if (i != -1) {
                        if (i3 == i) {
                            i4 = -1;
                        } else if (((Integer)
                                                Binder.withCleanCallingIdentity(
                                                        new FunctionalUtils
                                                                .ThrowingSupplier() { // from class:
                                                                                      // com.android.server.devicepolicy.DevicePolicyEngine$$ExternalSyntheticLambda5
                                                            public final Object getOrThrow() {
                                                                DevicePolicyEngine
                                                                        devicePolicyEngine2 =
                                                                                DevicePolicyEngine
                                                                                        .this;
                                                                int i5 = i3;
                                                                UserInfo profileParent =
                                                                        devicePolicyEngine2
                                                                                .mUserManager
                                                                                .getProfileParent(
                                                                                        i5);
                                                                if (profileParent != null) {
                                                                    i5 = profileParent.id;
                                                                }
                                                                return Integer.valueOf(i5);
                                                            }
                                                        }))
                                        .intValue()
                                == i) {
                            i4 = -2;
                        }
                    }
                    bundle.putInt("android.app.admin.extra.POLICY_TARGET_USER_ID", i4);
                    bundle.putInt("android.app.admin.extra.POLICY_UPDATE_RESULT_KEY", i2);
                    intent.putExtras(bundle);
                    devicePolicyEngine.maybeSendIntentToAdminReceivers(
                            intent, UserHandle.of(i3), queryBroadcastReceiversAsUser);
                    break;
                } else {
                    DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                            new StringBuilder(
                                    "Couldn't find any receivers that handle"
                                        + " ACTION_DEVICE_POLICY_SET_RESULT in package "),
                            enforcingAdmin.mPackageName,
                            "DevicePolicyEngine");
                    break;
                }
            default:
                final DevicePolicyEngine devicePolicyEngine2 = this.f$0;
                Intent intent2 = this.f$1;
                EnforcingAdmin enforcingAdmin2 = this.f$2;
                PolicyDefinition policyDefinition2 = this.f$3;
                int i5 = this.f$4;
                int i6 = this.f$5;
                List queryBroadcastReceiversAsUser2 =
                        devicePolicyEngine2
                                .mContext
                                .getPackageManager()
                                .queryBroadcastReceiversAsUser(
                                        intent2,
                                        PackageManager.ResolveInfoFlags.of(2L),
                                        enforcingAdmin2.mUserId);
                if (!queryBroadcastReceiversAsUser2.isEmpty()) {
                    Bundle bundle2 = new Bundle();
                    policyDefinition2.mPolicyKey.writeToBundle(bundle2);
                    final int i7 = enforcingAdmin2.mUserId;
                    int i8 = -3;
                    if (i5 != -1) {
                        if (i7 == i5) {
                            i8 = -1;
                        } else if (((Integer)
                                                Binder.withCleanCallingIdentity(
                                                        new FunctionalUtils
                                                                .ThrowingSupplier() { // from class:
                                                                                      // com.android.server.devicepolicy.DevicePolicyEngine$$ExternalSyntheticLambda5
                                                            public final Object getOrThrow() {
                                                                DevicePolicyEngine
                                                                        devicePolicyEngine22 =
                                                                                DevicePolicyEngine
                                                                                        .this;
                                                                int i52 = i7;
                                                                UserInfo profileParent =
                                                                        devicePolicyEngine22
                                                                                .mUserManager
                                                                                .getProfileParent(
                                                                                        i52);
                                                                if (profileParent != null) {
                                                                    i52 = profileParent.id;
                                                                }
                                                                return Integer.valueOf(i52);
                                                            }
                                                        }))
                                        .intValue()
                                == i5) {
                            i8 = -2;
                        }
                    }
                    bundle2.putInt("android.app.admin.extra.POLICY_TARGET_USER_ID", i8);
                    bundle2.putInt("android.app.admin.extra.POLICY_UPDATE_RESULT_KEY", i6);
                    intent2.putExtras(bundle2);
                    intent2.addFlags(268435456);
                    devicePolicyEngine2.maybeSendIntentToAdminReceivers(
                            intent2, UserHandle.of(i7), queryBroadcastReceiversAsUser2);
                    break;
                } else {
                    DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                            new StringBuilder(
                                    "Couldn't find any receivers that handle"
                                        + " ACTION_DEVICE_POLICY_CHANGED in package "),
                            enforcingAdmin2.mPackageName,
                            "DevicePolicyEngine");
                    break;
                }
        }
    }
}

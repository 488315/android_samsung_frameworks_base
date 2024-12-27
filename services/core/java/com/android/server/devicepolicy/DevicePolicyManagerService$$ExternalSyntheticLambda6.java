package com.android.server.devicepolicy;

import android.content.pm.UserInfo;
import android.net.Uri;
import android.os.UserHandle;
import android.provider.Settings;
import android.provider.Telephony;

import com.android.internal.util.FunctionalUtils;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class DevicePolicyManagerService$$ExternalSyntheticLambda6
        implements FunctionalUtils.ThrowingSupplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DevicePolicyManagerService f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda6(
            DevicePolicyManagerService devicePolicyManagerService, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = devicePolicyManagerService;
        this.f$1 = i;
    }

    public final Object getOrThrow() {
        boolean hasProfileOwner;
        switch (this.$r8$classId) {
            case 0:
                DevicePolicyManagerService devicePolicyManagerService = this.f$0;
                return Boolean.valueOf(
                        devicePolicyManagerService.mLockPatternUtils
                                .isSeparateProfileChallengeEnabled(this.f$1));
            case 1:
                DevicePolicyManagerService devicePolicyManagerService2 = this.f$0;
                List profiles = devicePolicyManagerService2.mUserManager.getProfiles(this.f$1);
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < profiles.size(); i++) {
                    UserInfo userInfo = (UserInfo) profiles.get(i);
                    if (userInfo.isManagedProfile()) {
                        int i2 = userInfo.id;
                        synchronized (devicePolicyManagerService2.getLockObject()) {
                            hasProfileOwner =
                                    devicePolicyManagerService2.mOwners.hasProfileOwner(i2);
                        }
                        if (hasProfileOwner && !userInfo.isSecureFolder()) {
                            arrayList.add(new UserHandle(userInfo.id));
                        }
                    }
                }
                return arrayList;
            case 2:
                DevicePolicyManagerService devicePolicyManagerService3 = this.f$0;
                int i3 = this.f$1;
                UserInfo profileParent =
                        devicePolicyManagerService3.mUserManager.getProfileParent(i3);
                if (profileParent != null) {
                    i3 = profileParent.id;
                }
                return Integer.valueOf(i3);
            case 3:
                return Boolean.valueOf(this.f$0.updatePersonalAppsSuspension(this.f$1));
            case 4:
                return Integer.valueOf(
                        this.f$0
                                .mContext
                                .getContentResolver()
                                .delete(
                                        Uri.withAppendedPath(
                                                Telephony.Carriers.DPC_URI,
                                                Integer.toString(this.f$1)),
                                        null,
                                        null));
            case 5:
                return this.f$0
                        .mContext
                        .getContentResolver()
                        .query(
                                Uri.withAppendedPath(
                                        Telephony.Carriers.DPC_URI, Integer.toString(this.f$1)),
                                null,
                                null,
                                null,
                                "name ASC");
            case 6:
                return this.f$0.getProfileOwnerAsUser(this.f$1);
            case 7:
                DevicePolicyManagerService devicePolicyManagerService4 = this.f$0;
                return devicePolicyManagerService4.mUserManager.getUserInfo(this.f$1);
            case 8:
                return Boolean.valueOf(this.f$0.updatePersonalAppsSuspension(this.f$1));
            case 9:
                DevicePolicyManagerService devicePolicyManagerService5 = this.f$0;
                return Boolean.valueOf(
                        Settings.Secure.putInt(
                                devicePolicyManagerService5.mContext.getContentResolver(),
                                "managed_provisioning_dpc_downloaded",
                                this.f$1));
            case 10:
                return this.f$0.getUserData(this.f$1);
            case 11:
                DevicePolicyManagerService devicePolicyManagerService6 = this.f$0;
                return Boolean.valueOf(
                        devicePolicyManagerService6.mLockPatternUtils.isSecure(this.f$1));
            default:
                DevicePolicyManagerService devicePolicyManagerService7 = this.f$0;
                return Boolean.valueOf(
                        devicePolicyManagerService7.mUserManager.getUserInfo(this.f$1).isDemo());
        }
    }
}

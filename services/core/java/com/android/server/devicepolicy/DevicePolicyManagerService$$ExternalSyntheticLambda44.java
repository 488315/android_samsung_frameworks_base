package com.android.server.devicepolicy;

import android.app.admin.DevicePolicyEventLogger;
import android.os.Bundle;
import android.os.UserHandle;
import com.android.internal.util.FunctionalUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class DevicePolicyManagerService$$ExternalSyntheticLambda44 implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DevicePolicyManagerService f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ Bundle f$2;
    public final /* synthetic */ CallerIdentity f$3;

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda44(DevicePolicyManagerService devicePolicyManagerService, CallerIdentity callerIdentity, String str, Bundle bundle) {
        this.$r8$classId = 2;
        this.f$0 = devicePolicyManagerService;
        this.f$3 = callerIdentity;
        this.f$1 = str;
        this.f$2 = bundle;
    }

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda44(DevicePolicyManagerService devicePolicyManagerService, String str, Bundle bundle, CallerIdentity callerIdentity, int i) {
        this.$r8$classId = i;
        this.f$0 = devicePolicyManagerService;
        this.f$1 = str;
        this.f$2 = bundle;
        this.f$3 = callerIdentity;
    }

    public final void runOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                DevicePolicyManagerService devicePolicyManagerService = this.f$0;
                devicePolicyManagerService.mUserManager.setApplicationRestrictions(this.f$1, this.f$2, UserHandle.getUserHandleForUid(this.f$3.mUid));
                break;
            case 1:
                DevicePolicyManagerService devicePolicyManagerService2 = this.f$0;
                devicePolicyManagerService2.mUserManager.setApplicationRestrictions(this.f$1, this.f$2, UserHandle.getUserHandleForUid(this.f$3.mUid));
                break;
            default:
                DevicePolicyManagerService devicePolicyManagerService3 = this.f$0;
                CallerIdentity callerIdentity = this.f$3;
                String str = this.f$1;
                Bundle bundle = this.f$2;
                devicePolicyManagerService3.mUserManager.setUserName(UserHandle.getUserId(callerIdentity.mUid), str);
                DevicePolicyEventLogger.createEvent(40).setAdmin(callerIdentity.mComponentName).setKnoxBundleValue(bundle).write();
                break;
        }
    }
}

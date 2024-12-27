package com.android.server.devicepolicy;

import android.content.ComponentName;
import com.android.internal.util.FunctionalUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class DevicePolicyManagerService$$ExternalSyntheticLambda42 implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DevicePolicyManagerService f$0;
    public final /* synthetic */ ActiveAdmin f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ ComponentName f$3;

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda42(DevicePolicyManagerService devicePolicyManagerService, ActiveAdmin activeAdmin, int i, ComponentName componentName, int i2) {
        this.$r8$classId = i2;
        this.f$0 = devicePolicyManagerService;
        this.f$1 = activeAdmin;
        this.f$2 = i;
        this.f$3 = componentName;
    }

    public final void runOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                DevicePolicyManagerService devicePolicyManagerService = this.f$0;
                ActiveAdmin activeAdmin = this.f$1;
                int i = this.f$2;
                ComponentName componentName = this.f$3;
                devicePolicyManagerService.clearDeviceOwnerLocked(i, activeAdmin);
                devicePolicyManagerService.removeActiveAdminLocked(i, componentName);
                devicePolicyManagerService.mInjector.getClass();
                devicePolicyManagerService.mKnoxAnalyticsHelper.sendOwnerChangedBroadcastWithExtra(i, activeAdmin.info.getPackageName(), false);
                break;
            default:
                DevicePolicyManagerService devicePolicyManagerService2 = this.f$0;
                ActiveAdmin activeAdmin2 = this.f$1;
                int i2 = this.f$2;
                ComponentName componentName2 = this.f$3;
                devicePolicyManagerService2.clearProfileOwnerLocked(i2, activeAdmin2);
                devicePolicyManagerService2.removeActiveAdminLocked(i2, componentName2);
                devicePolicyManagerService2.sendOwnerChangedBroadcast(i2, "android.app.action.PROFILE_OWNER_CHANGED");
                break;
        }
    }
}

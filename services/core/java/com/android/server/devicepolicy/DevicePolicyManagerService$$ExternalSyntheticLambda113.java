package com.android.server.devicepolicy;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.provider.ContactsInternal;
import com.android.internal.util.FunctionalUtils;
import com.android.server.devicepolicy.DevicePolicyManagerService;
import com.samsung.android.knox.SemPersonaManager;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class DevicePolicyManagerService$$ExternalSyntheticLambda113 implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ DevicePolicyManagerService f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ Object f$3;

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda113(DevicePolicyManagerService devicePolicyManagerService, ComponentName componentName, int i, boolean z) {
        this.f$0 = devicePolicyManagerService;
        this.f$3 = componentName;
        this.f$2 = i;
        this.f$1 = z;
    }

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda113(DevicePolicyManagerService devicePolicyManagerService, boolean z, int i, Intent intent) {
        this.f$0 = devicePolicyManagerService;
        this.f$1 = z;
        this.f$2 = i;
        this.f$3 = intent;
    }

    public final void runOrThrow() {
        int managedUserId;
        switch (this.$r8$classId) {
            case 0:
                DevicePolicyManagerService devicePolicyManagerService = this.f$0;
                boolean z = this.f$1;
                int i = this.f$2;
                Intent intent = (Intent) this.f$3;
                synchronized (devicePolicyManagerService.getLockObject()) {
                    try {
                        if (z) {
                            DevicePolicyManagerService.Injector injector = devicePolicyManagerService.mInjector;
                            Context context = devicePolicyManagerService.mContext;
                            injector.getClass();
                            managedUserId = SemPersonaManager.getSecureFolderId(context);
                        } else {
                            managedUserId = devicePolicyManagerService.getManagedUserId(i);
                        }
                        if (managedUserId < 0) {
                            return;
                        }
                        if (devicePolicyManagerService.getCrossProfileCallerIdDisabledForUser(managedUserId) && devicePolicyManagerService.getCrossProfileContactsSearchDisabledForUser(managedUserId)) {
                            return;
                        }
                        ContactsInternal.startQuickContactWithErrorToastForUser(devicePolicyManagerService.mContext, intent, new UserHandle(managedUserId));
                        return;
                    } finally {
                    }
                }
            default:
                DevicePolicyManagerService devicePolicyManagerService2 = this.f$0;
                ComponentName componentName = (ComponentName) this.f$3;
                int i2 = this.f$2;
                boolean z2 = this.f$1;
                ActiveAdmin activeAdminForCallerLockedMDM = devicePolicyManagerService2.getActiveAdminForCallerLockedMDM(0, i2, componentName);
                if (activeAdminForCallerLockedMDM.simplePasswordEnabled != z2) {
                    activeAdminForCallerLockedMDM.simplePasswordEnabled = z2;
                    devicePolicyManagerService2.saveSettingsLocked(i2, false, false, false);
                    return;
                }
                return;
        }
    }
}

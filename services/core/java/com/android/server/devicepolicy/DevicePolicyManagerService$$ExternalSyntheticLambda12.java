package com.android.server.devicepolicy;

import android.content.Context;
import android.hardware.usb.UsbManager;
import android.os.Binder;

import com.android.internal.util.FunctionalUtils;

public final /* synthetic */ class DevicePolicyManagerService$$ExternalSyntheticLambda12
        implements FunctionalUtils.ThrowingSupplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda12(
            Object obj, int i, boolean z) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = z;
    }

    public final Object getOrThrow() {
        Boolean valueOf;
        switch (this.$r8$classId) {
            case 0:
                DevicePolicyManagerService devicePolicyManagerService =
                        (DevicePolicyManagerService) this.f$0;
                boolean z = this.f$1;
                synchronized (devicePolicyManagerService.getLockObject()) {
                    if (z) {
                        try {
                            DevicePolicyManagerService.Injector injector =
                                    devicePolicyManagerService.mInjector;
                            DevicePolicyManagerService$$ExternalSyntheticLambda33
                                    devicePolicyManagerService$$ExternalSyntheticLambda33 =
                                            new DevicePolicyManagerService$$ExternalSyntheticLambda33(
                                                    devicePolicyManagerService, 3);
                            injector.getClass();
                            if (!((Boolean)
                                            Binder.withCleanCallingIdentity(
                                                    devicePolicyManagerService$$ExternalSyntheticLambda33))
                                    .booleanValue()) {
                                DevicePolicyManagerService.Injector injector2 =
                                        devicePolicyManagerService.mInjector;
                                DevicePolicyManagerService$$ExternalSyntheticLambda33
                                        devicePolicyManagerService$$ExternalSyntheticLambda332 =
                                                new DevicePolicyManagerService$$ExternalSyntheticLambda33(
                                                        devicePolicyManagerService, 6);
                                injector2.getClass();
                                valueOf =
                                        Boolean.valueOf(
                                                ((Boolean)
                                                                        Binder
                                                                                .withCleanCallingIdentity(
                                                                                        devicePolicyManagerService$$ExternalSyntheticLambda332))
                                                                .booleanValue()
                                                        & devicePolicyManagerService
                                                                .migratePoliciesPostUpgradeToDevicePolicyEngineLocked());
                            }
                        } finally {
                        }
                    }
                    DevicePolicyManagerService.Injector injector3 =
                            devicePolicyManagerService.mInjector;
                    DevicePolicyManagerService$$ExternalSyntheticLambda33
                            devicePolicyManagerService$$ExternalSyntheticLambda333 =
                                    new DevicePolicyManagerService$$ExternalSyntheticLambda33(
                                            devicePolicyManagerService, 5);
                    injector3.getClass();
                    if (!((Boolean)
                                    Binder.withCleanCallingIdentity(
                                            devicePolicyManagerService$$ExternalSyntheticLambda333))
                            .booleanValue()) {
                        valueOf = Boolean.FALSE;
                    }
                    DevicePolicyManagerService.Injector injector22 =
                            devicePolicyManagerService.mInjector;
                    DevicePolicyManagerService$$ExternalSyntheticLambda33
                            devicePolicyManagerService$$ExternalSyntheticLambda3322 =
                                    new DevicePolicyManagerService$$ExternalSyntheticLambda33(
                                            devicePolicyManagerService, 6);
                    injector22.getClass();
                    valueOf =
                            Boolean.valueOf(
                                    ((Boolean)
                                                            Binder.withCleanCallingIdentity(
                                                                    devicePolicyManagerService$$ExternalSyntheticLambda3322))
                                                    .booleanValue()
                                            & devicePolicyManagerService
                                                    .migratePoliciesPostUpgradeToDevicePolicyEngineLocked());
                }
                return valueOf;
            default:
                Context context = (Context) this.f$0;
                return Boolean.valueOf(
                        ((UsbManager) context.getSystemService(UsbManager.class))
                                .enableUsbDataSignal(this.f$1));
        }
    }
}

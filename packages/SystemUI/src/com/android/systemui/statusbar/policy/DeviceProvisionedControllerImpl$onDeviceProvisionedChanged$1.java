package com.android.systemui.statusbar.policy;

import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
final /* synthetic */ class DeviceProvisionedControllerImpl$onDeviceProvisionedChanged$1 extends FunctionReferenceImpl implements Function1 {
    public static final DeviceProvisionedControllerImpl$onDeviceProvisionedChanged$1 INSTANCE = new DeviceProvisionedControllerImpl$onDeviceProvisionedChanged$1();

    public DeviceProvisionedControllerImpl$onDeviceProvisionedChanged$1() {
        super(1, DeviceProvisionedController.DeviceProvisionedListener.class, "onDeviceProvisionedChanged", "onDeviceProvisionedChanged()V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        ((DeviceProvisionedController.DeviceProvisionedListener) obj).onDeviceProvisionedChanged();
        return Unit.INSTANCE;
    }
}

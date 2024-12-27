package com.android.systemui.statusbar.policy;

import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
final /* synthetic */ class DeviceProvisionedControllerImpl$onUserSetupChanged$1 extends FunctionReferenceImpl implements Function1 {
    public static final DeviceProvisionedControllerImpl$onUserSetupChanged$1 INSTANCE = new DeviceProvisionedControllerImpl$onUserSetupChanged$1();

    public DeviceProvisionedControllerImpl$onUserSetupChanged$1() {
        super(1, DeviceProvisionedController.DeviceProvisionedListener.class, "onUserSetupChanged", "onUserSetupChanged()V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        ((DeviceProvisionedController.DeviceProvisionedListener) obj).onUserSetupChanged();
        return Unit.INSTANCE;
    }
}

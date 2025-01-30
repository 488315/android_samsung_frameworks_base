package com.android.systemui.statusbar.policy;

import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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

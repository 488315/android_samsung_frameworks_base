package com.android.systemui.statusbar.policy;

import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final /* synthetic */ class DeviceProvisionedControllerImpl$onDeviceProvisionedChanged$1 extends FunctionReferenceImpl implements Function1 {
    public static final DeviceProvisionedControllerImpl$onDeviceProvisionedChanged$1 INSTANCE = new DeviceProvisionedControllerImpl$onDeviceProvisionedChanged$1();

    public DeviceProvisionedControllerImpl$onDeviceProvisionedChanged$1() {
        super(1, DeviceProvisionedController.DeviceProvisionedListener.class, "onDeviceProvisionedChanged", "onDeviceProvisionedChanged()V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        ((DeviceProvisionedController.DeviceProvisionedListener) obj).onDeviceProvisionedChanged();
        return Unit.INSTANCE;
    }
}

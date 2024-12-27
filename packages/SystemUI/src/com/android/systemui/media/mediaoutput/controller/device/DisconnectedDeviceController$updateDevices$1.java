package com.android.systemui.media.mediaoutput.controller.device;

import com.android.systemui.media.mediaoutput.controller.device.DisconnectedDeviceController;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class DisconnectedDeviceController$updateDevices$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ DisconnectedDeviceController this$0;

    public DisconnectedDeviceController$updateDevices$1(DisconnectedDeviceController disconnectedDeviceController, Continuation continuation) {
        super(continuation);
        this.this$0 = disconnectedDeviceController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        DisconnectedDeviceController disconnectedDeviceController = this.this$0;
        DisconnectedDeviceController.Companion companion = DisconnectedDeviceController.Companion;
        return disconnectedDeviceController.updateDevices$2(null, this);
    }
}

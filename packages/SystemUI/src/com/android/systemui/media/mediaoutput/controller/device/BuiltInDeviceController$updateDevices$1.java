package com.android.systemui.media.mediaoutput.controller.device;

import com.android.systemui.media.mediaoutput.controller.device.BuiltInDeviceController;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class BuiltInDeviceController$updateDevices$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ BuiltInDeviceController this$0;

    public BuiltInDeviceController$updateDevices$1(BuiltInDeviceController builtInDeviceController, Continuation continuation) {
        super(continuation);
        this.this$0 = builtInDeviceController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        BuiltInDeviceController builtInDeviceController = this.this$0;
        BuiltInDeviceController.Companion companion = BuiltInDeviceController.Companion;
        return builtInDeviceController.updateDevices$1(null, false, this);
    }
}

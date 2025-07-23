package com.android.systemui.media.mediaoutput.controller.device;

import com.android.systemui.media.mediaoutput.controller.device.BuiltInDeviceController;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class BuiltInDeviceController$updateDevices$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ BuiltInDeviceController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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

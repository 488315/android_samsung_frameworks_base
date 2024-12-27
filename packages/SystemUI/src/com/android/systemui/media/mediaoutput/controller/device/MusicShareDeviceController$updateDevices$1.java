package com.android.systemui.media.mediaoutput.controller.device;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class MusicShareDeviceController$updateDevices$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ MusicShareDeviceController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MusicShareDeviceController$updateDevices$1(MusicShareDeviceController musicShareDeviceController, Continuation continuation) {
        super(continuation);
        this.this$0 = musicShareDeviceController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return MusicShareDeviceController.access$updateDevices(this.this$0, this);
    }
}

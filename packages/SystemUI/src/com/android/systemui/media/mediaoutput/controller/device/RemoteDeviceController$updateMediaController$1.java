package com.android.systemui.media.mediaoutput.controller.device;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class RemoteDeviceController$updateMediaController$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ RemoteDeviceController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RemoteDeviceController$updateMediaController$1(RemoteDeviceController remoteDeviceController, Continuation continuation) {
        super(continuation);
        this.this$0 = remoteDeviceController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return RemoteDeviceController.access$updateMediaController(this.this$0, this);
    }
}

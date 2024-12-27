package com.android.systemui.media.mediaoutput.controller.device;

import com.android.systemui.media.mediaoutput.controller.device.DisconnectedDeviceController;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class DisconnectedDeviceController$updateDevices$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ DisconnectedDeviceController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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

package com.android.systemui.media.mediaoutput.controller.device;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class WifiDisplayDeviceController$updateDevices$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ WifiDisplayDeviceController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WifiDisplayDeviceController$updateDevices$1(WifiDisplayDeviceController wifiDisplayDeviceController, Continuation continuation) {
        super(continuation);
        this.this$0 = wifiDisplayDeviceController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return WifiDisplayDeviceController.access$updateDevices(this.this$0, this);
    }
}

package com.android.systemui.media.mediaoutput.controller.device;

import com.android.systemui.media.mediaoutput.controller.device.BluetoothDeviceController;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class BluetoothDeviceController$updateDevices$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ BluetoothDeviceController this$0;

    public BluetoothDeviceController$updateDevices$1(BluetoothDeviceController bluetoothDeviceController, Continuation continuation) {
        super(continuation);
        this.this$0 = bluetoothDeviceController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        BluetoothDeviceController bluetoothDeviceController = this.this$0;
        BluetoothDeviceController.Companion companion = BluetoothDeviceController.Companion;
        return bluetoothDeviceController.updateDevices(null, false, this);
    }
}

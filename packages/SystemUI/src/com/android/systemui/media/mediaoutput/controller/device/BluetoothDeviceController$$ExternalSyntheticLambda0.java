package com.android.systemui.media.mediaoutput.controller.device;

import android.bluetooth.BluetoothAdapter;
import com.android.systemui.media.mediaoutput.controller.device.BluetoothDeviceController;
import kotlin.jvm.functions.Function0;

/* loaded from: classes2.dex */
public final /* synthetic */ class BluetoothDeviceController$$ExternalSyntheticLambda0 implements Function0 {
    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        BluetoothDeviceController.Companion companion = BluetoothDeviceController.Companion;
        return BluetoothAdapter.getDefaultAdapter();
    }
}

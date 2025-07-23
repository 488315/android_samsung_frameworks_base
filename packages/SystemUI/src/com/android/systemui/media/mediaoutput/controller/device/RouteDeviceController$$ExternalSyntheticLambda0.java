package com.android.systemui.media.mediaoutput.controller.device;

import android.bluetooth.BluetoothAdapter;
import com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class RouteDeviceController$$ExternalSyntheticLambda0 implements Function0 {
    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        RouteDeviceController.Companion companion = RouteDeviceController.Companion;
        return BluetoothAdapter.getDefaultAdapter();
    }
}

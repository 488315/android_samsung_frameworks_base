package com.android.settingslib.bluetooth;

import kotlinx.coroutines.flow.CallbackFlowBuilder;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class LocalBluetoothManagerExtKt {
    public static final CallbackFlowBuilder getHeadsetAudioModeChanges(LocalBluetoothManager localBluetoothManager) {
        return FlowKt.callbackFlow(new LocalBluetoothManagerExtKt$headsetAudioModeChanges$1(localBluetoothManager, null));
    }
}

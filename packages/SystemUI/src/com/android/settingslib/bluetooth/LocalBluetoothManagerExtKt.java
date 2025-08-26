package com.android.settingslib.bluetooth;

import kotlinx.coroutines.flow.CallbackFlowBuilder;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: classes.dex */
public abstract class LocalBluetoothManagerExtKt {
    public static final CallbackFlowBuilder getHeadsetAudioModeChanges(LocalBluetoothManager localBluetoothManager) {
        return FlowKt.callbackFlow(new LocalBluetoothManagerExtKt$headsetAudioModeChanges$1(localBluetoothManager, null));
    }
}

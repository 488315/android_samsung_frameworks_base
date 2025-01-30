package com.android.systemui.statusbar.policy.bluetooth;

import com.android.settingslib.bluetooth.LocalBluetoothManager;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BluetoothRepositoryImpl implements BluetoothRepository {
    public final CoroutineDispatcher bgDispatcher;
    public final LocalBluetoothManager localBluetoothManager;

    public BluetoothRepositoryImpl(CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, LocalBluetoothManager localBluetoothManager) {
        this.bgDispatcher = coroutineDispatcher;
        this.localBluetoothManager = localBluetoothManager;
    }
}

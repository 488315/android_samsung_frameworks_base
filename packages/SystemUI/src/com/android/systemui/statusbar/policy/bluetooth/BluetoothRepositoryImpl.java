package com.android.systemui.statusbar.policy.bluetooth;

import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.systemui.statusbar.policy.BluetoothControllerImpl$$ExternalSyntheticLambda1;
import java.util.Collection;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

public final class BluetoothRepositoryImpl implements BluetoothRepository {
    public final CoroutineDispatcher bgDispatcher;
    public final LocalBluetoothManager localBluetoothManager;
    public final CoroutineScope scope;

    public BluetoothRepositoryImpl(CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, LocalBluetoothManager localBluetoothManager) {
        this.scope = coroutineScope;
        this.bgDispatcher = coroutineDispatcher;
        this.localBluetoothManager = localBluetoothManager;
    }

    public final void fetchConnectionStatusInBackground(Collection collection, BluetoothControllerImpl$$ExternalSyntheticLambda1 bluetoothControllerImpl$$ExternalSyntheticLambda1) {
        BuildersKt.launch$default(this.scope, null, null, new BluetoothRepositoryImpl$fetchConnectionStatusInBackground$1(this, collection, bluetoothControllerImpl$$ExternalSyntheticLambda1, null), 3);
    }
}

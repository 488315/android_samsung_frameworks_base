package com.android.systemui.statusbar.policy.bluetooth;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.systemui.statusbar.policy.BluetoothControllerImpl$$ExternalSyntheticLambda0;
import java.util.Collection;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class BluetoothRepositoryImpl implements BluetoothRepository {
    public final CoroutineDispatcher bgDispatcher;
    public final LocalBluetoothManager localBluetoothManager;
    public final CoroutineScope scope;

    public BluetoothRepositoryImpl(CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, LocalBluetoothManager localBluetoothManager) {
        this.scope = coroutineScope;
        this.bgDispatcher = coroutineDispatcher;
        this.localBluetoothManager = localBluetoothManager;
    }

    public final void fetchConnectionStatusInBackground(Collection collection, BluetoothControllerImpl$$ExternalSyntheticLambda0 bluetoothControllerImpl$$ExternalSyntheticLambda0) {
        CoroutineTracingKt.launchTraced$default(this.scope, null, null, new BluetoothRepositoryImpl$fetchConnectionStatusInBackground$1(this, collection, bluetoothControllerImpl$$ExternalSyntheticLambda0, null), 7);
    }
}

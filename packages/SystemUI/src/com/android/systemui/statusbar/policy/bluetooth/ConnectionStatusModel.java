package com.android.systemui.statusbar.policy.bluetooth;

import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ConnectionStatusModel {
    public final List connectedDevices;
    public final int maxConnectionState;

    public ConnectionStatusModel(int i, List<? extends CachedBluetoothDevice> list) {
        this.maxConnectionState = i;
        this.connectedDevices = list;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ConnectionStatusModel)) {
            return false;
        }
        ConnectionStatusModel connectionStatusModel = (ConnectionStatusModel) obj;
        return this.maxConnectionState == connectionStatusModel.maxConnectionState && Intrinsics.areEqual(this.connectedDevices, connectionStatusModel.connectedDevices);
    }

    public final int hashCode() {
        return this.connectedDevices.hashCode() + (Integer.hashCode(this.maxConnectionState) * 31);
    }

    public final String toString() {
        return "ConnectionStatusModel(maxConnectionState=" + this.maxConnectionState + ", connectedDevices=" + this.connectedDevices + ")";
    }
}

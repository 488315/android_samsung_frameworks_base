package com.android.systemui.statusbar.policy.bluetooth;

import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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

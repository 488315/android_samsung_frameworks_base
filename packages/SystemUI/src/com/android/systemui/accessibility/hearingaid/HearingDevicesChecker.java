package com.android.systemui.accessibility.hearingaid;

import android.content.Context;
import com.android.settingslib.bluetooth.LocalBluetoothManager;

public final class HearingDevicesChecker {
    public final Context mContext;
    public final LocalBluetoothManager mLocalBluetoothManager;

    public HearingDevicesChecker(Context context, LocalBluetoothManager localBluetoothManager) {
        this.mContext = context;
        this.mLocalBluetoothManager = localBluetoothManager;
    }

    public final boolean isAnyPairedHearingDevice() {
        LocalBluetoothManager localBluetoothManager = this.mLocalBluetoothManager;
        if (localBluetoothManager != null && localBluetoothManager.mLocalAdapter.mAdapter.isEnabled()) {
            return localBluetoothManager.mCachedDeviceManager.getCachedDevicesCopy().stream().anyMatch(new HearingDevicesChecker$$ExternalSyntheticLambda0(this, 0));
        }
        return false;
    }
}

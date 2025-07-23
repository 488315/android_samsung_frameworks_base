package com.android.systemui.accessibility.hearingaid;

import android.content.Context;
import com.android.settingslib.bluetooth.LocalBluetoothManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class HearingDevicesChecker {
    public final Context mContext;
    public final LocalBluetoothManager mLocalBluetoothManager;

    public HearingDevicesChecker(Context context, LocalBluetoothManager localBluetoothManager) {
        this.mContext = context;
        this.mLocalBluetoothManager = localBluetoothManager;
    }

    public final boolean isAnyActiveHearingDevice() {
        LocalBluetoothManager localBluetoothManager = this.mLocalBluetoothManager;
        if (localBluetoothManager != null && localBluetoothManager.mLocalAdapter.mAdapter.isEnabled()) {
            return localBluetoothManager.mCachedDeviceManager.getCachedDevicesCopy().stream().anyMatch(new HearingDevicesChecker$$ExternalSyntheticLambda0(this, 1));
        }
        return false;
    }

    public final boolean isAnyPairedHearingDevice() {
        LocalBluetoothManager localBluetoothManager = this.mLocalBluetoothManager;
        if (localBluetoothManager != null && localBluetoothManager.mLocalAdapter.mAdapter.isEnabled()) {
            return localBluetoothManager.mCachedDeviceManager.getCachedDevicesCopy().stream().anyMatch(new HearingDevicesChecker$$ExternalSyntheticLambda0(this, 0));
        }
        return false;
    }
}

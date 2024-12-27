package com.android.systemui.accessibility.hearingaid;

import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.flags.Flags;
import java.util.function.Predicate;

public final /* synthetic */ class HearingDevicesChecker$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ HearingDevicesChecker f$0;

    public /* synthetic */ HearingDevicesChecker$$ExternalSyntheticLambda0(HearingDevicesChecker hearingDevicesChecker, int i) {
        this.$r8$classId = i;
        this.f$0 = hearingDevicesChecker;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        HearingDevicesChecker hearingDevicesChecker = this.f$0;
        CachedBluetoothDevice cachedBluetoothDevice = (CachedBluetoothDevice) obj;
        hearingDevicesChecker.getClass();
        switch (i) {
            case 0:
                if (cachedBluetoothDevice.isHearingAidDevice() && cachedBluetoothDevice.mBondState != 10) {
                    Flags.enableHideExclusivelyManagedBluetoothDevice();
                    if (!BluetoothUtils.isExclusivelyManagedBluetoothDevice(hearingDevicesChecker.mContext, cachedBluetoothDevice.mDevice)) {
                    }
                }
                break;
            default:
                if (BluetoothUtils.isActiveMediaDevice(cachedBluetoothDevice) && BluetoothUtils.isAvailableHearingDevice(cachedBluetoothDevice)) {
                    Flags.enableHideExclusivelyManagedBluetoothDevice();
                    if (!BluetoothUtils.isExclusivelyManagedBluetoothDevice(hearingDevicesChecker.mContext, cachedBluetoothDevice.mDevice)) {
                    }
                }
                break;
        }
        return false;
    }
}

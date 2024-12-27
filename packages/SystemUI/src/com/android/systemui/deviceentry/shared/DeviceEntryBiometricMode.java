package com.android.systemui.deviceentry.shared;

import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import kotlin.enums.EnumEntriesKt;

public final class DeviceEntryBiometricMode {
    public static final /* synthetic */ DeviceEntryBiometricMode[] $VALUES;
    public static final DeviceEntryBiometricMode CO_EXPERIENCE;
    public static final DeviceEntryBiometricMode FACE_ONLY;
    public static final DeviceEntryBiometricMode FINGERPRINT_ONLY;
    public static final DeviceEntryBiometricMode NONE;

    static {
        DeviceEntryBiometricMode deviceEntryBiometricMode = new DeviceEntryBiometricMode(PeripheralBarcodeConstants.Symbology.Type.TYPE_NONE, 0);
        NONE = deviceEntryBiometricMode;
        DeviceEntryBiometricMode deviceEntryBiometricMode2 = new DeviceEntryBiometricMode("FACE_ONLY", 1);
        FACE_ONLY = deviceEntryBiometricMode2;
        DeviceEntryBiometricMode deviceEntryBiometricMode3 = new DeviceEntryBiometricMode("FINGERPRINT_ONLY", 2);
        FINGERPRINT_ONLY = deviceEntryBiometricMode3;
        DeviceEntryBiometricMode deviceEntryBiometricMode4 = new DeviceEntryBiometricMode("CO_EXPERIENCE", 3);
        CO_EXPERIENCE = deviceEntryBiometricMode4;
        DeviceEntryBiometricMode[] deviceEntryBiometricModeArr = {deviceEntryBiometricMode, deviceEntryBiometricMode2, deviceEntryBiometricMode3, deviceEntryBiometricMode4};
        $VALUES = deviceEntryBiometricModeArr;
        EnumEntriesKt.enumEntries(deviceEntryBiometricModeArr);
    }

    private DeviceEntryBiometricMode(String str, int i) {
    }

    public static DeviceEntryBiometricMode valueOf(String str) {
        return (DeviceEntryBiometricMode) Enum.valueOf(DeviceEntryBiometricMode.class, str);
    }

    public static DeviceEntryBiometricMode[] values() {
        return (DeviceEntryBiometricMode[]) $VALUES.clone();
    }
}

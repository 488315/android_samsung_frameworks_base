package com.android.systemui.deviceentry.shared;

import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

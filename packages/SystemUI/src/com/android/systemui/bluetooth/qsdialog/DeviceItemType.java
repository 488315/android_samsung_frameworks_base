package com.android.systemui.bluetooth.qsdialog;

import kotlin.enums.EnumEntriesKt;

public final class DeviceItemType {
    public static final /* synthetic */ DeviceItemType[] $VALUES;
    public static final DeviceItemType ACTIVE_MEDIA_BLUETOOTH_DEVICE;
    public static final DeviceItemType AVAILABLE_MEDIA_BLUETOOTH_DEVICE;
    public static final DeviceItemType CONNECTED_BLUETOOTH_DEVICE;
    public static final DeviceItemType SAVED_BLUETOOTH_DEVICE;

    static {
        DeviceItemType deviceItemType = new DeviceItemType("ACTIVE_MEDIA_BLUETOOTH_DEVICE", 0);
        ACTIVE_MEDIA_BLUETOOTH_DEVICE = deviceItemType;
        DeviceItemType deviceItemType2 = new DeviceItemType("AUDIO_SHARING_MEDIA_BLUETOOTH_DEVICE", 1);
        DeviceItemType deviceItemType3 = new DeviceItemType("AVAILABLE_MEDIA_BLUETOOTH_DEVICE", 2);
        AVAILABLE_MEDIA_BLUETOOTH_DEVICE = deviceItemType3;
        DeviceItemType deviceItemType4 = new DeviceItemType("CONNECTED_BLUETOOTH_DEVICE", 3);
        CONNECTED_BLUETOOTH_DEVICE = deviceItemType4;
        DeviceItemType deviceItemType5 = new DeviceItemType("SAVED_BLUETOOTH_DEVICE", 4);
        SAVED_BLUETOOTH_DEVICE = deviceItemType5;
        DeviceItemType[] deviceItemTypeArr = {deviceItemType, deviceItemType2, deviceItemType3, deviceItemType4, deviceItemType5};
        $VALUES = deviceItemTypeArr;
        EnumEntriesKt.enumEntries(deviceItemTypeArr);
    }

    private DeviceItemType(String str, int i) {
    }

    public static DeviceItemType valueOf(String str) {
        return (DeviceItemType) Enum.valueOf(DeviceItemType.class, str);
    }

    public static DeviceItemType[] values() {
        return (DeviceItemType[]) $VALUES.clone();
    }
}

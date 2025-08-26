package com.android.systemui.inputdevice.tutorial.data.repository;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes2.dex */
public final class DeviceType {
    public static final /* synthetic */ DeviceType[] $VALUES;
    public static final DeviceType KEYBOARD;
    public static final DeviceType TOUCHPAD;

    static {
        DeviceType deviceType = new DeviceType("KEYBOARD", 0);
        KEYBOARD = deviceType;
        DeviceType deviceType2 = new DeviceType("TOUCHPAD", 1);
        TOUCHPAD = deviceType2;
        DeviceType[] deviceTypeArr = {deviceType, deviceType2};
        $VALUES = deviceTypeArr;
        EnumEntriesKt.enumEntries(deviceTypeArr);
    }

    private DeviceType(String str, int i) {
    }

    public static DeviceType valueOf(String str) {
        return (DeviceType) Enum.valueOf(DeviceType.class, str);
    }

    public static DeviceType[] values() {
        return (DeviceType[]) $VALUES.clone();
    }
}

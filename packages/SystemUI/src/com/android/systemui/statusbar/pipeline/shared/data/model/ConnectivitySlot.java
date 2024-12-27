package com.android.systemui.statusbar.pipeline.shared.data.model;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
public final class ConnectivitySlot {
    public static final /* synthetic */ ConnectivitySlot[] $VALUES;
    public static final ConnectivitySlot AIRPLANE;
    public static final ConnectivitySlot ETHERNET;
    public static final ConnectivitySlot MOBILE;
    public static final ConnectivitySlot WIFI;

    static {
        ConnectivitySlot connectivitySlot = new ConnectivitySlot("AIRPLANE", 0);
        AIRPLANE = connectivitySlot;
        ConnectivitySlot connectivitySlot2 = new ConnectivitySlot("ETHERNET", 1);
        ETHERNET = connectivitySlot2;
        ConnectivitySlot connectivitySlot3 = new ConnectivitySlot("MOBILE", 2);
        MOBILE = connectivitySlot3;
        ConnectivitySlot connectivitySlot4 = new ConnectivitySlot("WIFI", 3);
        WIFI = connectivitySlot4;
        ConnectivitySlot[] connectivitySlotArr = {connectivitySlot, connectivitySlot2, connectivitySlot3, connectivitySlot4};
        $VALUES = connectivitySlotArr;
        EnumEntriesKt.enumEntries(connectivitySlotArr);
    }

    private ConnectivitySlot(String str, int i) {
    }

    public static ConnectivitySlot valueOf(String str) {
        return (ConnectivitySlot) Enum.valueOf(ConnectivitySlot.class, str);
    }

    public static ConnectivitySlot[] values() {
        return (ConnectivitySlot[]) $VALUES.clone();
    }
}

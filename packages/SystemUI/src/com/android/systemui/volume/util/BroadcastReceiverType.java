package com.android.systemui.volume.util;

import kotlin.enums.EnumEntriesKt;

public final class BroadcastReceiverType {
    public static final /* synthetic */ BroadcastReceiverType[] $VALUES;
    public static final BroadcastReceiverType A2DP_ACTIVE_DEVICE_CHANGE;
    public static final BroadcastReceiverType ALLSOUND_OFF;
    public static final BroadcastReceiverType AOD;
    public static final BroadcastReceiverType BUDS_ICON_SERVER_CHANGE;
    public static final BroadcastReceiverType BUDS_TOGETHER;
    public static final BroadcastReceiverType DISPLAY_MANAGER;
    public static final BroadcastReceiverType DUAL_AUDIO_MODE;
    public static final BroadcastReceiverType HDMI_CONNECTION;
    public static final BroadcastReceiverType HEADSET_CONNECTION;
    public static final BroadcastReceiverType MIRROR_LINK;
    public static final BroadcastReceiverType MUSIC_SHARE;
    public static final BroadcastReceiverType OPEN_THEME;
    public static final BroadcastReceiverType UNINSTALL_PACKAGE;

    static {
        BroadcastReceiverType broadcastReceiverType = new BroadcastReceiverType("DISPLAY_MANAGER", 0);
        DISPLAY_MANAGER = broadcastReceiverType;
        BroadcastReceiverType broadcastReceiverType2 = new BroadcastReceiverType("ALLSOUND_OFF", 1);
        ALLSOUND_OFF = broadcastReceiverType2;
        BroadcastReceiverType broadcastReceiverType3 = new BroadcastReceiverType("MIRROR_LINK", 2);
        MIRROR_LINK = broadcastReceiverType3;
        BroadcastReceiverType broadcastReceiverType4 = new BroadcastReceiverType("BUDS_TOGETHER", 3);
        BUDS_TOGETHER = broadcastReceiverType4;
        BroadcastReceiverType broadcastReceiverType5 = new BroadcastReceiverType("MUSIC_SHARE", 4);
        MUSIC_SHARE = broadcastReceiverType5;
        BroadcastReceiverType broadcastReceiverType6 = new BroadcastReceiverType("DUAL_AUDIO_MODE", 5);
        DUAL_AUDIO_MODE = broadcastReceiverType6;
        BroadcastReceiverType broadcastReceiverType7 = new BroadcastReceiverType("OPEN_THEME", 6);
        OPEN_THEME = broadcastReceiverType7;
        BroadcastReceiverType broadcastReceiverType8 = new BroadcastReceiverType("AOD", 7);
        AOD = broadcastReceiverType8;
        BroadcastReceiverType broadcastReceiverType9 = new BroadcastReceiverType("HEADSET_CONNECTION", 8);
        HEADSET_CONNECTION = broadcastReceiverType9;
        BroadcastReceiverType broadcastReceiverType10 = new BroadcastReceiverType("A2DP_ACTIVE_DEVICE_CHANGE", 9);
        A2DP_ACTIVE_DEVICE_CHANGE = broadcastReceiverType10;
        BroadcastReceiverType broadcastReceiverType11 = new BroadcastReceiverType("BUDS_ICON_SERVER_CHANGE", 10);
        BUDS_ICON_SERVER_CHANGE = broadcastReceiverType11;
        BroadcastReceiverType broadcastReceiverType12 = new BroadcastReceiverType("UNINSTALL_PACKAGE", 11);
        UNINSTALL_PACKAGE = broadcastReceiverType12;
        BroadcastReceiverType broadcastReceiverType13 = new BroadcastReceiverType("HDMI_CONNECTION", 12);
        HDMI_CONNECTION = broadcastReceiverType13;
        BroadcastReceiverType[] broadcastReceiverTypeArr = {broadcastReceiverType, broadcastReceiverType2, broadcastReceiverType3, broadcastReceiverType4, broadcastReceiverType5, broadcastReceiverType6, broadcastReceiverType7, broadcastReceiverType8, broadcastReceiverType9, broadcastReceiverType10, broadcastReceiverType11, broadcastReceiverType12, broadcastReceiverType13};
        $VALUES = broadcastReceiverTypeArr;
        EnumEntriesKt.enumEntries(broadcastReceiverTypeArr);
    }

    private BroadcastReceiverType(String str, int i) {
    }

    public static BroadcastReceiverType valueOf(String str) {
        return (BroadcastReceiverType) Enum.valueOf(BroadcastReceiverType.class, str);
    }

    public static BroadcastReceiverType[] values() {
        return (BroadcastReceiverType[]) $VALUES.clone();
    }
}

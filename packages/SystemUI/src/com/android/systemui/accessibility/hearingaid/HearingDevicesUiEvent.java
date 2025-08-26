package com.android.systemui.accessibility.hearingaid;

import com.android.internal.logging.UiEventLogger;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes.dex */
public final class HearingDevicesUiEvent implements UiEventLogger.UiEventEnum {
    public static final /* synthetic */ HearingDevicesUiEvent[] $VALUES;
    public static final HearingDevicesUiEvent HEARING_DEVICES_AMBIENT_CHANGE_SEPARATED;
    public static final HearingDevicesUiEvent HEARING_DEVICES_AMBIENT_CHANGE_UNIFIED;
    public static final HearingDevicesUiEvent HEARING_DEVICES_AMBIENT_COLLAPSE_CONTROLS;
    public static final HearingDevicesUiEvent HEARING_DEVICES_AMBIENT_EXPAND_CONTROLS;
    public static final HearingDevicesUiEvent HEARING_DEVICES_AMBIENT_MUTE;
    public static final HearingDevicesUiEvent HEARING_DEVICES_AMBIENT_UNMUTE;
    public static final HearingDevicesUiEvent HEARING_DEVICES_CONNECT;
    public static final HearingDevicesUiEvent HEARING_DEVICES_DIALOG_SHOW;
    public static final HearingDevicesUiEvent HEARING_DEVICES_DISCONNECT;
    public static final HearingDevicesUiEvent HEARING_DEVICES_GEAR_CLICK;
    public static final HearingDevicesUiEvent HEARING_DEVICES_INPUT_ROUTING_SELECT;
    public static final HearingDevicesUiEvent HEARING_DEVICES_PAIR;
    public static final HearingDevicesUiEvent HEARING_DEVICES_PRESET_SELECT;
    public static final HearingDevicesUiEvent HEARING_DEVICES_RELATED_TOOL_CLICK;
    public static final HearingDevicesUiEvent HEARING_DEVICES_SETTINGS_CLICK;
    public static final HearingDevicesUiEvent HEARING_DEVICES_SET_ACTIVE;
    private final int id;

    static {
        HearingDevicesUiEvent hearingDevicesUiEvent = new HearingDevicesUiEvent("HEARING_DEVICES_DIALOG_SHOW", 0, 1848);
        HEARING_DEVICES_DIALOG_SHOW = hearingDevicesUiEvent;
        HearingDevicesUiEvent hearingDevicesUiEvent2 = new HearingDevicesUiEvent("HEARING_DEVICES_PAIR", 1, 1849);
        HEARING_DEVICES_PAIR = hearingDevicesUiEvent2;
        HearingDevicesUiEvent hearingDevicesUiEvent3 = new HearingDevicesUiEvent("HEARING_DEVICES_CONNECT", 2, 1850);
        HEARING_DEVICES_CONNECT = hearingDevicesUiEvent3;
        HearingDevicesUiEvent hearingDevicesUiEvent4 = new HearingDevicesUiEvent("HEARING_DEVICES_DISCONNECT", 3, 1851);
        HEARING_DEVICES_DISCONNECT = hearingDevicesUiEvent4;
        HearingDevicesUiEvent hearingDevicesUiEvent5 = new HearingDevicesUiEvent("HEARING_DEVICES_SET_ACTIVE", 4, 1852);
        HEARING_DEVICES_SET_ACTIVE = hearingDevicesUiEvent5;
        HearingDevicesUiEvent hearingDevicesUiEvent6 = new HearingDevicesUiEvent("HEARING_DEVICES_GEAR_CLICK", 5, 1853);
        HEARING_DEVICES_GEAR_CLICK = hearingDevicesUiEvent6;
        HearingDevicesUiEvent hearingDevicesUiEvent7 = new HearingDevicesUiEvent("HEARING_DEVICES_PRESET_SELECT", 6, 1854);
        HEARING_DEVICES_PRESET_SELECT = hearingDevicesUiEvent7;
        HearingDevicesUiEvent hearingDevicesUiEvent8 = new HearingDevicesUiEvent("HEARING_DEVICES_RELATED_TOOL_CLICK", 7, 1856);
        HEARING_DEVICES_RELATED_TOOL_CLICK = hearingDevicesUiEvent8;
        HearingDevicesUiEvent hearingDevicesUiEvent9 = new HearingDevicesUiEvent("HEARING_DEVICES_AMBIENT_CHANGE_UNIFIED", 8, 2149);
        HEARING_DEVICES_AMBIENT_CHANGE_UNIFIED = hearingDevicesUiEvent9;
        HearingDevicesUiEvent hearingDevicesUiEvent10 = new HearingDevicesUiEvent("HEARING_DEVICES_AMBIENT_CHANGE_SEPARATED", 9, 2150);
        HEARING_DEVICES_AMBIENT_CHANGE_SEPARATED = hearingDevicesUiEvent10;
        HearingDevicesUiEvent hearingDevicesUiEvent11 = new HearingDevicesUiEvent("HEARING_DEVICES_AMBIENT_MUTE", 10, 2151);
        HEARING_DEVICES_AMBIENT_MUTE = hearingDevicesUiEvent11;
        HearingDevicesUiEvent hearingDevicesUiEvent12 = new HearingDevicesUiEvent("HEARING_DEVICES_AMBIENT_UNMUTE", 11, 2152);
        HEARING_DEVICES_AMBIENT_UNMUTE = hearingDevicesUiEvent12;
        HearingDevicesUiEvent hearingDevicesUiEvent13 = new HearingDevicesUiEvent("HEARING_DEVICES_AMBIENT_EXPAND_CONTROLS", 12, 2153);
        HEARING_DEVICES_AMBIENT_EXPAND_CONTROLS = hearingDevicesUiEvent13;
        HearingDevicesUiEvent hearingDevicesUiEvent14 = new HearingDevicesUiEvent("HEARING_DEVICES_AMBIENT_COLLAPSE_CONTROLS", 13, 2154);
        HEARING_DEVICES_AMBIENT_COLLAPSE_CONTROLS = hearingDevicesUiEvent14;
        HearingDevicesUiEvent hearingDevicesUiEvent15 = new HearingDevicesUiEvent("HEARING_DEVICES_INPUT_ROUTING_SELECT", 14, 2155);
        HEARING_DEVICES_INPUT_ROUTING_SELECT = hearingDevicesUiEvent15;
        HearingDevicesUiEvent hearingDevicesUiEvent16 = new HearingDevicesUiEvent("HEARING_DEVICES_SETTINGS_CLICK", 15, 2172);
        HEARING_DEVICES_SETTINGS_CLICK = hearingDevicesUiEvent16;
        HearingDevicesUiEvent[] hearingDevicesUiEventArr = {hearingDevicesUiEvent, hearingDevicesUiEvent2, hearingDevicesUiEvent3, hearingDevicesUiEvent4, hearingDevicesUiEvent5, hearingDevicesUiEvent6, hearingDevicesUiEvent7, hearingDevicesUiEvent8, hearingDevicesUiEvent9, hearingDevicesUiEvent10, hearingDevicesUiEvent11, hearingDevicesUiEvent12, hearingDevicesUiEvent13, hearingDevicesUiEvent14, hearingDevicesUiEvent15, hearingDevicesUiEvent16};
        $VALUES = hearingDevicesUiEventArr;
        EnumEntriesKt.enumEntries(hearingDevicesUiEventArr);
    }

    private HearingDevicesUiEvent(String str, int i, int i2) {
        this.id = i2;
    }

    public static HearingDevicesUiEvent valueOf(String str) {
        return (HearingDevicesUiEvent) Enum.valueOf(HearingDevicesUiEvent.class, str);
    }

    public static HearingDevicesUiEvent[] values() {
        return (HearingDevicesUiEvent[]) $VALUES.clone();
    }

    public final int getId() {
        return this.id;
    }
}

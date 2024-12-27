package com.android.systemui.stylus;

import com.android.internal.logging.UiEventLogger;
import com.sec.ims.volte2.data.VolteConstants;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
public final class StylusUiEvent implements UiEventLogger.UiEventEnum {
    public static final /* synthetic */ StylusUiEvent[] $VALUES;
    public static final StylusUiEvent BLUETOOTH_STYLUS_CONNECTED;
    public static final StylusUiEvent BLUETOOTH_STYLUS_DISCONNECTED;
    public static final StylusUiEvent STYLUS_LOW_BATTERY_NOTIFICATION_CLICKED;
    public static final StylusUiEvent STYLUS_LOW_BATTERY_NOTIFICATION_DISMISSED;
    public static final StylusUiEvent STYLUS_LOW_BATTERY_NOTIFICATION_SHOWN;
    public static final StylusUiEvent USI_STYLUS_BATTERY_PRESENCE_FIRST_DETECTED;
    public static final StylusUiEvent USI_STYLUS_BATTERY_PRESENCE_REMOVED;
    private final int _id;

    static {
        StylusUiEvent stylusUiEvent = new StylusUiEvent("STYLUS_LOW_BATTERY_NOTIFICATION_SHOWN", 0, 1298);
        STYLUS_LOW_BATTERY_NOTIFICATION_SHOWN = stylusUiEvent;
        StylusUiEvent stylusUiEvent2 = new StylusUiEvent("STYLUS_LOW_BATTERY_NOTIFICATION_CLICKED", 1, 1299);
        STYLUS_LOW_BATTERY_NOTIFICATION_CLICKED = stylusUiEvent2;
        StylusUiEvent stylusUiEvent3 = new StylusUiEvent("STYLUS_LOW_BATTERY_NOTIFICATION_DISMISSED", 2, 1300);
        STYLUS_LOW_BATTERY_NOTIFICATION_DISMISSED = stylusUiEvent3;
        StylusUiEvent stylusUiEvent4 = new StylusUiEvent("STYLUS_STARTED_CHARGING", 3, VolteConstants.ErrorCode.PPP_OPEN_FAILURE);
        StylusUiEvent stylusUiEvent5 = new StylusUiEvent("STYLUS_STOPPED_CHARGING", 4, 1303);
        StylusUiEvent stylusUiEvent6 = new StylusUiEvent("BLUETOOTH_STYLUS_CONNECTED", 5, 1304);
        BLUETOOTH_STYLUS_CONNECTED = stylusUiEvent6;
        StylusUiEvent stylusUiEvent7 = new StylusUiEvent("BLUETOOTH_STYLUS_DISCONNECTED", 6, 1305);
        BLUETOOTH_STYLUS_DISCONNECTED = stylusUiEvent7;
        StylusUiEvent stylusUiEvent8 = new StylusUiEvent("USI_STYLUS_BATTERY_PRESENCE_FIRST_DETECTED", 7, 1306);
        USI_STYLUS_BATTERY_PRESENCE_FIRST_DETECTED = stylusUiEvent8;
        StylusUiEvent stylusUiEvent9 = new StylusUiEvent("USI_STYLUS_BATTERY_PRESENCE_REMOVED", 8, 1307);
        USI_STYLUS_BATTERY_PRESENCE_REMOVED = stylusUiEvent9;
        StylusUiEvent[] stylusUiEventArr = {stylusUiEvent, stylusUiEvent2, stylusUiEvent3, stylusUiEvent4, stylusUiEvent5, stylusUiEvent6, stylusUiEvent7, stylusUiEvent8, stylusUiEvent9};
        $VALUES = stylusUiEventArr;
        EnumEntriesKt.enumEntries(stylusUiEventArr);
    }

    private StylusUiEvent(String str, int i, int i2) {
        this._id = i2;
    }

    public static StylusUiEvent valueOf(String str) {
        return (StylusUiEvent) Enum.valueOf(StylusUiEvent.class, str);
    }

    public static StylusUiEvent[] values() {
        return (StylusUiEvent[]) $VALUES.clone();
    }

    public final int getId() {
        return this._id;
    }
}

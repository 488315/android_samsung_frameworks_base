package com.android.systemui.volume.dialog.ui;

import com.android.internal.logging.UiEventLogger;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class VolumeDialogUiEvent implements UiEventLogger.UiEventEnum {
    public static final /* synthetic */ VolumeDialogUiEvent[] $VALUES;
    public static final VolumeDialogUiEvent RINGER_MODE_NORMAL;
    public static final VolumeDialogUiEvent RINGER_MODE_SILENT;
    public static final VolumeDialogUiEvent RINGER_MODE_VIBRATE;
    public static final VolumeDialogUiEvent VOLUME_DIALOG_DISMISS_SCREEN_OFF;
    public static final VolumeDialogUiEvent VOLUME_DIALOG_DISMISS_SETTINGS;
    public static final VolumeDialogUiEvent VOLUME_DIALOG_DISMISS_STREAM_GONE;
    public static final VolumeDialogUiEvent VOLUME_DIALOG_DISMISS_SYSTEM;
    public static final VolumeDialogUiEvent VOLUME_DIALOG_DISMISS_TIMEOUT;
    public static final VolumeDialogUiEvent VOLUME_DIALOG_DISMISS_TOUCH_OUTSIDE;
    public static final VolumeDialogUiEvent VOLUME_DIALOG_DISMISS_USB_TEMP_ALARM_CHANGED;
    public static final VolumeDialogUiEvent VOLUME_DIALOG_SETTINGS_CLICK;
    public static final VolumeDialogUiEvent VOLUME_DIALOG_SHOW_REMOTE_VOLUME_CHANGED;
    public static final VolumeDialogUiEvent VOLUME_DIALOG_SHOW_USB_TEMP_ALARM_CHANGED;
    public static final VolumeDialogUiEvent VOLUME_DIALOG_SHOW_VOLUME_CHANGED;
    public static final VolumeDialogUiEvent VOLUME_DIALOG_SLIDER_STARTED_TRACKING_TOUCH;
    public static final VolumeDialogUiEvent VOLUME_DIALOG_SLIDER_STOPPED_TRACKING_TOUCH;
    private final int metricId;

    static {
        VolumeDialogUiEvent volumeDialogUiEvent = new VolumeDialogUiEvent("RINGER_MODE_SILENT", 0, 154);
        RINGER_MODE_SILENT = volumeDialogUiEvent;
        VolumeDialogUiEvent volumeDialogUiEvent2 = new VolumeDialogUiEvent("RINGER_MODE_VIBRATE", 1, 155);
        RINGER_MODE_VIBRATE = volumeDialogUiEvent2;
        VolumeDialogUiEvent volumeDialogUiEvent3 = new VolumeDialogUiEvent("RINGER_MODE_NORMAL", 2, 334);
        RINGER_MODE_NORMAL = volumeDialogUiEvent3;
        VolumeDialogUiEvent volumeDialogUiEvent4 = new VolumeDialogUiEvent("VOLUME_DIALOG_SETTINGS_CLICK", 3, 143);
        VOLUME_DIALOG_SETTINGS_CLICK = volumeDialogUiEvent4;
        VolumeDialogUiEvent volumeDialogUiEvent5 = new VolumeDialogUiEvent("VOLUME_DIALOG_SHOW_VOLUME_CHANGED", 4, 128);
        VOLUME_DIALOG_SHOW_VOLUME_CHANGED = volumeDialogUiEvent5;
        VolumeDialogUiEvent volumeDialogUiEvent6 = new VolumeDialogUiEvent("VOLUME_DIALOG_SHOW_REMOTE_VOLUME_CHANGED", 5, 129);
        VOLUME_DIALOG_SHOW_REMOTE_VOLUME_CHANGED = volumeDialogUiEvent6;
        VolumeDialogUiEvent volumeDialogUiEvent7 = new VolumeDialogUiEvent("VOLUME_DIALOG_SHOW_USB_TEMP_ALARM_CHANGED", 6, 130);
        VOLUME_DIALOG_SHOW_USB_TEMP_ALARM_CHANGED = volumeDialogUiEvent7;
        VolumeDialogUiEvent volumeDialogUiEvent8 = new VolumeDialogUiEvent("VOLUME_DIALOG_DISMISS_TOUCH_OUTSIDE", 7, 134);
        VOLUME_DIALOG_DISMISS_TOUCH_OUTSIDE = volumeDialogUiEvent8;
        VolumeDialogUiEvent volumeDialogUiEvent9 = new VolumeDialogUiEvent("VOLUME_DIALOG_DISMISS_SYSTEM", 8, 135);
        VOLUME_DIALOG_DISMISS_SYSTEM = volumeDialogUiEvent9;
        VolumeDialogUiEvent volumeDialogUiEvent10 = new VolumeDialogUiEvent("VOLUME_DIALOG_DISMISS_TIMEOUT", 9, 136);
        VOLUME_DIALOG_DISMISS_TIMEOUT = volumeDialogUiEvent10;
        VolumeDialogUiEvent volumeDialogUiEvent11 = new VolumeDialogUiEvent("VOLUME_DIALOG_DISMISS_SCREEN_OFF", 10, 137);
        VOLUME_DIALOG_DISMISS_SCREEN_OFF = volumeDialogUiEvent11;
        VolumeDialogUiEvent volumeDialogUiEvent12 = new VolumeDialogUiEvent("VOLUME_DIALOG_DISMISS_SETTINGS", 11, 138);
        VOLUME_DIALOG_DISMISS_SETTINGS = volumeDialogUiEvent12;
        VolumeDialogUiEvent volumeDialogUiEvent13 = new VolumeDialogUiEvent("VOLUME_DIALOG_DISMISS_STREAM_GONE", 12, 140);
        VOLUME_DIALOG_DISMISS_STREAM_GONE = volumeDialogUiEvent13;
        VolumeDialogUiEvent volumeDialogUiEvent14 = new VolumeDialogUiEvent("VOLUME_DIALOG_DISMISS_USB_TEMP_ALARM_CHANGED", 13, 142);
        VOLUME_DIALOG_DISMISS_USB_TEMP_ALARM_CHANGED = volumeDialogUiEvent14;
        VolumeDialogUiEvent volumeDialogUiEvent15 = new VolumeDialogUiEvent("VOLUME_DIALOG_SLIDER_STARTED_TRACKING_TOUCH", 14, 1620);
        VOLUME_DIALOG_SLIDER_STARTED_TRACKING_TOUCH = volumeDialogUiEvent15;
        VolumeDialogUiEvent volumeDialogUiEvent16 = new VolumeDialogUiEvent("VOLUME_DIALOG_SLIDER_STOPPED_TRACKING_TOUCH", 15, 1621);
        VOLUME_DIALOG_SLIDER_STOPPED_TRACKING_TOUCH = volumeDialogUiEvent16;
        VolumeDialogUiEvent[] volumeDialogUiEventArr = {volumeDialogUiEvent, volumeDialogUiEvent2, volumeDialogUiEvent3, volumeDialogUiEvent4, volumeDialogUiEvent5, volumeDialogUiEvent6, volumeDialogUiEvent7, volumeDialogUiEvent8, volumeDialogUiEvent9, volumeDialogUiEvent10, volumeDialogUiEvent11, volumeDialogUiEvent12, volumeDialogUiEvent13, volumeDialogUiEvent14, volumeDialogUiEvent15, volumeDialogUiEvent16};
        $VALUES = volumeDialogUiEventArr;
        EnumEntriesKt.enumEntries(volumeDialogUiEventArr);
    }

    private VolumeDialogUiEvent(String str, int i, int i2) {
        this.metricId = i2;
    }

    public static VolumeDialogUiEvent valueOf(String str) {
        return (VolumeDialogUiEvent) Enum.valueOf(VolumeDialogUiEvent.class, str);
    }

    public static VolumeDialogUiEvent[] values() {
        return (VolumeDialogUiEvent[]) $VALUES.clone();
    }

    public final int getId() {
        return this.metricId;
    }
}

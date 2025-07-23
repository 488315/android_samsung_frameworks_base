package com.android.systemui.statusbar.chips.uievents;

import com.android.internal.logging.UiEventLogger;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class StatusBarChipUiEvent implements UiEventLogger.UiEventEnum {
    public static final /* synthetic */ StatusBarChipUiEvent[] $VALUES;
    public static final StatusBarChipUiEvent STATUS_BAR_CHIP_REMOVED;
    public static final StatusBarChipUiEvent STATUS_BAR_CHIP_TAP_TO_SHOW;
    public static final StatusBarChipUiEvent STATUS_BAR_NEW_CHIP_CALL;
    public static final StatusBarChipUiEvent STATUS_BAR_NEW_CHIP_CAST_TO_OTHER_DEVICE;
    public static final StatusBarChipUiEvent STATUS_BAR_NEW_CHIP_NOTIFICATION;
    public static final StatusBarChipUiEvent STATUS_BAR_NEW_CHIP_SCREEN_RECORD;
    public static final StatusBarChipUiEvent STATUS_BAR_NEW_CHIP_SHARE_TO_APP;
    private final int _id;

    static {
        StatusBarChipUiEvent statusBarChipUiEvent = new StatusBarChipUiEvent("STATUS_BAR_NEW_CHIP_CALL", 0, 2211);
        STATUS_BAR_NEW_CHIP_CALL = statusBarChipUiEvent;
        StatusBarChipUiEvent statusBarChipUiEvent2 = new StatusBarChipUiEvent("STATUS_BAR_NEW_CHIP_SCREEN_RECORD", 1, 2212);
        STATUS_BAR_NEW_CHIP_SCREEN_RECORD = statusBarChipUiEvent2;
        StatusBarChipUiEvent statusBarChipUiEvent3 = new StatusBarChipUiEvent("STATUS_BAR_NEW_CHIP_SHARE_TO_APP", 2, 2213);
        STATUS_BAR_NEW_CHIP_SHARE_TO_APP = statusBarChipUiEvent3;
        StatusBarChipUiEvent statusBarChipUiEvent4 = new StatusBarChipUiEvent("STATUS_BAR_NEW_CHIP_CAST_TO_OTHER_DEVICE", 3, 2214);
        STATUS_BAR_NEW_CHIP_CAST_TO_OTHER_DEVICE = statusBarChipUiEvent4;
        StatusBarChipUiEvent statusBarChipUiEvent5 = new StatusBarChipUiEvent("STATUS_BAR_NEW_CHIP_NOTIFICATION", 4, 2215);
        STATUS_BAR_NEW_CHIP_NOTIFICATION = statusBarChipUiEvent5;
        StatusBarChipUiEvent statusBarChipUiEvent6 = new StatusBarChipUiEvent("STATUS_BAR_CHIP_REMOVED", 5, 2216);
        STATUS_BAR_CHIP_REMOVED = statusBarChipUiEvent6;
        StatusBarChipUiEvent statusBarChipUiEvent7 = new StatusBarChipUiEvent("STATUS_BAR_CHIP_TAP_TO_SHOW", 6, 2217);
        STATUS_BAR_CHIP_TAP_TO_SHOW = statusBarChipUiEvent7;
        StatusBarChipUiEvent[] statusBarChipUiEventArr = {statusBarChipUiEvent, statusBarChipUiEvent2, statusBarChipUiEvent3, statusBarChipUiEvent4, statusBarChipUiEvent5, statusBarChipUiEvent6, statusBarChipUiEvent7, new StatusBarChipUiEvent("STATUS_BAR_CHIP_TAP_TO_HIDE", 7, 2218)};
        $VALUES = statusBarChipUiEventArr;
        EnumEntriesKt.enumEntries(statusBarChipUiEventArr);
    }

    private StatusBarChipUiEvent(String str, int i, int i2) {
        this._id = i2;
    }

    public static StatusBarChipUiEvent valueOf(String str) {
        return (StatusBarChipUiEvent) Enum.valueOf(StatusBarChipUiEvent.class, str);
    }

    public static StatusBarChipUiEvent[] values() {
        return (StatusBarChipUiEvent[]) $VALUES.clone();
    }

    public final int getId() {
        return this._id;
    }
}

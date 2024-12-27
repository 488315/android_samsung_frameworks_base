package com.android.systemui.power.shared.model;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
public final class ScreenPowerState {
    public static final /* synthetic */ ScreenPowerState[] $VALUES;
    public static final ScreenPowerState SCREEN_OFF;
    public static final ScreenPowerState SCREEN_ON;
    public static final ScreenPowerState SCREEN_TURNING_OFF;
    public static final ScreenPowerState SCREEN_TURNING_ON;

    static {
        ScreenPowerState screenPowerState = new ScreenPowerState("SCREEN_OFF", 0);
        SCREEN_OFF = screenPowerState;
        ScreenPowerState screenPowerState2 = new ScreenPowerState("SCREEN_TURNING_ON", 1);
        SCREEN_TURNING_ON = screenPowerState2;
        ScreenPowerState screenPowerState3 = new ScreenPowerState("SCREEN_ON", 2);
        SCREEN_ON = screenPowerState3;
        ScreenPowerState screenPowerState4 = new ScreenPowerState("SCREEN_TURNING_OFF", 3);
        SCREEN_TURNING_OFF = screenPowerState4;
        ScreenPowerState[] screenPowerStateArr = {screenPowerState, screenPowerState2, screenPowerState3, screenPowerState4};
        $VALUES = screenPowerStateArr;
        EnumEntriesKt.enumEntries(screenPowerStateArr);
    }

    private ScreenPowerState(String str, int i) {
    }

    public static ScreenPowerState valueOf(String str) {
        return (ScreenPowerState) Enum.valueOf(ScreenPowerState.class, str);
    }

    public static ScreenPowerState[] values() {
        return (ScreenPowerState[]) $VALUES.clone();
    }
}

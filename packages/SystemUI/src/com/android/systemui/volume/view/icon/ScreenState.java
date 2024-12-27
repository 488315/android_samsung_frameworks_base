package com.android.systemui.volume.view.icon;

import kotlin.enums.EnumEntriesKt;

public final class ScreenState {
    public static final /* synthetic */ ScreenState[] $VALUES;
    public static final ScreenState SCREEN_NORMAL;
    public static final ScreenState SCREEN_SUB_DISPLAY;
    public static final ScreenState SCREEN_SUB_LARGE_DISPLAY;

    static {
        ScreenState screenState = new ScreenState("SCREEN_NORMAL", 0);
        SCREEN_NORMAL = screenState;
        ScreenState screenState2 = new ScreenState("SCREEN_SUB_DISPLAY", 1);
        SCREEN_SUB_DISPLAY = screenState2;
        ScreenState screenState3 = new ScreenState("SCREEN_SUB_LARGE_DISPLAY", 2);
        SCREEN_SUB_LARGE_DISPLAY = screenState3;
        ScreenState[] screenStateArr = {screenState, screenState2, screenState3};
        $VALUES = screenStateArr;
        EnumEntriesKt.enumEntries(screenStateArr);
    }

    private ScreenState(String str, int i) {
    }

    public static ScreenState valueOf(String str) {
        return (ScreenState) Enum.valueOf(ScreenState.class, str);
    }

    public static ScreenState[] values() {
        return (ScreenState[]) $VALUES.clone();
    }
}

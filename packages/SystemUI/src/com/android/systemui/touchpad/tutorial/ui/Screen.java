package com.android.systemui.touchpad.tutorial.ui;

import kotlin.enums.EnumEntriesKt;

public final class Screen {
    public static final /* synthetic */ Screen[] $VALUES;
    public static final Screen BACK_GESTURE;
    public static final Screen HOME_GESTURE;
    public static final Screen TUTORIAL_SELECTION;

    static {
        Screen screen = new Screen("TUTORIAL_SELECTION", 0);
        TUTORIAL_SELECTION = screen;
        Screen screen2 = new Screen("BACK_GESTURE", 1);
        BACK_GESTURE = screen2;
        Screen screen3 = new Screen("HOME_GESTURE", 2);
        HOME_GESTURE = screen3;
        Screen[] screenArr = {screen, screen2, screen3};
        $VALUES = screenArr;
        EnumEntriesKt.enumEntries(screenArr);
    }

    private Screen(String str, int i) {
    }

    public static Screen valueOf(String str) {
        return (Screen) Enum.valueOf(Screen.class, str);
    }

    public static Screen[] values() {
        return (Screen[]) $VALUES.clone();
    }
}

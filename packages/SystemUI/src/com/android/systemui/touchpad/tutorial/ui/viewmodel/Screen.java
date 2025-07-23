package com.android.systemui.touchpad.tutorial.ui.viewmodel;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class Screen {
    public static final /* synthetic */ Screen[] $VALUES;
    public static final Screen BACK_GESTURE;
    public static final Screen HOME_GESTURE;
    public static final Screen RECENT_APPS_GESTURE;
    public static final Screen SWITCH_APPS_GESTURE;
    public static final Screen TUTORIAL_SELECTION;

    static {
        Screen screen = new Screen("TUTORIAL_SELECTION", 0);
        TUTORIAL_SELECTION = screen;
        Screen screen2 = new Screen("BACK_GESTURE", 1);
        BACK_GESTURE = screen2;
        Screen screen3 = new Screen("HOME_GESTURE", 2);
        HOME_GESTURE = screen3;
        Screen screen4 = new Screen("RECENT_APPS_GESTURE", 3);
        RECENT_APPS_GESTURE = screen4;
        Screen screen5 = new Screen("SWITCH_APPS_GESTURE", 4);
        SWITCH_APPS_GESTURE = screen5;
        Screen[] screenArr = {screen, screen2, screen3, screen4, screen5};
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

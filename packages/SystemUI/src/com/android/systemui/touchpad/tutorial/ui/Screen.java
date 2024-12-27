package com.android.systemui.touchpad.tutorial.ui;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
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

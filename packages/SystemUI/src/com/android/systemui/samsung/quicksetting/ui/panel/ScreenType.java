package com.android.systemui.samsung.quicksetting.ui.panel;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ScreenType {
    public static final /* synthetic */ ScreenType[] $VALUES;
    public static final ScreenType LANDSCAPE;
    public static final ScreenType PORTRAIT;
    public static final ScreenType SQUARE = null;

    static {
        ScreenType screenType = new ScreenType("PORTRAIT", 0);
        PORTRAIT = screenType;
        ScreenType screenType2 = new ScreenType("SQUARE", 1);
        ScreenType screenType3 = new ScreenType("LANDSCAPE", 2);
        LANDSCAPE = screenType3;
        ScreenType[] screenTypeArr = {screenType, screenType2, screenType3};
        $VALUES = screenTypeArr;
        EnumEntriesKt.enumEntries(screenTypeArr);
    }

    private ScreenType(String str, int i) {
    }

    public static ScreenType valueOf(String str) {
        return (ScreenType) Enum.valueOf(ScreenType.class, str);
    }

    public static ScreenType[] values() {
        return (ScreenType[]) $VALUES.clone();
    }
}

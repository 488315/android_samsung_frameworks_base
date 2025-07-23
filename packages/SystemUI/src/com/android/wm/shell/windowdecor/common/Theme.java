package com.android.wm.shell.windowdecor.common;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class Theme {
    public static final /* synthetic */ Theme[] $VALUES;
    public static final Theme DARK;
    public static final Theme LIGHT;

    static {
        Theme theme = new Theme("LIGHT", 0);
        LIGHT = theme;
        Theme theme2 = new Theme("DARK", 1);
        DARK = theme2;
        Theme[] themeArr = {theme, theme2};
        $VALUES = themeArr;
        EnumEntriesKt.enumEntries(themeArr);
    }

    private Theme(String str, int i) {
    }

    public static Theme valueOf(String str) {
        return (Theme) Enum.valueOf(Theme.class, str);
    }

    public static Theme[] values() {
        return (Theme[]) $VALUES.clone();
    }
}

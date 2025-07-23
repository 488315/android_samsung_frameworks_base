package com.samsung.sesl.compose.component.tokens;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SeslAppBarColorSchemeKeyTokens implements SeslColorSchemeKeyTokens {
    public static final /* synthetic */ SeslAppBarColorSchemeKeyTokens[] $VALUES;
    public static final SeslAppBarColorSchemeKeyTokens TopAppBarBackgroundColor;
    public static final SeslAppBarColorSchemeKeyTokens TopAppBarMenuTextColor;
    public static final SeslAppBarColorSchemeKeyTokens TopAppBarSubTitleTextColor;
    public static final SeslAppBarColorSchemeKeyTokens TopAppBarTitleTextColor;
    public static final SeslAppBarColorSchemeKeyTokens TopExtendedAppBarSubTitleTextColor;

    static {
        SeslAppBarColorSchemeKeyTokens seslAppBarColorSchemeKeyTokens = new SeslAppBarColorSchemeKeyTokens("TopAppBarBackgroundColor", 0);
        TopAppBarBackgroundColor = seslAppBarColorSchemeKeyTokens;
        SeslAppBarColorSchemeKeyTokens seslAppBarColorSchemeKeyTokens2 = new SeslAppBarColorSchemeKeyTokens("TopAppBarTitleTextColor", 1);
        TopAppBarTitleTextColor = seslAppBarColorSchemeKeyTokens2;
        SeslAppBarColorSchemeKeyTokens seslAppBarColorSchemeKeyTokens3 = new SeslAppBarColorSchemeKeyTokens("TopAppBarSubTitleTextColor", 2);
        TopAppBarSubTitleTextColor = seslAppBarColorSchemeKeyTokens3;
        SeslAppBarColorSchemeKeyTokens seslAppBarColorSchemeKeyTokens4 = new SeslAppBarColorSchemeKeyTokens("TopAppBarMenuTextColor", 3);
        TopAppBarMenuTextColor = seslAppBarColorSchemeKeyTokens4;
        SeslAppBarColorSchemeKeyTokens seslAppBarColorSchemeKeyTokens5 = new SeslAppBarColorSchemeKeyTokens("TopExtendedAppBarSubTitleTextColor", 4);
        TopExtendedAppBarSubTitleTextColor = seslAppBarColorSchemeKeyTokens5;
        SeslAppBarColorSchemeKeyTokens[] seslAppBarColorSchemeKeyTokensArr = {seslAppBarColorSchemeKeyTokens, seslAppBarColorSchemeKeyTokens2, seslAppBarColorSchemeKeyTokens3, seslAppBarColorSchemeKeyTokens4, seslAppBarColorSchemeKeyTokens5};
        $VALUES = seslAppBarColorSchemeKeyTokensArr;
        EnumEntriesKt.enumEntries(seslAppBarColorSchemeKeyTokensArr);
    }

    private SeslAppBarColorSchemeKeyTokens(String str, int i) {
    }

    public static SeslAppBarColorSchemeKeyTokens valueOf(String str) {
        return (SeslAppBarColorSchemeKeyTokens) Enum.valueOf(SeslAppBarColorSchemeKeyTokens.class, str);
    }

    public static SeslAppBarColorSchemeKeyTokens[] values() {
        return (SeslAppBarColorSchemeKeyTokens[]) $VALUES.clone();
    }
}

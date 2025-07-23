package com.samsung.sesl.compose.component.tokens;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SeslCommonColorSchemeKeyTokens implements SeslColorSchemeKeyTokens {
    public static final /* synthetic */ SeslCommonColorSchemeKeyTokens[] $VALUES;
    public static final SeslCommonColorSchemeKeyTokens MainTextColor;
    public static final SeslCommonColorSchemeKeyTokens PointTextColor;
    public static final SeslCommonColorSchemeKeyTokens Primary;
    public static final SeslCommonColorSchemeKeyTokens Ripple;
    public static final SeslCommonColorSchemeKeyTokens RoundedCorner;
    public static final SeslCommonColorSchemeKeyTokens SubTextColor;
    public static final SeslCommonColorSchemeKeyTokens WindowBackground;

    static {
        SeslCommonColorSchemeKeyTokens seslCommonColorSchemeKeyTokens = new SeslCommonColorSchemeKeyTokens("Primary", 0);
        Primary = seslCommonColorSchemeKeyTokens;
        SeslCommonColorSchemeKeyTokens seslCommonColorSchemeKeyTokens2 = new SeslCommonColorSchemeKeyTokens("WindowBackground", 1);
        WindowBackground = seslCommonColorSchemeKeyTokens2;
        SeslCommonColorSchemeKeyTokens seslCommonColorSchemeKeyTokens3 = new SeslCommonColorSchemeKeyTokens("Ripple", 2);
        Ripple = seslCommonColorSchemeKeyTokens3;
        SeslCommonColorSchemeKeyTokens seslCommonColorSchemeKeyTokens4 = new SeslCommonColorSchemeKeyTokens("RoundedCorner", 3);
        RoundedCorner = seslCommonColorSchemeKeyTokens4;
        SeslCommonColorSchemeKeyTokens seslCommonColorSchemeKeyTokens5 = new SeslCommonColorSchemeKeyTokens("MainTextColor", 4);
        MainTextColor = seslCommonColorSchemeKeyTokens5;
        SeslCommonColorSchemeKeyTokens seslCommonColorSchemeKeyTokens6 = new SeslCommonColorSchemeKeyTokens("SubTextColor", 5);
        SubTextColor = seslCommonColorSchemeKeyTokens6;
        SeslCommonColorSchemeKeyTokens seslCommonColorSchemeKeyTokens7 = new SeslCommonColorSchemeKeyTokens("PointTextColor", 6);
        PointTextColor = seslCommonColorSchemeKeyTokens7;
        SeslCommonColorSchemeKeyTokens[] seslCommonColorSchemeKeyTokensArr = {seslCommonColorSchemeKeyTokens, seslCommonColorSchemeKeyTokens2, seslCommonColorSchemeKeyTokens3, seslCommonColorSchemeKeyTokens4, seslCommonColorSchemeKeyTokens5, seslCommonColorSchemeKeyTokens6, seslCommonColorSchemeKeyTokens7};
        $VALUES = seslCommonColorSchemeKeyTokensArr;
        EnumEntriesKt.enumEntries(seslCommonColorSchemeKeyTokensArr);
    }

    private SeslCommonColorSchemeKeyTokens(String str, int i) {
    }

    public static SeslCommonColorSchemeKeyTokens valueOf(String str) {
        return (SeslCommonColorSchemeKeyTokens) Enum.valueOf(SeslCommonColorSchemeKeyTokens.class, str);
    }

    public static SeslCommonColorSchemeKeyTokens[] values() {
        return (SeslCommonColorSchemeKeyTokens[]) $VALUES.clone();
    }
}

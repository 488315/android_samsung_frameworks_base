package com.samsung.sesl.compose.component.tokens;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SeslTabColorSchemeKeyTokens implements SeslColorSchemeKeyTokens {
    public static final /* synthetic */ SeslTabColorSchemeKeyTokens[] $VALUES;
    public static final SeslTabColorSchemeKeyTokens SelectedTextColor;
    public static final SeslTabColorSchemeKeyTokens SubTabIndicatorBackgroundColor;
    public static final SeslTabColorSchemeKeyTokens SubTabSelectedTextColor;
    public static final SeslTabColorSchemeKeyTokens SubTabTextColor;
    public static final SeslTabColorSchemeKeyTokens SubTabTwoLineSelectedTextColor;
    public static final SeslTabColorSchemeKeyTokens SubTabTwoLineSubSelectedTextColor;
    public static final SeslTabColorSchemeKeyTokens SubTabTwoLineSubTextColor;
    public static final SeslTabColorSchemeKeyTokens SubTabTwoLineTextColor;
    public static final SeslTabColorSchemeKeyTokens TextColor;

    static {
        SeslTabColorSchemeKeyTokens seslTabColorSchemeKeyTokens = new SeslTabColorSchemeKeyTokens("TextColor", 0);
        TextColor = seslTabColorSchemeKeyTokens;
        SeslTabColorSchemeKeyTokens seslTabColorSchemeKeyTokens2 = new SeslTabColorSchemeKeyTokens("SelectedTextColor", 1);
        SelectedTextColor = seslTabColorSchemeKeyTokens2;
        SeslTabColorSchemeKeyTokens seslTabColorSchemeKeyTokens3 = new SeslTabColorSchemeKeyTokens("SubTabTextColor", 2);
        SubTabTextColor = seslTabColorSchemeKeyTokens3;
        SeslTabColorSchemeKeyTokens seslTabColorSchemeKeyTokens4 = new SeslTabColorSchemeKeyTokens("SubTabSelectedTextColor", 3);
        SubTabSelectedTextColor = seslTabColorSchemeKeyTokens4;
        SeslTabColorSchemeKeyTokens seslTabColorSchemeKeyTokens5 = new SeslTabColorSchemeKeyTokens("SubTabTwoLineTextColor", 4);
        SubTabTwoLineTextColor = seslTabColorSchemeKeyTokens5;
        SeslTabColorSchemeKeyTokens seslTabColorSchemeKeyTokens6 = new SeslTabColorSchemeKeyTokens("SubTabTwoLineSelectedTextColor", 5);
        SubTabTwoLineSelectedTextColor = seslTabColorSchemeKeyTokens6;
        SeslTabColorSchemeKeyTokens seslTabColorSchemeKeyTokens7 = new SeslTabColorSchemeKeyTokens("SubTabTwoLineSubTextColor", 6);
        SubTabTwoLineSubTextColor = seslTabColorSchemeKeyTokens7;
        SeslTabColorSchemeKeyTokens seslTabColorSchemeKeyTokens8 = new SeslTabColorSchemeKeyTokens("SubTabTwoLineSubSelectedTextColor", 7);
        SubTabTwoLineSubSelectedTextColor = seslTabColorSchemeKeyTokens8;
        SeslTabColorSchemeKeyTokens seslTabColorSchemeKeyTokens9 = new SeslTabColorSchemeKeyTokens("SubTabIndicatorBackgroundColor", 8);
        SubTabIndicatorBackgroundColor = seslTabColorSchemeKeyTokens9;
        SeslTabColorSchemeKeyTokens[] seslTabColorSchemeKeyTokensArr = {seslTabColorSchemeKeyTokens, seslTabColorSchemeKeyTokens2, seslTabColorSchemeKeyTokens3, seslTabColorSchemeKeyTokens4, seslTabColorSchemeKeyTokens5, seslTabColorSchemeKeyTokens6, seslTabColorSchemeKeyTokens7, seslTabColorSchemeKeyTokens8, seslTabColorSchemeKeyTokens9};
        $VALUES = seslTabColorSchemeKeyTokensArr;
        EnumEntriesKt.enumEntries(seslTabColorSchemeKeyTokensArr);
    }

    private SeslTabColorSchemeKeyTokens(String str, int i) {
    }

    public static SeslTabColorSchemeKeyTokens valueOf(String str) {
        return (SeslTabColorSchemeKeyTokens) Enum.valueOf(SeslTabColorSchemeKeyTokens.class, str);
    }

    public static SeslTabColorSchemeKeyTokens[] values() {
        return (SeslTabColorSchemeKeyTokens[]) $VALUES.clone();
    }
}

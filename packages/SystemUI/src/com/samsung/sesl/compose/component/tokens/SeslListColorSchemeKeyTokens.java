package com.samsung.sesl.compose.component.tokens;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SeslListColorSchemeKeyTokens implements SeslColorSchemeKeyTokens {
    public static final /* synthetic */ SeslListColorSchemeKeyTokens[] $VALUES;
    public static final SeslListColorSchemeKeyTokens ScrollbarThumbActivate;
    public static final SeslListColorSchemeKeyTokens ScrollbarThumbInActivate;

    static {
        SeslListColorSchemeKeyTokens seslListColorSchemeKeyTokens = new SeslListColorSchemeKeyTokens("ScrollbarThumbActivate", 0);
        ScrollbarThumbActivate = seslListColorSchemeKeyTokens;
        SeslListColorSchemeKeyTokens seslListColorSchemeKeyTokens2 = new SeslListColorSchemeKeyTokens("ScrollbarThumbInActivate", 1);
        ScrollbarThumbInActivate = seslListColorSchemeKeyTokens2;
        SeslListColorSchemeKeyTokens[] seslListColorSchemeKeyTokensArr = {seslListColorSchemeKeyTokens, seslListColorSchemeKeyTokens2};
        $VALUES = seslListColorSchemeKeyTokensArr;
        EnumEntriesKt.enumEntries(seslListColorSchemeKeyTokensArr);
    }

    private SeslListColorSchemeKeyTokens(String str, int i) {
    }

    public static SeslListColorSchemeKeyTokens valueOf(String str) {
        return (SeslListColorSchemeKeyTokens) Enum.valueOf(SeslListColorSchemeKeyTokens.class, str);
    }

    public static SeslListColorSchemeKeyTokens[] values() {
        return (SeslListColorSchemeKeyTokens[]) $VALUES.clone();
    }
}

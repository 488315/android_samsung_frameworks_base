package com.samsung.sesl.compose.component.tokens;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SeslDividerColorSchemeKeyTokens implements SeslColorSchemeKeyTokens {
    public static final /* synthetic */ SeslDividerColorSchemeKeyTokens[] $VALUES;
    public static final SeslDividerColorSchemeKeyTokens HorizontalDividerColor;
    public static final SeslDividerColorSchemeKeyTokens VerticalDividerColor;

    static {
        SeslDividerColorSchemeKeyTokens seslDividerColorSchemeKeyTokens = new SeslDividerColorSchemeKeyTokens("HorizontalDividerColor", 0);
        HorizontalDividerColor = seslDividerColorSchemeKeyTokens;
        SeslDividerColorSchemeKeyTokens seslDividerColorSchemeKeyTokens2 = new SeslDividerColorSchemeKeyTokens("VerticalDividerColor", 1);
        VerticalDividerColor = seslDividerColorSchemeKeyTokens2;
        SeslDividerColorSchemeKeyTokens[] seslDividerColorSchemeKeyTokensArr = {seslDividerColorSchemeKeyTokens, seslDividerColorSchemeKeyTokens2};
        $VALUES = seslDividerColorSchemeKeyTokensArr;
        EnumEntriesKt.enumEntries(seslDividerColorSchemeKeyTokensArr);
    }

    private SeslDividerColorSchemeKeyTokens(String str, int i) {
    }

    public static SeslDividerColorSchemeKeyTokens valueOf(String str) {
        return (SeslDividerColorSchemeKeyTokens) Enum.valueOf(SeslDividerColorSchemeKeyTokens.class, str);
    }

    public static SeslDividerColorSchemeKeyTokens[] values() {
        return (SeslDividerColorSchemeKeyTokens[]) $VALUES.clone();
    }
}

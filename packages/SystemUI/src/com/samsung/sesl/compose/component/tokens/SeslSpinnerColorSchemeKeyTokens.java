package com.samsung.sesl.compose.component.tokens;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SeslSpinnerColorSchemeKeyTokens implements SeslColorSchemeKeyTokens {
    public static final /* synthetic */ SeslSpinnerColorSchemeKeyTokens[] $VALUES;
    public static final SeslSpinnerColorSchemeKeyTokens IconColorDefault;
    public static final SeslSpinnerColorSchemeKeyTokens ItemTextColorNormal;

    static {
        SeslSpinnerColorSchemeKeyTokens seslSpinnerColorSchemeKeyTokens = new SeslSpinnerColorSchemeKeyTokens("ItemTextColorNormal", 0);
        ItemTextColorNormal = seslSpinnerColorSchemeKeyTokens;
        SeslSpinnerColorSchemeKeyTokens seslSpinnerColorSchemeKeyTokens2 = new SeslSpinnerColorSchemeKeyTokens("IconColorDefault", 1);
        IconColorDefault = seslSpinnerColorSchemeKeyTokens2;
        SeslSpinnerColorSchemeKeyTokens[] seslSpinnerColorSchemeKeyTokensArr = {seslSpinnerColorSchemeKeyTokens, seslSpinnerColorSchemeKeyTokens2};
        $VALUES = seslSpinnerColorSchemeKeyTokensArr;
        EnumEntriesKt.enumEntries(seslSpinnerColorSchemeKeyTokensArr);
    }

    private SeslSpinnerColorSchemeKeyTokens(String str, int i) {
    }

    public static SeslSpinnerColorSchemeKeyTokens valueOf(String str) {
        return (SeslSpinnerColorSchemeKeyTokens) Enum.valueOf(SeslSpinnerColorSchemeKeyTokens.class, str);
    }

    public static SeslSpinnerColorSchemeKeyTokens[] values() {
        return (SeslSpinnerColorSchemeKeyTokens[]) $VALUES.clone();
    }
}

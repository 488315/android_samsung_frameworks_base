package com.samsung.sesl.compose.component.tokens;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SeslPopupColorSchemeKeyTokens implements SeslColorSchemeKeyTokens {
    public static final /* synthetic */ SeslPopupColorSchemeKeyTokens[] $VALUES;
    public static final SeslPopupColorSchemeKeyTokens BackgroundColor;

    static {
        SeslPopupColorSchemeKeyTokens seslPopupColorSchemeKeyTokens = new SeslPopupColorSchemeKeyTokens("BackgroundColor", 0);
        BackgroundColor = seslPopupColorSchemeKeyTokens;
        SeslPopupColorSchemeKeyTokens[] seslPopupColorSchemeKeyTokensArr = {seslPopupColorSchemeKeyTokens};
        $VALUES = seslPopupColorSchemeKeyTokensArr;
        EnumEntriesKt.enumEntries(seslPopupColorSchemeKeyTokensArr);
    }

    private SeslPopupColorSchemeKeyTokens(String str, int i) {
    }

    public static SeslPopupColorSchemeKeyTokens valueOf(String str) {
        return (SeslPopupColorSchemeKeyTokens) Enum.valueOf(SeslPopupColorSchemeKeyTokens.class, str);
    }

    public static SeslPopupColorSchemeKeyTokens[] values() {
        return (SeslPopupColorSchemeKeyTokens[]) $VALUES.clone();
    }
}

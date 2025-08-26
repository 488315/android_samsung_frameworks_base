package com.samsung.sesl.compose.component.tokens;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes4.dex */
public final class SeslPopupColorSchemeKeyTokens implements SeslColorSchemeKeyTokens {
    public static final /* synthetic */ SeslPopupColorSchemeKeyTokens[] $VALUES;
    public static final SeslPopupColorSchemeKeyTokens BackgroundColor;
    public static final SeslPopupColorSchemeKeyTokens BorderColor;

    static {
        SeslPopupColorSchemeKeyTokens seslPopupColorSchemeKeyTokens = new SeslPopupColorSchemeKeyTokens("BackgroundColor", 0);
        BackgroundColor = seslPopupColorSchemeKeyTokens;
        SeslPopupColorSchemeKeyTokens seslPopupColorSchemeKeyTokens2 = new SeslPopupColorSchemeKeyTokens("BorderColor", 1);
        BorderColor = seslPopupColorSchemeKeyTokens2;
        SeslPopupColorSchemeKeyTokens[] seslPopupColorSchemeKeyTokensArr = {seslPopupColorSchemeKeyTokens, seslPopupColorSchemeKeyTokens2};
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

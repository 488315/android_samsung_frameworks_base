package com.samsung.sesl.compose.component.tokens;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SeslAlertDialogColorSchemeKeyTokens implements SeslColorSchemeKeyTokens {
    public static final /* synthetic */ SeslAlertDialogColorSchemeKeyTokens[] $VALUES;
    public static final SeslAlertDialogColorSchemeKeyTokens ButtonTextColor;
    public static final SeslAlertDialogColorSchemeKeyTokens MessageTextColor;
    public static final SeslAlertDialogColorSchemeKeyTokens TitleTextColor;

    static {
        SeslAlertDialogColorSchemeKeyTokens seslAlertDialogColorSchemeKeyTokens = new SeslAlertDialogColorSchemeKeyTokens("TitleTextColor", 0);
        TitleTextColor = seslAlertDialogColorSchemeKeyTokens;
        SeslAlertDialogColorSchemeKeyTokens seslAlertDialogColorSchemeKeyTokens2 = new SeslAlertDialogColorSchemeKeyTokens("MessageTextColor", 1);
        MessageTextColor = seslAlertDialogColorSchemeKeyTokens2;
        SeslAlertDialogColorSchemeKeyTokens seslAlertDialogColorSchemeKeyTokens3 = new SeslAlertDialogColorSchemeKeyTokens("ButtonTextColor", 2);
        ButtonTextColor = seslAlertDialogColorSchemeKeyTokens3;
        SeslAlertDialogColorSchemeKeyTokens[] seslAlertDialogColorSchemeKeyTokensArr = {seslAlertDialogColorSchemeKeyTokens, seslAlertDialogColorSchemeKeyTokens2, seslAlertDialogColorSchemeKeyTokens3};
        $VALUES = seslAlertDialogColorSchemeKeyTokensArr;
        EnumEntriesKt.enumEntries(seslAlertDialogColorSchemeKeyTokensArr);
    }

    private SeslAlertDialogColorSchemeKeyTokens(String str, int i) {
    }

    public static SeslAlertDialogColorSchemeKeyTokens valueOf(String str) {
        return (SeslAlertDialogColorSchemeKeyTokens) Enum.valueOf(SeslAlertDialogColorSchemeKeyTokens.class, str);
    }

    public static SeslAlertDialogColorSchemeKeyTokens[] values() {
        return (SeslAlertDialogColorSchemeKeyTokens[]) $VALUES.clone();
    }
}

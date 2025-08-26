package com.samsung.sesl.compose.component.tokens;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes4.dex */
public final class SeslAlertDialogColorSchemeKeyTokens implements SeslColorSchemeKeyTokens {
    public static final /* synthetic */ SeslAlertDialogColorSchemeKeyTokens[] $VALUES;
    public static final SeslAlertDialogColorSchemeKeyTokens ButtonTextColor;
    public static final SeslAlertDialogColorSchemeKeyTokens ListTextColor;
    public static final SeslAlertDialogColorSchemeKeyTokens MessageTextColor;
    public static final SeslAlertDialogColorSchemeKeyTokens TitleTextColor;

    static {
        SeslAlertDialogColorSchemeKeyTokens seslAlertDialogColorSchemeKeyTokens = new SeslAlertDialogColorSchemeKeyTokens("TitleTextColor", 0);
        TitleTextColor = seslAlertDialogColorSchemeKeyTokens;
        SeslAlertDialogColorSchemeKeyTokens seslAlertDialogColorSchemeKeyTokens2 = new SeslAlertDialogColorSchemeKeyTokens("MessageTextColor", 1);
        MessageTextColor = seslAlertDialogColorSchemeKeyTokens2;
        SeslAlertDialogColorSchemeKeyTokens seslAlertDialogColorSchemeKeyTokens3 = new SeslAlertDialogColorSchemeKeyTokens("ButtonTextColor", 2);
        ButtonTextColor = seslAlertDialogColorSchemeKeyTokens3;
        SeslAlertDialogColorSchemeKeyTokens seslAlertDialogColorSchemeKeyTokens4 = new SeslAlertDialogColorSchemeKeyTokens("ListTextColor", 3);
        ListTextColor = seslAlertDialogColorSchemeKeyTokens4;
        SeslAlertDialogColorSchemeKeyTokens[] seslAlertDialogColorSchemeKeyTokensArr = {seslAlertDialogColorSchemeKeyTokens, seslAlertDialogColorSchemeKeyTokens2, seslAlertDialogColorSchemeKeyTokens3, seslAlertDialogColorSchemeKeyTokens4};
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

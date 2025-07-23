package com.samsung.sesl.compose.component.tokens;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SeslPopupDrawableSchemeKeyTokens {
    public static final /* synthetic */ SeslPopupDrawableSchemeKeyTokens[] $VALUES;
    public static final SeslPopupDrawableSchemeKeyTokens MenuBackground;

    static {
        SeslPopupDrawableSchemeKeyTokens seslPopupDrawableSchemeKeyTokens = new SeslPopupDrawableSchemeKeyTokens("MenuBackground", 0);
        MenuBackground = seslPopupDrawableSchemeKeyTokens;
        SeslPopupDrawableSchemeKeyTokens[] seslPopupDrawableSchemeKeyTokensArr = {seslPopupDrawableSchemeKeyTokens};
        $VALUES = seslPopupDrawableSchemeKeyTokensArr;
        EnumEntriesKt.enumEntries(seslPopupDrawableSchemeKeyTokensArr);
    }

    private SeslPopupDrawableSchemeKeyTokens(String str, int i) {
    }

    public static SeslPopupDrawableSchemeKeyTokens valueOf(String str) {
        return (SeslPopupDrawableSchemeKeyTokens) Enum.valueOf(SeslPopupDrawableSchemeKeyTokens.class, str);
    }

    public static SeslPopupDrawableSchemeKeyTokens[] values() {
        return (SeslPopupDrawableSchemeKeyTokens[]) $VALUES.clone();
    }
}

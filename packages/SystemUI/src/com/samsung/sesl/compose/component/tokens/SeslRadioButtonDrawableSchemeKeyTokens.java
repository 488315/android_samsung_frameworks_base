package com.samsung.sesl.compose.component.tokens;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes4.dex */
public final class SeslRadioButtonDrawableSchemeKeyTokens implements SeslDrawableSchemeKeyTokens {
    public static final /* synthetic */ SeslRadioButtonDrawableSchemeKeyTokens[] $VALUES;
    public static final SeslRadioButtonDrawableSchemeKeyTokens DisabledOff;
    public static final SeslRadioButtonDrawableSchemeKeyTokens DisabledOn;
    public static final SeslRadioButtonDrawableSchemeKeyTokens Selected;
    public static final SeslRadioButtonDrawableSchemeKeyTokens Unselected;

    static {
        SeslRadioButtonDrawableSchemeKeyTokens seslRadioButtonDrawableSchemeKeyTokens = new SeslRadioButtonDrawableSchemeKeyTokens("Selected", 0);
        Selected = seslRadioButtonDrawableSchemeKeyTokens;
        SeslRadioButtonDrawableSchemeKeyTokens seslRadioButtonDrawableSchemeKeyTokens2 = new SeslRadioButtonDrawableSchemeKeyTokens("Unselected", 1);
        Unselected = seslRadioButtonDrawableSchemeKeyTokens2;
        SeslRadioButtonDrawableSchemeKeyTokens seslRadioButtonDrawableSchemeKeyTokens3 = new SeslRadioButtonDrawableSchemeKeyTokens("DisabledOn", 2);
        DisabledOn = seslRadioButtonDrawableSchemeKeyTokens3;
        SeslRadioButtonDrawableSchemeKeyTokens seslRadioButtonDrawableSchemeKeyTokens4 = new SeslRadioButtonDrawableSchemeKeyTokens("DisabledOff", 3);
        DisabledOff = seslRadioButtonDrawableSchemeKeyTokens4;
        SeslRadioButtonDrawableSchemeKeyTokens[] seslRadioButtonDrawableSchemeKeyTokensArr = {seslRadioButtonDrawableSchemeKeyTokens, seslRadioButtonDrawableSchemeKeyTokens2, seslRadioButtonDrawableSchemeKeyTokens3, seslRadioButtonDrawableSchemeKeyTokens4};
        $VALUES = seslRadioButtonDrawableSchemeKeyTokensArr;
        EnumEntriesKt.enumEntries(seslRadioButtonDrawableSchemeKeyTokensArr);
    }

    private SeslRadioButtonDrawableSchemeKeyTokens(String str, int i) {
    }

    public static SeslRadioButtonDrawableSchemeKeyTokens valueOf(String str) {
        return (SeslRadioButtonDrawableSchemeKeyTokens) Enum.valueOf(SeslRadioButtonDrawableSchemeKeyTokens.class, str);
    }

    public static SeslRadioButtonDrawableSchemeKeyTokens[] values() {
        return (SeslRadioButtonDrawableSchemeKeyTokens[]) $VALUES.clone();
    }
}

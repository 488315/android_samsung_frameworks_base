package com.samsung.sesl.compose.component.tokens;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SeslCheckboxDrawableSchemeKeyTokens {
    public static final /* synthetic */ SeslCheckboxDrawableSchemeKeyTokens[] $VALUES;
    public static final SeslCheckboxDrawableSchemeKeyTokens CheckboxDisabledOff;
    public static final SeslCheckboxDrawableSchemeKeyTokens CheckboxDisabledOn;
    public static final SeslCheckboxDrawableSchemeKeyTokens CheckboxSelected;
    public static final SeslCheckboxDrawableSchemeKeyTokens CheckboxUnselected;

    static {
        SeslCheckboxDrawableSchemeKeyTokens seslCheckboxDrawableSchemeKeyTokens = new SeslCheckboxDrawableSchemeKeyTokens("CheckboxSelected", 0);
        CheckboxSelected = seslCheckboxDrawableSchemeKeyTokens;
        SeslCheckboxDrawableSchemeKeyTokens seslCheckboxDrawableSchemeKeyTokens2 = new SeslCheckboxDrawableSchemeKeyTokens("CheckboxUnselected", 1);
        CheckboxUnselected = seslCheckboxDrawableSchemeKeyTokens2;
        SeslCheckboxDrawableSchemeKeyTokens seslCheckboxDrawableSchemeKeyTokens3 = new SeslCheckboxDrawableSchemeKeyTokens("CheckboxDisabledOn", 2);
        CheckboxDisabledOn = seslCheckboxDrawableSchemeKeyTokens3;
        SeslCheckboxDrawableSchemeKeyTokens seslCheckboxDrawableSchemeKeyTokens4 = new SeslCheckboxDrawableSchemeKeyTokens("CheckboxDisabledOff", 3);
        CheckboxDisabledOff = seslCheckboxDrawableSchemeKeyTokens4;
        SeslCheckboxDrawableSchemeKeyTokens[] seslCheckboxDrawableSchemeKeyTokensArr = {seslCheckboxDrawableSchemeKeyTokens, seslCheckboxDrawableSchemeKeyTokens2, seslCheckboxDrawableSchemeKeyTokens3, seslCheckboxDrawableSchemeKeyTokens4};
        $VALUES = seslCheckboxDrawableSchemeKeyTokensArr;
        EnumEntriesKt.enumEntries(seslCheckboxDrawableSchemeKeyTokensArr);
    }

    private SeslCheckboxDrawableSchemeKeyTokens(String str, int i) {
    }

    public static SeslCheckboxDrawableSchemeKeyTokens valueOf(String str) {
        return (SeslCheckboxDrawableSchemeKeyTokens) Enum.valueOf(SeslCheckboxDrawableSchemeKeyTokens.class, str);
    }

    public static SeslCheckboxDrawableSchemeKeyTokens[] values() {
        return (SeslCheckboxDrawableSchemeKeyTokens[]) $VALUES.clone();
    }
}

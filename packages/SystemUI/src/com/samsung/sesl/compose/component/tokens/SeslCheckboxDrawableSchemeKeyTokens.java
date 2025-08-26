package com.samsung.sesl.compose.component.tokens;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes4.dex */
public final class SeslCheckboxDrawableSchemeKeyTokens implements SeslDrawableSchemeKeyTokens {
    public static final /* synthetic */ SeslCheckboxDrawableSchemeKeyTokens[] $VALUES;
    public static final SeslCheckboxDrawableSchemeKeyTokens DisabledOff;
    public static final SeslCheckboxDrawableSchemeKeyTokens DisabledOn;
    public static final SeslCheckboxDrawableSchemeKeyTokens Selected;
    public static final SeslCheckboxDrawableSchemeKeyTokens Unselected;

    static {
        SeslCheckboxDrawableSchemeKeyTokens seslCheckboxDrawableSchemeKeyTokens = new SeslCheckboxDrawableSchemeKeyTokens("Selected", 0);
        Selected = seslCheckboxDrawableSchemeKeyTokens;
        SeslCheckboxDrawableSchemeKeyTokens seslCheckboxDrawableSchemeKeyTokens2 = new SeslCheckboxDrawableSchemeKeyTokens("Unselected", 1);
        Unselected = seslCheckboxDrawableSchemeKeyTokens2;
        SeslCheckboxDrawableSchemeKeyTokens seslCheckboxDrawableSchemeKeyTokens3 = new SeslCheckboxDrawableSchemeKeyTokens("DisabledOn", 2);
        DisabledOn = seslCheckboxDrawableSchemeKeyTokens3;
        SeslCheckboxDrawableSchemeKeyTokens seslCheckboxDrawableSchemeKeyTokens4 = new SeslCheckboxDrawableSchemeKeyTokens("DisabledOff", 3);
        DisabledOff = seslCheckboxDrawableSchemeKeyTokens4;
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

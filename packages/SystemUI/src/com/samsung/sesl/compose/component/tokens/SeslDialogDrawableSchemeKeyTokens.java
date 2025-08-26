package com.samsung.sesl.compose.component.tokens;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes4.dex */
public final class SeslDialogDrawableSchemeKeyTokens implements SeslDrawableSchemeKeyTokens {
    public static final /* synthetic */ SeslDialogDrawableSchemeKeyTokens[] $VALUES;
    public static final SeslDialogDrawableSchemeKeyTokens Background;

    static {
        SeslDialogDrawableSchemeKeyTokens seslDialogDrawableSchemeKeyTokens = new SeslDialogDrawableSchemeKeyTokens("Background", 0);
        Background = seslDialogDrawableSchemeKeyTokens;
        SeslDialogDrawableSchemeKeyTokens[] seslDialogDrawableSchemeKeyTokensArr = {seslDialogDrawableSchemeKeyTokens};
        $VALUES = seslDialogDrawableSchemeKeyTokensArr;
        EnumEntriesKt.enumEntries(seslDialogDrawableSchemeKeyTokensArr);
    }

    private SeslDialogDrawableSchemeKeyTokens(String str, int i) {
    }

    public static SeslDialogDrawableSchemeKeyTokens valueOf(String str) {
        return (SeslDialogDrawableSchemeKeyTokens) Enum.valueOf(SeslDialogDrawableSchemeKeyTokens.class, str);
    }

    public static SeslDialogDrawableSchemeKeyTokens[] values() {
        return (SeslDialogDrawableSchemeKeyTokens[]) $VALUES.clone();
    }
}

package com.samsung.sesl.compose.component.tokens;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes4.dex */
public final class SeslAppBarDimensionSchemeKeyTokens {
    public static final /* synthetic */ SeslAppBarDimensionSchemeKeyTokens[] $VALUES;
    public static final SeslAppBarDimensionSchemeKeyTokens TopAppBarTopPadding;

    static {
        SeslAppBarDimensionSchemeKeyTokens seslAppBarDimensionSchemeKeyTokens = new SeslAppBarDimensionSchemeKeyTokens("TopAppBarTopPadding", 0);
        TopAppBarTopPadding = seslAppBarDimensionSchemeKeyTokens;
        SeslAppBarDimensionSchemeKeyTokens[] seslAppBarDimensionSchemeKeyTokensArr = {seslAppBarDimensionSchemeKeyTokens};
        $VALUES = seslAppBarDimensionSchemeKeyTokensArr;
        EnumEntriesKt.enumEntries(seslAppBarDimensionSchemeKeyTokensArr);
    }

    private SeslAppBarDimensionSchemeKeyTokens(String str, int i) {
    }

    public static SeslAppBarDimensionSchemeKeyTokens valueOf(String str) {
        return (SeslAppBarDimensionSchemeKeyTokens) Enum.valueOf(SeslAppBarDimensionSchemeKeyTokens.class, str);
    }

    public static SeslAppBarDimensionSchemeKeyTokens[] values() {
        return (SeslAppBarDimensionSchemeKeyTokens[]) $VALUES.clone();
    }
}

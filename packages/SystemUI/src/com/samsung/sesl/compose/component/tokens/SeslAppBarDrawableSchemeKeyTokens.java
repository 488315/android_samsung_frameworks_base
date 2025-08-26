package com.samsung.sesl.compose.component.tokens;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes4.dex */
public final class SeslAppBarDrawableSchemeKeyTokens implements SeslDrawableSchemeKeyTokens {
    public static final /* synthetic */ SeslAppBarDrawableSchemeKeyTokens[] $VALUES;
    public static final SeslAppBarDrawableSchemeKeyTokens BackIcon;

    static {
        SeslAppBarDrawableSchemeKeyTokens seslAppBarDrawableSchemeKeyTokens = new SeslAppBarDrawableSchemeKeyTokens("BackIcon", 0);
        BackIcon = seslAppBarDrawableSchemeKeyTokens;
        SeslAppBarDrawableSchemeKeyTokens[] seslAppBarDrawableSchemeKeyTokensArr = {seslAppBarDrawableSchemeKeyTokens};
        $VALUES = seslAppBarDrawableSchemeKeyTokensArr;
        EnumEntriesKt.enumEntries(seslAppBarDrawableSchemeKeyTokensArr);
    }

    private SeslAppBarDrawableSchemeKeyTokens(String str, int i) {
    }

    public static SeslAppBarDrawableSchemeKeyTokens valueOf(String str) {
        return (SeslAppBarDrawableSchemeKeyTokens) Enum.valueOf(SeslAppBarDrawableSchemeKeyTokens.class, str);
    }

    public static SeslAppBarDrawableSchemeKeyTokens[] values() {
        return (SeslAppBarDrawableSchemeKeyTokens[]) $VALUES.clone();
    }
}

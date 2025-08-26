package com.samsung.sesl.compose.component.tokens;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes4.dex */
public final class SeslButtonColorSchemeKeyTokens implements SeslColorSchemeKeyTokens {
    public static final /* synthetic */ SeslButtonColorSchemeKeyTokens[] $VALUES;
    public static final SeslButtonColorSchemeKeyTokens ContainerColor;
    public static final SeslButtonColorSchemeKeyTokens ContentColor;

    static {
        SeslButtonColorSchemeKeyTokens seslButtonColorSchemeKeyTokens = new SeslButtonColorSchemeKeyTokens("ContainerColor", 0);
        ContainerColor = seslButtonColorSchemeKeyTokens;
        SeslButtonColorSchemeKeyTokens seslButtonColorSchemeKeyTokens2 = new SeslButtonColorSchemeKeyTokens("ContentColor", 1);
        ContentColor = seslButtonColorSchemeKeyTokens2;
        SeslButtonColorSchemeKeyTokens[] seslButtonColorSchemeKeyTokensArr = {seslButtonColorSchemeKeyTokens, seslButtonColorSchemeKeyTokens2};
        $VALUES = seslButtonColorSchemeKeyTokensArr;
        EnumEntriesKt.enumEntries(seslButtonColorSchemeKeyTokensArr);
    }

    private SeslButtonColorSchemeKeyTokens(String str, int i) {
    }

    public static SeslButtonColorSchemeKeyTokens valueOf(String str) {
        return (SeslButtonColorSchemeKeyTokens) Enum.valueOf(SeslButtonColorSchemeKeyTokens.class, str);
    }

    public static SeslButtonColorSchemeKeyTokens[] values() {
        return (SeslButtonColorSchemeKeyTokens[]) $VALUES.clone();
    }
}

package com.samsung.sesl.compose.component.tokens;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes4.dex */
public final class SeslSwitchColorSchemeKeyTokens implements SeslColorSchemeKeyTokens {
    public static final /* synthetic */ SeslSwitchColorSchemeKeyTokens[] $VALUES;
    public static final SeslSwitchColorSchemeKeyTokens ThumbOffColor;
    public static final SeslSwitchColorSchemeKeyTokens ThumbOnColor;
    public static final SeslSwitchColorSchemeKeyTokens TrackOffColor;
    public static final SeslSwitchColorSchemeKeyTokens TrackOnColor;

    static {
        SeslSwitchColorSchemeKeyTokens seslSwitchColorSchemeKeyTokens = new SeslSwitchColorSchemeKeyTokens("TrackOnColor", 0);
        TrackOnColor = seslSwitchColorSchemeKeyTokens;
        SeslSwitchColorSchemeKeyTokens seslSwitchColorSchemeKeyTokens2 = new SeslSwitchColorSchemeKeyTokens("TrackOffColor", 1);
        TrackOffColor = seslSwitchColorSchemeKeyTokens2;
        SeslSwitchColorSchemeKeyTokens seslSwitchColorSchemeKeyTokens3 = new SeslSwitchColorSchemeKeyTokens("ThumbOnColor", 2);
        ThumbOnColor = seslSwitchColorSchemeKeyTokens3;
        SeslSwitchColorSchemeKeyTokens seslSwitchColorSchemeKeyTokens4 = new SeslSwitchColorSchemeKeyTokens("ThumbOffColor", 3);
        ThumbOffColor = seslSwitchColorSchemeKeyTokens4;
        SeslSwitchColorSchemeKeyTokens[] seslSwitchColorSchemeKeyTokensArr = {seslSwitchColorSchemeKeyTokens, seslSwitchColorSchemeKeyTokens2, seslSwitchColorSchemeKeyTokens3, seslSwitchColorSchemeKeyTokens4};
        $VALUES = seslSwitchColorSchemeKeyTokensArr;
        EnumEntriesKt.enumEntries(seslSwitchColorSchemeKeyTokensArr);
    }

    private SeslSwitchColorSchemeKeyTokens(String str, int i) {
    }

    public static SeslSwitchColorSchemeKeyTokens valueOf(String str) {
        return (SeslSwitchColorSchemeKeyTokens) Enum.valueOf(SeslSwitchColorSchemeKeyTokens.class, str);
    }

    public static SeslSwitchColorSchemeKeyTokens[] values() {
        return (SeslSwitchColorSchemeKeyTokens[]) $VALUES.clone();
    }
}

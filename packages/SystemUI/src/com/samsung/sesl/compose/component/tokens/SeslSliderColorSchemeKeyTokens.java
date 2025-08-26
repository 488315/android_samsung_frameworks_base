package com.samsung.sesl.compose.component.tokens;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes4.dex */
public final class SeslSliderColorSchemeKeyTokens implements SeslColorSchemeKeyTokens {
    public static final /* synthetic */ SeslSliderColorSchemeKeyTokens[] $VALUES;
    public static final SeslSliderColorSchemeKeyTokens ActivateThumbStrokeColor;
    public static final SeslSliderColorSchemeKeyTokens ActivateTickColor;
    public static final SeslSliderColorSchemeKeyTokens ActivateTrackColor;
    public static final SeslSliderColorSchemeKeyTokens InactivateTrackColor;
    public static final SeslSliderColorSchemeKeyTokens InactiveThumbStrokeColor;
    public static final SeslSliderColorSchemeKeyTokens InactiveTickColor;
    public static final SeslSliderColorSchemeKeyTokens LevelTackColor;
    public static final SeslSliderColorSchemeKeyTokens OverlapActivateColor;
    public static final SeslSliderColorSchemeKeyTokens OverlapInactiveColor;
    public static final SeslSliderColorSchemeKeyTokens ThumbFillColor;

    static {
        SeslSliderColorSchemeKeyTokens seslSliderColorSchemeKeyTokens = new SeslSliderColorSchemeKeyTokens("ThumbFillColor", 0);
        ThumbFillColor = seslSliderColorSchemeKeyTokens;
        SeslSliderColorSchemeKeyTokens seslSliderColorSchemeKeyTokens2 = new SeslSliderColorSchemeKeyTokens("ActivateThumbStrokeColor", 1);
        ActivateThumbStrokeColor = seslSliderColorSchemeKeyTokens2;
        SeslSliderColorSchemeKeyTokens seslSliderColorSchemeKeyTokens3 = new SeslSliderColorSchemeKeyTokens("InactiveThumbStrokeColor", 2);
        InactiveThumbStrokeColor = seslSliderColorSchemeKeyTokens3;
        SeslSliderColorSchemeKeyTokens seslSliderColorSchemeKeyTokens4 = new SeslSliderColorSchemeKeyTokens("ActivateTrackColor", 3);
        ActivateTrackColor = seslSliderColorSchemeKeyTokens4;
        SeslSliderColorSchemeKeyTokens seslSliderColorSchemeKeyTokens5 = new SeslSliderColorSchemeKeyTokens("InactivateTrackColor", 4);
        InactivateTrackColor = seslSliderColorSchemeKeyTokens5;
        SeslSliderColorSchemeKeyTokens seslSliderColorSchemeKeyTokens6 = new SeslSliderColorSchemeKeyTokens("OverlapActivateColor", 5);
        OverlapActivateColor = seslSliderColorSchemeKeyTokens6;
        SeslSliderColorSchemeKeyTokens seslSliderColorSchemeKeyTokens7 = new SeslSliderColorSchemeKeyTokens("OverlapInactiveColor", 6);
        OverlapInactiveColor = seslSliderColorSchemeKeyTokens7;
        SeslSliderColorSchemeKeyTokens seslSliderColorSchemeKeyTokens8 = new SeslSliderColorSchemeKeyTokens("LevelTackColor", 7);
        LevelTackColor = seslSliderColorSchemeKeyTokens8;
        SeslSliderColorSchemeKeyTokens seslSliderColorSchemeKeyTokens9 = new SeslSliderColorSchemeKeyTokens("ActivateTickColor", 8);
        ActivateTickColor = seslSliderColorSchemeKeyTokens9;
        SeslSliderColorSchemeKeyTokens seslSliderColorSchemeKeyTokens10 = new SeslSliderColorSchemeKeyTokens("InactiveTickColor", 9);
        InactiveTickColor = seslSliderColorSchemeKeyTokens10;
        SeslSliderColorSchemeKeyTokens[] seslSliderColorSchemeKeyTokensArr = {seslSliderColorSchemeKeyTokens, seslSliderColorSchemeKeyTokens2, seslSliderColorSchemeKeyTokens3, seslSliderColorSchemeKeyTokens4, seslSliderColorSchemeKeyTokens5, seslSliderColorSchemeKeyTokens6, seslSliderColorSchemeKeyTokens7, seslSliderColorSchemeKeyTokens8, seslSliderColorSchemeKeyTokens9, seslSliderColorSchemeKeyTokens10};
        $VALUES = seslSliderColorSchemeKeyTokensArr;
        EnumEntriesKt.enumEntries(seslSliderColorSchemeKeyTokensArr);
    }

    private SeslSliderColorSchemeKeyTokens(String str, int i) {
    }

    public static SeslSliderColorSchemeKeyTokens valueOf(String str) {
        return (SeslSliderColorSchemeKeyTokens) Enum.valueOf(SeslSliderColorSchemeKeyTokens.class, str);
    }

    public static SeslSliderColorSchemeKeyTokens[] values() {
        return (SeslSliderColorSchemeKeyTokens[]) $VALUES.clone();
    }
}

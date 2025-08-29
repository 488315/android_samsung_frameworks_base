package com.samsung.sesl.compose.component.tokens;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.Color;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class SeslTabTokens {
    public static final Companion Companion = new Companion(null);
    public static final SeslTabTokens darkTabTokens;
    public static final SeslTabTokens lightTabTokens;
    public final long selectedTextColor;
    public final long subTabIndicatorBackgroundColor;
    public final long subTabSelectedTextColor;
    public final long subTabTextColor;
    public final long subTabTwoLineSelectedTextColor;
    public final long subTabTwoLineSubSelectedTextColor;
    public final long subTabTwoLineSubTextColor;
    public final long subTabTwoLineTextColor;
    public final long textColor;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[SeslTabColorSchemeKeyTokens.values().length];
            try {
                iArr[SeslTabColorSchemeKeyTokens.TextColor.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[SeslTabColorSchemeKeyTokens.SelectedTextColor.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[SeslTabColorSchemeKeyTokens.SubTabTextColor.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[SeslTabColorSchemeKeyTokens.SubTabSelectedTextColor.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[SeslTabColorSchemeKeyTokens.SubTabTwoLineTextColor.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[SeslTabColorSchemeKeyTokens.SubTabTwoLineSelectedTextColor.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[SeslTabColorSchemeKeyTokens.SubTabTwoLineSubTextColor.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[SeslTabColorSchemeKeyTokens.SubTabTwoLineSubSelectedTextColor.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[SeslTabColorSchemeKeyTokens.SubTabIndicatorBackgroundColor.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        SeslPaletteTokens seslPaletteTokens = SeslPaletteTokens.INSTANCE;
        seslPaletteTokens.getClass();
        long j = SeslPaletteTokens.GRAY_TEXT_L5;
        seslPaletteTokens.getClass();
        long j2 = SeslPaletteTokens.GRAY_TEXT_L1;
        seslPaletteTokens.getClass();
        seslPaletteTokens.getClass();
        seslPaletteTokens.getClass();
        seslPaletteTokens.getClass();
        long j3 = SeslPaletteTokens.GRAY_TEXT_L2;
        seslPaletteTokens.getClass();
        seslPaletteTokens.getClass();
        long j4 = SeslPaletteTokens.Primary_Blue_Point_Light;
        seslPaletteTokens.getClass();
        lightTabTokens = new SeslTabTokens(j, j2, j, j2, j, j3, j, j4, j3, null);
        seslPaletteTokens.getClass();
        long j5 = SeslPaletteTokens.GRAY_TEXT_D6;
        seslPaletteTokens.getClass();
        long j6 = SeslPaletteTokens.GRAY_TEXT_D1;
        seslPaletteTokens.getClass();
        seslPaletteTokens.getClass();
        long j7 = SeslPaletteTokens.GRAY_TEXT_D2;
        seslPaletteTokens.getClass();
        seslPaletteTokens.getClass();
        seslPaletteTokens.getClass();
        seslPaletteTokens.getClass();
        long j8 = SeslPaletteTokens.Primary_Blue_Point_Dark;
        seslPaletteTokens.getClass();
        darkTabTokens = new SeslTabTokens(j5, j6, j5, j7, j5, j7, j5, j8, j6, null);
    }

    public /* synthetic */ SeslTabTokens(long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, j3, j4, j5, j6, j7, j8, j9);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SeslTabTokens)) {
            return false;
        }
        SeslTabTokens seslTabTokens = (SeslTabTokens) obj;
        long j = seslTabTokens.textColor;
        Color.Companion companion = Color.Companion;
        return ULong.m3446equalsimpl0(this.textColor, j) && ULong.m3446equalsimpl0(this.selectedTextColor, seslTabTokens.selectedTextColor) && ULong.m3446equalsimpl0(this.subTabTextColor, seslTabTokens.subTabTextColor) && ULong.m3446equalsimpl0(this.subTabSelectedTextColor, seslTabTokens.subTabSelectedTextColor) && ULong.m3446equalsimpl0(this.subTabTwoLineTextColor, seslTabTokens.subTabTwoLineTextColor) && ULong.m3446equalsimpl0(this.subTabTwoLineSelectedTextColor, seslTabTokens.subTabTwoLineSelectedTextColor) && ULong.m3446equalsimpl0(this.subTabTwoLineSubTextColor, seslTabTokens.subTabTwoLineSubTextColor) && ULong.m3446equalsimpl0(this.subTabTwoLineSubSelectedTextColor, seslTabTokens.subTabTwoLineSubSelectedTextColor) && ULong.m3446equalsimpl0(this.subTabIndicatorBackgroundColor, seslTabTokens.subTabIndicatorBackgroundColor);
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        return Long.hashCode(this.subTabIndicatorBackgroundColor) + MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(Long.hashCode(this.textColor) * 31, 31, this.selectedTextColor), 31, this.subTabTextColor), 31, this.subTabSelectedTextColor), 31, this.subTabTwoLineTextColor), 31, this.subTabTwoLineSelectedTextColor), 31, this.subTabTwoLineSubTextColor), 31, this.subTabTwoLineSubSelectedTextColor);
    }

    public final String toString() {
        String strM464toStringimpl = Color.m464toStringimpl(this.textColor);
        String strM464toStringimpl2 = Color.m464toStringimpl(this.selectedTextColor);
        String strM464toStringimpl3 = Color.m464toStringimpl(this.subTabTextColor);
        String strM464toStringimpl4 = Color.m464toStringimpl(this.subTabSelectedTextColor);
        String strM464toStringimpl5 = Color.m464toStringimpl(this.subTabTwoLineTextColor);
        String strM464toStringimpl6 = Color.m464toStringimpl(this.subTabTwoLineSelectedTextColor);
        String strM464toStringimpl7 = Color.m464toStringimpl(this.subTabTwoLineSubTextColor);
        String strM464toStringimpl8 = Color.m464toStringimpl(this.subTabTwoLineSubSelectedTextColor);
        String strM464toStringimpl9 = Color.m464toStringimpl(this.subTabIndicatorBackgroundColor);
        StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("SeslTabTokens(textColor=", strM464toStringimpl, ", selectedTextColor=", strM464toStringimpl2, ", subTabTextColor=");
        MoveResult$$ExternalSyntheticOutline0.m(sbM, strM464toStringimpl3, ", subTabSelectedTextColor=", strM464toStringimpl4, ", subTabTwoLineTextColor=");
        MoveResult$$ExternalSyntheticOutline0.m(sbM, strM464toStringimpl5, ", subTabTwoLineSelectedTextColor=", strM464toStringimpl6, ", subTabTwoLineSubTextColor=");
        MoveResult$$ExternalSyntheticOutline0.m(sbM, strM464toStringimpl7, ", subTabTwoLineSubSelectedTextColor=", strM464toStringimpl8, ", subTabIndicatorBackgroundColor=");
        return TransitionKt$$ExternalSyntheticOutline0.m(sbM, strM464toStringimpl9, ")");
    }

    private SeslTabTokens(long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9) {
        this.textColor = j;
        this.selectedTextColor = j2;
        this.subTabTextColor = j3;
        this.subTabSelectedTextColor = j4;
        this.subTabTwoLineTextColor = j5;
        this.subTabTwoLineSelectedTextColor = j6;
        this.subTabTwoLineSubTextColor = j7;
        this.subTabTwoLineSubSelectedTextColor = j8;
        this.subTabIndicatorBackgroundColor = j9;
    }
}

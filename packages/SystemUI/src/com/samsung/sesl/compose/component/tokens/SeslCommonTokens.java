package com.samsung.sesl.compose.component.tokens;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class SeslCommonTokens {
    public static final Companion Companion = new Companion(null);
    public static final SeslCommonTokens darkCommonTokens;
    public static final SeslCommonTokens lightCommonTokens;
    public final long mainTextColor;
    public final long pointTextColor;
    public final long primaryColor;
    public final long rippleColor;
    public final long roundedCornerColor;
    public final long subTextColor;
    public final long windowBackgroundColor;

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
            int[] iArr = new int[SeslCommonColorSchemeKeyTokens.values().length];
            try {
                iArr[SeslCommonColorSchemeKeyTokens.Primary.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[SeslCommonColorSchemeKeyTokens.WindowBackground.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[SeslCommonColorSchemeKeyTokens.Ripple.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[SeslCommonColorSchemeKeyTokens.RoundedCorner.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[SeslCommonColorSchemeKeyTokens.MainTextColor.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[SeslCommonColorSchemeKeyTokens.SubTextColor.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[SeslCommonColorSchemeKeyTokens.PointTextColor.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        SeslPaletteTokens.INSTANCE.getClass();
        long j = SeslPaletteTokens.Primary_Blue;
        long j2 = SeslPaletteTokens.GRAYSCALE_L1;
        Color.Companion.getClass();
        long j3 = Color.Black;
        lightCommonTokens = new SeslCommonTokens(j, j2, ColorKt.Color(Color.m463getRedimpl(j3), Color.m462getGreenimpl(j3), Color.m460getBlueimpl(j3), 0.1f, Color.m461getColorSpaceimpl(j3)), SeslPaletteTokens.GRAYSCALE_L3, SeslPaletteTokens.GRAY_TEXT_L1, SeslPaletteTokens.GRAY_TEXT_L5, SeslPaletteTokens.Primary_Blue_Point_Light, null);
        long j4 = SeslPaletteTokens.GRAYSCALE_D3;
        long j5 = Color.White;
        darkCommonTokens = new SeslCommonTokens(j, j4, ColorKt.Color(Color.m463getRedimpl(j5), Color.m462getGreenimpl(j5), Color.m460getBlueimpl(j5), 0.2f, Color.m461getColorSpaceimpl(j5)), SeslPaletteTokens.GRAYSCALE_D1, SeslPaletteTokens.Common_White, SeslPaletteTokens.GRAY_TEXT_D6, SeslPaletteTokens.Primary_Blue_Point_Dark, null);
    }

    public /* synthetic */ SeslCommonTokens(long j, long j2, long j3, long j4, long j5, long j6, long j7, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, j3, j4, j5, j6, j7);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SeslCommonTokens)) {
            return false;
        }
        SeslCommonTokens seslCommonTokens = (SeslCommonTokens) obj;
        long j = seslCommonTokens.primaryColor;
        Color.Companion companion = Color.Companion;
        return ULong.m3446equalsimpl0(this.primaryColor, j) && ULong.m3446equalsimpl0(this.windowBackgroundColor, seslCommonTokens.windowBackgroundColor) && ULong.m3446equalsimpl0(this.rippleColor, seslCommonTokens.rippleColor) && ULong.m3446equalsimpl0(this.roundedCornerColor, seslCommonTokens.roundedCornerColor) && ULong.m3446equalsimpl0(this.mainTextColor, seslCommonTokens.mainTextColor) && ULong.m3446equalsimpl0(this.subTextColor, seslCommonTokens.subTextColor) && ULong.m3446equalsimpl0(this.pointTextColor, seslCommonTokens.pointTextColor);
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        return Long.hashCode(this.pointTextColor) + MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(Long.hashCode(this.primaryColor) * 31, 31, this.windowBackgroundColor), 31, this.rippleColor), 31, this.roundedCornerColor), 31, this.mainTextColor), 31, this.subTextColor);
    }

    public final String toString() {
        String strM464toStringimpl = Color.m464toStringimpl(this.primaryColor);
        String strM464toStringimpl2 = Color.m464toStringimpl(this.windowBackgroundColor);
        String strM464toStringimpl3 = Color.m464toStringimpl(this.rippleColor);
        String strM464toStringimpl4 = Color.m464toStringimpl(this.roundedCornerColor);
        String strM464toStringimpl5 = Color.m464toStringimpl(this.mainTextColor);
        String strM464toStringimpl6 = Color.m464toStringimpl(this.subTextColor);
        String strM464toStringimpl7 = Color.m464toStringimpl(this.pointTextColor);
        StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("SeslCommonTokens(primaryColor=", strM464toStringimpl, ", windowBackgroundColor=", strM464toStringimpl2, ", rippleColor=");
        MoveResult$$ExternalSyntheticOutline0.m(sbM, strM464toStringimpl3, ", roundedCornerColor=", strM464toStringimpl4, ", mainTextColor=");
        MoveResult$$ExternalSyntheticOutline0.m(sbM, strM464toStringimpl5, ", subTextColor=", strM464toStringimpl6, ", pointTextColor=");
        return TransitionKt$$ExternalSyntheticOutline0.m(sbM, strM464toStringimpl7, ")");
    }

    private SeslCommonTokens(long j, long j2, long j3, long j4, long j5, long j6, long j7) {
        this.primaryColor = j;
        this.windowBackgroundColor = j2;
        this.rippleColor = j3;
        this.roundedCornerColor = j4;
        this.mainTextColor = j5;
        this.subTextColor = j6;
        this.pointTextColor = j7;
    }
}

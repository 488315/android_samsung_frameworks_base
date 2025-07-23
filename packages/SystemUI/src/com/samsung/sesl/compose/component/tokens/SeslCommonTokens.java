package com.samsung.sesl.compose.component.tokens;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        long Color;
        long Color2;
        SeslPaletteTokens.INSTANCE.getClass();
        long j = SeslPaletteTokens.Primary_Blue;
        long j2 = SeslPaletteTokens.GRAYSCALE_L1;
        Color.Companion.getClass();
        Color = ColorKt.Color(Color.m461getRedimpl(r0), Color.m460getGreenimpl(r0), Color.m458getBlueimpl(r0), 0.1f, Color.m459getColorSpaceimpl(Color.Black));
        lightCommonTokens = new SeslCommonTokens(j, j2, Color, SeslPaletteTokens.GRAYSCALE_L3, SeslPaletteTokens.GRAY_TEXT_L1, SeslPaletteTokens.GRAY_TEXT_L5, SeslPaletteTokens.Primary_Blue_Point_Light, null);
        long j3 = SeslPaletteTokens.GRAYSCALE_D3;
        Color2 = ColorKt.Color(Color.m461getRedimpl(r0), Color.m460getGreenimpl(r0), Color.m458getBlueimpl(r0), 0.2f, Color.m459getColorSpaceimpl(Color.White));
        darkCommonTokens = new SeslCommonTokens(j, j3, Color2, SeslPaletteTokens.GRAYSCALE_D1, SeslPaletteTokens.Common_White, SeslPaletteTokens.GRAY_TEXT_D6, SeslPaletteTokens.Primary_Blue_Point_Dark, null);
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
        return ULong.m3427equalsimpl0(this.primaryColor, j) && ULong.m3427equalsimpl0(this.windowBackgroundColor, seslCommonTokens.windowBackgroundColor) && ULong.m3427equalsimpl0(this.rippleColor, seslCommonTokens.rippleColor) && ULong.m3427equalsimpl0(this.roundedCornerColor, seslCommonTokens.roundedCornerColor) && ULong.m3427equalsimpl0(this.mainTextColor, seslCommonTokens.mainTextColor) && ULong.m3427equalsimpl0(this.subTextColor, seslCommonTokens.subTextColor) && ULong.m3427equalsimpl0(this.pointTextColor, seslCommonTokens.pointTextColor);
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        return Long.hashCode(this.pointTextColor) + MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(Long.hashCode(this.primaryColor) * 31, 31, this.windowBackgroundColor), 31, this.rippleColor), 31, this.roundedCornerColor), 31, this.mainTextColor), 31, this.subTextColor);
    }

    public final String toString() {
        String m462toStringimpl = Color.m462toStringimpl(this.primaryColor);
        String m462toStringimpl2 = Color.m462toStringimpl(this.windowBackgroundColor);
        String m462toStringimpl3 = Color.m462toStringimpl(this.rippleColor);
        String m462toStringimpl4 = Color.m462toStringimpl(this.roundedCornerColor);
        String m462toStringimpl5 = Color.m462toStringimpl(this.mainTextColor);
        String m462toStringimpl6 = Color.m462toStringimpl(this.subTextColor);
        String m462toStringimpl7 = Color.m462toStringimpl(this.pointTextColor);
        StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("SeslCommonTokens(primaryColor=", m462toStringimpl, ", windowBackgroundColor=", m462toStringimpl2, ", rippleColor=");
        MoveResult$$ExternalSyntheticOutline0.m(m, m462toStringimpl3, ", roundedCornerColor=", m462toStringimpl4, ", mainTextColor=");
        MoveResult$$ExternalSyntheticOutline0.m(m, m462toStringimpl5, ", subTextColor=", m462toStringimpl6, ", pointTextColor=");
        return TransitionKt$$ExternalSyntheticOutline0.m(m, m462toStringimpl7, ")");
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

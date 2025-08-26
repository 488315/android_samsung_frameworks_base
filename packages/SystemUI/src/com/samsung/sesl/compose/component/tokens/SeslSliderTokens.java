package com.samsung.sesl.compose.component.tokens;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import com.android.systemui.bixby2.controller.NotificationController$$ExternalSyntheticOutline0;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class SeslSliderTokens {
    public static final Companion Companion = new Companion(null);
    public static final SeslSliderTokens darkSliderTokens;
    public static final SeslSliderTokens lightSliderTokens;
    public final long activateThumbStrokeColor;
    public final long activateTickColor;
    public final long activateTrackColor;
    public final long inactivateTrackColor;
    public final long inactiveThumbStrokeColor;
    public final long inactiveTickColor;
    public final long levelTrackColor;
    public final long overlapActivateColor;
    public final long overlapInactiveColor;
    public final long thumbFillColor;

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
            int[] iArr = new int[SeslSliderColorSchemeKeyTokens.values().length];
            try {
                iArr[SeslSliderColorSchemeKeyTokens.ThumbFillColor.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[SeslSliderColorSchemeKeyTokens.ActivateThumbStrokeColor.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[SeslSliderColorSchemeKeyTokens.InactiveThumbStrokeColor.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[SeslSliderColorSchemeKeyTokens.ActivateTrackColor.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[SeslSliderColorSchemeKeyTokens.InactivateTrackColor.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[SeslSliderColorSchemeKeyTokens.OverlapActivateColor.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[SeslSliderColorSchemeKeyTokens.OverlapInactiveColor.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[SeslSliderColorSchemeKeyTokens.LevelTackColor.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[SeslSliderColorSchemeKeyTokens.ActivateTickColor.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[SeslSliderColorSchemeKeyTokens.InactiveTickColor.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        SeslPaletteTokens.INSTANCE.getClass();
        long j = SeslPaletteTokens.GRAYSCALE_L1;
        long j2 = SeslPaletteTokens.Primary_Blue;
        long j3 = SeslPaletteTokens.GRAYSCALE_L7;
        long j4 = SeslPaletteTokens.GRAYSCALE_L8;
        long jColor = ColorKt.Color(Color.m463getRedimpl(j4), Color.m462getGreenimpl(j4), Color.m460getBlueimpl(j4), 0.45f, Color.m461getColorSpaceimpl(j4));
        long j5 = SeslPaletteTokens.Functional_Orange_Light;
        long jColor2 = ColorKt.Color(Color.m463getRedimpl(j4), Color.m462getGreenimpl(j4), Color.m460getBlueimpl(j4), 0.45f, Color.m461getColorSpaceimpl(j4));
        long jColor3 = ColorKt.Color(Color.m463getRedimpl(j4), Color.m462getGreenimpl(j4), Color.m460getBlueimpl(j4), 0.4f, Color.m461getColorSpaceimpl(j4));
        long j6 = SeslPaletteTokens.GRAYSCALE_D9;
        lightSliderTokens = new SeslSliderTokens(j, j2, j3, j2, jColor, j5, jColor2, jColor3, j6, j6, null);
        darkSliderTokens = new SeslSliderTokens(j, j2, SeslPaletteTokens.GRAYSCALE_D7, j2, ColorKt.Color(Color.m463getRedimpl(j4), Color.m462getGreenimpl(j4), Color.m460getBlueimpl(j4), 0.4f, Color.m461getColorSpaceimpl(j4)), SeslPaletteTokens.Functional_Orange_Dark, ColorKt.Color(Color.m463getRedimpl(j4), Color.m462getGreenimpl(j4), Color.m460getBlueimpl(j4), 0.4f, Color.m461getColorSpaceimpl(j4)), ColorKt.Color(Color.m463getRedimpl(j4), Color.m462getGreenimpl(j4), Color.m460getBlueimpl(j4), 0.4f, Color.m461getColorSpaceimpl(j4)), j6, j6, null);
    }

    public /* synthetic */ SeslSliderTokens(long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9, long j10, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, j3, j4, j5, j6, j7, j8, j9, j10);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SeslSliderTokens)) {
            return false;
        }
        SeslSliderTokens seslSliderTokens = (SeslSliderTokens) obj;
        long j = seslSliderTokens.thumbFillColor;
        Color.Companion companion = Color.Companion;
        return ULong.m3447equalsimpl0(this.thumbFillColor, j) && ULong.m3447equalsimpl0(this.activateThumbStrokeColor, seslSliderTokens.activateThumbStrokeColor) && ULong.m3447equalsimpl0(this.inactiveThumbStrokeColor, seslSliderTokens.inactiveThumbStrokeColor) && ULong.m3447equalsimpl0(this.activateTrackColor, seslSliderTokens.activateTrackColor) && ULong.m3447equalsimpl0(this.inactivateTrackColor, seslSliderTokens.inactivateTrackColor) && ULong.m3447equalsimpl0(this.overlapActivateColor, seslSliderTokens.overlapActivateColor) && ULong.m3447equalsimpl0(this.overlapInactiveColor, seslSliderTokens.overlapInactiveColor) && ULong.m3447equalsimpl0(this.levelTrackColor, seslSliderTokens.levelTrackColor) && ULong.m3447equalsimpl0(this.activateTickColor, seslSliderTokens.activateTickColor) && ULong.m3447equalsimpl0(this.inactiveTickColor, seslSliderTokens.inactiveTickColor);
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        return Long.hashCode(this.inactiveTickColor) + MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(Long.hashCode(this.thumbFillColor) * 31, 31, this.activateThumbStrokeColor), 31, this.inactiveThumbStrokeColor), 31, this.activateTrackColor), 31, this.inactivateTrackColor), 31, this.overlapActivateColor), 31, this.overlapInactiveColor), 31, this.levelTrackColor), 31, this.activateTickColor);
    }

    public final String toString() {
        String strM464toStringimpl = Color.m464toStringimpl(this.thumbFillColor);
        String strM464toStringimpl2 = Color.m464toStringimpl(this.activateThumbStrokeColor);
        String strM464toStringimpl3 = Color.m464toStringimpl(this.inactiveThumbStrokeColor);
        String strM464toStringimpl4 = Color.m464toStringimpl(this.activateTrackColor);
        String strM464toStringimpl5 = Color.m464toStringimpl(this.inactivateTrackColor);
        String strM464toStringimpl6 = Color.m464toStringimpl(this.overlapActivateColor);
        String strM464toStringimpl7 = Color.m464toStringimpl(this.overlapInactiveColor);
        String strM464toStringimpl8 = Color.m464toStringimpl(this.levelTrackColor);
        String strM464toStringimpl9 = Color.m464toStringimpl(this.activateTickColor);
        String strM464toStringimpl10 = Color.m464toStringimpl(this.inactiveTickColor);
        StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("SeslSliderTokens(thumbFillColor=", strM464toStringimpl, ", activateThumbStrokeColor=", strM464toStringimpl2, ", inactiveThumbStrokeColor=");
        MoveResult$$ExternalSyntheticOutline0.m(sbM, strM464toStringimpl3, ", activateTrackColor=", strM464toStringimpl4, ", inactivateTrackColor=");
        MoveResult$$ExternalSyntheticOutline0.m(sbM, strM464toStringimpl5, ", overlapActivateColor=", strM464toStringimpl6, ", overlapInactiveColor=");
        MoveResult$$ExternalSyntheticOutline0.m(sbM, strM464toStringimpl7, ", levelTrackColor=", strM464toStringimpl8, ", activateTickColor=");
        return NotificationController$$ExternalSyntheticOutline0.m(sbM, strM464toStringimpl9, ", inactiveTickColor=", strM464toStringimpl10, ")");
    }

    private SeslSliderTokens(long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9, long j10) {
        this.thumbFillColor = j;
        this.activateThumbStrokeColor = j2;
        this.inactiveThumbStrokeColor = j3;
        this.activateTrackColor = j4;
        this.inactivateTrackColor = j5;
        this.overlapActivateColor = j6;
        this.overlapInactiveColor = j7;
        this.levelTrackColor = j8;
        this.activateTickColor = j9;
        this.inactiveTickColor = j10;
    }
}

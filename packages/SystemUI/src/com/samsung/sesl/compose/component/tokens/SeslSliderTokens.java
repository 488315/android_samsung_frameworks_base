package com.samsung.sesl.compose.component.tokens;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import com.android.systemui.bixby2.controller.NotificationController$$ExternalSyntheticOutline0;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        long Color;
        long Color2;
        long Color3;
        long Color4;
        long Color5;
        long Color6;
        SeslPaletteTokens.INSTANCE.getClass();
        long j = SeslPaletteTokens.GRAYSCALE_L1;
        long j2 = SeslPaletteTokens.Primary_Blue;
        long j3 = SeslPaletteTokens.GRAYSCALE_L7;
        long j4 = SeslPaletteTokens.GRAYSCALE_L8;
        Color = ColorKt.Color(Color.m461getRedimpl(j4), Color.m460getGreenimpl(j4), Color.m458getBlueimpl(j4), 0.45f, Color.m459getColorSpaceimpl(j4));
        long j5 = SeslPaletteTokens.Functional_Orange_Light;
        Color2 = ColorKt.Color(Color.m461getRedimpl(j4), Color.m460getGreenimpl(j4), Color.m458getBlueimpl(j4), 0.45f, Color.m459getColorSpaceimpl(j4));
        Color3 = ColorKt.Color(Color.m461getRedimpl(j4), Color.m460getGreenimpl(j4), Color.m458getBlueimpl(j4), 0.4f, Color.m459getColorSpaceimpl(j4));
        long j6 = SeslPaletteTokens.GRAYSCALE_D9;
        lightSliderTokens = new SeslSliderTokens(j, j2, j3, j2, Color, j5, Color2, Color3, j6, j6, null);
        long j7 = SeslPaletteTokens.GRAYSCALE_D7;
        Color4 = ColorKt.Color(Color.m461getRedimpl(j4), Color.m460getGreenimpl(j4), Color.m458getBlueimpl(j4), 0.4f, Color.m459getColorSpaceimpl(j4));
        long j8 = SeslPaletteTokens.Functional_Orange_Dark;
        Color5 = ColorKt.Color(Color.m461getRedimpl(j4), Color.m460getGreenimpl(j4), Color.m458getBlueimpl(j4), 0.4f, Color.m459getColorSpaceimpl(j4));
        Color6 = ColorKt.Color(Color.m461getRedimpl(j4), Color.m460getGreenimpl(j4), Color.m458getBlueimpl(j4), 0.4f, Color.m459getColorSpaceimpl(j4));
        darkSliderTokens = new SeslSliderTokens(j, j2, j7, j2, Color4, j8, Color5, Color6, j6, j6, null);
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
        return ULong.m3427equalsimpl0(this.thumbFillColor, j) && ULong.m3427equalsimpl0(this.activateThumbStrokeColor, seslSliderTokens.activateThumbStrokeColor) && ULong.m3427equalsimpl0(this.inactiveThumbStrokeColor, seslSliderTokens.inactiveThumbStrokeColor) && ULong.m3427equalsimpl0(this.activateTrackColor, seslSliderTokens.activateTrackColor) && ULong.m3427equalsimpl0(this.inactivateTrackColor, seslSliderTokens.inactivateTrackColor) && ULong.m3427equalsimpl0(this.overlapActivateColor, seslSliderTokens.overlapActivateColor) && ULong.m3427equalsimpl0(this.overlapInactiveColor, seslSliderTokens.overlapInactiveColor) && ULong.m3427equalsimpl0(this.levelTrackColor, seslSliderTokens.levelTrackColor) && ULong.m3427equalsimpl0(this.activateTickColor, seslSliderTokens.activateTickColor) && ULong.m3427equalsimpl0(this.inactiveTickColor, seslSliderTokens.inactiveTickColor);
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        return Long.hashCode(this.inactiveTickColor) + MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(Long.hashCode(this.thumbFillColor) * 31, 31, this.activateThumbStrokeColor), 31, this.inactiveThumbStrokeColor), 31, this.activateTrackColor), 31, this.inactivateTrackColor), 31, this.overlapActivateColor), 31, this.overlapInactiveColor), 31, this.levelTrackColor), 31, this.activateTickColor);
    }

    public final String toString() {
        String m462toStringimpl = Color.m462toStringimpl(this.thumbFillColor);
        String m462toStringimpl2 = Color.m462toStringimpl(this.activateThumbStrokeColor);
        String m462toStringimpl3 = Color.m462toStringimpl(this.inactiveThumbStrokeColor);
        String m462toStringimpl4 = Color.m462toStringimpl(this.activateTrackColor);
        String m462toStringimpl5 = Color.m462toStringimpl(this.inactivateTrackColor);
        String m462toStringimpl6 = Color.m462toStringimpl(this.overlapActivateColor);
        String m462toStringimpl7 = Color.m462toStringimpl(this.overlapInactiveColor);
        String m462toStringimpl8 = Color.m462toStringimpl(this.levelTrackColor);
        String m462toStringimpl9 = Color.m462toStringimpl(this.activateTickColor);
        String m462toStringimpl10 = Color.m462toStringimpl(this.inactiveTickColor);
        StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("SeslSliderTokens(thumbFillColor=", m462toStringimpl, ", activateThumbStrokeColor=", m462toStringimpl2, ", inactiveThumbStrokeColor=");
        MoveResult$$ExternalSyntheticOutline0.m(m, m462toStringimpl3, ", activateTrackColor=", m462toStringimpl4, ", inactivateTrackColor=");
        MoveResult$$ExternalSyntheticOutline0.m(m, m462toStringimpl5, ", overlapActivateColor=", m462toStringimpl6, ", overlapInactiveColor=");
        MoveResult$$ExternalSyntheticOutline0.m(m, m462toStringimpl7, ", levelTrackColor=", m462toStringimpl8, ", activateTickColor=");
        return NotificationController$$ExternalSyntheticOutline0.m(m, m462toStringimpl9, ", inactiveTickColor=", m462toStringimpl10, ")");
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

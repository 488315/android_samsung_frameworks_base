package com.android.compose;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.Color;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class PlatformSliderColors {
    public final long disabledIconColor;
    public final long disabledIndicatorColor;
    public final long disabledLabelColor;
    public final long disabledTrackColor;
    public final long iconColor;
    public final long indicatorColor;
    public final long labelColorOnIndicator;
    public final long labelColorOnTrack;
    public final long trackColor;

    public /* synthetic */ PlatformSliderColors(long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, j3, j4, j5, j6, j7, j8, j9);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PlatformSliderColors)) {
            return false;
        }
        PlatformSliderColors platformSliderColors = (PlatformSliderColors) obj;
        long j = platformSliderColors.trackColor;
        Color.Companion companion = Color.Companion;
        return ULong.m3447equalsimpl0(this.trackColor, j) && ULong.m3447equalsimpl0(this.indicatorColor, platformSliderColors.indicatorColor) && ULong.m3447equalsimpl0(this.iconColor, platformSliderColors.iconColor) && ULong.m3447equalsimpl0(this.labelColorOnIndicator, platformSliderColors.labelColorOnIndicator) && ULong.m3447equalsimpl0(this.labelColorOnTrack, platformSliderColors.labelColorOnTrack) && ULong.m3447equalsimpl0(this.disabledTrackColor, platformSliderColors.disabledTrackColor) && ULong.m3447equalsimpl0(this.disabledIndicatorColor, platformSliderColors.disabledIndicatorColor) && ULong.m3447equalsimpl0(this.disabledIconColor, platformSliderColors.disabledIconColor) && ULong.m3447equalsimpl0(this.disabledLabelColor, platformSliderColors.disabledLabelColor);
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        return Long.hashCode(this.disabledLabelColor) + MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(Long.hashCode(this.trackColor) * 31, 31, this.indicatorColor), 31, this.iconColor), 31, this.labelColorOnIndicator), 31, this.labelColorOnTrack), 31, this.disabledTrackColor), 31, this.disabledIndicatorColor), 31, this.disabledIconColor);
    }

    public final String toString() {
        String strM464toStringimpl = Color.m464toStringimpl(this.trackColor);
        String strM464toStringimpl2 = Color.m464toStringimpl(this.indicatorColor);
        String strM464toStringimpl3 = Color.m464toStringimpl(this.iconColor);
        String strM464toStringimpl4 = Color.m464toStringimpl(this.labelColorOnIndicator);
        String strM464toStringimpl5 = Color.m464toStringimpl(this.labelColorOnTrack);
        String strM464toStringimpl6 = Color.m464toStringimpl(this.disabledTrackColor);
        String strM464toStringimpl7 = Color.m464toStringimpl(this.disabledIndicatorColor);
        String strM464toStringimpl8 = Color.m464toStringimpl(this.disabledIconColor);
        String strM464toStringimpl9 = Color.m464toStringimpl(this.disabledLabelColor);
        StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("PlatformSliderColors(trackColor=", strM464toStringimpl, ", indicatorColor=", strM464toStringimpl2, ", iconColor=");
        MoveResult$$ExternalSyntheticOutline0.m(sbM, strM464toStringimpl3, ", labelColorOnIndicator=", strM464toStringimpl4, ", labelColorOnTrack=");
        MoveResult$$ExternalSyntheticOutline0.m(sbM, strM464toStringimpl5, ", disabledTrackColor=", strM464toStringimpl6, ", disabledIndicatorColor=");
        MoveResult$$ExternalSyntheticOutline0.m(sbM, strM464toStringimpl7, ", disabledIconColor=", strM464toStringimpl8, ", disabledLabelColor=");
        return TransitionKt$$ExternalSyntheticOutline0.m(sbM, strM464toStringimpl9, ")");
    }

    private PlatformSliderColors(long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9) {
        this.trackColor = j;
        this.indicatorColor = j2;
        this.iconColor = j3;
        this.labelColorOnIndicator = j4;
        this.labelColorOnTrack = j5;
        this.disabledTrackColor = j6;
        this.disabledIndicatorColor = j7;
        this.disabledIconColor = j8;
        this.disabledLabelColor = j9;
    }
}

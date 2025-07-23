package com.android.compose;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.Color;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        return ULong.m3427equalsimpl0(this.trackColor, j) && ULong.m3427equalsimpl0(this.indicatorColor, platformSliderColors.indicatorColor) && ULong.m3427equalsimpl0(this.iconColor, platformSliderColors.iconColor) && ULong.m3427equalsimpl0(this.labelColorOnIndicator, platformSliderColors.labelColorOnIndicator) && ULong.m3427equalsimpl0(this.labelColorOnTrack, platformSliderColors.labelColorOnTrack) && ULong.m3427equalsimpl0(this.disabledTrackColor, platformSliderColors.disabledTrackColor) && ULong.m3427equalsimpl0(this.disabledIndicatorColor, platformSliderColors.disabledIndicatorColor) && ULong.m3427equalsimpl0(this.disabledIconColor, platformSliderColors.disabledIconColor) && ULong.m3427equalsimpl0(this.disabledLabelColor, platformSliderColors.disabledLabelColor);
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        return Long.hashCode(this.disabledLabelColor) + MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(Long.hashCode(this.trackColor) * 31, 31, this.indicatorColor), 31, this.iconColor), 31, this.labelColorOnIndicator), 31, this.labelColorOnTrack), 31, this.disabledTrackColor), 31, this.disabledIndicatorColor), 31, this.disabledIconColor);
    }

    public final String toString() {
        String m462toStringimpl = Color.m462toStringimpl(this.trackColor);
        String m462toStringimpl2 = Color.m462toStringimpl(this.indicatorColor);
        String m462toStringimpl3 = Color.m462toStringimpl(this.iconColor);
        String m462toStringimpl4 = Color.m462toStringimpl(this.labelColorOnIndicator);
        String m462toStringimpl5 = Color.m462toStringimpl(this.labelColorOnTrack);
        String m462toStringimpl6 = Color.m462toStringimpl(this.disabledTrackColor);
        String m462toStringimpl7 = Color.m462toStringimpl(this.disabledIndicatorColor);
        String m462toStringimpl8 = Color.m462toStringimpl(this.disabledIconColor);
        String m462toStringimpl9 = Color.m462toStringimpl(this.disabledLabelColor);
        StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("PlatformSliderColors(trackColor=", m462toStringimpl, ", indicatorColor=", m462toStringimpl2, ", iconColor=");
        MoveResult$$ExternalSyntheticOutline0.m(m, m462toStringimpl3, ", labelColorOnIndicator=", m462toStringimpl4, ", labelColorOnTrack=");
        MoveResult$$ExternalSyntheticOutline0.m(m, m462toStringimpl5, ", disabledTrackColor=", m462toStringimpl6, ", disabledIndicatorColor=");
        MoveResult$$ExternalSyntheticOutline0.m(m, m462toStringimpl7, ", disabledIconColor=", m462toStringimpl8, ", disabledLabelColor=");
        return TransitionKt$$ExternalSyntheticOutline0.m(m, m462toStringimpl9, ")");
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

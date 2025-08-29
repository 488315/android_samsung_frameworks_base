package androidx.compose.material3;

import androidx.compose.ui.graphics.Color;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class SliderColors {
    public final long activeTickColor;
    public final long activeTrackColor;
    public final long disabledActiveTickColor;
    public final long disabledActiveTrackColor;
    public final long disabledInactiveTickColor;
    public final long disabledInactiveTrackColor;
    public final long disabledThumbColor;
    public final long inactiveTickColor;
    public final long inactiveTrackColor;
    public final long thumbColor;

    public /* synthetic */ SliderColors(long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9, long j10, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, j3, j4, j5, j6, j7, j8, j9, j10);
    }

    /* renamed from: copy--K518z4, reason: not valid java name */
    public final SliderColors m291copyK518z4(long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9, long j10) {
        return new SliderColors(j != 16 ? j : this.thumbColor, j2 != 16 ? j2 : this.activeTrackColor, j3 != 16 ? j3 : this.activeTickColor, j4 != 16 ? j4 : this.inactiveTrackColor, j5 != 16 ? j5 : this.inactiveTickColor, j6 != 16 ? j6 : this.disabledThumbColor, j7 != 16 ? j7 : this.disabledActiveTrackColor, j8 != 16 ? j8 : this.disabledActiveTickColor, j9 != 16 ? j9 : this.disabledInactiveTrackColor, j10 != 16 ? j10 : this.disabledInactiveTickColor, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof SliderColors)) {
            return false;
        }
        SliderColors sliderColors = (SliderColors) obj;
        Color.Companion companion = Color.Companion;
        return ULong.m3446equalsimpl0(this.thumbColor, sliderColors.thumbColor) && ULong.m3446equalsimpl0(this.activeTrackColor, sliderColors.activeTrackColor) && ULong.m3446equalsimpl0(this.activeTickColor, sliderColors.activeTickColor) && ULong.m3446equalsimpl0(this.inactiveTrackColor, sliderColors.inactiveTrackColor) && ULong.m3446equalsimpl0(this.inactiveTickColor, sliderColors.inactiveTickColor) && ULong.m3446equalsimpl0(this.disabledThumbColor, sliderColors.disabledThumbColor) && ULong.m3446equalsimpl0(this.disabledActiveTrackColor, sliderColors.disabledActiveTrackColor) && ULong.m3446equalsimpl0(this.disabledActiveTickColor, sliderColors.disabledActiveTickColor) && ULong.m3446equalsimpl0(this.disabledInactiveTrackColor, sliderColors.disabledInactiveTrackColor) && ULong.m3446equalsimpl0(this.disabledInactiveTickColor, sliderColors.disabledInactiveTickColor);
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        return Long.hashCode(this.disabledInactiveTickColor) + MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(Long.hashCode(this.thumbColor) * 31, 31, this.activeTrackColor), 31, this.activeTickColor), 31, this.inactiveTrackColor), 31, this.inactiveTickColor), 31, this.disabledThumbColor), 31, this.disabledActiveTrackColor), 31, this.disabledActiveTickColor), 31, this.disabledInactiveTrackColor);
    }

    /* renamed from: trackColor-WaAFU9c$material3_release, reason: not valid java name */
    public final long m292trackColorWaAFU9c$material3_release(boolean z, boolean z2) {
        return z ? z2 ? this.activeTrackColor : this.inactiveTrackColor : z2 ? this.disabledActiveTrackColor : this.disabledInactiveTrackColor;
    }

    private SliderColors(long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9, long j10) {
        this.thumbColor = j;
        this.activeTrackColor = j2;
        this.activeTickColor = j3;
        this.inactiveTrackColor = j4;
        this.inactiveTickColor = j5;
        this.disabledThumbColor = j6;
        this.disabledActiveTrackColor = j7;
        this.disabledActiveTickColor = j8;
        this.disabledInactiveTrackColor = j9;
        this.disabledInactiveTickColor = j10;
    }
}

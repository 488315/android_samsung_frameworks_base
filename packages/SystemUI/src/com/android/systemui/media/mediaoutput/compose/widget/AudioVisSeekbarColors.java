package com.android.systemui.media.mediaoutput.compose.widget;

import kotlin.jvm.internal.DefaultConstructorMarker;

public final class AudioVisSeekbarColors {
    public final long activeTrackColor;
    public final long activeTrackSecondaryColor;
    public final long disabledActiveTrackColor;
    public final long disabledActiveTrackSecondaryColor;
    public final long disabledInactiveTrackColor;
    public final long disabledInactiveTrackSecondaryColor;
    public final long disabledThumbColor;
    public final long inactiveTrackColor;
    public final long inactiveTrackSecondaryColor;
    public final long thumbColor;

    public /* synthetic */ AudioVisSeekbarColors(long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9, long j10, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, j3, j4, j5, j6, j7, j8, j9, j10);
    }

    /* renamed from: trackColor-WaAFU9c$frameworks__base__packages__SystemUI__android_common__SystemUI_core, reason: not valid java name */
    public final long m1974x119e4b41(boolean z, boolean z2) {
        return z ? z2 ? this.activeTrackColor : this.inactiveTrackColor : z2 ? this.disabledActiveTrackColor : this.disabledInactiveTrackColor;
    }

    private AudioVisSeekbarColors(long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9, long j10) {
        this.thumbColor = j;
        this.activeTrackColor = j2;
        this.activeTrackSecondaryColor = j3;
        this.inactiveTrackColor = j4;
        this.disabledThumbColor = j6;
        this.disabledActiveTrackColor = j7;
        this.disabledActiveTrackSecondaryColor = j8;
        this.disabledInactiveTrackColor = j9;
    }
}

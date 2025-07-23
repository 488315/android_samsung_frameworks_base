package com.samsung.sesl.compose.theme;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.Color;
import com.android.systemui.bixby2.controller.NotificationController$$ExternalSyntheticOutline0;
import com.samsung.sesl.compose.component.SeslSwitchColors;
import com.samsung.sesl.compose.component.SeslTopAppBarColors;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SeslColorScheme {
    public final long background;
    public final long controlNormal;
    public SeslSwitchColors defaultSwitchColorsCached;
    public SeslTopAppBarColors defaultTopAppBarColorsCached;
    public final long mainText;
    public final long pointText;
    public final long primary;
    public final long ripple;
    public final long roundedCorner;
    public final long subText;

    public /* synthetic */ SeslColorScheme(long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, j3, j4, j5, j6, j7, j8);
    }

    /* renamed from: copy-FD3wquc$default, reason: not valid java name */
    public static SeslColorScheme m3340copyFD3wquc$default(SeslColorScheme seslColorScheme, long j, long j2) {
        long j3 = seslColorScheme.mainText;
        long j4 = seslColorScheme.subText;
        long j5 = seslColorScheme.pointText;
        long j6 = seslColorScheme.background;
        long j7 = seslColorScheme.roundedCorner;
        long j8 = seslColorScheme.ripple;
        seslColorScheme.getClass();
        return new SeslColorScheme(j, j3, j4, j5, j6, j7, j8, j2, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SeslColorScheme)) {
            return false;
        }
        SeslColorScheme seslColorScheme = (SeslColorScheme) obj;
        long j = seslColorScheme.primary;
        Color.Companion companion = Color.Companion;
        return ULong.m3427equalsimpl0(this.primary, j) && ULong.m3427equalsimpl0(this.mainText, seslColorScheme.mainText) && ULong.m3427equalsimpl0(this.subText, seslColorScheme.subText) && ULong.m3427equalsimpl0(this.pointText, seslColorScheme.pointText) && ULong.m3427equalsimpl0(this.background, seslColorScheme.background) && ULong.m3427equalsimpl0(this.roundedCorner, seslColorScheme.roundedCorner) && ULong.m3427equalsimpl0(this.ripple, seslColorScheme.ripple) && ULong.m3427equalsimpl0(this.controlNormal, seslColorScheme.controlNormal);
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        return Long.hashCode(this.controlNormal) + MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(Long.hashCode(this.primary) * 31, 31, this.mainText), 31, this.subText), 31, this.pointText), 31, this.background), 31, this.roundedCorner), 31, this.ripple);
    }

    public final String toString() {
        String m462toStringimpl = Color.m462toStringimpl(this.primary);
        String m462toStringimpl2 = Color.m462toStringimpl(this.mainText);
        String m462toStringimpl3 = Color.m462toStringimpl(this.subText);
        String m462toStringimpl4 = Color.m462toStringimpl(this.pointText);
        String m462toStringimpl5 = Color.m462toStringimpl(this.background);
        String m462toStringimpl6 = Color.m462toStringimpl(this.roundedCorner);
        String m462toStringimpl7 = Color.m462toStringimpl(this.ripple);
        String m462toStringimpl8 = Color.m462toStringimpl(this.controlNormal);
        StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("SeslColorScheme(primary=", m462toStringimpl, ", mainText=", m462toStringimpl2, ", subText=");
        MoveResult$$ExternalSyntheticOutline0.m(m, m462toStringimpl3, ", pointText=", m462toStringimpl4, ", background=");
        MoveResult$$ExternalSyntheticOutline0.m(m, m462toStringimpl5, ", roundedCorner=", m462toStringimpl6, ", ripple=");
        return NotificationController$$ExternalSyntheticOutline0.m(m, m462toStringimpl7, ", controlNormal=", m462toStringimpl8, ")");
    }

    private SeslColorScheme(long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8) {
        this.primary = j;
        this.mainText = j2;
        this.subText = j3;
        this.pointText = j4;
        this.background = j5;
        this.roundedCorner = j6;
        this.ripple = j7;
        this.controlNormal = j8;
    }
}

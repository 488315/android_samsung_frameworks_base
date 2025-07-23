package com.android.systemui.volume.panel.component.selector.ui.composable;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.Color;
import com.android.systemui.bixby2.controller.NotificationController$$ExternalSyntheticOutline0;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class VolumePanelRadioButtonBarColors {
    public final long iconColor;
    public final long indicatorBackgroundColor;
    public final long indicatorColor;
    public final long labelColor;
    public final long selectedIconColor;
    public final long selectedLabelColor;

    public /* synthetic */ VolumePanelRadioButtonBarColors(long j, long j2, long j3, long j4, long j5, long j6, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, j3, j4, j5, j6);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof VolumePanelRadioButtonBarColors)) {
            return false;
        }
        VolumePanelRadioButtonBarColors volumePanelRadioButtonBarColors = (VolumePanelRadioButtonBarColors) obj;
        long j = volumePanelRadioButtonBarColors.indicatorColor;
        Color.Companion companion = Color.Companion;
        return ULong.m3427equalsimpl0(this.indicatorColor, j) && ULong.m3427equalsimpl0(this.indicatorBackgroundColor, volumePanelRadioButtonBarColors.indicatorBackgroundColor) && ULong.m3427equalsimpl0(this.iconColor, volumePanelRadioButtonBarColors.iconColor) && ULong.m3427equalsimpl0(this.selectedIconColor, volumePanelRadioButtonBarColors.selectedIconColor) && ULong.m3427equalsimpl0(this.labelColor, volumePanelRadioButtonBarColors.labelColor) && ULong.m3427equalsimpl0(this.selectedLabelColor, volumePanelRadioButtonBarColors.selectedLabelColor);
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        return Long.hashCode(this.selectedLabelColor) + MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(Long.hashCode(this.indicatorColor) * 31, 31, this.indicatorBackgroundColor), 31, this.iconColor), 31, this.selectedIconColor), 31, this.labelColor);
    }

    public final String toString() {
        String m462toStringimpl = Color.m462toStringimpl(this.indicatorColor);
        String m462toStringimpl2 = Color.m462toStringimpl(this.indicatorBackgroundColor);
        String m462toStringimpl3 = Color.m462toStringimpl(this.iconColor);
        String m462toStringimpl4 = Color.m462toStringimpl(this.selectedIconColor);
        String m462toStringimpl5 = Color.m462toStringimpl(this.labelColor);
        String m462toStringimpl6 = Color.m462toStringimpl(this.selectedLabelColor);
        StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("VolumePanelRadioButtonBarColors(indicatorColor=", m462toStringimpl, ", indicatorBackgroundColor=", m462toStringimpl2, ", iconColor=");
        MoveResult$$ExternalSyntheticOutline0.m(m, m462toStringimpl3, ", selectedIconColor=", m462toStringimpl4, ", labelColor=");
        return NotificationController$$ExternalSyntheticOutline0.m(m, m462toStringimpl5, ", selectedLabelColor=", m462toStringimpl6, ")");
    }

    private VolumePanelRadioButtonBarColors(long j, long j2, long j3, long j4, long j5, long j6) {
        this.indicatorColor = j;
        this.indicatorBackgroundColor = j2;
        this.iconColor = j3;
        this.selectedIconColor = j4;
        this.labelColor = j5;
        this.selectedLabelColor = j6;
    }
}

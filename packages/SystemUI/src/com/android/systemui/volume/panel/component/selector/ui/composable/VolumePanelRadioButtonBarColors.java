package com.android.systemui.volume.panel.component.selector.ui.composable;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.Color;
import com.android.systemui.bixby2.controller.NotificationController$$ExternalSyntheticOutline0;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

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
        return ULong.m3447equalsimpl0(this.indicatorColor, j) && ULong.m3447equalsimpl0(this.indicatorBackgroundColor, volumePanelRadioButtonBarColors.indicatorBackgroundColor) && ULong.m3447equalsimpl0(this.iconColor, volumePanelRadioButtonBarColors.iconColor) && ULong.m3447equalsimpl0(this.selectedIconColor, volumePanelRadioButtonBarColors.selectedIconColor) && ULong.m3447equalsimpl0(this.labelColor, volumePanelRadioButtonBarColors.labelColor) && ULong.m3447equalsimpl0(this.selectedLabelColor, volumePanelRadioButtonBarColors.selectedLabelColor);
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        return Long.hashCode(this.selectedLabelColor) + MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(Long.hashCode(this.indicatorColor) * 31, 31, this.indicatorBackgroundColor), 31, this.iconColor), 31, this.selectedIconColor), 31, this.labelColor);
    }

    public final String toString() {
        String strM464toStringimpl = Color.m464toStringimpl(this.indicatorColor);
        String strM464toStringimpl2 = Color.m464toStringimpl(this.indicatorBackgroundColor);
        String strM464toStringimpl3 = Color.m464toStringimpl(this.iconColor);
        String strM464toStringimpl4 = Color.m464toStringimpl(this.selectedIconColor);
        String strM464toStringimpl5 = Color.m464toStringimpl(this.labelColor);
        String strM464toStringimpl6 = Color.m464toStringimpl(this.selectedLabelColor);
        StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("VolumePanelRadioButtonBarColors(indicatorColor=", strM464toStringimpl, ", indicatorBackgroundColor=", strM464toStringimpl2, ", iconColor=");
        MoveResult$$ExternalSyntheticOutline0.m(sbM, strM464toStringimpl3, ", selectedIconColor=", strM464toStringimpl4, ", labelColor=");
        return NotificationController$$ExternalSyntheticOutline0.m(sbM, strM464toStringimpl5, ", selectedLabelColor=", strM464toStringimpl6, ")");
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

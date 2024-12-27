package com.android.systemui.volume.panel.component.selector.ui.composable;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.animation.Scale$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.Color;
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        return Color.m386equalsimpl0(this.indicatorColor, volumePanelRadioButtonBarColors.indicatorColor) && Color.m386equalsimpl0(this.indicatorBackgroundColor, volumePanelRadioButtonBarColors.indicatorBackgroundColor) && Color.m386equalsimpl0(this.iconColor, volumePanelRadioButtonBarColors.iconColor) && Color.m386equalsimpl0(this.selectedIconColor, volumePanelRadioButtonBarColors.selectedIconColor) && Color.m386equalsimpl0(this.labelColor, volumePanelRadioButtonBarColors.labelColor) && Color.m386equalsimpl0(this.selectedLabelColor, volumePanelRadioButtonBarColors.selectedLabelColor);
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        return Long.hashCode(this.selectedLabelColor) + Scale$$ExternalSyntheticOutline0.m(Scale$$ExternalSyntheticOutline0.m(Scale$$ExternalSyntheticOutline0.m(Scale$$ExternalSyntheticOutline0.m(Long.hashCode(this.indicatorColor) * 31, 31, this.indicatorBackgroundColor), 31, this.iconColor), 31, this.selectedIconColor), 31, this.labelColor);
    }

    public final String toString() {
        String m392toStringimpl = Color.m392toStringimpl(this.indicatorColor);
        String m392toStringimpl2 = Color.m392toStringimpl(this.indicatorBackgroundColor);
        String m392toStringimpl3 = Color.m392toStringimpl(this.iconColor);
        String m392toStringimpl4 = Color.m392toStringimpl(this.selectedIconColor);
        String m392toStringimpl5 = Color.m392toStringimpl(this.labelColor);
        String m392toStringimpl6 = Color.m392toStringimpl(this.selectedLabelColor);
        StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("VolumePanelRadioButtonBarColors(indicatorColor=", m392toStringimpl, ", indicatorBackgroundColor=", m392toStringimpl2, ", iconColor=");
        ConstraintWidget$$ExternalSyntheticOutline0.m(m, m392toStringimpl3, ", selectedIconColor=", m392toStringimpl4, ", labelColor=");
        m.append(m392toStringimpl5);
        m.append(", selectedLabelColor=");
        m.append(m392toStringimpl6);
        m.append(")");
        return m.toString();
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

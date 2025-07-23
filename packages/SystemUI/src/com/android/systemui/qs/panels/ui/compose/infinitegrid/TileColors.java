package com.android.systemui.qs.panels.ui.compose.infinitegrid;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.foundation.shape.DpCornerSize$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.Color;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class TileColors {
    public final float alpha;
    public final long background;
    public final long icon;
    public final long iconBackground;
    public final long label;
    public final long secondaryLabel;

    public /* synthetic */ TileColors(long j, long j2, long j3, long j4, long j5, float f, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, j3, j4, j5, f);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TileColors)) {
            return false;
        }
        TileColors tileColors = (TileColors) obj;
        long j = tileColors.background;
        Color.Companion companion = Color.Companion;
        return ULong.m3427equalsimpl0(this.background, j) && ULong.m3427equalsimpl0(this.iconBackground, tileColors.iconBackground) && ULong.m3427equalsimpl0(this.label, tileColors.label) && ULong.m3427equalsimpl0(this.secondaryLabel, tileColors.secondaryLabel) && ULong.m3427equalsimpl0(this.icon, tileColors.icon) && Float.compare(this.alpha, tileColors.alpha) == 0;
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        return Float.hashCode(this.alpha) + MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(Long.hashCode(this.background) * 31, 31, this.iconBackground), 31, this.label), 31, this.secondaryLabel), 31, this.icon);
    }

    public final String toString() {
        String m462toStringimpl = Color.m462toStringimpl(this.background);
        String m462toStringimpl2 = Color.m462toStringimpl(this.iconBackground);
        String m462toStringimpl3 = Color.m462toStringimpl(this.label);
        String m462toStringimpl4 = Color.m462toStringimpl(this.secondaryLabel);
        String m462toStringimpl5 = Color.m462toStringimpl(this.icon);
        StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("TileColors(background=", m462toStringimpl, ", iconBackground=", m462toStringimpl2, ", label=");
        MoveResult$$ExternalSyntheticOutline0.m(m, m462toStringimpl3, ", secondaryLabel=", m462toStringimpl4, ", icon=");
        m.append(m462toStringimpl5);
        m.append(", alpha=");
        return DpCornerSize$$ExternalSyntheticOutline0.m(this.alpha, ")", m);
    }

    private TileColors(long j, long j2, long j3, long j4, long j5, float f) {
        this.background = j;
        this.iconBackground = j2;
        this.label = j3;
        this.secondaryLabel = j4;
        this.icon = j5;
        this.alpha = f;
    }

    public /* synthetic */ TileColors(long j, long j2, long j3, long j4, long j5, float f, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, j3, j4, j5, (i & 32) != 0 ? 1.0f : f, null);
    }
}

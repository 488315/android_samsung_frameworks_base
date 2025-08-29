package com.samsung.sesl.compose.component;

import androidx.compose.ui.graphics.Color;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class SeslTooltipColor {
    public final long containerColor;
    public final long contentColor;

    public /* synthetic */ SeslTooltipColor(long j, long j2, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SeslTooltipColor)) {
            return false;
        }
        SeslTooltipColor seslTooltipColor = (SeslTooltipColor) obj;
        long j = seslTooltipColor.containerColor;
        Color.Companion companion = Color.Companion;
        return ULong.m3446equalsimpl0(this.containerColor, j) && ULong.m3446equalsimpl0(this.contentColor, seslTooltipColor.contentColor);
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        return Long.hashCode(this.contentColor) + (Long.hashCode(this.containerColor) * 31);
    }

    public final String toString() {
        return MotionLayout$$ExternalSyntheticOutline0.m("SeslTooltipColor(containerColor=", Color.m464toStringimpl(this.containerColor), ", contentColor=", Color.m464toStringimpl(this.contentColor), ")");
    }

    private SeslTooltipColor(long j, long j2) {
        this.containerColor = j;
        this.contentColor = j2;
    }
}

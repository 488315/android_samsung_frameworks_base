package com.samsung.sesl.compose.foundation;

import androidx.compose.ui.graphics.Color;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class SeslBasicCheckboxColors {
    public final long checkColor;
    public final long circleStrokeColor;

    public /* synthetic */ SeslBasicCheckboxColors(long j, long j2, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SeslBasicCheckboxColors)) {
            return false;
        }
        SeslBasicCheckboxColors seslBasicCheckboxColors = (SeslBasicCheckboxColors) obj;
        long j = seslBasicCheckboxColors.checkColor;
        Color.Companion companion = Color.Companion;
        return ULong.m3447equalsimpl0(this.checkColor, j) && ULong.m3447equalsimpl0(this.circleStrokeColor, seslBasicCheckboxColors.circleStrokeColor);
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        return Long.hashCode(this.circleStrokeColor) + (Long.hashCode(this.checkColor) * 31);
    }

    public final String toString() {
        return MotionLayout$$ExternalSyntheticOutline0.m("SeslBasicCheckboxColors(checkColor=", Color.m464toStringimpl(this.checkColor), ", circleStrokeColor=", Color.m464toStringimpl(this.circleStrokeColor), ")");
    }

    private SeslBasicCheckboxColors(long j, long j2) {
        this.checkColor = j;
        this.circleStrokeColor = j2;
    }
}

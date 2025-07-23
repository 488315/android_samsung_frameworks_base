package com.samsung.sesl.compose.foundation;

import androidx.compose.ui.graphics.Color;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        return ULong.m3427equalsimpl0(this.checkColor, j) && ULong.m3427equalsimpl0(this.circleStrokeColor, seslBasicCheckboxColors.circleStrokeColor);
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        return Long.hashCode(this.circleStrokeColor) + (Long.hashCode(this.checkColor) * 31);
    }

    public final String toString() {
        return MotionLayout$$ExternalSyntheticOutline0.m("SeslBasicCheckboxColors(checkColor=", Color.m462toStringimpl(this.checkColor), ", circleStrokeColor=", Color.m462toStringimpl(this.circleStrokeColor), ")");
    }

    private SeslBasicCheckboxColors(long j, long j2) {
        this.checkColor = j;
        this.circleStrokeColor = j2;
    }
}

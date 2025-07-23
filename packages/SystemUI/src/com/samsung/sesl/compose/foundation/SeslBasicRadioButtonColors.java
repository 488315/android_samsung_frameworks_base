package com.samsung.sesl.compose.foundation;

import androidx.compose.ui.graphics.Color;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SeslBasicRadioButtonColors {
    public final long checkedColor;
    public final long uncheckedColor;

    public /* synthetic */ SeslBasicRadioButtonColors(long j, long j2, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SeslBasicRadioButtonColors)) {
            return false;
        }
        SeslBasicRadioButtonColors seslBasicRadioButtonColors = (SeslBasicRadioButtonColors) obj;
        long j = seslBasicRadioButtonColors.checkedColor;
        Color.Companion companion = Color.Companion;
        return ULong.m3427equalsimpl0(this.checkedColor, j) && ULong.m3427equalsimpl0(this.uncheckedColor, seslBasicRadioButtonColors.uncheckedColor);
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        return Long.hashCode(this.uncheckedColor) + (Long.hashCode(this.checkedColor) * 31);
    }

    public final String toString() {
        return MotionLayout$$ExternalSyntheticOutline0.m("SeslBasicRadioButtonColors(checkedColor=", Color.m462toStringimpl(this.checkedColor), ", uncheckedColor=", Color.m462toStringimpl(this.uncheckedColor), ")");
    }

    private SeslBasicRadioButtonColors(long j, long j2) {
        this.checkedColor = j;
        this.uncheckedColor = j2;
    }
}

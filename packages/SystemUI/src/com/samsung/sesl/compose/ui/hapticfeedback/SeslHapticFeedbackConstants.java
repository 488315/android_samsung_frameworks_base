package com.samsung.sesl.compose.ui.hapticfeedback;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SeslHapticFeedbackConstants {
    public final Function1 canHapticFeedback;
    public final int effectSwitch;

    public SeslHapticFeedbackConstants(Function1 function1, int i) {
        this.canHapticFeedback = function1;
        this.effectSwitch = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SeslHapticFeedbackConstants)) {
            return false;
        }
        SeslHapticFeedbackConstants seslHapticFeedbackConstants = (SeslHapticFeedbackConstants) obj;
        return Intrinsics.areEqual(this.canHapticFeedback, seslHapticFeedbackConstants.canHapticFeedback) && this.effectSwitch == seslHapticFeedbackConstants.effectSwitch;
    }

    public final int hashCode() {
        return Integer.hashCode(this.effectSwitch) + (this.canHapticFeedback.hashCode() * 31);
    }

    public final String toString() {
        return "SeslHapticFeedbackConstants(canHapticFeedback=" + this.canHapticFeedback + ", effectSwitch=" + this.effectSwitch + ")";
    }
}

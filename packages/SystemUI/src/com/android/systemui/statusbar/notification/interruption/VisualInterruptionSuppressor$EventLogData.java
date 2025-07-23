package com.android.systemui.statusbar.notification.interruption;

import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class VisualInterruptionSuppressor$EventLogData {
    public final String description;
    public final String number;

    public VisualInterruptionSuppressor$EventLogData(String str, String str2) {
        this.number = str;
        this.description = str2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof VisualInterruptionSuppressor$EventLogData)) {
            return false;
        }
        VisualInterruptionSuppressor$EventLogData visualInterruptionSuppressor$EventLogData = (VisualInterruptionSuppressor$EventLogData) obj;
        return Intrinsics.areEqual(this.number, visualInterruptionSuppressor$EventLogData.number) && Intrinsics.areEqual(this.description, visualInterruptionSuppressor$EventLogData.description);
    }

    public final int hashCode() {
        return this.description.hashCode() + (this.number.hashCode() * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("EventLogData(number=");
        sb.append(this.number);
        sb.append(", description=");
        return TransitionKt$$ExternalSyntheticOutline0.m(sb, this.description, ")");
    }
}

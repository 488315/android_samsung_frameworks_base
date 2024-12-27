package com.android.systemui.statusbar.notification.interruption;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

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
        return ComponentActivity$1$$ExternalSyntheticOutline0.m(sb, this.description, ")");
    }
}

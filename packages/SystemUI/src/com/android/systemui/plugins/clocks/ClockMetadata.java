package com.android.systemui.plugins.clocks;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

public final class ClockMetadata {
    private final String clockId;

    public ClockMetadata(String str) {
        this.clockId = str;
    }

    public static /* synthetic */ ClockMetadata copy$default(ClockMetadata clockMetadata, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            str = clockMetadata.clockId;
        }
        return clockMetadata.copy(str);
    }

    public final String component1() {
        return this.clockId;
    }

    public final ClockMetadata copy(String str) {
        return new ClockMetadata(str);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof ClockMetadata) && Intrinsics.areEqual(this.clockId, ((ClockMetadata) obj).clockId);
    }

    public final String getClockId() {
        return this.clockId;
    }

    public int hashCode() {
        return this.clockId.hashCode();
    }

    public String toString() {
        return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("ClockMetadata(clockId=", this.clockId, ")");
    }
}

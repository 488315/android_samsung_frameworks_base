package com.android.systemui.plugins;

import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ClockMetadata {
    private final String clockId;
    private final String name;

    public ClockMetadata(String str, String str2) {
        this.clockId = str;
        this.name = str2;
    }

    public static /* synthetic */ ClockMetadata copy$default(ClockMetadata clockMetadata, String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = clockMetadata.clockId;
        }
        if ((i & 2) != 0) {
            str2 = clockMetadata.name;
        }
        return clockMetadata.copy(str, str2);
    }

    public final String component1() {
        return this.clockId;
    }

    public final String component2() {
        return this.name;
    }

    public final ClockMetadata copy(String str, String str2) {
        return new ClockMetadata(str, str2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ClockMetadata)) {
            return false;
        }
        ClockMetadata clockMetadata = (ClockMetadata) obj;
        return Intrinsics.areEqual(this.clockId, clockMetadata.clockId) && Intrinsics.areEqual(this.name, clockMetadata.name);
    }

    public final String getClockId() {
        return this.clockId;
    }

    public final String getName() {
        return this.name;
    }

    public int hashCode() {
        return this.name.hashCode() + (this.clockId.hashCode() * 31);
    }

    public String toString() {
        return MotionLayout$$ExternalSyntheticOutline0.m22m("ClockMetadata(clockId=", this.clockId, ", name=", this.name, ")");
    }

    public ClockMetadata(String str) {
        this(str, str);
    }
}

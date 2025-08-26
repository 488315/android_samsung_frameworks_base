package com.android.systemui.plugins.clocks;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.CarrierTextManagerLogger$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class ClockMetadata {
    public static final int $stable = 0;
    private final String clockId;
    private final boolean isDeprecated;
    private final String replacementTarget;

    public ClockMetadata(String str, boolean z, String str2) {
        this.clockId = str;
        this.isDeprecated = z;
        this.replacementTarget = str2;
    }

    public static /* synthetic */ ClockMetadata copy$default(ClockMetadata clockMetadata, String str, boolean z, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = clockMetadata.clockId;
        }
        if ((i & 2) != 0) {
            z = clockMetadata.isDeprecated;
        }
        if ((i & 4) != 0) {
            str2 = clockMetadata.replacementTarget;
        }
        return clockMetadata.copy(str, z, str2);
    }

    public final String component1() {
        return this.clockId;
    }

    public final boolean component2() {
        return this.isDeprecated;
    }

    public final String component3() {
        return this.replacementTarget;
    }

    public final ClockMetadata copy(String str, boolean z, String str2) {
        return new ClockMetadata(str, z, str2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ClockMetadata)) {
            return false;
        }
        ClockMetadata clockMetadata = (ClockMetadata) obj;
        return Intrinsics.areEqual(this.clockId, clockMetadata.clockId) && this.isDeprecated == clockMetadata.isDeprecated && Intrinsics.areEqual(this.replacementTarget, clockMetadata.replacementTarget);
    }

    public final String getClockId() {
        return this.clockId;
    }

    public final String getReplacementTarget() {
        return this.replacementTarget;
    }

    public int hashCode() {
        int iM = TransitionData$$ExternalSyntheticOutline0.m(this.clockId.hashCode() * 31, 31, this.isDeprecated);
        String str = this.replacementTarget;
        return iM + (str == null ? 0 : str.hashCode());
    }

    public final boolean isDeprecated() {
        return this.isDeprecated;
    }

    public String toString() {
        String str = this.clockId;
        boolean z = this.isDeprecated;
        return TransitionKt$$ExternalSyntheticOutline0.m(CarrierTextManagerLogger$$ExternalSyntheticOutline0.m("ClockMetadata(clockId=", str, ", isDeprecated=", ", replacementTarget=", z), this.replacementTarget, ")");
    }

    public /* synthetic */ ClockMetadata(String str, boolean z, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? false : z, (i & 4) != 0 ? null : str2);
    }
}

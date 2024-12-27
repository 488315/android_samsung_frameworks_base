package com.android.systemui.flags;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class FlagToken {
    public final boolean isEnabled;
    public final String name;

    public FlagToken(String str, boolean z) {
        this.name = str;
        this.isEnabled = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FlagToken)) {
            return false;
        }
        FlagToken flagToken = (FlagToken) obj;
        return Intrinsics.areEqual(this.name, flagToken.name) && this.isEnabled == flagToken.isEnabled;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.isEnabled) + (this.name.hashCode() * 31);
    }

    public final String toString() {
        return this.name + " (" + (this.isEnabled ? "enabled" : "disabled") + ")";
    }
}

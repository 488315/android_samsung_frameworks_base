package com.android.systemui.flags;

import com.android.systemui.bixby2.controller.NotificationController$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
        return NotificationController$$ExternalSyntheticOutline0.m(new StringBuilder(), this.name, " (", this.isEnabled ? "enabled" : "disabled", ")");
    }
}

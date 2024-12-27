package com.android.systemui.deviceentry.shared.model;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

public final class FingerprintLockoutMessage extends FingerprintMessage {
    public final String fingerprintLockoutMessage;

    public FingerprintLockoutMessage(String str) {
        super(str);
        this.fingerprintLockoutMessage = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof FingerprintLockoutMessage) && Intrinsics.areEqual(this.fingerprintLockoutMessage, ((FingerprintLockoutMessage) obj).fingerprintLockoutMessage);
    }

    public final int hashCode() {
        String str = this.fingerprintLockoutMessage;
        if (str == null) {
            return 0;
        }
        return str.hashCode();
    }

    public final String toString() {
        return ComponentActivity$1$$ExternalSyntheticOutline0.m(new StringBuilder("FingerprintLockoutMessage(fingerprintLockoutMessage="), this.fingerprintLockoutMessage, ")");
    }
}

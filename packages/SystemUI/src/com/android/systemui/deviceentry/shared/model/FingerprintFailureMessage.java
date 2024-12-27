package com.android.systemui.deviceentry.shared.model;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

public final class FingerprintFailureMessage extends FingerprintMessage {
    public final String msg;

    public FingerprintFailureMessage(String str) {
        super(str);
        this.msg = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof FingerprintFailureMessage) && Intrinsics.areEqual(this.msg, ((FingerprintFailureMessage) obj).msg);
    }

    public final int hashCode() {
        String str = this.msg;
        if (str == null) {
            return 0;
        }
        return str.hashCode();
    }

    public final String toString() {
        return ComponentActivity$1$$ExternalSyntheticOutline0.m(new StringBuilder("FingerprintFailureMessage(msg="), this.msg, ")");
    }
}

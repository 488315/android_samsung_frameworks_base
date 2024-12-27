package com.android.systemui.deviceentry.shared.model;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class FaceFailureMessage extends FaceMessage {
    public final String msg;

    public FaceFailureMessage(String str) {
        super(str);
        this.msg = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof FaceFailureMessage) && Intrinsics.areEqual(this.msg, ((FaceFailureMessage) obj).msg);
    }

    public final int hashCode() {
        return this.msg.hashCode();
    }

    public final String toString() {
        return ComponentActivity$1$$ExternalSyntheticOutline0.m(new StringBuilder("FaceFailureMessage(msg="), this.msg, ")");
    }
}

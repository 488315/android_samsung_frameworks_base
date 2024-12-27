package com.android.systemui.deviceentry.shared.model;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class FaceTimeoutMessage extends FaceMessage {
    public final String faceTimeoutMessage;

    public FaceTimeoutMessage(String str) {
        super(str);
        this.faceTimeoutMessage = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof FaceTimeoutMessage) && Intrinsics.areEqual(this.faceTimeoutMessage, ((FaceTimeoutMessage) obj).faceTimeoutMessage);
    }

    public final int hashCode() {
        String str = this.faceTimeoutMessage;
        if (str == null) {
            return 0;
        }
        return str.hashCode();
    }

    public final String toString() {
        return ComponentActivity$1$$ExternalSyntheticOutline0.m(new StringBuilder("FaceTimeoutMessage(faceTimeoutMessage="), this.faceTimeoutMessage, ")");
    }
}

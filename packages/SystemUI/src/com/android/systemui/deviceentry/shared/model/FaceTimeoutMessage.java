package com.android.systemui.deviceentry.shared.model;

import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        return TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder("FaceTimeoutMessage(faceTimeoutMessage="), this.faceTimeoutMessage, ")");
    }
}

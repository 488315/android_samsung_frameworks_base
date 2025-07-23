package com.android.systemui.deviceentry.shared.model;

import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
        return TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder("FaceFailureMessage(msg="), this.msg, ")");
    }
}

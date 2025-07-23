package com.android.systemui.deviceentry.shared.model;

import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class FaceLockoutMessage extends FaceMessage {
    public final String msg;

    public FaceLockoutMessage(String str) {
        super(str);
        this.msg = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof FaceLockoutMessage) && Intrinsics.areEqual(this.msg, ((FaceLockoutMessage) obj).msg);
    }

    public final int hashCode() {
        String str = this.msg;
        if (str == null) {
            return 0;
        }
        return str.hashCode();
    }

    public final String toString() {
        return TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder("FaceLockoutMessage(msg="), this.msg, ")");
    }
}

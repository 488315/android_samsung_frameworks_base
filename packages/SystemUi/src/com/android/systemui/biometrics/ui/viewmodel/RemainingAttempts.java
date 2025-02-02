package com.android.systemui.biometrics.ui.viewmodel;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class RemainingAttempts {
    public final String message;
    public final Integer remaining;

    /* JADX WARN: Multi-variable type inference failed */
    public RemainingAttempts() {
        this(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RemainingAttempts)) {
            return false;
        }
        RemainingAttempts remainingAttempts = (RemainingAttempts) obj;
        return Intrinsics.areEqual(this.remaining, remainingAttempts.remaining) && Intrinsics.areEqual(this.message, remainingAttempts.message);
    }

    public final int hashCode() {
        Integer num = this.remaining;
        return this.message.hashCode() + ((num == null ? 0 : num.hashCode()) * 31);
    }

    public final String toString() {
        return "RemainingAttempts(remaining=" + this.remaining + ", message=" + this.message + ")";
    }

    public RemainingAttempts(Integer num, String str) {
        this.remaining = num;
        this.message = str;
    }

    public /* synthetic */ RemainingAttempts(Integer num, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : num, (i & 2) != 0 ? "" : str);
    }
}

package com.android.systemui.keyguard.shared.model;

import android.animation.ValueAnimator;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class TransitionInfo {
    public final ValueAnimator animator;
    public final KeyguardState from;
    public final TransitionModeOnCanceled modeOnCanceled;
    public final String ownerName;
    public final KeyguardState to;

    public TransitionInfo(String str, KeyguardState keyguardState, KeyguardState keyguardState2, ValueAnimator valueAnimator, TransitionModeOnCanceled transitionModeOnCanceled) {
        this.ownerName = str;
        this.from = keyguardState;
        this.to = keyguardState2;
        this.animator = valueAnimator;
        this.modeOnCanceled = transitionModeOnCanceled;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TransitionInfo)) {
            return false;
        }
        TransitionInfo transitionInfo = (TransitionInfo) obj;
        return Intrinsics.areEqual(this.ownerName, transitionInfo.ownerName) && this.from == transitionInfo.from && this.to == transitionInfo.to && Intrinsics.areEqual(this.animator, transitionInfo.animator) && this.modeOnCanceled == transitionInfo.modeOnCanceled;
    }

    public final int hashCode() {
        int hashCode = (this.to.hashCode() + ((this.from.hashCode() + (this.ownerName.hashCode() * 31)) * 31)) * 31;
        ValueAnimator valueAnimator = this.animator;
        return this.modeOnCanceled.hashCode() + ((hashCode + (valueAnimator == null ? 0 : valueAnimator.hashCode())) * 31);
    }

    public final String toString() {
        return "TransitionInfo(ownerName=" + this.ownerName + ", from=" + this.from + ", to=" + this.to + ", " + (this.animator != null ? "animated" : "manual") + ")";
    }

    public /* synthetic */ TransitionInfo(String str, KeyguardState keyguardState, KeyguardState keyguardState2, ValueAnimator valueAnimator, TransitionModeOnCanceled transitionModeOnCanceled, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, keyguardState, keyguardState2, valueAnimator, (i & 16) != 0 ? TransitionModeOnCanceled.LAST_VALUE : transitionModeOnCanceled);
    }
}

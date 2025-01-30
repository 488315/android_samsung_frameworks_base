package com.android.systemui.keyguard.shared.model;

import android.animation.ValueAnimator;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class TransitionInfo {
    public final ValueAnimator animator;
    public final KeyguardState from;
    public final String ownerName;

    /* renamed from: to */
    public final KeyguardState f303to;

    public TransitionInfo(String str, KeyguardState keyguardState, KeyguardState keyguardState2, ValueAnimator valueAnimator) {
        this.ownerName = str;
        this.from = keyguardState;
        this.f303to = keyguardState2;
        this.animator = valueAnimator;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TransitionInfo)) {
            return false;
        }
        TransitionInfo transitionInfo = (TransitionInfo) obj;
        return Intrinsics.areEqual(this.ownerName, transitionInfo.ownerName) && this.from == transitionInfo.from && this.f303to == transitionInfo.f303to && Intrinsics.areEqual(this.animator, transitionInfo.animator);
    }

    public final int hashCode() {
        int hashCode = (this.f303to.hashCode() + ((this.from.hashCode() + (this.ownerName.hashCode() * 31)) * 31)) * 31;
        ValueAnimator valueAnimator = this.animator;
        return hashCode + (valueAnimator == null ? 0 : valueAnimator.hashCode());
    }

    public final String toString() {
        return "TransitionInfo(ownerName=" + this.ownerName + ", from=" + this.from + ", to=" + this.f303to + ", " + (this.animator != null ? "animated" : "manual") + ")";
    }
}

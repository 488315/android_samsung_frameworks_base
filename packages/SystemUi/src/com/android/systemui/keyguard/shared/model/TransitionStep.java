package com.android.systemui.keyguard.shared.model;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.settingslib.udfps.UdfpsOverlayParams$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class TransitionStep {
    public final KeyguardState from;
    public final String ownerName;

    /* renamed from: to */
    public final KeyguardState f304to;
    public final TransitionState transitionState;
    public final float value;

    public TransitionStep() {
        this(null, null, 0.0f, null, null, 31, null);
    }

    public static TransitionStep copy$default(TransitionStep transitionStep, float f, TransitionState transitionState, int i) {
        KeyguardState keyguardState = (i & 1) != 0 ? transitionStep.from : null;
        KeyguardState keyguardState2 = (i & 2) != 0 ? transitionStep.f304to : null;
        if ((i & 4) != 0) {
            f = transitionStep.value;
        }
        float f2 = f;
        if ((i & 8) != 0) {
            transitionState = transitionStep.transitionState;
        }
        TransitionState transitionState2 = transitionState;
        String str = (i & 16) != 0 ? transitionStep.ownerName : null;
        transitionStep.getClass();
        return new TransitionStep(keyguardState, keyguardState2, f2, transitionState2, str);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TransitionStep)) {
            return false;
        }
        TransitionStep transitionStep = (TransitionStep) obj;
        return this.from == transitionStep.from && this.f304to == transitionStep.f304to && Float.compare(this.value, transitionStep.value) == 0 && this.transitionState == transitionStep.transitionState && Intrinsics.areEqual(this.ownerName, transitionStep.ownerName);
    }

    public final int hashCode() {
        return this.ownerName.hashCode() + ((this.transitionState.hashCode() + UdfpsOverlayParams$$ExternalSyntheticOutline0.m90m(this.value, (this.f304to.hashCode() + (this.from.hashCode() * 31)) * 31, 31)) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("TransitionStep(from=");
        sb.append(this.from);
        sb.append(", to=");
        sb.append(this.f304to);
        sb.append(", value=");
        sb.append(this.value);
        sb.append(", transitionState=");
        sb.append(this.transitionState);
        sb.append(", ownerName=");
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(sb, this.ownerName, ")");
    }

    public TransitionStep(KeyguardState keyguardState, KeyguardState keyguardState2, float f, TransitionState transitionState, String str) {
        this.from = keyguardState;
        this.f304to = keyguardState2;
        this.value = f;
        this.transitionState = transitionState;
        this.ownerName = str;
    }

    public /* synthetic */ TransitionStep(KeyguardState keyguardState, KeyguardState keyguardState2, float f, TransitionState transitionState, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? KeyguardState.OFF : keyguardState, (i & 2) != 0 ? KeyguardState.OFF : keyguardState2, (i & 4) != 0 ? 0.0f : f, (i & 8) != 0 ? TransitionState.FINISHED : transitionState, (i & 16) != 0 ? "" : str);
    }

    public TransitionStep(TransitionInfo transitionInfo, float f, TransitionState transitionState) {
        this(transitionInfo.from, transitionInfo.f303to, f, transitionState, transitionInfo.ownerName);
    }
}

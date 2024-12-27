package com.android.systemui.keyguard.shared.model;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class TransitionStep {
    public final KeyguardState from;
    public final String ownerName;
    public final KeyguardState to;
    public final TransitionState transitionState;
    public final float value;

    public TransitionStep() {
        this(null, null, 0.0f, null, null, 31, null);
    }

    public static TransitionStep copy$default(TransitionStep transitionStep, float f, TransitionState transitionState, int i) {
        KeyguardState keyguardState = transitionStep.from;
        KeyguardState keyguardState2 = transitionStep.to;
        if ((i & 8) != 0) {
            transitionState = transitionStep.transitionState;
        }
        String str = transitionStep.ownerName;
        transitionStep.getClass();
        return new TransitionStep(keyguardState, keyguardState2, f, transitionState, str);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TransitionStep)) {
            return false;
        }
        TransitionStep transitionStep = (TransitionStep) obj;
        return this.from == transitionStep.from && this.to == transitionStep.to && Float.compare(this.value, transitionStep.value) == 0 && this.transitionState == transitionStep.transitionState && Intrinsics.areEqual(this.ownerName, transitionStep.ownerName);
    }

    public final int hashCode() {
        return this.ownerName.hashCode() + ((this.transitionState.hashCode() + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.value, (this.to.hashCode() + (this.from.hashCode() * 31)) * 31, 31)) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("TransitionStep(from=");
        sb.append(this.from);
        sb.append(", to=");
        sb.append(this.to);
        sb.append(", value=");
        sb.append(this.value);
        sb.append(", transitionState=");
        sb.append(this.transitionState);
        sb.append(", ownerName=");
        return ComponentActivity$1$$ExternalSyntheticOutline0.m(sb, this.ownerName, ")");
    }

    public TransitionStep(KeyguardState keyguardState) {
        this(keyguardState, null, 0.0f, null, null, 30, null);
    }

    public TransitionStep(KeyguardState keyguardState, KeyguardState keyguardState2) {
        this(keyguardState, keyguardState2, 0.0f, null, null, 28, null);
    }

    public TransitionStep(KeyguardState keyguardState, KeyguardState keyguardState2, float f) {
        this(keyguardState, keyguardState2, f, null, null, 24, null);
    }

    public TransitionStep(KeyguardState keyguardState, KeyguardState keyguardState2, float f, TransitionState transitionState) {
        this(keyguardState, keyguardState2, f, transitionState, null, 16, null);
    }

    public TransitionStep(KeyguardState keyguardState, KeyguardState keyguardState2, float f, TransitionState transitionState, String str) {
        this.from = keyguardState;
        this.to = keyguardState2;
        this.value = f;
        this.transitionState = transitionState;
        this.ownerName = str;
    }

    public /* synthetic */ TransitionStep(KeyguardState keyguardState, KeyguardState keyguardState2, float f, TransitionState transitionState, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? KeyguardState.OFF : keyguardState, (i & 2) != 0 ? KeyguardState.OFF : keyguardState2, (i & 4) != 0 ? 0.0f : f, (i & 8) != 0 ? TransitionState.FINISHED : transitionState, (i & 16) != 0 ? "" : str);
    }

    public TransitionStep(TransitionInfo transitionInfo, float f, TransitionState transitionState) {
        this(transitionInfo.from, transitionInfo.to, f, transitionState, transitionInfo.ownerName);
    }
}

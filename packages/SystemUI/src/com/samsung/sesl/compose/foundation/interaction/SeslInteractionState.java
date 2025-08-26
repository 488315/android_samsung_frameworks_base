package com.samsung.sesl.compose.foundation.interaction;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class SeslInteractionState {
    public static final Companion Companion = new Companion(null);
    public static final SeslInteractionState None = new SeslInteractionState(false, false, false, false);
    public final boolean dragged;
    public final boolean focused;
    public final boolean hovered;
    public final boolean pressed;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public SeslInteractionState(boolean z, boolean z2, boolean z3, boolean z4) {
        this.pressed = z;
        this.focused = z2;
        this.hovered = z3;
        this.dragged = z4;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SeslInteractionState)) {
            return false;
        }
        SeslInteractionState seslInteractionState = (SeslInteractionState) obj;
        return this.pressed == seslInteractionState.pressed && this.focused == seslInteractionState.focused && this.hovered == seslInteractionState.hovered && this.dragged == seslInteractionState.dragged;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.dragged) + TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(Boolean.hashCode(this.pressed) * 31, 31, this.focused), 31, this.hovered);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("SeslInteractionState(pressed=");
        sb.append(this.pressed);
        sb.append(", focused=");
        sb.append(this.focused);
        sb.append(", hovered=");
        sb.append(this.hovered);
        sb.append(", dragged=");
        return MoveResult$$ExternalSyntheticOutline0.m(sb, this.dragged, ")");
    }

    public /* synthetic */ SeslInteractionState(boolean z, boolean z2, boolean z3, boolean z4, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, z2, z3, (i & 8) != 0 ? false : z4);
    }
}

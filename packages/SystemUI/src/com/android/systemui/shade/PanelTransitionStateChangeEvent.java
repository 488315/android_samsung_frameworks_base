package com.android.systemui.shade;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class PanelTransitionStateChangeEvent {
    public final boolean enabled;
    public final float fraction;
    public final int state;

    public PanelTransitionStateChangeEvent(boolean z, float f, int i) {
        this.enabled = z;
        this.fraction = f;
        this.state = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PanelTransitionStateChangeEvent)) {
            return false;
        }
        PanelTransitionStateChangeEvent panelTransitionStateChangeEvent = (PanelTransitionStateChangeEvent) obj;
        return this.enabled == panelTransitionStateChangeEvent.enabled && Float.compare(this.fraction, panelTransitionStateChangeEvent.fraction) == 0 && this.state == panelTransitionStateChangeEvent.state;
    }

    public final int hashCode() {
        return Integer.hashCode(this.state) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.fraction, Boolean.hashCode(this.enabled) * 31, 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("PanelTransitionStateChangeEvent(enabled=");
        sb.append(this.enabled);
        sb.append(", fraction=");
        sb.append(this.fraction);
        sb.append(", state=");
        return Anchor$$ExternalSyntheticOutline0.m(this.state, ")", sb);
    }
}

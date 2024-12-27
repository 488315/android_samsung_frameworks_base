package com.android.systemui.keyguard.shared.model;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.foundation.shape.DpCornerSize$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ScrimAlpha {
    public final float behindAlpha;
    public final float frontAlpha;
    public final float notificationsAlpha;

    public ScrimAlpha() {
        this(0.0f, 0.0f, 0.0f, 7, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ScrimAlpha)) {
            return false;
        }
        ScrimAlpha scrimAlpha = (ScrimAlpha) obj;
        return Float.compare(this.frontAlpha, scrimAlpha.frontAlpha) == 0 && Float.compare(this.behindAlpha, scrimAlpha.behindAlpha) == 0 && Float.compare(this.notificationsAlpha, scrimAlpha.notificationsAlpha) == 0;
    }

    public final int hashCode() {
        return Float.hashCode(this.notificationsAlpha) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.behindAlpha, Float.hashCode(this.frontAlpha) * 31, 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ScrimAlpha(frontAlpha=");
        sb.append(this.frontAlpha);
        sb.append(", behindAlpha=");
        sb.append(this.behindAlpha);
        sb.append(", notificationsAlpha=");
        return DpCornerSize$$ExternalSyntheticOutline0.m(sb, this.notificationsAlpha, ")");
    }

    public ScrimAlpha(float f, float f2, float f3) {
        this.frontAlpha = f;
        this.behindAlpha = f2;
        this.notificationsAlpha = f3;
    }

    public /* synthetic */ ScrimAlpha(float f, float f2, float f3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 0.0f : f, (i & 2) != 0 ? 0.0f : f2, (i & 4) != 0 ? 0.0f : f3);
    }
}

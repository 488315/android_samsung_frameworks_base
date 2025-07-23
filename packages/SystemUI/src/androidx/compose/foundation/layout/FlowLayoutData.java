package androidx.compose.foundation.layout;

import androidx.compose.animation.AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class FlowLayoutData {
    public final float fillCrossAxisFraction;

    public FlowLayoutData(float f) {
        this.fillCrossAxisFraction = f;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof FlowLayoutData) && Float.compare(this.fillCrossAxisFraction, ((FlowLayoutData) obj).fillCrossAxisFraction) == 0;
    }

    public final int hashCode() {
        return Float.hashCode(this.fillCrossAxisFraction);
    }

    public final String toString() {
        return AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0.m(new StringBuilder("FlowLayoutData(fillCrossAxisFraction="), this.fillCrossAxisFraction, ')');
    }
}

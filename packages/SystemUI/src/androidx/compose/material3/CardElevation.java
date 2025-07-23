package androidx.compose.material3;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.ui.unit.Dp;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class CardElevation {
    public final float defaultElevation;
    public final float disabledElevation;
    public final float focusedElevation;
    public final float hoveredElevation;
    public final float pressedElevation;

    public /* synthetic */ CardElevation(float f, float f2, float f3, float f4, float f5, float f6, DefaultConstructorMarker defaultConstructorMarker) {
        this(f, f2, f3, f4, f5, f6);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof CardElevation)) {
            return false;
        }
        CardElevation cardElevation = (CardElevation) obj;
        return Dp.m836equalsimpl0(this.defaultElevation, cardElevation.defaultElevation) && Dp.m836equalsimpl0(this.pressedElevation, cardElevation.pressedElevation) && Dp.m836equalsimpl0(this.focusedElevation, cardElevation.focusedElevation) && Dp.m836equalsimpl0(this.hoveredElevation, cardElevation.hoveredElevation) && Dp.m836equalsimpl0(this.disabledElevation, cardElevation.disabledElevation);
    }

    public final int hashCode() {
        Dp.Companion companion = Dp.Companion;
        return Float.hashCode(this.disabledElevation) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.hoveredElevation, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.focusedElevation, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.pressedElevation, Float.hashCode(this.defaultElevation) * 31, 31), 31), 31);
    }

    private CardElevation(float f, float f2, float f3, float f4, float f5, float f6) {
        this.defaultElevation = f;
        this.pressedElevation = f2;
        this.focusedElevation = f3;
        this.hoveredElevation = f4;
        this.disabledElevation = f6;
    }
}

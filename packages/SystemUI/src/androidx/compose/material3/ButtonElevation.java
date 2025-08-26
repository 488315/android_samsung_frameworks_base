package androidx.compose.material3;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.ui.unit.Dp;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class ButtonElevation {
    public final float defaultElevation;
    public final float disabledElevation;
    public final float focusedElevation;
    public final float hoveredElevation;
    public final float pressedElevation;

    public /* synthetic */ ButtonElevation(float f, float f2, float f3, float f4, float f5, DefaultConstructorMarker defaultConstructorMarker) {
        this(f, f2, f3, f4, f5);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof ButtonElevation)) {
            return false;
        }
        ButtonElevation buttonElevation = (ButtonElevation) obj;
        return Dp.m838equalsimpl0(this.defaultElevation, buttonElevation.defaultElevation) && Dp.m838equalsimpl0(this.pressedElevation, buttonElevation.pressedElevation) && Dp.m838equalsimpl0(this.focusedElevation, buttonElevation.focusedElevation) && Dp.m838equalsimpl0(this.hoveredElevation, buttonElevation.hoveredElevation) && Dp.m838equalsimpl0(this.disabledElevation, buttonElevation.disabledElevation);
    }

    public final int hashCode() {
        Dp.Companion companion = Dp.Companion;
        return Float.hashCode(this.disabledElevation) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.hoveredElevation, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.focusedElevation, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.pressedElevation, Float.hashCode(this.defaultElevation) * 31, 31), 31), 31);
    }

    private ButtonElevation(float f, float f2, float f3, float f4, float f5) {
        this.defaultElevation = f;
        this.pressedElevation = f2;
        this.focusedElevation = f3;
        this.hoveredElevation = f4;
        this.disabledElevation = f5;
    }
}

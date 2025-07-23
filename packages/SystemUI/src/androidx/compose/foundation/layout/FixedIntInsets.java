package androidx.compose.foundation.layout;

import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.LayoutDirection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class FixedIntInsets implements WindowInsets {
    public final int bottomVal;
    public final int leftVal;
    public final int rightVal;
    public final int topVal;

    public FixedIntInsets(int i, int i2, int i3, int i4) {
        this.leftVal = i;
        this.topVal = i2;
        this.rightVal = i3;
        this.bottomVal = i4;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FixedIntInsets)) {
            return false;
        }
        FixedIntInsets fixedIntInsets = (FixedIntInsets) obj;
        return this.leftVal == fixedIntInsets.leftVal && this.topVal == fixedIntInsets.topVal && this.rightVal == fixedIntInsets.rightVal && this.bottomVal == fixedIntInsets.bottomVal;
    }

    @Override // androidx.compose.foundation.layout.WindowInsets
    public final int getBottom(Density density) {
        return this.bottomVal;
    }

    @Override // androidx.compose.foundation.layout.WindowInsets
    public final int getLeft(Density density, LayoutDirection layoutDirection) {
        return this.leftVal;
    }

    @Override // androidx.compose.foundation.layout.WindowInsets
    public final int getRight(Density density, LayoutDirection layoutDirection) {
        return this.rightVal;
    }

    @Override // androidx.compose.foundation.layout.WindowInsets
    public final int getTop(Density density) {
        return this.topVal;
    }

    public final int hashCode() {
        return (((((this.leftVal * 31) + this.topVal) * 31) + this.rightVal) * 31) + this.bottomVal;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("Insets(left=");
        sb.append(this.leftVal);
        sb.append(", top=");
        sb.append(this.topVal);
        sb.append(", right=");
        sb.append(this.rightVal);
        sb.append(", bottom=");
        return BackEventCompat$$ExternalSyntheticOutline0.m(sb, this.bottomVal, ')');
    }
}

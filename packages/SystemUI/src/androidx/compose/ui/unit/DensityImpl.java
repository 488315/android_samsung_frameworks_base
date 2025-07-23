package androidx.compose.ui.unit;

import androidx.compose.animation.AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class DensityImpl implements Density {
    public final float density;
    public final float fontScale;

    public DensityImpl(float f, float f2) {
        this.density = f;
        this.fontScale = f2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DensityImpl)) {
            return false;
        }
        DensityImpl densityImpl = (DensityImpl) obj;
        return Float.compare(this.density, densityImpl.density) == 0 && Float.compare(this.fontScale, densityImpl.fontScale) == 0;
    }

    @Override // androidx.compose.ui.unit.Density
    public final float getDensity() {
        return this.density;
    }

    @Override // androidx.compose.ui.unit.FontScaling
    public final float getFontScale() {
        return this.fontScale;
    }

    public final int hashCode() {
        return Float.hashCode(this.fontScale) + (Float.hashCode(this.density) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("DensityImpl(density=");
        sb.append(this.density);
        sb.append(", fontScale=");
        return AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0.m(sb, this.fontScale, ')');
    }
}

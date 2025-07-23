package androidx.compose.ui.unit;

import androidx.compose.animation.AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0;
import androidx.compose.ui.unit.fontscaling.FontScaleConverter;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class LinearFontScaleConverter implements FontScaleConverter {
    public final float fontScale;

    public LinearFontScaleConverter(float f) {
        this.fontScale = f;
    }

    @Override // androidx.compose.ui.unit.fontscaling.FontScaleConverter
    public final float convertDpToSp(float f) {
        return f / this.fontScale;
    }

    @Override // androidx.compose.ui.unit.fontscaling.FontScaleConverter
    public final float convertSpToDp(float f) {
        return f * this.fontScale;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof LinearFontScaleConverter) && Float.compare(this.fontScale, ((LinearFontScaleConverter) obj).fontScale) == 0;
    }

    public final int hashCode() {
        return Float.hashCode(this.fontScale);
    }

    public final String toString() {
        return AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0.m(new StringBuilder("LinearFontScaleConverter(fontScale="), this.fontScale, ')');
    }
}

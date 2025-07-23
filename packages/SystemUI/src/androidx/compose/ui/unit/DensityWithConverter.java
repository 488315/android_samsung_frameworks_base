package androidx.compose.ui.unit;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.fontscaling.FontScaleConverter;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class DensityWithConverter implements Density {
    public final FontScaleConverter converter;
    public final float density;
    public final float fontScale;

    public DensityWithConverter(float f, float f2, FontScaleConverter fontScaleConverter) {
        this.density = f;
        this.fontScale = f2;
        this.converter = fontScaleConverter;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DensityWithConverter)) {
            return false;
        }
        DensityWithConverter densityWithConverter = (DensityWithConverter) obj;
        return Float.compare(this.density, densityWithConverter.density) == 0 && Float.compare(this.fontScale, densityWithConverter.fontScale) == 0 && Intrinsics.areEqual(this.converter, densityWithConverter.converter);
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
        return this.converter.hashCode() + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.fontScale, Float.hashCode(this.density) * 31, 31);
    }

    @Override // androidx.compose.ui.unit.FontScaling
    /* renamed from: toDp-GaN1DYA */
    public final float mo52toDpGaN1DYA(long j) {
        long m867getTypeUIouoOA = TextUnit.m867getTypeUIouoOA(j);
        TextUnitType.Companion.getClass();
        if (!TextUnitType.m874equalsimpl0(m867getTypeUIouoOA, TextUnitType.Sp)) {
            throw new IllegalStateException("Only Sp can convert to Px");
        }
        float convertSpToDp = this.converter.convertSpToDp(TextUnit.m868getValueimpl(j));
        Dp.Companion companion = Dp.Companion;
        return convertSpToDp;
    }

    @Override // androidx.compose.ui.unit.FontScaling
    /* renamed from: toSp-0xMU5do */
    public final long mo59toSp0xMU5do(float f) {
        return TextUnitKt.pack(this.converter.convertDpToSp(f), 4294967296L);
    }

    public final String toString() {
        return "DensityWithConverter(density=" + this.density + ", fontScale=" + this.fontScale + ", converter=" + this.converter + ')';
    }
}

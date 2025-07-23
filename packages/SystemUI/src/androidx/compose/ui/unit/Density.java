package androidx.compose.ui.unit;

import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.unit.Dp;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface Density extends FontScaling {
    float getDensity();

    /* renamed from: roundToPx--R2X_6o */
    default int mo647roundToPxR2X_6o(long j) {
        return Math.round(mo56toPxR2X_6o(j));
    }

    /* renamed from: roundToPx-0680j_4 */
    default int mo51roundToPx0680j_4(float f) {
        float mo57toPx0680j_4 = mo57toPx0680j_4(f);
        if (Float.isInfinite(mo57toPx0680j_4)) {
            return Integer.MAX_VALUE;
        }
        return Math.round(mo57toPx0680j_4);
    }

    /* renamed from: toDp-u2uoSUM */
    default float mo54toDpu2uoSUM(int i) {
        float density = i / getDensity();
        Dp.Companion companion = Dp.Companion;
        return density;
    }

    /* renamed from: toDpSize-k-rfVVM */
    default long mo55toDpSizekrfVVM(long j) {
        if (j != 9205357640488583168L) {
            return DpKt.m838DpSizeYgX7TsA(mo53toDpu2uoSUM(Float.intBitsToFloat((int) (j >> 32))), mo53toDpu2uoSUM(Float.intBitsToFloat((int) (j & 4294967295L))));
        }
        DpSize.Companion.getClass();
        return DpSize.Unspecified;
    }

    /* renamed from: toPx--R2X_6o */
    default float mo56toPxR2X_6o(long j) {
        long m867getTypeUIouoOA = TextUnit.m867getTypeUIouoOA(j);
        TextUnitType.Companion.getClass();
        if (TextUnitType.m874equalsimpl0(m867getTypeUIouoOA, TextUnitType.Sp)) {
            return mo57toPx0680j_4(mo52toDpGaN1DYA(j));
        }
        throw new IllegalStateException("Only Sp can convert to Px");
    }

    /* renamed from: toPx-0680j_4 */
    default float mo57toPx0680j_4(float f) {
        return getDensity() * f;
    }

    /* renamed from: toSize-XkaWNTQ */
    default long mo58toSizeXkaWNTQ(long j) {
        if (j == 9205357640488583168L) {
            Size.Companion.getClass();
            return Size.Unspecified;
        }
        float mo57toPx0680j_4 = mo57toPx0680j_4(DpSize.m845getWidthD9Ej5fM(j));
        float mo57toPx0680j_42 = mo57toPx0680j_4(DpSize.m844getHeightD9Ej5fM(j));
        long floatToRawIntBits = (Float.floatToRawIntBits(mo57toPx0680j_4) << 32) | (Float.floatToRawIntBits(mo57toPx0680j_42) & 4294967295L);
        Size.Companion companion = Size.Companion;
        return floatToRawIntBits;
    }

    /* renamed from: toSp-kPz2Gy4 */
    default long mo60toSpkPz2Gy4(float f) {
        return mo59toSp0xMU5do(mo53toDpu2uoSUM(f));
    }

    /* renamed from: toDp-u2uoSUM */
    default float mo53toDpu2uoSUM(float f) {
        float density = f / getDensity();
        Dp.Companion companion = Dp.Companion;
        return density;
    }
}

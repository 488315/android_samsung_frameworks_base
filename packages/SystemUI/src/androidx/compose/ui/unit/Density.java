package androidx.compose.ui.unit;

import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.unit.Dp;

/* loaded from: classes.dex */
public interface Density extends FontScaling {
    float getDensity();

    /* renamed from: roundToPx--R2X_6o */
    default int mo649roundToPxR2X_6o(long j) {
        return Math.round(mo57toPxR2X_6o(j));
    }

    /* renamed from: roundToPx-0680j_4 */
    default int mo52roundToPx0680j_4(float f) {
        float fMo58toPx0680j_4 = mo58toPx0680j_4(f);
        if (Float.isInfinite(fMo58toPx0680j_4)) {
            return Integer.MAX_VALUE;
        }
        return Math.round(fMo58toPx0680j_4);
    }

    /* renamed from: toDp-u2uoSUM */
    default float mo55toDpu2uoSUM(int i) {
        float density = i / getDensity();
        Dp.Companion companion = Dp.Companion;
        return density;
    }

    /* renamed from: toDpSize-k-rfVVM */
    default long mo56toDpSizekrfVVM(long j) {
        if (j != 9205357640488583168L) {
            return DpKt.m840DpSizeYgX7TsA(mo54toDpu2uoSUM(Float.intBitsToFloat((int) (j >> 32))), mo54toDpu2uoSUM(Float.intBitsToFloat((int) (j & 4294967295L))));
        }
        DpSize.Companion.getClass();
        return DpSize.Unspecified;
    }

    /* renamed from: toPx--R2X_6o */
    default float mo57toPxR2X_6o(long j) {
        long jM869getTypeUIouoOA = TextUnit.m869getTypeUIouoOA(j);
        TextUnitType.Companion.getClass();
        if (TextUnitType.m876equalsimpl0(jM869getTypeUIouoOA, TextUnitType.Sp)) {
            return mo58toPx0680j_4(mo53toDpGaN1DYA(j));
        }
        throw new IllegalStateException("Only Sp can convert to Px");
    }

    /* renamed from: toPx-0680j_4 */
    default float mo58toPx0680j_4(float f) {
        return getDensity() * f;
    }

    /* renamed from: toSize-XkaWNTQ */
    default long mo59toSizeXkaWNTQ(long j) {
        if (j == 9205357640488583168L) {
            Size.Companion.getClass();
            return Size.Unspecified;
        }
        float fMo58toPx0680j_4 = mo58toPx0680j_4(DpSize.m847getWidthD9Ej5fM(j));
        float fMo58toPx0680j_42 = mo58toPx0680j_4(DpSize.m846getHeightD9Ej5fM(j));
        long jFloatToRawIntBits = (Float.floatToRawIntBits(fMo58toPx0680j_4) << 32) | (Float.floatToRawIntBits(fMo58toPx0680j_42) & 4294967295L);
        Size.Companion companion = Size.Companion;
        return jFloatToRawIntBits;
    }

    /* renamed from: toSp-kPz2Gy4 */
    default long mo61toSpkPz2Gy4(float f) {
        return mo60toSp0xMU5do(mo54toDpu2uoSUM(f));
    }

    /* renamed from: toDp-u2uoSUM */
    default float mo54toDpu2uoSUM(float f) {
        float density = f / getDensity();
        Dp.Companion companion = Dp.Companion;
        return density;
    }
}

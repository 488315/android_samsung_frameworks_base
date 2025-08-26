package androidx.compose.ui.unit;

import androidx.compose.ui.unit.TextUnit;

/* loaded from: classes.dex */
public abstract class TextUnitKt {
    /* renamed from: checkArithmetic--R2X_6o, reason: not valid java name */
    public static final void m873checkArithmeticR2X_6o(long j) {
        TextUnit.Companion companion = TextUnit.Companion;
        if ((j & 1095216660480L) == 0) {
            InlineClassHelperKt.throwIllegalArgumentException("Cannot perform operation for Unspecified type.");
        }
    }

    /* renamed from: checkArithmetic-NB67dxo, reason: not valid java name */
    public static final void m874checkArithmeticNB67dxo(long j, long j2) {
        TextUnit.Companion companion = TextUnit.Companion;
        if (!(((j & 1095216660480L) == 0 || (1095216660480L & j2) == 0) ? false : true)) {
            InlineClassHelperKt.throwIllegalArgumentException("Cannot perform operation for Unspecified type.");
        }
        if (TextUnitType.m876equalsimpl0(TextUnit.m869getTypeUIouoOA(j), TextUnit.m869getTypeUIouoOA(j2))) {
            return;
        }
        InlineClassHelperKt.throwIllegalArgumentException("Cannot perform operation for " + ((Object) TextUnitType.m877toStringimpl(TextUnit.m869getTypeUIouoOA(j))) + " and " + ((Object) TextUnitType.m877toStringimpl(TextUnit.m869getTypeUIouoOA(j2))));
    }

    public static final long getSp(double d) {
        return pack((float) d, 4294967296L);
    }

    public static final long pack(float f, long j) {
        long jFloatToRawIntBits = j | (Float.floatToRawIntBits(f) & 4294967295L);
        TextUnit.Companion companion = TextUnit.Companion;
        return jFloatToRawIntBits;
    }

    public static final long getSp(int i) {
        return pack(i, 4294967296L);
    }
}

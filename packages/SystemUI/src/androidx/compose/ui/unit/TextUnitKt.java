package androidx.compose.ui.unit;

import androidx.compose.ui.unit.TextUnit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class TextUnitKt {
    /* renamed from: checkArithmetic--R2X_6o, reason: not valid java name */
    public static final void m871checkArithmeticR2X_6o(long j) {
        TextUnit.Companion companion = TextUnit.Companion;
        if ((j & 1095216660480L) == 0) {
            InlineClassHelperKt.throwIllegalArgumentException("Cannot perform operation for Unspecified type.");
        }
    }

    /* renamed from: checkArithmetic-NB67dxo, reason: not valid java name */
    public static final void m872checkArithmeticNB67dxo(long j, long j2) {
        TextUnit.Companion companion = TextUnit.Companion;
        if (!(((j & 1095216660480L) == 0 || (1095216660480L & j2) == 0) ? false : true)) {
            InlineClassHelperKt.throwIllegalArgumentException("Cannot perform operation for Unspecified type.");
        }
        if (TextUnitType.m874equalsimpl0(TextUnit.m867getTypeUIouoOA(j), TextUnit.m867getTypeUIouoOA(j2))) {
            return;
        }
        InlineClassHelperKt.throwIllegalArgumentException("Cannot perform operation for " + ((Object) TextUnitType.m875toStringimpl(TextUnit.m867getTypeUIouoOA(j))) + " and " + ((Object) TextUnitType.m875toStringimpl(TextUnit.m867getTypeUIouoOA(j2))));
    }

    public static final long getSp(double d) {
        return pack((float) d, 4294967296L);
    }

    public static final long pack(float f, long j) {
        long floatToRawIntBits = j | (Float.floatToRawIntBits(f) & 4294967295L);
        TextUnit.Companion companion = TextUnit.Companion;
        return floatToRawIntBits;
    }

    public static final long getSp(int i) {
        return pack(i, 4294967296L);
    }
}

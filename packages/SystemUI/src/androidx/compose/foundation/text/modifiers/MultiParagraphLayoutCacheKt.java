package androidx.compose.foundation.text.modifiers;

import androidx.compose.ui.unit.TextUnit;
import androidx.compose.ui.unit.TextUnitKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class MultiParagraphLayoutCacheKt {
    public static final long DefaultFontSize = TextUnitKt.getSp(14);

    /* renamed from: access$times-NB67dxo, reason: not valid java name */
    public static final long m227access$timesNB67dxo(long j, long j2) {
        if (!TextUnit.m869isEmimpl(j2)) {
            throw new IllegalArgumentException("The multiplier must be in em, but was " + ((Object) TextUnit.m870toStringimpl(j2)) + '.');
        }
        if (TextUnit.m869isEmimpl(j)) {
            throw new IllegalStateException("Cannot convert Em to Px when style.fontSize is Em (" + ((Object) TextUnit.m870toStringimpl(j2)) + "). Please declare the style.fontSize with Sp units instead.");
        }
        long j3 = j & 1095216660480L;
        if (j3 != 0) {
            float m868getValueimpl = TextUnit.m868getValueimpl(j2);
            TextUnitKt.m871checkArithmeticR2X_6o(j);
            return TextUnitKt.pack(TextUnit.m868getValueimpl(j) * m868getValueimpl, j3);
        }
        float m868getValueimpl2 = TextUnit.m868getValueimpl(j2);
        long j4 = DefaultFontSize;
        TextUnitKt.m871checkArithmeticR2X_6o(j4);
        return TextUnitKt.pack(TextUnit.m868getValueimpl(j4) * m868getValueimpl2, 1095216660480L & j4);
    }
}

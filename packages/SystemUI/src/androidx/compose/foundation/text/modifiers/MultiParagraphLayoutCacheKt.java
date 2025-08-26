package androidx.compose.foundation.text.modifiers;

import androidx.compose.ui.unit.TextUnit;
import androidx.compose.ui.unit.TextUnitKt;

/* loaded from: classes.dex */
public abstract class MultiParagraphLayoutCacheKt {
    public static final long DefaultFontSize = TextUnitKt.getSp(14);

    /* renamed from: access$times-NB67dxo, reason: not valid java name */
    public static final long m228access$timesNB67dxo(long j, long j2) {
        if (!TextUnit.m871isEmimpl(j2)) {
            throw new IllegalArgumentException("The multiplier must be in em, but was " + ((Object) TextUnit.m872toStringimpl(j2)) + '.');
        }
        if (TextUnit.m871isEmimpl(j)) {
            throw new IllegalStateException("Cannot convert Em to Px when style.fontSize is Em (" + ((Object) TextUnit.m872toStringimpl(j2)) + "). Please declare the style.fontSize with Sp units instead.");
        }
        long j3 = j & 1095216660480L;
        if (j3 != 0) {
            float fM870getValueimpl = TextUnit.m870getValueimpl(j2);
            TextUnitKt.m873checkArithmeticR2X_6o(j);
            return TextUnitKt.pack(TextUnit.m870getValueimpl(j) * fM870getValueimpl, j3);
        }
        float fM870getValueimpl2 = TextUnit.m870getValueimpl(j2);
        long j4 = DefaultFontSize;
        TextUnitKt.m873checkArithmeticR2X_6o(j4);
        return TextUnitKt.pack(TextUnit.m870getValueimpl(j4) * fM870getValueimpl2, 1095216660480L & j4);
    }
}

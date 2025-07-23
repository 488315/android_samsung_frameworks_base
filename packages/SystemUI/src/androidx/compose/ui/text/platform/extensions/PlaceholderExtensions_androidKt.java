package androidx.compose.ui.text.platform.extensions;

import androidx.compose.ui.unit.TextUnit;
import androidx.compose.ui.unit.TextUnitType;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class PlaceholderExtensions_androidKt {
    /* renamed from: getSpanUnit--R2X_6o, reason: not valid java name */
    public static final int m784getSpanUnitR2X_6o(long j) {
        long m867getTypeUIouoOA = TextUnit.m867getTypeUIouoOA(j);
        TextUnitType.Companion companion = TextUnitType.Companion;
        companion.getClass();
        if (TextUnitType.m874equalsimpl0(m867getTypeUIouoOA, TextUnitType.Sp)) {
            return 0;
        }
        companion.getClass();
        return TextUnitType.m874equalsimpl0(m867getTypeUIouoOA, TextUnitType.Em) ? 1 : 2;
    }
}

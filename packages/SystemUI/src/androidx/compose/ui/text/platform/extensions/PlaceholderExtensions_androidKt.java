package androidx.compose.ui.text.platform.extensions;

import androidx.compose.ui.unit.TextUnit;
import androidx.compose.ui.unit.TextUnitType;

/* loaded from: classes.dex */
public abstract class PlaceholderExtensions_androidKt {
    /* renamed from: getSpanUnit--R2X_6o, reason: not valid java name */
    public static final int m786getSpanUnitR2X_6o(long j) {
        long jM869getTypeUIouoOA = TextUnit.m869getTypeUIouoOA(j);
        TextUnitType.Companion companion = TextUnitType.Companion;
        companion.getClass();
        if (TextUnitType.m876equalsimpl0(jM869getTypeUIouoOA, TextUnitType.Sp)) {
            return 0;
        }
        companion.getClass();
        return TextUnitType.m876equalsimpl0(jM869getTypeUIouoOA, TextUnitType.Em) ? 1 : 2;
    }
}

package androidx.compose.foundation.internal;

import android.os.Parcel;
import androidx.compose.ui.unit.TextUnit;
import androidx.compose.ui.unit.TextUnitType;

/* loaded from: classes.dex */
public final class EncodeHelper {
    public Parcel parcel = Parcel.obtain();

    public final void encode(byte b) {
        this.parcel.writeByte(b);
    }

    /* renamed from: encode--R2X_6o, reason: not valid java name */
    public final void m90encodeR2X_6o(long j) {
        long jM869getTypeUIouoOA = TextUnit.m869getTypeUIouoOA(j);
        TextUnitType.Companion companion = TextUnitType.Companion;
        companion.getClass();
        byte b = 0;
        if (!TextUnitType.m876equalsimpl0(jM869getTypeUIouoOA, 0L)) {
            companion.getClass();
            if (TextUnitType.m876equalsimpl0(jM869getTypeUIouoOA, TextUnitType.Sp)) {
                b = 1;
            } else {
                companion.getClass();
                if (TextUnitType.m876equalsimpl0(jM869getTypeUIouoOA, TextUnitType.Em)) {
                    b = 2;
                }
            }
        }
        encode(b);
        long jM869getTypeUIouoOA2 = TextUnit.m869getTypeUIouoOA(j);
        companion.getClass();
        if (TextUnitType.m876equalsimpl0(jM869getTypeUIouoOA2, 0L)) {
            return;
        }
        encode(TextUnit.m870getValueimpl(j));
    }

    public final void encode(float f) {
        this.parcel.writeFloat(f);
    }
}

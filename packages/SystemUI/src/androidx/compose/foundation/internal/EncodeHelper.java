package androidx.compose.foundation.internal;

import android.os.Parcel;
import androidx.compose.ui.unit.TextUnit;
import androidx.compose.ui.unit.TextUnitType;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class EncodeHelper {
    public Parcel parcel = Parcel.obtain();

    public final void encode(byte b) {
        this.parcel.writeByte(b);
    }

    /* renamed from: encode--R2X_6o, reason: not valid java name */
    public final void m89encodeR2X_6o(long j) {
        long m867getTypeUIouoOA = TextUnit.m867getTypeUIouoOA(j);
        TextUnitType.Companion companion = TextUnitType.Companion;
        companion.getClass();
        byte b = 0;
        if (!TextUnitType.m874equalsimpl0(m867getTypeUIouoOA, 0L)) {
            companion.getClass();
            if (TextUnitType.m874equalsimpl0(m867getTypeUIouoOA, TextUnitType.Sp)) {
                b = 1;
            } else {
                companion.getClass();
                if (TextUnitType.m874equalsimpl0(m867getTypeUIouoOA, TextUnitType.Em)) {
                    b = 2;
                }
            }
        }
        encode(b);
        long m867getTypeUIouoOA2 = TextUnit.m867getTypeUIouoOA(j);
        companion.getClass();
        if (TextUnitType.m874equalsimpl0(m867getTypeUIouoOA2, 0L)) {
            return;
        }
        encode(TextUnit.m868getValueimpl(j));
    }

    public final void encode(float f) {
        this.parcel.writeFloat(f);
    }
}

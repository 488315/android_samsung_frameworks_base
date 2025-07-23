package androidx.compose.foundation.internal;

import android.os.Parcel;
import android.util.Base64;
import androidx.compose.ui.unit.TextUnit;
import androidx.compose.ui.unit.TextUnitKt;
import androidx.compose.ui.unit.TextUnitType;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class DecodeHelper {
    public final Parcel parcel;

    public DecodeHelper(String str) {
        Parcel obtain = Parcel.obtain();
        this.parcel = obtain;
        byte[] decode = Base64.decode(str, 0);
        obtain.unmarshall(decode, 0, decode.length);
        obtain.setDataPosition(0);
    }

    /* renamed from: decodeTextUnit-XSAIIZE, reason: not valid java name */
    public final long m88decodeTextUnitXSAIIZE() {
        long j;
        byte readByte = this.parcel.readByte();
        if (readByte == 1) {
            TextUnitType.Companion.getClass();
            j = TextUnitType.Sp;
        } else if (readByte == 2) {
            TextUnitType.Companion.getClass();
            j = TextUnitType.Em;
        } else {
            TextUnitType.Companion.getClass();
            j = 0;
        }
        TextUnitType.Companion.getClass();
        if (!TextUnitType.m874equalsimpl0(j, 0L)) {
            return TextUnitKt.pack(this.parcel.readFloat(), j);
        }
        TextUnit.Companion.getClass();
        return TextUnit.Unspecified;
    }
}

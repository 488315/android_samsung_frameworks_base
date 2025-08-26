package androidx.compose.foundation.internal;

import android.os.Parcel;
import android.util.Base64;
import androidx.compose.ui.unit.TextUnit;
import androidx.compose.ui.unit.TextUnitKt;
import androidx.compose.ui.unit.TextUnitType;

/* loaded from: classes.dex */
public final class DecodeHelper {
    public final Parcel parcel;

    public DecodeHelper(String str) {
        Parcel parcelObtain = Parcel.obtain();
        this.parcel = parcelObtain;
        byte[] bArrDecode = Base64.decode(str, 0);
        parcelObtain.unmarshall(bArrDecode, 0, bArrDecode.length);
        parcelObtain.setDataPosition(0);
    }

    /* renamed from: decodeTextUnit-XSAIIZE, reason: not valid java name */
    public final long m89decodeTextUnitXSAIIZE() {
        long j;
        byte b = this.parcel.readByte();
        if (b == 1) {
            TextUnitType.Companion.getClass();
            j = TextUnitType.Sp;
        } else if (b == 2) {
            TextUnitType.Companion.getClass();
            j = TextUnitType.Em;
        } else {
            TextUnitType.Companion.getClass();
            j = 0;
        }
        TextUnitType.Companion.getClass();
        if (!TextUnitType.m876equalsimpl0(j, 0L)) {
            return TextUnitKt.pack(this.parcel.readFloat(), j);
        }
        TextUnit.Companion.getClass();
        return TextUnit.Unspecified;
    }
}

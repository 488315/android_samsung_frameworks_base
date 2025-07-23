package kotlin.uuid;

import kotlin.text.HexExtensionsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class UuidKt__UuidKt extends UuidKt__UuidJVMKt {
    public static final void access$formatBytesInto(long j, byte[] bArr, int i, int i2) {
        int i3 = (i2 * 2) + i;
        for (int i4 = 0; i4 < i2; i4++) {
            int i5 = HexExtensionsKt.BYTE_TO_LOWER_CASE_HEX_DIGITS[(int) (255 & j)];
            bArr[i3 - 1] = (byte) i5;
            i3 -= 2;
            bArr[i3] = (byte) (i5 >> 8);
            j >>= 8;
        }
    }
}

package com.android.internal.org.bouncycastle.crypto.paddings;

import com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException;
import java.security.SecureRandom;

/* loaded from: classes5.dex */
public class TBCPadding implements BlockCipherPadding {
    @Override // com.android.internal.org.bouncycastle.crypto.paddings.BlockCipherPadding
    public void init(SecureRandom secureRandom) throws IllegalArgumentException {
    }

    @Override // com.android.internal.org.bouncycastle.crypto.paddings.BlockCipherPadding
    public String getPaddingName() {
        return "TBC";
    }

    @Override // com.android.internal.org.bouncycastle.crypto.paddings.BlockCipherPadding
    public int addPadding(byte[] bArr, int i) {
        int length = bArr.length - i;
        int i2 = 255;
        if (i <= 0 ? (bArr[bArr.length - 1] & 1) != 0 : (bArr[i - 1] & 1) != 0) {
            i2 = 0;
        }
        byte b = (byte) i2;
        while (i < bArr.length) {
            bArr[i] = b;
            i++;
        }
        return length;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.paddings.BlockCipherPadding
    public int padCount(byte[] bArr) throws InvalidCipherTextException {
        int length = bArr.length - 1;
        int i = bArr[length] & 255;
        int i2 = -1;
        int i3 = 1;
        while (true) {
            length--;
            if (length < 0) {
                return i3;
            }
            i2 &= (((bArr[length] & 255) ^ i) - 1) >> 31;
            i3 -= i2;
        }
    }
}

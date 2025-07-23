package com.android.internal.org.bouncycastle.crypto.paddings;

import com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException;
import java.security.SecureRandom;

/* loaded from: classes5.dex */
public class PKCS7Padding implements BlockCipherPadding {
    @Override // com.android.internal.org.bouncycastle.crypto.paddings.BlockCipherPadding
    public void init(SecureRandom secureRandom) throws IllegalArgumentException {
    }

    @Override // com.android.internal.org.bouncycastle.crypto.paddings.BlockCipherPadding
    public String getPaddingName() {
        return "PKCS7";
    }

    @Override // com.android.internal.org.bouncycastle.crypto.paddings.BlockCipherPadding
    public int addPadding(byte[] bArr, int i) {
        byte length = (byte) (bArr.length - i);
        while (i < bArr.length) {
            bArr[i] = length;
            i++;
        }
        return length;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.paddings.BlockCipherPadding
    public int padCount(byte[] bArr) throws InvalidCipherTextException {
        byte b = bArr[bArr.length - 1];
        int i = b & 255;
        int length = bArr.length - i;
        int i2 = ((i - 1) | length) >> 31;
        for (int i3 = 0; i3 < bArr.length; i3++) {
            i2 |= (bArr[i3] ^ b) & (~((i3 - length) >> 31));
        }
        if (i2 == 0) {
            return i;
        }
        throw new InvalidCipherTextException("pad block corrupted");
    }
}

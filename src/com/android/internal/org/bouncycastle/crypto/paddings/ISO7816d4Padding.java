package com.android.internal.org.bouncycastle.crypto.paddings;

import com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException;
import java.security.SecureRandom;

/* loaded from: classes5.dex */
public class ISO7816d4Padding implements BlockCipherPadding {
    @Override // com.android.internal.org.bouncycastle.crypto.paddings.BlockCipherPadding
    public void init(SecureRandom secureRandom) throws IllegalArgumentException {
    }

    @Override // com.android.internal.org.bouncycastle.crypto.paddings.BlockCipherPadding
    public String getPaddingName() {
        return "ISO7816-4";
    }

    @Override // com.android.internal.org.bouncycastle.crypto.paddings.BlockCipherPadding
    public int addPadding(byte[] bArr, int i) {
        int length = bArr.length - i;
        bArr[i] = Byte.MIN_VALUE;
        while (true) {
            i++;
            if (i >= bArr.length) {
                return length;
            }
            bArr[i] = 0;
        }
    }

    @Override // com.android.internal.org.bouncycastle.crypto.paddings.BlockCipherPadding
    public int padCount(byte[] bArr) throws InvalidCipherTextException {
        int length = bArr.length;
        int i = -1;
        int i2 = -1;
        while (true) {
            length--;
            if (length < 0) {
                break;
            }
            i ^= ((((r3 ^ 128) - 1) >> 31) & i2) & (length ^ i);
            i2 &= ((bArr[length] & 255) - 1) >> 31;
        }
        if (i < 0) {
            throw new InvalidCipherTextException("pad block corrupted");
        }
        return bArr.length - i;
    }
}

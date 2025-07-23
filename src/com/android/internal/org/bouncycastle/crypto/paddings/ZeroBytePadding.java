package com.android.internal.org.bouncycastle.crypto.paddings;

import com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException;
import java.security.SecureRandom;

/* loaded from: classes5.dex */
public class ZeroBytePadding implements BlockCipherPadding {
    @Override // com.android.internal.org.bouncycastle.crypto.paddings.BlockCipherPadding
    public void init(SecureRandom secureRandom) throws IllegalArgumentException {
    }

    @Override // com.android.internal.org.bouncycastle.crypto.paddings.BlockCipherPadding
    public String getPaddingName() {
        return "ZeroByte";
    }

    @Override // com.android.internal.org.bouncycastle.crypto.paddings.BlockCipherPadding
    public int addPadding(byte[] bArr, int i) {
        int length = bArr.length - i;
        while (i < bArr.length) {
            bArr[i] = 0;
            i++;
        }
        return length;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.paddings.BlockCipherPadding
    public int padCount(byte[] bArr) throws InvalidCipherTextException {
        int length = bArr.length;
        int i = 0;
        int i2 = -1;
        while (true) {
            length--;
            if (length < 0) {
                return i;
            }
            i2 &= ((bArr[length] & 255) - 1) >> 31;
            i -= i2;
        }
    }
}

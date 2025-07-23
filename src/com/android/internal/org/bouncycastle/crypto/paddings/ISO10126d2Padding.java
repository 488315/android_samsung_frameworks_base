package com.android.internal.org.bouncycastle.crypto.paddings;

import com.android.internal.org.bouncycastle.crypto.CryptoServicesRegistrar;
import com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException;
import java.security.SecureRandom;

/* loaded from: classes5.dex */
public class ISO10126d2Padding implements BlockCipherPadding {
    SecureRandom random;

    @Override // com.android.internal.org.bouncycastle.crypto.paddings.BlockCipherPadding
    public void init(SecureRandom secureRandom) throws IllegalArgumentException {
        this.random = CryptoServicesRegistrar.getSecureRandom(secureRandom);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.paddings.BlockCipherPadding
    public String getPaddingName() {
        return "ISO10126-2";
    }

    @Override // com.android.internal.org.bouncycastle.crypto.paddings.BlockCipherPadding
    public int addPadding(byte[] bArr, int i) {
        byte length = (byte) (bArr.length - i);
        while (i < bArr.length - 1) {
            bArr[i] = (byte) this.random.nextInt();
            i++;
        }
        bArr[i] = length;
        return length;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.paddings.BlockCipherPadding
    public int padCount(byte[] bArr) throws InvalidCipherTextException {
        int i = bArr[bArr.length - 1] & 255;
        if ((((bArr.length - i) | (i - 1)) >> 31) == 0) {
            return i;
        }
        throw new InvalidCipherTextException("pad block corrupted");
    }
}

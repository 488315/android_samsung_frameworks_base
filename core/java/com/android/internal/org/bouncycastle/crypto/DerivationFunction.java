package com.android.internal.org.bouncycastle.crypto;

/* loaded from: classes5.dex */
public interface DerivationFunction {
    int generateBytes(byte[] bArr, int i, int i2)
            throws DataLengthException, IllegalArgumentException;

    void init(DerivationParameters derivationParameters);
}

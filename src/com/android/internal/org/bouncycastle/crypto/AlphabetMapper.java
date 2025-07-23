package com.android.internal.org.bouncycastle.crypto;

/* loaded from: classes5.dex */
public interface AlphabetMapper {
    char[] convertToChars(byte[] bArr);

    byte[] convertToIndexes(char[] cArr);

    int getRadix();
}

package com.android.internal.org.bouncycastle.crypto;

/* loaded from: classes5.dex */
public interface EncapsulatedSecretExtractor {
    byte[] extractSecret(byte[] bArr);

    int getEncapsulationLength();
}

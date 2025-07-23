package com.android.internal.org.bouncycastle.jcajce.spec;

import com.android.internal.org.bouncycastle.util.Arrays;
import java.security.PrivateKey;
import java.security.spec.AlgorithmParameterSpec;

/* loaded from: classes5.dex */
public class KEMExtractSpec implements AlgorithmParameterSpec {
    private final byte[] encapsulation;
    private final String keyAlgorithmName;
    private final int keySizeInBits;
    private final PrivateKey privateKey;

    public KEMExtractSpec(PrivateKey privateKey, byte[] bArr, String str) {
        this(privateKey, bArr, str, 256);
    }

    public KEMExtractSpec(PrivateKey privateKey, byte[] bArr, String str, int i) {
        this.privateKey = privateKey;
        this.encapsulation = Arrays.clone(bArr);
        this.keyAlgorithmName = str;
        this.keySizeInBits = i;
    }

    public byte[] getEncapsulation() {
        return Arrays.clone(this.encapsulation);
    }

    public PrivateKey getPrivateKey() {
        return this.privateKey;
    }

    public String getKeyAlgorithmName() {
        return this.keyAlgorithmName;
    }

    public int getKeySize() {
        return this.keySizeInBits;
    }
}

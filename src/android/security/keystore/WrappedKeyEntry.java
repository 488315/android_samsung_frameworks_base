package android.security.keystore;

import java.security.KeyStore;
import java.security.spec.AlgorithmParameterSpec;

/* loaded from: classes3.dex */
public class WrappedKeyEntry implements KeyStore.Entry {
    private final AlgorithmParameterSpec mAlgorithmParameterSpec;
    private final String mTransformation;
    private final byte[] mWrappedKeyBytes;
    private final String mWrappingKeyAlias;

    public WrappedKeyEntry(byte[] bArr, String str, String str2, AlgorithmParameterSpec algorithmParameterSpec) {
        this.mWrappedKeyBytes = bArr;
        this.mWrappingKeyAlias = str;
        this.mTransformation = str2;
        this.mAlgorithmParameterSpec = algorithmParameterSpec;
    }

    public byte[] getWrappedKeyBytes() {
        return this.mWrappedKeyBytes;
    }

    public String getWrappingKeyAlias() {
        return this.mWrappingKeyAlias;
    }

    public String getTransformation() {
        return this.mTransformation;
    }

    public AlgorithmParameterSpec getAlgorithmParameterSpec() {
        return this.mAlgorithmParameterSpec;
    }
}

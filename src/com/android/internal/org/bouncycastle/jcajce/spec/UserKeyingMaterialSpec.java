package com.android.internal.org.bouncycastle.jcajce.spec;

import com.android.internal.org.bouncycastle.util.Arrays;
import java.security.spec.AlgorithmParameterSpec;

/* loaded from: classes5.dex */
public class UserKeyingMaterialSpec implements AlgorithmParameterSpec {
    private final byte[] salt;
    private final byte[] userKeyingMaterial;

    public UserKeyingMaterialSpec(byte[] bArr) {
        this(bArr, null);
    }

    public UserKeyingMaterialSpec(byte[] bArr, byte[] bArr2) {
        this.userKeyingMaterial = Arrays.clone(bArr);
        this.salt = Arrays.clone(bArr2);
    }

    public byte[] getUserKeyingMaterial() {
        return Arrays.clone(this.userKeyingMaterial);
    }

    public byte[] getSalt() {
        return Arrays.clone(this.salt);
    }
}

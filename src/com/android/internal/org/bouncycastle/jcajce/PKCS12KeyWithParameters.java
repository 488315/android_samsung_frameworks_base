package com.android.internal.org.bouncycastle.jcajce;

import com.android.internal.org.bouncycastle.util.Arrays;
import javax.crypto.interfaces.PBEKey;

/* loaded from: classes5.dex */
public class PKCS12KeyWithParameters extends PKCS12Key implements PBEKey {
    private final int iterationCount;
    private final byte[] salt;

    public PKCS12KeyWithParameters(char[] cArr, byte[] bArr, int i) {
        super(cArr);
        this.salt = Arrays.clone(bArr);
        this.iterationCount = i;
    }

    public PKCS12KeyWithParameters(char[] cArr, boolean z, byte[] bArr, int i) {
        super(cArr, z);
        this.salt = Arrays.clone(bArr);
        this.iterationCount = i;
    }

    @Override // javax.crypto.interfaces.PBEKey
    public byte[] getSalt() {
        return this.salt;
    }

    @Override // javax.crypto.interfaces.PBEKey
    public int getIterationCount() {
        return this.iterationCount;
    }
}

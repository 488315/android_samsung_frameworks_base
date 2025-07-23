package com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util;

import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.crypto.CipherParameters;
import com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.PBE;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.SecretKey;
import javax.crypto.spec.PBEKeySpec;

/* loaded from: classes5.dex */
public class PBESecretKeyFactory extends BaseSecretKeyFactory implements PBE {
    private int digest;
    private boolean forCipher;
    private int ivSize;
    private int keySize;
    private int scheme;

    public PBESecretKeyFactory(String str, ASN1ObjectIdentifier aSN1ObjectIdentifier, boolean z, int i, int i2, int i3, int i4) {
        super(str, aSN1ObjectIdentifier);
        this.forCipher = z;
        this.scheme = i;
        this.digest = i2;
        this.keySize = i3;
        this.ivSize = i4;
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseSecretKeyFactory, javax.crypto.SecretKeyFactorySpi
    protected SecretKey engineGenerateSecret(KeySpec keySpec) throws InvalidKeySpecException {
        CipherParameters makePBEMacParameters;
        if (keySpec instanceof PBEKeySpec) {
            PBEKeySpec pBEKeySpec = (PBEKeySpec) keySpec;
            if (pBEKeySpec.getSalt() == null) {
                return new BCPBEKey(this.algName, this.algOid, this.scheme, this.digest, this.keySize, this.ivSize, pBEKeySpec, null);
            }
            if (this.forCipher) {
                makePBEMacParameters = PBE.Util.makePBEParameters(pBEKeySpec, this.scheme, this.digest, this.keySize, this.ivSize);
            } else {
                makePBEMacParameters = PBE.Util.makePBEMacParameters(pBEKeySpec, this.scheme, this.digest, this.keySize);
            }
            return new BCPBEKey(this.algName, this.algOid, this.scheme, this.digest, this.keySize, this.ivSize, pBEKeySpec, makePBEMacParameters);
        }
        throw new InvalidKeySpecException("Invalid KeySpec");
    }
}

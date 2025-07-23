package com.samsung.ucm.keystore;

import android.security.keystore.KeyProperties;
import java.io.ByteArrayInputStream;
import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.ECKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.ECParameterSpec;

/* loaded from: classes6.dex */
public class UcmKeyStorePrivateKey extends UcmKeyStoreKey implements PrivateKey {
    protected ECParameterSpec mECParameterSpec;
    protected BigInteger mModulus;

    @Override // com.samsung.ucm.keystore.UcmKeyStoreKey
    public int hashCode() {
        return 1;
    }

    public UcmKeyStorePrivateKey(String str, String str2) {
        super(str, "RSA");
        this.mModulus = null;
        this.mECParameterSpec = null;
    }

    public UcmKeyStorePrivateKey(String str, String str2, byte[] bArr) {
        this(str, str2);
        try {
            PublicKey publicKey = ((X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(bArr))).getPublicKey();
            if (publicKey != null) {
                this.mAlgorithm = publicKey.getAlgorithm();
                if ("RSA".equals(this.mAlgorithm)) {
                    this.mModulus = ((RSAPublicKey) publicKey).getModulus();
                } else if (KeyProperties.KEY_ALGORITHM_EC.equals(this.mAlgorithm)) {
                    this.mECParameterSpec = ((ECKey) publicKey).getParams();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.samsung.ucm.keystore.UcmKeyStoreKey
    public boolean equals(Object obj) {
        if (!(obj instanceof UcmKeyStorePrivateKey)) {
            return false;
        }
        super.equals(obj);
        return false;
    }
}

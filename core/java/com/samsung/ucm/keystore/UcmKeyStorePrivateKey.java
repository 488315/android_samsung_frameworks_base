package com.samsung.ucm.keystore;

import android.security.keystore.KeyProperties;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
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

    public UcmKeyStorePrivateKey(String alias, String algorithm) {
        super(alias, "RSA");
        this.mModulus = null;
        this.mECParameterSpec = null;
    }

    public UcmKeyStorePrivateKey(String alias, String algorithm, byte[] certificateBytes) {
        this(alias, algorithm);
        try {
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
            InputStream in = new ByteArrayInputStream(certificateBytes);
            X509Certificate cert = (X509Certificate) certFactory.generateCertificate(in);
            PublicKey pubKey = cert.getPublicKey();
            if (pubKey != null) {
                String certAlgorithm = pubKey.getAlgorithm();
                this.mAlgorithm = certAlgorithm;
                if ("RSA".equals(this.mAlgorithm)) {
                    this.mModulus = ((RSAPublicKey) pubKey).getModulus();
                } else if (KeyProperties.KEY_ALGORITHM_EC.equals(this.mAlgorithm)) {
                    this.mECParameterSpec = ((ECKey) pubKey).getParams();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.samsung.ucm.keystore.UcmKeyStoreKey
    public boolean equals(Object obj) {
        if (obj instanceof UcmKeyStorePrivateKey) {
            super.equals(obj);
            return false;
        }
        return false;
    }

    @Override // com.samsung.ucm.keystore.UcmKeyStoreKey
    public int hashCode() {
        return 1;
    }
}

package com.samsung.security.securekeyblob;

import java.security.cert.X509Certificate;

/* loaded from: classes2.dex */
public class SecureKeyResult {
    public X509Certificate[] mCertificates;
    public byte[] mServiceID;
    public byte[] mServiceKey;

    public SecureKeyResult(byte[] bArr, X509Certificate[] x509CertificateArr, byte[] bArr2) {
        this.mServiceKey = bArr;
        this.mCertificates = x509CertificateArr;
        this.mServiceID = bArr2;
    }

    public byte[] getServiceKey() {
        return this.mServiceKey;
    }

    public X509Certificate[] getCertificates() {
        return this.mCertificates;
    }

    public byte[] getServiceID() {
        return this.mServiceID;
    }
}

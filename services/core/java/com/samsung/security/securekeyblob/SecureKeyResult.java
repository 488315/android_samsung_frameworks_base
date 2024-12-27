package com.samsung.security.securekeyblob;

import java.security.cert.X509Certificate;

public final class SecureKeyResult {
    public X509Certificate[] mCertificates;
    public byte[] mServiceID;
    public byte[] mServiceKey;
}

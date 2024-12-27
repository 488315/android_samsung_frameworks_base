package com.android.server.enterprise.threatdefense;

import android.util.Base64;
import android.util.Log;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.util.Arrays;

public final class MTDSignature {
    public final byte[] mData;
    public final byte[] mSignature;

    public MTDSignature(String str) {
        byte[] decode = Base64.decode(str, 0);
        if (decode.length > 256) {
            this.mData = Arrays.copyOfRange(decode, 0, decode.length - 256);
            this.mSignature = Arrays.copyOfRange(decode, decode.length - 256, decode.length);
        } else {
            Log.e("MTDSignature", "data size=" + decode.length);
            throw new IllegalArgumentException("Invalid SHA256. please encode the String as UTF_8");
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.security.cert.Certificate getCertificate() {
        /*
            java.lang.String r0 = "IOException"
            java.lang.String r1 = "MTDSignature"
            java.lang.String r2 = "FileNotFoundException : "
            java.lang.String r3 = "CertificateException : "
            r4 = 0
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L2c java.security.cert.CertificateException -> L2e java.io.FileNotFoundException -> L31 java.lang.SecurityException -> L34
            java.lang.String r6 = "/etc/mtdl.crt"
            r5.<init>(r6)     // Catch: java.lang.Throwable -> L2c java.security.cert.CertificateException -> L2e java.io.FileNotFoundException -> L31 java.lang.SecurityException -> L34
            java.lang.String r6 = "X.509"
            java.security.cert.CertificateFactory r6 = java.security.cert.CertificateFactory.getInstance(r6)     // Catch: java.lang.Throwable -> L23 java.security.cert.CertificateException -> L26 java.io.FileNotFoundException -> L28 java.lang.SecurityException -> L2a
            java.security.cert.Certificate r4 = r6.generateCertificate(r5)     // Catch: java.lang.Throwable -> L23 java.security.cert.CertificateException -> L26 java.io.FileNotFoundException -> L28 java.lang.SecurityException -> L2a
            r5.close()     // Catch: java.io.IOException -> L1e
            goto L76
        L1e:
            r2 = move-exception
            android.util.Log.e(r1, r0, r2)
            goto L76
        L23:
            r2 = move-exception
            r4 = r5
            goto L77
        L26:
            r2 = move-exception
            goto L37
        L28:
            r3 = move-exception
            goto L53
        L2a:
            r2 = move-exception
            goto L6c
        L2c:
            r2 = move-exception
            goto L77
        L2e:
            r2 = move-exception
            r5 = r4
            goto L37
        L31:
            r3 = move-exception
            r5 = r4
            goto L53
        L34:
            r2 = move-exception
            r5 = r4
            goto L6c
        L37:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L23
            r6.<init>(r3)     // Catch: java.lang.Throwable -> L23
            java.lang.String r3 = r2.getMessage()     // Catch: java.lang.Throwable -> L23
            r6.append(r3)     // Catch: java.lang.Throwable -> L23
            java.lang.String r3 = r6.toString()     // Catch: java.lang.Throwable -> L23
            android.util.Log.e(r1, r3)     // Catch: java.lang.Throwable -> L23
            r2.printStackTrace()     // Catch: java.lang.Throwable -> L23
            if (r5 == 0) goto L76
            r5.close()     // Catch: java.io.IOException -> L1e
            goto L76
        L53:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L23
            r6.<init>(r2)     // Catch: java.lang.Throwable -> L23
            java.lang.String r2 = r3.getMessage()     // Catch: java.lang.Throwable -> L23
            r6.append(r2)     // Catch: java.lang.Throwable -> L23
            java.lang.String r2 = r6.toString()     // Catch: java.lang.Throwable -> L23
            android.util.Log.e(r1, r2)     // Catch: java.lang.Throwable -> L23
            if (r5 == 0) goto L76
            r5.close()     // Catch: java.io.IOException -> L1e
            goto L76
        L6c:
            java.lang.String r3 = "SecurityException"
            android.util.Log.e(r1, r3, r2)     // Catch: java.lang.Throwable -> L23
            if (r5 == 0) goto L76
            r5.close()     // Catch: java.io.IOException -> L1e
        L76:
            return r4
        L77:
            if (r4 == 0) goto L81
            r4.close()     // Catch: java.io.IOException -> L7d
            goto L81
        L7d:
            r3 = move-exception
            android.util.Log.e(r1, r0, r3)
        L81:
            throw r2
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.threatdefense.MTDSignature.getCertificate():java.security.cert.Certificate");
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.security.PublicKey getPublicKey(byte[] r2) {
        /*
            r0 = 0
            java.io.ByteArrayInputStream r1 = new java.io.ByteArrayInputStream     // Catch: java.lang.Throwable -> L24 java.security.cert.CertificateException -> L26
            r1.<init>(r2)     // Catch: java.lang.Throwable -> L24 java.security.cert.CertificateException -> L26
            java.lang.String r2 = "X.509"
            java.security.cert.CertificateFactory r2 = java.security.cert.CertificateFactory.getInstance(r2)     // Catch: java.lang.Throwable -> L1f java.security.cert.CertificateException -> L22
            java.security.cert.Certificate r2 = r2.generateCertificate(r1)     // Catch: java.lang.Throwable -> L1f java.security.cert.CertificateException -> L22
            java.security.cert.X509Certificate r2 = (java.security.cert.X509Certificate) r2     // Catch: java.lang.Throwable -> L1f java.security.cert.CertificateException -> L22
            java.security.PublicKey r2 = r2.getPublicKey()     // Catch: java.lang.Throwable -> L1f java.security.cert.CertificateException -> L22
            r1.close()     // Catch: java.io.IOException -> L1a
            goto L1e
        L1a:
            r0 = move-exception
            r0.printStackTrace()
        L1e:
            return r2
        L1f:
            r2 = move-exception
            r0 = r1
            goto L36
        L22:
            r2 = move-exception
            goto L28
        L24:
            r2 = move-exception
            goto L36
        L26:
            r2 = move-exception
            r1 = r0
        L28:
            r2.printStackTrace()     // Catch: java.lang.Throwable -> L1f
            if (r1 == 0) goto L35
            r1.close()     // Catch: java.io.IOException -> L31
            goto L35
        L31:
            r2 = move-exception
            r2.printStackTrace()
        L35:
            return r0
        L36:
            if (r0 == 0) goto L40
            r0.close()     // Catch: java.io.IOException -> L3c
            goto L40
        L3c:
            r0 = move-exception
            r0.printStackTrace()
        L40:
            throw r2
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.threatdefense.MTDSignature.getPublicKey(byte[]):java.security.PublicKey");
    }

    public final String getVerifiedData() {
        byte[] bArr = this.mData;
        boolean z = false;
        try {
            Certificate certificate = getCertificate();
            if (certificate != null) {
                Signature signature = Signature.getInstance("SHA256withRSA/PSS");
                PublicKey publicKey = getPublicKey(certificate.getEncoded());
                if (publicKey != null) {
                    signature.initVerify(publicKey);
                    signature.update(bArr);
                    z = signature.verify(this.mSignature);
                }
            }
        } catch (InvalidKeyException
                | NoSuchAlgorithmException
                | SignatureException
                | CertificateEncodingException e) {
            e.printStackTrace();
        }
        if (!z) {
            Log.i("MTDSignature", "Verification failed !!!");
            return null;
        }
        String str = new String(bArr, StandardCharsets.UTF_8);
        if (ThreatDefenseService.DEBUG) {
            Log.i("MTDSignature", "Verified !!! data=".concat(str));
        } else {
            Log.i("MTDSignature", "Verified !!!");
        }
        return str;
    }
}

package com.android.server.enterprise.threatdefense;

import android.util.Base64;
import android.util.Log;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;

/* loaded from: classes2.dex */
public class MTDSignature {
    public static final String TAG = "MTDSignature";
    public byte[] mData;
    public byte[] mSignature;

    public MTDSignature(String str) {
        byte[] decode = Base64.decode(str, 0);
        if (decode.length > 256) {
            this.mData = Arrays.copyOfRange(decode, 0, decode.length - 256);
            this.mSignature = Arrays.copyOfRange(decode, decode.length - 256, decode.length);
            return;
        }
        Log.e(TAG, "data size=" + decode.length);
        throw new IllegalArgumentException("Invalid SHA256. please encode the String as UTF_8");
    }

    public String getVerifiedData() {
        if (verify()) {
            String str = new String(this.mData, StandardCharsets.UTF_8);
            if (ThreatDefenseService.DEBUG) {
                Log.i(TAG, "Verified !!! data=" + str);
            } else {
                Log.i(TAG, "Verified !!!");
            }
            return str;
        }
        Log.i(TAG, "Verification failed !!!");
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r1v10, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r1v11, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r1v12 */
    /* JADX WARN: Type inference failed for: r1v13 */
    /* JADX WARN: Type inference failed for: r1v14, types: [java.io.FileInputStream, java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r1v15 */
    /* JADX WARN: Type inference failed for: r1v16 */
    /* JADX WARN: Type inference failed for: r1v17 */
    /* JADX WARN: Type inference failed for: r1v18 */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v5 */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r1v7 */
    /* JADX WARN: Type inference failed for: r1v8, types: [java.io.IOException, java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r1v9, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r2v15, types: [java.security.cert.CertificateFactory] */
    public final Certificate getCertificate() {
        ?? e;
        Certificate certificate = null;
        try {
        } catch (Throwable th) {
            th = th;
        }
        try {
            try {
                e = new FileInputStream("/etc/mtdl.crt");
            } catch (IOException e2) {
                e = e2;
                Log.e(TAG, "IOException", e);
            }
            try {
                certificate = CertificateFactory.getInstance("X.509").generateCertificate(e);
                e.close();
                e = e;
            } catch (FileNotFoundException e3) {
                e = e3;
                Log.e(TAG, "FileNotFoundException : " + e.getMessage());
                if (e != 0) {
                    e.close();
                    e = e;
                }
                return certificate;
            } catch (SecurityException e4) {
                e = e4;
                Log.e(TAG, "SecurityException", e);
                if (e != 0) {
                    e.close();
                    e = e;
                }
                return certificate;
            } catch (CertificateException e5) {
                e = e5;
                Log.e(TAG, "CertificateException : " + e.getMessage());
                e.printStackTrace();
                if (e != 0) {
                    e.close();
                    e = e;
                }
                return certificate;
            }
        } catch (FileNotFoundException e6) {
            e = e6;
            e = 0;
        } catch (SecurityException e7) {
            e = e7;
            e = 0;
        } catch (CertificateException e8) {
            e = e8;
            e = 0;
        } catch (Throwable th2) {
            e = 0;
            th = th2;
            if (e != 0) {
                try {
                    e.close();
                } catch (IOException e9) {
                    Log.e(TAG, "IOException", e9);
                }
            }
            throw th;
        }
        return certificate;
    }

    public final boolean verify() {
        try {
            Certificate certificate = getCertificate();
            if (certificate != null) {
                Signature signature = Signature.getInstance("SHA256withRSA/PSS");
                PublicKey publicKey = getPublicKey(certificate.getEncoded());
                if (publicKey == null) {
                    return false;
                }
                signature.initVerify(publicKey);
                signature.update(this.mData);
                return signature.verify(this.mSignature);
            }
        } catch (InvalidKeyException | NoSuchAlgorithmException | SignatureException | CertificateEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0038 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final PublicKey getPublicKey(byte[] bArr) {
        Throwable th;
        ByteArrayInputStream byteArrayInputStream;
        InputStream inputStream = null;
        try {
            try {
                byteArrayInputStream = new ByteArrayInputStream(bArr);
                try {
                    PublicKey publicKey = ((X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(byteArrayInputStream)).getPublicKey();
                    try {
                        byteArrayInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return publicKey;
                } catch (CertificateException e2) {
                    e = e2;
                    e.printStackTrace();
                    if (byteArrayInputStream != null) {
                        try {
                            byteArrayInputStream.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                    return null;
                }
            } catch (Throwable th2) {
                th = th2;
                if (0 != 0) {
                    try {
                        inputStream.close();
                    } catch (IOException e4) {
                        e4.printStackTrace();
                    }
                }
                throw th;
            }
        } catch (CertificateException e5) {
            e = e5;
            byteArrayInputStream = null;
        } catch (Throwable th3) {
            th = th3;
            if (0 != 0) {
            }
            throw th;
        }
    }
}

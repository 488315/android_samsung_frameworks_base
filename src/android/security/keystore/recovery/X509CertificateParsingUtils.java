package android.security.keystore.recovery;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;

/* loaded from: classes3.dex */
public class X509CertificateParsingUtils {
    private static final String CERT_FORMAT = "X.509";

    public static X509Certificate decodeBase64Cert(String str) throws CertificateException {
        try {
            return decodeCert(decodeBase64(str));
        } catch (IllegalArgumentException e) {
            throw new CertificateException(e);
        }
    }

    private static byte[] decodeBase64(String str) {
        return Base64.getDecoder().decode(str);
    }

    private static X509Certificate decodeCert(byte[] bArr) throws CertificateException {
        return decodeCert(new ByteArrayInputStream(bArr));
    }

    private static X509Certificate decodeCert(InputStream inputStream) throws CertificateException {
        try {
            return (X509Certificate) CertificateFactory.getInstance(CERT_FORMAT).generateCertificate(inputStream);
        } catch (CertificateException e) {
            throw new RuntimeException(e);
        }
    }
}

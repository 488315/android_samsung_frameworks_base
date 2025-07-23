package android.net.vcn.persistablebundleutils;

import java.io.ByteArrayInputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Objects;

/* loaded from: classes3.dex */
public class CertUtils {
    private static final String CERT_TYPE_X509 = "X.509";
    private static final String PRIVATE_KEY_TYPE_RSA = "RSA";

    public static X509Certificate certificateFromByteArray(byte[] bArr) {
        Objects.requireNonNull(bArr, "derEncoded is null");
        try {
            return (X509Certificate) CertificateFactory.getInstance(CERT_TYPE_X509).generateCertificate(new ByteArrayInputStream(bArr));
        } catch (CertificateException e) {
            throw new IllegalArgumentException("Fail to decode certificate", e);
        }
    }

    public static RSAPrivateKey privateKeyFromByteArray(byte[] bArr) {
        Objects.requireNonNull(bArr, "pkcs8Encoded was null");
        try {
            return (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(bArr));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new IllegalArgumentException("Fail to decode PrivateKey", e);
        }
    }
}

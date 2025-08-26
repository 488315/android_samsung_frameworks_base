package android.security.keystore;

import android.annotation.SystemApi;
import android.content.Context;
import android.security.keymaster.KeymasterCertificateChain;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore2.AndroidKeyStoreSpi;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.ProviderException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.ECGenParameterSpec;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@SystemApi
/* loaded from: classes3.dex */
public abstract class AttestationUtils {
    public static final int ID_TYPE_IMEI = 2;
    public static final int ID_TYPE_MEID = 3;
    public static final int ID_TYPE_SERIAL = 1;
    public static final int USE_INDIVIDUAL_ATTESTATION = 4;

    private AttestationUtils() {
    }

    public static X509Certificate[] parseCertificateChain(KeymasterCertificateChain keymasterCertificateChain) throws KeyAttestationException {
        List<byte[]> certificates = keymasterCertificateChain.getCertificates();
        if (certificates.size() < 2) {
            throw new KeyAttestationException("Attestation certificate chain contained " + certificates.size() + " entries. At least two are required.");
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            Iterator<byte[]> it = certificates.iterator();
            while (it.hasNext()) {
                byteArrayOutputStream.write(it.next());
            }
            return (X509Certificate[]) CertificateFactory.getInstance("X.509").generateCertificates(new ByteArrayInputStream(byteArrayOutputStream.toByteArray())).toArray(new X509Certificate[0]);
        } catch (Exception e) {
            throw new KeyAttestationException("Unable to construct certificate chain", e);
        }
    }

    public static X509Certificate[] attestDeviceIds(Context context, int[] iArr, byte[] bArr) throws NoSuchAlgorithmException, DeviceIdAttestationException, IOException, KeyStoreException, CertificateException, NoSuchProviderException, InvalidAlgorithmParameterException {
        if (bArr == null) {
            throw new NullPointerException("Missing attestation challenge");
        }
        if (iArr == null) {
            throw new NullPointerException("Missing id types");
        }
        String strGenerateRandomAlias = generateRandomAlias();
        KeyGenParameterSpec.Builder attestationChallenge = new KeyGenParameterSpec.Builder(strGenerateRandomAlias, 4).setAlgorithmParameterSpec(new ECGenParameterSpec("secp256r1")).setDigests("SHA-256").setAttestationChallenge(bArr);
        if (iArr != null) {
            attestationChallenge.setAttestationIds(iArr);
            attestationChallenge.setDevicePropertiesAttestationIncluded(true);
        }
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_EC, AndroidKeyStoreSpi.NAME);
            keyPairGenerator.initialize(attestationChallenge.build());
            keyPairGenerator.generateKeyPair();
            KeyStore keyStore = KeyStore.getInstance(AndroidKeyStoreSpi.NAME);
            keyStore.load(null);
            Certificate[] certificateChain = keyStore.getCertificateChain(strGenerateRandomAlias);
            X509Certificate[] x509CertificateArr = (X509Certificate[]) Arrays.copyOf(certificateChain, certificateChain.length, X509Certificate[].class);
            keyStore.deleteEntry(strGenerateRandomAlias);
            return x509CertificateArr;
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            if (e2.getCause() instanceof DeviceIdAttestationException) {
                throw ((DeviceIdAttestationException) e2.getCause());
            }
            if ((e2 instanceof ProviderException) && (e2.getCause() instanceof IllegalArgumentException)) {
                throw ((IllegalArgumentException) e2.getCause());
            }
            throw new DeviceIdAttestationException("Unable to perform attestation", e2);
        }
    }

    private static String generateRandomAlias() {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            sb.append(secureRandom.nextInt(26) + 65);
        }
        return sb.toString();
    }

    public static boolean isChainValid(KeymasterCertificateChain keymasterCertificateChain) {
        return keymasterCertificateChain != null && keymasterCertificateChain.getCertificates().size() >= 2;
    }
}

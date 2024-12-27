package android.security.keystore2;

import android.os.Process;
import android.os.SystemProperties;
import android.security.KeyStore2;
import android.security.KeyStoreException;
import android.security.KeyStoreSecurityLevel;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.security.keystore.KeyStoreCryptoOperation;
import android.system.keystore2.Authorization;
import android.system.keystore2.KeyDescriptor;
import android.system.keystore2.KeyEntryResponse;
import android.system.keystore2.KeyMetadata;
import android.util.Log;

import com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Key;
import java.security.KeyPair;
import java.security.Provider;
import java.security.ProviderException;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.UnrecoverableKeyException;
import java.security.cert.X509Certificate;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.Mac;
import javax.crypto.SecretKey;

/* loaded from: classes3.dex */
public class AndroidKeyStoreProvider extends Provider {
    private static final String DESEDE_SYSTEM_PROPERTY = "ro.hardware.keystore_desede";
    private static final String ED25519_OID = "1.3.101.112";
    private static final String PACKAGE_NAME = "android.security.keystore2";
    private static final String PROVIDER_NAME = "AndroidKeyStore";
    private static final String TAG = "AndroidKeyStoreProvider";
    private static final String X25519_ALIAS = "XDH";

    public AndroidKeyStoreProvider() {
        super("AndroidKeyStore", 1.0d, "Android KeyStore security provider");
        boolean supports3DES = "true".equals(SystemProperties.get(DESEDE_SYSTEM_PROPERTY));
        if ("CN".equals(SystemProperties.get("ro.csc.countryiso_code"))
                && "system_server".equals(Process.myProcessName())
                && !"FINISH".equals(SystemProperties.get("persist.sys.setupwizard"))) {
            try {
                Log.i(
                        TAG,
                        "original rkp_hostname : "
                                + SystemProperties.get("remote_provisioning.hostname"));
                SystemProperties.set("remote_provisioning.hostname", "");
            } catch (Exception e) {
                Log.e(
                        TAG,
                        "Failed to set remote_provisioning.hostname : "
                                + e.getClass().getSimpleName(),
                        e);
            }
            if ("".equals(SystemProperties.get("remote_provisioning.hostname"))) {
                Log.i(TAG, "remote_provisioning.hostname is empty. setting complete!");
            }
        }
        put("KeyStore.AndroidKeyStore", "android.security.keystore2.AndroidKeyStoreSpi");
        put(
                "KeyPairGenerator.EC",
                "android.security.keystore2.AndroidKeyStoreKeyPairGeneratorSpi$EC");
        put(
                "KeyPairGenerator.RSA",
                "android.security.keystore2.AndroidKeyStoreKeyPairGeneratorSpi$RSA");
        put(
                "KeyPairGenerator.XDH",
                "android.security.keystore2.AndroidKeyStoreKeyPairGeneratorSpi$XDH");
        put(
                "KeyPairGenerator.ED25519",
                "android.security.keystore2.AndroidKeyStoreKeyPairGeneratorSpi$ED25519");
        putKeyFactoryImpl(KeyProperties.KEY_ALGORITHM_EC);
        putKeyFactoryImpl("RSA");
        putKeyFactoryImpl("XDH");
        putKeyFactoryImpl("ED25519");
        put("KeyGenerator.AES", "android.security.keystore2.AndroidKeyStoreKeyGeneratorSpi$AES");
        put(
                "KeyGenerator.HmacSHA1",
                "android.security.keystore2.AndroidKeyStoreKeyGeneratorSpi$HmacSHA1");
        put(
                "KeyGenerator.HmacSHA224",
                "android.security.keystore2.AndroidKeyStoreKeyGeneratorSpi$HmacSHA224");
        put(
                "KeyGenerator.HmacSHA256",
                "android.security.keystore2.AndroidKeyStoreKeyGeneratorSpi$HmacSHA256");
        put(
                "KeyGenerator.HmacSHA384",
                "android.security.keystore2.AndroidKeyStoreKeyGeneratorSpi$HmacSHA384");
        put(
                "KeyGenerator.HmacSHA512",
                "android.security.keystore2.AndroidKeyStoreKeyGeneratorSpi$HmacSHA512");
        if (supports3DES) {
            put(
                    "KeyGenerator.DESede",
                    "android.security.keystore2.AndroidKeyStoreKeyGeneratorSpi$DESede");
        }
        put("KeyAgreement.ECDH", "android.security.keystore2.AndroidKeyStoreKeyAgreementSpi$ECDH");
        put("KeyAgreement.XDH", "android.security.keystore2.AndroidKeyStoreKeyAgreementSpi$XDH");
        putSecretKeyFactoryImpl("AES");
        if (supports3DES) {
            putSecretKeyFactoryImpl(KeyProperties.KEY_ALGORITHM_3DES);
        }
        putSecretKeyFactoryImpl(KeyProperties.KEY_ALGORITHM_HMAC_SHA1);
        putSecretKeyFactoryImpl(KeyProperties.KEY_ALGORITHM_HMAC_SHA224);
        putSecretKeyFactoryImpl(KeyProperties.KEY_ALGORITHM_HMAC_SHA256);
        putSecretKeyFactoryImpl(KeyProperties.KEY_ALGORITHM_HMAC_SHA384);
        putSecretKeyFactoryImpl(KeyProperties.KEY_ALGORITHM_HMAC_SHA512);
    }

    public static void install() {
        Provider[] providers = Security.getProviders();
        int bcProviderIndex = -1;
        int i = 0;
        while (true) {
            if (i >= providers.length) {
                break;
            }
            Provider provider = providers[i];
            if (!BouncyCastleProvider.PROVIDER_NAME.equals(provider.getName())) {
                i++;
            } else {
                bcProviderIndex = i;
                break;
            }
        }
        Security.addProvider(new AndroidKeyStoreProvider());
        Provider workaroundProvider = new AndroidKeyStoreBCWorkaroundProvider();
        if (bcProviderIndex != -1) {
            Security.insertProviderAt(workaroundProvider, bcProviderIndex + 1);
        } else {
            Security.addProvider(workaroundProvider);
        }
    }

    private void putSecretKeyFactoryImpl(String algorithm) {
        put(
                "SecretKeyFactory." + algorithm,
                "android.security.keystore2.AndroidKeyStoreSecretKeyFactorySpi");
    }

    private void putKeyFactoryImpl(String algorithm) {
        put("KeyFactory." + algorithm, "android.security.keystore2.AndroidKeyStoreKeyFactorySpi");
    }

    public static long getKeyStoreOperationHandle(Object cryptoPrimitive) {
        Object spi;
        if (cryptoPrimitive == null) {
            throw new NullPointerException();
        }
        if (cryptoPrimitive instanceof Signature) {
            spi = ((Signature) cryptoPrimitive).getCurrentSpi();
        } else if (cryptoPrimitive instanceof Mac) {
            spi = ((Mac) cryptoPrimitive).getCurrentSpi();
        } else if (cryptoPrimitive instanceof Cipher) {
            spi = ((Cipher) cryptoPrimitive).getCurrentSpi();
        } else if (cryptoPrimitive instanceof KeyAgreement) {
            spi = ((KeyAgreement) cryptoPrimitive).getCurrentSpi();
        } else {
            throw new IllegalArgumentException(
                    "Unsupported crypto primitive: "
                            + cryptoPrimitive
                            + ". Supported: Signature, Mac, Cipher");
        }
        if (spi == null) {
            throw new IllegalStateException("Crypto primitive not initialized");
        }
        if (!(spi instanceof KeyStoreCryptoOperation)) {
            throw new IllegalArgumentException(
                    "Crypto primitive not backed by AndroidKeyStore provider: "
                            + cryptoPrimitive
                            + ", spi: "
                            + spi);
        }
        return ((KeyStoreCryptoOperation) spi).getOperationHandle();
    }

    static AndroidKeyStorePublicKey makeAndroidKeyStorePublicKeyFromKeyEntryResponse(
            KeyDescriptor descriptor,
            KeyMetadata metadata,
            KeyStoreSecurityLevel iSecurityLevel,
            int algorithm)
            throws UnrecoverableKeyException {
        if (metadata.certificate == null) {
            throw new UnrecoverableKeyException(
                    "Failed to obtain X.509 form of public key. Keystore has no public certificate"
                        + " stored.");
        }
        byte[] x509PublicCert = metadata.certificate;
        X509Certificate parsedX509Certificate = AndroidKeyStoreSpi.toCertificate(x509PublicCert);
        if (parsedX509Certificate == null) {
            throw new UnrecoverableKeyException(
                    "Failed to parse the X.509 certificate containing the public key. This likely"
                        + " indicates a hardware problem.");
        }
        PublicKey publicKey = parsedX509Certificate.getPublicKey();
        String jcaKeyAlgorithm = publicKey.getAlgorithm();
        if (KeyProperties.KEY_ALGORITHM_EC.equalsIgnoreCase(jcaKeyAlgorithm)) {
            return new AndroidKeyStoreECPublicKey(
                    descriptor, metadata, iSecurityLevel, (ECPublicKey) publicKey);
        }
        if ("RSA".equalsIgnoreCase(jcaKeyAlgorithm)) {
            return new AndroidKeyStoreRSAPublicKey(
                    descriptor, metadata, iSecurityLevel, (RSAPublicKey) publicKey);
        }
        if (ED25519_OID.equalsIgnoreCase(jcaKeyAlgorithm)) {
            byte[] publicKeyEncoded = publicKey.getEncoded();
            return new AndroidKeyStoreEdECPublicKey(
                    descriptor, metadata, ED25519_OID, iSecurityLevel, publicKeyEncoded);
        }
        if ("XDH".equalsIgnoreCase(jcaKeyAlgorithm)) {
            return new AndroidKeyStoreXDHPublicKey(
                    descriptor, metadata, "XDH", iSecurityLevel, publicKey.getEncoded());
        }
        throw new ProviderException(
                "Unsupported Android Keystore public key algorithm: " + jcaKeyAlgorithm);
    }

    public static AndroidKeyStorePublicKey loadAndroidKeyStorePublicKeyFromKeystore(
            KeyStore2 keyStore, String privateKeyAlias, int namespace)
            throws UnrecoverableKeyException, KeyPermanentlyInvalidatedException {
        AndroidKeyStoreKey key =
                loadAndroidKeyStoreKeyFromKeystore(keyStore, privateKeyAlias, namespace);
        if (key instanceof AndroidKeyStorePublicKey) {
            return (AndroidKeyStorePublicKey) key;
        }
        throw new UnrecoverableKeyException("No asymmetric key found by the given alias.");
    }

    public static KeyPair loadAndroidKeyStoreKeyPairFromKeystore(
            KeyStore2 keyStore, KeyDescriptor descriptor)
            throws UnrecoverableKeyException, KeyPermanentlyInvalidatedException {
        AndroidKeyStoreKey key = loadAndroidKeyStoreKeyFromKeystore(keyStore, descriptor);
        if (key instanceof AndroidKeyStorePublicKey) {
            AndroidKeyStorePublicKey publicKey = (AndroidKeyStorePublicKey) key;
            return new KeyPair(publicKey, publicKey.getPrivateKey());
        }
        throw new UnrecoverableKeyException("No asymmetric key found by the given alias.");
    }

    public static AndroidKeyStorePrivateKey loadAndroidKeyStorePrivateKeyFromKeystore(
            KeyStore2 keyStore, String privateKeyAlias, int namespace)
            throws UnrecoverableKeyException, KeyPermanentlyInvalidatedException {
        AndroidKeyStoreKey key =
                loadAndroidKeyStoreKeyFromKeystore(keyStore, privateKeyAlias, namespace);
        if (key instanceof AndroidKeyStorePublicKey) {
            return ((AndroidKeyStorePublicKey) key).getPrivateKey();
        }
        throw new UnrecoverableKeyException("No asymmetric key found by the given alias.");
    }

    public static SecretKey loadAndroidKeyStoreSecretKeyFromKeystore(
            KeyStore2 keyStore, KeyDescriptor descriptor)
            throws UnrecoverableKeyException, KeyPermanentlyInvalidatedException {
        Key loadAndroidKeyStoreKeyFromKeystore =
                loadAndroidKeyStoreKeyFromKeystore(keyStore, descriptor);
        if (loadAndroidKeyStoreKeyFromKeystore instanceof SecretKey) {
            return (SecretKey) loadAndroidKeyStoreKeyFromKeystore;
        }
        throw new UnrecoverableKeyException("No secret key found by the given alias.");
    }

    private static AndroidKeyStoreSecretKey makeAndroidKeyStoreSecretKeyFromKeyEntryResponse(
            KeyDescriptor descriptor, KeyEntryResponse response, int algorithm, int digest)
            throws UnrecoverableKeyException {
        try {
            String keyAlgorithmString =
                    KeyProperties.KeyAlgorithm.fromKeymasterSecretKeyAlgorithm(algorithm, digest);
            return new AndroidKeyStoreSecretKey(
                    descriptor,
                    response.metadata,
                    keyAlgorithmString,
                    new KeyStoreSecurityLevel(response.iSecurityLevel));
        } catch (IllegalArgumentException e) {
            throw ((UnrecoverableKeyException)
                    new UnrecoverableKeyException("Unsupported secret key type").initCause(e));
        }
    }

    public static AndroidKeyStoreKey loadAndroidKeyStoreKeyFromKeystore(
            KeyStore2 keyStore, String alias, int namespace)
            throws UnrecoverableKeyException, KeyPermanentlyInvalidatedException {
        KeyDescriptor descriptor = new KeyDescriptor();
        if (namespace == -1) {
            descriptor.nspace = -1L;
            descriptor.domain = 0;
        } else {
            descriptor.nspace = namespace;
            descriptor.domain = 2;
        }
        descriptor.alias = alias;
        descriptor.blob = null;
        AndroidKeyStoreKey key = loadAndroidKeyStoreKeyFromKeystore(keyStore, descriptor);
        if (key instanceof AndroidKeyStorePublicKey) {
            return ((AndroidKeyStorePublicKey) key).getPrivateKey();
        }
        return key;
    }

    private static AndroidKeyStoreKey loadAndroidKeyStoreKeyFromKeystore(
            KeyStore2 keyStore, KeyDescriptor descriptor)
            throws UnrecoverableKeyException, KeyPermanentlyInvalidatedException {
        try {
            KeyEntryResponse response = keyStore.getKeyEntry(descriptor);
            if (response.iSecurityLevel == null) {
                return null;
            }
            Integer keymasterAlgorithm = null;
            int keymasterDigest = -1;
            for (Authorization a : response.metadata.authorizations) {
                switch (a.keyParameter.tag) {
                    case 268435458:
                        keymasterAlgorithm = Integer.valueOf(a.keyParameter.value.getAlgorithm());
                        break;
                    case 536870917:
                        if (keymasterDigest == -1) {
                            keymasterDigest = a.keyParameter.value.getDigest();
                            break;
                        } else {
                            break;
                        }
                }
            }
            if (keymasterAlgorithm == null) {
                throw new UnrecoverableKeyException("Key algorithm unknown");
            }
            if (keymasterAlgorithm.intValue() == 128
                    || keymasterAlgorithm.intValue() == 32
                    || keymasterAlgorithm.intValue() == 33) {
                return makeAndroidKeyStoreSecretKeyFromKeyEntryResponse(
                        descriptor, response, keymasterAlgorithm.intValue(), keymasterDigest);
            }
            if (keymasterAlgorithm.intValue() == 1 || keymasterAlgorithm.intValue() == 3) {
                return makeAndroidKeyStorePublicKeyFromKeyEntryResponse(
                        descriptor,
                        response.metadata,
                        new KeyStoreSecurityLevel(response.iSecurityLevel),
                        keymasterAlgorithm.intValue());
            }
            throw new UnrecoverableKeyException("Key algorithm unknown");
        } catch (KeyStoreException e) {
            switch (e.getErrorCode()) {
                case 7:
                    return null;
                case 17:
                    throw new KeyPermanentlyInvalidatedException(
                            "User changed or deleted their auth credentials", e);
                default:
                    throw ((UnrecoverableKeyException)
                            new UnrecoverableKeyException("Failed to obtain information about key")
                                    .initCause(e));
            }
        }
    }
}

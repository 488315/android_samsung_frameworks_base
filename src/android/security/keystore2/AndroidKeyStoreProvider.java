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
        boolean zEquals = "true".equals(SystemProperties.get(DESEDE_SYSTEM_PROPERTY));
        if ("CN".equals(SystemProperties.get("ro.csc.countryiso_code")) && "system_server".equals(Process.myProcessName()) && !"FINISH".equals(SystemProperties.get("persist.sys.setupwizard"))) {
            try {
                Log.i(TAG, "original rkp_hostname : " + SystemProperties.get("remote_provisioning.hostname"));
                SystemProperties.set("remote_provisioning.hostname", "");
            } catch (Exception e) {
                Log.e(TAG, "Failed to set remote_provisioning.hostname : " + e.getClass().getSimpleName(), e);
            }
            if ("".equals(SystemProperties.get("remote_provisioning.hostname"))) {
                Log.i(TAG, "remote_provisioning.hostname is empty. setting complete!");
            }
        }
        put("KeyStore.AndroidKeyStore", "android.security.keystore2.AndroidKeyStoreSpi");
        put("KeyPairGenerator.EC", "android.security.keystore2.AndroidKeyStoreKeyPairGeneratorSpi$EC");
        put("KeyPairGenerator.RSA", "android.security.keystore2.AndroidKeyStoreKeyPairGeneratorSpi$RSA");
        put("KeyPairGenerator.XDH", "android.security.keystore2.AndroidKeyStoreKeyPairGeneratorSpi$XDH");
        put("KeyPairGenerator.ED25519", "android.security.keystore2.AndroidKeyStoreKeyPairGeneratorSpi$ED25519");
        putKeyFactoryImpl(KeyProperties.KEY_ALGORITHM_EC);
        putKeyFactoryImpl("RSA");
        putKeyFactoryImpl("XDH");
        putKeyFactoryImpl("ED25519");
        put("KeyGenerator.AES", "android.security.keystore2.AndroidKeyStoreKeyGeneratorSpi$AES");
        put("KeyGenerator.HmacSHA1", "android.security.keystore2.AndroidKeyStoreKeyGeneratorSpi$HmacSHA1");
        put("KeyGenerator.HmacSHA224", "android.security.keystore2.AndroidKeyStoreKeyGeneratorSpi$HmacSHA224");
        put("KeyGenerator.HmacSHA256", "android.security.keystore2.AndroidKeyStoreKeyGeneratorSpi$HmacSHA256");
        put("KeyGenerator.HmacSHA384", "android.security.keystore2.AndroidKeyStoreKeyGeneratorSpi$HmacSHA384");
        put("KeyGenerator.HmacSHA512", "android.security.keystore2.AndroidKeyStoreKeyGeneratorSpi$HmacSHA512");
        if (zEquals) {
            put("KeyGenerator.DESede", "android.security.keystore2.AndroidKeyStoreKeyGeneratorSpi$DESede");
        }
        put("KeyAgreement.ECDH", "android.security.keystore2.AndroidKeyStoreKeyAgreementSpi$ECDH");
        put("KeyAgreement.XDH", "android.security.keystore2.AndroidKeyStoreKeyAgreementSpi$XDH");
        putSecretKeyFactoryImpl("AES");
        if (zEquals) {
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
        int i = 0;
        while (true) {
            if (i >= providers.length) {
                i = -1;
                break;
            } else if (BouncyCastleProvider.PROVIDER_NAME.equals(providers[i].getName())) {
                break;
            } else {
                i++;
            }
        }
        Security.addProvider(new AndroidKeyStoreProvider());
        AndroidKeyStoreBCWorkaroundProvider androidKeyStoreBCWorkaroundProvider = new AndroidKeyStoreBCWorkaroundProvider();
        if (i != -1) {
            Security.insertProviderAt(androidKeyStoreBCWorkaroundProvider, i + 1);
        } else {
            Security.addProvider(androidKeyStoreBCWorkaroundProvider);
        }
    }

    private void putSecretKeyFactoryImpl(String str) {
        put("SecretKeyFactory." + str, "android.security.keystore2.AndroidKeyStoreSecretKeyFactorySpi");
    }

    private void putKeyFactoryImpl(String str) {
        put("KeyFactory." + str, "android.security.keystore2.AndroidKeyStoreKeyFactorySpi");
    }

    public static long getKeyStoreOperationHandle(Object obj) {
        Object currentSpi;
        obj.getClass();
        if (obj instanceof Signature) {
            currentSpi = ((Signature) obj).getCurrentSpi();
        } else if (obj instanceof Mac) {
            currentSpi = ((Mac) obj).getCurrentSpi();
        } else if (obj instanceof Cipher) {
            currentSpi = ((Cipher) obj).getCurrentSpi();
        } else if (obj instanceof KeyAgreement) {
            currentSpi = ((KeyAgreement) obj).getCurrentSpi();
        } else {
            throw new IllegalArgumentException("Unsupported crypto primitive: " + obj + ". Supported: Signature, Mac, Cipher");
        }
        if (currentSpi == null) {
            throw new IllegalStateException("Crypto primitive not initialized");
        }
        if (!(currentSpi instanceof KeyStoreCryptoOperation)) {
            throw new IllegalArgumentException("Crypto primitive not backed by AndroidKeyStore provider: " + obj + ", spi: " + currentSpi);
        }
        return ((KeyStoreCryptoOperation) currentSpi).getOperationHandle();
    }

    static AndroidKeyStorePublicKey makeAndroidKeyStorePublicKeyFromKeyEntryResponse(KeyDescriptor keyDescriptor, KeyMetadata keyMetadata, KeyStoreSecurityLevel keyStoreSecurityLevel, int i) throws UnrecoverableKeyException {
        if (keyMetadata.certificate == null) {
            throw new UnrecoverableKeyException("Failed to obtain X.509 form of public key. Keystore has no public certificate stored.");
        }
        X509Certificate certificate = AndroidKeyStoreSpi.toCertificate(keyMetadata.certificate);
        if (certificate == null) {
            throw new UnrecoverableKeyException("Failed to parse the X.509 certificate containing the public key. This likely indicates a hardware problem.");
        }
        PublicKey publicKey = certificate.getPublicKey();
        String algorithm = publicKey.getAlgorithm();
        if (KeyProperties.KEY_ALGORITHM_EC.equalsIgnoreCase(algorithm)) {
            return new AndroidKeyStoreECPublicKey(keyDescriptor, keyMetadata, keyStoreSecurityLevel, (ECPublicKey) publicKey);
        }
        if ("RSA".equalsIgnoreCase(algorithm)) {
            return new AndroidKeyStoreRSAPublicKey(keyDescriptor, keyMetadata, keyStoreSecurityLevel, (RSAPublicKey) publicKey);
        }
        if (ED25519_OID.equalsIgnoreCase(algorithm)) {
            return new AndroidKeyStoreEdECPublicKey(keyDescriptor, keyMetadata, ED25519_OID, keyStoreSecurityLevel, publicKey.getEncoded());
        }
        if ("XDH".equalsIgnoreCase(algorithm)) {
            return new AndroidKeyStoreXDHPublicKey(keyDescriptor, keyMetadata, "XDH", keyStoreSecurityLevel, publicKey.getEncoded());
        }
        throw new ProviderException("Unsupported Android Keystore public key algorithm: " + algorithm);
    }

    public static AndroidKeyStorePublicKey loadAndroidKeyStorePublicKeyFromKeystore(KeyStore2 keyStore2, String str, int i) throws KeyPermanentlyInvalidatedException, UnrecoverableKeyException {
        AndroidKeyStoreKey androidKeyStoreKeyLoadAndroidKeyStoreKeyFromKeystore = loadAndroidKeyStoreKeyFromKeystore(keyStore2, str, i);
        if (androidKeyStoreKeyLoadAndroidKeyStoreKeyFromKeystore instanceof AndroidKeyStorePublicKey) {
            return (AndroidKeyStorePublicKey) androidKeyStoreKeyLoadAndroidKeyStoreKeyFromKeystore;
        }
        throw new UnrecoverableKeyException("No asymmetric key found by the given alias.");
    }

    public static KeyPair loadAndroidKeyStoreKeyPairFromKeystore(KeyStore2 keyStore2, KeyDescriptor keyDescriptor) throws KeyPermanentlyInvalidatedException, UnrecoverableKeyException {
        AndroidKeyStoreKey androidKeyStoreKeyLoadAndroidKeyStoreKeyFromKeystore = loadAndroidKeyStoreKeyFromKeystore(keyStore2, keyDescriptor);
        if (androidKeyStoreKeyLoadAndroidKeyStoreKeyFromKeystore instanceof AndroidKeyStorePublicKey) {
            AndroidKeyStorePublicKey androidKeyStorePublicKey = (AndroidKeyStorePublicKey) androidKeyStoreKeyLoadAndroidKeyStoreKeyFromKeystore;
            return new KeyPair(androidKeyStorePublicKey, androidKeyStorePublicKey.getPrivateKey());
        }
        throw new UnrecoverableKeyException("No asymmetric key found by the given alias.");
    }

    public static AndroidKeyStorePrivateKey loadAndroidKeyStorePrivateKeyFromKeystore(KeyStore2 keyStore2, String str, int i) throws KeyPermanentlyInvalidatedException, UnrecoverableKeyException {
        AndroidKeyStoreKey androidKeyStoreKeyLoadAndroidKeyStoreKeyFromKeystore = loadAndroidKeyStoreKeyFromKeystore(keyStore2, str, i);
        if (androidKeyStoreKeyLoadAndroidKeyStoreKeyFromKeystore instanceof AndroidKeyStorePublicKey) {
            return ((AndroidKeyStorePublicKey) androidKeyStoreKeyLoadAndroidKeyStoreKeyFromKeystore).getPrivateKey();
        }
        throw new UnrecoverableKeyException("No asymmetric key found by the given alias.");
    }

    public static SecretKey loadAndroidKeyStoreSecretKeyFromKeystore(KeyStore2 keyStore2, KeyDescriptor keyDescriptor) throws KeyPermanentlyInvalidatedException, UnrecoverableKeyException {
        Key keyLoadAndroidKeyStoreKeyFromKeystore = loadAndroidKeyStoreKeyFromKeystore(keyStore2, keyDescriptor);
        if (keyLoadAndroidKeyStoreKeyFromKeystore instanceof SecretKey) {
            return (SecretKey) keyLoadAndroidKeyStoreKeyFromKeystore;
        }
        throw new UnrecoverableKeyException("No secret key found by the given alias.");
    }

    private static AndroidKeyStoreSecretKey makeAndroidKeyStoreSecretKeyFromKeyEntryResponse(KeyDescriptor keyDescriptor, KeyEntryResponse keyEntryResponse, int i, int i2) throws UnrecoverableKeyException {
        try {
            return new AndroidKeyStoreSecretKey(keyDescriptor, keyEntryResponse.metadata, KeyProperties.KeyAlgorithm.fromKeymasterSecretKeyAlgorithm(i, i2), new KeyStoreSecurityLevel(keyEntryResponse.iSecurityLevel));
        } catch (IllegalArgumentException e) {
            throw ((UnrecoverableKeyException) new UnrecoverableKeyException("Unsupported secret key type").initCause(e));
        }
    }

    public static AndroidKeyStoreKey loadAndroidKeyStoreKeyFromKeystore(KeyStore2 keyStore2, String str, int i) throws KeyPermanentlyInvalidatedException, UnrecoverableKeyException {
        int i2;
        if (i == -1) {
            i2 = 0;
            i = -1;
        } else {
            i2 = 2;
        }
        return loadAndroidKeyStoreKeyFromKeystore(keyStore2, str, i, i2);
    }

    public static AndroidKeyStoreKey loadAndroidKeyStoreKeyFromKeystore(KeyStore2 keyStore2, String str, long j, int i) throws KeyPermanentlyInvalidatedException, UnrecoverableKeyException {
        KeyDescriptor keyDescriptor = new KeyDescriptor();
        keyDescriptor.nspace = j;
        keyDescriptor.domain = i;
        keyDescriptor.alias = str;
        keyDescriptor.blob = null;
        AndroidKeyStoreKey androidKeyStoreKeyLoadAndroidKeyStoreKeyFromKeystore = loadAndroidKeyStoreKeyFromKeystore(keyStore2, keyDescriptor);
        return androidKeyStoreKeyLoadAndroidKeyStoreKeyFromKeystore instanceof AndroidKeyStorePublicKey ? ((AndroidKeyStorePublicKey) androidKeyStoreKeyLoadAndroidKeyStoreKeyFromKeystore).getPrivateKey() : androidKeyStoreKeyLoadAndroidKeyStoreKeyFromKeystore;
    }

    private static AndroidKeyStoreKey loadAndroidKeyStoreKeyFromKeystore(KeyStore2 keyStore2, KeyDescriptor keyDescriptor) throws KeyPermanentlyInvalidatedException, UnrecoverableKeyException {
        Integer numValueOf = null;
        try {
            KeyEntryResponse keyEntry = keyStore2.getKeyEntry(keyDescriptor);
            if (keyEntry.iSecurityLevel == null) {
                return null;
            }
            int digest = -1;
            for (Authorization authorization : keyEntry.metadata.authorizations) {
                int i = authorization.keyParameter.tag;
                if (i == 268435458) {
                    numValueOf = Integer.valueOf(authorization.keyParameter.value.getAlgorithm());
                } else if (i == 536870917 && digest == -1) {
                    digest = authorization.keyParameter.value.getDigest();
                }
            }
            if (numValueOf == null) {
                throw new UnrecoverableKeyException("Key algorithm unknown");
            }
            if (numValueOf.intValue() == 128 || numValueOf.intValue() == 32 || numValueOf.intValue() == 33) {
                return makeAndroidKeyStoreSecretKeyFromKeyEntryResponse(keyDescriptor, keyEntry, numValueOf.intValue(), digest);
            }
            if (numValueOf.intValue() == 1 || numValueOf.intValue() == 3) {
                return makeAndroidKeyStorePublicKeyFromKeyEntryResponse(keyDescriptor, keyEntry.metadata, new KeyStoreSecurityLevel(keyEntry.iSecurityLevel), numValueOf.intValue());
            }
            throw new UnrecoverableKeyException("Key algorithm unknown");
        } catch (KeyStoreException e) {
            int errorCode = e.getErrorCode();
            if (errorCode == 7) {
                return null;
            }
            if (errorCode == 17) {
                throw new KeyPermanentlyInvalidatedException("User changed or deleted their auth credentials", e);
            }
            throw ((UnrecoverableKeyException) new UnrecoverableKeyException("Failed to obtain information about key").initCause(e));
        }
    }
}

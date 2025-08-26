package android.security.keystore2;

import android.os.storage.VolumeInfo;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyInfo;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactorySpi;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

/* loaded from: classes3.dex */
public class AndroidKeyStoreKeyFactorySpi extends KeyFactorySpi {
    @Override // java.security.KeyFactorySpi
    protected <T extends KeySpec> T engineGetKeySpec(Key key, Class<T> cls) throws InvalidKeySpecException {
        if (key == null) {
            throw new InvalidKeySpecException("key == null");
        }
        boolean z = key instanceof AndroidKeyStorePrivateKey;
        if (!z && !(key instanceof AndroidKeyStorePublicKey)) {
            throw new InvalidKeySpecException("Unsupported key type: " + key.getClass().getName() + ". This KeyFactory supports only Android Keystore asymmetric keys");
        }
        if (cls == null) {
            throw new InvalidKeySpecException("keySpecClass == null");
        }
        if (KeyInfo.class.equals(cls)) {
            if (!z) {
                throw new InvalidKeySpecException("Unsupported key type: " + key.getClass().getName() + ". KeyInfo can be obtained only for Android Keystore private keys");
            }
            return AndroidKeyStoreSecretKeyFactorySpi.getKeyInfo((AndroidKeyStorePrivateKey) key);
        }
        if (X509EncodedKeySpec.class.equals(cls)) {
            if (!(key instanceof AndroidKeyStorePublicKey)) {
                throw new InvalidKeySpecException("Unsupported key type: " + key.getClass().getName() + ". X509EncodedKeySpec can be obtained only for Android Keystore public keys");
            }
            return new X509EncodedKeySpec(((AndroidKeyStorePublicKey) key).getEncoded());
        }
        if (PKCS8EncodedKeySpec.class.equals(cls)) {
            if (z) {
                throw new InvalidKeySpecException("Key material export of Android Keystore private keys is not supported");
            }
            throw new InvalidKeySpecException("Cannot export key material of public key in PKCS#8 format. Only X.509 format (X509EncodedKeySpec) supported for public keys.");
        }
        boolean zEquals = RSAPublicKeySpec.class.equals(cls);
        String str = VolumeInfo.ID_PRIVATE_INTERNAL;
        if (zEquals) {
            if (key instanceof AndroidKeyStoreRSAPublicKey) {
                AndroidKeyStoreRSAPublicKey androidKeyStoreRSAPublicKey = (AndroidKeyStoreRSAPublicKey) key;
                return new RSAPublicKeySpec(androidKeyStoreRSAPublicKey.getModulus(), androidKeyStoreRSAPublicKey.getPublicExponent());
            }
            StringBuilder sb = new StringBuilder("Obtaining RSAPublicKeySpec not supported for ");
            sb.append(key.getAlgorithm());
            sb.append(" ");
            if (!z) {
                str = "public";
            }
            sb.append(str);
            sb.append(" key");
            throw new InvalidKeySpecException(sb.toString());
        }
        if (ECPublicKeySpec.class.equals(cls)) {
            if (key instanceof AndroidKeyStoreECPublicKey) {
                AndroidKeyStoreECPublicKey androidKeyStoreECPublicKey = (AndroidKeyStoreECPublicKey) key;
                return new ECPublicKeySpec(androidKeyStoreECPublicKey.getW(), androidKeyStoreECPublicKey.getParams());
            }
            StringBuilder sb2 = new StringBuilder("Obtaining ECPublicKeySpec not supported for ");
            sb2.append(key.getAlgorithm());
            sb2.append(" ");
            if (!z) {
                str = "public";
            }
            sb2.append(str);
            sb2.append(" key");
            throw new InvalidKeySpecException(sb2.toString());
        }
        throw new InvalidKeySpecException("Unsupported key spec: " + cls.getName());
    }

    @Override // java.security.KeyFactorySpi
    protected PrivateKey engineGeneratePrivate(KeySpec keySpec) throws InvalidKeySpecException {
        throw new InvalidKeySpecException("To generate a key pair in Android Keystore, use KeyPairGenerator initialized with " + KeyGenParameterSpec.class.getName());
    }

    @Override // java.security.KeyFactorySpi
    protected PublicKey engineGeneratePublic(KeySpec keySpec) throws InvalidKeySpecException {
        throw new InvalidKeySpecException("To generate a key pair in Android Keystore, use KeyPairGenerator initialized with " + KeyGenParameterSpec.class.getName());
    }

    @Override // java.security.KeyFactorySpi
    protected Key engineTranslateKey(Key key) throws InvalidKeyException {
        if (key == null) {
            throw new InvalidKeyException("key == null");
        }
        if ((key instanceof AndroidKeyStorePrivateKey) || (key instanceof AndroidKeyStorePublicKey)) {
            return key;
        }
        throw new InvalidKeyException("To import a key into Android Keystore, use KeyStore.setEntry");
    }
}

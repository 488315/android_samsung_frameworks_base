package com.samsung.ucm.keystore;

import android.security.keystore.KeyProperties;

import com.samsung.android.security.mdf.MdfUtils;

import java.security.Provider;

/* loaded from: classes6.dex */
public class KnoxUcmKeyStoreProvider extends Provider {
    private static final String KEYSTORE_PRIVATE_KEY_CLASS_NAME =
            "com.samsung.ucm.keystore.UcmKeyStorePrivateKey";
    private static final String KEYSTORE_SECRET_KEY_CLASS_NAME =
            "com.samsung.ucm.keystore.UcmKeyStoreSecretKey";
    private static final String PACKAGE_NAME = "com.samsung.ucm.keystore";
    public static final String PROPERTY_PERSIST_UCM_CRYPTO = "persist.security.ucmcrypto";
    public static final String PROPERTY_UCM_CRYPTO = "security.ucmcrypto";
    public static final String PROVIDER_NAME = "UcmKeystore";

    public KnoxUcmKeyStoreProvider() {
        super(PROVIDER_NAME, 1.0d, "Samsung UCM Keystore Provider");
        putSymmetricCipherImpl(
                "AES/CBC/NoPadding",
                "com.samsung.ucm.keystore.UcmKeyStoreCipherSpi$AesCbcNoPadding");
        putSymmetricCipherImpl(
                "AES/CBC/ISO9797-M2",
                "com.samsung.ucm.keystore.UcmKeyStoreCipherSpi$AesCbcIso9797M2");
        putSymmetricCipherImpl(
                MdfUtils.MDF_CIPHER_MODE,
                "com.samsung.ucm.keystore.UcmKeyStoreCipherSpi$AesGcmNoPadding");
        putAsymmetricCipherImpl(
                "RSA/ECB/PKCS1Padding",
                "com.samsung.ucm.keystore.UcmKeyStoreCipherSpi$PKCS1Padding");
        put("Alg.Alias.Cipher.RSA/None/PKCS1Padding", "RSA/ECB/PKCS1Padding");
        putAsymmetricCipherImpl(
                "RSA/ECB/OAEPPadding",
                "com.samsung.ucm.keystore.UcmKeyStoreCipherSpi$OAEPWithSHA1AndMGF1Padding");
        put("Alg.Alias.Cipher.RSA/None/OAEPPadding", "RSA/ECB/OAEPPadding");
        putAsymmetricCipherImpl(
                "RSA/ECB/OAEPWithSHA-1AndMGF1Padding",
                "com.samsung.ucm.keystore.UcmKeyStoreCipherSpi$OAEPWithSHA1AndMGF1Padding");
        put(
                "Alg.Alias.Cipher.RSA/None/OAEPWithSHA-1AndMGF1Padding",
                "RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
        putAsymmetricCipherImpl(
                "RSA/ECB/OAEPWithSHA-224AndMGF1Padding",
                "com.samsung.ucm.keystore.UcmKeyStoreCipherSpi$OAEPWithSHA224AndMGF1Padding");
        put(
                "Alg.Alias.Cipher.RSA/None/OAEPWithSHA-224AndMGF1Padding",
                "RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        putAsymmetricCipherImpl(
                "RSA/ECB/OAEPWithSHA-256AndMGF1Padding",
                "com.samsung.ucm.keystore.UcmKeyStoreCipherSpi$OAEPWithSHA256AndMGF1Padding");
        put(
                "Alg.Alias.Cipher.RSA/None/OAEPWithSHA-256AndMGF1Padding",
                "RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        putAsymmetricCipherImpl(
                "RSA/ECB/OAEPWithSHA-384AndMGF1Padding",
                "com.samsung.ucm.keystore.UcmKeyStoreCipherSpi$OAEPWithSHA384AndMGF1Padding");
        put(
                "Alg.Alias.Cipher.RSA/None/OAEPWithSHA-384AndMGF1Padding",
                "RSA/ECB/OAEPWithSHA-384AndMGF1Padding");
        putAsymmetricCipherImpl(
                "RSA/ECB/OAEPWithSHA-512AndMGF1Padding",
                "com.samsung.ucm.keystore.UcmKeyStoreCipherSpi$OAEPWithSHA512AndMGF1Padding");
        put(
                "Alg.Alias.Cipher.RSA/None/OAEPWithSHA-512AndMGF1Padding",
                "RSA/ECB/OAEPWithSHA-512AndMGF1Padding");
        putSignatureImpl(
                "MD5withRSA",
                "com.samsung.ucm.keystore.UcmKeyStoreSignatureSpi$MD5WithPKCS1Padding");
        put("Alg.Alias.Signature.MD5WithRSAEncryption", "MD5withRSA");
        put("Alg.Alias.Signature.MD5/RSA", "MD5withRSA");
        put("Alg.Alias.Signature.1.2.840.113549.1.1.4", "MD5withRSA");
        put("Alg.Alias.Signature.1.2.840.113549.2.5with1.2.840.113549.1.1.1", "MD5withRSA");
        putSignatureImpl(
                "SHA1withRSA",
                "com.samsung.ucm.keystore.UcmKeyStoreSignatureSpi$SHA1WithPKCS1Padding");
        put("Alg.Alias.Signature.SHA1WithRSAEncryption", "SHA1withRSA");
        put("Alg.Alias.Signature.SHA1/RSA", "SHA1withRSA");
        put("Alg.Alias.Signature.SHA-1/RSA", "SHA1withRSA");
        put("Alg.Alias.Signature.1.2.840.113549.1.1.5", "SHA1withRSA");
        put("Alg.Alias.Signature.1.3.14.3.2.26with1.2.840.113549.1.1.1", "SHA1withRSA");
        put("Alg.Alias.Signature.1.3.14.3.2.26with1.2.840.113549.1.1.5", "SHA1withRSA");
        put("Alg.Alias.Signature.1.3.14.3.2.29", "SHA1withRSA");
        putSignatureImpl(
                "SHA224withRSA",
                "com.samsung.ucm.keystore.UcmKeyStoreSignatureSpi$SHA224WithPKCS1Padding");
        put("Alg.Alias.Signature.SHA224WithRSAEncryption", "SHA224withRSA");
        put("Alg.Alias.Signature.1.2.840.113549.1.1.11", "SHA224withRSA");
        put("Alg.Alias.Signature.2.16.840.1.101.3.4.2.4with1.2.840.113549.1.1.1", "SHA224withRSA");
        put("Alg.Alias.Signature.2.16.840.1.101.3.4.2.4with1.2.840.113549.1.1.11", "SHA224withRSA");
        putSignatureImpl(
                "SHA256withRSA",
                "com.samsung.ucm.keystore.UcmKeyStoreSignatureSpi$SHA256WithPKCS1Padding");
        put("Alg.Alias.Signature.SHA256WithRSAEncryption", "SHA256withRSA");
        put("Alg.Alias.Signature.1.2.840.113549.1.1.11", "SHA256withRSA");
        put("Alg.Alias.Signature.2.16.840.1.101.3.4.2.1with1.2.840.113549.1.1.1", "SHA256withRSA");
        put("Alg.Alias.Signature.2.16.840.1.101.3.4.2.1with1.2.840.113549.1.1.11", "SHA256withRSA");
        putSignatureImpl(
                "SHA384withRSA",
                "com.samsung.ucm.keystore.UcmKeyStoreSignatureSpi$SHA384WithPKCS1Padding");
        put("Alg.Alias.Signature.SHA384WithRSAEncryption", "SHA384withRSA");
        put("Alg.Alias.Signature.1.2.840.113549.1.1.12", "SHA384withRSA");
        put("Alg.Alias.Signature.2.16.840.1.101.3.4.2.2with1.2.840.113549.1.1.1", "SHA384withRSA");
        putSignatureImpl(
                "SHA512withRSA",
                "com.samsung.ucm.keystore.UcmKeyStoreSignatureSpi$SHA512WithPKCS1Padding");
        put("Alg.Alias.Signature.SHA512WithRSAEncryption", "SHA512withRSA");
        put("Alg.Alias.Signature.1.2.840.113549.1.1.13", "SHA512withRSA");
        put("Alg.Alias.Signature.2.16.840.1.101.3.4.2.3with1.2.840.113549.1.1.1", "SHA512withRSA");
        putSignatureImpl(
                "SHA1withRSA/PSS",
                "com.samsung.ucm.keystore.UcmKeyStoreSignatureSpi$SHA1WithPSSPadding");
        putSignatureImpl(
                "SHA224withRSA/PSS",
                "com.samsung.ucm.keystore.UcmKeyStoreSignatureSpi$SHA224WithPSSPadding");
        putSignatureImpl(
                "SHA256withRSA/PSS",
                "com.samsung.ucm.keystore.UcmKeyStoreSignatureSpi$SHA256WithPSSPadding");
        putSignatureImpl(
                "SHA384withRSA/PSS",
                "com.samsung.ucm.keystore.UcmKeyStoreSignatureSpi$SHA384WithPSSPadding");
        putSignatureImpl(
                "SHA512withRSA/PSS",
                "com.samsung.ucm.keystore.UcmKeyStoreSignatureSpi$SHA512WithPSSPadding");
        putSignatureImpl(
                "NONEwithECDSA", "com.samsung.ucm.keystore.UcmKeyStoreSignatureSpi$NONEwithECDSA");
        putSignatureImpl(
                "SHA1withECDSA", "com.samsung.ucm.keystore.UcmKeyStoreSignatureSpi$SHA1withECDSA");
        put("Alg.Alias.Signature.ECDSA", "SHA1withECDSA");
        put("Alg.Alias.Signature.ECDSAwithSHA1", "SHA1withECDSA");
        put("Alg.Alias.Signature.1.2.840.10045.4.1", "SHA1withECDSA");
        put("Alg.Alias.Signature.1.3.14.3.2.26with1.2.840.10045.2.1", "SHA1withECDSA");
        putSignatureImpl(
                "SHA224withECDSA",
                "com.samsung.ucm.keystore.UcmKeyStoreSignatureSpi$SHA224withECDSA");
        put("Alg.Alias.Signature.1.2.840.10045.4.3.1", "SHA224withECDSA");
        put("Alg.Alias.Signature.2.16.840.1.101.3.4.2.4with1.2.840.10045.2.1", "SHA224withECDSA");
        putSignatureImpl(
                "SHA256withECDSA",
                "com.samsung.ucm.keystore.UcmKeyStoreSignatureSpi$SHA256withECDSA");
        put("Alg.Alias.Signature.1.2.840.10045.4.3.2", "SHA256withECDSA");
        put("Alg.Alias.Signature.2.16.840.1.101.3.4.2.1with1.2.840.10045.2.1", "SHA256withECDSA");
        putSignatureImpl(
                "SHA384withECDSA",
                "com.samsung.ucm.keystore.UcmKeyStoreSignatureSpi$SHA384withECDSA");
        put("Alg.Alias.Signature.1.2.840.10045.4.3.3", "SHA384withECDSA");
        put("Alg.Alias.Signature.2.16.840.1.101.3.4.2.2with1.2.840.10045.2.1", "SHA384withECDSA");
        putSignatureImpl(
                "SHA512withECDSA",
                "com.samsung.ucm.keystore.UcmKeyStoreSignatureSpi$SHA512withECDSA");
        put("Alg.Alias.Signature.1.2.840.10045.4.3.4", "SHA512withECDSA");
        put("Alg.Alias.Signature.2.16.840.1.101.3.4.2.3with1.2.840.10045.2.1", "SHA512withECDSA");
        putSignatureImpl(
                "SHA1withECGDSA",
                "com.samsung.ucm.keystore.UcmKeyStoreSignatureSpi$SHA1withECGDSA");
        putSignatureImpl(
                "SHA224withECGDSA",
                "com.samsung.ucm.keystore.UcmKeyStoreSignatureSpi$SHA224withECGDSA");
        putSignatureImpl(
                "SHA256withECGDSA",
                "com.samsung.ucm.keystore.UcmKeyStoreSignatureSpi$SHA256withECGDSA");
        putSignatureImpl(
                "SHA384withECGDSA",
                "com.samsung.ucm.keystore.UcmKeyStoreSignatureSpi$SHA384withECGDSA");
        putSignatureImpl(
                "SHA512withECGDSA",
                "com.samsung.ucm.keystore.UcmKeyStoreSignatureSpi$SHA512withECGDSA");
        putMacImpl("HmacMD5", "com.samsung.ucm.keystore.UcmKeyStoreMacSpi$HmacMD5");
        putMacImpl(
                KeyProperties.KEY_ALGORITHM_HMAC_SHA1,
                "com.samsung.ucm.keystore.UcmKeyStoreMacSpi$HmacSHA1");
        putMacImpl(
                KeyProperties.KEY_ALGORITHM_HMAC_SHA224,
                "com.samsung.ucm.keystore.UcmKeyStoreMacSpi$HmacSHA224");
        putMacImpl(
                KeyProperties.KEY_ALGORITHM_HMAC_SHA256,
                "com.samsung.ucm.keystore.UcmKeyStoreMacSpi$HmacSHA256");
        putMacImpl(
                KeyProperties.KEY_ALGORITHM_HMAC_SHA384,
                "com.samsung.ucm.keystore.UcmKeyStoreMacSpi$HmacSHA384");
        putMacImpl(
                KeyProperties.KEY_ALGORITHM_HMAC_SHA512,
                "com.samsung.ucm.keystore.UcmKeyStoreMacSpi$HmacSHA512");
    }

    private void putSymmetricCipherImpl(String transformation, String implClass) {
        put("Cipher." + transformation, implClass);
        put("Cipher." + transformation + " SupportedKeyClasses", KEYSTORE_SECRET_KEY_CLASS_NAME);
    }

    private void putAsymmetricCipherImpl(String transformation, String implClass) {
        put("Cipher." + transformation, implClass);
        put("Cipher." + transformation + " SupportedKeyClasses", KEYSTORE_PRIVATE_KEY_CLASS_NAME);
    }

    private void putSignatureImpl(String algorithm, String implClass) {
        put("Signature." + algorithm, implClass);
        put("Signature." + algorithm + " SupportedKeyClasses", KEYSTORE_PRIVATE_KEY_CLASS_NAME);
    }

    private void putMacImpl(String transformation, String implClass) {
        put("Mac." + transformation, implClass);
        put("Mac." + transformation + " SupportedKeyClasses", KEYSTORE_SECRET_KEY_CLASS_NAME);
    }
}

package com.samsung.android.knox.ucm.plugin.agent;

import android.content.Context;
import android.os.Bundle;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.KeyPairGeneratorSpi;
import java.security.KeyStore;
import java.security.KeyStoreSpi;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SecureRandomSpi;
import java.security.SignatureSpi;
import java.security.cert.Certificate;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Date;
import javax.crypto.CipherSpi;
import javax.crypto.KeyGeneratorSpi;
import javax.crypto.MacSpi;
import javax.crypto.SecretKey;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class UcmAgentProviderImpl extends Provider {
    public static final String CIPHER = "Cipher";
    public static final String CIPHER_RSA_ECB_NOPADDING = "RSA/ECB/NoPadding";
    public static final String CIPHER_RSA_ECB_PKCS1PADDING = "RSA/ECB/PKCS1Padding";
    public static final String KEYPAIRGENERATOR = "KeyPairGenerator";
    public static final String KEYPAIRGENERATOR_RSA = "RSA";
    public static final String KEYSTORE = "KeyStore";
    public static final String KEYSTORE_TYPE = "KNOX";
    public static String KEY_EXTRA_BLOCK_MODES = "extra_block_modes";
    public static String KEY_EXTRA_DIGESTS = "extra_digests";
    public static String KEY_EXTRA_EC_CURVE_NAME = "extra_ec_curve_name";
    public static String KEY_EXTRA_PURPOSE = "extra_purpose";
    public static String KEY_EXTRA_RANDOMIZED_ENCRYPTION = "extra_randomized_encryption";
    public static String KEY_EXTRA_SIGNATURE_PADDINGS = "extra_signature_paddings";
    private static final String PROVIDER_DESC = "Samsung Extension Keystore Provider Impl";
    private static final String PROVIDER_NAME = "AgentProviderImpl";
    private static final double PROVIDER_VERSION = 1.0d;
    public static final String SECURERANDOM = "SecureRandom";
    public static final String SECURERANDOM_SHA1PRNG = "SHA1PRNG";

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class AbstractProviderService extends Provider.Service {
        public AbstractProviderService(Provider provider, String str, String str2, String str3, Context context) {
            super(provider, str, str2, str3, null, null);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class UcmAgentCipherSpi extends CipherSpi implements UcmAgentSpiProperty {
        private int errorStatus = 0;
        private Bundle mProperty;

        @Override // javax.crypto.CipherSpi
        public int engineDoFinal(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
            throw new UnsupportedOperationException("Not supported");
        }

        @Override // javax.crypto.CipherSpi
        public abstract byte[] engineDoFinal(byte[] bArr, int i, int i2);

        @Override // javax.crypto.CipherSpi
        public int engineGetBlockSize() {
            throw new UnsupportedOperationException("Not supported");
        }

        @Override // javax.crypto.CipherSpi
        public byte[] engineGetIV() {
            throw new UnsupportedOperationException("Not supported");
        }

        @Override // javax.crypto.CipherSpi
        public int engineGetOutputSize(int i) {
            throw new UnsupportedOperationException("Not supported");
        }

        @Override // javax.crypto.CipherSpi
        public AlgorithmParameters engineGetParameters() {
            throw new UnsupportedOperationException("Not supported");
        }

        @Override // javax.crypto.CipherSpi
        public void engineInit(int i, Key key, AlgorithmParameters algorithmParameters, SecureRandom secureRandom) {
            throw new UnsupportedOperationException("Not supported");
        }

        @Override // javax.crypto.CipherSpi
        public abstract void engineInit(int i, Key key, SecureRandom secureRandom);

        @Override // javax.crypto.CipherSpi
        public abstract void engineInit(int i, Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom);

        @Override // javax.crypto.CipherSpi
        public byte[] engineUpdate(byte[] bArr, int i, int i2) {
            throw new UnsupportedOperationException("Not supported");
        }

        @Override // javax.crypto.CipherSpi
        public abstract void engineUpdateAAD(byte[] bArr, int i, int i2);

        @Override // com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl.UcmAgentSpiProperty
        public int getErrorCode() {
            return this.errorStatus;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl.UcmAgentSpiProperty
        public Bundle getProperty() {
            return this.mProperty;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl.UcmAgentSpiProperty
        public void setErrorCode(int i) {
            this.errorStatus = i;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl.UcmAgentSpiProperty
        public void setProperty(Bundle bundle) {
            this.mProperty = bundle;
        }

        @Override // javax.crypto.CipherSpi
        public int engineUpdate(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
            throw new UnsupportedOperationException("Not supported");
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class UcmAgentKeyGeneratorSpi extends KeyGeneratorSpi implements UcmAgentSpiProperty {
        private int errorStatus = 0;
        private Bundle mProperty;

        @Override // javax.crypto.KeyGeneratorSpi
        public abstract SecretKey engineGenerateKey();

        @Override // javax.crypto.KeyGeneratorSpi
        public void engineInit(SecureRandom secureRandom) {
            throw new UnsupportedOperationException("Not supported");
        }

        @Override // javax.crypto.KeyGeneratorSpi
        public abstract void engineInit(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom);

        @Override // com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl.UcmAgentSpiProperty
        public int getErrorCode() {
            return this.errorStatus;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl.UcmAgentSpiProperty
        public Bundle getProperty() {
            return this.mProperty;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl.UcmAgentSpiProperty
        public void setErrorCode(int i) {
            this.errorStatus = i;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl.UcmAgentSpiProperty
        public void setProperty(Bundle bundle) {
            this.mProperty = bundle;
        }

        @Override // javax.crypto.KeyGeneratorSpi
        public void engineInit(int i, SecureRandom secureRandom) {
            throw new UnsupportedOperationException("Not supported");
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class UcmAgentKeyPairGeneratorSpi extends KeyPairGeneratorSpi implements UcmAgentSpiProperty {
        private int errorStatus = 0;
        private Bundle mProperty;

        @Override // com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl.UcmAgentSpiProperty
        public int getErrorCode() {
            return this.errorStatus;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl.UcmAgentSpiProperty
        public Bundle getProperty() {
            return this.mProperty;
        }

        @Override // java.security.KeyPairGeneratorSpi
        public void initialize(int i, SecureRandom secureRandom) {
            throw new UnsupportedOperationException("Not supported");
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl.UcmAgentSpiProperty
        public void setErrorCode(int i) {
            this.errorStatus = i;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl.UcmAgentSpiProperty
        public void setProperty(Bundle bundle) {
            this.mProperty = bundle;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class UcmAgentKeyStoreSpi extends KeyStoreSpi implements UcmAgentSpiProperty {
        private int errorStatus = 0;
        private Bundle mProperty;

        @Override // java.security.KeyStoreSpi
        public String engineGetCertificateAlias(Certificate certificate) {
            throw new UnsupportedOperationException("Can not determine certificate alias");
        }

        @Override // java.security.KeyStoreSpi
        public Date engineGetCreationDate(String str) {
            throw new UnsupportedOperationException("Can not determine creation date");
        }

        @Override // java.security.KeyStoreSpi
        public void engineLoad(InputStream inputStream, char[] cArr) {
            throw new UnsupportedOperationException("please use engineLoad(LoadStoreParameter)");
        }

        @Override // java.security.KeyStoreSpi
        public abstract void engineLoad(KeyStore.LoadStoreParameter loadStoreParameter);

        @Override // java.security.KeyStoreSpi
        public void engineSetCertificateEntry(String str, Certificate certificate) {
            throw new UnsupportedOperationException("Please use engineSetEntry()");
        }

        @Override // java.security.KeyStoreSpi
        public abstract void engineSetEntry(String str, KeyStore.Entry entry, KeyStore.ProtectionParameter protectionParameter);

        @Override // java.security.KeyStoreSpi
        public void engineSetKeyEntry(String str, Key key, char[] cArr, Certificate[] certificateArr) {
            throw new UnsupportedOperationException("Please use engineSetEntry()");
        }

        @Override // java.security.KeyStoreSpi
        public void engineStore(OutputStream outputStream, char[] cArr) {
            throw new UnsupportedOperationException("Can not serialize to OutputStream");
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl.UcmAgentSpiProperty
        public int getErrorCode() {
            return this.errorStatus;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl.UcmAgentSpiProperty
        public Bundle getProperty() {
            return this.mProperty;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl.UcmAgentSpiProperty
        public void setErrorCode(int i) {
            this.errorStatus = i;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl.UcmAgentSpiProperty
        public void setProperty(Bundle bundle) {
            this.mProperty = bundle;
        }

        @Override // java.security.KeyStoreSpi
        public void engineSetKeyEntry(String str, byte[] bArr, Certificate[] certificateArr) {
            throw new UnsupportedOperationException("Can not determine encoding type");
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class UcmAgentMacSpi extends MacSpi implements UcmAgentSpiProperty {
        private int errorStatus = 0;
        private Bundle mProperty;

        @Override // javax.crypto.MacSpi
        public abstract byte[] engineDoFinal();

        @Override // javax.crypto.MacSpi
        public int engineGetMacLength() {
            throw new UnsupportedOperationException("Not supported");
        }

        @Override // javax.crypto.MacSpi
        public abstract void engineInit(Key key, AlgorithmParameterSpec algorithmParameterSpec);

        @Override // javax.crypto.MacSpi
        public void engineReset() {
            throw new UnsupportedOperationException("Not supported");
        }

        @Override // javax.crypto.MacSpi
        public void engineUpdate(byte b) {
            engineUpdate(new byte[]{b}, 0, 1);
        }

        @Override // javax.crypto.MacSpi
        public abstract void engineUpdate(byte[] bArr, int i, int i2);

        @Override // com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl.UcmAgentSpiProperty
        public int getErrorCode() {
            return this.errorStatus;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl.UcmAgentSpiProperty
        public Bundle getProperty() {
            return this.mProperty;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl.UcmAgentSpiProperty
        public void setErrorCode(int i) {
            this.errorStatus = i;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl.UcmAgentSpiProperty
        public void setProperty(Bundle bundle) {
            this.mProperty = bundle;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class UcmAgentSecureRandomSpi extends SecureRandomSpi implements UcmAgentSpiProperty {
        private int errorStatus = 0;
        private Bundle mProperty;

        @Override // java.security.SecureRandomSpi
        public abstract byte[] engineGenerateSeed(int i);

        public void engineMixSeed(byte[] bArr) {
            throw new UnsupportedOperationException("Not supported");
        }

        @Override // java.security.SecureRandomSpi
        public void engineNextBytes(byte[] bArr) {
            throw new UnsupportedOperationException("Not supported");
        }

        @Override // java.security.SecureRandomSpi
        public void engineSetSeed(byte[] bArr) {
            throw new UnsupportedOperationException("Not supported");
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl.UcmAgentSpiProperty
        public int getErrorCode() {
            return this.errorStatus;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl.UcmAgentSpiProperty
        public Bundle getProperty() {
            return this.mProperty;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl.UcmAgentSpiProperty
        public void setErrorCode(int i) {
            this.errorStatus = i;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl.UcmAgentSpiProperty
        public void setProperty(Bundle bundle) {
            this.mProperty = bundle;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class UcmAgentSignatureSpi extends SignatureSpi implements UcmAgentSpiProperty {
        private int errorStatus = 0;
        private Bundle mProperty;

        @Override // java.security.SignatureSpi
        public Object engineGetParameter(String str) {
            throw new UnsupportedOperationException("Not supported");
        }

        @Override // java.security.SignatureSpi
        public abstract void engineInitSign(PrivateKey privateKey);

        @Override // java.security.SignatureSpi
        public void engineInitVerify(PublicKey publicKey) {
            throw new UnsupportedOperationException("Not supported");
        }

        @Override // java.security.SignatureSpi
        public void engineSetParameter(String str, Object obj) {
            throw new UnsupportedOperationException("Not supported");
        }

        @Override // java.security.SignatureSpi
        public abstract byte[] engineSign();

        @Override // java.security.SignatureSpi
        public void engineUpdate(byte b) {
            throw new UnsupportedOperationException("Not supported");
        }

        @Override // java.security.SignatureSpi
        public abstract void engineUpdate(byte[] bArr, int i, int i2);

        @Override // java.security.SignatureSpi
        public boolean engineVerify(byte[] bArr) {
            throw new UnsupportedOperationException("Not supported");
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl.UcmAgentSpiProperty
        public int getErrorCode() {
            return this.errorStatus;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl.UcmAgentSpiProperty
        public Bundle getProperty() {
            return this.mProperty;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl.UcmAgentSpiProperty
        public void setErrorCode(int i) {
            this.errorStatus = i;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl.UcmAgentSpiProperty
        public void setProperty(Bundle bundle) {
            this.mProperty = bundle;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface UcmAgentSpiProperty {
        public static final String KEY_ADMIN_ID = "admin_id";
        public static final String KEY_ALGORITHM = "algorithm";
        public static final String KEY_CALLER_UID = "callerUid";
        public static final String KEY_ENTRY_TYPE = "entryType";
        public static final String KEY_EXTRA_ARGS = "extraArgs";
        public static final String KEY_ISALLOWWIFI = "allow_wifi";
        public static final String KEY_ISMANAGED = "ismanaged";
        public static final String KEY_IS_CALLER_MDM = "ismdm";
        public static final String KEY_KEYSIZE = "keysize";
        public static final String KEY_OWNER_ID = "ownerUid";
        public static final String KEY_REMOVABLE_USER_CERTIFICATES_LIST = "removable_user_certificates_list";
        public static final String KEY_RENEW_CERT = "renew";
        public static final String KEY_RESOURCE_ID = "resource";
        public static final String KEY_SECRET_KEY = "secret_key";
        public static final String KEY_STORAGE_OPTION = "ese_storage_option";
        public static final String KEY_USER_ID = "user_id";
        public static final int PRIVATE_RESOURCE = 2;
        public static final int SHARED_KEYCHAIN_RESOURCE = 1;
        public static final int SHARED_WIFI_RESOURCE = 3;
        public static final int STORE_INSIDE_CS = 101;
        public static final int STORE_OUTSIDE_CS = 102;
        public static final int UCS_ALL_RESOURCE = 4;

        int getErrorCode();

        Bundle getProperty();

        void setErrorCode(int i);

        void setProperty(Bundle bundle);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static final class UcsKeyPairGeneratorSpec implements AlgorithmParameterSpec {
        private final int mKeySize;
        private final String mKeystoreAlias;

        public UcsKeyPairGeneratorSpec(String str, int i) {
            this.mKeystoreAlias = str;
            this.mKeySize = i;
        }

        public int getKeySize() {
            return this.mKeySize;
        }

        public String getKeystoreAlias() {
            return this.mKeystoreAlias;
        }
    }

    public UcmAgentProviderImpl() {
        super(PROVIDER_NAME, 1.0d, PROVIDER_DESC);
    }

    public void putServiceImpl(Provider.Service service) {
        if (service != null) {
            putService(service);
        }
    }

    public void removeServiceImpl(Provider.Service service) {
        if (service != null) {
            removeService(service);
        }
    }
}

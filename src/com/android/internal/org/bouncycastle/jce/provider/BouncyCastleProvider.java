package com.android.internal.org.bouncycastle.jce.provider;

import android.media.ExifInterface;
import android.media.MediaMetrics;
import android.security.KeyChain;
import android.security.keystore.KeyProperties;
import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import com.android.internal.org.bouncycastle.crypto.CryptoServiceConstraintsException;
import com.android.internal.org.bouncycastle.crypto.CryptoServiceProperties;
import com.android.internal.org.bouncycastle.crypto.CryptoServicePurpose;
import com.android.internal.org.bouncycastle.crypto.CryptoServicesRegistrar;
import com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import com.android.internal.org.bouncycastle.jcajce.provider.config.ProviderConfiguration;
import com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.ClassUtil;
import com.android.internal.org.bouncycastle.jcajce.provider.util.AlgorithmProvider;
import com.android.internal.org.bouncycastle.jcajce.provider.util.AsymmetricKeyInfoConverter;
import java.io.IOException;
import java.security.AccessController;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PrivilegedAction;
import java.security.Provider;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/* loaded from: classes5.dex */
public final class BouncyCastleProvider extends Provider implements ConfigurableProvider {
    private static final String ASYMMETRIC_PACKAGE = "com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.";
    private static final String DIGEST_PACKAGE = "com.android.internal.org.bouncycastle.jcajce.provider.digest.";
    private static final String KEYSTORE_PACKAGE = "com.android.internal.org.bouncycastle.jcajce.provider.keystore.";
    private static final String SYMMETRIC_PACKAGE = "com.android.internal.org.bouncycastle.jcajce.provider.symmetric.";
    private final Provider privateProvider;
    private Map<String, Provider.Service> serviceMap;
    private static final Logger LOG = Logger.getLogger(BouncyCastleProvider.class.getName());
    private static String info = "BouncyCastle Security Provider v1.77";
    public static final ProviderConfiguration CONFIGURATION = new BouncyCastleProviderConfiguration();
    private static final Map keyInfoConverters = new HashMap();
    private static final Class revChkClass = ClassUtil.loadClass(BouncyCastleProvider.class, "java.security.cert.PKIXRevocationChecker");
    private static final String[] SYMMETRIC_GENERIC = {"PBEPBKDF2", "PBEPKCS12", "PBES2AlgorithmParameters"};
    private static final String[] SYMMETRIC_MACS = new String[0];
    private static final CryptoServiceProperties[] SYMMETRIC_CIPHERS = {service("AES", 256), service("ARC4", 20), service("Blowfish", 128), service("DES", 56), service(KeyProperties.KEY_ALGORITHM_3DES, 112), service("RC2", 128), service("Twofish", 256)};
    private static final String[] ASYMMETRIC_GENERIC = {"X509"};
    private static final String[] ASYMMETRIC_CIPHERS = {"DSA", "DH", KeyProperties.KEY_ALGORITHM_EC, "RSA"};
    private static final String[] DIGESTS = {KeyProperties.DIGEST_MD5, "SHA1", "SHA224", "SHA256", "SHA384", "SHA512"};
    public static final String PROVIDER_NAME = "BC";
    private static final String[] KEYSTORES = {PROVIDER_NAME, "BCFKS", KeyChain.EXTRA_PKCS12};

    public BouncyCastleProvider() {
        super(PROVIDER_NAME, 1.77d, info);
        this.serviceMap = new ConcurrentHashMap();
        this.privateProvider = new PrivateProvider();
        AccessController.doPrivileged(new PrivilegedAction() { // from class: com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.1
            @Override // java.security.PrivilegedAction
            public Object run() {
                BouncyCastleProvider.this.setup();
                return null;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setup() {
        loadAlgorithms(DIGEST_PACKAGE, DIGESTS);
        loadAlgorithms(SYMMETRIC_PACKAGE, SYMMETRIC_GENERIC);
        loadAlgorithms(SYMMETRIC_PACKAGE, SYMMETRIC_MACS);
        loadAlgorithms(SYMMETRIC_PACKAGE, SYMMETRIC_CIPHERS);
        loadAlgorithms(ASYMMETRIC_PACKAGE, ASYMMETRIC_GENERIC);
        loadAlgorithms(ASYMMETRIC_PACKAGE, ASYMMETRIC_CIPHERS);
        loadAlgorithms(KEYSTORE_PACKAGE, KEYSTORES);
        put("CertPathValidator.PKIX", "com.android.internal.org.bouncycastle.jce.provider.PKIXCertPathValidatorSpi");
        put("CertPathBuilder.PKIX", "com.android.internal.org.bouncycastle.jce.provider.PKIXCertPathBuilderSpi");
        put("CertStore.Collection", "com.android.internal.org.bouncycastle.jce.provider.CertStoreCollectionSpi");
    }

    private void loadAlgorithms(String str, String[] strArr) {
        for (int i = 0; i != strArr.length; i++) {
            loadServiceClass(str, strArr[i]);
        }
    }

    private void loadAlgorithms(String str, CryptoServiceProperties[] cryptoServicePropertiesArr) {
        for (int i = 0; i != cryptoServicePropertiesArr.length; i++) {
            CryptoServiceProperties cryptoServiceProperties = cryptoServicePropertiesArr[i];
            try {
                CryptoServicesRegistrar.checkConstraints(cryptoServiceProperties);
                loadServiceClass(str, cryptoServiceProperties.getServiceName());
            } catch (CryptoServiceConstraintsException unused) {
                Logger logger = LOG;
                if (logger.isLoggable(Level.FINE)) {
                    logger.fine("service for " + cryptoServiceProperties.getServiceName() + " ignored due to constraints");
                }
            }
        }
    }

    private void loadServiceClass(String str, String str2) {
        Class clsLoadClass = ClassUtil.loadClass(BouncyCastleProvider.class, str + str2 + "$Mappings");
        if (clsLoadClass != null) {
            try {
                ((AlgorithmProvider) clsLoadClass.newInstance()).configure(this);
            } catch (Exception e) {
                throw new InternalError("cannot create instance of " + str + str2 + "$Mappings : " + e);
            }
        }
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider
    public void setParameter(String str, Object obj) {
        ProviderConfiguration providerConfiguration = CONFIGURATION;
        synchronized (providerConfiguration) {
            ((BouncyCastleProviderConfiguration) providerConfiguration).setParameter(str, obj);
        }
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider
    public boolean hasAlgorithm(String str, String str2) {
        if (containsKey(str + MediaMetrics.SEPARATOR + str2)) {
            return true;
        }
        StringBuilder sb = new StringBuilder("Alg.Alias.");
        sb.append(str);
        sb.append(MediaMetrics.SEPARATOR);
        sb.append(str2);
        return containsKey(sb.toString());
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider
    public void addAlgorithm(String str, String str2) {
        if (containsKey(str)) {
            throw new IllegalStateException("duplicate provider key (" + str + ") found");
        }
        put(str, str2);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider
    public void addAlgorithm(String str, String str2, Map<String, String> map) {
        addAlgorithm(str, str2);
        addAttributes(str, map);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider
    public void addAlgorithm(String str, ASN1ObjectIdentifier aSN1ObjectIdentifier, String str2) {
        addAlgorithm(str + MediaMetrics.SEPARATOR + aSN1ObjectIdentifier, str2);
        addAlgorithm(str + ".OID." + aSN1ObjectIdentifier, str2);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider
    public void addAlgorithm(String str, ASN1ObjectIdentifier aSN1ObjectIdentifier, String str2, Map<String, String> map) {
        addAlgorithm(str, aSN1ObjectIdentifier, str2);
        addAttributes(str + MediaMetrics.SEPARATOR + aSN1ObjectIdentifier, map);
        addAttributes(str + ".OID." + aSN1ObjectIdentifier, map);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider
    public void addKeyInfoConverter(ASN1ObjectIdentifier aSN1ObjectIdentifier, AsymmetricKeyInfoConverter asymmetricKeyInfoConverter) {
        Map map = keyInfoConverters;
        synchronized (map) {
            map.put(aSN1ObjectIdentifier, asymmetricKeyInfoConverter);
        }
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider
    public AsymmetricKeyInfoConverter getKeyInfoConverter(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return (AsymmetricKeyInfoConverter) keyInfoConverters.get(aSN1ObjectIdentifier);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider
    public void addAttributes(String str, Map<String, String> map) {
        put(str + " ImplementedIn", ExifInterface.TAG_SOFTWARE);
        for (String str2 : map.keySet()) {
            String str3 = str + " " + str2;
            if (containsKey(str3)) {
                throw new IllegalStateException("duplicate provider attribute key (" + str3 + ") found");
            }
            put(str3, map.get(str2));
        }
    }

    private static AsymmetricKeyInfoConverter getAsymmetricKeyInfoConverter(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        AsymmetricKeyInfoConverter asymmetricKeyInfoConverter;
        Map map = keyInfoConverters;
        synchronized (map) {
            asymmetricKeyInfoConverter = (AsymmetricKeyInfoConverter) map.get(aSN1ObjectIdentifier);
        }
        return asymmetricKeyInfoConverter;
    }

    public static PublicKey getPublicKey(SubjectPublicKeyInfo subjectPublicKeyInfo) throws IOException {
        try {
            return KeyFactory.getInstance(subjectPublicKeyInfo.getAlgorithmId().getAlgorithm().getId()).generatePublic(new X509EncodedKeySpec(subjectPublicKeyInfo.getEncoded()));
        } catch (NoSuchAlgorithmException unused) {
            return null;
        } catch (InvalidKeySpecException e) {
            throw new IOException(e);
        }
    }

    public static PrivateKey getPrivateKey(PrivateKeyInfo privateKeyInfo) throws IOException {
        try {
            return KeyFactory.getInstance(privateKeyInfo.getPrivateKeyAlgorithm().getAlgorithm().getId()).generatePrivate(new PKCS8EncodedKeySpec(privateKeyInfo.getEncoded()));
        } catch (NoSuchAlgorithmException unused) {
            return null;
        } catch (InvalidKeySpecException e) {
            throw new IOException(e);
        }
    }

    private static final class PrivateProvider extends Provider {
        public PrivateProvider() {
            super("BCPrivate", 1.0d, "Android BC private use only");
        }
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider
    public void addPrivateAlgorithm(String str, String str2) {
        if (this.privateProvider.containsKey(str)) {
            throw new IllegalStateException("duplicate provider key (" + str + ") found");
        }
        this.privateProvider.put(str, str2);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider
    public void addPrivateAlgorithm(String str, ASN1ObjectIdentifier aSN1ObjectIdentifier, String str2) {
        addPrivateAlgorithm(str + MediaMetrics.SEPARATOR + aSN1ObjectIdentifier, str2);
    }

    public Provider getPrivateProvider() {
        return this.privateProvider;
    }

    private static CryptoServiceProperties service(String str, int i) {
        return new JcaCryptoService(str, i);
    }

    private static class JcaCryptoService implements CryptoServiceProperties {
        private final int bitsOfSecurity;
        private final String name;

        @Override // com.android.internal.org.bouncycastle.crypto.CryptoServiceProperties
        public Object getParams() {
            return null;
        }

        JcaCryptoService(String str, int i) {
            this.name = str;
            this.bitsOfSecurity = i;
        }

        @Override // com.android.internal.org.bouncycastle.crypto.CryptoServiceProperties
        public int bitsOfSecurity() {
            return this.bitsOfSecurity;
        }

        @Override // com.android.internal.org.bouncycastle.crypto.CryptoServiceProperties
        public String getServiceName() {
            return this.name;
        }

        @Override // com.android.internal.org.bouncycastle.crypto.CryptoServiceProperties
        public CryptoServicePurpose getPurpose() {
            return CryptoServicePurpose.ANY;
        }
    }
}

package com.samsung.android.knox.ucm.core.jcajce;

import android.os.Bundle;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl;
import com.samsung.android.knox.ucm.plugin.service.KeyGenerator;
import com.samsung.android.knox.zt.devicetrust.cert.CertProvisionProfile;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class UcmKeystoreProvider extends Provider {
    public static final String KEYSTORE = "KeyStore";
    public static final List<String> KEY_GENERATOR_SUPPORTED_ALGORITHMS = Arrays.asList("AES", "HmacMD5", "HmacSHA1", "HmacSHA224", "HmacSHA256", "HmacSHA384", "HmacSHA512");
    public static final String PROVIDER_DESC = "Samsung KNOX-based Keystore Provider";
    public static final String PROVIDER_NAME = "KNOX";
    public static final double PROVIDER_VERSION = 1.0d;
    private String mSource;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class UcmProviderService extends Provider.Service {
        public String mSrc;

        public UcmProviderService(Provider provider, String str, String str2, String str3, String[] strArr, String str4) {
            super(provider, str, str2, str3, toList(strArr), null);
            this.mSrc = str4;
        }

        public static List<String> toList(String[] strArr) {
            if (strArr == null) {
                return null;
            }
            return Arrays.asList(strArr);
        }

        @Override // java.security.Provider.Service
        public Object newInstance(Object obj) throws NoSuchAlgorithmException {
            String type = getType();
            if (type.equals("KeyStore")) {
                return new UcmKeystore(this.mSrc);
            }
            if (type.equals("KeyPairGenerator")) {
                return new UcmKeyPairGenerator(this.mSrc, getAlgorithm());
            }
            if (type.equals("SecureRandom")) {
                return new UcmSecureRandom(this.mSrc);
            }
            if (type.equals(KeyGenerator.SERVICE)) {
                return new UcmKeyGenerator(this.mSrc, getAlgorithm());
            }
            throw new NoSuchAlgorithmException("Unknown type: ".concat(type));
        }
    }

    public UcmKeystoreProvider() {
        this("KNOX", null);
    }

    @Override // java.util.Hashtable, java.util.Map
    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof UcmKeystoreProvider) && super.equals(obj)) {
            return this.mSource.equals(((UcmKeystoreProvider) obj).mSource);
        }
        return false;
    }

    @Override // java.util.Hashtable, java.util.Map
    public int hashCode() {
        return this.mSource.hashCode();
    }

    public final void setBundle(Bundle bundle) {
        setProperty(UniversalCredentialUtil.UNIQUE_ID, bundle.getString(UniversalCredentialUtil.UNIQUE_ID, ""));
        setProperty("id", bundle.getString("id", ""));
        setProperty(UniversalCredentialUtil.AGENT_SUMMARY, bundle.getString(UniversalCredentialUtil.AGENT_SUMMARY, ""));
        setProperty(UniversalCredentialUtil.AGENT_TITLE, bundle.getString(UniversalCredentialUtil.AGENT_TITLE, ""));
        setProperty(UniversalCredentialUtil.AGENT_STORAGE_TYPE, bundle.getString(UniversalCredentialUtil.AGENT_STORAGE_TYPE, ""));
        setProperty(UniversalCredentialUtil.AGENT_VENDORID, bundle.getString(UniversalCredentialUtil.AGENT_VENDORID, ""));
        setProperty(UniversalCredentialUtil.AGENT_ISDETACHABLE, ((Boolean) bundle.get(UniversalCredentialUtil.AGENT_ISDETACHABLE)).toString());
        setProperty(UniversalCredentialUtil.AGENT_REQUSERVERIFICATION, ((Boolean) bundle.get(UniversalCredentialUtil.AGENT_REQUSERVERIFICATION)).toString());
        setProperty(UniversalCredentialUtil.AGENT_ISHARDWAREBACKED, ((Boolean) bundle.get(UniversalCredentialUtil.AGENT_ISHARDWAREBACKED)).toString());
        setProperty(UniversalCredentialUtil.AGENT_ISREADONLY, ((Boolean) bundle.get(UniversalCredentialUtil.AGENT_ISREADONLY)).toString());
        setProperty("packageName", (String) bundle.get("packageName"));
        setProperty(UniversalCredentialUtil.AGENT_ISMANAGEABLE, ((Boolean) bundle.get(UniversalCredentialUtil.AGENT_ISMANAGEABLE)).toString());
        setProperty(UniversalCredentialUtil.AGENT_ENFORCEMANAGEMENT, ((Boolean) bundle.get(UniversalCredentialUtil.AGENT_ENFORCEMANAGEMENT)).toString());
        setProperty(UniversalCredentialUtil.AGENT_CONFIGURATORLIST, bundle.getString(UniversalCredentialUtil.AGENT_CONFIGURATORLIST, ""));
        setProperty(UniversalCredentialUtil.AGENT_IS_SUPPORT_CHANGE_PIN, ((Boolean) bundle.get(UniversalCredentialUtil.AGENT_IS_SUPPORT_CHANGE_PIN)).toString());
        setProperty(UniversalCredentialUtil.AGENT_IS_SUPPORT_CHANGE_PIN_WITH_PASSWORD, ((Boolean) bundle.get(UniversalCredentialUtil.AGENT_IS_SUPPORT_CHANGE_PIN_WITH_PASSWORD)).toString());
        setProperty(UniversalCredentialUtil.AGENT_IS_SUPPORT_BIOMETRIC_FOR_UCM, ((Boolean) bundle.get(UniversalCredentialUtil.AGENT_IS_SUPPORT_BIOMETRIC_FOR_UCM)).toString());
        setProperty(UniversalCredentialUtil.AGENT_IS_PUK_SUPPORTED, ((Boolean) bundle.get(UniversalCredentialUtil.AGENT_IS_PUK_SUPPORTED)).toString());
        setProperty(UniversalCredentialUtil.AGENT_IS_GENERATE_PASSWORD_AVAILABLE, ((Boolean) bundle.get(UniversalCredentialUtil.AGENT_IS_GENERATE_PASSWORD_AVAILABLE)).toString());
    }

    public UcmKeystoreProvider(String str, Bundle bundle) {
        super(str, 1.0d, PROVIDER_DESC);
        this.mSource = str;
        if (bundle != null) {
            setBundle(bundle);
        }
        putService(new UcmProviderService(this, "KeyStore", "KNOX", UcmKeystore.class.getName(), null, this.mSource));
        putService(new UcmProviderService(this, "KeyPairGenerator", "RSA", UcmKeyPairGenerator.class.getName(), null, this.mSource));
        putService(new UcmProviderService(this, "KeyPairGenerator", CertProvisionProfile.KEY_TYPE_EC, UcmKeyPairGenerator.class.getName(), null, this.mSource));
        putService(new UcmProviderService(this, "SecureRandom", UcmAgentProviderImpl.SECURERANDOM_SHA1PRNG, UcmSecureRandom.class.getName(), null, this.mSource));
        Iterator<String> it = KEY_GENERATOR_SUPPORTED_ALGORITHMS.iterator();
        while (it.hasNext()) {
            putService(new UcmProviderService(this, KeyGenerator.SERVICE, it.next(), UcmKeyGenerator.class.getName(), null, this.mSource));
        }
    }
}

package com.android.internal.org.bouncycastle.jcajce.util;

import com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;

/* loaded from: classes5.dex */
public class BCJcaJceHelper extends ProviderJcaJceHelper {
    private static volatile Provider bcProvider;

    private static synchronized Provider getBouncyCastleProvider() {
        Provider provider = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME);
        if (provider instanceof BouncyCastleProvider) {
            return provider;
        }
        if (bcProvider != null) {
            return bcProvider;
        }
        bcProvider = new BouncyCastleProvider();
        return bcProvider;
    }

    public BCJcaJceHelper() {
        super(getBouncyCastleProvider());
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.util.ProviderJcaJceHelper, com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper
    public Cipher createCipher(String str) throws NoSuchPaddingException, NoSuchAlgorithmException {
        try {
            return super.createCipher(str);
        } catch (NoSuchAlgorithmException e) {
            return Cipher.getInstance(str, this.getPrivateProvider());
        }
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.util.ProviderJcaJceHelper, com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper
    public SecretKeyFactory createSecretKeyFactory(String str) throws NoSuchAlgorithmException {
        try {
            return super.createSecretKeyFactory(str);
        } catch (NoSuchAlgorithmException e) {
            return SecretKeyFactory.getInstance(str, this.getPrivateProvider());
        }
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.util.ProviderJcaJceHelper, com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper
    public Mac createMac(String str) throws NoSuchAlgorithmException {
        try {
            return super.createMac(str);
        } catch (NoSuchAlgorithmException e) {
            return Mac.getInstance(str, this.getPrivateProvider());
        }
    }

    private Provider getPrivateProvider() {
        if (this.provider instanceof BouncyCastleProvider) {
            return ((BouncyCastleProvider) this.provider).getPrivateProvider();
        }
        throw new IllegalStateException("Internal error in BCJcaJceHelper");
    }
}

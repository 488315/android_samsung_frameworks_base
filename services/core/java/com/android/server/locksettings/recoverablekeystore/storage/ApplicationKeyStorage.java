package com.android.server.locksettings.recoverablekeystore.storage;

import android.os.ServiceSpecificException;
import android.security.keystore.KeyProtection;
import android.util.Log;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.am.ActiveServices$$ExternalSyntheticOutline0;
import com.android.server.locksettings.recoverablekeystore.KeyStoreProxy;
import com.android.server.locksettings.recoverablekeystore.KeyStoreProxyImpl;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Locale;
import javax.crypto.spec.SecretKeySpec;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ApplicationKeyStorage {
    public final KeyStoreProxy mKeyStore;

    public ApplicationKeyStorage(KeyStoreProxy keyStoreProxy) {
        this.mKeyStore = keyStoreProxy;
    }

    public static ApplicationKeyStorage getInstance() {
        KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
        try {
            keyStore.load(null);
            return new ApplicationKeyStorage(new KeyStoreProxyImpl(keyStore));
        } catch (IOException | NoSuchAlgorithmException | CertificateException e) {
            throw new KeyStoreException("Unable to load keystore.", e);
        }
    }

    public static String getInternalAlias(int i, int i2, String str) {
        StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "com.android.server.locksettings.recoverablekeystore/application/", "/", "/");
        m.append(str);
        return m.toString();
    }

    public final void deleteEntry(int i, int i2, String str) {
        Locale locale = Locale.US;
        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "Del ", "/", "/"), str, "RecoverableAppKeyStore");
        try {
            KeyStoreProxy keyStoreProxy = this.mKeyStore;
            ((KeyStoreProxyImpl) keyStoreProxy).mKeyStore.deleteEntry(getInternalAlias(i, i2, str));
        } catch (KeyStoreException e) {
            throw new ServiceSpecificException(22, e.getMessage());
        }
    }

    public final void setSymmetricKeyEntry(int i, int i2, byte[] bArr, String str) {
        Locale locale = Locale.US;
        Log.i("RecoverableAppKeyStore", ActiveServices$$ExternalSyntheticOutline0.m(bArr.length, str, ": ", " bytes of key material", ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "Set ", "/", "/")));
        try {
            KeyStoreProxy keyStoreProxy = this.mKeyStore;
            ((KeyStoreProxyImpl) keyStoreProxy).mKeyStore.setEntry(getInternalAlias(i, i2, str), new KeyStore.SecretKeyEntry(new SecretKeySpec(bArr, "AES")), new KeyProtection.Builder(3).setBlockModes("GCM").setEncryptionPaddings("NoPadding").build());
        } catch (KeyStoreException e) {
            throw new ServiceSpecificException(22, e.getMessage());
        }
    }
}

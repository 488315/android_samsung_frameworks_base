package com.samsung.android.knox.ucm.plugin.service;

import android.os.Bundle;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.KeyStoreSpi;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.UnrecoverableEntryException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Enumeration;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class KeyStore {
    public static final String SERVICE = "KeyStore";
    public final String algorithm;
    public final Provider provider;
    public KeyStoreSpi spiImpl;

    private KeyStore(UcmAgentProviderImpl.UcmAgentKeyStoreSpi ucmAgentKeyStoreSpi, Provider provider, String str) {
        this.algorithm = str;
        this.spiImpl = ucmAgentKeyStoreSpi;
        this.provider = provider;
    }

    public static KeyStore getInstance(String str, Provider provider) throws NoSuchAlgorithmException {
        if (provider == null) {
            throw new IllegalArgumentException("provider == null");
        }
        if (str == null) {
            throw new NullPointerException("algorithm == null");
        }
        UcmAgentProviderImpl.UcmAgentKeyStoreSpi ucmAgentKeyStoreSpi = (UcmAgentProviderImpl.UcmAgentKeyStoreSpi) UcmSpiUtil.getSpi("KeyStore", UcmAgentProviderImpl.UcmAgentKeyStoreSpi.class, str, provider);
        if (ucmAgentKeyStoreSpi != null) {
            return new KeyStore(ucmAgentKeyStoreSpi, provider, str);
        }
        throw new NullPointerException("spi == null");
    }

    public Enumeration<String> aliases() throws KeyStoreException {
        return this.spiImpl.engineAliases();
    }

    public void deleteEntry(String str) throws KeyStoreException {
        this.spiImpl.engineDeleteEntry(str);
    }

    public Certificate[] getCertificateChain(String str) throws KeyStoreException {
        return this.spiImpl.engineGetCertificateChain(str);
    }

    public KeyStore.Entry getEntry(String str, KeyStore.ProtectionParameter protectionParameter) throws NoSuchAlgorithmException, UnrecoverableEntryException, KeyStoreException {
        return this.spiImpl.engineGetEntry(str, protectionParameter);
    }

    public int getErrorStatus() {
        return ((UcmAgentProviderImpl.UcmAgentKeyStoreSpi) this.spiImpl).getErrorCode();
    }

    public Key getKey(String str, char[] cArr) throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {
        return this.spiImpl.engineGetKey(str, cArr);
    }

    public void load(InputStream inputStream, char[] cArr) throws IOException, NoSuchAlgorithmException, CertificateException {
        this.spiImpl.engineLoad(inputStream, cArr);
    }

    public void setEntry(String str, KeyStore.Entry entry, KeyStore.ProtectionParameter protectionParameter) throws KeyStoreException {
        this.spiImpl.engineSetEntry(str, entry, protectionParameter);
    }

    public void setProperty(Bundle bundle) {
        ((UcmAgentProviderImpl.UcmAgentKeyStoreSpi) this.spiImpl).setProperty(bundle);
    }

    public void load(KeyStore.LoadStoreParameter loadStoreParameter) throws IOException, NoSuchAlgorithmException, CertificateException {
        this.spiImpl.engineLoad(loadStoreParameter);
    }
}

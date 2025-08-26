package com.samsung.android.knox.ucm.plugin.service;

import android.os.Bundle;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

/* loaded from: classes4.dex */
public class Cipher {
    public static final String SERVICE = "Cipher";
    public final String algorithm;
    public final Provider provider;
    public UcmAgentProviderImpl.UcmAgentCipherSpi spiImpl;

    private Cipher(UcmAgentProviderImpl.UcmAgentCipherSpi ucmAgentCipherSpi, Provider provider, String str) {
        this.algorithm = str;
        this.spiImpl = ucmAgentCipherSpi;
        this.provider = provider;
    }

    public static Cipher getInstance(String str, Provider provider) throws NoSuchAlgorithmException {
        if (provider == null) {
            throw new IllegalArgumentException("provider == null");
        }
        if (str == null) {
            throw new NullPointerException("algorithm == null");
        }
        UcmAgentProviderImpl.UcmAgentCipherSpi ucmAgentCipherSpi = (UcmAgentProviderImpl.UcmAgentCipherSpi) UcmSpiUtil.getSpi("Cipher", UcmAgentProviderImpl.UcmAgentCipherSpi.class, str, provider);
        if (ucmAgentCipherSpi != null) {
            return new Cipher(ucmAgentCipherSpi, provider, str);
        }
        throw new NullPointerException("spi == null");
    }

    public byte[] doFinal(byte[] bArr) throws BadPaddingException, IllegalBlockSizeException {
        return this.spiImpl.engineDoFinal(bArr, 0, bArr.length);
    }

    public int getErrorStatus() {
        return this.spiImpl.getErrorCode();
    }

    public void init(int i, Key key) throws InvalidKeyException {
        this.spiImpl.engineInit(i, key, null);
    }

    public void setProperty(Bundle bundle) {
        this.spiImpl.setProperty(bundle);
    }

    public void updateAAD(byte[] bArr) throws IllegalStateException, UnsupportedOperationException {
        this.spiImpl.engineUpdateAAD(bArr, 0, bArr.length);
    }

    public void init(int i, Key key, AlgorithmParameterSpec algorithmParameterSpec) throws InvalidKeyException, InvalidAlgorithmParameterException {
        this.spiImpl.engineInit(i, key, algorithmParameterSpec, (java.security.SecureRandom) null);
    }
}

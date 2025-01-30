package com.samsung.android.knox.ucm.plugin.service;

import android.os.Bundle;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl;
import java.security.Key;
import java.security.Provider;
import java.security.spec.AlgorithmParameterSpec;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class Cipher {
    public static final String SERVICE = "Cipher";
    public final String algorithm;
    public final Provider provider;
    public UcmAgentProviderImpl.UcmAgentCipherSpi spiImpl;

    private Cipher(UcmAgentProviderImpl.UcmAgentCipherSpi ucmAgentCipherSpi, Provider provider, String str) {
        this.algorithm = str;
        this.spiImpl = ucmAgentCipherSpi;
        this.provider = provider;
    }

    public static Cipher getInstance(String str, Provider provider) {
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

    public final byte[] doFinal(byte[] bArr) {
        return this.spiImpl.engineDoFinal(bArr, 0, bArr.length);
    }

    public final int getErrorStatus() {
        return this.spiImpl.getErrorCode();
    }

    public final void init(int i, Key key) {
        this.spiImpl.engineInit(i, key, null);
    }

    public final void setProperty(Bundle bundle) {
        this.spiImpl.setProperty(bundle);
    }

    public final void updateAAD(byte[] bArr) {
        this.spiImpl.engineUpdateAAD(bArr, 0, bArr.length);
    }

    public final void init(int i, Key key, AlgorithmParameterSpec algorithmParameterSpec) {
        this.spiImpl.engineInit(i, key, algorithmParameterSpec, (java.security.SecureRandom) null);
    }
}

package com.samsung.android.knox.ucm.plugin.service;

import android.os.Bundle;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl;
import java.security.PrivateKey;
import java.security.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class Signature {
    public static final String SERVICE = "Signature";
    public final String algorithm;
    public final Provider provider;
    public UcmAgentProviderImpl.UcmAgentSignatureSpi spiImpl;

    private Signature(UcmAgentProviderImpl.UcmAgentSignatureSpi ucmAgentSignatureSpi, Provider provider, String str) {
        this.algorithm = str;
        this.spiImpl = ucmAgentSignatureSpi;
        this.provider = provider;
    }

    public static Signature getInstance(String str, Provider provider) {
        if (provider == null) {
            throw new IllegalArgumentException("provider == null");
        }
        if (str == null) {
            throw new NullPointerException("algorithm == null");
        }
        UcmAgentProviderImpl.UcmAgentSignatureSpi ucmAgentSignatureSpi = (UcmAgentProviderImpl.UcmAgentSignatureSpi) UcmSpiUtil.getSpi(SERVICE, UcmAgentProviderImpl.UcmAgentSignatureSpi.class, str, provider);
        if (ucmAgentSignatureSpi != null) {
            return new Signature(ucmAgentSignatureSpi, provider, str);
        }
        throw new NullPointerException("spi == null");
    }

    public final int getErrorStatus() {
        return this.spiImpl.getErrorCode();
    }

    public final void initSign(PrivateKey privateKey) {
        this.spiImpl.engineInitSign(privateKey);
    }

    public final void setProperty(Bundle bundle) {
        this.spiImpl.setProperty(bundle);
    }

    public final byte[] sign() {
        return this.spiImpl.engineSign();
    }

    public final void update(byte[] bArr) {
        this.spiImpl.engineUpdate(bArr, 0, bArr.length);
    }

    public final void update(byte[] bArr, int i, int i2) {
        this.spiImpl.engineUpdate(bArr, i, i2);
    }
}

package com.samsung.android.knox.ucm.plugin.service;

import android.os.Bundle;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl;
import java.security.Provider;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.SecretKey;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class Mac {
    public static final String SERVICE = "Mac";
    public final String algorithm;
    public final Provider provider;
    public UcmAgentProviderImpl.UcmAgentMacSpi spiImpl;

    private Mac(UcmAgentProviderImpl.UcmAgentMacSpi ucmAgentMacSpi, Provider provider, String str) {
        this.spiImpl = ucmAgentMacSpi;
        this.provider = provider;
        this.algorithm = str;
    }

    public static Mac getInstance(String str, Provider provider) {
        if (provider == null) {
            throw new IllegalArgumentException("provider == null");
        }
        if (str == null) {
            throw new NullPointerException("algorithm == null");
        }
        UcmAgentProviderImpl.UcmAgentMacSpi ucmAgentMacSpi = (UcmAgentProviderImpl.UcmAgentMacSpi) UcmSpiUtil.getSpi(SERVICE, UcmAgentProviderImpl.UcmAgentMacSpi.class, str, provider);
        if (ucmAgentMacSpi != null) {
            return new Mac(ucmAgentMacSpi, provider, str);
        }
        throw new NullPointerException("spi == null");
    }

    public final byte[] doFinal() {
        return this.spiImpl.engineDoFinal();
    }

    public final int getErrorStatus() {
        return this.spiImpl.getErrorCode();
    }

    public final void init(SecretKey secretKey, AlgorithmParameterSpec algorithmParameterSpec) {
        this.spiImpl.engineInit(secretKey, algorithmParameterSpec);
    }

    public final void setProperty(Bundle bundle) {
        this.spiImpl.setProperty(bundle);
    }

    public final void update(byte[] bArr, int i, int i2) {
        this.spiImpl.engineUpdate(bArr, i, i2);
    }
}

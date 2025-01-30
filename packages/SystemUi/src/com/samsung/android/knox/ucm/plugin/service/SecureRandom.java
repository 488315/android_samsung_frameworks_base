package com.samsung.android.knox.ucm.plugin.service;

import android.os.Bundle;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl;
import java.security.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class SecureRandom {
    public static final String SERVICE = "SecureRandom";
    public final String algorithm;
    public final Provider provider;
    public final UcmAgentProviderImpl.UcmAgentSecureRandomSpi spiImpl;

    private SecureRandom(UcmAgentProviderImpl.UcmAgentSecureRandomSpi ucmAgentSecureRandomSpi, Provider provider, String str) {
        this.provider = provider;
        this.spiImpl = ucmAgentSecureRandomSpi;
        this.algorithm = str;
    }

    public static SecureRandom getInstance(String str, Provider provider) {
        if (provider == null) {
            throw new IllegalArgumentException("provider == null");
        }
        if (str == null) {
            throw new NullPointerException("algorithm == null");
        }
        UcmAgentProviderImpl.UcmAgentSecureRandomSpi ucmAgentSecureRandomSpi = (UcmAgentProviderImpl.UcmAgentSecureRandomSpi) UcmSpiUtil.getSpi("SecureRandom", UcmAgentProviderImpl.UcmAgentSecureRandomSpi.class, str, provider);
        if (ucmAgentSecureRandomSpi != null) {
            return new SecureRandom(ucmAgentSecureRandomSpi, provider, str);
        }
        throw new NullPointerException("spi == null");
    }

    public final byte[] generateSeed(int i) {
        return this.spiImpl.engineGenerateSeed(i);
    }

    public final int getErrorStatus() {
        return this.spiImpl.getErrorCode();
    }

    public final void setProperty(Bundle bundle) {
        this.spiImpl.setProperty(bundle);
    }
}

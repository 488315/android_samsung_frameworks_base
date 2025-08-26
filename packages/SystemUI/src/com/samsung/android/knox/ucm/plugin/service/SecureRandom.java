package com.samsung.android.knox.ucm.plugin.service;

import android.os.Bundle;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;

/* loaded from: classes4.dex */
public class SecureRandom {
    public static final String SERVICE = "SecureRandom";
    public final String algorithm;
    public final Provider provider;
    public final UcmAgentProviderImpl.UcmAgentSecureRandomSpi spiImpl;

    private SecureRandom(UcmAgentProviderImpl.UcmAgentSecureRandomSpi ucmAgentSecureRandomSpi, Provider provider, String str) {
        this.provider = provider;
        this.spiImpl = ucmAgentSecureRandomSpi;
        this.algorithm = str;
    }

    public static SecureRandom getInstance(String str, Provider provider) throws NoSuchAlgorithmException {
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

    public byte[] generateSeed(int i) {
        return this.spiImpl.engineGenerateSeed(i);
    }

    public int getErrorStatus() {
        return this.spiImpl.getErrorCode();
    }

    public void setProperty(Bundle bundle) {
        this.spiImpl.setProperty(bundle);
    }
}

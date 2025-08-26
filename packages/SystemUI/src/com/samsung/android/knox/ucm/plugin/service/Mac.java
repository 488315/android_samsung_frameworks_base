package com.samsung.android.knox.ucm.plugin.service;

import android.os.Bundle;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.SecretKey;

/* loaded from: classes4.dex */
public class Mac {
    public static final String SERVICE = "Mac";
    public final String algorithm;
    public final Provider provider;
    public UcmAgentProviderImpl.UcmAgentMacSpi spiImpl;

    private Mac(UcmAgentProviderImpl.UcmAgentMacSpi ucmAgentMacSpi, Provider provider, String str) {
        this.spiImpl = ucmAgentMacSpi;
        this.provider = provider;
        this.algorithm = str;
    }

    public static Mac getInstance(String str, Provider provider) throws NoSuchAlgorithmException {
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

    public byte[] doFinal() {
        return this.spiImpl.engineDoFinal();
    }

    public int getErrorStatus() {
        return this.spiImpl.getErrorCode();
    }

    public void init(SecretKey secretKey, AlgorithmParameterSpec algorithmParameterSpec) throws InvalidKeyException, InvalidAlgorithmParameterException {
        this.spiImpl.engineInit(secretKey, algorithmParameterSpec);
    }

    public void setProperty(Bundle bundle) {
        this.spiImpl.setProperty(bundle);
    }

    public void update(byte[] bArr, int i, int i2) {
        this.spiImpl.engineUpdate(bArr, i, i2);
    }
}

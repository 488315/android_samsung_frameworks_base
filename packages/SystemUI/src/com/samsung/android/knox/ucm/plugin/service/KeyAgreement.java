package com.samsung.android.knox.ucm.plugin.service;

import android.os.Bundle;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.spec.AlgorithmParameterSpec;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class KeyAgreement {
    public static final String SERVICE = "KeyAgreement";
    public final String algorithm;
    public final Provider provider;
    public final UcmAgentProviderImpl.UcmAgentKeyAgreementSpi spiImpl;

    private KeyAgreement(UcmAgentProviderImpl.UcmAgentKeyAgreementSpi ucmAgentKeyAgreementSpi, Provider provider, String str) {
        this.spiImpl = ucmAgentKeyAgreementSpi;
        this.provider = provider;
        this.algorithm = str;
    }

    public static KeyAgreement getInstance(String str, Provider provider) throws NoSuchAlgorithmException {
        if (provider == null) {
            throw new IllegalArgumentException("provider == null");
        }
        if (str == null) {
            throw new NullPointerException("algorithm == null");
        }
        UcmAgentProviderImpl.UcmAgentKeyAgreementSpi ucmAgentKeyAgreementSpi = (UcmAgentProviderImpl.UcmAgentKeyAgreementSpi) UcmSpiUtil.getSpi(SERVICE, UcmAgentProviderImpl.UcmAgentKeyAgreementSpi.class, str, provider);
        if (ucmAgentKeyAgreementSpi != null) {
            return new KeyAgreement(ucmAgentKeyAgreementSpi, provider, str);
        }
        throw new NullPointerException("spi == null");
    }

    public void doPhase(Key key) throws InvalidKeyException {
        this.spiImpl.engineDoPhase(key, true);
    }

    public byte[] engineGenerateSecret() throws IllegalStateException {
        return this.spiImpl.engineGenerateSecret();
    }

    public int getErrorStatus() {
        return this.spiImpl.getErrorCode();
    }

    public void init(Key key, AlgorithmParameterSpec algorithmParameterSpec) throws InvalidAlgorithmParameterException, InvalidKeyException {
        this.spiImpl.engineInit(key, algorithmParameterSpec, null);
    }

    public void setProperty(Bundle bundle) {
        this.spiImpl.setProperty(bundle);
    }
}

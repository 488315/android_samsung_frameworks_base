package com.samsung.android.knox.ucm.plugin.service;

import android.os.Bundle;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.SignatureException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class Signature {
    public static final String SERVICE = "Signature";
    public final String algorithm;
    public final Provider provider;
    public UcmAgentProviderImpl.UcmAgentSignatureSpi spiImpl;

    private Signature(UcmAgentProviderImpl.UcmAgentSignatureSpi ucmAgentSignatureSpi, Provider provider, String str) {
        this.algorithm = str;
        this.spiImpl = ucmAgentSignatureSpi;
        this.provider = provider;
    }

    public static Signature getInstance(String str, Provider provider) throws NoSuchAlgorithmException {
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

    public int getErrorStatus() {
        return this.spiImpl.getErrorCode();
    }

    public void initSign(PrivateKey privateKey) throws InvalidKeyException {
        this.spiImpl.engineInitSign(privateKey);
    }

    public void setProperty(Bundle bundle) {
        this.spiImpl.setProperty(bundle);
    }

    public byte[] sign() throws SignatureException {
        return this.spiImpl.engineSign();
    }

    public void update(byte[] bArr) throws SignatureException {
        this.spiImpl.engineUpdate(bArr, 0, bArr.length);
    }

    public void update(byte[] bArr, int i, int i2) throws SignatureException {
        this.spiImpl.engineUpdate(bArr, i, i2);
    }
}

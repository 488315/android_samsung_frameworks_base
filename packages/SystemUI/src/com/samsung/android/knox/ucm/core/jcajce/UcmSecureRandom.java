package com.samsung.android.knox.ucm.core.jcajce;

import android.os.Bundle;
import android.os.Process;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentService;
import java.security.SecureRandomSpi;

/* loaded from: classes4.dex */
public class UcmSecureRandom extends SecureRandomSpi {
    private String mSource;

    public UcmSecureRandom(String str) {
        this.mSource = str;
    }

    @Override // java.security.SecureRandomSpi
    public final byte[] engineGenerateSeed(int i) {
        Bundle bundleGenerateSecureRandom = UniversalCredentialUtil.getInstance().generateSecureRandom(new UniversalCredentialUtil.UcmUriBuilder(this.mSource).setResourceId(2).setUid(Process.myUid()).build(), i, null);
        if (bundleGenerateSecureRandom == null) {
            return null;
        }
        return bundleGenerateSecureRandom.getByteArray(UcmAgentService.PLUGIN_BYTEARRAY_RESPONSE);
    }

    public final void engineMixSeed(byte[] bArr) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override // java.security.SecureRandomSpi
    public final void engineNextBytes(byte[] bArr) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override // java.security.SecureRandomSpi
    public final void engineSetSeed(byte[] bArr) {
        throw new UnsupportedOperationException("Not supported");
    }
}

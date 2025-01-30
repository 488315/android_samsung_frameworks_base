package com.samsung.android.knox.ucm.core.jcajce;

import android.os.Bundle;
import android.os.Process;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentService;
import java.security.SecureRandomSpi;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class UcmSecureRandom extends SecureRandomSpi {
    private String mSource;

    public UcmSecureRandom(String str) {
        this.mSource = str;
    }

    @Override // java.security.SecureRandomSpi
    public final byte[] engineGenerateSeed(int i) {
        Bundle generateSecureRandom = UniversalCredentialUtil.getInstance().generateSecureRandom(new UniversalCredentialUtil.UcmUriBuilder(this.mSource).setResourceId(2).setUid(Process.myUid()).build(), i, null);
        if (generateSecureRandom == null) {
            return null;
        }
        return generateSecureRandom.getByteArray(UcmAgentService.PLUGIN_BYTEARRAY_RESPONSE);
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

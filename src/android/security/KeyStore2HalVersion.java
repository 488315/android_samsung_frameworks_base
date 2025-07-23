package android.security;

import android.security.KeyStore2;
import android.system.keystore2.IKeystoreService;

/* compiled from: KeyStore2HalLatest.java */
/* loaded from: classes3.dex */
class KeyStore2HalVersion {
    KeyStore2HalVersion() {
    }

    public static byte[] getSupplementaryAttestationInfoHelper(final int i, KeyStore2 keyStore2) throws KeyStoreException {
        return (byte[]) keyStore2.handleRemoteExceptionWithRetry(new KeyStore2.CheckedRemoteRequest() { // from class: android.security.KeyStore2HalVersion$$ExternalSyntheticLambda0
            @Override // android.security.KeyStore2.CheckedRemoteRequest
            public final Object execute(IKeystoreService iKeystoreService) {
                byte[] supplementaryAttestationInfo;
                supplementaryAttestationInfo = iKeystoreService.getSupplementaryAttestationInfo(i);
                return supplementaryAttestationInfo;
            }
        });
    }
}

package com.samsung.android.authenticator;

import android.os.ParcelFileDescriptor;

/* loaded from: classes6.dex */
final class AuthenticatorTrustedApplication implements TrustedApplication {
    private static final String TAG = "ATA";
    private static final TrustedAppAssetType mType = TrustedAppAssetType.PASS_AUTHENTICATOR;
    private final int mHandle;

    AuthenticatorTrustedApplication(int i) {
        this.mHandle = i;
    }

    @Override // com.samsung.android.authenticator.TrustedApplication
    public int load() {
        TrustedAppAssetType trustedAppAssetType = mType;
        if (!HalService.load(trustedAppAssetType, (ParcelFileDescriptor) null, 0L, 0L)) {
            AuthenticatorLog.e(TAG, "tl failed. " + trustedAppAssetType);
            return -1;
        }
        return this.mHandle;
    }

    @Override // com.samsung.android.authenticator.TrustedApplication
    public byte[] execute(byte[] bArr) {
        return HalService.execute(mType, bArr);
    }

    @Override // com.samsung.android.authenticator.TrustedApplication
    public int unload() {
        TrustedAppAssetType trustedAppAssetType = mType;
        if (HalService.unload(trustedAppAssetType)) {
            return 0;
        }
        AuthenticatorLog.e(TAG, "tu failed. " + trustedAppAssetType);
        return -1;
    }

    @Override // com.samsung.android.authenticator.TrustedApplication
    public int getHandle() {
        return this.mHandle;
    }
}

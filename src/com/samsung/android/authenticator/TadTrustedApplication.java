package com.samsung.android.authenticator;

import android.os.ParcelFileDescriptor;

/* loaded from: classes6.dex */
final class TadTrustedApplication implements TrustedApplication {
    private static final String TAG = "TTA";
    private final int mHandle;

    TadTrustedApplication(int i) {
        this.mHandle = i;
    }

    @Override // com.samsung.android.authenticator.TrustedApplication
    public int load() {
        AuthenticatorLog.d(TAG, "public int load()");
        if (!HalService.load(TrustedAppType.ASSET_DOWNLOADER_TRUSTED_APP, (ParcelFileDescriptor) null, 0L, 0L)) {
            AuthenticatorLog.e(TAG, "tl failed. " + TrustedAppType.ASSET_DOWNLOADER_TRUSTED_APP);
            return -1;
        }
        return this.mHandle;
    }

    @Override // com.samsung.android.authenticator.TrustedApplication
    public byte[] execute(byte[] bArr) {
        AuthenticatorLog.d(TAG, "public byte[] execute(byte[] command)");
        return HalService.execute(TrustedAppType.ASSET_DOWNLOADER_TRUSTED_APP, bArr);
    }

    @Override // com.samsung.android.authenticator.TrustedApplication
    public int unload() {
        AuthenticatorLog.d(TAG, "public int unload()");
        if (HalService.unload(TrustedAppType.ASSET_DOWNLOADER_TRUSTED_APP)) {
            return 0;
        }
        AuthenticatorLog.e(TAG, "tu failed. " + TrustedAppType.ASSET_DOWNLOADER_TRUSTED_APP);
        return -1;
    }

    @Override // com.samsung.android.authenticator.TrustedApplication
    public int getHandle() {
        return this.mHandle;
    }
}

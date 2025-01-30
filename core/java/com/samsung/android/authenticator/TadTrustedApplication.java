package com.samsung.android.authenticator;

import android.p009os.ParcelFileDescriptor;
import com.samsung.android.authenticator.SemTrustedApplicationExecutor;

/* loaded from: classes5.dex */
final class TadTrustedApplication implements TrustedApplication {
    private static final String TAG = "TTA";
    private final int mHandle;

    TadTrustedApplication(int handle) {
        this.mHandle = handle;
    }

    @Override // com.samsung.android.authenticator.TrustedApplication
    public int load() {
        AuthenticatorLog.m249d(TAG, "public int load()");
        if (!HalService.load(SemTrustedApplicationExecutor.TrustedAppType.ASSET_DOWNLOADER_TRUSTED_APP, (ParcelFileDescriptor) null, 0L, 0L)) {
            AuthenticatorLog.m250e(TAG, "tl failed. " + SemTrustedApplicationExecutor.TrustedAppType.ASSET_DOWNLOADER_TRUSTED_APP);
            return -1;
        }
        return this.mHandle;
    }

    @Override // com.samsung.android.authenticator.TrustedApplication
    public byte[] execute(byte[] command) {
        AuthenticatorLog.m249d(TAG, "public byte[] execute(byte[] command)");
        return HalService.execute(SemTrustedApplicationExecutor.TrustedAppType.ASSET_DOWNLOADER_TRUSTED_APP, command);
    }

    @Override // com.samsung.android.authenticator.TrustedApplication
    public int unload() {
        AuthenticatorLog.m249d(TAG, "public int unload()");
        if (!HalService.unload(SemTrustedApplicationExecutor.TrustedAppType.ASSET_DOWNLOADER_TRUSTED_APP)) {
            AuthenticatorLog.m250e(TAG, "tu failed. " + SemTrustedApplicationExecutor.TrustedAppType.ASSET_DOWNLOADER_TRUSTED_APP);
            return -1;
        }
        return 0;
    }

    @Override // com.samsung.android.authenticator.TrustedApplication
    public int getHandle() {
        return this.mHandle;
    }
}

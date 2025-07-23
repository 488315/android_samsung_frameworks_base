package com.samsung.android.authenticator;

import android.os.ParcelFileDescriptor;

/* loaded from: classes6.dex */
final class DownloadedTrustedApplication implements TrustedApplication {
    private static final String TAG = "DATA";
    private final int mHandle;
    private final long mLen;
    private final long mOffset;
    private final ParcelFileDescriptor mPfd;
    private final TrustedAppAssetType mType;

    DownloadedTrustedApplication(int i, TrustedAppAssetType trustedAppAssetType, ParcelFileDescriptor parcelFileDescriptor, long j, long j2) {
        this.mHandle = i;
        this.mType = trustedAppAssetType;
        this.mPfd = parcelFileDescriptor;
        this.mOffset = j;
        this.mLen = j2;
    }

    @Override // com.samsung.android.authenticator.TrustedApplication
    public int load() {
        if (!HalService.load(this.mType, this.mPfd, this.mOffset, this.mLen)) {
            AuthenticatorLog.e(TAG, "tl failed. " + this.mType);
            return -1;
        }
        return this.mHandle;
    }

    @Override // com.samsung.android.authenticator.TrustedApplication
    public byte[] execute(byte[] bArr) {
        return HalService.execute(this.mType, bArr);
    }

    @Override // com.samsung.android.authenticator.TrustedApplication
    public int unload() {
        if (HalService.unload(this.mType)) {
            return 0;
        }
        AuthenticatorLog.e(TAG, "tu failed. " + this.mType);
        return -1;
    }

    @Override // com.samsung.android.authenticator.TrustedApplication
    public int getHandle() {
        return this.mHandle;
    }
}

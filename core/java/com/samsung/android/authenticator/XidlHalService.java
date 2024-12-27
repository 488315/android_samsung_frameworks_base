package com.samsung.android.authenticator;

import android.os.ParcelFileDescriptor;

interface XidlHalService {
    byte[] execute(
            SemTrustedApplicationExecutor.TrustedAppAssetType trustedAppAssetType, byte[] bArr);

    byte[] execute(SemTrustedApplicationExecutor.TrustedAppType trustedAppType, byte[] bArr);

    boolean isAvailable();

    boolean load(
            SemTrustedApplicationExecutor.TrustedAppAssetType trustedAppAssetType,
            ParcelFileDescriptor parcelFileDescriptor,
            long j,
            long j2);

    boolean load(
            SemTrustedApplicationExecutor.TrustedAppType trustedAppType,
            ParcelFileDescriptor parcelFileDescriptor,
            long j,
            long j2);

    boolean unload(SemTrustedApplicationExecutor.TrustedAppAssetType trustedAppAssetType);

    boolean unload(SemTrustedApplicationExecutor.TrustedAppType trustedAppType);

    static XidlHalService makeHalService() {
        AidlHalService aidlHalService = new AidlHalService();
        if (aidlHalService.isAvailable()) {
            return aidlHalService;
        }
        return new HidlHalService();
    }
}

package com.samsung.android.authenticator;

import android.os.ParcelFileDescriptor;

/* loaded from: classes6.dex */
interface XidlHalService {
    byte[] execute(TrustedAppAssetType trustedAppAssetType, byte[] bArr);

    byte[] execute(TrustedAppType trustedAppType, byte[] bArr);

    boolean isAvailable();

    boolean load(TrustedAppAssetType trustedAppAssetType, ParcelFileDescriptor parcelFileDescriptor, long j, long j2);

    boolean load(TrustedAppType trustedAppType, ParcelFileDescriptor parcelFileDescriptor, long j, long j2);

    boolean unload(TrustedAppAssetType trustedAppAssetType);

    boolean unload(TrustedAppType trustedAppType);

    static XidlHalService makeHalService() {
        AidlHalService aidlHalService = new AidlHalService();
        return aidlHalService.isAvailable() ? aidlHalService : new HidlHalService();
    }
}

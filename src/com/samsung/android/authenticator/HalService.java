package com.samsung.android.authenticator;

import android.os.ParcelFileDescriptor;

/* loaded from: classes6.dex */
final class HalService {
    private static final String TAG = "HS";
    private static XidlHalService sService;

    private HalService() {
        throw new AssertionError();
    }

    private static synchronized XidlHalService getService() {
        XidlHalService xidlHalService;
        synchronized (HalService.class) {
            if (sService == null) {
                sService = XidlHalService.makeHalService();
            }
            xidlHalService = sService;
        }
        return xidlHalService;
    }

    private static <T> T checkNotNullState(T t) {
        if (t != null) {
            return t;
        }
        throw new IllegalStateException("can not found service");
    }

    public static boolean load(TrustedAppType trustedAppType, ParcelFileDescriptor parcelFileDescriptor, long j, long j2) {
        return ((XidlHalService) checkNotNullState(getService())).load(trustedAppType, parcelFileDescriptor, j, j2);
    }

    public static boolean load(TrustedAppAssetType trustedAppAssetType, ParcelFileDescriptor parcelFileDescriptor, long j, long j2) {
        return ((XidlHalService) checkNotNullState(getService())).load(trustedAppAssetType, parcelFileDescriptor, j, j2);
    }

    public static boolean unload(TrustedAppType trustedAppType) {
        return ((XidlHalService) checkNotNullState(getService())).unload(trustedAppType);
    }

    public static boolean unload(TrustedAppAssetType trustedAppAssetType) {
        return ((XidlHalService) checkNotNullState(getService())).unload(trustedAppAssetType);
    }

    public static byte[] execute(TrustedAppType trustedAppType, byte[] bArr) {
        return ((XidlHalService) checkNotNullState(getService())).execute(trustedAppType, bArr);
    }

    public static byte[] execute(TrustedAppAssetType trustedAppAssetType, byte[] bArr) {
        return ((XidlHalService) checkNotNullState(getService())).execute(trustedAppAssetType, bArr);
    }
}

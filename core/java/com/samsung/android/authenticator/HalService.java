package com.samsung.android.authenticator;

import android.p009os.ParcelFileDescriptor;
import com.samsung.android.authenticator.SemTrustedApplicationExecutor;

/* loaded from: classes5.dex */
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

    private static <T> T checkNotNullState(T reference) {
        if (reference == null) {
            throw new IllegalStateException("can not found service");
        }
        return reference;
    }

    public static boolean load(SemTrustedApplicationExecutor.TrustedAppType type, ParcelFileDescriptor fd, long offset, long len) {
        XidlHalService service = (XidlHalService) checkNotNullState(getService());
        return service.load(type, fd, offset, len);
    }

    public static boolean load(SemTrustedApplicationExecutor.TrustedAppAssetType type, ParcelFileDescriptor fd, long offset, long len) {
        XidlHalService service = (XidlHalService) checkNotNullState(getService());
        return service.load(type, fd, offset, len);
    }

    public static boolean unload(SemTrustedApplicationExecutor.TrustedAppType type) {
        XidlHalService service = (XidlHalService) checkNotNullState(getService());
        return service.unload(type);
    }

    public static boolean unload(SemTrustedApplicationExecutor.TrustedAppAssetType type) {
        XidlHalService service = (XidlHalService) checkNotNullState(getService());
        return service.unload(type);
    }

    public static byte[] execute(SemTrustedApplicationExecutor.TrustedAppType type, byte[] command) {
        XidlHalService service = (XidlHalService) checkNotNullState(getService());
        return service.execute(type, command);
    }

    public static byte[] execute(SemTrustedApplicationExecutor.TrustedAppAssetType type, byte[] command) {
        XidlHalService service = (XidlHalService) checkNotNullState(getService());
        return service.execute(type, command);
    }
}

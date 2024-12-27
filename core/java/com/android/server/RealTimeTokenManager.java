package com.android.server;

import android.os.IBinder;
import android.os.ServiceManager;
import android.util.Slog;

public final class RealTimeTokenManager {
    public static final int RTTS_ERR_GENERAL_ERROR = -101;
    public static final int RTTS_ERR_INVALID_ARGUMENT = -102;
    public static final int RTTS_ERR_INVALID_DEVICE_TIME = -105;
    public static final int RTTS_ERR_OUT_OF_STORAGE = -108;
    public static final int RTTS_ERR_PERMISSION_DENIED = -104;
    public static final int RTTS_ERR_SERVICE_NOT_READY = -103;
    public static final int RTTS_ERR_TOKEN_ALREADY_EXIST = -106;
    public static final int RTTS_ERR_TOKEN_NOT_EXIST = -107;
    public static final int RTTS_ERR_TOKEN_NOT_SUPPORTED = -109;
    public static final int RTTS_SUCCESS = 0;
    private static final String TAG = "RealTimeTokenManager";
    private IRealTimeTokenService mService;

    private RealTimeTokenManager() {
        Slog.i(TAG, "RealTimeTokenManager getService");
        IBinder b = ServiceManager.getService("RealTimeTokenService");
        this.mService = IRealTimeTokenService.Stub.asInterface(b);
        if (this.mService == null) {
            Slog.i(TAG, " Failed to getService, return null");
        }
    }

    public static RealTimeTokenManager getInstance() {
        RealTimeTokenManager rtts = new RealTimeTokenManager();
        return rtts;
    }

    public int registerTokenInfo(long tag, long expiry) {
        try {
            return this.mService.registerTokenInfo(tag, expiry);
        } catch (Exception e) {
            e.printStackTrace();
            return -101;
        }
    }

    public int checkTokenInfoExpiry(long tag) {
        try {
            return this.mService.checkTokenInfoExpiry(tag);
        } catch (Exception e) {
            e.printStackTrace();
            return -101;
        }
    }

    public int unregisterTokenInfo(long tag) {
        try {
            return this.mService.unregisterTokenInfo(tag);
        } catch (Exception e) {
            e.printStackTrace();
            return -101;
        }
    }

    public int unregisterAllTokenInfo() {
        try {
            return this.mService.unregisterAllTokenInfo();
        } catch (Exception e) {
            e.printStackTrace();
            return -101;
        }
    }
}

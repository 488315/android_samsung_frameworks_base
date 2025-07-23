package com.android.server;

import android.os.ServiceManager;
import android.util.Slog;
import com.android.server.IRealTimeTokenService;

/* loaded from: classes6.dex */
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
        IRealTimeTokenService asInterface = IRealTimeTokenService.Stub.asInterface(ServiceManager.getService("RealTimeTokenService"));
        this.mService = asInterface;
        if (asInterface == null) {
            Slog.i(TAG, " Failed to getService, return null");
        }
    }

    public static RealTimeTokenManager getInstance() {
        return new RealTimeTokenManager();
    }

    public int registerTokenInfo(long j, long j2) {
        try {
            return this.mService.registerTokenInfo(j, j2);
        } catch (Exception e) {
            e.printStackTrace();
            return -101;
        }
    }

    public int checkTokenInfoExpiry(long j) {
        try {
            return this.mService.checkTokenInfoExpiry(j);
        } catch (Exception e) {
            e.printStackTrace();
            return -101;
        }
    }

    public int unregisterTokenInfo(long j) {
        try {
            return this.mService.unregisterTokenInfo(j);
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

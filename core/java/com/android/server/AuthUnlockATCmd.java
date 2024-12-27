package com.android.server;

import android.content.Context;
import android.provider.Settings;
import android.service.persistentdata.PersistentDataBlockManager;
import android.util.Slog;

import java.nio.charset.StandardCharsets;

/* loaded from: classes5.dex */
public class AuthUnlockATCmd implements IWorkOnAt {
    private static final String AT_COMMAND_FRPUNLCK = "FRPUNLCK";
    private static final String AT_COMMAND_HEADER = "AT";
    private static final String AT_RESPONSE_CONN_FAILED = "NG (FAILED CONNECTION)";
    private static final String AT_RESPONSE_END = "\r\n\r\nOK\r\n";
    private static final String AT_RESPONSE_INVALID_PARAM = "NG (INVALID_PARAM)";
    private static final String AT_RESPONSE_NA = "NA";
    private static final String AT_RESPONSE_START = "\r\n";
    private static final int ERR_SERVICE_INTERNAL = 1;
    private static final int ERR_SERVICE_NOT_SUPPORTED = 0;
    private static final String TAG = "AuthUnlockATCmd";
    private static final Object mLock = new Object();
    private Context mContext;
    private PersistentDataBlockManager mPDB;
    private int mServiceSupport;

    private native byte[] nativeSessionAccept(byte[] bArr);

    private native int nativeSessionComplete(byte[] bArr);

    static {
        System.loadLibrary("frpunlock");
    }

    public AuthUnlockATCmd(Context context) {
        this.mContext = context;
    }

    @Override // com.android.server.IWorkOnAt
    public String getCmd() {
        return AT_COMMAND_FRPUNLCK;
    }

    @Override // com.android.server.IWorkOnAt
    public String processCmd(String cmd) {
        int ret;
        byte[] response;
        String result = "";
        String[] params = parsingParam(cmd);
        String[] supportedParams = {"1,0,", "1,1,", "3,0,0"};
        if (params == null) {
            return AT_RESPONSE_INVALID_PARAM;
        }
        this.mPDB =
                (PersistentDataBlockManager)
                        this.mContext.getSystemService(Context.PERSISTENT_DATA_BLOCK_SERVICE);
        if (this.mPDB == null) {
            return AT_RESPONSE_CONN_FAILED;
        }
        try {
            result = params[0] + ",";
            if (supportedParams[0].equals(cmd.substring(0, supportedParams[0].length()))) {
                byte[] data = params[2].trim().getBytes(StandardCharsets.UTF_8);
                synchronized (mLock) {
                    try {
                        response = nativeSessionAccept(data);
                    } catch (Exception e) {
                        response = null;
                    }
                }
                if (response != null) {
                    return result + new String(response, StandardCharsets.UTF_8);
                }
                return result + "NG(1)";
            }
            if (supportedParams[1].equals(cmd.substring(0, supportedParams[1].length()))) {
                byte[] data2 = params[2].trim().getBytes(StandardCharsets.UTF_8);
                synchronized (mLock) {
                    try {
                        ret = nativeSessionComplete(data2);
                    } catch (Exception e2) {
                        ret = 1;
                    }
                }
                if (ret == 0) {
                    if (this.mPDB.deactivateFactoryResetProtection(new byte[32])) {
                        Slog.i(TAG, "FRP is deactivated!");
                    } else {
                        Slog.e(TAG, "FRP partition is wiped, but can't update the FRP status");
                    }
                    Settings.Secure.putInt(
                            this.mContext.getContentResolver(), "secure_frp_mode", 0);
                    return result + "UNLOCK SUCCESS";
                }
                Slog.i(TAG, "FRP deactivating FAILED!");
                return result + "NG(1)";
            }
            if (supportedParams[2].equals(cmd.substring(0, supportedParams[2].length()))) {
                if (this.mPDB.isFactoryResetProtectionActive()) {
                    return result + "LOCK";
                }
                return result + "UNLOCK";
            }
            return result + AT_RESPONSE_INVALID_PARAM;
        } catch (Exception e3) {
            String result2 = result + AT_RESPONSE_INVALID_PARAM;
            e3.printStackTrace();
            return result2;
        }
        String result22 = result + AT_RESPONSE_INVALID_PARAM;
        e3.printStackTrace();
        return result22;
    }

    private String[] parsingParam(String cmd) {
        try {
            String params = cmd.substring(0, cmd.length());
            String[] result = params.split(",");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

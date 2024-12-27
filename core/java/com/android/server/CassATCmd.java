package com.android.server;

import android.content.Context;

import com.samsung.android.service.vaultkeeper.VaultKeeperManager;

/* loaded from: classes5.dex */
public class CassATCmd implements IWorkOnAt {
    private static final String AT_COMMAND_CASS = "MGRTCASS";
    private static final String AT_RESPONSE_CONN_FAILED = "NG_FAILEDCONNECTION";
    private static final String AT_RESPONSE_EXCEPTION = "NG_EXCEPTION";
    private static final String AT_RESPONSE_INVALID_PARAM = "NG_INVALIDPARAM";
    private static final String AT_RESPONSE_NG = "NG";
    private static final String AT_RESPONSE_NO_KEY = "NG_NOKEY";
    private static final String AT_RESPONSE_OK = "OK";
    private static final String TAG = CassATCmd.class.getSimpleName();
    private static final String mCassVaultName = "CASS";
    private static Context mContext;
    private VaultKeeperManager mVkm;

    public CassATCmd(Context context) {
        mContext = context;
        this.mVkm = VaultKeeperManager.getInstance(mCassVaultName);
    }

    @Override // com.android.server.IWorkOnAt
    public String getCmd() {
        new String();
        return AT_COMMAND_CASS;
    }

    @Override // com.android.server.IWorkOnAt
    public String processCmd(String cmd) {
        String result = new String();
        String[] params = parsingParam(cmd);
        String[] supportedParams = {"0,0,0,0"};
        if (params == null) {
            return AT_RESPONSE_INVALID_PARAM;
        }
        if (this.mVkm == null) {
            return AT_RESPONSE_CONN_FAILED;
        }
        try {
            String result2 = params[0] + ",";
            if (!supportedParams[0].equals(cmd)) {
                result = result2 + AT_RESPONSE_INVALID_PARAM;
            } else if (this.mVkm.isInitialized()) {
                if (this.mVkm.migrationStorage()) {
                    result = result2 + "OK";
                } else {
                    result = result2 + "NG";
                }
            } else {
                result = result2 + AT_RESPONSE_NO_KEY;
            }
            return result;
        } catch (Exception e) {
            return result + AT_RESPONSE_EXCEPTION;
        }
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

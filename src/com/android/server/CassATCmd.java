package com.android.server;

import android.content.Context;
import com.samsung.android.service.vaultkeeper.VaultKeeperManager;

/* loaded from: classes6.dex */
public class CassATCmd implements IWorkOnAt {
    private static final String AT_COMMAND_CASS = "MGRTCASS";
    private static final String AT_RESPONSE_CONN_FAILED = "NG_FAILEDCONNECTION";
    private static final String AT_RESPONSE_EXCEPTION = "NG_EXCEPTION";
    private static final String AT_RESPONSE_INVALID_PARAM = "NG_INVALIDPARAM";
    private static final String AT_RESPONSE_NG = "NG";
    private static final String AT_RESPONSE_NO_KEY = "NG_NOKEY";
    private static final String AT_RESPONSE_OK = "OK";
    private static final String TAG = "CassATCmd";
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
    public String processCmd(String str) {
        String str2 = new String();
        String[] parsingParam = parsingParam(str);
        String[] strArr = {"0,0,0,0"};
        if (parsingParam == null) {
            return AT_RESPONSE_INVALID_PARAM;
        }
        if (this.mVkm == null) {
            return AT_RESPONSE_CONN_FAILED;
        }
        try {
            String str3 = parsingParam[0] + ",";
            if (strArr[0].equals(str)) {
                if (this.mVkm.isInitialized()) {
                    if (this.mVkm.migrationStorage()) {
                        return str3 + "OK";
                    }
                    return str3 + "NG";
                }
                return str3 + AT_RESPONSE_NO_KEY;
            }
            return str3 + AT_RESPONSE_INVALID_PARAM;
        } catch (Exception unused) {
            return str2 + AT_RESPONSE_EXCEPTION;
        }
    }

    private String[] parsingParam(String str) {
        try {
            return str.substring(0, str.length()).split(",");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

package com.android.server;

import android.content.Context;
import android.util.Log;

/* loaded from: classes5.dex */
public class SamsungAttestationATCmd extends DevRootKeyATCmd implements IWorkOnAt {
    protected static final String TAG = "DEVROOT#ATCmd(2.0.0)";
    protected static final String VERSION = "2.0.0";

    public SamsungAttestationATCmd(Context context) {
        super(context);
        if (isSupportnewSAKatcmd) {
            Log.m98i(TAG, "SkeymintATCmd start");
        } else {
            Log.m98i(TAG, "SkeymintATCmd is not start");
        }
    }

    @Override // com.android.server.DevRootKeyATCmd, com.android.server.IWorkOnAt
    public String getCmd() {
        return "DEVROOTK";
    }

    @Override // com.android.server.DevRootKeyATCmd, com.android.server.IWorkOnAt
    public String processCmd(String cmd) {
        if (!isSupportnewSAKatcmd) {
            Log.m98i(TAG, "DevRootKeyATCmd.processCmd is run");
            return super.processCmd(cmd);
        }
        String[] params = parsingParam(cmd);
        if (params != null && params.length == 3) {
            try {
                Log.m98i(TAG, "ProcessCmd [" + cmd + "] start");
                String result = params[0] + ",";
                if ((Integer.parseInt(params[0]) == 0 || Integer.parseInt(params[0]) == 1) && !params[2].equals("0")) {
                    return result + SecureKeyConst.AT_RESPONSE_INVALID_PARAM;
                }
                switch (Integer.parseInt(params[0] + params[1])) {
                    case 0:
                    case 1:
                    case 2:
                    case 10:
                    case 11:
                    case 12:
                    case 20:
                    case 21:
                    case 22:
                    case 23:
                        Log.m98i(TAG, "New DRK AT cmd");
                        return super.processCmd(cmd);
                    case 3:
                    case 4:
                    case 5:
                    case 13:
                    case 24:
                    case 25:
                    case 26:
                    case 27:
                        Log.m98i(TAG, "New SamsungAttestation AT cmd");
                        return super.processCmd(cmd);
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                    case 14:
                    case 15:
                    case 16:
                    case 17:
                    case 18:
                    case 19:
                    default:
                        Log.m98i(TAG, "ProcessCmd [" + cmd + "] end");
                        return result;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "NG_FAIL(EXCEPTION_OCCURS) " + e.getMessage();
            }
        }
        return SecureKeyConst.AT_RESPONSE_INVALID_PARAM;
    }
}

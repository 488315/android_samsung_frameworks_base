package com.android.server;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Slog;
import com.samsung.android.service.EngineeringMode.EngineeringModeManager;

/* loaded from: classes6.dex */
public class AutoBlockATCmd implements IWorkOnAt {
    private static final String ACTION_MODE_RESET_AUTOBLOCKER = "com.samsung.android.intent.action.MODE_RESET_AUTOBLOCKER";
    private static final String AT_COMMAND_BLOCKER = "ABSTACHK";
    private static final String AT_COMMAND_HEADER = "AT";
    private static final String AT_RESPONSE_ATBLOCK_OFF = "OFF";
    private static final String AT_RESPONSE_ATBLOCK_ON = "ON";
    private static final String AT_RESPONSE_EXCEPTION = "NG (EXCEPTION)";
    private static final String AT_RESPONSE_INVALID_PARAM = "NG (INVALID_PARAM)";
    private static final String AT_RESPONSE_NG_FAIL = "NG,NOK";
    private static final String AT_RESPONSE_NG_NOTOKEN = "NG,NO_TOK";
    private static final String AT_RESPONSE_OK = "OK";
    private static final int EM_IDX = 61;
    private static final String PERMISSION_ACCESS_AUTOBLOCKER = "com.samsung.android.permission.ACCESS_AUTOBLOCKER";
    private static final String RAMPART = "com.samsung.android.rampart";
    private static final String TAG = "AutoBlockATCmd";
    private static Context mContext;
    private EngineeringModeManager mEMMgr;

    public AutoBlockATCmd(Context context) {
        mContext = context;
        this.mEMMgr = new EngineeringModeManager(context.getApplicationContext());
    }

    @Override // com.android.server.IWorkOnAt
    public String getCmd() {
        return AT_COMMAND_BLOCKER;
    }

    @Override // com.android.server.IWorkOnAt
    public String processCmd(String str) {
        String str2;
        String str3;
        String[] strArrParsingParam = parsingParam(str);
        String[] strArr = {"0,0", "1,0"};
        if (strArrParsingParam == null) {
            Slog.i(TAG, "processCmd: params is null");
            return AT_RESPONSE_INVALID_PARAM;
        }
        try {
            String str4 = strArrParsingParam[0] + ",";
            String str5 = strArr[0];
            if (str5.equals(str.substring(0, str5.length()))) {
                Slog.i(TAG, "AT+ABSTACHK=0,0");
                EngineeringModeManager engineeringModeManager = this.mEMMgr;
                if (engineeringModeManager != null && engineeringModeManager.isConnected()) {
                    if (this.mEMMgr.getStatus(61) == 1) {
                        Intent intent = new Intent(ACTION_MODE_RESET_AUTOBLOCKER);
                        intent.setPackage(RAMPART);
                        intent.addFlags(32);
                        mContext.sendBroadcast(intent, "com.samsung.android.permission.ACCESS_AUTOBLOCKER");
                        str3 = str4 + "OK";
                    } else {
                        str3 = str4 + AT_RESPONSE_NG_NOTOKEN;
                    }
                    String str6 = str3;
                    Slog.i(TAG, "AT+ABSTACHK=0,0 is complete.");
                    return str6;
                }
                Slog.i(TAG, "Cannot connect to em service");
                return AT_RESPONSE_NG_FAIL;
            }
            String str7 = strArr[1];
            if (str7.equals(str.substring(0, str7.length()))) {
                Slog.i(TAG, "AT+ABSTACHK=1,0");
                if (isRampartBlockedAdbCommand()) {
                    str2 = str4 + AT_RESPONSE_ATBLOCK_ON;
                } else {
                    str2 = str4 + "OFF";
                }
                String str8 = str2;
                Slog.i(TAG, "AT+ABSTACHK=1,0 is complete.");
                return str8;
            }
            return str4 + AT_RESPONSE_INVALID_PARAM;
        } catch (Exception e) {
            String str9 = "" + AT_RESPONSE_EXCEPTION;
            e.printStackTrace();
            return str9;
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

    private boolean isRampartBlockedAdbCommand() {
        return Settings.Secure.getInt(mContext.getContentResolver(), Settings.Secure.RAMPART_MAIN_SWITCH_ENABLED, 0) == 1;
    }
}

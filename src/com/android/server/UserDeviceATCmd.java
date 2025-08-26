package com.android.server;

import android.content.Context;
import android.os.Build;
import android.os.SystemProperties;
import android.util.Slog;
import com.samsung.android.lock.LsConstants;
import com.samsung.android.service.EngineeringMode.EngineeringModeManager;

/* loaded from: classes6.dex */
public class UserDeviceATCmd implements IWorkOnAt {
    private static final String ANDROID_RB_PROPERTY = "sys.powerctl";
    private static final String AT_COMMAND_HEADER = "AT";
    private static final String AT_COMMAND_URDEVICE = "URDEVICE";
    private static final String AT_RESPONSE_CONN_FAILED = "NG (FAILED CONNECTION)";
    private static final String AT_RESPONSE_DEV = "1";
    private static final String AT_RESPONSE_ERR = "0";
    private static final String AT_RESPONSE_EXCEPTION = "NG (EXCEPTION)";
    private static final String AT_RESPONSE_EXIST_EM_TOKEN = "EMTOKEN";
    private static final String AT_RESPONSE_INVALID_PARAM = "NG (INVALID_PARAM)";
    private static final String AT_RESPONSE_NG = "NG";
    private static final String AT_RESPONSE_NO_EM_TOKEN = "NONE";
    private static final String AT_RESPONSE_OK = "OK";
    private static final String AT_RESPONSE_USR = "2";
    private static final String EM_PROPERTY = "ro.boot.em.status";
    private static final String EM_PROPERTY_STATE_DEV = "0x1";
    private static final String EM_PROPERTY_STATE_USR = "0x0";
    private static final String EM_PROPERTY_STATE_USR_WITH_EM = "0x2";
    private static final String RB_CMD_EM_FORCE_USER = "em_mode_force_user";
    private static final String TAG = "UserDeviceATCmd";
    private static Context mContext;
    private EngineeringModeManager mEMMgr;

    public UserDeviceATCmd(Context context) {
        mContext = context;
        this.mEMMgr = new EngineeringModeManager(context.getApplicationContext());
    }

    @Override // com.android.server.IWorkOnAt
    public String getCmd() {
        return AT_COMMAND_URDEVICE;
    }

    @Override // com.android.server.IWorkOnAt
    public String processCmd(String str) {
        String str2;
        String str3;
        String[] strArrParsingParam = parsingParam(str);
        String[] strArr = {"0,0,0,0", "1,0,0,0", "0,1,0,0", "0,2,0,0"};
        if (strArrParsingParam == null) {
            Slog.i(TAG, "processCmd: params is null");
            return AT_RESPONSE_INVALID_PARAM;
        }
        EngineeringModeManager engineeringModeManager = this.mEMMgr;
        if (engineeringModeManager == null) {
            Slog.i(TAG, "Cannot connect to em service");
            return AT_RESPONSE_CONN_FAILED;
        }
        if (!engineeringModeManager.isConnected()) {
            Slog.i(TAG, "Failed to connect to em service");
            return AT_RESPONSE_CONN_FAILED;
        }
        try {
            String str4 = strArrParsingParam[0] + ",";
            String str5 = strArr[0];
            if (str5.equals(str.substring(0, str5.length()))) {
                Slog.i(TAG, "AT+URDEVICE=0,0,0,0");
                if (this.mEMMgr.removeToken() == 1) {
                    str3 = str4 + "OK";
                } else {
                    str3 = str4 + "NG";
                }
                String str6 = str3;
                Slog.i(TAG, "0,0,0,0 is complete.");
                return str6;
            }
            String str7 = strArr[1];
            if (str7.equals(str.substring(0, str7.length()))) {
                Slog.i(TAG, "AT+URDEVICE=1,0,0,0");
                String str8 = "0";
                String str9 = SystemProperties.get(EM_PROPERTY);
                if (str9.equals(EM_PROPERTY_STATE_USR) || str9.equals(EM_PROPERTY_STATE_USR_WITH_EM)) {
                    str8 = AT_RESPONSE_USR;
                } else if (str9.equals(EM_PROPERTY_STATE_DEV)) {
                    str8 = "1";
                }
                String str10 = "NONE";
                if (Build.VERSION.DEVICE_INITIAL_SDK_INT < 28 && this.mEMMgr.isTokenInstalled() == 1) {
                    str10 = AT_RESPONSE_EXIST_EM_TOKEN;
                }
                String str11 = str4 + makeResCmd(str8, str10);
                Slog.i(TAG, "1,0,0,0 is complete.");
                return str11;
            }
            String str12 = strArr[2];
            if (str12.equals(str.substring(0, str12.length()))) {
                Slog.i(TAG, "AT+URDEVICE=0,1,0,0");
                SystemProperties.set(ANDROID_RB_PROPERTY, "reboot,em_mode_force_user");
                String str13 = str4 + "OK";
                Slog.i(TAG, "0,1,0,0 is complete.");
                return str13;
            }
            String str14 = strArr[3];
            if (str14.equals(str.substring(0, str14.length()))) {
                Slog.i(TAG, "AT+URDEVICE=0,2,0,0");
                if (this.mEMMgr.sendFuseCmd() == 1) {
                    str2 = str4 + "OK";
                } else {
                    str2 = str4 + "NG";
                }
                String str15 = str2;
                Slog.i(TAG, "0,2,0,0 is complete.");
                return str15;
            }
            return str4 + AT_RESPONSE_INVALID_PARAM;
        } catch (Exception e) {
            String str16 = "" + AT_RESPONSE_EXCEPTION;
            e.printStackTrace();
            return str16;
        }
    }

    private String makeResCmd(String str, String str2) {
        String str3;
        if (str.equals(AT_RESPONSE_USR) && str2.equals("NONE")) {
            str3 = "OK,";
        } else {
            str3 = "NG,";
        }
        return str3 + str + "," + str2;
    }

    private String[] parsingParam(String str) {
        try {
            return str.substring(0, str.length()).split(",");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean isFactoryBinary() {
        return "factory".equalsIgnoreCase(SystemProperties.get("ro.factory.factory_binary", LsConstants.TAG_UNKNOWN));
    }
}

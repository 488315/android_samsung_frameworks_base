package com.android.server;

import android.content.Context;
import android.os.Build;
import android.os.SystemProperties;
import android.util.Slog;

import com.samsung.android.service.EngineeringMode.EngineeringModeManager;

/* loaded from: classes5.dex */
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
    public String processCmd(String cmd) {
        String result;
        String tokStatus;
        String result2;
        String[] params = parsingParam(cmd);
        String[] supportedParams = {"0,0,0,0", "1,0,0,0", "0,1,0,0", "0,2,0,0"};
        if (params == null) {
            Slog.i(TAG, "processCmd: params is null");
            return AT_RESPONSE_INVALID_PARAM;
        }
        if (this.mEMMgr == null) {
            Slog.i(TAG, "Cannot connect to em service");
            return AT_RESPONSE_CONN_FAILED;
        }
        if (!this.mEMMgr.isConnected()) {
            Slog.i(TAG, "Failed to connect to em service");
            return AT_RESPONSE_CONN_FAILED;
        }
        try {
            String result3 = params[0] + ",";
            if (supportedParams[0].equals(cmd.substring(0, supportedParams[0].length()))) {
                Slog.i(TAG, "AT+URDEVICE=0,0,0,0");
                if (this.mEMMgr.removeToken() == 1) {
                    result2 = result3 + "OK";
                } else {
                    result2 = result3 + "NG";
                }
                Slog.i(TAG, "0,0,0,0 is complete.");
                return result2;
            }
            if (supportedParams[1].equals(cmd.substring(0, supportedParams[1].length()))) {
                Slog.i(TAG, "AT+URDEVICE=1,0,0,0");
                String devStatus = "0";
                String emProp = SystemProperties.get(EM_PROPERTY);
                if (!emProp.equals(EM_PROPERTY_STATE_USR)
                        && !emProp.equals(EM_PROPERTY_STATE_USR_WITH_EM)) {
                    if (emProp.equals(EM_PROPERTY_STATE_DEV)) {
                        devStatus = "1";
                    }
                    tokStatus = "NONE";
                    if (Build.VERSION.DEVICE_INITIAL_SDK_INT < 28
                            && this.mEMMgr.isTokenInstalled() == 1) {
                        tokStatus = AT_RESPONSE_EXIST_EM_TOKEN;
                    }
                    String result4 = result3 + makeResCmd(devStatus, tokStatus);
                    Slog.i(TAG, "1,0,0,0 is complete.");
                    return result4;
                }
                devStatus = AT_RESPONSE_USR;
                tokStatus = "NONE";
                if (Build.VERSION.DEVICE_INITIAL_SDK_INT < 28) {
                    tokStatus = AT_RESPONSE_EXIST_EM_TOKEN;
                }
                String result42 = result3 + makeResCmd(devStatus, tokStatus);
                Slog.i(TAG, "1,0,0,0 is complete.");
                return result42;
            }
            if (supportedParams[2].equals(cmd.substring(0, supportedParams[2].length()))) {
                Slog.i(TAG, "AT+URDEVICE=0,1,0,0");
                SystemProperties.set(ANDROID_RB_PROPERTY, "reboot,em_mode_force_user");
                String result5 = result3 + "OK";
                Slog.i(TAG, "0,1,0,0 is complete.");
                return result5;
            }
            if (!supportedParams[3].equals(cmd.substring(0, supportedParams[3].length()))) {
                return result3 + AT_RESPONSE_INVALID_PARAM;
            }
            Slog.i(TAG, "AT+URDEVICE=0,2,0,0");
            if (this.mEMMgr.sendFuseCmd() == 1) {
                result = result3 + "OK";
            } else {
                result = result3 + "NG";
            }
            Slog.i(TAG, "0,2,0,0 is complete.");
            return result;
        } catch (Exception e) {
            String result6 = "" + AT_RESPONSE_EXCEPTION;
            e.printStackTrace();
            return result6;
        }
    }

    private String makeResCmd(String resDev, String resTok) {
        String result;
        if (resDev.equals(AT_RESPONSE_USR) && resTok.equals("NONE")) {
            result = "OK,";
        } else {
            result = "NG,";
        }
        return result + resDev + "," + resTok;
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

    private boolean isFactoryBinary() {
        return "factory"
                .equalsIgnoreCase(SystemProperties.get("ro.factory.factory_binary", "Unknown"));
    }
}

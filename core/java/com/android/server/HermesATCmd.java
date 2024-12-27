package com.android.server;

import android.content.Context;
import android.util.Log;
import com.samsung.android.service.HermesService.HermesServiceManager;

/* loaded from: classes5.dex */
public class HermesATCmd implements IWorkOnAt {
    private static final String AT_COMMAND_HEADER = "AT";
    private static final String AT_COMMAND_HERMES = "ISOSECHW";
    private static final int AT_MAIN_INDEX = 0;
    private static final int AT_MAIN_INDEX_OPERATION = 0;
    private static final int AT_MAIN_INDEX_READ_DATA = 1;
    private static final int AT_MAIN_INDEX_TEST_DATA = 9;
    private static final int AT_MAIN_INDEX_WRITE_DATA = 2;
    private static final int AT_MAIN_OPERATION = 0;
    private static final int AT_MAIN_READ_DATA = 10;
    private static final int AT_MAIN_TEST_DATA = 90;
    private static final int AT_MAIN_WRITE_DATA = 20;
    private static final int AT_MID_INDEX = 1;
    private static final int AT_MINOR_INDEX = 2;
    private static final String AT_RESPONSE_FAILED = "NG";
    private static final String AT_RESPONSE_INVALID_PARAM = "NG(INVALID_PARAM)";
    private static final String AT_RESPONSE_NONE = "NONE";
    private static final String AT_RESPONSE_OK = "OK";
    private static final int NOT_PROVISIONED = 10000;
    private static final int NO_ERROR = 0;
    private static final int SAMSUNG_HERMES_CLOSE = 91;
    private static final int SAMSUNG_HERMES_COS_PATCH = 93;
    private static final int SAMSUNG_HERMES_GET_SECUREHW_INFO = 10;
    private static final int SAMSUNG_HERMES_GET_SEID = 94;
    private static final int SAMSUNG_HERMES_OPEN = 90;
    private static final int SAMSUNG_HERMES_PROVISIONING = 1;
    private static final int SAMSUNG_HERMES_SELFTEST = 0;
    private static final int SAMSUNG_HERMES_SEND_APDU = 92;
    private static final int SAMSUNG_HERMES_UPDATE_CRYPTO_FW = 2;
    private static final int SAMSUNG_HERMES_VERIFY_PROVISONING = 11;
    private static final String TAG = "HERMES#ATCmd";
    private Context mContext;
    private HermesServiceManager mHermesServiceManager;

    private HermesServiceManager bindHermesServiceManager() {
        if (this.mHermesServiceManager == null) {
            Log.i(TAG, "bindHermesServiceManager() is called.");
            this.mHermesServiceManager = new HermesServiceManager(this.mContext.getApplicationContext());
        }
        return this.mHermesServiceManager;
    }

    public HermesATCmd(Context context) {
        this.mContext = context;
    }

    @Override // com.android.server.IWorkOnAt
    public String getCmd() {
        return AT_COMMAND_HERMES;
    }

    @Override // com.android.server.IWorkOnAt
    public String processCmd(String cmd) {
        String[] params = parsingParam(cmd);
        if (params == null) {
            Log.e(TAG, "ISOSECHW processCmd wrong param.");
            return AT_RESPONSE_INVALID_PARAM;
        }
        String result = new String(params[0] + ",");
        try {
            Log.i(TAG, "ISOSECHW ProcessCmd [" + cmd + "] start");
            switch (Integer.parseInt(params[0] + params[1])) {
                case 0:
                    if (params[2].equals("0")) {
                        byte[] stinfo = bindHermesServiceManager().hermesSelftest();
                        if (stinfo == null) {
                            result = result + "OK";
                        } else {
                            result = result + "NG_" + new String(stinfo);
                        }
                        break;
                    } else {
                        bindHermesServiceManager().hermesSelftest(params[2]);
                        break;
                    }
                case 1:
                    int ret = bindHermesServiceManager().hermesProvisioning();
                    if (ret == 0) {
                        result = result + "OK";
                        break;
                    } else {
                        result = result + "NG" + ret;
                        break;
                    }
                case 2:
                    if (params[2].equals("0")) {
                        byte[] resultinfo = bindHermesServiceManager().hermesUpdateCryptoFW();
                        if (resultinfo != null) {
                            result = result + new String(resultinfo);
                            break;
                        } else {
                            result = result + "NG";
                            break;
                        }
                    } else if (params[2].equals("1")) {
                        byte[] resultinfo2 = bindHermesServiceManager().hermesUpdateApplet();
                        if (resultinfo2 != null) {
                            result = result + new String(resultinfo2);
                            break;
                        } else {
                            result = result + "NG";
                            break;
                        }
                    }
                case 10:
                    byte[] chipinfo = bindHermesServiceManager().hermesGetSecureHWInfo();
                    if (chipinfo != null) {
                        result = result + new String(chipinfo);
                        break;
                    } else {
                        result = result + "NG";
                        break;
                    }
                case 11:
                    int ret2 = bindHermesServiceManager().hermesVerifyProvisioning();
                    if (ret2 == 0) {
                        result = result + "OK";
                        break;
                    } else if (ret2 == 10000) {
                        result = result + "NONE";
                        break;
                    } else {
                        result = result + "NG" + ret2;
                        break;
                    }
                case 90:
                    int ret3 = bindHermesServiceManager().open();
                    if (ret3 == 0) {
                        result = result + "OK";
                        break;
                    } else {
                        result = result + "NG" + ret3;
                        break;
                    }
                case 91:
                    int ret4 = bindHermesServiceManager().close();
                    if (ret4 == 0) {
                        result = result + "OK";
                        break;
                    } else {
                        result = result + "NG" + ret4;
                        break;
                    }
                case 92:
                    byte[] rapdu = bindHermesServiceManager().send(hexStringToByteArray(params[2]));
                    if (rapdu != null) {
                        result = result + byteArrayToHexString(rapdu);
                        break;
                    } else {
                        result = result + "NG";
                        break;
                    }
                case 93:
                    byte[] res = bindHermesServiceManager().cosPatchTest(hexStringToByteArray(params[2]));
                    if (res != null) {
                        result = result + new String(res);
                        break;
                    } else {
                        result = result + "NG";
                        break;
                    }
                case 94:
                    byte[] cplc = bindHermesServiceManager().getSeId();
                    if (cplc != null) {
                        result = result + byteArrayToHexString(cplc);
                        break;
                    } else {
                        result = result + "NG";
                        break;
                    }
                default:
                    Log.e(TAG, "ISOSECHW ProcessCmd wrong command.");
                    result = result + AT_RESPONSE_INVALID_PARAM;
                    break;
            }
            Log.i(TAG, "ISOSECHW ProcessCmd [" + cmd + "] end");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return result + "NG " + e.getMessage();
        }
    }

    private String[] parsingParam(String cmd) {
        try {
            String[] result = cmd.split(",");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private byte[] hexStringToByteArray(String hexString) {
        int length = hexString.length();
        byte[] byteArray = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            byteArray[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character.digit(hexString.charAt(i + 1), 16));
        }
        return byteArray;
    }

    private String byteArrayToHexString(byte[] byteArray) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : byteArray) {
            hexString.append(String.format("%02X", Byte.valueOf(b)));
        }
        return hexString.toString();
    }
}

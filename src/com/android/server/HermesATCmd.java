package com.android.server;

import android.content.Context;
import android.util.Log;
import com.samsung.android.service.HermesService.HermesServiceManager;
import java.nio.charset.StandardCharsets;

/* loaded from: classes6.dex */
public class HermesATCmd implements IWorkOnAt {
    private static final String AT_COMMAND_HEADER = "AT";
    private static final String AT_COMMAND_HERMES = "ISOSECHW";
    private static final int AT_LAST_INDEX = 3;
    private static final int AT_MAIN_INDEX = 0;
    private static final int AT_MAIN_INDEX_OPERATION = 0;
    private static final int AT_MAIN_INDEX_READ_DATA = 1;
    private static final int AT_MAIN_INDEX_SELFTEST_DATA = 0;
    private static final int AT_MAIN_INDEX_TEST_DATA = 9;
    private static final int AT_MAIN_INDEX_WRITE_DATA = 2;
    private static final int AT_MAIN_OPERATION = 0;
    private static final int AT_MAIN_READ_DATA = 10;
    private static final int AT_MAIN_SELFTEST_DATA = 0;
    private static final int AT_MAIN_TEST_DATA = 90;
    private static final int AT_MAIN_WRITE_DATA = 20;
    private static final int AT_MID_INDEX = 1;
    private static final int AT_MINOR_INDEX = 2;
    private static final String AT_RESPONSE_FAILED = "NG";
    private static final String AT_RESPONSE_INVALID_PARAM = "NG(INVALID_PARAM)";
    private static final String AT_RESPONSE_NONE = "NONE";
    private static final String AT_RESPONSE_OK = "OK";
    private static final int NOT_PROVISIONED = 10000;
    private static final int NOT_PROVISIONED_MAX = 10004;
    private static final int NO_ERROR = 0;
    private static final int SAMSUNG_HERMES2_BIST_SPECIFIC = 3;
    private static final int SAMSUNG_HERMES_CLOSE = 91;
    private static final int SAMSUNG_HERMES_COS_PATCH = 93;
    private static final int SAMSUNG_HERMES_GET_APPLET_VERSION = 12;
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

    /* JADX WARN: Removed duplicated region for block: B:71:0x02d8 A[Catch: Exception -> 0x0394, TryCatch #0 {Exception -> 0x0394, blocks: (B:7:0x0026, B:17:0x006a, B:86:0x037f, B:18:0x0080, B:20:0x008a, B:21:0x009f, B:22:0x00b0, B:24:0x00c0, B:25:0x00d8, B:26:0x00e9, B:28:0x00f9, B:29:0x010e, B:30:0x011f, B:32:0x0129, B:33:0x013a, B:34:0x014e, B:36:0x0158, B:37:0x0169, B:38:0x017d, B:40:0x0187, B:41:0x019f, B:42:0x01b0, B:44:0x01ba, B:49:0x01d3, B:50:0x01e6, B:71:0x02d8, B:73:0x02e2, B:74:0x02fa, B:51:0x01fa, B:53:0x01fe, B:55:0x021e, B:57:0x0230, B:58:0x024a, B:59:0x0260, B:61:0x0268, B:63:0x0272, B:64:0x028a, B:65:0x029b, B:67:0x02a5, B:69:0x02af, B:70:0x02c7, B:75:0x030b, B:77:0x0315, B:78:0x0325, B:79:0x0338, B:81:0x0340, B:83:0x034a, B:84:0x035b, B:85:0x0376), top: B:91:0x0026 }] */
    @Override // com.android.server.IWorkOnAt
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public String processCmd(String str) throws NumberFormatException {
        String str2;
        String[] strArrParsingParam = parsingParam(str);
        if (strArrParsingParam == null) {
            Log.e(TAG, "ISOSECHW processCmd wrong param.");
            return AT_RESPONSE_INVALID_PARAM;
        }
        String str3 = strArrParsingParam[0] + ",";
        try {
            Log.i(TAG, "ISOSECHW ProcessCmd [" + str + "] start");
            int i = Integer.parseInt(strArrParsingParam[0] + strArrParsingParam[1]);
            if (i != 0) {
                if (i == 1) {
                    int iHermesProvisioning = bindHermesServiceManager().hermesProvisioning();
                    if (iHermesProvisioning == 0) {
                        str3 = str3 + "OK";
                    } else {
                        str3 = str3 + "NG" + iHermesProvisioning;
                    }
                } else if (i != 2) {
                    if (i == 3) {
                        if (strArrParsingParam.length >= 4) {
                            byte[] bArrHermesSelftest = bindHermesServiceManager().hermesSelftest(strArrParsingParam[2] + "," + strArrParsingParam[3]);
                            if (bArrHermesSelftest == null) {
                                str2 = str3 + "OK";
                            } else {
                                str2 = str3 + "NG_" + new String(bArrHermesSelftest, StandardCharsets.UTF_8);
                            }
                            str3 = str2;
                        } else {
                            Log.e(TAG, "ISOSECHW ProcessCmd(8,0,X,X) need 4 params.");
                            str3 = str3 + AT_RESPONSE_INVALID_PARAM;
                        }
                    } else {
                        switch (i) {
                            case 10:
                                byte[] bArrHermesGetSecureHWInfo = bindHermesServiceManager().hermesGetSecureHWInfo();
                                if (bArrHermesGetSecureHWInfo != null) {
                                    str3 = str3 + new String(bArrHermesGetSecureHWInfo, StandardCharsets.UTF_8);
                                    break;
                                } else {
                                    str3 = str3 + "NG";
                                    break;
                                }
                            case 11:
                                int iHermesVerifyProvisioning = bindHermesServiceManager().hermesVerifyProvisioning();
                                if (iHermesVerifyProvisioning != 0) {
                                    if (iHermesVerifyProvisioning >= 10000 && iHermesVerifyProvisioning <= 10004) {
                                        str3 = str3 + "NONE";
                                        break;
                                    } else {
                                        str3 = str3 + "NG" + iHermesVerifyProvisioning;
                                        break;
                                    }
                                } else {
                                    str3 = str3 + "OK";
                                    break;
                                }
                            case 12:
                                byte[] bArrHermesGetAppletVersion = bindHermesServiceManager().hermesGetAppletVersion();
                                if (bArrHermesGetAppletVersion != null) {
                                    str3 = str3 + new String(bArrHermesGetAppletVersion, StandardCharsets.UTF_8);
                                    break;
                                } else {
                                    str3 = str3 + "NG";
                                    break;
                                }
                            default:
                                switch (i) {
                                    case 90:
                                        int iOpen = bindHermesServiceManager().open();
                                        if (iOpen == 0) {
                                            str3 = str3 + "OK";
                                            break;
                                        } else {
                                            str3 = str3 + "NG" + iOpen;
                                            break;
                                        }
                                    case 91:
                                        int iClose = bindHermesServiceManager().close();
                                        if (iClose == 0) {
                                            str3 = str3 + "OK";
                                            break;
                                        } else {
                                            str3 = str3 + "NG" + iClose;
                                            break;
                                        }
                                    case 92:
                                        byte[] bArrSend = bindHermesServiceManager().send(hexStringToByteArray(strArrParsingParam[2]));
                                        if (bArrSend != null) {
                                            str3 = str3 + byteArrayToHexString(bArrSend);
                                            break;
                                        } else {
                                            str3 = str3 + "NG";
                                            break;
                                        }
                                    case 93:
                                        byte[] bArrCosPatchTest = bindHermesServiceManager().cosPatchTest(hexStringToByteArray(strArrParsingParam[2]));
                                        if (bArrCosPatchTest != null) {
                                            str3 = str3 + new String(bArrCosPatchTest, StandardCharsets.UTF_8);
                                            break;
                                        } else {
                                            str3 = str3 + "NG";
                                            break;
                                        }
                                    case 94:
                                        byte[] seId = bindHermesServiceManager().getSeId();
                                        if (seId != null) {
                                            str3 = str3 + byteArrayToHexString(seId);
                                            break;
                                        } else {
                                            str3 = str3 + "NG";
                                            break;
                                        }
                                    default:
                                        Log.e(TAG, "ISOSECHW ProcessCmd wrong command.");
                                        str3 = str3 + AT_RESPONSE_INVALID_PARAM;
                                        break;
                                }
                        }
                    }
                } else if (strArrParsingParam[2].equals("0")) {
                    byte[] bArrHermesUpdateCryptoFW = bindHermesServiceManager().hermesUpdateCryptoFW();
                    if (bArrHermesUpdateCryptoFW != null) {
                        str3 = str3 + new String(bArrHermesUpdateCryptoFW, StandardCharsets.UTF_8);
                    } else {
                        str3 = str3 + "NG";
                    }
                } else if (strArrParsingParam[2].equals("1")) {
                    byte[] bArrHermesUpdateApplet = bindHermesServiceManager().hermesUpdateApplet();
                    if (bArrHermesUpdateApplet != null) {
                        str3 = str3 + new String(bArrHermesUpdateApplet, StandardCharsets.UTF_8);
                    } else {
                        str3 = str3 + "NG";
                    }
                }
            } else if (strArrParsingParam[2].equals("0")) {
                byte[] bArrHermesSelftest2 = bindHermesServiceManager().hermesSelftest();
                if (bArrHermesSelftest2 == null) {
                    str2 = str3 + "OK";
                } else {
                    str2 = str3 + "NG_" + new String(bArrHermesSelftest2, StandardCharsets.UTF_8);
                }
                str3 = str2;
            } else {
                bindHermesServiceManager().hermesSelftest(strArrParsingParam[2]);
            }
            Log.i(TAG, "ISOSECHW ProcessCmd [" + str + "] end");
            return str3;
        } catch (Exception e) {
            e.printStackTrace();
            return str3 + "NG " + e.getMessage();
        }
    }

    private String[] parsingParam(String str) {
        try {
            return str.split(",");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private byte[] hexStringToByteArray(String str) {
        int length = str.length();
        byte[] bArr = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            bArr[i / 2] = (byte) ((Character.digit(str.charAt(i), 16) << 4) + Character.digit(str.charAt(i + 1), 16));
        }
        return bArr;
    }

    private String byteArrayToHexString(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            sb.append(String.format("%02X", Byte.valueOf(b)));
        }
        return sb.toString();
    }
}

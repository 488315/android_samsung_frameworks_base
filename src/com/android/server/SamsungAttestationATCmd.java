package com.android.server;

import android.content.Context;
import android.os.SystemProperties;
import android.telecom.Logging.Session;
import android.util.Log;

/* loaded from: classes6.dex */
public class SamsungAttestationATCmd extends DevRootKeyATCmd implements IWorkOnAt {
    private static final String TAG = "DEVROOT#ATCmd(2.0.0)";
    private static final String VERSION = "2.0.0";

    public SamsungAttestationATCmd(Context context) {
        super(context);
    }

    @Override // com.android.server.DevRootKeyATCmd, com.android.server.IWorkOnAt
    public String processCmd(String str) {
        String str2;
        int parseInt;
        int parseInt2;
        int installDeviceUnboundKey;
        if (!isSupportATCommandV2) {
            Log.i(TAG, "DevRootKeyATCmd.processCmd is run");
            return super.processCmd(str);
        }
        String[] parsingParam = parsingParam(str);
        if (parsingParam == null || parsingParam.length != 3) {
            return SecureKeyConst.AT_RESPONSE_INVALID_PARAM;
        }
        try {
            Log.i(TAG, "ProcessCmd [" + str + "] start");
            String str3 = parsingParam[0] + ",";
            if ((Integer.parseInt(parsingParam[0]) == 0 || Integer.parseInt(parsingParam[0]) == 1) && !parsingParam[2].equals("0")) {
                return str3 + SecureKeyConst.AT_RESPONSE_INVALID_PARAM;
            }
            int parseInt3 = Integer.parseInt(parsingParam[0] + parsingParam[1]);
            if (parseInt3 != 0 && parseInt3 != 1 && parseInt3 != 2) {
                if (parseInt3 == 3) {
                    int validateDeviceKey = validateDeviceKey(3);
                    if (validateDeviceKey != 0) {
                        str2 = str3 + "NG_FAIL " + validateDeviceKey;
                    } else {
                        str2 = str3 + SecureKeyConst.AT_RESPONSE_OK;
                    }
                } else if (parseInt3 == 4) {
                    if ("factory".equals(SystemProperties.get("ro.factory.factory_binary"))) {
                        Log.i(TAG, "Skipping device ID provision on factory binary.");
                    } else {
                        int provisionDeviceID = provisionDeviceID(1);
                        if (provisionDeviceID != 0) {
                            str2 = str3 + "NG_FAIL " + provisionDeviceID;
                        }
                    }
                    if ("1".equals(SystemProperties.get("remote_provisioning.tee.rkp_only"))) {
                        Log.i(TAG, "Skipping validateDeviceKey on rkp_only.");
                        str2 = str3 + SecureKeyConst.AT_RESPONSE_OK;
                    } else {
                        int validateDeviceKey2 = validateDeviceKey(1);
                        if (validateDeviceKey2 != 0) {
                            str2 = str3 + "NG_FAIL " + validateDeviceKey2;
                        } else {
                            str2 = str3 + SecureKeyConst.AT_RESPONSE_OK;
                        }
                    }
                } else if (parseInt3 == 5) {
                    if ("factory".equals(SystemProperties.get("ro.factory.factory_binary"))) {
                        Log.i(TAG, "Skipping device ID provision on factory binary.");
                    } else {
                        int provisionDeviceID2 = provisionDeviceID(4);
                        if (provisionDeviceID2 != 0) {
                            str2 = str3 + "NG_FAIL " + provisionDeviceID2;
                        }
                    }
                    int validateDeviceKey3 = validateDeviceKey(4);
                    if (validateDeviceKey3 != 0) {
                        str2 = str3 + "NG_FAIL " + validateDeviceKey3;
                    } else {
                        str2 = str3 + SecureKeyConst.AT_RESPONSE_OK;
                    }
                } else {
                    switch (parseInt3) {
                        default:
                            switch (parseInt3) {
                                case 20:
                                case 21:
                                case 22:
                                case 23:
                                    break;
                                case 24:
                                    if (parsingParam[2].length() != 17) {
                                        str2 = str3 + SecureKeyConst.AT_RESPONSE_INVALID_PARAM;
                                        break;
                                    } else {
                                        byte[] generateCertificateSigningRequest = generateCertificateSigningRequest(3, parsingParam[2].substring(0, 14), parsingParam[2].substring(14, 17));
                                        if (generateCertificateSigningRequest != null) {
                                            str2 = str3 + new String(generateCertificateSigningRequest);
                                            break;
                                        } else {
                                            str2 = str3 + SecureKeyConst.AT_RESPONSE_FAILED;
                                            break;
                                        }
                                    }
                                case 25:
                                    String substring = parsingParam[2].substring(2, 5);
                                    if (!parsingParam[2].substring(0, 2).equals(SecureKeyConst.AT_CMD_DRK_V2_VERSION)) {
                                        str2 = str3 + SecureKeyConst.AT_RESPONSE_INVALID_PARAM;
                                        break;
                                    } else {
                                        if (substring.equals(SecureKeyConst.AT_CMD_DRK_V2_WRITING_END)) {
                                            parseInt = getKeyBlobIndex() + 1;
                                        } else {
                                            parseInt = Integer.parseInt(substring);
                                        }
                                        String str4 = parsingParam[2];
                                        if (!appendKeyBlob(parseInt, str4.substring(str4.indexOf(Session.SESSION_SEPARATION_CHAR_CHILD) + 1, parsingParam[2].length()).trim())) {
                                            str2 = str3 + "NG_FAIL(DATA MISSED) SN-" + parseInt + " TB-" + (getKeyBlobIndex() + 1);
                                            break;
                                        } else {
                                            if (substring.equals(SecureKeyConst.AT_CMD_DRK_V2_WRITING_END)) {
                                                int installDeviceBoundCertificate = installDeviceBoundCertificate(3, getTotalKeyBlob());
                                                initTlvKeyBlob();
                                                if (installDeviceBoundCertificate != 0) {
                                                    str2 = str3 + "NG_FAIL " + installDeviceBoundCertificate;
                                                    break;
                                                } else {
                                                    sendSakUidMsgAppletBindingIntent();
                                                }
                                            }
                                            str2 = str3 + SecureKeyConst.AT_RESPONSE_OK;
                                            break;
                                        }
                                    }
                                case 26:
                                case 27:
                                    String substring2 = parsingParam[2].substring(2, 5);
                                    if (!parsingParam[2].substring(0, 2).equals(SecureKeyConst.AT_CMD_DRK_V2_VERSION)) {
                                        str2 = str3 + SecureKeyConst.AT_RESPONSE_INVALID_PARAM;
                                        break;
                                    } else {
                                        if (substring2.equals(SecureKeyConst.AT_CMD_DRK_V2_WRITING_END)) {
                                            parseInt2 = getKeyBlobIndex() + 1;
                                        } else {
                                            parseInt2 = Integer.parseInt(substring2);
                                        }
                                        String str5 = parsingParam[2];
                                        if (!appendKeyBlob(parseInt2, str5.substring(str5.indexOf(Session.SESSION_SEPARATION_CHAR_CHILD) + 1, parsingParam[2].length()).trim())) {
                                            str2 = str3 + "NG_FAIL(DATA MISSED) SN-" + parseInt2 + " TB-" + (getKeyBlobIndex() + 1);
                                            break;
                                        } else {
                                            if (substring2.equals(SecureKeyConst.AT_CMD_DRK_V2_WRITING_END)) {
                                                if (Integer.parseInt(parsingParam[0] + parsingParam[1]) == 26) {
                                                    installDeviceUnboundKey = installDeviceUnboundKey(1, getTotalKeyBlob());
                                                } else {
                                                    installDeviceUnboundKey = installDeviceUnboundKey(4, getTotalKeyBlob());
                                                }
                                                initTlvKeyBlob();
                                                if (installDeviceUnboundKey != 0) {
                                                    str2 = str3 + "NG_FAIL " + installDeviceUnboundKey;
                                                    break;
                                                }
                                            }
                                            str2 = str3 + SecureKeyConst.AT_RESPONSE_OK;
                                            break;
                                        }
                                    }
                                default:
                                    str2 = str3 + SecureKeyConst.AT_RESPONSE_UNIMPLEMENTED;
                                    break;
                            }
                        case 10:
                        case 11:
                        case 12:
                        case 13:
                            break;
                    }
                }
                Log.i(TAG, "ProcessCmd [" + str + "] end");
                return str2;
            }
            return super.processCmd(str);
        } catch (Exception e) {
            e.printStackTrace();
            return "NG_FAIL(EXCEPTION_OCCURS) " + e.getMessage();
        }
    }
}

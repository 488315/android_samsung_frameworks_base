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

    /* JADX WARN: Removed duplicated region for block: B:52:0x0195 A[Catch: Exception -> 0x03ed, TryCatch #0 {Exception -> 0x03ed, blocks: (B:11:0x0029, B:13:0x005b, B:19:0x007d, B:32:0x00bd, B:112:0x03d3, B:33:0x00d0, B:35:0x00e5, B:36:0x00f6, B:38:0x00fc, B:40:0x0107, B:42:0x0123, B:43:0x0145, B:45:0x014b, B:47:0x0168, B:49:0x017c, B:51:0x0181, B:48:0x0173, B:52:0x0195, B:39:0x0103, B:53:0x01a6, B:55:0x01bb, B:56:0x01cc, B:58:0x01d2, B:60:0x01e1, B:62:0x01fd, B:63:0x0221, B:65:0x0227, B:67:0x0235, B:68:0x0249, B:69:0x024c, B:59:0x01db, B:70:0x025d, B:72:0x0269, B:73:0x027a, B:75:0x0290, B:76:0x02a6, B:77:0x02b9, B:79:0x02c3, B:91:0x0308, B:93:0x030e, B:94:0x0322, B:81:0x02c8, B:83:0x02d4, B:85:0x02e0, B:88:0x02ed, B:90:0x02f4, B:95:0x0333, B:97:0x033d, B:101:0x035c, B:103:0x036b, B:104:0x0380, B:106:0x0387, B:107:0x039a, B:98:0x0341, B:100:0x0348, B:108:0x03aa, B:110:0x03b1, B:111:0x03c4, B:114:0x03e8, B:15:0x0063, B:17:0x006d), top: B:120:0x0029 }] */
    @Override // com.android.server.DevRootKeyATCmd, com.android.server.IWorkOnAt
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public String processCmd(String str) throws NumberFormatException {
        String str2;
        int iProvisionDeviceID;
        if (!isSupportATCommandV2) {
            Log.i(TAG, "DevRootKeyATCmd.processCmd is run");
            return super.processCmd(str);
        }
        String[] strArrParsingParam = parsingParam(str);
        if (strArrParsingParam == null || strArrParsingParam.length != 3) {
            return SecureKeyConst.AT_RESPONSE_INVALID_PARAM;
        }
        try {
            Log.i(TAG, "ProcessCmd [" + str + "] start");
            String str3 = strArrParsingParam[0] + ",";
            if ((Integer.parseInt(strArrParsingParam[0]) == 0 || Integer.parseInt(strArrParsingParam[0]) == 1) && !strArrParsingParam[2].equals("0")) {
                return str3 + SecureKeyConst.AT_RESPONSE_INVALID_PARAM;
            }
            int i = Integer.parseInt(strArrParsingParam[0] + strArrParsingParam[1]);
            if (i != 0 && i != 1 && i != 2) {
                if (i == 3) {
                    int iValidateDeviceKey = validateDeviceKey(3);
                    if (iValidateDeviceKey != 0) {
                        str2 = str3 + "NG_FAIL " + iValidateDeviceKey;
                    } else {
                        str2 = str3 + SecureKeyConst.AT_RESPONSE_OK;
                    }
                } else if (i == 4) {
                    if ("factory".equals(SystemProperties.get("ro.factory.factory_binary"))) {
                        Log.i(TAG, "Skipping device ID provision on factory binary.");
                    } else {
                        int iProvisionDeviceID2 = provisionDeviceID(1);
                        if (iProvisionDeviceID2 != 0) {
                            str2 = str3 + "NG_FAIL " + iProvisionDeviceID2;
                        }
                    }
                    if ("1".equals(SystemProperties.get("remote_provisioning.tee.rkp_only"))) {
                        Log.i(TAG, "Skipping validateDeviceKey on rkp_only.");
                        str2 = str3 + SecureKeyConst.AT_RESPONSE_OK;
                    } else {
                        int iValidateDeviceKey2 = validateDeviceKey(1);
                        if (iValidateDeviceKey2 != 0) {
                            str2 = str3 + "NG_FAIL " + iValidateDeviceKey2;
                        } else {
                            str2 = str3 + SecureKeyConst.AT_RESPONSE_OK;
                        }
                    }
                } else if (i != 5) {
                    switch (i) {
                        default:
                            switch (i) {
                                case 20:
                                case 21:
                                case 22:
                                case 23:
                                    break;
                                case 24:
                                    if (strArrParsingParam[2].length() == 17) {
                                        byte[] bArrGenerateCertificateSigningRequest = generateCertificateSigningRequest(3, strArrParsingParam[2].substring(0, 14), strArrParsingParam[2].substring(14, 17));
                                        if (bArrGenerateCertificateSigningRequest == null) {
                                            str2 = str3 + SecureKeyConst.AT_RESPONSE_FAILED;
                                            break;
                                        } else {
                                            str2 = str3 + new String(bArrGenerateCertificateSigningRequest);
                                            break;
                                        }
                                    } else {
                                        str2 = str3 + SecureKeyConst.AT_RESPONSE_INVALID_PARAM;
                                        break;
                                    }
                                case 25:
                                    String strSubstring = strArrParsingParam[2].substring(2, 5);
                                    if (!strArrParsingParam[2].substring(0, 2).equals(SecureKeyConst.AT_CMD_DRK_V2_VERSION)) {
                                        str2 = str3 + SecureKeyConst.AT_RESPONSE_INVALID_PARAM;
                                        break;
                                    } else {
                                        int keyBlobIndex = strSubstring.equals(SecureKeyConst.AT_CMD_DRK_V2_WRITING_END) ? getKeyBlobIndex() + 1 : Integer.parseInt(strSubstring);
                                        String str4 = strArrParsingParam[2];
                                        if (!appendKeyBlob(keyBlobIndex, str4.substring(str4.indexOf(Session.SESSION_SEPARATION_CHAR_CHILD) + 1, strArrParsingParam[2].length()).trim())) {
                                            str2 = str3 + "NG_FAIL(DATA MISSED) SN-" + keyBlobIndex + " TB-" + (getKeyBlobIndex() + 1);
                                            break;
                                        } else if (strSubstring.equals(SecureKeyConst.AT_CMD_DRK_V2_WRITING_END)) {
                                            int iInstallDeviceBoundCertificate = installDeviceBoundCertificate(3, getTotalKeyBlob());
                                            initTlvKeyBlob();
                                            if (iInstallDeviceBoundCertificate == 0) {
                                                sendSakUidMsgAppletBindingIntent();
                                                str2 = str3 + SecureKeyConst.AT_RESPONSE_OK;
                                                break;
                                            } else {
                                                str2 = str3 + "NG_FAIL " + iInstallDeviceBoundCertificate;
                                                break;
                                            }
                                        } else {
                                            str2 = str3 + SecureKeyConst.AT_RESPONSE_OK;
                                        }
                                    }
                                case 26:
                                case 27:
                                    String strSubstring2 = strArrParsingParam[2].substring(2, 5);
                                    if (!strArrParsingParam[2].substring(0, 2).equals(SecureKeyConst.AT_CMD_DRK_V2_VERSION)) {
                                        str2 = str3 + SecureKeyConst.AT_RESPONSE_INVALID_PARAM;
                                        break;
                                    } else {
                                        int keyBlobIndex2 = strSubstring2.equals(SecureKeyConst.AT_CMD_DRK_V2_WRITING_END) ? getKeyBlobIndex() + 1 : Integer.parseInt(strSubstring2);
                                        String str5 = strArrParsingParam[2];
                                        if (!appendKeyBlob(keyBlobIndex2, str5.substring(str5.indexOf(Session.SESSION_SEPARATION_CHAR_CHILD) + 1, strArrParsingParam[2].length()).trim())) {
                                            str2 = str3 + "NG_FAIL(DATA MISSED) SN-" + keyBlobIndex2 + " TB-" + (getKeyBlobIndex() + 1);
                                            break;
                                        } else if (strSubstring2.equals(SecureKeyConst.AT_CMD_DRK_V2_WRITING_END)) {
                                            StringBuilder sb = new StringBuilder();
                                            sb.append(strArrParsingParam[0]);
                                            sb.append(strArrParsingParam[1]);
                                            int iInstallDeviceUnboundKey = Integer.parseInt(sb.toString()) == 26 ? installDeviceUnboundKey(1, getTotalKeyBlob()) : installDeviceUnboundKey(4, getTotalKeyBlob());
                                            initTlvKeyBlob();
                                            if (iInstallDeviceUnboundKey == 0) {
                                                str2 = str3 + SecureKeyConst.AT_RESPONSE_OK;
                                                break;
                                            } else {
                                                str2 = str3 + "NG_FAIL " + iInstallDeviceUnboundKey;
                                                break;
                                            }
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
                            return super.processCmd(str);
                    }
                } else {
                    if ("factory".equals(SystemProperties.get("ro.factory.factory_binary"))) {
                        Log.i(TAG, "Skipping device ID provision on factory binary.");
                    } else if (!SystemProperties.get("ro.build.flavor", "").contains("m1q") && !SystemProperties.get("ro.build.flavor", "").contains("m2q") && !SystemProperties.get("ro.build.flavor", "").contains("m3q") && (iProvisionDeviceID = provisionDeviceID(4)) != 0) {
                        str2 = str3 + "NG_FAIL " + iProvisionDeviceID;
                    }
                    int iValidateDeviceKey3 = validateDeviceKey(4);
                    if (iValidateDeviceKey3 != 0) {
                        str2 = str3 + "NG_FAIL " + iValidateDeviceKey3;
                    } else {
                        str2 = str3 + SecureKeyConst.AT_RESPONSE_OK;
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

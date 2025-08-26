package com.android.server;

import android.content.Context;
import android.os.SystemProperties;
import android.util.Slog;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.SyncFailedException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/* loaded from: classes6.dex */
public class HdcptestATCmd implements IWorkOnAt {
    private static final String AT_COMMAND_HDCPTEST = "HDCPTEST";
    private static final String AT_COMMAND_HEADER = "AT";
    private static final String AT_COMMON_INTERVAL = " ";
    private static final int AT_HDCP_DP_HASH_SIZE = 32;
    private static final String AT_HDCP_DP_VER_13_INSTALL_CMD = "idp1";
    private static final String AT_HDCP_DP_VER_13_INSTALL_M_CMD = "id1m";
    private static final String AT_HDCP_DP_VER_13_VERIFY_CMD = "vdp1";
    private static final String AT_HDCP_DP_VER_13_VERIFY_M_CMD = "vd1m";
    private static final String AT_HDCP_DP_VER_13_WRITE_CMD = "wdp1";
    private static final String AT_HDCP_DP_VER_13_WRITE_M_CMD = "wd1m";
    private static final String AT_HDCP_DP_VER_22_INSTALL_CMD = "idp2";
    private static final String AT_HDCP_DP_VER_22_INSTALL_M_CMD = "id2m";
    private static final String AT_HDCP_DP_VER_22_VERIFY_CMD = "vdp2";
    private static final String AT_HDCP_DP_VER_22_VERIFY_M_CMD = "vd2m";
    private static final String AT_HDCP_DP_VER_22_WRITE_CMD = "wdp2";
    private static final String AT_HDCP_DP_VER_22_WRITE_M_CMD = "wd2m";
    private static final String AT_HDCP_FILE_PATH_CPK = "/efs/cpk";
    private static final String AT_HDCP_FILE_PATH_EFS = "/efs";
    private static final String AT_HDCP_KEY_20 = "/h2k.dat";
    private static final String AT_HDCP_VERIFY_CMD = "vhdk";
    private static final String AT_HDCP_WRITE_CMD = "whdk";
    private static final String AT_RESPONSE_ERROR_EXEC = "NG (ERROR_EXEC)";
    private static final String AT_RESPONSE_EXCEPTION = "NG (EXCEPTION)";
    private static final String AT_RESPONSE_INTEGRITY_FAIL = "NG (INTEGRITY CHK FAIL)";
    private static final String AT_RESPONSE_INVALID_PARAM = "NG (INVALID_PARAM)";
    private static final String AT_RESPONSE_NG = "NG";
    private static final String AT_RESPONSE_NG_FIELD = "NG_FIELD";
    private static final String AT_RESPONSE_NG_KEY = "NG_KEY";
    private static final String AT_RESPONSE_NO_DATA = "NG (NO_DATA)";
    private static final String AT_RESPONSE_NO_EFS_PARTITION = "NG (NO_EFS)";
    private static final String AT_RESPONSE_NO_EXIST_PATH = "NG (NO_PATH)";
    private static final String AT_RESPONSE_OK = "OK";
    private static final String AT_SERIAL_PATH = "/sys/class/scsi_host/host0/unique_number";
    private static final String AT_SERIAL_PATH2 = "/sys/block/mmcblk0/device/cid";
    private static final String AT_SERIAL_PATH3 = "/sys/class/sec/ufs/un";
    private static final int AT_SERIAL_SIZE = 32;
    private static final String AT_WV_DEFAULT_SERIAL = "S000000000000000";
    private static final String AT_WV_INSTALL_CMD = "iwvk";
    private static final String AT_WV_KEY = "/efs/wv.keys";
    private static final String AT_WV_KEY_HUAQIN = "persist/data/widevine/widevine";
    private static final String AT_WV_VERIFY_CMD = "vwvk";
    private static final String AT_WV_VERIFY_CMD_JDM = "jvwk";
    private static final String AT_WV_ZERO_STRING = "0";
    private static final String EFS_PARTITION = "/efs";
    private static final int ERROR_EXEC = 44;
    private static final int ERROR_INTERNAL = 1;
    private static final int NO_ERROR = 0;
    private static final String TAG = "HdcptestATCmd";
    private static final int TYPE_DIR = 1;
    private static final int TYPE_FILE = 2;
    private static final String VENDOR_EFS_PARTITION = "/mnt/vendor/efs";
    private static Context mContext = null;
    private static final String productType = "in_house";
    private int mErrorCode;
    private boolean mRunningBSD = false;

    native int sendTobsd(String str);

    public HdcptestATCmd(Context context) {
        setContext(context);
        System.loadLibrary("BSD_jni");
    }

    @Override // com.android.server.IWorkOnAt
    public String getCmd() {
        return AT_COMMAND_HDCPTEST;
    }

    private static void setContext(Context context) {
        mContext = context;
    }

    /* JADX WARN: Removed duplicated region for block: B:314:0x08e1 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:342:0x08ca A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:348:0x0905 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // com.android.server.IWorkOnAt
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public String processCmd(String str) throws Throwable {
        Throwable th;
        String str2;
        String str3;
        String str4;
        String[] strArr;
        String str5;
        String str6;
        String str7;
        String str8;
        String str9;
        String str10;
        String str11;
        String str12;
        FileInputStream fileInputStream;
        String strTrim;
        String str13;
        String str14;
        String str15;
        String str16;
        int iSendTobsd;
        String str17;
        String str18;
        String str19 = str;
        String[] strArrParsingParam = parsingParam(str);
        String[] strArr2 = {"0,0", "0,3", "0,4", "0,5", "0,8", "0,9", "1,0", "2,", "3,3,", "3,4,", "3,5,", "3,8,", "3,9,"};
        if (strArrParsingParam == null) {
            Slog.i(TAG, "processCmd: params is null");
            return AT_RESPONSE_INVALID_PARAM;
        }
        FileInputStream fileInputStream2 = null;
        fileInputStream = null;
        str = null;
        str = null;
        String str20 = null;
        FileInputStream fileInputStream3 = null;
        fileInputStream = null;
        fileInputStream = null;
        fileInputStream = null;
        FileInputStream fileInputStream4 = null;
        try {
            try {
                if (this.mRunningBSD) {
                    strArr = strArr2;
                } else {
                    Slog.i(TAG, "Start BSD service!");
                    strArr = strArr2;
                    SystemProperties.set("ctl.start", "bsd");
                    this.mRunningBSD = true;
                }
                str5 = strArrParsingParam[0] + ",";
                try {
                    str6 = strArr[0];
                } catch (Exception e) {
                    e = e;
                    str2 = AT_RESPONSE_EXCEPTION;
                }
            } catch (Exception e2) {
                e = e2;
                str2 = AT_RESPONSE_EXCEPTION;
                str3 = "";
            }
            try {
                try {
                    try {
                        if (str6.equals(str19.substring(0, str6.length()))) {
                            Slog.i(TAG, "AT+HDCPTEST=0,0");
                            if (!checkPath("/efs", 1)) {
                                Slog.i(TAG, "efs partition is not mounted");
                                return AT_RESPONSE_NO_EFS_PARTITION;
                            }
                            int iSendTobsd2 = sendTobsd(AT_HDCP_VERIFY_CMD);
                            if (iSendTobsd2 == 0) {
                                str18 = str5 + "OK";
                            } else if (checkPath("/efs/cpk/h2k.dat", 2)) {
                                if (iSendTobsd2 == 44) {
                                    str18 = str5 + AT_RESPONSE_ERROR_EXEC;
                                } else {
                                    str18 = str5 + AT_RESPONSE_NG_FIELD;
                                }
                            } else if (!checkPath("/efs/h2k.dat", 2)) {
                                str18 = str5 + AT_RESPONSE_NG_KEY;
                            } else if (iSendTobsd2 == 44) {
                                str18 = str5 + AT_RESPONSE_ERROR_EXEC;
                            } else {
                                str18 = str5 + AT_RESPONSE_NG_FIELD;
                            }
                            str19 = str18;
                            Slog.i(TAG, "0,0 is complete!");
                        } else {
                            String str21 = strArr[1];
                            if (str21.equals(str19.substring(0, str21.length()))) {
                                Slog.i(TAG, "AT+HDCPTEST=0,3");
                                if (SystemProperties.getInt("ro.product.first_api_level", 0) >= 29 || checkPath(AT_WV_KEY, 2)) {
                                    iSendTobsd = sendTobsd(AT_WV_VERIFY_CMD);
                                } else {
                                    str5 = str5 + AT_RESPONSE_NG_KEY;
                                    iSendTobsd = 44;
                                }
                                if (iSendTobsd == 0) {
                                    str17 = str5 + "OK";
                                } else if (iSendTobsd == 44) {
                                    str17 = str5 + AT_RESPONSE_ERROR_EXEC;
                                } else {
                                    str17 = str5 + AT_RESPONSE_NG_FIELD;
                                }
                                str19 = str17;
                                Slog.i(TAG, "0,3 is complete.");
                            } else {
                                String str22 = strArr[2];
                                if (str22.equals(str19.substring(0, str22.length()))) {
                                    Slog.i(TAG, "AT+HDCPTEST=0,4");
                                    if (sendTobsd(AT_HDCP_DP_VER_22_VERIFY_CMD) == 0) {
                                        str16 = str5 + "OK";
                                    } else {
                                        int iSendTobsd3 = sendTobsd(AT_HDCP_DP_VER_22_INSTALL_CMD);
                                        if (iSendTobsd3 == 0) {
                                            str16 = str5 + "OK";
                                        } else if (iSendTobsd3 == 44) {
                                            str16 = str5 + AT_RESPONSE_ERROR_EXEC;
                                        } else {
                                            str16 = str5 + "NG";
                                        }
                                    }
                                    str19 = str16;
                                    Slog.i(TAG, "0,4 is complete.");
                                } else {
                                    String str23 = strArr[3];
                                    if (str23.equals(str19.substring(0, str23.length()))) {
                                        Slog.i(TAG, "AT+HDCPTEST=0,5");
                                        if (sendTobsd(AT_HDCP_DP_VER_13_VERIFY_CMD) == 0) {
                                            str15 = str5 + "OK";
                                        } else {
                                            int iSendTobsd4 = sendTobsd(AT_HDCP_DP_VER_13_INSTALL_CMD);
                                            if (iSendTobsd4 == 0) {
                                                str15 = str5 + "OK";
                                            } else if (iSendTobsd4 == 44) {
                                                str15 = str5 + AT_RESPONSE_ERROR_EXEC;
                                            } else {
                                                str15 = str5 + "NG";
                                            }
                                        }
                                        str19 = str15;
                                        Slog.i(TAG, "0,5 is complete.");
                                    } else {
                                        String str24 = strArr[4];
                                        if (str24.equals(str19.substring(0, str24.length()))) {
                                            Slog.i(TAG, "AT+HDCPTEST=0,8");
                                            if (sendTobsd(AT_HDCP_DP_VER_22_VERIFY_M_CMD) == 0) {
                                                str14 = str5 + "OK";
                                            } else {
                                                int iSendTobsd5 = sendTobsd(AT_HDCP_DP_VER_22_INSTALL_M_CMD);
                                                if (iSendTobsd5 == 0) {
                                                    str14 = str5 + "OK";
                                                } else if (iSendTobsd5 == 44) {
                                                    str14 = str5 + AT_RESPONSE_ERROR_EXEC;
                                                } else {
                                                    str14 = str5 + "NG";
                                                }
                                            }
                                            str19 = str14;
                                            Slog.i(TAG, "0,8 is complete.");
                                        } else {
                                            String str25 = strArr[5];
                                            if (!str25.equals(str19.substring(0, str25.length()))) {
                                                String str26 = strArr[6];
                                                if (str26.equals(str19.substring(0, str26.length()))) {
                                                    Slog.i(TAG, "AT+HDCPTEST=1,0");
                                                    String str27 = checkPath(AT_SERIAL_PATH3, 2) ? AT_SERIAL_PATH3 : checkPath(AT_SERIAL_PATH, 2) ? AT_SERIAL_PATH : checkPath(AT_SERIAL_PATH2, 2) ? AT_SERIAL_PATH2 : null;
                                                    if (str27 != null) {
                                                        byte[] bArr = new byte[32];
                                                        fileInputStream = new FileInputStream(str27);
                                                        try {
                                                            try {
                                                                if (fileInputStream.read(bArr) != -1) {
                                                                    if (str27.equals(AT_SERIAL_PATH) || str27.equals(AT_SERIAL_PATH3)) {
                                                                        strTrim = new String(bArr).trim();
                                                                    } else if (str27.equals(AT_SERIAL_PATH2)) {
                                                                        strTrim = new String(bArr).trim().substring(16, 32);
                                                                    }
                                                                    str20 = strTrim;
                                                                } else {
                                                                    Slog.e(TAG, "Read S/N Failed");
                                                                }
                                                            } catch (Exception e3) {
                                                                e = e3;
                                                                fileInputStream4 = fileInputStream;
                                                                str3 = str5;
                                                                str2 = AT_RESPONSE_EXCEPTION;
                                                                String str28 = str3 + str2;
                                                                e.printStackTrace();
                                                                if (fileInputStream4 != null) {
                                                                    try {
                                                                        fileInputStream4.close();
                                                                    } catch (Exception e4) {
                                                                        e = e4;
                                                                        str4 = str28 + str2;
                                                                        e.printStackTrace();
                                                                        return str4;
                                                                    }
                                                                }
                                                                return str28;
                                                            }
                                                        } catch (Throwable th2) {
                                                            th = th2;
                                                            fileInputStream2 = fileInputStream;
                                                            if (fileInputStream2 == null) {
                                                                throw th;
                                                            }
                                                            try {
                                                                fileInputStream2.close();
                                                                throw th;
                                                            } catch (Exception e5) {
                                                                e5.printStackTrace();
                                                                throw th;
                                                            }
                                                        }
                                                    } else {
                                                        fileInputStream = null;
                                                    }
                                                    if (str20 == null) {
                                                        str20 = AT_WV_DEFAULT_SERIAL;
                                                    }
                                                    String str29 = str20;
                                                    if (str29.length() < 16) {
                                                        if (str29.length() <= 0) {
                                                            str29 = "0000000000000000";
                                                        } else {
                                                            str29 = str29 + AT_WV_DEFAULT_SERIAL.substring(str29.length());
                                                        }
                                                    }
                                                    Slog.i(TAG, "Serial Number : " + str29);
                                                    str19 = str5 + str29;
                                                    try {
                                                        Slog.i(TAG, "1,0 is complete.");
                                                        fileInputStream3 = fileInputStream;
                                                    } catch (Exception e6) {
                                                        e = e6;
                                                        str3 = str19;
                                                        fileInputStream4 = fileInputStream;
                                                        str2 = AT_RESPONSE_EXCEPTION;
                                                        String str282 = str3 + str2;
                                                        e.printStackTrace();
                                                        if (fileInputStream4 != null) {
                                                        }
                                                        return str282;
                                                    }
                                                } else {
                                                    String str30 = strArr[7];
                                                    if (str30.equals(str19.substring(0, str30.length()))) {
                                                        Slog.i(TAG, "AT+HDCPTEST=2,Data");
                                                        if (!checkPath("/efs", 1)) {
                                                            Slog.i(TAG, "efs partition is not mounted");
                                                            return AT_RESPONSE_NO_EFS_PARTITION;
                                                        }
                                                        String str31 = strArrParsingParam[1];
                                                        if (str31 == null || str31.length() == 0) {
                                                            str12 = str5 + AT_RESPONSE_NO_DATA;
                                                        } else {
                                                            Slog.i(TAG, "Param size : " + strArrParsingParam[1].length());
                                                            makeDirectory(AT_HDCP_FILE_PATH_CPK);
                                                            if (sendTobsd(AT_HDCP_WRITE_CMD + strArrParsingParam[1]) == 0) {
                                                                str12 = str5 + "OK";
                                                            } else {
                                                                str12 = str5 + "NG";
                                                            }
                                                        }
                                                        str19 = str12;
                                                        Slog.i(TAG, "2,Data is complete.");
                                                    } else {
                                                        String str32 = strArr[8];
                                                        if (str32.equals(str19.substring(0, str32.length()))) {
                                                            Slog.i(TAG, "AT+HDCPTEST=3,3,Data");
                                                            String str33 = strArrParsingParam[2];
                                                            if (str33 == null || str33.length() == 0) {
                                                                str11 = str5 + AT_RESPONSE_NO_DATA;
                                                            } else {
                                                                Slog.i(TAG, "Param size : " + strArrParsingParam[2].length());
                                                                int iSendTobsd6 = sendTobsd(AT_WV_INSTALL_CMD + strArrParsingParam[2]);
                                                                if (iSendTobsd6 == 0) {
                                                                    str11 = str5 + "OK";
                                                                } else if (iSendTobsd6 == 44) {
                                                                    str11 = str5 + AT_RESPONSE_ERROR_EXEC;
                                                                } else {
                                                                    str11 = str5 + "NG";
                                                                }
                                                            }
                                                            str19 = str11;
                                                            Slog.i(TAG, "3,3,Data is complete.");
                                                        } else {
                                                            String str34 = strArr[9];
                                                            if (str34.equals(str19.substring(0, str34.length()))) {
                                                                Slog.i(TAG, "AT+HDCPTEST=3,4,Data");
                                                                if (!checkPath("/efs", 1)) {
                                                                    Slog.i(TAG, "efs partition is not mounted");
                                                                    return AT_RESPONSE_NO_EFS_PARTITION;
                                                                }
                                                                byte[] bArrHexToByteArray = hexToByteArray(strArrParsingParam[2]);
                                                                if (bArrHexToByteArray == null) {
                                                                    str10 = str5 + AT_RESPONSE_NO_DATA;
                                                                } else if (checkMsgIntegrity(bArrHexToByteArray)) {
                                                                    Slog.i(TAG, "bArray size : " + bArrHexToByteArray.length);
                                                                    makeDirectory(AT_HDCP_FILE_PATH_CPK);
                                                                    if (sendTobsd(AT_HDCP_DP_VER_22_WRITE_CMD + strArrParsingParam[2]) == 0) {
                                                                        str10 = str5 + "OK";
                                                                    } else {
                                                                        str10 = str5 + "NG";
                                                                    }
                                                                } else {
                                                                    Slog.i(TAG, "Failed to check integtiry -size:" + bArrHexToByteArray.length);
                                                                    str10 = str5 + AT_RESPONSE_INTEGRITY_FAIL;
                                                                }
                                                                str19 = str10;
                                                                Slog.i(TAG, "3,4,Data is complete.");
                                                            } else {
                                                                String str35 = strArr[10];
                                                                if (str35.equals(str19.substring(0, str35.length()))) {
                                                                    Slog.i(TAG, "AT+HDCPTEST=3,5,Data");
                                                                    if (!checkPath("/efs", 1)) {
                                                                        Slog.i(TAG, "efs partition is not mounted");
                                                                        return AT_RESPONSE_NO_EFS_PARTITION;
                                                                    }
                                                                    byte[] bArrHexToByteArray2 = hexToByteArray(strArrParsingParam[2]);
                                                                    if (bArrHexToByteArray2 == null) {
                                                                        str9 = str5 + AT_RESPONSE_NO_DATA;
                                                                    } else if (checkMsgIntegrity(bArrHexToByteArray2)) {
                                                                        Slog.i(TAG, "bArray size : " + bArrHexToByteArray2.length);
                                                                        makeDirectory(AT_HDCP_FILE_PATH_CPK);
                                                                        if (sendTobsd(AT_HDCP_DP_VER_13_WRITE_CMD + strArrParsingParam[2]) == 0) {
                                                                            str9 = str5 + "OK";
                                                                        } else {
                                                                            str9 = str5 + "NG";
                                                                        }
                                                                    } else {
                                                                        Slog.i(TAG, "Failed to check integtiry -size:" + bArrHexToByteArray2.length);
                                                                        str9 = str5 + AT_RESPONSE_INTEGRITY_FAIL;
                                                                    }
                                                                    str19 = str9;
                                                                    Slog.i(TAG, "3,5,Data is complete.");
                                                                } else {
                                                                    String str36 = strArr[11];
                                                                    if (str36.equals(str19.substring(0, str36.length()))) {
                                                                        Slog.i(TAG, "AT+HDCPTEST=3,8,Data");
                                                                        if (!checkPath("/efs", 1)) {
                                                                            Slog.i(TAG, "efs partition is not mounted");
                                                                            return AT_RESPONSE_NO_EFS_PARTITION;
                                                                        }
                                                                        byte[] bArrHexToByteArray3 = hexToByteArray(strArrParsingParam[2]);
                                                                        if (bArrHexToByteArray3 == null) {
                                                                            str8 = str5 + AT_RESPONSE_NO_DATA;
                                                                        } else if (checkMsgIntegrity(bArrHexToByteArray3)) {
                                                                            Slog.i(TAG, "bArray size : " + bArrHexToByteArray3.length);
                                                                            makeDirectory(AT_HDCP_FILE_PATH_CPK);
                                                                            if (sendTobsd(AT_HDCP_DP_VER_22_WRITE_M_CMD + strArrParsingParam[2]) == 0) {
                                                                                str8 = str5 + "OK";
                                                                            } else {
                                                                                str8 = str5 + "NG";
                                                                            }
                                                                        } else {
                                                                            Slog.i(TAG, "Failed to check integtiry -size:" + bArrHexToByteArray3.length);
                                                                            str8 = str5 + AT_RESPONSE_INTEGRITY_FAIL;
                                                                        }
                                                                        str19 = str8;
                                                                        Slog.i(TAG, "3,8,Data is complete.");
                                                                    } else {
                                                                        String str37 = strArr[12];
                                                                        if (str37.equals(str19.substring(0, str37.length()))) {
                                                                            Slog.i(TAG, "AT+HDCPTEST=3,9,Data");
                                                                            if (!checkPath("/efs", 1)) {
                                                                                Slog.i(TAG, "efs partition is not mounted");
                                                                                return AT_RESPONSE_NO_EFS_PARTITION;
                                                                            }
                                                                            byte[] bArrHexToByteArray4 = hexToByteArray(strArrParsingParam[2]);
                                                                            if (bArrHexToByteArray4 == null) {
                                                                                str7 = str5 + AT_RESPONSE_NO_DATA;
                                                                            } else if (checkMsgIntegrity(bArrHexToByteArray4)) {
                                                                                Slog.i(TAG, "bArray size : " + bArrHexToByteArray4.length);
                                                                                makeDirectory(AT_HDCP_FILE_PATH_CPK);
                                                                                if (sendTobsd(AT_HDCP_DP_VER_13_WRITE_M_CMD + strArrParsingParam[2]) == 0) {
                                                                                    str7 = str5 + "OK";
                                                                                } else {
                                                                                    str7 = str5 + "NG";
                                                                                }
                                                                            } else {
                                                                                Slog.i(TAG, "Failed to check integtiry -size:" + bArrHexToByteArray4.length);
                                                                                str7 = str5 + AT_RESPONSE_INTEGRITY_FAIL;
                                                                            }
                                                                            str19 = str7;
                                                                            Slog.i(TAG, "3,9,Data is complete.");
                                                                        } else {
                                                                            str19 = str5 + AT_RESPONSE_INVALID_PARAM;
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                if (fileInputStream3 != null) {
                                                    return str19;
                                                }
                                                try {
                                                    fileInputStream3.close();
                                                    return str19;
                                                } catch (Exception e7) {
                                                    e = e7;
                                                    str4 = str19 + AT_RESPONSE_EXCEPTION;
                                                    e.printStackTrace();
                                                    return str4;
                                                }
                                            }
                                            Slog.i(TAG, "AT+HDCPTEST=0,9");
                                            if (sendTobsd(AT_HDCP_DP_VER_13_VERIFY_M_CMD) == 0) {
                                                str13 = str5 + "OK";
                                            } else {
                                                int iSendTobsd7 = sendTobsd(AT_HDCP_DP_VER_13_INSTALL_M_CMD);
                                                if (iSendTobsd7 == 0) {
                                                    str13 = str5 + "OK";
                                                } else if (iSendTobsd7 == 44) {
                                                    str13 = str5 + AT_RESPONSE_ERROR_EXEC;
                                                } else {
                                                    str13 = str5 + "NG";
                                                }
                                            }
                                            str19 = str13;
                                            Slog.i(TAG, "0,9 is complete.");
                                        }
                                    }
                                }
                            }
                        }
                        if (fileInputStream3 != null) {
                        }
                    } catch (Exception e8) {
                        e = e8;
                    }
                } catch (Exception e9) {
                    e = e9;
                    str3 = str19;
                }
            } catch (Exception e10) {
                e = e10;
                str2 = AT_RESPONSE_EXCEPTION;
                str3 = str5;
                String str2822 = str3 + str2;
                e.printStackTrace();
                if (fileInputStream4 != null) {
                }
                return str2822;
            }
        } catch (Throwable th3) {
            th = th3;
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

    public static byte[] hexToByteArray(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        int length = str.length() / 2;
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            bArr[i] = (byte) Integer.parseInt(str.substring(i2, i2 + 2), 16);
        }
        return bArr;
    }

    public static int execCmd(String str, String str2) {
        if (!checkPath(str, 2)) {
            return 44;
        }
        if (str2 != null) {
            str = (str + AT_COMMON_INTERVAL) + str2;
        }
        Process processExec = null;
        try {
            try {
                processExec = Runtime.getRuntime().exec(str);
                processExec.waitFor();
                int iExitValue = processExec.exitValue();
                if (processExec != null) {
                    processExec.destroy();
                }
                return iExitValue;
            } catch (Exception e) {
                e.printStackTrace();
                if (processExec == null) {
                    return 44;
                }
                processExec.destroy();
                return 44;
            }
        } catch (Throwable th) {
            if (processExec != null) {
                processExec.destroy();
            }
            throw th;
        }
    }

    public static boolean checkPath(String str, int i) {
        File file = new File(str);
        if (i == 1) {
            return file.isDirectory();
        }
        if (i != 2) {
            return false;
        }
        return file.isFile();
    }

    public static String getHdcp2XPath() {
        if (checkPath(AT_HDCP_FILE_PATH_CPK, 1)) {
            Slog.i(TAG, "Get path : cpk");
            return AT_HDCP_FILE_PATH_CPK;
        }
        Slog.i(TAG, "Get path : legacy");
        return "/efs";
    }

    public static boolean checkMsgIntegrity(byte[] bArr) throws NoSuchAlgorithmException {
        byte[] bArr2 = new byte[32];
        int length = bArr.length - 32;
        byte[] bArr3 = new byte[length];
        try {
            System.arraycopy(bArr, 0, bArr3, 0, length);
            System.arraycopy(bArr, length, bArr2, 0, 32);
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(bArr3);
            if (Arrays.equals(bArr2, messageDigest.digest())) {
                Slog.i(TAG, "Integrity Check : Pass");
                return true;
            }
            Slog.i(TAG, "Integrity Check : Failure");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void makeDirectory(String str) {
        File file = new File(AT_HDCP_FILE_PATH_CPK);
        if (checkPath(AT_HDCP_FILE_PATH_CPK, 1)) {
            return;
        }
        Slog.i(TAG, "Make cpkPath");
        if (file.mkdirs()) {
            return;
        }
        Slog.e(TAG, "Make cpkPath Failse");
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:18:0x003d -> B:83:0x008a). Please report as a decompilation issue!!! */
    public static int writeFile(byte[] bArr, String str, int i) throws Throwable {
        FileOutputStream fileOutputStream;
        BufferedOutputStream bufferedOutputStream;
        int i2 = 1;
        BufferedOutputStream bufferedOutputStream2 = null;
        try {
            try {
                try {
                    fileOutputStream = new FileOutputStream(str);
                    try {
                        bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                    } catch (SyncFailedException e) {
                        e = e;
                    } catch (Exception e2) {
                        e = e2;
                    }
                } catch (Throwable th) {
                    th = th;
                }
            } catch (SyncFailedException e3) {
                e = e3;
                fileOutputStream = null;
            } catch (Exception e4) {
                e = e4;
                fileOutputStream = null;
            } catch (Throwable th2) {
                th = th2;
                fileOutputStream = null;
            }
        } catch (Exception e5) {
            e5.printStackTrace();
        }
        try {
            Slog.i(TAG, "Prepare buffer stream");
            bufferedOutputStream2 = null;
            bufferedOutputStream.write(bArr, 0, i);
            Slog.i(TAG, "Write data into buffer");
            bufferedOutputStream.flush();
            fileOutputStream.getFD().sync();
            i2 = 1 ^ (checkPath(str, 2) ? 1 : 0);
            try {
                bufferedOutputStream.close();
            } catch (Exception e6) {
                e6.printStackTrace();
            }
            fileOutputStream.close();
        } catch (SyncFailedException e7) {
            e = e7;
            bufferedOutputStream2 = bufferedOutputStream;
            Slog.e(TAG, "SyncFailedException occurs");
            e.printStackTrace();
            if (bufferedOutputStream2 != null) {
                try {
                    bufferedOutputStream2.close();
                } catch (Exception e8) {
                    e8.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            return i2;
        } catch (Exception e9) {
            e = e9;
            bufferedOutputStream2 = bufferedOutputStream;
            e.printStackTrace();
            if (bufferedOutputStream2 != null) {
                try {
                    bufferedOutputStream2.close();
                } catch (Exception e10) {
                    e10.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            return i2;
        } catch (Throwable th3) {
            th = th3;
            bufferedOutputStream2 = bufferedOutputStream;
            if (bufferedOutputStream2 != null) {
                try {
                    bufferedOutputStream2.close();
                } catch (Exception e11) {
                    e11.printStackTrace();
                }
            }
            if (fileOutputStream == null) {
                throw th;
            }
            try {
                fileOutputStream.close();
                throw th;
            } catch (Exception e12) {
                e12.printStackTrace();
                throw th;
            }
        }
        return i2;
    }
}

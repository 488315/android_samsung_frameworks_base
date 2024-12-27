package com.android.server;

import android.content.Context;
import android.util.Slog;

import java.io.File;
import java.security.MessageDigest;
import java.util.Arrays;

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

    /* JADX WARN: Removed duplicated region for block: B:298:0x0b9c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:304:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:313:0x0bbd A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:320:? A[SYNTHETIC] */
    @Override // com.android.server.IWorkOnAt
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String processCmd(java.lang.String r26) {
        /*
            Method dump skipped, instructions count: 3034
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.HdcptestATCmd.processCmd(java.lang.String):java.lang.String");
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

    public static byte[] hexToByteArray(String hexData) {
        if (hexData == null || hexData.length() == 0) {
            return null;
        }
        byte[] bArray = new byte[hexData.length() / 2];
        for (int i = 0; i < bArray.length; i++) {
            bArray[i] = (byte) Integer.parseInt(hexData.substring(i * 2, (i * 2) + 2), 16);
        }
        return bArray;
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0051, code lost:

       return r0;
    */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x004e, code lost:

       if (r1 == null) goto L18;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int execCmd(java.lang.String r3, java.lang.String r4) {
        /*
            r0 = 44
            r1 = 2
            boolean r1 = checkPath(r3, r1)
            if (r1 != 0) goto La
            return r0
        La:
            if (r4 == 0) goto L30
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.String r2 = " "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r3 = r1.toString()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.StringBuilder r1 = r1.append(r4)
            java.lang.String r3 = r1.toString()
        L30:
            r1 = 0
            java.lang.Runtime r2 = java.lang.Runtime.getRuntime()     // Catch: java.lang.Throwable -> L48 java.lang.Exception -> L4a
            java.lang.Process r2 = r2.exec(r3)     // Catch: java.lang.Throwable -> L48 java.lang.Exception -> L4a
            r1 = r2
            r1.waitFor()     // Catch: java.lang.Throwable -> L48 java.lang.Exception -> L4a
            int r2 = r1.exitValue()     // Catch: java.lang.Throwable -> L48 java.lang.Exception -> L4a
            r0 = r2
            if (r1 == 0) goto L51
        L44:
            r1.destroy()
            goto L51
        L48:
            r2 = move-exception
            goto L52
        L4a:
            r2 = move-exception
            r2.printStackTrace()     // Catch: java.lang.Throwable -> L48
            if (r1 == 0) goto L51
            goto L44
        L51:
            return r0
        L52:
            if (r1 == 0) goto L57
            r1.destroy()
        L57:
            throw r2
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: com.android.server.HdcptestATCmd.execCmd(java.lang.String,"
                    + " java.lang.String):int");
    }

    public static boolean checkPath(String filePath, int type) {
        File tmpFile = new File(filePath);
        switch (type) {
            case 1:
                boolean result = tmpFile.isDirectory();
                return result;
            case 2:
                boolean result2 = tmpFile.isFile();
                return result2;
            default:
                return false;
        }
    }

    public static String getHdcp2XPath() {
        if (checkPath(AT_HDCP_FILE_PATH_CPK, 1)) {
            Slog.i(TAG, "Get path : cpk");
            return AT_HDCP_FILE_PATH_CPK;
        }
        Slog.i(TAG, "Get path : legacy");
        return "/efs";
    }

    public static boolean checkMsgIntegrity(byte[] Array) {
        boolean ret = false;
        byte[] bMD1 = new byte[32];
        byte[] bArr = new byte[32];
        byte[] bMsg = new byte[Array.length - bMD1.length];
        try {
            System.arraycopy(Array, 0, bMsg, 0, bMsg.length);
            System.arraycopy(Array, bMsg.length, bMD1, 0, bMD1.length);
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(bMsg);
            byte[] bMD2 = md.digest();
            if (Arrays.equals(bMD1, bMD2)) {
                ret = true;
                Slog.i(TAG, "Integrity Check : Pass");
            } else {
                Slog.i(TAG, "Integrity Check : Failure");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static void makeDirectory(String dir) {
        File cpkPath = new File(AT_HDCP_FILE_PATH_CPK);
        if (!checkPath(AT_HDCP_FILE_PATH_CPK, 1)) {
            Slog.i(TAG, "Make cpkPath");
            if (!cpkPath.mkdirs()) {
                Slog.e(TAG, "Make cpkPath Failse");
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x00bb A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x009a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:69:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int writeFile(byte[] r5, java.lang.String r6, int r7) {
        /*
            Method dump skipped, instructions count: 202
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: com.android.server.HdcptestATCmd.writeFile(byte[],"
                    + " java.lang.String, int):int");
    }
}

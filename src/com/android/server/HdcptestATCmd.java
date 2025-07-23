package com.android.server;

import android.content.Context;
import android.util.Slog;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.SyncFailedException;
import java.security.MessageDigest;
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

    /* JADX WARN: Removed duplicated region for block: B:173:0x0905 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x08e1 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x08ca A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // com.android.server.IWorkOnAt
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String processCmd(java.lang.String r32) {
        /*
            Method dump skipped, instructions count: 2346
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.HdcptestATCmd.processCmd(java.lang.String):java.lang.String");
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
        Process process = null;
        try {
            try {
                process = Runtime.getRuntime().exec(str);
                process.waitFor();
                int exitValue = process.exitValue();
                if (process != null) {
                    process.destroy();
                }
                return exitValue;
            } catch (Exception e) {
                e.printStackTrace();
                if (process == null) {
                    return 44;
                }
                process.destroy();
                return 44;
            }
        } catch (Throwable th) {
            if (process != null) {
                process.destroy();
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

    public static boolean checkMsgIntegrity(byte[] bArr) {
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

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:94:0x003d -> B:15:0x008a). Please report as a decompilation issue!!! */
    public static int writeFile(byte[] bArr, String str, int i) {
        FileOutputStream fileOutputStream;
        int i2 = 1;
        BufferedOutputStream bufferedOutputStream = null;
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            try {
                fileOutputStream = new FileOutputStream(str);
                try {
                    BufferedOutputStream bufferedOutputStream2 = new BufferedOutputStream(fileOutputStream);
                    try {
                        Slog.i(TAG, "Prepare buffer stream");
                        bufferedOutputStream = null;
                        bufferedOutputStream2.write(bArr, 0, i);
                        Slog.i(TAG, "Write data into buffer");
                        bufferedOutputStream2.flush();
                        fileOutputStream.getFD().sync();
                        i2 = 1 ^ (checkPath(str, 2) ? 1 : 0);
                        try {
                            bufferedOutputStream2.close();
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                        fileOutputStream.close();
                    } catch (SyncFailedException e3) {
                        e = e3;
                        bufferedOutputStream = bufferedOutputStream2;
                        Slog.e(TAG, "SyncFailedException occurs");
                        e.printStackTrace();
                        if (bufferedOutputStream != null) {
                            try {
                                bufferedOutputStream.close();
                            } catch (Exception e4) {
                                e4.printStackTrace();
                            }
                        }
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                        return i2;
                    } catch (Exception e5) {
                        e = e5;
                        bufferedOutputStream = bufferedOutputStream2;
                        e.printStackTrace();
                        if (bufferedOutputStream != null) {
                            try {
                                bufferedOutputStream.close();
                            } catch (Exception e6) {
                                e6.printStackTrace();
                            }
                        }
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                        return i2;
                    } catch (Throwable th) {
                        th = th;
                        bufferedOutputStream = bufferedOutputStream2;
                        if (bufferedOutputStream != null) {
                            try {
                                bufferedOutputStream.close();
                            } catch (Exception e7) {
                                e7.printStackTrace();
                            }
                        }
                        if (fileOutputStream == null) {
                            throw th;
                        }
                        try {
                            fileOutputStream.close();
                            throw th;
                        } catch (Exception e8) {
                            e8.printStackTrace();
                            throw th;
                        }
                    }
                } catch (SyncFailedException e9) {
                    e = e9;
                } catch (Exception e10) {
                    e = e10;
                }
            } catch (SyncFailedException e11) {
                e = e11;
                fileOutputStream = null;
            } catch (Exception e12) {
                e = e12;
                fileOutputStream = null;
            } catch (Throwable th2) {
                th = th2;
                fileOutputStream = null;
            }
            return i2;
        } catch (Throwable th3) {
            th = th3;
        }
    }
}

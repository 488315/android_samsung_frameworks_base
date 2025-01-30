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
import java.util.Arrays;

/* loaded from: classes5.dex */
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

  /* JADX WARN: Removed duplicated region for block: B:163:0x0a20 A[EXC_TOP_SPLITTER, SYNTHETIC] */
  /* JADX WARN: Removed duplicated region for block: B:169:? A[RETURN, SYNTHETIC] */
  /* JADX WARN: Removed duplicated region for block: B:174:0x0a41 A[EXC_TOP_SPLITTER, SYNTHETIC] */
  /* JADX WARN: Removed duplicated region for block: B:181:? A[SYNTHETIC] */
  @Override // com.android.server.IWorkOnAt
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public String processCmd(String cmd) {
    Throwable th;
    Exception e;
    Exception e2;
    StringBuilder append;
    String result;
    FileInputStream hIStream;
    String str = AT_RESPONSE_EXCEPTION;
    String result2 = "";
    String[] params = parsingParam(cmd);
    String[] supportedParams = {
      "0,0", "0,3", "0,4", "0,5", "0,8", "0,9", "1,0", "2,", "3,3,", "3,4,", "3,5,", "3,8,", "3,9,"
    };
    FileInputStream hIStream2 = null;
    if (params == null) {
      Slog.m117i(TAG, "processCmd: params is null");
      return AT_RESPONSE_INVALID_PARAM;
    }
    if (!checkPath("/efs", 1)) {
      Slog.m117i(TAG, "efs partition is not mounted");
      return AT_RESPONSE_NO_EFS_PARTITION;
    }
    try {
      if (!this.mRunningBSD) {
        try {
          try {
            Slog.m117i(TAG, "Start BSD service!");
            SystemProperties.set("ctl.start", "bsd");
            this.mRunningBSD = true;
          } catch (Exception e3) {
            e = e3;
            result2 = result2 + str;
            e.printStackTrace();
            if (hIStream2 != null) {
              return result2;
            }
            try {
              hIStream2.close();
              return result2;
            } catch (Exception e4) {
              e2 = e4;
              append = new StringBuilder().append(result2);
              String result3 = append.append(str).toString();
              e2.printStackTrace();
              return result3;
            }
          }
        } catch (Throwable th2) {
          th = th2;
          if (hIStream2 != null) {
            throw th;
          }
          try {
            hIStream2.close();
            throw th;
          } catch (Exception e5) {
            String str2 = result2 + str;
            e5.printStackTrace();
            throw th;
          }
        }
      }
      result2 = params[0] + ",";
      FileInputStream hIStream3 = null;
      try {
        if (supportedParams[0].equals(cmd.substring(0, supportedParams[0].length()))) {
          Slog.m117i(TAG, "AT+HDCPTEST=0,0");
          int ret = sendTobsd(AT_HDCP_VERIFY_CMD);
          result =
              ret == 0
                  ? result2 + "OK"
                  : !checkPath(
                          new StringBuilder()
                              .append(AT_HDCP_FILE_PATH_CPK)
                              .append(AT_HDCP_KEY_20)
                              .toString(),
                          2)
                      ? !checkPath(
                              new StringBuilder().append("/efs").append(AT_HDCP_KEY_20).toString(),
                              2)
                          ? result2 + AT_RESPONSE_NG_KEY
                          : ret == 44
                              ? result2 + AT_RESPONSE_ERROR_EXEC
                              : result2 + AT_RESPONSE_NG_FIELD
                      : ret == 44
                          ? result2 + AT_RESPONSE_ERROR_EXEC
                          : result2 + AT_RESPONSE_NG_FIELD;
          Slog.m117i(TAG, "0,0 is complete!");
          hIStream = null;
        } else {
          try {
            if (supportedParams[1].equals(cmd.substring(0, supportedParams[1].length()))) {
              Slog.m117i(TAG, "AT+HDCPTEST=0,3");
              int ret2 = 44;
              if ("jdm".equals(productType)) {
                Slog.m117i(TAG, "0,3 test for jdm");
                ret2 = sendTobsd(AT_WV_VERIFY_CMD_JDM);
              } else if (SystemProperties.getInt("ro.product.first_api_level", 0) >= 29) {
                ret2 = sendTobsd(AT_WV_VERIFY_CMD);
              } else if (checkPath(AT_WV_KEY, 2)) {
                ret2 = sendTobsd(AT_WV_VERIFY_CMD);
              } else {
                result2 = result2 + AT_RESPONSE_NG_KEY;
              }
              result =
                  ret2 == 0
                      ? result2 + "OK"
                      : ret2 == 44
                          ? result2 + AT_RESPONSE_ERROR_EXEC
                          : result2 + AT_RESPONSE_NG_FIELD;
              Slog.m117i(TAG, "0,3 is complete.");
              hIStream = null;
            } else if (supportedParams[2].equals(cmd.substring(0, supportedParams[2].length()))) {
              Slog.m117i(TAG, "AT+HDCPTEST=0,4");
              if (sendTobsd(AT_HDCP_DP_VER_22_VERIFY_CMD) == 0) {
                result = result2 + "OK";
              } else {
                int ret3 = sendTobsd(AT_HDCP_DP_VER_22_INSTALL_CMD);
                result =
                    ret3 == 0
                        ? result2 + "OK"
                        : ret3 == 44 ? result2 + AT_RESPONSE_ERROR_EXEC : result2 + "NG";
              }
              Slog.m117i(TAG, "0,4 is complete.");
              hIStream = null;
            } else if (supportedParams[3].equals(cmd.substring(0, supportedParams[3].length()))) {
              Slog.m117i(TAG, "AT+HDCPTEST=0,5");
              if (sendTobsd(AT_HDCP_DP_VER_13_VERIFY_CMD) == 0) {
                result = result2 + "OK";
              } else {
                int ret4 = sendTobsd(AT_HDCP_DP_VER_13_INSTALL_CMD);
                result =
                    ret4 == 0
                        ? result2 + "OK"
                        : ret4 == 44 ? result2 + AT_RESPONSE_ERROR_EXEC : result2 + "NG";
              }
              Slog.m117i(TAG, "0,5 is complete.");
              hIStream = null;
            } else if (supportedParams[4].equals(cmd.substring(0, supportedParams[4].length()))) {
              Slog.m117i(TAG, "AT+HDCPTEST=0,8");
              if (sendTobsd(AT_HDCP_DP_VER_22_VERIFY_M_CMD) == 0) {
                result = result2 + "OK";
              } else {
                int ret5 = sendTobsd(AT_HDCP_DP_VER_22_INSTALL_M_CMD);
                result =
                    ret5 == 0
                        ? result2 + "OK"
                        : ret5 == 44 ? result2 + AT_RESPONSE_ERROR_EXEC : result2 + "NG";
              }
              Slog.m117i(TAG, "0,8 is complete.");
              hIStream = null;
            } else if (supportedParams[5].equals(cmd.substring(0, supportedParams[5].length()))) {
              Slog.m117i(TAG, "AT+HDCPTEST=0,9");
              if (sendTobsd(AT_HDCP_DP_VER_13_VERIFY_M_CMD) == 0) {
                result = result2 + "OK";
              } else {
                int ret6 = sendTobsd(AT_HDCP_DP_VER_13_INSTALL_M_CMD);
                result =
                    ret6 == 0
                        ? result2 + "OK"
                        : ret6 == 44 ? result2 + AT_RESPONSE_ERROR_EXEC : result2 + "NG";
              }
              Slog.m117i(TAG, "0,9 is complete.");
              hIStream = null;
            } else if (supportedParams[6].equals(cmd.substring(0, supportedParams[6].length()))) {
              Slog.m117i(TAG, "AT+HDCPTEST=1,0");
              String serialNo = null;
              String serialPath = null;
              if (checkPath(AT_SERIAL_PATH3, 2)) {
                serialPath = AT_SERIAL_PATH3;
              } else if (checkPath(AT_SERIAL_PATH, 2)) {
                serialPath = AT_SERIAL_PATH;
              } else if (checkPath(AT_SERIAL_PATH2, 2)) {
                serialPath = AT_SERIAL_PATH2;
              }
              if (serialPath != null) {
                byte[] bArray = new byte[32];
                FileInputStream hIStream4 = new FileInputStream(serialPath);
                try {
                  if (hIStream4.read(bArray) != -1) {
                    if (serialPath != AT_SERIAL_PATH && serialPath != AT_SERIAL_PATH3) {
                      if (serialPath == AT_SERIAL_PATH2) {
                        serialNo = new String(bArray).trim().substring(16, 32);
                        hIStream3 = hIStream4;
                      }
                    }
                    serialNo = new String(bArray).trim();
                    hIStream3 = hIStream4;
                  } else {
                    Slog.m115e(TAG, "Read S/N Failed");
                  }
                  serialNo = null;
                  hIStream3 = hIStream4;
                } catch (Exception e6) {
                  e = e6;
                  hIStream2 = hIStream4;
                  str = AT_RESPONSE_EXCEPTION;
                  result2 = result2 + str;
                  e.printStackTrace();
                  if (hIStream2 != null) {}
                } catch (Throwable th3) {
                  th = th3;
                  hIStream2 = hIStream4;
                  str = AT_RESPONSE_EXCEPTION;
                  if (hIStream2 != null) {}
                }
              }
              if (serialNo == null) {
                serialNo = AT_WV_DEFAULT_SERIAL;
              }
              String AT_RESPONSE_SERIAL =
                  serialNo.length() >= 16
                      ? serialNo
                      : serialNo.length() <= 0
                          ? "0" + AT_WV_DEFAULT_SERIAL.substring(1)
                          : serialNo + AT_WV_DEFAULT_SERIAL.substring(serialNo.length());
              Slog.m117i(TAG, "Serial Number : " + AT_RESPONSE_SERIAL);
              result = result2 + AT_RESPONSE_SERIAL;
              Slog.m117i(TAG, "1,0 is complete.");
              hIStream = hIStream3;
            } else if (supportedParams[7].equals(cmd.substring(0, supportedParams[7].length()))) {
              if (params[1] != null && params[1].length() != 0) {
                Slog.m117i(TAG, "Param size : " + params[1].length());
                makeDirectory(AT_HDCP_FILE_PATH_CPK);
                result =
                    sendTobsd(
                                new StringBuilder()
                                    .append(AT_HDCP_WRITE_CMD)
                                    .append(params[1])
                                    .toString())
                            == 0
                        ? result2 + "OK"
                        : result2 + "NG";
                Slog.m117i(TAG, "2,Data is complete.");
                hIStream = null;
              }
              result = result2 + AT_RESPONSE_NO_DATA;
              Slog.m117i(TAG, "2,Data is complete.");
              hIStream = null;
            } else if (supportedParams[8].equals(cmd.substring(0, supportedParams[8].length()))) {
              Slog.m117i(TAG, "AT+HDCPTEST=3,3,Data");
              if (params[2] != null && params[2].length() != 0) {
                Slog.m117i(TAG, "Param size : " + params[2].length());
                int ret7 = sendTobsd(AT_WV_INSTALL_CMD + params[2]);
                result =
                    ret7 == 0
                        ? result2 + "OK"
                        : ret7 == 44 ? result2 + AT_RESPONSE_ERROR_EXEC : result2 + "NG";
                Slog.m117i(TAG, "3,3,Data is complete.");
                hIStream = null;
              }
              result = result2 + AT_RESPONSE_NO_DATA;
              Slog.m117i(TAG, "3,3,Data is complete.");
              hIStream = null;
            } else if (supportedParams[9].equals(cmd.substring(0, supportedParams[9].length()))) {
              Slog.m117i(TAG, "AT+HDCPTEST=3,4,Data");
              byte[] bArray2 = hexToByteArray(params[2]);
              if (bArray2 == null) {
                result = result2 + AT_RESPONSE_NO_DATA;
              } else if (checkMsgIntegrity(bArray2)) {
                Slog.m117i(TAG, "bArray size : " + bArray2.length);
                makeDirectory(AT_HDCP_FILE_PATH_CPK);
                result =
                    sendTobsd(
                                new StringBuilder()
                                    .append(AT_HDCP_DP_VER_22_WRITE_CMD)
                                    .append(params[2])
                                    .toString())
                            == 0
                        ? result2 + "OK"
                        : result2 + "NG";
              } else {
                Slog.m117i(TAG, "Failed to check integtiry -size:" + bArray2.length);
                result = result2 + AT_RESPONSE_INTEGRITY_FAIL;
              }
              Slog.m117i(TAG, "3,4,Data is complete.");
              hIStream = null;
            } else if (supportedParams[10].equals(cmd.substring(0, supportedParams[10].length()))) {
              Slog.m117i(TAG, "AT+HDCPTEST=3,5,Data");
              byte[] bArray3 = hexToByteArray(params[2]);
              if (bArray3 == null) {
                result = result2 + AT_RESPONSE_NO_DATA;
              } else if (checkMsgIntegrity(bArray3)) {
                Slog.m117i(TAG, "bArray size : " + bArray3.length);
                makeDirectory(AT_HDCP_FILE_PATH_CPK);
                result =
                    sendTobsd(
                                new StringBuilder()
                                    .append(AT_HDCP_DP_VER_13_WRITE_CMD)
                                    .append(params[2])
                                    .toString())
                            == 0
                        ? result2 + "OK"
                        : result2 + "NG";
              } else {
                Slog.m117i(TAG, "Failed to check integtiry -size:" + bArray3.length);
                result = result2 + AT_RESPONSE_INTEGRITY_FAIL;
              }
              Slog.m117i(TAG, "3,5,Data is complete.");
              hIStream = null;
            } else if (supportedParams[11].equals(cmd.substring(0, supportedParams[11].length()))) {
              Slog.m117i(TAG, "AT+HDCPTEST=3,8,Data");
              byte[] bArray4 = hexToByteArray(params[2]);
              if (bArray4 == null) {
                result = result2 + AT_RESPONSE_NO_DATA;
              } else if (checkMsgIntegrity(bArray4)) {
                Slog.m117i(TAG, "bArray size : " + bArray4.length);
                makeDirectory(AT_HDCP_FILE_PATH_CPK);
                result =
                    sendTobsd(
                                new StringBuilder()
                                    .append(AT_HDCP_DP_VER_22_WRITE_M_CMD)
                                    .append(params[2])
                                    .toString())
                            == 0
                        ? result2 + "OK"
                        : result2 + "NG";
              } else {
                Slog.m117i(TAG, "Failed to check integtiry -size:" + bArray4.length);
                result = result2 + AT_RESPONSE_INTEGRITY_FAIL;
              }
              Slog.m117i(TAG, "3,8,Data is complete.");
              hIStream = null;
            } else if (supportedParams[12].equals(cmd.substring(0, supportedParams[12].length()))) {
              Slog.m117i(TAG, "AT+HDCPTEST=3,9,Data");
              byte[] bArray5 = hexToByteArray(params[2]);
              if (bArray5 == null) {
                result = result2 + AT_RESPONSE_NO_DATA;
              } else if (checkMsgIntegrity(bArray5)) {
                Slog.m117i(TAG, "bArray size : " + bArray5.length);
                makeDirectory(AT_HDCP_FILE_PATH_CPK);
                result =
                    sendTobsd(
                                new StringBuilder()
                                    .append(AT_HDCP_DP_VER_13_WRITE_M_CMD)
                                    .append(params[2])
                                    .toString())
                            == 0
                        ? result2 + "OK"
                        : result2 + "NG";
              } else {
                Slog.m117i(TAG, "Failed to check integtiry -size:" + bArray5.length);
                result = result2 + AT_RESPONSE_INTEGRITY_FAIL;
              }
              Slog.m117i(TAG, "3,9,Data is complete.");
              hIStream = null;
            } else {
              result = result2 + AT_RESPONSE_INVALID_PARAM;
              hIStream = null;
            }
          } catch (Exception e7) {
            str = AT_RESPONSE_EXCEPTION;
            e = e7;
            hIStream2 = null;
          } catch (Throwable th4) {
            str = AT_RESPONSE_EXCEPTION;
            th = th4;
            hIStream2 = null;
          }
        }
        if (hIStream == null) {
          return result;
        }
        try {
          hIStream.close();
          return result;
        } catch (Exception e8) {
          e2 = e8;
          append = new StringBuilder().append(result);
          str = AT_RESPONSE_EXCEPTION;
          String result32 = append.append(str).toString();
          e2.printStackTrace();
          return result32;
        }
      } catch (Exception e9) {
        e = e9;
        hIStream2 = null;
        str = AT_RESPONSE_EXCEPTION;
      } catch (Throwable th5) {
        th = th5;
        hIStream2 = null;
        str = AT_RESPONSE_EXCEPTION;
      }
    } catch (Exception e10) {
      e = e10;
    } catch (Throwable th6) {
      th = th6;
    }
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

  public static int execCmd(String cmd, String param) {
    if (!checkPath(cmd, 2)) {
      return 44;
    }
    if (param != null) {
      cmd = (cmd + AT_COMMON_INTERVAL) + param;
    }
    try {
      Runtime rt = Runtime.getRuntime();
      Process pc = rt.exec(cmd);
      pc.waitFor();
      int ret = pc.exitValue();
      return ret;
    } catch (Exception e) {
      e.printStackTrace();
      return 44;
    }
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
      Slog.m117i(TAG, "Get path : cpk");
      return AT_HDCP_FILE_PATH_CPK;
    }
    Slog.m117i(TAG, "Get path : legacy");
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
        Slog.m117i(TAG, "Integrity Check : Pass");
      } else {
        Slog.m117i(TAG, "Integrity Check : Failure");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return ret;
  }

  public static void makeDirectory(String dir) {
    File cpkPath = new File(AT_HDCP_FILE_PATH_CPK);
    if (!checkPath(AT_HDCP_FILE_PATH_CPK, 1)) {
      Slog.m117i(TAG, "Make cpkPath");
      if (!cpkPath.mkdirs()) {
        Slog.m115e(TAG, "Make cpkPath Failse");
      }
    }
  }

  /* JADX WARN: Removed duplicated region for block: B:36:0x00bb A[EXC_TOP_SPLITTER, SYNTHETIC] */
  /* JADX WARN: Removed duplicated region for block: B:60:0x009a A[EXC_TOP_SPLITTER, SYNTHETIC] */
  /* JADX WARN: Removed duplicated region for block: B:69:? A[RETURN, SYNTHETIC] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public static int writeFile(byte[] bArray, String filePath, int length) {
    int ret;
    FileOutputStream hOStream = null;
    BufferedOutputStream hBOStream = null;
    try {
      try {
        hOStream = new FileOutputStream(filePath);
        hBOStream = new BufferedOutputStream(hOStream);
        Slog.m117i(TAG, "Prepare buffer stream");
        hBOStream.write(bArray, 0, length);
        Slog.m117i(TAG, "Write data into buffer");
        hBOStream.flush();
        hOStream.getFD().sync();
        ret = checkPath(filePath, 2) ? 0 : 1;
        try {
          try {
            hBOStream.close();
          } catch (Exception e) {
            e.printStackTrace();
          }
          try {
            try {
              hOStream.close();
            } finally {
            }
          } catch (Exception e2) {
            e2.printStackTrace();
          }
        } finally {
        }
      } catch (Throwable th) {
        if (hBOStream != null) {
          try {
            try {
              hBOStream.close();
            } catch (Exception e3) {
              e3.printStackTrace();
              if (hOStream != null) {
                try {
                  hOStream.close();
                } catch (Exception e4) {
                  e4.printStackTrace();
                }
              }
              throw th;
            }
          } finally {
          }
        }
        try {
          if (hOStream != null) {}
          throw th;
        } finally {
        }
      }
    } catch (SyncFailedException e5) {
      Slog.m115e(TAG, "SyncFailedException occurs");
      ret = 1;
      e5.printStackTrace();
      if (hBOStream != null) {
        try {
          try {
            hBOStream.close();
          } catch (Exception e6) {
            e6.printStackTrace();
            if (hOStream != null) {
              return 1;
            }
            try {
              try {
                hOStream.close();
              } catch (Exception e7) {
                e7.printStackTrace();
              }
              return ret;
            } finally {
            }
          }
        } finally {
        }
      }
      if (hOStream != null) {}
    } catch (Exception e8) {
      e8.printStackTrace();
      try {
        if (hBOStream != null) {
          try {
            hBOStream.close();
          } catch (Exception e9) {
            e9.printStackTrace();
          }
        }
        if (hOStream == null) {
          return 1;
        }
        try {
          try {
            hOStream.close();
          } catch (Exception e10) {
            e10.printStackTrace();
            return 1;
          }
          return 1;
        } finally {
        }
      } finally {
      }
    }
    return ret;
  }
}

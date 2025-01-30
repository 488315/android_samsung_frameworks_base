package com.android.server;

import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.service.persistentdata.PersistentDataBlockManager;
import java.nio.charset.StandardCharsets;

/* loaded from: classes5.dex */
public class AuthUnlockATCmd implements IWorkOnAt {
  private static final String AT_COMMAND_FRPUNLCK = "FRPUNLCK";
  private static final String AT_COMMAND_HEADER = "AT";
  private static final String AT_RESPONSE_CONN_FAILED = "NG (FAILED CONNECTION)";
  private static final String AT_RESPONSE_END = "\r\n\r\nOK\r\n";
  private static final String AT_RESPONSE_INVALID_PARAM = "NG (INVALID_PARAM)";
  private static final String AT_RESPONSE_NA = "NA";
  private static final String AT_RESPONSE_START = "\r\n";
  private static final int ERR_SERVICE_INTERNAL = 1;
  private static final int ERR_SERVICE_NOT_SUPPORTED = 0;
  private static final String TAG = "AuthUnlockATCmd";
  private static final Object mLock = new Object();
  private Context mContext;
  private PersistentDataBlockManager mPDB;
  private int mServiceSupport;

  private native byte[] nativeSessionAccept(byte[] bArr);

  private native int nativeSessionComplete(byte[] bArr);

  static {
    System.loadLibrary("frpunlock");
  }

  public AuthUnlockATCmd(Context context) {
    this.mContext = context;
  }

  @Override // com.android.server.IWorkOnAt
  public String getCmd() {
    return AT_COMMAND_FRPUNLCK;
  }

  @Override // com.android.server.IWorkOnAt
  public String processCmd(String cmd) {
    int ret;
    String result;
    byte[] response;
    String result2 = "";
    String[] params = parsingParam(cmd);
    String[] supportedParams = {"1,0,", "1,1,"};
    if (params == null) {
      return AT_RESPONSE_INVALID_PARAM;
    }
    PersistentDataBlockManager persistentDataBlockManager =
        (PersistentDataBlockManager)
            this.mContext.getSystemService(Context.PERSISTENT_DATA_BLOCK_SERVICE);
    this.mPDB = persistentDataBlockManager;
    if (persistentDataBlockManager == null) {
      return AT_RESPONSE_CONN_FAILED;
    }
    try {
      result2 = params[0] + ",";
      if (supportedParams[0].equals(cmd.substring(0, supportedParams[0].length()))) {
        byte[] data = params[2].trim().getBytes(StandardCharsets.UTF_8);
        synchronized (mLock) {
          try {
            response = nativeSessionAccept(data);
          } catch (Exception e) {
            response = null;
          }
        }
        if (response != null) {
          return result2 + new String(response, StandardCharsets.UTF_8);
        }
        return result2 + "NG(1)";
      }
      if (supportedParams[1].equals(cmd.substring(0, supportedParams[1].length()))) {
        byte[] data2 = params[2].trim().getBytes(StandardCharsets.UTF_8);
        synchronized (mLock) {
          try {
            ret = nativeSessionComplete(data2);
          } catch (Exception e2) {
            ret = 1;
          }
        }
        if (ret == 0) {
          try {
            this.mPDB.wipe();
            result = result2 + "UNLOCK SUCCESS";
          } catch (Exception e3) {
            result = result2 + "NG(1)";
            e3.printStackTrace();
          }
          return result;
        }
        return result2 + "NG(" + ret + NavigationBarInflaterView.KEY_CODE_END;
      }
      return result2 + AT_RESPONSE_INVALID_PARAM;
    } catch (Exception e4) {
      String result3 = result2 + AT_RESPONSE_INVALID_PARAM;
      e4.printStackTrace();
      return result3;
    }
    String result32 = result2 + AT_RESPONSE_INVALID_PARAM;
    e4.printStackTrace();
    return result32;
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
}

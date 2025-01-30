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
  private static final int AT_MAIN_INDEX_WRITE_DATA = 2;
  private static final int AT_MAIN_OPERATION = 0;
  private static final int AT_MAIN_READ_DATA = 10;
  private static final int AT_MAIN_WRITE_DATA = 20;
  private static final int AT_MID_INDEX = 1;
  private static final int AT_MINOR_INDEX = 2;
  private static final String AT_RESPONSE_FAILED = "NG";
  private static final String AT_RESPONSE_INVALID_PARAM = "NG(INVALID_PARAM)";
  private static final String AT_RESPONSE_NONE = "NONE";
  private static final String AT_RESPONSE_OK = "OK";
  private static final int NOT_PROVISIONED = 10000;
  private static final int NO_ERROR = 0;
  private static final int SAMSUNG_HERMES_GET_SECUREHW_INFO = 10;
  private static final int SAMSUNG_HERMES_PROVISIONING = 1;
  private static final int SAMSUNG_HERMES_SELFTEST = 0;
  private static final int SAMSUNG_HERMES_UPDATE_CRYPTO_FW = 2;
  private static final int SAMSUNG_HERMES_VERIFY_PROVISONING = 11;
  private static final String TAG = "HERMES#ATCmd";
  private Context mContext;
  private HermesServiceManager mHermesServiceManager;

  private HermesServiceManager bindHermesServiceManager() {
    if (this.mHermesServiceManager == null) {
      Log.m98i(TAG, "bindHermesServiceManager() is called.");
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
      Log.m96e(TAG, "ISOSECHW processCmd wrong param.");
      return AT_RESPONSE_INVALID_PARAM;
    }
    String result = new String(params[0] + ",");
    try {
      Log.m98i(TAG, "ISOSECHW ProcessCmd [" + cmd + "] start");
      switch (Integer.parseInt(params[0] + params[1])) {
        case 0:
          byte[] stinfo = bindHermesServiceManager().hermesSelftest();
          if (stinfo == null) {
            result = result + "OK";
            break;
          } else {
            result = result + "NG_" + new String(stinfo);
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
          byte[] resultinfo = bindHermesServiceManager().hermesUpdateCryptoFW();
          if (resultinfo != null) {
            result = result + new String(resultinfo);
            break;
          } else {
            result = result + "NG";
            break;
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
        default:
          Log.m96e(TAG, "ISOSECHW ProcessCmd wrong command.");
          result = result + AT_RESPONSE_INVALID_PARAM;
          break;
      }
      Log.m98i(TAG, "ISOSECHW ProcessCmd [" + cmd + "] end");
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
}

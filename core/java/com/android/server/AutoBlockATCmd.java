package com.android.server;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Slog;
import com.samsung.android.service.EngineeringMode.EngineeringModeManager;

/* loaded from: classes5.dex */
public class AutoBlockATCmd implements IWorkOnAt {
  private static final String ACTION_MODE_RESET_AUTOBLOCKER =
      "com.samsung.android.intent.action.MODE_RESET_AUTOBLOCKER";
  private static final String AT_COMMAND_BLOCKER = "ABSTACHK";
  private static final String AT_COMMAND_HEADER = "AT";
  private static final String AT_RESPONSE_ATBLOCK_OFF = "OFF";
  private static final String AT_RESPONSE_ATBLOCK_ON = "ON";
  private static final String AT_RESPONSE_EXCEPTION = "NG (EXCEPTION)";
  private static final String AT_RESPONSE_INVALID_PARAM = "NG (INVALID_PARAM)";
  private static final String AT_RESPONSE_NG_FAIL = "NG,NOK";
  private static final String AT_RESPONSE_NG_NOTOKEN = "NG,NO_TOK";
  private static final String AT_RESPONSE_OK = "OK";
  private static final int EM_IDX = 61;
  private static final String PERMISSION_ACCESS_AUTOBLOCKER =
      "com.samsung.android.permission.ACCESS_AUTOBLOCKER";
  private static final String RAMPART = "com.samsung.android.rampart";
  private static final String TAG = "AutoBlockATCmd";
  private static Context mContext;
  private EngineeringModeManager mEMMgr;

  public AutoBlockATCmd(Context context) {
    mContext = context;
    this.mEMMgr = new EngineeringModeManager(context.getApplicationContext());
  }

  @Override // com.android.server.IWorkOnAt
  public String getCmd() {
    return AT_COMMAND_BLOCKER;
  }

  @Override // com.android.server.IWorkOnAt
  public String processCmd(String cmd) {
    String result;
    String result2;
    String[] params = parsingParam(cmd);
    String[] supportedParams = {"0,0", "1,0"};
    if (params == null) {
      Slog.m117i(TAG, "processCmd: params is null");
      return AT_RESPONSE_INVALID_PARAM;
    }
    try {
      String result3 = params[0] + ",";
      if (supportedParams[0].equals(cmd.substring(0, supportedParams[0].length()))) {
        Slog.m117i(TAG, "AT+ABSTACHK=0,0");
        EngineeringModeManager engineeringModeManager = this.mEMMgr;
        if (engineeringModeManager != null && engineeringModeManager.isConnected()) {
          int emStat = this.mEMMgr.getStatus(61);
          if (emStat == 1) {
            Intent rampartResetIntent = new Intent(ACTION_MODE_RESET_AUTOBLOCKER);
            rampartResetIntent.setPackage(RAMPART);
            rampartResetIntent.addFlags(32);
            mContext.sendBroadcast(
                rampartResetIntent, "com.samsung.android.permission.ACCESS_AUTOBLOCKER");
            setSUWSwitchOff();
            result2 = result3 + "OK";
          } else {
            result2 = result3 + AT_RESPONSE_NG_NOTOKEN;
          }
          Slog.m117i(TAG, "AT+ABSTACHK=0,0 is complete.");
          return result2;
        }
        Slog.m117i(TAG, "Cannot connect to em service");
        return AT_RESPONSE_NG_FAIL;
      }
      if (!supportedParams[1].equals(cmd.substring(0, supportedParams[1].length()))) {
        return result3 + AT_RESPONSE_INVALID_PARAM;
      }
      Slog.m117i(TAG, "AT+ABSTACHK=1,0");
      if (isRampartBlockedAdbCommand()) {
        result = result3 + AT_RESPONSE_ATBLOCK_ON;
      } else {
        result = result3 + "OFF";
      }
      Slog.m117i(TAG, "AT+ABSTACHK=1,0 is complete.");
      return result;
    } catch (Exception e) {
      String result4 = "" + AT_RESPONSE_EXCEPTION;
      e.printStackTrace();
      return result4;
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

  private boolean isRampartBlockedAdbCommand() {
    return Settings.Secure.getInt(
            mContext.getContentResolver(), Settings.Secure.RAMPART_MAIN_SWITCH_ENABLED, 0)
        == 1;
  }

  private void setSUWSwitchOff() {
    Settings.System.putInt(mContext.getContentResolver(), Settings.System.RAMPART_SUW_MAIN_ON, 0);
  }
}

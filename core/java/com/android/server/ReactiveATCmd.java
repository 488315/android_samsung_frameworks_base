package com.android.server;

import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.provider.Settings;
import android.util.Slog;
import com.samsung.android.service.reactive.ReactiveServiceManager;
import java.nio.charset.StandardCharsets;

/* loaded from: classes5.dex */
public class ReactiveATCmd implements IWorkOnAt {
  private static final String AT_COMMAND_HEADER = "AT";
  private static final String AT_COMMAND_REACTIVE = "REACTIVE";
  private static final String AT_RESPONSE_CONN_FAILED = "NG (FAILED CONNECTION)";
  private static final String AT_RESPONSE_END = "\r\n\r\nOK\r\n";
  private static final String AT_RESPONSE_INVALID_PARAM = "NG (INVALID_PARAM)";
  private static final String AT_RESPONSE_NA = "NA";
  private static final String AT_RESPONSE_START = "\r\n";
  private static final String AT_RESPONSE_STATUS_LOCK = "LOCK";
  private static final String AT_RESPONSE_STATUS_LOCK_1 = "LOCK_1";
  private static final String AT_RESPONSE_STATUS_LOCK_2 = "LOCK_2";
  private static final String AT_RESPONSE_STATUS_LOCK_3 = "LOCK_3";
  private static final String AT_RESPONSE_STATUS_LOCK_4 = "LOCK_4";
  private static final String AT_RESPONSE_STATUS_LOCK_5 = "LOCK_5";
  private static final String AT_RESPONSE_STATUS_TRIGGERED = "TRIGGERED";
  private static final String AT_RESPONSE_STATUS_UNLOCK = "UNLOCK";
  private static final int LOCK_STATE_1 = 3;
  private static final int LOCK_STATE_2 = 4;
  private static final int LOCK_STATE_3 = 5;
  private static final int LOCK_STATE_4 = 6;
  private static final int LOCK_STATE_5 = 7;
  private static final int SERVICE_GOOGLE_NWD_SUPPORTED = 4;
  private static final int SERVICE_IS_NOT_SUPPORTED = 0;
  private static final int SERVICE_SAMSUNG_NWD_SUPPORTED = 2;
  private static final int SERVICE_SAMSUNG_SWD_SUPPORTED = 1;
  private static final String TAG = "ReactiveATCmd";
  private Context mContext;
  private ReactiveServiceManager mRSM;

  public ReactiveATCmd(Context context) {
    this.mContext = context;
    this.mRSM = new ReactiveServiceManager(context.getApplicationContext());
  }

  @Override // com.android.server.IWorkOnAt
  public String getCmd() {
    return AT_COMMAND_REACTIVE;
  }

  @Override // com.android.server.IWorkOnAt
  public String processCmd(String cmd) {
    String result;
    String result2;
    String result3;
    int flag;
    String result4;
    String[] params = parsingParam(cmd);
    String[] supportedParams = {"1,0,0", "2,0,", "2,1,", "2,2,"};
    if (params == null) {
      return AT_RESPONSE_INVALID_PARAM;
    }
    ReactiveServiceManager reactiveServiceManager = this.mRSM;
    if (reactiveServiceManager == null || !reactiveServiceManager.isConnected()) {
      return AT_RESPONSE_CONN_FAILED;
    }
    try {
      String result5 = params[0] + ",";
      if (supportedParams[0].equals(cmd.substring(0, supportedParams[0].length()))) {
        int appliedSolution = this.mRSM.getRawServiceValueForAtCommand();
        switch (appliedSolution) {
          case 4:
            flag = this.mRSM.getFlag(2);
            break;
          case 5:
            int frp_flag = this.mRSM.getFlag(2);
            int rl_flag = this.mRSM.getFlag(0);
            if (rl_flag == 2) {
              flag = rl_flag;
              break;
            } else if (frp_flag == 2) {
              flag = frp_flag;
              break;
            } else if (frp_flag == 0 && rl_flag == 0) {
              flag = 0;
              break;
            } else if (frp_flag == 1 && rl_flag == 0) {
              flag = 3;
              break;
            } else if (frp_flag == 0 && rl_flag == 1) {
              flag = 4;
              break;
            } else if (frp_flag == 1 && rl_flag == 1) {
              flag = 6;
              break;
            } else {
              flag = rl_flag;
              break;
            }
          case 6:
            int frp_flag2 = this.mRSM.getFlag(2);
            int rl_flag2 = this.mRSM.getFlag(0);
            if (rl_flag2 == 2) {
              flag = rl_flag2;
              break;
            } else if (frp_flag2 == 2) {
              flag = frp_flag2;
              break;
            } else if (frp_flag2 == 0 && rl_flag2 == 0) {
              flag = 0;
              break;
            } else if (frp_flag2 == 1 && rl_flag2 == 0) {
              flag = 3;
              break;
            } else if (frp_flag2 == 0 && rl_flag2 == 1) {
              flag = 5;
              break;
            } else if (frp_flag2 == 1 && rl_flag2 == 1) {
              flag = 7;
              break;
            } else {
              flag = rl_flag2;
              break;
            }
          default:
            flag = this.mRSM.getFlag(0);
            break;
        }
        switch (flag) {
          case 0:
            result4 = result5 + AT_RESPONSE_STATUS_UNLOCK;
            break;
          case 1:
            result4 = result5 + AT_RESPONSE_STATUS_LOCK;
            break;
          case 2:
            result4 = result5 + AT_RESPONSE_STATUS_TRIGGERED;
            break;
          case 3:
            result4 = result5 + AT_RESPONSE_STATUS_LOCK_1;
            break;
          case 4:
            result4 = result5 + AT_RESPONSE_STATUS_LOCK_2;
            break;
          case 5:
            result4 = result5 + AT_RESPONSE_STATUS_LOCK_3;
            break;
          case 6:
            result4 = result5 + AT_RESPONSE_STATUS_LOCK_4;
            break;
          case 7:
            result4 = result5 + AT_RESPONSE_STATUS_LOCK_5;
            break;
          default:
            result4 = result5 + "NG(" + flag + NavigationBarInflaterView.KEY_CODE_END;
            break;
        }
        return result4;
      }
      if (supportedParams[1].equals(cmd.substring(0, supportedParams[1].length()))) {
        byte[] data = params[2].trim().getBytes(StandardCharsets.UTF_8);
        byte[] response = this.mRSM.sessionAccept(data);
        if (response != null) {
          result3 = result5 + new String(response, StandardCharsets.UTF_8);
        } else {
          result3 =
              result5 + "NG(" + this.mRSM.getErrorCode() + NavigationBarInflaterView.KEY_CODE_END;
        }
        return result3;
      }
      if (supportedParams[2].equals(cmd.substring(0, supportedParams[2].length()))) {
        byte[] data2 = params[2].trim().getBytes(StandardCharsets.UTF_8);
        int ret = this.mRSM.sessionComplete(data2);
        if (ret == 0) {
          result2 = result5 + SecureKeyConst.AT_RESPONSE_OK;
        } else {
          result2 = result5 + "NG(" + ret + NavigationBarInflaterView.KEY_CODE_END;
        }
        return result2;
      }
      if (!supportedParams[3].equals(cmd.substring(0, supportedParams[3].length()))) {
        return result5 + AT_RESPONSE_INVALID_PARAM;
      }
      byte[] data3 = params[2].trim().getBytes(StandardCharsets.UTF_8);
      int ret2 = this.mRSM.sessionComplete(data3);
      if (ret2 == 0) {
        Settings.Secure.putInt(this.mContext.getContentResolver(), "secure_frp_mode", 0);
        Slog.m117i(TAG, "Secure FRP mode is disabled");
        result = result5 + SecureKeyConst.AT_RESPONSE_OK;
      } else {
        result = result5 + "NG(" + ret2 + NavigationBarInflaterView.KEY_CODE_END;
      }
      return result;
    } catch (Exception e) {
      String result6 = "" + AT_RESPONSE_INVALID_PARAM;
      e.printStackTrace();
      return result6;
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
}

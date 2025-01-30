package com.samsung.android.knox.dar.ddar.fsm;

import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.samsung.android.knox.dar.ddar.DualDarConstants;
import com.samsung.android.knox.dar.ddar.proxy.IProxyService;
import com.samsung.android.knox.dar.ddar.proxy.KnoxProxyManager;

/* loaded from: classes5.dex */
public class StateMachine {
  private static final boolean DEBUG = false;
  private static final String GET_CURRENT_STATE = "GET_CURRENT_STATE";
  private static final String GET_PREVIOUS_STATE = "GET_PREVIOUS_STATE";
  private static final String KEY_DUAL_DAR_USER_ID = "KEY_DUAL_DAR_USER_ID";
  private static final String KEY_EVENT = "KEY_EVENT";
  private static final String KEY_STATE = "KEY_STATE";
  private static final String PROCESS_EVENT = "PROCESS_EVENT";
  private static final String SET_INITIAL_STATE = "SET_INITIAL_STATE";
  private static final String STATE_MACHINE_SERVICE = "STATE_MACHINE_SERVICE";
  private static final String SYSTEM_PROXY_AGENT = "SYSTEM_PROXY_AGENT";
  private static final String TAG = "DDAR::StateMachine";
  private static IProxyService _instance = null;

  public static void setInitialState() throws Exception {
    Log.m94d(TAG, "Set initial state DualDAR");
    Bundle response = sendCommand(SET_INITIAL_STATE, null);
    if (response == null || !response.getBoolean(DualDarConstants.DUAL_DAR_RESPONSE)) {
      throw new Exception("DualDAR initiate State failed!!");
    }
  }

  public static boolean processEvent(int dualDarUserId, Event event) {
    if (event == null) {
      return false;
    }
    Bundle in = new Bundle();
    in.putInt(KEY_DUAL_DAR_USER_ID, dualDarUserId);
    in.putString(KEY_EVENT, event.name());
    Bundle response = sendCommand(PROCESS_EVENT, in);
    if (response == null) {
      return false;
    }
    return response.getBoolean(DualDarConstants.DUAL_DAR_RESPONSE);
  }

  public static State getPreviousState(int dualDarUserId) {
    Bundle in = new Bundle();
    in.putInt(KEY_DUAL_DAR_USER_ID, dualDarUserId);
    Bundle response = sendCommand(GET_PREVIOUS_STATE, in);
    if (response == null) {
      return null;
    }
    return State.valueOf(response.getString(KEY_STATE));
  }

  public static State getCurrentState(int dualDarUserId) {
    Bundle in = new Bundle();
    in.putInt(KEY_DUAL_DAR_USER_ID, dualDarUserId);
    Bundle response = sendCommand(GET_CURRENT_STATE, in);
    if (response == null) {
      return null;
    }
    return State.valueOf(response.getString(KEY_STATE));
  }

  private static Bundle sendCommand(String command, Bundle params) {
    try {
      IProxyService service = getService();
      if (service == null) {
        Log.m96e(TAG, "sendCommand() : Error: Service Not found, command = " + command);
        return null;
      }
      return service.relay("SYSTEM_PROXY_AGENT", STATE_MACHINE_SERVICE, command, params);
    } catch (RemoteException re) {
      re.printStackTrace();
      return null;
    }
  }

  private static IProxyService getService() {
    if (_instance == null) {
      _instance =
          IProxyService.Stub.asInterface(ServiceManager.getService(KnoxProxyManager.PROXY_SERVICE));
    }
    return _instance;
  }
}

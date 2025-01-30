package com.samsung.android.knox;

import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;

/* loaded from: classes5.dex */
public class ContainerProxy {
  public static final String CATEGORY_CORE = "core";
  public static final String COMMAND_ADD_APP_SHORTCUT_TO_PERSONAL =
      "knox.container.proxy.COMMAND_ADD_APP_SHORTCUT_TO_PERSONAL";
  static final String COMMAND_BASE = "knox.container.proxy.COMMAND_";
  public static final String COMMAND_FOCUSED_USER_CHANGED =
      "knox.container.proxy.COMMAND_FOCUSED_USER_CHANGED";
  public static final String COMMAND_LEGACY_ENFORCE_PASSWORD =
      "knox.container.proxy.COMMAND_ENFORCE_PASSWORD";
  public static final String COMMAND_LEGACY_RESET_PASSWORD =
      "knox.container.proxy.COMMAND_RESET_PASSWORD";
  public static final String COMMAND_LOCK_PROFILE = "knox.container.proxy.COMMAND_LOCK_PROFILE";
  public static final String COMMAND_MANUAL_LOCK = "knox.container.proxy.COMMAND_MANUAL_LOCK";
  public static final String COMMAND_START_LOCK_SCREEN =
      "knox.container.proxy.COMMAND_START_LOCK_SCREEN";
  public static final String COMMAND_SWITCH_PROFILE = "knox.container.proxy.COMMAND_SWITCH_PROFILE";
  public static final String COMMAND_UPDATE_SWITCHER_NOTIFICATION =
      "knox.container.proxy.COMMAND_UPDATE_SWITCHER_NOTIFICATION";
  static final String EVENT_BASE = "knox.container.proxy.EVENT_";
  public static final String EVENT_DEVICE_OVER_HEAT = "knox.container.proxy.EVENT_DEVICE_OVER_HEAT";
  public static final String EVENT_DUAL_DAR_TRIAL_LICENSE_EXPIRED =
      "knox.container.proxy.EVENT_DUAL_DAR_TRIAL_LICENSE_EXPIRED";
  public static final String EVENT_FINGERPRINT_CHANGE =
      "knox.container.proxy.EVENT_FINGERPRINT_CHANGE";
  public static final String EVENT_HOME_SHOWN = "knox.container.proxy.EVENT_HOME_SHOWN";
  public static final String EVENT_LOCK_TIMEOUT = "knox.container.proxy.EVENT_LOCK_TIMEOUT";
  public static final String EVENT_NOTIFY_ACTIVITY_DRAWN =
      "knox.container.proxy.EVENT_ACTIVITY_DRAWN";
  public static final String EVENT_ULTRA_POWER_SAVING_MODE_CHANGE =
      "knox.container.proxy.EVENT_ULTRA_POWER_SAVING_MODE_CHANGE";
  private static final String EXTRA_BASE = "knox.container.proxy.EXTRA_";
  public static final String EXTRA_CALLING_PID = "knox.container.proxy.EXTRA_CALLING_PID";
  public static final String EXTRA_CALLING_UID = "knox.container.proxy.EXTRA_CALLING_UID";
  public static final String EXTRA_COMPONENT_NAME = "knox.container.proxy.EXTRA_COMPONENT_NAME";
  public static final String EXTRA_CONTAINER_OWNER = "knox.container.proxy.EXTRA_CONTAINER_OWNER";
  public static final String EXTRA_FINGERPRINT_ID = "knox.container.proxy.EXTRA_FINGERPRINT_ID";
  public static final String EXTRA_FLAGS = "knox.container.proxy.EXTRA_FLAGS";
  public static final String EXTRA_FLAG_DISABLE_IMMEDIATELY_LOCK =
      "knox.container.proxy.EXTRA_FLAG_DISABLE_IMMEDIATELY_LOCK";
  public static final String EXTRA_FLAG_IS_CUSTOM_CONTAINER =
      "knox.container.proxy.EXTRA_FLAG_IS_CUSTOM_CONTAINER";
  public static final String EXTRA_FLAG_NOTIFICATION_CLICKED =
      "knox.container.proxy.EXTRA_FLAG_NOTIFICATION_CLICKED";
  public static final String EXTRA_FROM_HOME_KEY = "knox.container.proxy.EXTRA_FROM_HOME_KEY";
  public static final String EXTRA_HOME_SCREEN_WALLPAPER =
      "knox.container.proxy.EXTRA_KNOX_HOME_SCREEN_WALLPAPER";
  public static final String EXTRA_INTENT = "android.intent.extra.INTENT";
  public static final String EXTRA_KEY = "knox.container.proxy.EXTRA_KEY";
  public static final String EXTRA_KNOX_LICENSE_RESULT_CODE =
      "knox.container.proxy.EXTRA_KNOX_LICENSE_RESULT_CODE";
  public static final String EXTRA_KNOX_LICENSE_RESULT_TYPE =
      "knox.container.proxy.EXTRA_KNOX_LICENSE_RESULT_TYPE";
  public static final String EXTRA_MULTIWINDOWRECORD =
      "knox.container.proxy.EXTRA_MULTIWINDOWRECORD";
  public static final String EXTRA_PACKAGE_NAME = "knox.container.proxy.EXTRA_PACKAGE_NAME";
  public static final String EXTRA_RESET_PWD_TOKEN = "knox.container.proxy.EXTRA_RESET_PWD_TOKEN";
  public static final String EXTRA_RESET_PWD_TOKEN_HANDLE =
      "knox.container.proxy.EXTRA_RESET_PWD_TOKEN_HANDLE";
  public static final String EXTRA_RESOLVE_INFO = "knox.container.proxy.EXTRA_RESOLVE_INFO";
  public static final String EXTRA_RETURN_RESULT = "android.intent.extra.RETURN_RESULT";
  public static final String EXTRA_SHOW_WHEN_LOCKED = "knox.container.proxy.EXTRA_SHOW_WHEN_LOCKED";
  public static final String EXTRA_TASK_ID = "knox.container.proxy.EXTRA_TASK_ID";
  public static final String EXTRA_USERID = "android.intent.extra.USER_ID";
  public static final String EXTRA_USER_HANDLE = "android.intent.extra.user_handle";
  public static final String EXTRA_USER_INFO = "knox.container.proxy.EXTRA_USER_INFO";
  public static final String EXTRA_VALUE = "knox.container.proxy.EXTRA_VALUE";
  public static final String POLICY_ADD_USER_RESTRICTION =
      "knox.container.proxy.POLICY_ADD_USER_RESTRICTION";
  public static final String POLICY_ADMIN_LOCK = "knox.container.proxy.POLICY_ADMIN_LOCK";
  public static final String POLICY_ADMIN_UNLOCK = "knox.container.proxy.POLICY_ADMIN_UNLOCK";
  static final String POLICY_BASE = "knox.container.proxy.POLICY_";
  public static final String POLICY_CLEAR_USER_RESTRICTION =
      "knox.container.proxy.POLICY_CLEAR_USER_RESTRICTION";
  public static final String POLICY_DEVICE_COMPROMISE =
      "knox.container.proxy.POLICY_DEVICE_COMPROMISE";
  public static final String POLICY_KNOX_LICENSE_STATE_CHANGE =
      "knox.container.proxy.POLICY_KNOX_LICENSE_STATE_CHANGE";
  public static final String POLICY_PASSWORD_CHANGE_ENFORCED =
      "knox.container.proxy.POLICY_PASSWORD_CHANGE_ENFORCED";
  public static final String POLICY_REVERT_CONTAINER_POLICY =
      "knox.container.proxy.POLICY_REVERT_CONTAINER_POLICY";
  public static final String POLICY_SDCARD_POLICY_CHANGED =
      "knox.container.proxy.POLICY_SDCARD_POLICY_CHANGED";
  public static final int RESULT_FAILED = 1;
  public static final int RESULT_FAILED_NO_PROFILE = 3;
  public static final int RESULT_FAILED_NO_SEPARATE_LOCK = 4;
  public static final int RESULT_FAILED_OPNOTSUPP = 2;
  public static final int RESULT_SERVICE_NOT_FOUND = 99;
  public static final int RESULT_SUCCESS = 0;
  private static final String TAG = "KnoxService::ContainerProxy";
  public static final String TEST_BASE = "knox.container.proxy.TEST_";
  private static ISemPersonaManager _instance = null;

  public static void test() {
    Log.m100v(TAG, "test");
  }

  private static ISemPersonaManager getService() {
    if (_instance == null) {
      _instance = ISemPersonaManager.Stub.asInterface(ServiceManager.getService("persona"));
    }
    return _instance;
  }

  private static Bundle sendProxyMessage(String category, String name, Bundle args) {
    if (getService() != null) {
      try {
        return getService().sendProxyMessage(category, name, args);
      } catch (RemoteException re) {
        re.printStackTrace();
        return null;
      }
    }
    return null;
  }

  public static Bundle sendCommand(String name, Bundle args) {
    if (!name.startsWith(COMMAND_BASE)) {
      Log.m96e(TAG, "sendCommand() not a command type : " + name);
      return null;
    }
    return sendProxyMessage(CATEGORY_CORE, name, args);
  }

  public static Bundle sendPolicyUpdate(String name, Bundle args) {
    if (!name.startsWith(POLICY_BASE)) {
      Log.m96e(TAG, "sendPolicyUpdate() not a policy type : " + name);
      return null;
    }
    return sendProxyMessage(CATEGORY_CORE, name, args);
  }

  public static Bundle sendEvent(String name, Bundle args) {
    if (!name.startsWith(EVENT_BASE)) {
      Log.m96e(TAG, "sendEvent() not a event type : " + name);
      return null;
    }
    return sendProxyMessage(CATEGORY_CORE, name, args);
  }

  public static Bundle sendTestMessage(String name, Bundle args) {
    return sendProxyMessage(CATEGORY_CORE, name, args);
  }

  public static String getTestMessage(String testMessage) {
    return new String(TEST_BASE + testMessage);
  }
}

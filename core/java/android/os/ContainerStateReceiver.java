package android.os;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.UserInfo;
import android.util.Log;

/* loaded from: classes3.dex */
public class ContainerStateReceiver extends BroadcastReceiver {
  public static final int CONTAINER_EVENT_ADMIN_LOCKED = 16;
  public static final int CONTAINER_EVENT_ADMIN_UNLOCKED = 17;
  public static final int CONTAINER_EVENT_CREATED = 0;
  public static final int CONTAINER_EVENT_ENABLED = 18;
  public static final int CONTAINER_EVENT_LICENSE_ACTIVATED = 11;
  public static final int CONTAINER_EVENT_LICENSE_EXPIRED = 12;
  public static final int CONTAINER_EVENT_LOCKED = 4;
  public static final int CONTAINER_EVENT_LOCKSCREEN_STATE_CHANGED = 15;
  public static final int CONTAINER_EVENT_ONELOCK = 19;
  public static final int CONTAINER_EVENT_REMOVED = 10;
  public static final int CONTAINER_EVENT_RESET = 9;
  public static final int CONTAINER_EVENT_RUNNING = 1;
  public static final int CONTAINER_EVENT_SHUTDOWN = 2;
  public static final int CONTAINER_EVENT_SWITCH = 3;
  public static final int CONTAINER_EVENT_UNLOCKED = 5;
  public static final int DEVICE_EVENT_OWNER_ACTIVATED = 13;
  public static final int DEVICE_EVENT_OWNER_LICENSE_ACTIVATED = 14;
  public static final int DEVICE_EVENT_OWNER_LICENSE_EXPIRED = 20;
  private static String TAG = "ContainerStateReceiver";
  private static boolean DEBUG = false;
  public static String ACTION_CONTAINER_STATE_RECEIVER =
      "com.samsung.android.knox.ACTION_CONTAINER_STATE_RECEIVER";
  public static String EXTRA_CONTIANER_ID = "com.samsung.knox.EXTRA_CONTIANER_ID";
  public static String EXTRA_CONTIANER_EVENT_ID = "com.samsung.knox.EXTRA_CONTIANER_EVENT_ID";
  public static String EXTRA_LOCKSCREEN_VISIBLE = "com.samsung.knox.EXTRA_LOCKSCREEN_VISIBLE";
  public static String EXTRA_CONTIANER_CONFIGURATION_TYPE =
      "com.samsung.knox.EXTRA_CONTIANER_CONFIGURATION_TYPE";
  public static String EXTRA_USER_INFO = "com.samsung.knox.EXTRA_USER_INFO";

  public static String toEventString(int event) {
    switch (event) {
      case 0:
        return "CONTAINER_EVENT_CREATED";
      case 1:
        return "CONTAINER_EVENT_RUNNING";
      case 2:
        return "CONTAINER_EVENT_SHUTDOWN";
      case 3:
        return "CONTAINER_EVENT_SWITCH";
      case 4:
        return "CONTAINER_EVENT_LOCKED";
      case 5:
        return "CONTAINER_EVENT_UNLOCKED";
      case 6:
      case 7:
      case 8:
      default:
        return "CONTAINER_EVENT_UNKNOWN";
      case 9:
        return "CONTAINER_EVENT_RESET";
      case 10:
        return "CONTAINER_EVENT_REMOVED";
      case 11:
        return "CONTAINER_EVENT_LICENSE_ACTIVATED";
      case 12:
        return "CONTAINER_EVENT_LICENSE_EXPIRED";
      case 13:
        return "DEVICE_EVENT_OWNER_ACTIVATED";
      case 14:
        return "DEVICE_EVENT_OWNER_LICENSE_ACTIVATED";
      case 15:
        return "CONTAINER_EVENT_LOCKSCREEN_STATE_CHANGED";
      case 16:
        return "CONTAINER_EVENT_ADMIN_LOCKED";
      case 17:
        return "CONTAINER_EVENT_ADMIN_UNLOCKED";
      case 18:
        return "CONTAINER_EVENT_ENABLED";
      case 19:
        return "CONTAINER_EVENT_ONELOCK";
      case 20:
        return "DEVICE_EVENT_OWNER_LICENSE_EXPIRED";
    }
  }

  public static void register(Context c, ContainerStateReceiver receiver) {
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction(ACTION_CONTAINER_STATE_RECEIVER);
    c.registerReceiver(receiver, intentFilter);
  }

  public static void unregister(Context c, ContainerStateReceiver receiver) {
    c.unregisterReceiver(receiver);
  }

  public void onContainerCreated(Context context, int userHandle, Bundle b) {}

  public void onContainerRunning(Context context, int userHandle, Bundle b) {}

  public void onContainerShutdown(Context context, int userHandle, Bundle b) {}

  public void onContainerSwitch(Context context, int userHandle, Bundle b) {}

  public void onPersonalSwitch(Context context, Bundle b) {}

  public void onContainerLocked(Context context, int userHandle, Bundle b) {}

  public void onContainerUnlocked(Context context, int userHandle, Bundle b) {}

  public void onContainerRemoved(Context context, int userHandle, Bundle b) {}

  public void onLicenseActivated(Context context, int userHandle, Bundle b) {}

  public void onLicenseExpired(Context context, int userHandle, Bundle b) {}

  public void onContainerReset(Context context, int userHandle, Bundle b) {}

  public void onDeviceOwnerActivated(Context context, Bundle b) {}

  public void onDeviceOwnerLicenseActivated(Context context, Bundle b) {}

  public void onDeviceOwnerLicenseExpired(Context context, Bundle b) {}

  public void onLockScreenStateChanged(
      Context context, int userHandle, boolean visible, Bundle b) {}

  public void onContainerAdminLocked(Context context, int userHandle, Bundle b) {}

  public void onContainerAdminUnlocked(Context context, int userHandle, Bundle b) {}

  public void onContainerEnabled(Context context, int userHandle, Bundle b) {}

  public void onContainerOneLocked(Context context, int userHandle, Bundle b) {}

  @Override // android.content.BroadcastReceiver
  public void onReceive(Context context, Intent intent) {
    UserManager um = (UserManager) context.getSystemService("user");
    if (ACTION_CONTAINER_STATE_RECEIVER.equals(intent.getAction())) {
      Bundle b = intent.getExtras();
      int userHandle = intent.getIntExtra(EXTRA_CONTIANER_ID, -10000);
      int event = intent.getIntExtra(EXTRA_CONTIANER_EVENT_ID, -1);
      UserInfo ui = um.getUserInfo(userHandle);
      if (ui == null) {
        Log.m96e(TAG, "onReceive failed to get UserInfo from handle:" + userHandle);
      }
      Log.m98i(TAG, " event: " + toEventString(event));
      switch (event) {
        case 0:
          onContainerCreated(context, userHandle, b);
          break;
        case 1:
          onContainerRunning(context, userHandle, b);
          break;
        case 2:
          onContainerShutdown(context, userHandle, b);
          break;
        case 3:
          if (userHandle == 0) {
            onPersonalSwitch(context, b);
            break;
          } else if (ui.isManagedProfile()) {
            onContainerSwitch(context, userHandle, b);
            break;
          }
          break;
        case 4:
          onContainerLocked(context, userHandle, b);
          break;
        case 5:
          onContainerUnlocked(context, userHandle, b);
          break;
        case 6:
        case 7:
        case 8:
        default:
          Log.m96e(TAG, "invalid event:" + event);
          break;
        case 9:
          onContainerReset(context, userHandle, b);
          break;
        case 10:
          onContainerRemoved(context, userHandle, b);
          break;
        case 11:
          onLicenseActivated(context, userHandle, b);
          break;
        case 12:
          onLicenseExpired(context, userHandle, b);
          break;
        case 13:
          onDeviceOwnerActivated(context, b);
          break;
        case 14:
          onDeviceOwnerLicenseActivated(context, b);
          break;
        case 15:
          onLockScreenStateChanged(
              context, userHandle, intent.getBooleanExtra(EXTRA_LOCKSCREEN_VISIBLE, false), b);
          break;
        case 16:
          onContainerAdminLocked(context, userHandle, b);
          break;
        case 17:
          onContainerAdminUnlocked(context, userHandle, b);
          break;
        case 18:
          onContainerEnabled(context, userHandle, b);
          break;
        case 19:
          onContainerOneLocked(context, userHandle, b);
          break;
        case 20:
          onDeviceOwnerLicenseExpired(context, b);
          break;
      }
    }
  }
}

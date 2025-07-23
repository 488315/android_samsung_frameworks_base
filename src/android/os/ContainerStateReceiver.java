package android.os;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.UserInfo;
import android.util.Log;

/* loaded from: classes3.dex */
public class ContainerStateReceiver extends BroadcastReceiver {
    public static String ACTION_CONTAINER_STATE_RECEIVER = "com.samsung.android.knox.ACTION_CONTAINER_STATE_RECEIVER";
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
    private static boolean DEBUG = false;
    public static final int DEVICE_EVENT_OWNER_ACTIVATED = 13;
    public static final int DEVICE_EVENT_OWNER_LICENSE_ACTIVATED = 14;
    public static final int DEVICE_EVENT_OWNER_LICENSE_EXPIRED = 20;
    public static String EXTRA_CONTIANER_CONFIGURATION_TYPE = "com.samsung.knox.EXTRA_CONTIANER_CONFIGURATION_TYPE";
    public static String EXTRA_CONTIANER_EVENT_ID = "com.samsung.knox.EXTRA_CONTIANER_EVENT_ID";
    public static String EXTRA_CONTIANER_ID = "com.samsung.knox.EXTRA_CONTIANER_ID";
    public static String EXTRA_LOCKSCREEN_VISIBLE = "com.samsung.knox.EXTRA_LOCKSCREEN_VISIBLE";
    public static String EXTRA_USER_INFO = "com.samsung.knox.EXTRA_USER_INFO";
    private static String TAG = "ContainerStateReceiver";

    public void onContainerAdminLocked(Context context, int i, Bundle bundle) {
    }

    public void onContainerAdminUnlocked(Context context, int i, Bundle bundle) {
    }

    public void onContainerCreated(Context context, int i, Bundle bundle) {
    }

    public void onContainerEnabled(Context context, int i, Bundle bundle) {
    }

    public void onContainerLocked(Context context, int i, Bundle bundle) {
    }

    public void onContainerOneLocked(Context context, int i, Bundle bundle) {
    }

    public void onContainerRemoved(Context context, int i, Bundle bundle) {
    }

    public void onContainerReset(Context context, int i, Bundle bundle) {
    }

    public void onContainerRunning(Context context, int i, Bundle bundle) {
    }

    public void onContainerShutdown(Context context, int i, Bundle bundle) {
    }

    public void onContainerSwitch(Context context, int i, Bundle bundle) {
    }

    public void onContainerUnlocked(Context context, int i, Bundle bundle) {
    }

    public void onDeviceOwnerActivated(Context context, Bundle bundle) {
    }

    public void onDeviceOwnerLicenseActivated(Context context, Bundle bundle) {
    }

    public void onDeviceOwnerLicenseExpired(Context context, Bundle bundle) {
    }

    public void onLicenseActivated(Context context, int i, Bundle bundle) {
    }

    public void onLicenseExpired(Context context, int i, Bundle bundle) {
    }

    public void onLockScreenStateChanged(Context context, int i, boolean z, Bundle bundle) {
    }

    public void onPersonalSwitch(Context context, Bundle bundle) {
    }

    public static String toEventString(int i) {
        switch (i) {
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

    public static void register(Context context, ContainerStateReceiver containerStateReceiver) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_CONTAINER_STATE_RECEIVER);
        context.registerReceiver(containerStateReceiver, intentFilter);
    }

    public static void unregister(Context context, ContainerStateReceiver containerStateReceiver) {
        context.unregisterReceiver(containerStateReceiver);
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        UserManager userManager = (UserManager) context.getSystemService("user");
        if (ACTION_CONTAINER_STATE_RECEIVER.equals(intent.getAction())) {
            Bundle extras = intent.getExtras();
            int intExtra = intent.getIntExtra(EXTRA_CONTIANER_ID, -10000);
            int intExtra2 = intent.getIntExtra(EXTRA_CONTIANER_EVENT_ID, -1);
            UserInfo userInfo = userManager.getUserInfo(intExtra);
            if (userInfo == null) {
                Log.e(TAG, "onReceive failed to get UserInfo from handle:" + intExtra);
            }
            Log.i(TAG, " event: " + toEventString(intExtra2));
            switch (intExtra2) {
                case 0:
                    onContainerCreated(context, intExtra, extras);
                    break;
                case 1:
                    onContainerRunning(context, intExtra, extras);
                    break;
                case 2:
                    onContainerShutdown(context, intExtra, extras);
                    break;
                case 3:
                    if (intExtra == 0) {
                        onPersonalSwitch(context, extras);
                        break;
                    } else if (userInfo.isManagedProfile()) {
                        onContainerSwitch(context, intExtra, extras);
                        break;
                    }
                    break;
                case 4:
                    onContainerLocked(context, intExtra, extras);
                    break;
                case 5:
                    onContainerUnlocked(context, intExtra, extras);
                    break;
                case 6:
                case 7:
                case 8:
                default:
                    Log.e(TAG, "invalid event:" + intExtra2);
                    break;
                case 9:
                    onContainerReset(context, intExtra, extras);
                    break;
                case 10:
                    onContainerRemoved(context, intExtra, extras);
                    break;
                case 11:
                    onLicenseActivated(context, intExtra, extras);
                    break;
                case 12:
                    onLicenseExpired(context, intExtra, extras);
                    break;
                case 13:
                    onDeviceOwnerActivated(context, extras);
                    break;
                case 14:
                    onDeviceOwnerLicenseActivated(context, extras);
                    break;
                case 15:
                    onLockScreenStateChanged(context, intExtra, intent.getBooleanExtra(EXTRA_LOCKSCREEN_VISIBLE, false), extras);
                    break;
                case 16:
                    onContainerAdminLocked(context, intExtra, extras);
                    break;
                case 17:
                    onContainerAdminUnlocked(context, intExtra, extras);
                    break;
                case 18:
                    onContainerEnabled(context, intExtra, extras);
                    break;
                case 19:
                    onContainerOneLocked(context, intExtra, extras);
                    break;
                case 20:
                    onDeviceOwnerLicenseExpired(context, extras);
                    break;
            }
        }
    }
}

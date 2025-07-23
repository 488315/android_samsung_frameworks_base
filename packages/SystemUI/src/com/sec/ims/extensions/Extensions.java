package com.sec.ims.extensions;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.UserManager;
import android.provider.Settings;
import com.samsung.android.knox.ex.peripheral.PeripheralConstants;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class Extensions {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ActivityManager {
        public static int getCurrentUser() {
            try {
                Class[] clsArr = new Class[0];
                return ((Integer) ReflectionUtils.invoke2(android.app.ActivityManager.class.getMethod("getCurrentUser", null), null, new Object[0])).intValue();
            } catch (IllegalStateException | NoSuchMethodException e) {
                e.printStackTrace();
                return -1;
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Build {
        public static final boolean IS_DEBUGGABLE = ((Boolean) ReflectionUtils.getValueOf("IS_DEBUGGABLE", (Class<?>) android.os.Build.class)).booleanValue();
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ContentProvider {
        public static Uri maybeAddUserId(Uri uri, int i) {
            try {
                return (Uri) ReflectionUtils.invoke2(android.content.ContentProvider.class.getMethod("maybeAddUserId", Uri.class, Integer.TYPE), null, uri, Integer.valueOf(i));
            } catch (IllegalStateException | NoSuchMethodException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ContentResolver {
        public static void registerContentObserver(android.content.ContentResolver contentResolver, Uri uri, boolean z, ContentObserver contentObserver, int i) {
            try {
                ReflectionUtils.invoke(android.content.ContentResolver.class.getMethod("registerContentObserver", Uri.class, Boolean.TYPE, ContentObserver.class, Integer.TYPE), contentResolver, uri, Boolean.valueOf(z), contentObserver, Integer.valueOf(i));
            } catch (IllegalStateException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Environment {
        public static void initForCurrentUser() {
            try {
                Class[] clsArr = new Class[0];
                ReflectionUtils.invoke(android.os.Environment.class.getMethod("initForCurrentUser", null), null, new Object[0]);
            } catch (IllegalStateException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ImsCallProfile {
        public static final String EXTRA_EMERGENCY_SERVICE_CATEGORY = "EccCat";
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Intent {
        public static final String ACTION_USER_SWITCHED = (String) ReflectionUtils.getValueOf("ACTION_USER_SWITCHED", (Class<?>) android.content.Intent.class);
        public static final int FLAG_RECEIVER_INCLUDE_BACKGROUND = ((Integer) ReflectionUtils.getValueOf("FLAG_RECEIVER_INCLUDE_BACKGROUND", (Class<?>) android.content.Intent.class)).intValue();
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class PhoneStateListener {
        public static final int LISTEN_PRECISE_CALL_STATE = ((Integer) ReflectionUtils.getValueOf("LISTEN_PRECISE_CALL_STATE", (Class<?>) android.telephony.PhoneStateListener.class)).intValue();
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class SubscriptionManager {
        public static int getActiveDataSubscriptionId() {
            return android.telephony.SubscriptionManager.getActiveDataSubscriptionId();
        }

        public static int getDefaultDataPhoneId(android.telephony.SubscriptionManager subscriptionManager) {
            try {
                Class[] clsArr = new Class[0];
                return ((Integer) ReflectionUtils.invoke2(android.telephony.SubscriptionManager.class.getMethod("getDefaultDataPhoneId", null), subscriptionManager, new Object[0])).intValue();
            } catch (IllegalStateException | NoSuchMethodException e) {
                e.printStackTrace();
                return -1;
            }
        }

        public static int getSlotId(int i) {
            return android.telephony.SubscriptionManager.getSlotIndex(i);
        }

        public static int[] getSubId(int i) {
            try {
                return (int[]) ReflectionUtils.invoke2(android.telephony.SubscriptionManager.class.getMethod("getSubId", Integer.TYPE), null, Integer.valueOf(i));
            } catch (IllegalStateException | NoSuchMethodException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class TelecomManager {
        public static final int RTT_MODE;
        public static final int RTT_MODE_OFF;
        public static final int TTY_MODE_OFF;

        static {
            int intValue = ((Integer) ReflectionUtils.getValueOf("TTY_MODE_OFF", (Class<?>) android.telecom.TelecomManager.class)).intValue();
            TTY_MODE_OFF = intValue;
            RTT_MODE = intValue + 4;
            RTT_MODE_OFF = intValue;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class UserHandle {
        public static final int USER_OWNER = 0;

        public static int myUserId() {
            try {
                Class[] clsArr = new Class[0];
                return ((Integer) ReflectionUtils.invoke2(android.os.UserHandle.class.getMethod("myUserId", null), null, new Object[0])).intValue();
            } catch (IllegalStateException | NoSuchMethodException e) {
                e.printStackTrace();
                return -1;
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class UserInfo {
        public static int getUserId(Object obj) {
            return ((Integer) ReflectionUtils.getValueOf("id", obj)).intValue();
        }

        public static boolean isBMode(Object obj) {
            try {
                Class[] clsArr = new Class[0];
                return ((Boolean) ReflectionUtils.invoke2(Class.forName("android.content.pm.UserInfo").getMethod("isBMode", null), obj, new Object[0])).booleanValue();
            } catch (ClassNotFoundException | IllegalStateException | NoSuchMethodException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class UserManagerRef {
        public static List<Object> getUsers(UserManager userManager) {
            try {
                Class[] clsArr = new Class[0];
                return (List) ReflectionUtils.invoke2(Class.forName("android.os.UserManager").getMethod("getUsers", null), userManager, new Object[0]);
            } catch (ClassNotFoundException | IllegalStateException | NoSuchMethodException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Settings {

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public class Global {
            public static final String MOBILE_DATA = (String) ReflectionUtils.getValueOf("MOBILE_DATA", (Class<?>) Settings.Global.class);
            public static final String DEVICE_NAME = (String) ReflectionUtils.getValueOf(PeripheralConstants.Internal.BtPairingExtraDataType.DEVICE_NAME, (Class<?>) Settings.Global.class);
            public static final String DEVICE_PROVISIONED = (String) ReflectionUtils.getValueOf("DEVICE_PROVISIONED", (Class<?>) Settings.Global.class);
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public class System {
            public static int getIntForUser(android.content.ContentResolver contentResolver, String str, int i) {
                try {
                    return ((Integer) ReflectionUtils.invoke2(Settings.System.class.getMethod("getIntForUser", android.content.ContentResolver.class, String.class, Integer.TYPE), null, contentResolver, str, Integer.valueOf(i))).intValue();
                } catch (IllegalStateException | NoSuchMethodException e) {
                    e.printStackTrace();
                    return -1;
                }
            }

            public static int getIntForUser(android.content.ContentResolver contentResolver, String str, int i, int i2) {
                try {
                    Class cls = Integer.TYPE;
                    return ((Integer) ReflectionUtils.invoke2(Settings.System.class.getMethod("getIntForUser", android.content.ContentResolver.class, String.class, cls, cls), null, contentResolver, str, Integer.valueOf(i), Integer.valueOf(i2))).intValue();
                } catch (IllegalStateException | NoSuchMethodException e) {
                    e.printStackTrace();
                    return -1;
                }
            }
        }
    }
}

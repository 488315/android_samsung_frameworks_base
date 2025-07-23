package com.android.internal.telephony.util;

import android.Manifest;
import android.app.ActivityManager;
import android.app.PendingIntent$$ExternalSyntheticLambda0;
import android.app.role.RoleManager;
import android.content.Context;
import android.content.pm.ComponentInfo;
import android.content.pm.ResolveInfo;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyFrameworkInitializer;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.telephony.ITelephony;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public final class TelephonyUtils {
    public static final Executor DIRECT_EXECUTOR;
    public static final boolean FORCE_VERBOSE_STATE_LOGGING = false;
    public static boolean IS_DEBUGGABLE = false;
    public static boolean IS_USER = "user".equals(Build.TYPE);
    private static final String LOG_TAG = "TelephonyUtils";

    public static boolean isValidService(int i) {
        return i >= 1 && i <= 6;
    }

    static {
        IS_DEBUGGABLE = SystemProperties.getInt("ro.debuggable", 0) == 1;
        DIRECT_EXECUTOR = new PendingIntent$$ExternalSyntheticLambda0();
    }

    public static boolean checkDumpPermission(Context context, String str, PrintWriter printWriter) {
        if (context.checkCallingOrSelfPermission(Manifest.permission.DUMP) == 0) {
            return true;
        }
        printWriter.println("Permission Denial: can't dump " + str + " from from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid() + " due to missing android.permission.DUMP permission");
        return false;
    }

    public static String emptyIfNull(String str) {
        return str == null ? "" : str;
    }

    public static <T> List<T> emptyIfNull(List<T> list) {
        return list == null ? Collections.EMPTY_LIST : list;
    }

    public static ComponentInfo getComponentInfo(ResolveInfo resolveInfo) {
        if (resolveInfo.activityInfo != null) {
            return resolveInfo.activityInfo;
        }
        if (resolveInfo.serviceInfo != null) {
            return resolveInfo.serviceInfo;
        }
        if (resolveInfo.providerInfo != null) {
            return resolveInfo.providerInfo;
        }
        throw new IllegalStateException("Missing ComponentInfo!");
    }

    public static void runWithCleanCallingIdentity(Runnable runnable) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            runnable.run();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static void runWithCleanCallingIdentity(final Runnable runnable, Executor executor) {
        if (runnable != null) {
            if (executor != null) {
                executor.execute(new Runnable() { // from class: com.android.internal.telephony.util.TelephonyUtils$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        TelephonyUtils.runWithCleanCallingIdentity(runnable);
                    }
                });
            } else {
                runWithCleanCallingIdentity(runnable);
            }
        }
    }

    public static <T> T runWithCleanCallingIdentity(Supplier<T> supplier) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return supplier.get();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static Bundle filterValues(Bundle bundle) {
        Bundle bundle2 = new Bundle(bundle);
        for (String str : bundle.keySet()) {
            Object obj = bundle.get(str);
            if (!(obj instanceof Integer) && !(obj instanceof Long) && !(obj instanceof Double) && !(obj instanceof String) && !(obj instanceof int[]) && !(obj instanceof long[]) && !(obj instanceof double[]) && !(obj instanceof String[]) && !(obj instanceof PersistableBundle) && obj != null && !(obj instanceof Boolean) && !(obj instanceof boolean[])) {
                if (obj instanceof Bundle) {
                    bundle2.putBundle(str, filterValues((Bundle) obj));
                } else if (!obj.getClass().getName().startsWith("android.")) {
                    bundle2.remove(str);
                }
            }
        }
        return bundle2;
    }

    public static void waitUntilReady(CountDownLatch countDownLatch, long j) {
        try {
            countDownLatch.await(j, TimeUnit.MILLISECONDS);
        } catch (InterruptedException unused) {
        }
    }

    public static String dataStateToString(int i) {
        switch (i) {
            case -1:
                return "UNKNOWN";
            case 0:
                return "DISCONNECTED";
            case 1:
                return "CONNECTING";
            case 2:
                return "CONNECTED";
            case 3:
                return "SUSPENDED";
            case 4:
                return "DISCONNECTING";
            case 5:
                return "HANDOVERINPROGRESS";
            default:
                return "UNKNOWN(" + i + NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public static String mobileDataPolicyToString(int i) {
        if (i == 1) {
            return "DATA_ON_NON_DEFAULT_DURING_VOICE_CALL";
        }
        if (i == 2) {
            return "MMS_ALWAYS_ALLOWED";
        }
        if (i == 3) {
            return "AUTO_DATA_SWITCH";
        }
        return "UNKNOWN(" + i + NavigationBarInflaterView.KEY_CODE_END;
    }

    public static String apnEditedStatusToString(int i) {
        if (i == 0) {
            return "UNEDITED";
        }
        if (i == 1) {
            return "USER_EDITED";
        }
        if (i == 2) {
            return "USER_DELETED";
        }
        if (i == 4) {
            return "CARRIER_EDITED";
        }
        if (i == 5) {
            return "CARRIER_DELETED";
        }
        return "UNKNOWN(" + i + NavigationBarInflaterView.KEY_CODE_END;
    }

    public static UserHandle getSubscriptionUserHandle(Context context, int i) {
        SubscriptionManager subscriptionManager = (SubscriptionManager) context.getSystemService(SubscriptionManager.class);
        if (subscriptionManager == null || !SubscriptionManager.isValidSubscriptionId(i)) {
            return null;
        }
        return subscriptionManager.getSubscriptionUserHandle(i);
    }

    public static void showSwitchToManagedProfileDialogIfAppropriate(Context context, int i, int i2, String str) {
        ITelephony asInterface;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            UserHandle userHandleForUid = UserHandle.getUserHandleForUid(i2);
            if (isUidForeground(context, i2) && isPackageSMSRoleHolderForUser(context, str, userHandleForUid)) {
                SubscriptionManager subscriptionManager = (SubscriptionManager) context.getSystemService(SubscriptionManager.class);
                if (!subscriptionManager.isActiveSubscriptionId(i)) {
                    Log.e(LOG_TAG, "Tried to send message with an inactive subscription " + i);
                    return;
                }
                UserHandle subscriptionUserHandle = subscriptionManager.getSubscriptionUserHandle(i);
                UserManager userManager = (UserManager) context.getSystemService(UserManager.class);
                if (subscriptionUserHandle != null && userManager.isManagedProfile(subscriptionUserHandle.getIdentifier()) && (asInterface = ITelephony.Stub.asInterface(TelephonyFrameworkInitializer.getTelephonyServiceManager().getTelephonyServiceRegisterer().get())) != null) {
                    try {
                        asInterface.showSwitchToManagedProfileDialog();
                    } catch (RemoteException unused) {
                        Log.e(LOG_TAG, "Failed to launch switch to managed profile dialog.");
                    }
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private static boolean isUidForeground(Context context, int i) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(ActivityManager.class);
        return activityManager != null && activityManager.getUidImportance(i) == 100;
    }

    private static boolean isPackageSMSRoleHolderForUser(Context context, String str, UserHandle userHandle) {
        List roleHoldersAsUser = ((RoleManager) context.getSystemService(RoleManager.class)).getRoleHoldersAsUser("android.app.role.SMS", userHandle);
        return !roleHoldersAsUser.isEmpty() && str.equals(roleHoldersAsUser.get(0));
    }

    private static boolean isValidPattern(String str, String str2) {
        return (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || !Pattern.compile(str2).matcher(str).matches()) ? false : true;
    }

    public static boolean isValidCountryCode(String str) {
        return isValidPattern(str, "^[A-Za-z]{2}$");
    }

    public static boolean isValidPlmn(String str) {
        return isValidPattern(str, "^(?:[0-9]{3})(?:[0-9]{2}|[0-9]{3})$");
    }
}

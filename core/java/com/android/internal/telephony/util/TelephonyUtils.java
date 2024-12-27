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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes5.dex */
public final class TelephonyUtils {
    public static final Executor DIRECT_EXECUTOR;
    public static boolean IS_DEBUGGABLE = false;
    public static boolean IS_USER = "user".equals(Build.TYPE);
    private static final String LOG_TAG = "TelephonyUtils";

    static {
        IS_DEBUGGABLE = SystemProperties.getInt("ro.debuggable", 0) == 1;
        DIRECT_EXECUTOR = new PendingIntent$$ExternalSyntheticLambda0();
    }

    public static boolean checkDumpPermission(Context context, String tag, PrintWriter pw) {
        if (context.checkCallingOrSelfPermission(Manifest.permission.DUMP) != 0) {
            pw.println(
                    "Permission Denial: can't dump "
                            + tag
                            + " from from pid="
                            + Binder.getCallingPid()
                            + ", uid="
                            + Binder.getCallingUid()
                            + " due to missing android.permission.DUMP permission");
            return false;
        }
        return true;
    }

    public static String emptyIfNull(String str) {
        return str == null ? "" : str;
    }

    public static <T> List<T> emptyIfNull(List<T> cur) {
        return cur == null ? Collections.emptyList() : cur;
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

    public static void runWithCleanCallingIdentity(Runnable action) {
        long callingIdentity = Binder.clearCallingIdentity();
        try {
            action.run();
        } finally {
            Binder.restoreCallingIdentity(callingIdentity);
        }
    }

    public static void runWithCleanCallingIdentity(final Runnable action, Executor executor) {
        if (action != null) {
            if (executor != null) {
                executor.execute(
                        new Runnable() { // from class:
                                         // com.android.internal.telephony.util.TelephonyUtils$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                TelephonyUtils.runWithCleanCallingIdentity(action);
                            }
                        });
            } else {
                runWithCleanCallingIdentity(action);
            }
        }
    }

    public static <T> T runWithCleanCallingIdentity(Supplier<T> action) {
        long callingIdentity = Binder.clearCallingIdentity();
        try {
            return action.get();
        } finally {
            Binder.restoreCallingIdentity(callingIdentity);
        }
    }

    public static Bundle filterValues(Bundle bundle) {
        Bundle ret = new Bundle(bundle);
        for (String key : bundle.keySet()) {
            Object value = bundle.get(key);
            if (!(value instanceof Integer)
                    && !(value instanceof Long)
                    && !(value instanceof Double)
                    && !(value instanceof String)
                    && !(value instanceof int[])
                    && !(value instanceof long[])
                    && !(value instanceof double[])
                    && !(value instanceof String[])
                    && !(value instanceof PersistableBundle)
                    && value != null
                    && !(value instanceof Boolean)
                    && !(value instanceof boolean[])) {
                if (value instanceof Bundle) {
                    ret.putBundle(key, filterValues((Bundle) value));
                } else if (!value.getClass().getName().startsWith("android.")) {
                    ret.remove(key);
                }
            }
        }
        return ret;
    }

    public static void waitUntilReady(CountDownLatch latch, long timeoutMs) {
        try {
            latch.await(timeoutMs, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
        }
    }

    public static String dataStateToString(int state) {
        switch (state) {
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
                return "UNKNOWN(" + state + NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public static String mobileDataPolicyToString(int mobileDataPolicy) {
        switch (mobileDataPolicy) {
            case 1:
                return "DATA_ON_NON_DEFAULT_DURING_VOICE_CALL";
            case 2:
                return "MMS_ALWAYS_ALLOWED";
            case 3:
                return "AUTO_DATA_SWITCH";
            default:
                return "UNKNOWN(" + mobileDataPolicy + NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public static String apnEditedStatusToString(int apnEditStatus) {
        switch (apnEditStatus) {
            case 0:
                return "UNEDITED";
            case 1:
                return "USER_EDITED";
            case 2:
                return "USER_DELETED";
            case 3:
            default:
                return "UNKNOWN(" + apnEditStatus + NavigationBarInflaterView.KEY_CODE_END;
            case 4:
                return "CARRIER_EDITED";
            case 5:
                return "CARRIER_DELETED";
        }
    }

    public static UserHandle getSubscriptionUserHandle(Context context, int subId) {
        SubscriptionManager subManager =
                (SubscriptionManager) context.getSystemService(SubscriptionManager.class);
        if (subManager == null || !SubscriptionManager.isValidSubscriptionId(subId)) {
            return null;
        }
        UserHandle userHandle = subManager.getSubscriptionUserHandle(subId);
        return userHandle;
    }

    public static void showSwitchToManagedProfileDialogIfAppropriate(
            Context context, int subId, int callingUid, String callingPackage) {
        ITelephony iTelephony;
        long token = Binder.clearCallingIdentity();
        try {
            UserHandle callingUserHandle = UserHandle.getUserHandleForUid(callingUid);
            if (isUidForeground(context, callingUid)
                    && isPackageSMSRoleHolderForUser(context, callingPackage, callingUserHandle)) {
                SubscriptionManager subscriptionManager =
                        (SubscriptionManager) context.getSystemService(SubscriptionManager.class);
                if (!subscriptionManager.isActiveSubscriptionId(subId)) {
                    Log.e(LOG_TAG, "Tried to send message with an inactive subscription " + subId);
                    return;
                }
                UserHandle associatedUserHandle =
                        subscriptionManager.getSubscriptionUserHandle(subId);
                UserManager um = (UserManager) context.getSystemService(UserManager.class);
                if (associatedUserHandle != null
                        && um.isManagedProfile(associatedUserHandle.getIdentifier())
                        && (iTelephony =
                                        ITelephony.Stub.asInterface(
                                                TelephonyFrameworkInitializer
                                                        .getTelephonyServiceManager()
                                                        .getTelephonyServiceRegisterer()
                                                        .get()))
                                != null) {
                    try {
                        iTelephony.showSwitchToManagedProfileDialog();
                    } catch (RemoteException e) {
                        Log.e(LOG_TAG, "Failed to launch switch to managed profile dialog.");
                    }
                }
            }
        } finally {
            Binder.restoreCallingIdentity(token);
        }
    }

    private static boolean isUidForeground(Context context, int uid) {
        ActivityManager am = (ActivityManager) context.getSystemService(ActivityManager.class);
        return am != null && am.getUidImportance(uid) == 100;
    }

    private static boolean isPackageSMSRoleHolderForUser(
            Context context, String callingPackage, UserHandle user) {
        RoleManager roleManager = (RoleManager) context.getSystemService(RoleManager.class);
        List<String> smsRoleHolder = roleManager.getRoleHoldersAsUser("android.app.role.SMS", user);
        return !smsRoleHolder.isEmpty() && callingPackage.equals(smsRoleHolder.get(0));
    }

    private static boolean isValidPattern(String input, String regex) {
        if (TextUtils.isEmpty(input) || TextUtils.isEmpty(regex)) {
            return false;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public static boolean isValidCountryCode(String countryCode) {
        return isValidPattern(countryCode, "^[A-Za-z]{2}$");
    }

    public static boolean isValidPlmn(String plmn) {
        return isValidPattern(plmn, "^(?:[0-9]{3})(?:[0-9]{2}|[0-9]{3})$");
    }

    public static boolean isValidService(int serviceType) {
        if (serviceType >= 1 && serviceType <= 6) {
            return true;
        }
        return false;
    }
}

package com.android.server.notification.edgelighting;

import android.app.AppOpsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.UserInfo;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.PowerManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.service.notification.StatusBarNotification;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Slog;
import android.util.SparseArray;

import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.widget.LockPatternUtils;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0;
import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;
import com.android.server.notification.NmRune;
import com.android.server.wm.WindowManagerInternal;

import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.edge.SemEdgeLightingInfo;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.view.SemWindowManager;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class EdgeLightingManager {
    public static final boolean DEBUG = Debug.semIsProductDev();
    public static final String TAG = "EdgeLightingManager";
    public final Context mContext;
    public final EdgeLightingClientManager mEdgeLightingClientManager;
    public final EdgeLightingPolicyManager mEdgeLightingPolicyManager;
    public IStatusBarService mIStatusBarService;
    public final AnonymousClass1 mPhoneStateListener;
    public final PowerManager mPowerManager;
    public final AnonymousClass2 mReceiver;
    public final SecurityPolicy mSecurityPolicy;
    public final UserManager mUserManager;
    public final WindowManagerInternal mWindowManagerInternal;
    public int mUserId = 0;
    public boolean mRinging = false;
    public boolean mUserSetupCompleted = false;
    public final Object mLock = new Object();
    public final SparseArray mCurrentProfiles = new SparseArray();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SecurityPolicy {
        public final AppOpsManager mAppOpsManager;
        public final Context mContext;

        /* renamed from: -$$Nest$menforceCallingPermission, reason: not valid java name */
        public static void m735$$Nest$menforceCallingPermission(
                SecurityPolicy securityPolicy, String str) {
            securityPolicy.getClass();
            int callingUid = Binder.getCallingUid();
            if ("eng".equals(Build.TYPE)
                    || securityPolicy.mContext.getPackageManager().checkSignatures(1000, callingUid)
                            == 0) {
                return;
            }
            Slog.e(
                    EdgeLightingManager.TAG,
                    AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0.m(
                            callingUid,
                            "Permission denied:",
                            str,
                            " uid=",
                            ", you need system uid or to be signed with the system certificate."));
            throw new SecurityException(
                    AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0.m(
                            callingUid,
                            "Permission denied:",
                            str,
                            " uid=",
                            ", you need system uid or to be signed with the system certificate."));
        }

        public SecurityPolicy(Context context) {
            this.mContext = context;
            this.mAppOpsManager = (AppOpsManager) context.getSystemService("appops");
        }

        public final void enforceCallFromPackage(String str) {
            this.mAppOpsManager.checkPackage(Binder.getCallingUid(), str);
        }

        public final void enforceCallingPermissionFromHost() {
            this.mContext.enforceCallingOrSelfPermission(
                    "com.samsung.android.permission.EDGE_LIGHTING_HOST", EdgeLightingManager.TAG);
        }
    }

    public EdgeLightingManager(Context context) {
        PhoneStateListener phoneStateListener =
                new PhoneStateListener() { // from class:
                                           // com.android.server.notification.edgelighting.EdgeLightingManager.1
                    @Override // android.telephony.PhoneStateListener
                    public final void onCallStateChanged(int i, String str) {
                        boolean z = i == 1;
                        EdgeLightingManager edgeLightingManager = EdgeLightingManager.this;
                        if (z == edgeLightingManager.mRinging) {
                            return;
                        }
                        edgeLightingManager.mRinging = z;
                        edgeLightingManager.mEdgeLightingPolicyManager.getClass();
                    }
                };
        BroadcastReceiver broadcastReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.android.server.notification.edgelighting.EdgeLightingManager.2
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        String action = intent.getAction();
                        if ("android.intent.action.SCREEN_OFF".equals(action)) {
                            EdgeLightingManager.this.mEdgeLightingClientManager.onScreenChanged(
                                    false);
                            EdgeLightingManager.this.getClass();
                        } else if ("android.intent.action.SCREEN_ON".equals(action)) {
                            EdgeLightingManager.this.mEdgeLightingClientManager.onScreenChanged(
                                    true);
                            EdgeLightingManager edgeLightingManager = EdgeLightingManager.this;
                            System.currentTimeMillis();
                            edgeLightingManager.getClass();
                        }
                    }
                };
        this.mContext = context;
        this.mSecurityPolicy = new SecurityPolicy(context);
        this.mEdgeLightingPolicyManager = new EdgeLightingPolicyManager(context);
        this.mEdgeLightingClientManager = new EdgeLightingClientManager(context);
        this.mPowerManager = (PowerManager) context.getSystemService("power");
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
        ((TelephonyManager) context.getSystemService("phone")).listen(phoneStateListener, 32);
        this.mWindowManagerInternal =
                (WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class);
        IntentFilter intentFilter = new IntentFilter("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        context.registerReceiver(broadcastReceiver, intentFilter);
    }

    public static boolean isFolded() {
        SemWindowManager semWindowManager;
        if (NmRune.NM_SUPPORT_SUB_DISPLAY_EDGE_LIGHTING
                || (semWindowManager = SemWindowManager.getInstance()) == null) {
            return false;
        }
        return semWindowManager.isFolded();
    }

    public final void hideForNotification(StatusBarNotification statusBarNotification) {
        if (isFolded()) {
            Slog.d(TAG, "hideForNotification : folded");
            return;
        }
        if (statusBarNotification == null || statusBarNotification.getNotification() == null) {
            return;
        }
        int identifier = statusBarNotification.getUser().getIdentifier();
        if (isCallingUserSupported(identifier)) {
            if (!isUserSetupCompleted()) {
                Slog.d(TAG, "hideForNotification : user setup is not yet completed");
                return;
            }
            String packageName = statusBarNotification.getPackageName();
            String str = TAG;
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    "hideForNotification : packageName = ", packageName, str);
            EdgeLightingPolicyManager.NotificationGroup notificationGroup =
                    this.mEdgeLightingPolicyManager.mNotificationGroup;
            synchronized (notificationGroup.mGroupMap) {
                Slog.d(
                        "EdgeLightingPolicyManager:NotificationGroup",
                        "remove : sbn : " + statusBarNotification);
                EdgeLightingPolicyManager.GroupNotificationData groupNotificationData =
                        (EdgeLightingPolicyManager.GroupNotificationData)
                                notificationGroup.mGroupMap.get(
                                        statusBarNotification.getGroupKey());
                if (groupNotificationData == null) {
                    notificationGroup.mGroupMap.remove(statusBarNotification.getGroupKey());
                } else if (statusBarNotification.getKey() != null) {
                    String key = statusBarNotification.getKey();
                    synchronized (groupNotificationData.mChildMap) {
                        groupNotificationData.mChildMap.remove(key);
                    }
                    if (groupNotificationData.mChildMap.size() == 0) {
                        notificationGroup.mGroupMap.remove(statusBarNotification.getGroupKey());
                    }
                } else if (groupNotificationData.mChildMap.size() == 0) {
                    notificationGroup.mGroupMap.remove(statusBarNotification.getGroupKey());
                }
                notificationGroup.dump();
            }
            if (!statusBarNotification.isOngoing()) {
                Slog.d(str, "hideForNotification : isOngoing is false");
                return;
            }
            if (this.mPowerManager.isInteractive()) {
                Slog.d(str, "hideForNotification : isInteractive is true");
                return;
            }
            if (this.mEdgeLightingPolicyManager.isEdgeLightingDisabled()
                    || this.mEdgeLightingPolicyManager.isEdgeLightingRestricted(1)
                    || this.mEdgeLightingPolicyManager.isEdgeLightingDisabledByPackage(
                            packageName)) {
                return;
            }
            if (this.mEdgeLightingPolicyManager.isAcceptableApplication(
                    1, identifier, packageName, false)) {
                if (EdgeLightingHistory.IS_DEV_DEBUG || DEBUG) {
                    DeviceIdleController$$ExternalSyntheticOutline0.m(
                            "hideEdgeLightingInternal : packageName = ",
                            packageName,
                            ", reason=1",
                            str);
                }
                this.mEdgeLightingClientManager.stopEdgeLightingInternal(1, packageName);
                return;
            }
            if (EdgeLightingHistory.IS_DEV_DEBUG || DEBUG) {
                Slog.d(str, "hideForNotification : return false by isAcceptableApplication.");
            }
        }
    }

    public final void hideForWakeLockByWindow(int i, String str) {
        EdgeLightingHistory edgeLightingHistory;
        String str2;
        boolean z = EdgeLightingHistory.IS_DEV_DEBUG;
        String str3 = TAG;
        boolean z2 = DEBUG;
        if (z || z2) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    "hideForWakeLockByWindow : packageName = ", str, str3);
        }
        int userId = UserHandle.getUserId(i);
        if (!isCallingUserSupported(userId)) {
            edgeLightingHistory = EdgeLightingHistory.getInstance();
            str2 = "hideForWakeLockInternal : Calling user is not supported | Package : ";
        } else if (isUserSetupCompleted()) {
            EdgeLightingPolicyManager edgeLightingPolicyManager = this.mEdgeLightingPolicyManager;
            if (edgeLightingPolicyManager.isAllowEdgelighting()) {
                EdgeLightingClientManager edgeLightingClientManager =
                        this.mEdgeLightingClientManager;
                if (edgeLightingClientManager.isAvailableEdgeLighting(2)) {
                    if (!edgeLightingPolicyManager.isEdgeLightingDisabled()
                            && !edgeLightingPolicyManager.isEdgeLightingRestricted(4)) {
                        if (edgeLightingPolicyManager.isAcceptableApplication(
                                4, userId, str, false)) {
                            edgeLightingClientManager.stopEdgeLightingInternal(6, str);
                            return;
                        } else {
                            if (z || z2) {
                                Slog.d(
                                        str3,
                                        "hideForWakeLockInternal : return false by"
                                            + " isAcceptableApplication.");
                                return;
                            }
                            return;
                        }
                    }
                    if (!z && !z2) {
                        return;
                    }
                    Slog.d(
                            str3,
                            "hideForWakeLockInternal : return false by checking disable policy.");
                    edgeLightingHistory = EdgeLightingHistory.getInstance();
                    str2 =
                            "hideForWakeLockInternal : return false by checking disable policy. |"
                                + " Package : ";
                } else {
                    if (!z && !z2) {
                        return;
                    }
                    Slog.d(
                            str3,
                            "hideForWakeLockInternal : return false by isAvailableEdgeLighting.");
                    edgeLightingHistory = EdgeLightingHistory.getInstance();
                    str2 =
                            "hideForWakeLockInternal : return false by isAvailableEdgeLighting. |"
                                + " Package : ";
                }
            } else {
                if (!z && !z2) {
                    return;
                }
                Slog.d(str3, "showForNotificationScreenOn : return false by isAllowEdgelighting.");
                edgeLightingHistory = EdgeLightingHistory.getInstance();
                str2 =
                        "hideForWakeLockInternal : return false by isAllowEdgelighting. | Package :"
                            + " ";
            }
        } else {
            Slog.d(str3, "hideForWakeLockInternal : user setup is not yet completed.");
            edgeLightingHistory = EdgeLightingHistory.getInstance();
            str2 = "hideForWakeLockInternal : user setup is not yet completed. | Package : ";
        }
        EdgeLightingManager$$ExternalSyntheticOutline0.m(str2, str, edgeLightingHistory);
    }

    public final boolean isCallingUserSupported(int i) {
        boolean z;
        boolean isDualAppId = SemDualAppManager.isDualAppId(i);
        boolean isKnoxId = SemPersonaManager.isKnoxId(i);
        if (EdgeLightingHistory.IS_DEV_DEBUG || DEBUG) {
            String str = TAG;
            StringBuilder m =
                    BatteryService$$ExternalSyntheticOutline0.m(
                            i, "isCallingUserSupported : callingUserId=", ", mUserId=");
            m.append(this.mUserId);
            m.append(", isDualAppId=");
            m.append(isDualAppId);
            m.append(", isKnoxId=");
            AnyMotionDetector$$ExternalSyntheticOutline0.m(str, m, isKnoxId);
        }
        if (this.mUserId == i || i == -1 || isDualAppId) {
            return true;
        }
        if (isKnoxId) {
            synchronized (this.mLock) {
                try {
                    SparseArray sparseArray = this.mCurrentProfiles;
                    z = (sparseArray == null || sparseArray.get(i) == null) ? false : true;
                    Slog.v(TAG, "isCurrentProfile = " + z);
                } finally {
                }
            }
            if (z) {
                return true;
            }
        }
        return false;
    }

    public final boolean isUserSetupCompleted() {
        if (!this.mUserSetupCompleted) {
            this.mUserSetupCompleted =
                    Settings.Secure.getIntForUser(
                                    this.mContext.getContentResolver(),
                                    "user_setup_complete",
                                    0,
                                    -2)
                            != 0;
        }
        return this.mUserSetupCompleted;
    }

    public final void showEdgeLightingInternal(int i, String str, Bundle bundle) {
        SemEdgeLightingInfo semEdgeLightingInfo =
                new SemEdgeLightingInfo(
                        2001, new int[] {bundle != null ? bundle.getInt("color", 0) : 0, 0});
        semEdgeLightingInfo.setUserId(this.mUserId);
        if (bundle != null) {
            semEdgeLightingInfo.setExtra(bundle);
        }
        if (EdgeLightingHistory.IS_DEV_DEBUG || DEBUG) {
            Slog.d(
                    TAG,
                    SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0
                            .m(i, "showEdgeLightingInternal : packageName = ", str, ", reason="));
        }
        this.mEdgeLightingClientManager.startEdgeLightingInternal(str, semEdgeLightingInfo, i);
    }

    /* JADX WARN: Removed duplicated region for block: B:127:0x035b  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x0360  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x0379  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x0374  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean showForNotification(
            android.service.notification.StatusBarNotification r24, android.os.Bundle r25) {
        /*
            Method dump skipped, instructions count: 1192
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.notification.edgelighting.EdgeLightingManager.showForNotification(android.service.notification.StatusBarNotification,"
                    + " android.os.Bundle):boolean");
    }

    public final boolean showForToast(int i, String str, String str2) {
        EdgeLightingPolicyManager.NotificationData notificationData;
        if (isFolded()) {
            Slog.d(TAG, "showForToast : folded");
            EdgeLightingHistory.getInstance().updateRejectHistory("showForToast : folded");
            return false;
        }
        int userId = UserHandle.getUserId(i);
        if (!isCallingUserSupported(userId)) {
            return false;
        }
        if (!isUserSetupCompleted()) {
            Slog.d(TAG, "showForToast : user setup is not yet completed.");
            EdgeLightingManager$$ExternalSyntheticOutline0.m(
                    "showForToast : user setup is not yet completed. | Package : ",
                    str,
                    EdgeLightingHistory.getInstance());
            return false;
        }
        boolean z = DEBUG;
        if (z) {
            GmsAlarmManager$$ExternalSyntheticOutline0.m(
                    "showForToast : packageName = ", str, ",text=", str2, TAG);
        } else if (EdgeLightingHistory.IS_DEV_DEBUG) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    "showForToast : packageName = ", str, TAG);
        }
        if (!this.mEdgeLightingPolicyManager.isAllowEdgelighting()) {
            if (EdgeLightingHistory.IS_DEV_DEBUG || z) {
                Slog.d(TAG, "showForNotificationScreenOn : return false by isAllowEdgelighting.");
                EdgeLightingManager$$ExternalSyntheticOutline0.m(
                        "showForToast : return false by isAllowEdgelighting. | Package : ",
                        str,
                        EdgeLightingHistory.getInstance());
            }
            return false;
        }
        if (!this.mEdgeLightingClientManager.isAvailableEdgeLighting(1)) {
            if (EdgeLightingHistory.IS_DEV_DEBUG || z) {
                Slog.d(TAG, "showForToast : return false by isAvailableEdgeLighting.");
                EdgeLightingManager$$ExternalSyntheticOutline0.m(
                        "showForToast : return false by isAvailableEdgeLighting. | Package : ",
                        str,
                        EdgeLightingHistory.getInstance());
            }
            return false;
        }
        if (this.mEdgeLightingPolicyManager.isEdgeLightingDisabled()
                || this.mEdgeLightingPolicyManager.isEdgeLightingRestricted(512)) {
            Slog.d(TAG, "showForToast : return false by checking disable policy.");
            EdgeLightingManager$$ExternalSyntheticOutline0.m(
                    "showForToast : return false by checking disable policy. | Package : ",
                    str,
                    EdgeLightingHistory.getInstance());
            return false;
        }
        if (!this.mEdgeLightingPolicyManager.isAcceptableApplication(512, userId, str, false)) {
            if (EdgeLightingHistory.IS_DEV_DEBUG || z) {
                Slog.d(TAG, "showForToast : return false by isAcceptableApplication.");
            }
            return false;
        }
        if (!this.mPowerManager.isInteractive()) {
            EdgeLightingManager$$ExternalSyntheticOutline0.m(
                    "showForToast : power not interactive. | Package : ",
                    str,
                    EdgeLightingHistory.getInstance());
            return false;
        }
        EdgeLightingPolicyManager edgeLightingPolicyManager = this.mEdgeLightingPolicyManager;
        synchronized (edgeLightingPolicyManager.mNotificationMap) {
            try {
                long currentTimeMillis = System.currentTimeMillis();
                notificationData =
                        (EdgeLightingPolicyManager.NotificationData)
                                edgeLightingPolicyManager.mNotificationMap.get(str);
                if (notificationData != null) {
                    if (EdgeLightingHistory.IS_DEV_DEBUG || EdgeLightingPolicyManager.DEBUG) {
                        Slog.d(
                                "EdgeLightingPolicyManager",
                                "getValidNotificationData packageName="
                                        + str
                                        + ",now="
                                        + currentTimeMillis
                                        + ",time="
                                        + notificationData.mTime
                                        + ",ret="
                                        + (currentTimeMillis - notificationData.mTime));
                    }
                    if (currentTimeMillis - notificationData.mTime < 4000) {}
                }
                notificationData = null;
            } finally {
            }
        }
        if (notificationData != null) {
            boolean z2 = (notificationData.mNotificationInfo.getInt("flag", 0) & 2) != 0;
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    "showForToast : ongoing check ", TAG, z2);
            if (!z2) {
                showEdgeLightingInternal(2, str, notificationData.mNotificationInfo);
                return true;
            }
            EdgeLightingManager$$ExternalSyntheticOutline0.m(
                    "showForToast : onGoing | Package : ", str, EdgeLightingHistory.getInstance());
        }
        return false;
    }

    public final boolean showForWakeLockByWindow(int i, String str) {
        boolean isInteractive = this.mPowerManager.isInteractive();
        if (EdgeLightingHistory.IS_DEV_DEBUG || DEBUG) {
            Slog.d(
                    TAG,
                    "showForWakeLockByWindow : packageName = "
                            + str
                            + ", screenOn = "
                            + isInteractive);
            EdgeLightingManager$$ExternalSyntheticOutline0.m(
                    "showForWakeLockByWindow : Calling user is not supported. | Package : ",
                    str,
                    EdgeLightingHistory.getInstance());
        }
        if (!isInteractive) {
            return showForWakeLockInternal(6, i, str);
        }
        EdgeLightingManager$$ExternalSyntheticOutline0.m(
                "showForWakeLockByWindow : screenOn | Package : ",
                str,
                EdgeLightingHistory.getInstance());
        return false;
    }

    public final boolean showForWakeLockInternal(int i, int i2, String str) {
        boolean isFolded = isFolded();
        String str2 = TAG;
        if (isFolded) {
            Slog.d(str2, "showForWakeLockInternal : folded");
            EdgeLightingHistory.getInstance()
                    .updateRejectHistory("showForWakeLockInternal : folded");
            return false;
        }
        int userId = UserHandle.getUserId(i2);
        if (!isCallingUserSupported(userId)) {
            EdgeLightingManager$$ExternalSyntheticOutline0.m(
                    "showForWakeLockInternal : Calling user is not supported. | Package : ",
                    str,
                    EdgeLightingHistory.getInstance());
            return false;
        }
        if ((new LockPatternUtils(this.mContext).getStrongAuthForUser(this.mUserId) & 32) != 0) {
            EdgeLightingHistory.getInstance()
                    .updateRejectHistory("showForWakeLockInternal : lockdown mode");
            return false;
        }
        if (!isUserSetupCompleted()) {
            Slog.d(str2, "showForWakeLockInternal : user setup is not yet completed");
            EdgeLightingManager$$ExternalSyntheticOutline0.m(
                    "showForWakeLockInternal : user setup is not yet completed. | Package : ",
                    str,
                    EdgeLightingHistory.getInstance());
            return false;
        }
        EdgeLightingPolicyManager edgeLightingPolicyManager = this.mEdgeLightingPolicyManager;
        boolean isAllowEdgelighting = edgeLightingPolicyManager.isAllowEdgelighting();
        boolean z = DEBUG;
        if (!isAllowEdgelighting) {
            if (EdgeLightingHistory.IS_DEV_DEBUG || z) {
                Slog.d(str2, "showForWakeLockInternal : return false by isAllowEdgelighting.");
                EdgeLightingManager$$ExternalSyntheticOutline0.m(
                        "showForWakeLockInternal : return false by isAllowEdgelighting. | Package :"
                            + " ",
                        str,
                        EdgeLightingHistory.getInstance());
            }
            return false;
        }
        if (!this.mEdgeLightingClientManager.isAvailableEdgeLighting(2)) {
            if (EdgeLightingHistory.IS_DEV_DEBUG || z) {
                Slog.d(str2, "showForWakeLockInternal : return false by isAvailableEdgeLighting.");
                EdgeLightingManager$$ExternalSyntheticOutline0.m(
                        "showForWakeLockInternal : return false by isAvailableEdgeLighting. |"
                            + " Package : ",
                        str,
                        EdgeLightingHistory.getInstance());
            }
            return false;
        }
        if (edgeLightingPolicyManager.isEdgeLightingDisabled()
                || edgeLightingPolicyManager.isEdgeLightingRestricted(4)) {
            if (EdgeLightingHistory.IS_DEV_DEBUG || z) {
                Slog.d(str2, "showForWakeLockInternal : return false by checking disable policy.");
                EdgeLightingManager$$ExternalSyntheticOutline0.m(
                        "showForWakeLockInternal : return false by checking disable policy. |"
                            + " Package : ",
                        str,
                        EdgeLightingHistory.getInstance());
            }
            return false;
        }
        if (edgeLightingPolicyManager.isAcceptableApplication(4, userId, str, false)) {
            showEdgeLightingInternal(i, str, null);
            return true;
        }
        if (EdgeLightingHistory.IS_DEV_DEBUG || z) {
            Slog.d(str2, "showForWakeLockInternal : return false by isAcceptableApplication.");
        }
        return false;
    }

    public final boolean showForWakeUp(String str, int i) {
        boolean z = EdgeLightingHistory.IS_DEV_DEBUG;
        String str2 = TAG;
        boolean z2 = DEBUG;
        if (z || z2) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    "showForWakeUp : packageName =", str, str2);
        }
        if (isFolded()) {
            Slog.d(str2, "showForWakeUpInternal : folded");
            EdgeLightingHistory.getInstance().updateRejectHistory("showForWakeUpInternal : folded");
            return false;
        }
        int userId = UserHandle.getUserId(i);
        if (!isCallingUserSupported(userId)) {
            EdgeLightingManager$$ExternalSyntheticOutline0.m(
                    "showForWakeUpInternal : Calling user is not supported. | Package : ",
                    str,
                    EdgeLightingHistory.getInstance());
            return false;
        }
        if ((new LockPatternUtils(this.mContext).getStrongAuthForUser(this.mUserId) & 32) != 0) {
            EdgeLightingHistory.getInstance()
                    .updateRejectHistory("showForWakeUpInternal : lockdown mode");
            return false;
        }
        if (!isUserSetupCompleted()) {
            Slog.d(str2, "showForWakeUpInternal : user setup is not yet completed");
            EdgeLightingManager$$ExternalSyntheticOutline0.m(
                    "showForWakeUpInternal : user setup is not yet completed. | Package : ",
                    str,
                    EdgeLightingHistory.getInstance());
            return false;
        }
        EdgeLightingPolicyManager edgeLightingPolicyManager = this.mEdgeLightingPolicyManager;
        if (!edgeLightingPolicyManager.isAllowEdgelighting()) {
            if (!z && !z2) {
                return false;
            }
            Slog.d(str2, "showForNotificationScreenOn : return false by isAllowEdgelighting.");
            EdgeLightingManager$$ExternalSyntheticOutline0.m(
                    "showForWakeUpInternal : return false by isAllowEdgelighting. | Package : ",
                    str,
                    EdgeLightingHistory.getInstance());
            return false;
        }
        if (!this.mEdgeLightingClientManager.isAvailableEdgeLighting(2)) {
            if (!z && !z2) {
                return false;
            }
            Slog.d(str2, "showForWakeUpInternal : return false by isAvailableEdgeLighting.");
            EdgeLightingManager$$ExternalSyntheticOutline0.m(
                    "showForWakeUpInternal : return false by isAvailableEdgeLighting. | Package : ",
                    str,
                    EdgeLightingHistory.getInstance());
            return false;
        }
        if (!edgeLightingPolicyManager.isEdgeLightingDisabled()
                && !edgeLightingPolicyManager.isEdgeLightingRestricted(2)) {
            if (edgeLightingPolicyManager.isAcceptableApplication(2, userId, str, false)) {
                showEdgeLightingInternal(3, str, null);
                return true;
            }
            Slog.d(str2, "showForWakeUpInternal : return false by isAcceptableApplication.");
            return false;
        }
        if (!z && !z2) {
            return false;
        }
        Slog.d(str2, "showForWakeUpInternal : return false by checking disable policy.");
        EdgeLightingManager$$ExternalSyntheticOutline0.m(
                "showForWakeUpInternal : return false by checking disable policy. | Package : ",
                str,
                EdgeLightingHistory.getInstance());
        return false;
    }

    public final void statusBarDisabled(int i) {
        EdgeLightingPolicyManager edgeLightingPolicyManager = this.mEdgeLightingPolicyManager;
        int i2 = edgeLightingPolicyManager.mStatusBarDisabled1 ^ i;
        edgeLightingPolicyManager.mStatusBarDisabled1 = i;
        StringBuilder sb = new StringBuilder("disable: < ");
        int i3 = i & 262144;
        sb.append(i3 != 0 ? "ALERTS" : "alerts");
        int i4 = i2 & 262144;
        sb.append(i4 != 0 ? "* " : " ");
        sb.append(">");
        EdgeLightingHistory edgeLightingHistory = EdgeLightingHistory.getInstance();
        String sb2 = sb.toString();
        edgeLightingHistory.getClass();
        if (EdgeLightingHistory.IS_DEV_DEBUG || EdgeLightingHistory.DEBUG) {
            if (EdgeLightingHistory.DEBUG) {
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        "updateEventHistory log = ", sb2, "EdgeLightingHistory");
            }
            String timestampFormat = EdgeLightingHistory.toTimestampFormat(sb2);
            synchronized (edgeLightingHistory.mLock) {
                try {
                    edgeLightingHistory.mEventHistory.add(timestampFormat);
                    while (edgeLightingHistory.mEventHistory.size() > 20) {
                        edgeLightingHistory.mEventHistory.remove(0);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        if (i4 != 0) {
            edgeLightingPolicyManager.mDisableNotificationAlerts = i3 != 0;
            edgeLightingPolicyManager.mHeadsUpObserver.onChange(true);
        }
    }

    public final void updateCurrentProfilesCache() {
        synchronized (this.mLock) {
            try {
                this.mCurrentProfiles.clear();
                UserManager userManager = this.mUserManager;
                if (userManager != null) {
                    for (UserInfo userInfo : userManager.getProfiles(this.mUserId)) {
                        this.mCurrentProfiles.put(userInfo.id, userInfo);
                    }
                }
                Slog.v(TAG, "updateCurrentProfilesCache size = " + this.mCurrentProfiles.size());
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}

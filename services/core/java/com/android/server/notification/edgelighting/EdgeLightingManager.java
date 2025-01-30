package com.android.server.notification.edgelighting;

import android.app.AppOpsManager;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.UserInfo;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.VibrationEffect;
import android.provider.Settings;
import android.service.notification.StatusBarNotification;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.statusbar.NotificationVisibility;
import com.android.internal.widget.LockPatternUtils;
import com.android.server.LocalServices;
import com.android.server.notification.NmRune;
import com.android.server.notification.edgelighting.EdgeLightingPolicyManager;
import com.android.server.wm.WindowManagerInternal;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.edge.EdgeLightingPolicy;
import com.samsung.android.edge.SemEdgeLightingInfo;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import com.samsung.android.view.SemWindowManager;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

/* loaded from: classes2.dex */
public class EdgeLightingManager {
    public static final boolean DEBUG = Debug.semIsProductDev();
    public static final String TAG = EdgeLightingManager.class.getSimpleName();
    public final Context mContext;
    public EdgeLightingClientManager mEdgeLightingClientManager;
    public EdgeLightingPolicyManager mEdgeLightingPolicyManager;
    public IStatusBarService mIStatusBarService;
    public PowerManager mPowerManager;
    public SecurityPolicy mSecurityPolicy;
    public TelephonyManager mTelephonyManager;
    public UserManager mUserManager;
    public WindowManagerInternal mWindowManagerInternal;
    public int mUserId = 0;
    public boolean mRinging = false;
    public long mScreenOnTimeStamp = 0;
    public String mTurnOverLightingPackage = null;
    public boolean mUserSetupCompleted = false;
    public final Object mLock = new Object();
    public final SparseArray mCurrentProfiles = new SparseArray();
    public PhoneStateListener mPhoneStateListener = new PhoneStateListener() { // from class: com.android.server.notification.edgelighting.EdgeLightingManager.1
        @Override // android.telephony.PhoneStateListener
        public void onCallStateChanged(int i, String str) {
            boolean z = i == 1;
            if (z == EdgeLightingManager.this.mRinging) {
                return;
            }
            EdgeLightingManager.this.mRinging = z;
            EdgeLightingManager.this.mEdgeLightingPolicyManager.setRinging(EdgeLightingManager.this.mRinging);
        }
    };
    public BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.android.server.notification.edgelighting.EdgeLightingManager.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.intent.action.SCREEN_OFF".equals(action)) {
                EdgeLightingManager.this.mEdgeLightingClientManager.onScreenChanged(false);
                EdgeLightingManager.this.mScreenOnTimeStamp = 0L;
            } else if ("android.intent.action.SCREEN_ON".equals(action)) {
                EdgeLightingManager.this.mEdgeLightingClientManager.onScreenChanged(true);
                EdgeLightingManager.this.mScreenOnTimeStamp = System.currentTimeMillis();
            }
        }
    };

    public EdgeLightingManager(Context context) {
        this.mContext = context;
        this.mSecurityPolicy = new SecurityPolicy(context);
        this.mEdgeLightingPolicyManager = new EdgeLightingPolicyManager(context);
        this.mEdgeLightingClientManager = new EdgeLightingClientManager(context);
        this.mPowerManager = (PowerManager) context.getSystemService("power");
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        this.mTelephonyManager = telephonyManager;
        telephonyManager.listen(this.mPhoneStateListener, 32);
        this.mWindowManagerInternal = (WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class);
        registerReceiver();
    }

    public final boolean isUserSetupCompleted() {
        if (!this.mUserSetupCompleted) {
            verifyUserSetupCompleted();
        }
        return this.mUserSetupCompleted;
    }

    public final void verifyUserSetupCompleted() {
        this.mUserSetupCompleted = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "user_setup_complete", 0, -2) != 0;
    }

    public final boolean isCallingUserSupported(int i) {
        boolean isDualAppId = SemDualAppManager.isDualAppId(i);
        boolean isKnoxId = SemPersonaManager.isKnoxId(i);
        if (EdgeLightingHistory.IS_DEV_DEBUG || DEBUG) {
            Slog.d(TAG, "isCallingUserSupported : callingUserId=" + i + ", mUserId=" + this.mUserId + ", isDualAppId=" + isDualAppId + ", isKnoxId=" + isKnoxId);
        }
        return this.mUserId == i || i == -1 || isDualAppId || (isKnoxId && isCurrentProfile(i));
    }

    public final void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        this.mContext.registerReceiver(this.mReceiver, intentFilter);
    }

    public void onBootPhaseAppsCanStart() {
        this.mEdgeLightingClientManager.onBootPhaseAppsCanStart();
    }

    public void onBootCompleted() {
        this.mEdgeLightingPolicyManager.onBootCompleted();
        updateCurrentProfilesCache();
    }

    public void onSwitchUser(int i) {
        this.mUserId = i;
        this.mEdgeLightingClientManager.onSwitchUser(i);
        this.mEdgeLightingPolicyManager.onSwitchUser(i);
        updateCurrentProfilesCache();
    }

    public void updateCurrentProfilesCache() {
        synchronized (this.mLock) {
            this.mCurrentProfiles.clear();
            UserManager userManager = this.mUserManager;
            if (userManager != null) {
                for (UserInfo userInfo : userManager.getProfiles(this.mUserId)) {
                    this.mCurrentProfiles.put(userInfo.id, userInfo);
                }
            }
            Slog.v(TAG, "updateCurrentProfilesCache size = " + this.mCurrentProfiles.size());
        }
    }

    public final boolean isCurrentProfile(int i) {
        boolean z;
        synchronized (this.mLock) {
            SparseArray sparseArray = this.mCurrentProfiles;
            z = (sparseArray == null || sparseArray.get(i) == null) ? false : true;
            Slog.v(TAG, "isCurrentProfile = " + z);
        }
        return z;
    }

    public void onUnlockUser(int i) {
        this.mEdgeLightingClientManager.onUnlockUser(i);
    }

    public void bindService(IBinder iBinder, int i, ComponentName componentName) {
        this.mSecurityPolicy.enforceCallingPermissionFromHost();
        this.mSecurityPolicy.enforceCallFromPackage(componentName.getPackageName());
        if (isEdgeLightingServiceUnbinded() && i == 4) {
            this.mTurnOverLightingPackage = null;
        }
        this.mEdgeLightingClientManager.bind(iBinder, i, componentName);
    }

    public void unbindService(IBinder iBinder, String str) {
        this.mSecurityPolicy.enforceCallingPermissionFromHost();
        this.mSecurityPolicy.enforceCallFromPackage(str);
        this.mEdgeLightingClientManager.unbind(iBinder, str);
    }

    public void updateEdgeLightingPackageList(String str, List list) {
        this.mSecurityPolicy.enforceCallingPermissionFromHost();
        this.mSecurityPolicy.enforceCallFromPackage(str);
        this.mEdgeLightingClientManager.updateEdgeLightingPackageList(str, list);
    }

    public void registerListener(IBinder iBinder, ComponentName componentName) {
        this.mSecurityPolicy.enforceCallFromPackage(componentName.getPackageName());
        this.mSecurityPolicy.enforceCallingPermission("registerListener");
        this.mEdgeLightingClientManager.registerListener(iBinder, componentName);
    }

    public void unregisterListener(IBinder iBinder, String str) {
        this.mSecurityPolicy.enforceCallFromPackage(str);
        this.mSecurityPolicy.enforceCallingPermission("unregisterListener");
        this.mEdgeLightingClientManager.unregisterListener(iBinder, str);
    }

    public void updateEdgeLightingPolicy(String str, EdgeLightingPolicy edgeLightingPolicy) {
        if (edgeLightingPolicy == null) {
            Slog.d(TAG, "updateEdgeLightingPolicy : policy is null");
            return;
        }
        this.mSecurityPolicy.enforceCallingPermissionFromHost();
        this.mSecurityPolicy.enforceCallFromPackage(str);
        this.mEdgeLightingPolicyManager.updateEdgeLightingPolicyFromHost(edgeLightingPolicy);
        this.mEdgeLightingClientManager.setConnectedMode((edgeLightingPolicy.getPolicyType() & 8) == 0);
    }

    public void startEdgeLighting(String str, SemEdgeLightingInfo semEdgeLightingInfo, IBinder iBinder) {
        this.mSecurityPolicy.enforceCallFromPackage(str);
        this.mSecurityPolicy.enforceCallingPermission("startEdgeLighting");
        this.mEdgeLightingClientManager.startEdgeLighting(str, semEdgeLightingInfo);
    }

    public void stopEdgeLighting(String str, IBinder iBinder) {
        this.mSecurityPolicy.enforceCallFromPackage(str);
        this.mSecurityPolicy.enforceCallingPermission("stopEdgeLighting");
        this.mEdgeLightingClientManager.stopEdgeLighting(str);
    }

    public final void showEdgeLightingInternal(String str, Bundle bundle, boolean z, int i, int i2) {
        SemEdgeLightingInfo semEdgeLightingInfo = new SemEdgeLightingInfo(2001, new int[]{bundle != null ? bundle.getInt("color", 0) : 0, 0});
        semEdgeLightingInfo.setUserId(this.mUserId);
        if (z) {
            semEdgeLightingInfo.setRepeatCount(-1);
        }
        if (bundle != null) {
            semEdgeLightingInfo.setExtra(bundle);
        }
        if (EdgeLightingHistory.IS_DEV_DEBUG || DEBUG) {
            Slog.d(TAG, "showEdgeLightingInternal : packageName = " + str + ", reason=" + i);
        }
        this.mEdgeLightingClientManager.startEdgeLightingInternal(str, semEdgeLightingInfo, i);
    }

    public final void hideEdgeLightingInternal(String str, int i) {
        if (EdgeLightingHistory.IS_DEV_DEBUG || DEBUG) {
            Slog.d(TAG, "hideEdgeLightingInternal : packageName = " + str + ", reason=" + i);
        }
        this.mEdgeLightingClientManager.stopEdgeLightingInternal(str, i);
    }

    public int getEdgeLightingState() {
        return this.mEdgeLightingClientManager.getEdgeLightingState();
    }

    public boolean isEdgeLightingNotificationAllowed(String str) {
        this.mSecurityPolicy.enforceCallFromPackage(str);
        int callingUserId = UserHandle.getCallingUserId();
        if (isCallingUserSupported(callingUserId) && this.mEdgeLightingPolicyManager.isHUNPeeked() && !this.mEdgeLightingPolicyManager.isEdgeLightingDisabled()) {
            if (!this.mEdgeLightingPolicyManager.isEdgeLightingRestricted(1) && !this.mEdgeLightingPolicyManager.isEdgeLightingDisabledByPackage(1, str)) {
                if (this.mEdgeLightingClientManager.isAvailableEdgeLighting(this.mPowerManager.isInteractive() ? 1 : 2)) {
                    return this.mEdgeLightingPolicyManager.isAcceptableApplication(str, 7, callingUserId);
                }
            }
        }
        return false;
    }

    public void disable(int i, String str, IBinder iBinder) {
        this.mSecurityPolicy.enforceCallFromPackage(str);
        this.mSecurityPolicy.enforceCallingPermission("disableEdgeLighting");
        this.mEdgeLightingPolicyManager.disable(i, str, iBinder);
    }

    public void disableEdgeLightingNotification(String str, boolean z) {
        this.mSecurityPolicy.enforceCallFromPackage(str);
        this.mEdgeLightingPolicyManager.disableEdgeLighting(1, str, z);
    }

    /* JADX WARN: Code restructure failed: missing block: B:36:0x00fc, code lost:
    
        if (com.android.server.notification.edgelighting.EdgeLightingManager.DEBUG != false) goto L30;
     */
    /* JADX WARN: Type inference failed for: r9v3, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r9v4 */
    /* JADX WARN: Type inference failed for: r9v5 */
    /* JADX WARN: Type inference failed for: r9v6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void cancelNotificationByGroupKey(String str, String str2, int i, int i2, String str3, String str4) {
        EdgeLightingPolicyManager.GroupNotificationData groupNotificationData;
        EdgeLightingPolicyManager.NotificationData summaryNotification;
        int i3;
        ?? r9;
        boolean z = EdgeLightingHistory.IS_DEV_DEBUG;
        if (z || DEBUG) {
            Slog.i(TAG, "cancelNotificationByGroupKey : cancel notification  uesrid = " + i2 + ", id=  " + i + " , key=  " + str3 + " , tag = " + str2 + " , group = " + str4);
        }
        EdgeLightingHistory.getInstance().updateEdgeLightingHistory("cancelNotificationByGroupKey pkg=" + str);
        this.mSecurityPolicy.enforceCallingPermissionFromHost();
        this.mSecurityPolicy.enforceCallingPermission("cancelNotificationByGroupKey");
        if (this.mIStatusBarService == null) {
            this.mIStatusBarService = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
        }
        if (this.mIStatusBarService == null) {
            Slog.e(TAG, "cancelNotificationByGroupKey : mIStatusBarService is null.");
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (str4 != null) {
                try {
                    groupNotificationData = this.mEdgeLightingPolicyManager.getGroupNotificationData(str4);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                if (groupNotificationData != null && groupNotificationData.getChildNotificationCount() <= 1 && groupNotificationData.getChild(str3) != null && (summaryNotification = groupNotificationData.getSummaryNotification()) != null) {
                    int i4 = summaryNotification.mNotificationInfo.getInt("user_id", 0);
                    int i5 = summaryNotification.mNotificationInfo.getInt("noti_id", 0);
                    String string = summaryNotification.mNotificationInfo.getString("noti_key", null);
                    String string2 = summaryNotification.mNotificationInfo.getString("noti_tag", null);
                    boolean z2 = true;
                    i3 = 0;
                    this.mIStatusBarService.onNotificationClear(str, i4, string, 0, 0, NotificationVisibility.obtain(string, 0, 1, true));
                    if (!z) {
                        r9 = z2;
                    }
                    Slog.i(TAG, "cancelNotificationByGroupKey : Child count 1. so cancel summary notification : uesrid = " + i4 + ", id=  " + i5 + " , key=  " + string + " , tag = " + string2);
                    r9 = z2;
                    this.mIStatusBarService.onNotificationClear(str, i2, str3, 0, 0, NotificationVisibility.obtain(str3, i3, (int) r9, (boolean) r9));
                }
            }
            r9 = 1;
            i3 = 0;
            this.mIStatusBarService.onNotificationClear(str, i2, str3, 0, 0, NotificationVisibility.obtain(str3, i3, (int) r9, (boolean) r9));
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void cancelNotification(String str, String str2, int i, int i2, String str3) {
        if (EdgeLightingHistory.IS_DEV_DEBUG || DEBUG) {
            Slog.d(TAG, "cancelNotification : pkg= " + str);
        }
        EdgeLightingHistory.getInstance().updateEdgeLightingHistory("cancelNotification pkg=" + str);
        this.mSecurityPolicy.enforceCallingPermissionFromHost();
        this.mSecurityPolicy.enforceCallingPermission("cancelNotification");
        if (this.mIStatusBarService == null) {
            this.mIStatusBarService = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
        }
        if (this.mIStatusBarService == null) {
            Slog.e(TAG, "cancelNotification : mIStatusBarService is null.");
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                this.mIStatusBarService.onNotificationClear(str, i2, str3, 0, 0, NotificationVisibility.obtain(str3, 0, 1, true));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean showForNotification(StatusBarNotification statusBarNotification, Bundle bundle) {
        int i;
        boolean isInteractive = this.mPowerManager.isInteractive();
        if (statusBarNotification.getNotification().fullScreenIntent != null) {
            if (!isInteractive) {
                Slog.d(TAG, "showForNotification : Should show fullScreenIntent while screen off");
                return false;
            }
            if (this.mWindowManagerInternal.isKeyguardShowingAndNotOccluded()) {
                Slog.d(TAG, "showForNotification : Should show fullScreenIntent while screen on and keyguard");
                return false;
            }
        }
        if (isFolded()) {
            Slog.d(TAG, "showForNotification : folded");
            EdgeLightingHistory.getInstance().updateRejectHistory("showForNotification : folded");
            return false;
        }
        if ((new LockPatternUtils(this.mContext).getStrongAuthForUser(this.mUserId) & 32) != 0) {
            EdgeLightingHistory.getInstance().updateRejectHistory("showForNotification : lockdown mode");
            return false;
        }
        boolean z = bundle.getBoolean("isHeadUp", false);
        boolean z2 = bundle.getBoolean("isUpdate", false);
        boolean z3 = bundle.getBoolean("isInterrupt", false);
        boolean z4 = bundle.getBoolean("bubble", false);
        boolean z5 = bundle.getBoolean("smartPopup", false);
        boolean z6 = bundle.getBoolean("canBypassDnd", false);
        int i2 = bundle.getInt(LauncherConfigurationInternal.KEY_SUPPLEMENT_SERVICE_PAGE_VISIBILITY_BOOLEAN, -1000);
        Uri uri = (Uri) bundle.getParcelable("sound");
        VibrationEffect vibrationEffect = (VibrationEffect) bundle.getParcelable("vibrate");
        if (statusBarNotification.getNotification() == null) {
            EdgeLightingHistory.getInstance().updateRejectHistory("showForNotification : Notification is null");
            return false;
        }
        boolean z7 = true;
        boolean z8 = (uri == null || Uri.EMPTY.equals(uri)) ? false : true;
        AudioManager audioManager = (AudioManager) this.mContext.getSystemService("audio");
        boolean z9 = vibrationEffect != null;
        if (vibrationEffect == null && z8 && audioManager.getRingerModeInternal() == 1) {
            z9 = true;
        }
        if (!z8 && !z9) {
            z7 = false;
        }
        int identifier = statusBarNotification.getUser().getIdentifier();
        String packageName = statusBarNotification.getPackageName();
        if (!isCallingUserSupported(identifier)) {
            EdgeLightingHistory.getInstance().updateRejectHistory("showForNotification : Calling user is not supported. | Package : " + packageName);
            return false;
        }
        if (!isUserSetupCompleted()) {
            Slog.d(TAG, "showForNotification : user setup is not yet completed");
            EdgeLightingHistory.getInstance().updateRejectHistory("showForNotification : user setup is not yet completed  | Package : " + packageName);
            return false;
        }
        int i3 = statusBarNotification.getNotification().ledARGB;
        boolean z10 = z7;
        if (EdgeLightingHistory.IS_DEV_DEBUG || DEBUG) {
            i = identifier;
            Slog.d(TAG, "showForNotification : isInteractive=" + isInteractive + ", isHeadUp=" + z + ", color=" + Integer.toHexString(i3) + ", canBypassDnd=" + z6 + ", sbn = " + statusBarNotification);
        } else {
            i = identifier;
            Slog.d(TAG, "showForNotification : isInteractive=" + isInteractive + ", isHeadUp=" + z + ", color=" + Integer.toHexString(i3) + ", canBypassDnd=" + z6 + ", packageName = " + packageName);
        }
        if (this.mEdgeLightingPolicyManager.isDexTouchpadEnabled()) {
            Slog.d(TAG, "showForNotification : DEX touchpad enable");
            EdgeLightingHistory.getInstance().updateRejectHistory("showForNotification : DEX touchpad enable : " + packageName);
            return false;
        }
        if ((statusBarNotification.getNotification().semFlags & 32) != 0) {
            Slog.d(TAG, "showForNotification : return false by notication disabling edge lighting.");
            EdgeLightingHistory.getInstance().updateRejectHistory("showForNotification : return false by notication disabling edge lighting | Package : " + packageName);
            return false;
        }
        if (z3) {
            Slog.d(TAG, "showForNotification : return false by isIntercepted");
            EdgeLightingHistory.getInstance().updateRejectHistory("showForNotification : isIntercepted. | Package : " + packageName);
            return false;
        }
        if (!isInteractive) {
            return showForNotificationScreenOff(statusBarNotification, z, z2, i, i2, z10, z4);
        }
        if (z4) {
            EdgeLightingHistory.getInstance().updateRejectHistory("showForNotification : canBubble. | Package : " + packageName);
            return false;
        }
        if (z5) {
            EdgeLightingHistory.getInstance().updateRejectHistory("showForNotification : isSmartPopup. | Package : " + packageName);
            return false;
        }
        return showForNotificationScreenOn(statusBarNotification, z, z2, i, i2, z10, z4);
    }

    public final boolean isEdgeLightingServiceUnbinded() {
        return !this.mEdgeLightingClientManager.existsHosts() && this.mEdgeLightingClientManager.isEdgeLightingSettingEnabled(this.mContext.getContentResolver());
    }

    public final boolean showForNotificationScreenOff(StatusBarNotification statusBarNotification, boolean z, boolean z2, int i, int i2, boolean z3, boolean z4) {
        String packageName = statusBarNotification.getPackageName();
        int edgeLightingCondition = this.mEdgeLightingClientManager.getEdgeLightingCondition();
        if (!this.mEdgeLightingClientManager.isAvailableEdgeLighting(6)) {
            if (EdgeLightingHistory.IS_DEV_DEBUG || DEBUG) {
                Slog.d(TAG, "showForNotificationScreenOff : return false by isAvailableEdgeLighting.");
                EdgeLightingHistory.getInstance().updateRejectHistory("showForNotificationScreenOff : return false by isAvailableEdgeLighting. | Package : " + packageName);
            }
            return false;
        }
        if (this.mEdgeLightingPolicyManager.isEdgeLightingDisabled() || this.mEdgeLightingPolicyManager.isEdgeLightingRestricted(1) || this.mEdgeLightingPolicyManager.isEdgeLightingDisabledByPackage(1, packageName)) {
            Slog.d(TAG, "showForNotificationScreenOff : return false by checking disable policy.");
            EdgeLightingHistory.getInstance().updateRejectHistory("showForNotificationScreenOff : return false by checking disable policy  | Package : " + packageName);
            return false;
        }
        Bundle putNotification = this.mEdgeLightingPolicyManager.putNotification(statusBarNotification, z, z2, i2, z3, z4);
        if (EdgeLightingPolicyManager.isEmptyText(putNotification)) {
            Slog.d(TAG, "showForNotificationScreenOff : texts are empty.");
            EdgeLightingHistory.getInstance().updateRejectHistory("showForNotificationScreenOff : texts are empty. | Package : " + packageName);
            Notification notification = statusBarNotification.getNotification();
            notification.semFlags = notification.semFlags | 16384;
            return false;
        }
        if (z2 && (statusBarNotification.getNotification().flags & 8) != 0) {
            Slog.i(TAG, "showForNotificationScreenOff Alert once notification. sbn: " + statusBarNotification);
            EdgeLightingHistory.getInstance().updateRejectHistory("showForNotificationScreenOff : Alert once notification. | Package : " + packageName + ", sbn: " + statusBarNotification);
            return false;
        }
        if (statusBarNotification.isGroup() && statusBarNotification.getNotification().suppressAlertingDueToGrouping()) {
            Slog.d(TAG, "showForNotificationScreenOff No peeking: suppressed due to group alert behavior");
            EdgeLightingHistory.getInstance().updateRejectHistory("showForNotificationScreenOff : No peeking suppressed due to group alert behavior. | Package : " + packageName);
            return false;
        }
        if (!this.mEdgeLightingPolicyManager.isAcceptableApplication(packageName, 1, (edgeLightingCondition & 4) != 0, i)) {
            if (EdgeLightingHistory.IS_DEV_DEBUG || DEBUG) {
                Slog.d(TAG, "showForNotificationScreenOff : return false by isAcceptableApplication.");
            }
            return false;
        }
        showEdgeLightingInternal(packageName, putNotification, false, edgeLightingCondition == 4 ? 7 : 1, 1);
        return (edgeLightingCondition & 1) != 0;
    }

    public final boolean showForNotificationScreenOn(StatusBarNotification statusBarNotification, boolean z, boolean z2, int i, int i2, boolean z3, boolean z4) {
        String packageName = statusBarNotification.getPackageName();
        int edgeLightingCondition = this.mEdgeLightingClientManager.getEdgeLightingCondition();
        if (!this.mEdgeLightingClientManager.isAvailableEdgeLighting(1)) {
            if (EdgeLightingHistory.IS_DEV_DEBUG || DEBUG) {
                Slog.d(TAG, "showForNotificationScreenOn : return false by isAvailableEdgeLighting.");
                EdgeLightingHistory.getInstance().updateRejectHistory("showForNotificationScreenOn : return false by isAvailableEdgeLighting. | Package : " + packageName);
            }
            return false;
        }
        boolean isAcceptableApplication = this.mEdgeLightingPolicyManager.isAcceptableApplication(packageName, 256, i);
        if ((!isAcceptableApplication && !this.mEdgeLightingPolicyManager.isEnabledHeadsUp()) || !this.mEdgeLightingPolicyManager.isHUNPeeked() || this.mEdgeLightingPolicyManager.isEdgeLightingDisabled() || this.mEdgeLightingPolicyManager.isEdgeLightingRestricted(1) || this.mEdgeLightingPolicyManager.isEdgeLightingDisabledByPackage(1, packageName)) {
            Slog.d(TAG, "showForNotificationScreenOn : return false by checking disable policy.");
            EdgeLightingHistory.getInstance().updateRejectHistory("showForNotificationScreenOn : return false by checking disable policy.  | Package : " + packageName);
            return false;
        }
        Bundle putNotification = this.mEdgeLightingPolicyManager.putNotification(statusBarNotification, z, z2, i2, z3, z4);
        if (EdgeLightingPolicyManager.isEmptyText(putNotification)) {
            Slog.d(TAG, "showForNotificationScreenOn : texts are empty.");
            EdgeLightingHistory.getInstance().updateRejectHistory("showForNotificationScreenOn : texts are empty. | Package : " + packageName);
            Notification notification = statusBarNotification.getNotification();
            notification.semFlags = notification.semFlags | 16384;
            return false;
        }
        if (z2 && (statusBarNotification.getNotification().flags & 8) != 0) {
            Slog.i(TAG, "showForNotificationScreenOn Alert once notification. sbn: " + statusBarNotification);
            EdgeLightingHistory.getInstance().updateRejectHistory("showForNotificationScreenOn : Alert once notification. | Package : " + packageName + ", sbn: " + statusBarNotification);
            return false;
        }
        if (statusBarNotification.isGroup() && statusBarNotification.getNotification().suppressAlertingDueToGrouping()) {
            Slog.d(TAG, "showForNotificationScreenOn No peeking: suppressed due to group alert behavior");
            EdgeLightingHistory.getInstance().updateRejectHistory("showForNotificationScreenOn : No peeking suppressed due to group alert behavior. | Package : " + packageName);
            return false;
        }
        if (!this.mEdgeLightingPolicyManager.isAcceptableApplication(packageName, 1, i)) {
            if (EdgeLightingHistory.IS_DEV_DEBUG || DEBUG) {
                Slog.d(TAG, "showForNotificationScreenOn : return false by isAcceptableApplication.");
            }
            return false;
        }
        if (z) {
            isAcceptableApplication = z;
        }
        boolean z5 = isAcceptableApplication && this.mEdgeLightingPolicyManager.isNotificationForEdgeLighting(statusBarNotification);
        if (z5) {
            showEdgeLightingInternal(packageName, putNotification, false, edgeLightingCondition == 4 ? 7 : 1, 1);
        }
        if ((edgeLightingCondition & 1) != 0) {
            return z5;
        }
        return false;
    }

    public boolean hideForNotification(StatusBarNotification statusBarNotification, int i) {
        if (isFolded()) {
            Slog.d(TAG, "hideForNotification : folded");
            return false;
        }
        if (statusBarNotification != null && statusBarNotification.getNotification() != null) {
            int identifier = statusBarNotification.getUser().getIdentifier();
            if (!isCallingUserSupported(identifier)) {
                return false;
            }
            if (!isUserSetupCompleted()) {
                Slog.d(TAG, "hideForNotification : user setup is not yet completed");
                return false;
            }
            String packageName = statusBarNotification.getPackageName();
            String str = TAG;
            Slog.d(str, "hideForNotification : packageName = " + packageName);
            this.mEdgeLightingPolicyManager.removeNotification(statusBarNotification);
            if (!statusBarNotification.isOngoing()) {
                Slog.d(str, "hideForNotification : isOngoing is false");
                return false;
            }
            if (this.mPowerManager.isInteractive()) {
                Slog.d(str, "hideForNotification : isInteractive is true");
                return false;
            }
            if (!this.mEdgeLightingPolicyManager.isEdgeLightingDisabled() && !this.mEdgeLightingPolicyManager.isEdgeLightingRestricted(1) && !this.mEdgeLightingPolicyManager.isEdgeLightingDisabledByPackage(1, packageName)) {
                if (!this.mEdgeLightingPolicyManager.isAcceptableApplication(packageName, 1, identifier)) {
                    if (EdgeLightingHistory.IS_DEV_DEBUG || DEBUG) {
                        Slog.d(str, "hideForNotification : return false by isAcceptableApplication.");
                    }
                    return false;
                }
                hideEdgeLightingInternal(packageName, 1);
            }
        }
        return false;
    }

    public boolean showForToast(String str, String str2, int i) {
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
            EdgeLightingHistory.getInstance().updateRejectHistory("showForToast : user setup is not yet completed. | Package : " + str);
            return false;
        }
        boolean z = DEBUG;
        if (z) {
            Slog.d(TAG, "showForToast : packageName = " + str + ",text=" + str2);
        } else if (EdgeLightingHistory.IS_DEV_DEBUG) {
            Slog.d(TAG, "showForToast : packageName = " + str);
        }
        if (!this.mEdgeLightingPolicyManager.isAllowEdgelighting(true)) {
            if (EdgeLightingHistory.IS_DEV_DEBUG || z) {
                Slog.d(TAG, "showForNotificationScreenOn : return false by isAllowEdgelighting.");
                EdgeLightingHistory.getInstance().updateRejectHistory("showForToast : return false by isAllowEdgelighting. | Package : " + str);
            }
            return false;
        }
        if (!this.mEdgeLightingClientManager.isAvailableEdgeLighting(1)) {
            if (EdgeLightingHistory.IS_DEV_DEBUG || z) {
                Slog.d(TAG, "showForToast : return false by isAvailableEdgeLighting.");
                EdgeLightingHistory.getInstance().updateRejectHistory("showForToast : return false by isAvailableEdgeLighting. | Package : " + str);
            }
            return false;
        }
        if (this.mEdgeLightingPolicyManager.isEdgeLightingDisabled() || this.mEdgeLightingPolicyManager.isEdgeLightingRestricted(512)) {
            Slog.d(TAG, "showForToast : return false by checking disable policy.");
            EdgeLightingHistory.getInstance().updateRejectHistory("showForToast : return false by checking disable policy. | Package : " + str);
            return false;
        }
        if (!this.mEdgeLightingPolicyManager.isAcceptableApplication(str, 512, userId)) {
            if (EdgeLightingHistory.IS_DEV_DEBUG || z) {
                Slog.d(TAG, "showForToast : return false by isAcceptableApplication.");
            }
            return false;
        }
        if (!this.mPowerManager.isInteractive()) {
            EdgeLightingHistory.getInstance().updateRejectHistory("showForToast : power not interactive. | Package : " + str);
            return false;
        }
        EdgeLightingPolicyManager.NotificationData validNotificationData = this.mEdgeLightingPolicyManager.getValidNotificationData(str);
        if (validNotificationData != null) {
            boolean isOnGoing = validNotificationData.isOnGoing();
            Slog.d(TAG, "showForToast : ongoing check " + isOnGoing);
            if (!isOnGoing) {
                showEdgeLightingInternal(str, validNotificationData.mNotificationInfo, false, 2, 512);
                return true;
            }
            EdgeLightingHistory.getInstance().updateRejectHistory("showForToast : onGoing | Package : " + str);
        }
        return false;
    }

    public boolean showForWakeUp(String str, int i) {
        if (EdgeLightingHistory.IS_DEV_DEBUG || DEBUG) {
            Slog.d(TAG, "showForWakeUp : packageName =" + str);
        }
        return showForWakeUpInternal(str, 3, i);
    }

    public boolean showForWakeUpByWindow(String str, String str2, int i) {
        if (!EdgeLightingHistory.IS_DEV_DEBUG && !DEBUG) {
            return false;
        }
        Slog.d(TAG, "showForWakeUpByWindow is not supported : packageName = " + str);
        return false;
    }

    public final boolean showForWakeUpInternal(String str, int i, int i2) {
        if (isFolded()) {
            Slog.d(TAG, "showForWakeUpInternal : folded");
            EdgeLightingHistory.getInstance().updateRejectHistory("showForWakeUpInternal : folded");
            return false;
        }
        int userId = UserHandle.getUserId(i2);
        if (!isCallingUserSupported(userId)) {
            EdgeLightingHistory.getInstance().updateRejectHistory("showForWakeUpInternal : Calling user is not supported. | Package : " + str);
            return false;
        }
        if ((new LockPatternUtils(this.mContext).getStrongAuthForUser(this.mUserId) & 32) != 0) {
            EdgeLightingHistory.getInstance().updateRejectHistory("showForWakeUpInternal : lockdown mode");
            return false;
        }
        if (!isUserSetupCompleted()) {
            Slog.d(TAG, "showForWakeUpInternal : user setup is not yet completed");
            EdgeLightingHistory.getInstance().updateRejectHistory("showForWakeUpInternal : user setup is not yet completed. | Package : " + str);
            return false;
        }
        if (!this.mEdgeLightingPolicyManager.isAllowEdgelighting(false)) {
            if (EdgeLightingHistory.IS_DEV_DEBUG || DEBUG) {
                Slog.d(TAG, "showForNotificationScreenOn : return false by isAllowEdgelighting.");
                EdgeLightingHistory.getInstance().updateRejectHistory("showForWakeUpInternal : return false by isAllowEdgelighting. | Package : " + str);
            }
            return false;
        }
        if (!this.mEdgeLightingClientManager.isAvailableEdgeLighting(2)) {
            if (EdgeLightingHistory.IS_DEV_DEBUG || DEBUG) {
                Slog.d(TAG, "showForWakeUpInternal : return false by isAvailableEdgeLighting.");
                EdgeLightingHistory.getInstance().updateRejectHistory("showForWakeUpInternal : return false by isAvailableEdgeLighting. | Package : " + str);
            }
            return false;
        }
        if (this.mEdgeLightingPolicyManager.isEdgeLightingDisabled() || this.mEdgeLightingPolicyManager.isEdgeLightingRestricted(2)) {
            if (EdgeLightingHistory.IS_DEV_DEBUG || DEBUG) {
                Slog.d(TAG, "showForWakeUpInternal : return false by checking disable policy.");
                EdgeLightingHistory.getInstance().updateRejectHistory("showForWakeUpInternal : return false by checking disable policy. | Package : " + str);
            }
            return false;
        }
        if (!this.mEdgeLightingPolicyManager.isAcceptableApplication(str, 2, userId)) {
            Slog.d(TAG, "showForWakeUpInternal : return false by isAcceptableApplication.");
            return false;
        }
        showEdgeLightingInternal(str, null, false, i, 2);
        return true;
    }

    public boolean showForWakeLock(String str, int i) {
        if (EdgeLightingHistory.IS_DEV_DEBUG || DEBUG) {
            Slog.d(TAG, "showForWakeLock : packageName = " + str);
        }
        return showForWakeLockInternal(str, 4, i);
    }

    public boolean showForWakeLockByWindow(String str, String str2, int i) {
        boolean isInteractive = this.mPowerManager.isInteractive();
        if (EdgeLightingHistory.IS_DEV_DEBUG || DEBUG) {
            Slog.d(TAG, "showForWakeLockByWindow : packageName = " + str + ", screenOn = " + isInteractive);
            EdgeLightingHistory edgeLightingHistory = EdgeLightingHistory.getInstance();
            StringBuilder sb = new StringBuilder();
            sb.append("showForWakeLockByWindow : Calling user is not supported. | Package : ");
            sb.append(str);
            edgeLightingHistory.updateRejectHistory(sb.toString());
        }
        if (isInteractive) {
            EdgeLightingHistory.getInstance().updateRejectHistory("showForWakeLockByWindow : screenOn | Package : " + str);
            return false;
        }
        return showForWakeLockInternal(str, 6, i);
    }

    public final boolean showForWakeLockInternal(String str, int i, int i2) {
        if (isFolded()) {
            Slog.d(TAG, "showForWakeLockInternal : folded");
            EdgeLightingHistory.getInstance().updateRejectHistory("showForWakeLockInternal : folded");
            return false;
        }
        int userId = UserHandle.getUserId(i2);
        if (!isCallingUserSupported(userId)) {
            EdgeLightingHistory.getInstance().updateRejectHistory("showForWakeLockInternal : Calling user is not supported. | Package : " + str);
            return false;
        }
        if ((new LockPatternUtils(this.mContext).getStrongAuthForUser(this.mUserId) & 32) != 0) {
            EdgeLightingHistory.getInstance().updateRejectHistory("showForWakeLockInternal : lockdown mode");
            return false;
        }
        if (!isUserSetupCompleted()) {
            Slog.d(TAG, "showForWakeLockInternal : user setup is not yet completed");
            EdgeLightingHistory.getInstance().updateRejectHistory("showForWakeLockInternal : user setup is not yet completed. | Package : " + str);
            return false;
        }
        if (!this.mEdgeLightingPolicyManager.isAllowEdgelighting(false)) {
            if (EdgeLightingHistory.IS_DEV_DEBUG || DEBUG) {
                Slog.d(TAG, "showForWakeLockInternal : return false by isAllowEdgelighting.");
                EdgeLightingHistory.getInstance().updateRejectHistory("showForWakeLockInternal : return false by isAllowEdgelighting. | Package : " + str);
            }
            return false;
        }
        if (!this.mEdgeLightingClientManager.isAvailableEdgeLighting(2)) {
            if (EdgeLightingHistory.IS_DEV_DEBUG || DEBUG) {
                Slog.d(TAG, "showForWakeLockInternal : return false by isAvailableEdgeLighting.");
                EdgeLightingHistory.getInstance().updateRejectHistory("showForWakeLockInternal : return false by isAvailableEdgeLighting. | Package : " + str);
            }
            return false;
        }
        if (this.mEdgeLightingPolicyManager.isEdgeLightingDisabled() || this.mEdgeLightingPolicyManager.isEdgeLightingRestricted(4)) {
            if (EdgeLightingHistory.IS_DEV_DEBUG || DEBUG) {
                Slog.d(TAG, "showForWakeLockInternal : return false by checking disable policy.");
                EdgeLightingHistory.getInstance().updateRejectHistory("showForWakeLockInternal : return false by checking disable policy. | Package : " + str);
            }
            return false;
        }
        if (!this.mEdgeLightingPolicyManager.isAcceptableApplication(str, 4, userId)) {
            if (EdgeLightingHistory.IS_DEV_DEBUG || DEBUG) {
                Slog.d(TAG, "showForWakeLockInternal : return false by isAcceptableApplication.");
            }
            return false;
        }
        showEdgeLightingInternal(str, null, false, i, 4);
        return true;
    }

    public void hideForWakeLock(String str, int i) {
        if (EdgeLightingHistory.IS_DEV_DEBUG || DEBUG) {
            Slog.d(TAG, "hideForWakeLock packageName=" + str);
        }
        Slog.d(TAG, "hideForWakeLock is no more used");
    }

    public void hideForWakeLockByWindow(String str, String str2, int i) {
        if (EdgeLightingHistory.IS_DEV_DEBUG || DEBUG) {
            Slog.d(TAG, "hideForWakeLockByWindow : packageName = " + str);
        }
        hideForWakeLockInternal(str, 6, i);
    }

    public final void hideForWakeLockInternal(String str, int i, int i2) {
        int userId = UserHandle.getUserId(i2);
        if (!isCallingUserSupported(userId)) {
            EdgeLightingHistory.getInstance().updateRejectHistory("hideForWakeLockInternal : Calling user is not supported | Package : " + str);
            return;
        }
        if (!isUserSetupCompleted()) {
            Slog.d(TAG, "hideForWakeLockInternal : user setup is not yet completed.");
            EdgeLightingHistory.getInstance().updateRejectHistory("hideForWakeLockInternal : user setup is not yet completed. | Package : " + str);
            return;
        }
        if (!this.mEdgeLightingPolicyManager.isAllowEdgelighting(false)) {
            if (EdgeLightingHistory.IS_DEV_DEBUG || DEBUG) {
                Slog.d(TAG, "showForNotificationScreenOn : return false by isAllowEdgelighting.");
                EdgeLightingHistory.getInstance().updateRejectHistory("hideForWakeLockInternal : return false by isAllowEdgelighting. | Package : " + str);
                return;
            }
            return;
        }
        if (!this.mEdgeLightingClientManager.isAvailableEdgeLighting(2)) {
            if (EdgeLightingHistory.IS_DEV_DEBUG || DEBUG) {
                Slog.d(TAG, "hideForWakeLockInternal : return false by isAvailableEdgeLighting.");
                EdgeLightingHistory.getInstance().updateRejectHistory("hideForWakeLockInternal : return false by isAvailableEdgeLighting. | Package : " + str);
                return;
            }
            return;
        }
        if (this.mEdgeLightingPolicyManager.isEdgeLightingDisabled() || this.mEdgeLightingPolicyManager.isEdgeLightingRestricted(4)) {
            if (EdgeLightingHistory.IS_DEV_DEBUG || DEBUG) {
                Slog.d(TAG, "hideForWakeLockInternal : return false by checking disable policy.");
                EdgeLightingHistory.getInstance().updateRejectHistory("hideForWakeLockInternal : return false by checking disable policy. | Package : " + str);
                return;
            }
            return;
        }
        if (!this.mEdgeLightingPolicyManager.isAcceptableApplication(str, 4, userId)) {
            if (EdgeLightingHistory.IS_DEV_DEBUG || DEBUG) {
                Slog.d(TAG, "hideForWakeLockInternal : return false by isAcceptableApplication.");
                return;
            }
            return;
        }
        this.mEdgeLightingClientManager.stopEdgeLightingInternal(str, i);
    }

    public void showForResumedActivity(ComponentName componentName) {
        this.mEdgeLightingPolicyManager.setResumedComponent(componentName);
    }

    public void statusBarDisabled(int i, int i2) {
        this.mEdgeLightingPolicyManager.statusBarDisabled(i, i2);
    }

    public void setSuppressed(int i) {
        this.mEdgeLightingPolicyManager.setSuppressed(i);
    }

    public boolean isPackageEnabled(String str, int i) {
        if (str == null) {
            Slog.d(TAG, "isPackageEnabled : packageName is null");
            return false;
        }
        if (this.mEdgeLightingPolicyManager.isRecommendPackage(str)) {
            Slog.d(TAG, "isPackageEnabled : recommend packageName = " + str);
            return true;
        }
        return this.mEdgeLightingPolicyManager.isAcceptableApplication(str, 1, i);
    }

    public final boolean isFolded() {
        SemWindowManager semWindowManager;
        if (NmRune.NM_SUPPORT_SUB_DISPLAY_EDGE_LIGHTING || (semWindowManager = SemWindowManager.getInstance()) == null) {
            return false;
        }
        return semWindowManager.isFolded();
    }

    public final class SecurityPolicy {
        public final AppOpsManager mAppOpsManager;
        public Context mContext;

        public SecurityPolicy(Context context) {
            this.mContext = context;
            this.mAppOpsManager = (AppOpsManager) context.getSystemService("appops");
        }

        public void enforceCallingPermissionFromHost() {
            this.mContext.enforceCallingOrSelfPermission("com.samsung.android.permission.EDGE_LIGHTING_HOST", EdgeLightingManager.TAG);
        }

        public void enforceCallFromPackage(String str) {
            this.mAppOpsManager.checkPackage(Binder.getCallingUid(), str);
        }

        public final void enforceCallingPermission(String str) {
            int callingUid = Binder.getCallingUid();
            if ("eng".equals(Build.TYPE) || this.mContext.getPackageManager().checkSignatures(1000, callingUid) == 0) {
                return;
            }
            Slog.e(EdgeLightingManager.TAG, "Permission denied:" + str + " uid=" + callingUid + ", you need system uid or to be signed with the system certificate.");
            throw new SecurityException("Permission denied:" + str + " uid=" + callingUid + ", you need system uid or to be signed with the system certificate.");
        }
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println("[EdgeLightingManager]");
        this.mEdgeLightingClientManager.dump(fileDescriptor, printWriter, strArr);
        this.mEdgeLightingPolicyManager.dump(fileDescriptor, printWriter, strArr);
        EdgeLightingHistory.getInstance().dump(fileDescriptor, printWriter, strArr);
    }
}

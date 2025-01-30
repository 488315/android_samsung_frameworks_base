package com.android.server.notification.edgelighting;

import android.app.INotificationManager;
import android.app.Notification;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.provider.Settings;
import android.service.notification.StatusBarNotification;
import android.service.vr.IVrManager;
import android.service.vr.IVrStateCallbacks;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.notification.edgelighting.policy.EdgeLightingPolicyRepository;
import com.samsung.android.cover.CoverManager;
import com.samsung.android.cover.CoverState;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.edge.EdgeLightingPolicy;
import com.samsung.android.edge.EdgeLightingPolicyInfo;
import com.samsung.android.emergencymode.SemEmergencyManager;
import com.samsung.android.feature.SemFloatingFeature;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class EdgeLightingPolicyManager {
    public static final boolean DEBUG = Debug.semIsProductDev();
    public static final Uri DESKTOP_SETTINGS_URI;
    public static final boolean EMERGENCY_MODE_ENABLED;
    public static final String TAG = "EdgeLightingPolicyManager";
    public EdgeLightingPolicyRepository mBlackPolicy;
    public Context mContext;
    public CoverManager mCoverManager;
    public final ContentObserver mDesktopSettingsObserver;
    public SemDesktopModeManager mDexManager;
    public final ContentObserver mHeadsUpObserver;
    public boolean mIsTouchpadEnabled;
    public INotificationManager mNm;
    public PackageManager mPackageManager;
    public ComponentName mResumedComponent;
    public long mResumedComponentTime;
    public boolean mVrMode;
    public final IVrStateCallbacks mVrStateCallbacks;
    public EdgeLightingPolicyRepository mWhitePolicy;
    public int mPolicyType = 0;
    public long mPolicyVersion = 0;
    public ArrayList mPriorityPolicy = new ArrayList();
    public boolean mRinging = false;
    public final HashMap mNotificationMap = new HashMap();
    public final NotificationGroup mNotificationGroup = new NotificationGroup();
    public final ArrayList mDisableRecords = new ArrayList();
    public final SparseArray mDisabledPackages = new SparseArray();
    public Handler mHandler = new Handler();
    public boolean mUseHeadsUp = false;
    public int mStatusBarDisabled1 = 0;
    public int mSuppressed = 0;
    public int mUserId = 0;
    public boolean mDisableNotificationAlerts = false;

    public final boolean isSupportedCover(int i) {
        return false;
    }

    static {
        EMERGENCY_MODE_ENABLED = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_SAFETYCARE") || SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_ULTRA_POWER_SAVING") || SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_BATTERY_CONVERSING");
        DESKTOP_SETTINGS_URI = Uri.parse("content://com.sec.android.desktopmode.uiservice.SettingsProvider/settings");
    }

    public final String getDesktopSettingsValue(String str, String str2) {
        Bundle bundle = new Bundle();
        bundle.putString("key", str);
        bundle.putString("def", str2);
        try {
            Bundle call = this.mContext.getContentResolver().call(DESKTOP_SETTINGS_URI, "getSettings", (String) null, bundle);
            if (call != null) {
                return call.getString(str);
            }
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Failed to get settings", e);
        }
        return str2;
    }

    public class NotificationData {
        public Bundle mNotificationInfo;
        public long mTime;

        public NotificationData(long j, Bundle bundle) {
            this.mTime = j;
            this.mNotificationInfo = bundle;
        }

        public String toString() {
            return " NotificationData { tag= " + this.mNotificationInfo.getString("noti_tag") + " } ";
        }

        public boolean isOnGoing() {
            return (this.mNotificationInfo.getInt("flag", 0) & 2) != 0;
        }
    }

    public EdgeLightingPolicyManager(Context context) {
        ContentObserver contentObserver = new ContentObserver(this.mHandler) { // from class: com.android.server.notification.edgelighting.EdgeLightingPolicyManager.1
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                EdgeLightingPolicyManager.this.mUseHeadsUp = !r3.mDisableNotificationAlerts;
                String str = EdgeLightingPolicyManager.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("heads up is ");
                sb.append(EdgeLightingPolicyManager.this.mUseHeadsUp ? "enabled" : "disabled");
                Log.d(str, sb.toString());
            }
        };
        this.mHeadsUpObserver = contentObserver;
        ContentObserver contentObserver2 = new ContentObserver(this.mHandler) { // from class: com.android.server.notification.edgelighting.EdgeLightingPolicyManager.2
            @Override // android.database.ContentObserver
            public void onChange(boolean z, Uri uri) {
                super.onChange(z, uri);
                String lastPathSegment = uri.getLastPathSegment();
                if (lastPathSegment == null || !lastPathSegment.equals("touchpad_enabled")) {
                    return;
                }
                EdgeLightingPolicyManager edgeLightingPolicyManager = EdgeLightingPolicyManager.this;
                edgeLightingPolicyManager.mIsTouchpadEnabled = "true".equals(edgeLightingPolicyManager.getDesktopSettingsValue(lastPathSegment, "false"));
            }
        };
        this.mDesktopSettingsObserver = contentObserver2;
        IVrStateCallbacks.Stub stub = new IVrStateCallbacks.Stub() { // from class: com.android.server.notification.edgelighting.EdgeLightingPolicyManager.3
            public void onVrStateChanged(boolean z) {
                EdgeLightingPolicyManager.this.mVrMode = z;
            }
        };
        this.mVrStateCallbacks = stub;
        this.mContext = context;
        this.mPackageManager = context.getPackageManager();
        this.mDexManager = (SemDesktopModeManager) context.getSystemService("desktopmode");
        this.mWhitePolicy = new EdgeLightingPolicyRepository();
        this.mBlackPolicy = new EdgeLightingPolicyRepository();
        contentObserver.onChange(true);
        this.mContext.getContentResolver().registerContentObserver(Settings.Global.getUriFor("heads_up_notifications_enabled"), true, contentObserver);
        contentObserver2.onChange(true);
        this.mContext.getContentResolver().registerContentObserver(DESKTOP_SETTINGS_URI, true, contentObserver2);
        this.mNm = INotificationManager.Stub.asInterface(ServiceManager.getService("notification"));
        IVrManager asInterface = IVrManager.Stub.asInterface(ServiceManager.getService("vrmanager"));
        if (asInterface != null) {
            try {
                asInterface.registerListener(stub);
            } catch (RemoteException e) {
                Slog.e(TAG, "Failed to register VR mode state listener: " + e);
            }
        }
    }

    public void onBootCompleted() {
        Slog.d(TAG, "onBootCompleted");
    }

    public void onSwitchUser(int i) {
        this.mUserId = i;
    }

    public void updateEdgeLightingPolicyFromHost(EdgeLightingPolicy edgeLightingPolicy) {
        this.mWhitePolicy.clear();
        this.mBlackPolicy.clear();
        this.mPriorityPolicy.clear();
        this.mPolicyType = edgeLightingPolicy.getPolicyType();
        this.mPolicyVersion = edgeLightingPolicy.getPolicyVersion();
        Iterator it = edgeLightingPolicy.getEdgeLightingPolicyInfoList().iterator();
        while (it.hasNext()) {
            EdgeLightingPolicyInfo edgeLightingPolicyInfo = (EdgeLightingPolicyInfo) it.next();
            int i = edgeLightingPolicyInfo.category;
            if (i == 1) {
                this.mWhitePolicy.updatePolicy(edgeLightingPolicyInfo);
            } else if (i == 2) {
                this.mBlackPolicy.updatePolicy(edgeLightingPolicyInfo);
            }
        }
        String stringForUser = Settings.Secure.getStringForUser(this.mContext.getContentResolver(), "edge_lighting_recommend_app_list", -2);
        if (stringForUser != null) {
            for (String str : stringForUser.split(",")) {
                this.mPriorityPolicy.add(str);
            }
        }
    }

    public boolean isRecommendPackage(String str) {
        return this.mPriorityPolicy.contains(str);
    }

    public boolean isHUNPeeked() {
        if (isDeviceInVrMode()) {
            Slog.d(TAG, "isHUNPeeked : Vr mode");
            return false;
        }
        if (!isMirrorLinkOn()) {
            return true;
        }
        Slog.d(TAG, "isHUNPeeked : mirror link on");
        return false;
    }

    public boolean isEnabledHeadsUp() {
        if (this.mUseHeadsUp) {
            return true;
        }
        Slog.d(TAG, "isHUNPeeked : UseHeadsUp = " + this.mUseHeadsUp);
        return false;
    }

    public boolean isDexTouchpadEnabled() {
        return this.mIsTouchpadEnabled;
    }

    public final boolean isDeviceInVrMode() {
        return this.mVrMode;
    }

    public final boolean isMirrorLinkOn() {
        return "1".equals(SystemProperties.get("net.mirrorlink.on"));
    }

    public final ApplicationInfo getApplicationInfo(String str) {
        try {
            return this.mPackageManager.getApplicationInfo(str, 0);
        } catch (PackageManager.NameNotFoundException unused) {
            Slog.e(TAG, "getApplicationInfo : packageName = " + str);
            return null;
        }
    }

    public final boolean isSystemApp(ApplicationInfo applicationInfo) {
        if (applicationInfo == null) {
            return false;
        }
        return applicationInfo.isSystemApp() || applicationInfo.isUpdatedSystemApp();
    }

    public final boolean isValidApplication(String str, int i) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        intent.setPackage(str);
        List queryIntentActivitiesAsUser = this.mPackageManager.queryIntentActivitiesAsUser(intent, 0, i);
        return queryIntentActivitiesAsUser != null && queryIntentActivitiesAsUser.size() > 0;
    }

    public boolean isAcceptableApplication(String str, int i, int i2) {
        return isAcceptableApplication(str, i, false, i2);
    }

    public boolean isAcceptableApplication(String str, int i, boolean z, int i2) {
        boolean isValidApplication;
        int i3;
        EdgeLightingPolicyInfo policyByCategory = getPolicyByCategory(1, str);
        boolean z2 = EdgeLightingHistory.IS_DEV_DEBUG;
        if (z2 || DEBUG) {
            StringBuffer stringBuffer = new StringBuffer("isAcceptableApplication: ");
            stringBuffer.append(" pkg=");
            stringBuffer.append(str);
            stringBuffer.append(" , range=");
            stringBuffer.append(i);
            stringBuffer.append(" , includeAllApp=");
            stringBuffer.append(z);
            stringBuffer.append(" , userId=");
            stringBuffer.append(i2);
            stringBuffer.append(" , infoRange=");
            stringBuffer.append(policyByCategory != null ? policyByCategory.range : 0);
            stringBuffer.append(" , infoCategory=");
            stringBuffer.append(policyByCategory != null ? policyByCategory.category : 0);
            Slog.d(TAG, stringBuffer.toString());
        }
        if ((65280 & i) != 0) {
            return (policyByCategory == null || (policyByCategory.range & i) == 0) ? false : true;
        }
        if (z || (this.mPolicyType & 1) != 0) {
            isValidApplication = isValidApplication(str, i2);
            if (isValidApplication && policyByCategory != null && (policyByCategory.range & i) == 0) {
                isValidApplication = false;
            }
        } else {
            isValidApplication = getAcceptablePolicy(str, i) != null;
            if (z2 || DEBUG) {
                StringBuffer stringBuffer2 = new StringBuffer("isAcceptableApplication: getAcceptablePolicy ");
                stringBuffer2.append(isValidApplication);
                Slog.d(TAG, stringBuffer2.toString());
            }
        }
        if (isValidApplication && (this.mPolicyType & 2) != 0) {
            ApplicationInfo applicationInfo = getApplicationInfo(str);
            isValidApplication = (applicationInfo == null || isSystemApp(applicationInfo)) ? false : true;
        }
        try {
            if (i2 < 0) {
                i3 = this.mPackageManager.getPackageUidAsUser(str, 0, this.mUserId);
            } else {
                i3 = this.mPackageManager.getPackageUidAsUser(str, 0, i2);
            }
        } catch (Exception e) {
            Slog.d(TAG, "getPackageUidAsUser failed - " + e);
            i3 = 0;
        }
        if ((i & 1) != 0) {
            try {
                if (this.mNm == null) {
                    this.mNm = INotificationManager.Stub.asInterface(ServiceManager.getService("notification"));
                }
                INotificationManager iNotificationManager = this.mNm;
                if (iNotificationManager != null) {
                    isValidApplication = iNotificationManager.isEdgeLightingAllowed(str, i3);
                }
                if (EdgeLightingHistory.IS_DEV_DEBUG || DEBUG) {
                    Slog.d(TAG, "isAcceptableApplication - accept rechecked by isEdgeLightingAllowed = " + isValidApplication + " uid = " + i3);
                }
            } catch (Exception e2) {
                Slog.d(TAG, "isEdgeLightingAllowed failed - " + e2);
            }
        }
        if (!isValidApplication || (this.mPolicyType & 4) == 0) {
            return isValidApplication;
        }
        boolean z3 = getRejectablePolicy(str, i) == null;
        if (isValidApplication && !z3) {
            try {
                INotificationManager iNotificationManager2 = this.mNm;
                if (iNotificationManager2 != null) {
                    iNotificationManager2.setAllowEdgeLighting(str, i3, false);
                }
                if (EdgeLightingHistory.IS_DEV_DEBUG || DEBUG) {
                    Slog.d(TAG, "Block list app so setAllowEdgeLighting to false");
                }
            } catch (Exception e3) {
                Slog.d(TAG, "setAllowEdgeLighting failed - " + e3);
            }
        }
        if (EdgeLightingHistory.IS_DEV_DEBUG || DEBUG) {
            StringBuffer stringBuffer3 = new StringBuffer("isAcceptableApplication: getRejectablePolicy ");
            stringBuffer3.append(z3);
            Slog.d(TAG, stringBuffer3.toString());
        }
        return z3;
    }

    public EdgeLightingPolicyInfo getAcceptablePolicy(String str, int i) {
        EdgeLightingPolicyInfo policyInfoWithPackageName;
        if (this.mWhitePolicy.size() < 1 || (policyInfoWithPackageName = this.mWhitePolicy.getPolicyInfoWithPackageName(str)) == null || (policyInfoWithPackageName.range & i) == 0) {
            return null;
        }
        return policyInfoWithPackageName;
    }

    public final EdgeLightingPolicyInfo getRejectablePolicy(String str, int i) {
        EdgeLightingPolicyInfo policyInfoWithPackageName;
        if (this.mBlackPolicy.size() < 1 || (policyInfoWithPackageName = this.mBlackPolicy.getPolicyInfoWithPackageName(str)) == null || (policyInfoWithPackageName.range & i) == 0) {
            return null;
        }
        return policyInfoWithPackageName;
    }

    public EdgeLightingPolicyInfo getPolicyByCategory(int i, String str) {
        EdgeLightingPolicyRepository edgeLightingPolicyRepository;
        if (i == 1) {
            edgeLightingPolicyRepository = this.mWhitePolicy;
        } else {
            edgeLightingPolicyRepository = i == 2 ? this.mBlackPolicy : null;
        }
        if (edgeLightingPolicyRepository == null || edgeLightingPolicyRepository.size() < 1) {
            return null;
        }
        return edgeLightingPolicyRepository.getPolicyInfoWithPackageName(str);
    }

    public void setRinging(boolean z) {
        this.mRinging = z;
    }

    public boolean isEdgeLightingRestricted(int i) {
        int edgeLightingRestrictState = getEdgeLightingRestrictState(i);
        if (edgeLightingRestrictState == 0) {
            return false;
        }
        Log.d(TAG, "EdgeLighting is Restricted(" + edgeLightingRestrictState + "), range = " + i);
        return true;
    }

    public boolean isEdgeLightingDisabled() {
        synchronized (this.mDisableRecords) {
            if (this.mDisableRecords.size() <= 0) {
                return false;
            }
            Log.d(TAG, "EdgeLighting is disabled");
            return true;
        }
    }

    public boolean isEdgeLightingDisabledByPackage(int i, String str) {
        if (str == null) {
            return false;
        }
        synchronized (this.mDisabledPackages) {
            if (this.mDisabledPackages.size() == 0) {
                return false;
            }
            ArraySet arraySet = (ArraySet) this.mDisabledPackages.get(i);
            if (arraySet != null && arraySet.size() != 0) {
                return arraySet.contains(str);
            }
            return false;
        }
    }

    public void statusBarDisabled(int i, int i2) {
        int i3 = this.mStatusBarDisabled1 ^ i;
        this.mStatusBarDisabled1 = i;
        StringBuilder sb = new StringBuilder();
        sb.append("disable: < ");
        int i4 = i & 262144;
        sb.append(i4 != 0 ? "ALERTS" : "alerts");
        int i5 = i3 & 262144;
        sb.append(i5 != 0 ? "* " : " ");
        sb.append(">");
        EdgeLightingHistory.getInstance().updateEventHistory(sb.toString());
        if (i5 != 0) {
            this.mDisableNotificationAlerts = i4 != 0;
            this.mHeadsUpObserver.onChange(true);
        }
    }

    public void setSuppressed(int i) {
        this.mSuppressed = i;
    }

    public boolean isAllowEdgelighting(boolean z) {
        int i = this.mSuppressed;
        return i == 0 || (i & 16) == 0;
    }

    public void disable(int i, String str, IBinder iBinder) {
        synchronized (this.mDisableRecords) {
            manageDisableRecoredLocked(i, str, iBinder);
        }
    }

    public void disableEdgeLighting(int i, String str, boolean z) {
        synchronized (this.mDisabledPackages) {
            ArraySet arraySet = (ArraySet) this.mDisabledPackages.get(i);
            if (arraySet == null) {
                arraySet = new ArraySet();
                this.mDisabledPackages.put(i, arraySet);
            }
            if (z) {
                arraySet.add(str);
            } else {
                arraySet.remove(str);
            }
        }
    }

    public final void manageDisableRecoredLocked(int i, String str, IBinder iBinder) {
        DisableRecord disableRecord;
        int size = this.mDisableRecords.size();
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                disableRecord = null;
                break;
            }
            disableRecord = (DisableRecord) this.mDisableRecords.get(i2);
            if (disableRecord.token.equals(iBinder)) {
                break;
            } else {
                i2++;
            }
        }
        if (i == 0 || !iBinder.isBinderAlive()) {
            if (disableRecord != null) {
                this.mDisableRecords.remove(i2);
                disableRecord.token.unlinkToDeath(disableRecord, 0);
                return;
            }
            return;
        }
        if (disableRecord == null) {
            disableRecord = new DisableRecord();
            try {
                iBinder.linkToDeath(disableRecord, 0);
                this.mDisableRecords.add(disableRecord);
            } catch (RemoteException e) {
                Slog.d(TAG, "manageDisableRecoredLocked : " + e.getMessage());
                return;
            }
        }
        disableRecord.what = i;
        disableRecord.token = iBinder;
        disableRecord.packageName = str;
    }

    public boolean isNotificationForEdgeLighting(StatusBarNotification statusBarNotification) {
        Notification notification = statusBarNotification.getNotification();
        if (notification == null) {
            return false;
        }
        if (notification.getSmallIcon() != null) {
            return true;
        }
        Slog.d(TAG, "isNotificationForEdgeLighting : small icon is null : " + statusBarNotification.getPackageName());
        return false;
    }

    public void removeNotification(StatusBarNotification statusBarNotification) {
        this.mNotificationGroup.remove(statusBarNotification);
    }

    public Bundle putNotification(StatusBarNotification statusBarNotification, boolean z, boolean z2, int i, boolean z3, boolean z4) {
        CharSequence charSequence;
        CharSequence charSequence2;
        CharSequence charSequence3;
        Bundle bundle;
        GroupNotificationData groupData;
        NotificationData notificationData;
        if (statusBarNotification.getNotification() == null) {
            return null;
        }
        Bundle bundle2 = statusBarNotification.getNotification().extras;
        CharSequence charSequence4 = statusBarNotification.getNotification().tickerText != null ? statusBarNotification.getNotification().tickerText : null;
        Icon smallIcon = statusBarNotification.getNotification().getSmallIcon();
        String groupKey = statusBarNotification.getGroupKey();
        if (bundle2 != null) {
            CharSequence charSequence5 = bundle2.getCharSequence("android.title");
            charSequence3 = bundle2.getCharSequence("android.text");
            charSequence2 = charSequence5;
            charSequence = bundle2.getCharSequence("android.subText");
        } else {
            charSequence = null;
            charSequence2 = null;
            charSequence3 = null;
        }
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("putNotification tickerText:");
        sb.append(charSequence4 != null);
        sb.append(",titleText:");
        sb.append(charSequence2 != null);
        sb.append(",text:");
        sb.append(charSequence3 != null);
        sb.append(",sub:");
        sb.append(charSequence != null);
        sb.append("cn= + ");
        sb.append(this.mResumedComponent);
        Slog.d(str, sb.toString());
        cleanUp();
        synchronized (this.mNotificationMap) {
            int i2 = statusBarNotification.getNotification().ledARGB;
            if (i2 == 0 && (notificationData = (NotificationData) this.mNotificationMap.get(statusBarNotification.getPackageName())) != null) {
                i2 = notificationData.mNotificationInfo.getInt("color", 0);
            }
            int i3 = -1000;
            try {
                INotificationManager iNotificationManager = this.mNm;
                if (iNotificationManager == null) {
                    Slog.d(str, "INotificationManager is null in putNotification, try reload");
                    this.mNm = INotificationManager.Stub.asInterface(ServiceManager.getService("notification"));
                } else {
                    i3 = iNotificationManager.getLockScreenNotificationVisibilityForPackage(statusBarNotification.getPackageName(), statusBarNotification.getUid());
                }
            } catch (RemoteException e) {
                Slog.d(TAG, "getLockScreenNotificationVisibilityForPackage failed - " + e);
            }
            long currentTimeMillis = System.currentTimeMillis();
            bundle = new Bundle();
            bundle.putBoolean("isHeadup", z);
            bundle.putBoolean("isUpdate", z2);
            bundle.putCharSequence("tickerText", charSequence4);
            bundle.putCharSequence("titleText", charSequence2);
            bundle.putCharSequence("text", charSequence3);
            bundle.putCharSequence("subText", charSequence);
            bundle.putParcelable("smallIcon", smallIcon);
            bundle.putInt("flag", statusBarNotification.getNotification().flags);
            bundle.putInt("color", i2);
            bundle.putInt("notification_color", statusBarNotification.getNotification().color);
            bundle.putInt("priority", statusBarNotification.getNotification().priority);
            ComponentName componentName = this.mResumedComponent;
            bundle.putString("component", componentName != null ? componentName.flattenToString() : null);
            bundle.putLong("component_time", this.mResumedComponentTime);
            bundle.putString("group_key", groupKey);
            bundle.putParcelable("content_intent", statusBarNotification.getNotification().contentIntent);
            bundle.putInt("user_id", statusBarNotification.getUser().getIdentifier());
            bundle.putString("noti_key", statusBarNotification.getKey());
            bundle.putInt("noti_id", statusBarNotification.getId());
            bundle.putString("noti_tag", statusBarNotification.getTag());
            bundle.putInt("noti_visiblity", i);
            bundle.putInt("package_visiblity", i3);
            bundle.putBoolean("alert_group", statusBarNotification.isGroup() && statusBarNotification.getNotification().suppressAlertingDueToGrouping());
            bundle.putCharSequence("category", statusBarNotification.getNotification().category);
            bundle.putBoolean("audible_alert", z3);
            bundle.putCharSequence("channel_id", statusBarNotification.getNotification().getChannelId());
            bundle.putBoolean("bubble", z4);
            if (statusBarNotification.getNotification().actions != null) {
                ArrayList<? extends Parcelable> arrayList = new ArrayList<>();
                for (Notification.Action action : statusBarNotification.getNotification().actions) {
                    arrayList.add(action);
                }
                bundle.putParcelableArrayList("noti_actions", arrayList);
            }
            NotificationData notificationData2 = new NotificationData(currentTimeMillis, bundle);
            if (groupKey != null) {
                this.mNotificationGroup.updateNotificationData(statusBarNotification, notificationData2);
            }
            if (statusBarNotification.getNotification().isGroupSummary() && (groupData = this.mNotificationGroup.getGroupData(statusBarNotification.getGroupKey())) != null && groupData.getLastChild() != null) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(bundle.getCharSequence("tickerText"));
                sb2.append(" -> ");
                sb2.append(groupData.getLastChild().mNotificationInfo.getCharSequence("tickerText"));
                sb2.append(" , ");
                bundle.putCharSequence("tickerText", groupData.getLastChild().mNotificationInfo.getCharSequence("tickerText"));
                sb2.append(bundle.getCharSequence("titleText"));
                sb2.append(" -> ");
                sb2.append(groupData.getLastChild().mNotificationInfo.getCharSequence("titleText"));
                sb2.append(" , ");
                bundle.putCharSequence("titleText", groupData.getLastChild().mNotificationInfo.getCharSequence("titleText"));
                sb2.append(bundle.getCharSequence("text"));
                sb2.append(" -> ");
                sb2.append(groupData.getLastChild().mNotificationInfo.getCharSequence("text"));
                sb2.append(" , ");
                bundle.putCharSequence("text", groupData.getLastChild().mNotificationInfo.getCharSequence("text"));
                sb2.append(bundle.getCharSequence("subText"));
                sb2.append(" -> ");
                sb2.append(groupData.getLastChild().mNotificationInfo.getCharSequence("subText"));
                bundle.putCharSequence("subText", groupData.getLastChild().mNotificationInfo.getCharSequence("subText"));
                bundle.putString("noti_key", groupData.getLastChild().mNotificationInfo.getString("noti_key"));
                bundle.putParcelable("content_intent", groupData.getLastChild().mNotificationInfo.getParcelable("content_intent"));
                if (DEBUG) {
                    Slog.i(TAG, "Summary notification display. Change text " + sb2.toString());
                }
            }
            if (!statusBarNotification.isGroup() && !statusBarNotification.getNotification().suppressAlertingDueToGrouping()) {
                this.mNotificationMap.put(statusBarNotification.getPackageName(), notificationData2);
            }
        }
        return bundle;
    }

    public static boolean isEmptyText(Bundle bundle) {
        if (bundle == null) {
            return true;
        }
        return TextUtils.isEmpty(bundle.getCharSequence("tickerText", null)) && TextUtils.isEmpty(bundle.getCharSequence("titleText", null)) && TextUtils.isEmpty(bundle.getCharSequence("text", null)) && TextUtils.isEmpty(bundle.getCharSequence("subText", null));
    }

    public final void cleanUp() {
        synchronized (this.mNotificationMap) {
            if (this.mNotificationMap.size() < 10) {
                return;
            }
            long currentTimeMillis = System.currentTimeMillis();
            Iterator it = this.mNotificationMap.entrySet().iterator();
            while (it.hasNext()) {
                if (((NotificationData) ((Map.Entry) it.next()).getValue()).mTime + 10000 < currentTimeMillis) {
                    it.remove();
                }
            }
        }
    }

    public NotificationData getValidNotificationData(String str) {
        synchronized (this.mNotificationMap) {
            long currentTimeMillis = System.currentTimeMillis();
            NotificationData notificationData = (NotificationData) this.mNotificationMap.get(str);
            if (notificationData == null) {
                return null;
            }
            if (EdgeLightingHistory.IS_DEV_DEBUG || DEBUG) {
                Slog.d(TAG, "getValidNotificationData packageName=" + str + ",now=" + currentTimeMillis + ",time=" + notificationData.mTime + ",ret=" + (currentTimeMillis - notificationData.mTime));
            }
            if (currentTimeMillis - notificationData.mTime < 4000) {
                return notificationData;
            }
            return null;
        }
    }

    public void setResumedComponent(ComponentName componentName) {
        if (componentName != null) {
            this.mResumedComponent = componentName;
            this.mResumedComponentTime = System.currentTimeMillis();
        }
    }

    public final int getEdgeLightingRestrictState(int i) {
        boolean z;
        int i2;
        CoverState coverState;
        if (this.mContext.getResources().getConfiguration().semMobileKeyboardCovered != 1) {
            if (this.mCoverManager == null) {
                this.mCoverManager = new CoverManager(this.mContext);
            }
            CoverManager coverManager = this.mCoverManager;
            if (coverManager == null || (coverState = coverManager.getCoverState()) == null) {
                z = true;
                i2 = 2;
            } else {
                z = coverState.getSwitchState();
                i2 = coverState.getType();
            }
            if (!z && (!isSupportedCover(i2) || i != 1)) {
                Slog.d(TAG, "getEdgeLightingRestrictState coverOpen=" + z + ", type=" + i2);
                return 2;
            }
        }
        if (EMERGENCY_MODE_ENABLED && SemEmergencyManager.isEmergencyMode(this.mContext)) {
            return 10;
        }
        if (this.mContext.getResources().getConfiguration().semDesktopModeEnabled != 1 || Settings.System.getIntForUser(this.mContext.getContentResolver(), "new_dex", 0, -2) == 1) {
            return 0;
        }
        Slog.d(TAG, "Desktop mode enabled and it is not new dex mode");
        return 11;
    }

    public class DisableRecord implements IBinder.DeathRecipient {
        public String packageName;
        public IBinder token;
        public int what;

        public DisableRecord() {
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            Slog.i(EdgeLightingPolicyManager.TAG, "binder died for packageName = " + this.packageName);
            EdgeLightingPolicyManager.this.disable(0, this.packageName, this.token);
            this.token.unlinkToDeath(this, 0);
        }
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println("-EdgeLightingPolicy start : type = " + this.mPolicyType + ", version = " + this.mPolicyVersion);
        if (EdgeLightingHistory.IS_DEV_DEBUG || DEBUG) {
            synchronized (this.mNotificationMap) {
                for (Map.Entry entry : this.mNotificationMap.entrySet()) {
                    printWriter.println("  " + ((String) entry.getKey()) + XmlUtils.STRING_ARRAY_SEPARATOR + entry.getValue());
                }
                printWriter.println("");
            }
        }
        synchronized (this.mDisableRecords) {
            Iterator it = this.mDisableRecords.iterator();
            while (it.hasNext()) {
                DisableRecord disableRecord = (DisableRecord) it.next();
                printWriter.println("  [DisableRecord] : " + disableRecord.packageName + " " + disableRecord.what);
            }
            printWriter.println("");
        }
        synchronized (this.mDisabledPackages) {
            int size = this.mDisabledPackages.size();
            for (int i = 0; i < size; i++) {
                printWriter.println("  [DisabledPackages] : type " + this.mDisabledPackages.keyAt(i) + " " + ((ArraySet) this.mDisabledPackages.valueAt(i)).toString());
            }
            printWriter.println("");
        }
        if (EdgeLightingHistory.IS_DEV_DEBUG || DEBUG) {
            this.mWhitePolicy.dump(fileDescriptor, printWriter, strArr, "white");
            this.mBlackPolicy.dump(fileDescriptor, printWriter, strArr, "black");
        }
        printWriter.println("mSuppressed=" + this.mSuppressed);
        printWriter.println("");
    }

    public class NotificationGroup {
        public final HashMap mGroupMap;

        public NotificationGroup() {
            this.mGroupMap = new HashMap();
        }

        public void remove(StatusBarNotification statusBarNotification) {
            synchronized (this.mGroupMap) {
                Slog.d(EdgeLightingPolicyManager.TAG + ":NotificationGroup", "remove : sbn : " + statusBarNotification);
                GroupNotificationData groupNotificationData = (GroupNotificationData) this.mGroupMap.get(getKey(statusBarNotification));
                if (groupNotificationData != null) {
                    if (statusBarNotification.getKey() != null) {
                        groupNotificationData.removeChild(statusBarNotification.getKey());
                        if (groupNotificationData.getChildNotificationCount() == 0) {
                            this.mGroupMap.remove(getKey(statusBarNotification));
                        }
                    } else if (groupNotificationData.getChildNotificationCount() == 0) {
                        this.mGroupMap.remove(getKey(statusBarNotification));
                    }
                } else {
                    this.mGroupMap.remove(getKey(statusBarNotification));
                }
                dump();
            }
        }

        public void updateNotificationData(StatusBarNotification statusBarNotification, NotificationData notificationData) {
            synchronized (this.mGroupMap) {
                if (statusBarNotification.getNotification().isGroupSummary() && notificationData.mNotificationInfo.getBoolean("isHeadup", false)) {
                    Slog.d(EdgeLightingPolicyManager.TAG + ".NotificationGroup", "updateNotificationData replace :" + getKey(statusBarNotification) + ", group  size=" + this.mGroupMap.size());
                    GroupNotificationData groupNotificationData = (GroupNotificationData) this.mGroupMap.get(getKey(statusBarNotification));
                    if (groupNotificationData != null) {
                        NotificationData child = groupNotificationData.getChild(statusBarNotification.getKey());
                        if (child != null) {
                            notificationData.mNotificationInfo.putCharSequence("tickerText", child.mNotificationInfo.getCharSequence("tickerText"));
                            notificationData.mNotificationInfo.putCharSequence("titleText", child.mNotificationInfo.getCharSequence("titleText"));
                            notificationData.mNotificationInfo.putCharSequence("text", child.mNotificationInfo.getCharSequence("text"));
                            notificationData.mNotificationInfo.putCharSequence("subText", child.mNotificationInfo.getCharSequence("subText"));
                        }
                    } else {
                        groupNotificationData = new GroupNotificationData();
                    }
                    groupNotificationData.setSummaryNotification(notificationData);
                    this.mGroupMap.put(getKey(statusBarNotification), groupNotificationData);
                } else {
                    GroupNotificationData groupNotificationData2 = (GroupNotificationData) this.mGroupMap.get(getKey(statusBarNotification));
                    if (groupNotificationData2 == null) {
                        groupNotificationData2 = new GroupNotificationData();
                    }
                    if (statusBarNotification.getNotification().isGroupSummary()) {
                        groupNotificationData2.setSummaryNotification(notificationData);
                    } else {
                        groupNotificationData2.putChild(statusBarNotification.getKey(), notificationData);
                    }
                    this.mGroupMap.put(getKey(statusBarNotification), groupNotificationData2);
                    dump();
                }
            }
        }

        public GroupNotificationData getGroupData(String str) {
            GroupNotificationData groupNotificationData;
            synchronized (this.mGroupMap) {
                groupNotificationData = (GroupNotificationData) this.mGroupMap.get(str);
            }
            return groupNotificationData;
        }

        public final String getKey(StatusBarNotification statusBarNotification) {
            return statusBarNotification.getGroupKey();
        }

        public final void dump() {
            synchronized (this.mGroupMap) {
                if (EdgeLightingPolicyManager.DEBUG) {
                    for (Map.Entry entry : this.mGroupMap.entrySet()) {
                        Slog.d(EdgeLightingPolicyManager.TAG + ".NotificationGroup", "group key = " + ((String) entry.getKey()) + " ,value= " + entry.getValue());
                    }
                } else {
                    Slog.d(EdgeLightingPolicyManager.TAG + ".NotificationGroup", "group notification count : " + this.mGroupMap.size());
                }
            }
        }
    }

    public GroupNotificationData getGroupNotificationData(String str) {
        return this.mNotificationGroup.getGroupData(str);
    }

    public class GroupNotificationData {
        public HashMap mChildMap = new LinkedHashMap();
        public NotificationData mSumaaryNotification;

        public void removeChild(String str) {
            synchronized (this.mChildMap) {
                this.mChildMap.remove(str);
            }
        }

        public void putChild(String str, NotificationData notificationData) {
            synchronized (this.mChildMap) {
                if (this.mChildMap.containsKey(str)) {
                    Slog.d(EdgeLightingPolicyManager.TAG + ".NotificationGroup", "Remove previous child data. " + str);
                    this.mChildMap.remove(str);
                }
                this.mChildMap.put(str, notificationData);
            }
        }

        public NotificationData getChild(String str) {
            NotificationData notificationData;
            synchronized (this.mChildMap) {
                notificationData = (NotificationData) this.mChildMap.get(str);
            }
            return notificationData;
        }

        public NotificationData getLastChild() {
            if (this.mChildMap.size() <= 0) {
                return null;
            }
            Map.Entry entry = (Map.Entry) new ArrayList(this.mChildMap.entrySet()).get(r0.size() - 1);
            Slog.d(EdgeLightingPolicyManager.TAG + ".NotificationGroup", " last child " + entry);
            return (NotificationData) entry.getValue();
        }

        public void setSummaryNotification(NotificationData notificationData) {
            this.mSumaaryNotification = notificationData;
        }

        public NotificationData getSummaryNotification() {
            return this.mSumaaryNotification;
        }

        public int getChildNotificationCount() {
            return this.mChildMap.size();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("summary : ");
            sb.append(this.mSumaaryNotification);
            sb.append(" , child : ");
            sb.append(this.mChildMap.size());
            if (EdgeLightingHistory.IS_DEV_DEBUG || EdgeLightingPolicyManager.DEBUG) {
                synchronized (this.mChildMap) {
                    for (Map.Entry entry : this.mChildMap.entrySet()) {
                        sb.append("key = " + ((String) entry.getKey()) + " , value = " + entry.getValue() + ",");
                    }
                }
            }
            sb.append(" } ");
            return sb.toString();
        }
    }
}
